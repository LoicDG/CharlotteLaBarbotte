package ca.qc.bdeb.sim203.TPCharlotte.Poissons;

import ca.qc.bdeb.sim203.TPCharlotte.Input;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Poisson {
    protected Image imagePoisson;
    private ImageView poisson = new ImageView();
    protected double vx, vy, ax, ay;
    protected double x, y;

    public Poisson(Image imagePoisson, double x, double y) {
        this.imagePoisson = imagePoisson;
        this.x = x;
        this.y = y;
        poisson.setImage(imagePoisson);
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

    public void update(double deltaTime) {
        updatePhysique(deltaTime);

    }

    protected void updatePhysique(double deltaTime) {
        x += deltaTime * vx;
        y += deltaTime * vy;
        vx += deltaTime * ax;
        vy += deltaTime * ay;
    }

    public void draw(GraphicsContext context) {
        context.drawImage(imagePoisson, x, y);
        if (Input.isPressed(KeyCode.D)) {
            context.setStroke(Color.WHITE);
            context.strokeRect(x, y, imagePoisson.getWidth(), imagePoisson.getHeight());
        }
    }
}
