package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu;

import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.Input;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public abstract class ObjetDuJeu {
    protected double x, y, w, h, vx, vy, ax, ay;
    protected Image image;

    public ObjetDuJeu(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return image.getWidth();
    }

    public double getH() {
        return image.getHeight();
    }

    public double getVx() {
        return vx;
    }

    public Image getImage() {
        return image;
    }

    public void update(double deltaTime) {
        updatePhysique(deltaTime);
    }

    public void updatePhysique(double deltaTime) {
        vx += ax * deltaTime;
        vy += ay * deltaTime;
        x += vx * deltaTime;
        y += vy * deltaTime;
    }

    public void draw(GraphicsContext context) {
        context.drawImage(image, x, y, w, h);
        if (Input.isPressed(KeyCode.D)) {
            drawContour(context);
        }
    }

    public void drawContour(GraphicsContext context) {
        context.setStroke(Color.WHITE);
        context.strokeRect(x, y, w, h);
    }
}