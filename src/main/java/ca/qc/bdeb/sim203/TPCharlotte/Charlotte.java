package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Charlotte extends Poisson {
    public Charlotte(Image imagePoisson, double x, double y) {
        super(imagePoisson, x, y);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
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

    @Override
    protected void updatePhysique(double deltaTime) {
        super.updatePhysique(deltaTime);
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
}
