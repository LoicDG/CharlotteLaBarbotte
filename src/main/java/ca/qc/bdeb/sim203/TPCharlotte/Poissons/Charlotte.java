package ca.qc.bdeb.sim203.TPCharlotte.Poissons;

import ca.qc.bdeb.sim203.TPCharlotte.*;
import ca.qc.bdeb.sim203.TPCharlotte.Projectiles.EtoileDeMer;
import ca.qc.bdeb.sim203.TPCharlotte.Projectiles.Hippocampes;
import ca.qc.bdeb.sim203.TPCharlotte.Projectiles.Projectiles;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Charlotte extends Poisson {
    //Appuyer sur la touche P permet de changer les projectiles
    //c pr le debug mode, pcq ca m'aide a tester les projectiles
    private int choixProjectile;
    private List<Projectiles> projectilesTires;
    private boolean espaceEstAppuye = false;
    private boolean pEstAppuye = false;
    private long tempsDuDernierTir;
    private static final long FREQUENCE_TIRS = 500; // 0.5 seconds in milliseconds


    public Charlotte(Image imagePoisson, double x, double y) {
        super(imagePoisson, x, y);
        projectilesTires = new ArrayList<>();
        tempsDuDernierTir = 0;
        choixProjectile = 1;

    }

    public void setX(double newX) {
        x = newX;
    }

    public void setY(double newY) {
        y = newY;
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        boolean p = Input.isPressed(KeyCode.P);
        boolean espaceEstAppuyeMaintenant = Input.isPressed(KeyCode.SPACE);

        if (p && !pEstAppuye){
            if (choixProjectile == 1) {
                choixProjectile = 2;
            } else if (choixProjectile == 2) {
                choixProjectile = 1;
            }
        } pEstAppuye = p;


        long tempsMaintenant = System.currentTimeMillis();
        if (espaceEstAppuye & !espaceEstAppuyeMaintenant&& tempsMaintenant - tempsDuDernierTir >= FREQUENCE_TIRS) {
            Projectiles newProjectile = null;
            switch (choixProjectile){
                case 1 -> {
                    newProjectile = new EtoileDeMer(x,y);projectilesTires.add(newProjectile);
                }
                case 2 -> {
                    for (int i = 0; i < 3; i++){
                        newProjectile = new Hippocampes(x,y);projectilesTires.add(newProjectile);
                    }
                }

            }
            tempsDuDernierTir = tempsMaintenant;
        }
        espaceEstAppuye = espaceEstAppuyeMaintenant;
    }

    @Override
    public void draw(GraphicsContext context) {
        super.draw(context);

        for (var projectile : projectilesTires) {
            projectile.draw(context);
            if (Input.isPressed(KeyCode.D)) {
                context.setStroke(Color.WHITE);
                context.strokeRect(projectile.getPosX(), projectile.getPosY(),
                        projectile.getImageProjectile().getWidth(), projectile.getImageProjectile().getHeight());
            }
        }

    }

    private void checkLimites(double deltaTime) {
        boolean left = Input.isPressed(KeyCode.LEFT);
        boolean right = Input.isPressed(KeyCode.RIGHT);
        boolean up = Input.isPressed(KeyCode.UP);
        boolean down = Input.isPressed(KeyCode.DOWN);
        double deceleration = -500;
        // Adjust velocity based on input
        if (left) { //TODO: Extract multiple method because code is copié collé and copié collé is nono. also a LOT of code, not rly readable
            ax = -1000;
        } else if (right) {
            ax = 1000;
        } else {
            ax = deceleration * vx / Math.abs(vx);
            if (vx == 0) ax = 0;// Stop moving horizontally
        }
        if (vx < -300) vx = -300;
        if (vx > 300) vx = 300;

        if (up) {
            ay = -1000;
        } else if (down) {
            ay = 1000;
        } else {
            ay = deceleration * vy / Math.abs(vy);
            if (vy == 0) ay = 0;// Stop moving vertically
        }
        if (vy < -300) vy = -300;
        if (vy > 300) vy = 300;


        // Update Charlotte's position
        x += vx * deltaTime;
        y += vy * deltaTime;

        // Ensure Charlotte stays within the screen boundaries
        if (x < 0) {
            x = 0;
        } else if (x > Main.WIDTH - imagePoisson.getWidth()) {
            x = Main.WIDTH - imagePoisson.getWidth();
        }

        if (y < 0) {
            y = 0;
        } else if (y > Main.HEIGHT - imagePoisson.getHeight()) {
            y = Main.HEIGHT - imagePoisson.getHeight();
        }
    }

    @Override
    protected void updatePhysique(double deltaTime) {
        super.updatePhysique(deltaTime);
        checkVitesseMax();
        checkLimites(deltaTime);
        for (int i = projectilesTires.size() - 1; i >= 0; i--) {
            var projectile = projectilesTires.get(i);
            if (projectile.estSortiEcran()) {
                projectilesTires.remove(i);
            } else {
                projectile.updatePhysique(deltaTime);
            }
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
