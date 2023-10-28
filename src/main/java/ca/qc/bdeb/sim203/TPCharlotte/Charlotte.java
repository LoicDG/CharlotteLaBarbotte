package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;


import java.util.ArrayList;
import java.util.List;

public class Charlotte extends Poisson {

    private List<Projectiles> projectilesTires;
    private boolean espaceEstAppuye;

    public Charlotte(Image imagePoisson, double x, double y) {
        super(imagePoisson, x, y);
        projectilesTires = new ArrayList<>();
        espaceEstAppuye = false;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);

        boolean espaceEstAppuyeMaintenant = Input.isPressed(KeyCode.SPACE);
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
        }
        if (!left && !right) {
            if (vx < 0) {
                ax = 100;
            } else {
                ax = -100;
            }
        }
        if (!up && !down) {
            if (vy < 0) {
                ay = 100;
            } else {
                ay = -100;
            }
        }


        if (espaceEstAppuye & !espaceEstAppuyeMaintenant) {
            Projectiles newProjectile = new EtoileDeMer(x, y);
            projectilesTires.add(newProjectile);
        }
        espaceEstAppuye = espaceEstAppuyeMaintenant;
    }

    @Override
    public void draw(GraphicsContext context) {
        super.draw(context);

        for (Projectiles projectile : projectilesTires) {
            projectile.dessiner(context);
        }

    }

    @Override
    protected void updatePhysique(double deltaTime) {
        super.updatePhysique(deltaTime);
        checkVitesseMax();
        for (Projectiles projectile : projectilesTires) {
            projectile.updatePhysique(deltaTime);
        }
    }

    private void checkVitesseMax() {
        if (vx >= 300) {
            vx = 300;
            ax = 0;
        } else if (vx <= -300) {
            vx = -300;
            ax = 0;
        }
        if (vy <= -300) {
            vy = -300;
            ay = 0;
        } else if (vy >= 300) {
            vy = 300;
            ay = 0;
        }
    }
}
