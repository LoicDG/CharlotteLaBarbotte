package ca.qc.bdeb.sim203.TPCharlotte.Poissons;

import ca.qc.bdeb.sim203.TPCharlotte.CanCollide;
import ca.qc.bdeb.sim203.TPCharlotte.Input;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetDuJeu;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Poisson implements CanCollide {
    protected Image imagePoisson;

    public double getVx() {
        return vx;
    }

    protected double vx, vy, ax, ay;
    protected double x, y;
    protected double w, h;

    public Poisson(Image imagePoisson, double x, double y) {
        this.imagePoisson = imagePoisson;
        this.x = x;
        this.y = y;
        w = imagePoisson.getWidth();
        h = imagePoisson.getHeight();
    }

    public Poisson(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Image getImagePoisson() {
        return imagePoisson;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public void update(double deltaTime) {
        updatePhysique(deltaTime);
    }

    protected void updatePhysique(double deltaTime) {
        vx += deltaTime * ax;
        vy += deltaTime * ay;
        x += deltaTime * vx;
        y += deltaTime * vy;
    }

    public void draw(GraphicsContext context) {
        context.drawImage(imagePoisson, x, y, w, h);
        drawHitBox(context);
    }

    protected void drawHitBox(GraphicsContext context) {
        if (Input.isPressed(KeyCode.D)) {
            context.setStroke(Color.WHITE);
            context.strokeRect(x, y, w, h);
        }
    }

    public boolean isEnCollision(CanCollide objet) {
        double dx = x - objet.getX();
        double dy = y - objet.getY();
        double dCarre = dx * dx + dy * dy;
        return dCarre < (w / 2 + objet.getW() / 2) *
                (w / 2 + objet.getH() / 2);
    }
}
