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

        // Check if the space bar is pressed
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
        } else if (!left && !right) {
            ax = -100;
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
        // Update the position of all active projectiles
        for (Projectiles projectile : projectilesTires) {
            if (projectile.estSortiEcran()){
                projectilesTires.remove(projectile);
            }
            projectile.updatePhysique(deltaTime);
        }

    }
}
