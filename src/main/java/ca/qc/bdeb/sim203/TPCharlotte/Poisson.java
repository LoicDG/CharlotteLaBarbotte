package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Poisson {
    private Image imagePoisson;
    private ImageView poisson = new ImageView();
    private double vx, vy, ax, ay;
    private double x, y;

    public Poisson(Image imagePoisson, double x, double y) {
        this.imagePoisson = imagePoisson;
        this.x = x;
        this.y = y;
        poisson.setImage(imagePoisson);
    }

    protected Image getImagePoisson() {
        return imagePoisson;
    }

    public void update(double deltaTime) {
        updatePhysique(deltaTime);
        boolean left = Input.isPressed(KeyCode.LEFT);
        boolean right = Input.isPressed(KeyCode.RIGHT);
        boolean up = Input.isPressed(KeyCode.UP);
        boolean down = Input.isPressed(KeyCode.DOWN);
        if (left) {
            ax = -1000;
        } else if (right) {
            ax = 1000;
        } else if (up) {
            ay = -1000;
        } else if (down) {
            ay = 1000;
        } else if (!left && !right) {
            ax = -100;
        }
    }

    private void updatePhysique(double deltaTime) {
        x += deltaTime * vx;
        y += deltaTime * vy;
        vx += deltaTime * ax;
        vy += deltaTime * ay;
        if (vx >= 300) {
            vx = 300;
        } else if (vx <= -300) {
            vx = -300;
        }
        if (vy <= -300) {
            vy = -300;
        } else if (vy >= 300) {
            vy = 300;
        }

    }

    public void draw(GraphicsContext context) {
        context.drawImage(imagePoisson, x, y);
    }
}
