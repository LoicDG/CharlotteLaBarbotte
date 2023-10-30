package ca.qc.bdeb.sim203.TPCharlotte.Poissons;

import ca.qc.bdeb.sim203.TPCharlotte.*;
import ca.qc.bdeb.sim203.TPCharlotte.Projectiles.BoiteDeSardine;
import ca.qc.bdeb.sim203.TPCharlotte.Projectiles.EtoileDeMer;
import ca.qc.bdeb.sim203.TPCharlotte.Projectiles.Hippocampes;
import ca.qc.bdeb.sim203.TPCharlotte.Projectiles.Projectiles;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Charlotte extends Poisson {

    public void setChoixProjectile(int choixProjectile) {
        this.choixProjectile = choixProjectile;
    }

    private int choixProjectile;
    private ArrayList<Projectiles> projectilesTires;
    private long tempsDuDernierTir;
    private static final long FREQUENCE_TIRS = 500; // 0.5 seconds in milliseconds
    private int pv = 4;
    private boolean invincible = false;
    private long tempsTouchee = 0;
    private double tempsVisible = 0;
    private long deathTime;
    private boolean first = true;


    public Charlotte(Image imagePoisson, double x, double y) {
        super(imagePoisson, x, y);
        projectilesTires = new ArrayList<>();
        tempsDuDernierTir = 0;
        choixProjectile = 1;
    }

    public int getChoixProjectile() {
        return choixProjectile;
    }

    public ArrayList<Projectiles> getProjectilesTires() {
        return projectilesTires;
    }

    public int getPv() {
        return pv;
    }

    public boolean isAlive() {
        if (pv == 0 && first) {
            deathTime = System.currentTimeMillis();
            first = false;
        }
        return pv > 0;
    }

    public long getDeathTime() {
        return deathTime;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public long getTempsTouchee() {
        return tempsTouchee;
    }

    public void setTempsTouchee(long tempsTouchee) {
        this.tempsTouchee = tempsTouchee;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void update(double deltaTime, Niveau niveauCourant) {
        super.update(deltaTime);
        if ((double) System.currentTimeMillis() / 1000 - tempsVisible >= 0.5 && invincible) {
            tempsVisible = (double) System.currentTimeMillis() / 1000;
        }
        invincible = (System.currentTimeMillis() - tempsTouchee) / 1000 < 2;
        if ((vx != 0 || vy != 0) && !invincible) {
            imagePoisson = new Image("code/charlotte-avant.png");
        } else if (invincible) {
            imagePoisson = new Image("code/charlotte-outch.png");
        } else {
            imagePoisson = new Image("code/charlotte.png");
        }

        //Mode débug
        if (Input.isPressed(KeyCode.D)) {
            if (Input.isPressed(KeyCode.Q)) {
                choixProjectile = 1;
            } else if (Input.isPressed(KeyCode.W)) {
                choixProjectile = 2;
            } else if (Input.isPressed(KeyCode.E)){
                choixProjectile = 3;
            }
            if (Input.isPressed(KeyCode.R)) {
                pv = 4;
            }
        }

        long tempsMaintenant = System.currentTimeMillis();
        if (Input.isPressed(KeyCode.SPACE) && tempsMaintenant - tempsDuDernierTir >= FREQUENCE_TIRS) {
            switch (choixProjectile) {
                case 1 -> {
                    projectilesTires.add(new EtoileDeMer(x, y));
                }
                case 2 -> {
                    for (int i = 0; i < 3; i++) {
                        projectilesTires.add(new Hippocampes(x, y));
                    }
                }
                case 3 -> {
                    projectilesTires.add(new BoiteDeSardine(x,y,niveauCourant.getPoissons()));
                }
            }
            tempsDuDernierTir = tempsMaintenant;
        }
    }

    @Override
    public void draw(GraphicsContext context) {
        if ((double) System.currentTimeMillis() / 1000 - tempsVisible <= 0.25 || !invincible) {
            super.draw(context);
        }
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
        // Adjust velocity based on input
        ax = adjustAcceleration(vx, right, left);
        if (ax == 0) vx = 0;
        vx = adjustSpeed(vx);

        ay = adjustAcceleration(vy, down, up);
        if (ay == 0) vy = 0;
        vy = adjustSpeed(vy);

        // Update Charlotte's position
        x += vx * deltaTime;
        y += vy * deltaTime;

        // Ensure Charlotte stays within the screen boundaries
        x = adjustPosition(x, Main.WIDTH, w);
        y = adjustPosition(y, Main.HEIGHT, h);
    }

    private double adjustPosition(double pos, double tailleMax, double taillePoisson) {
        if (pos < 0) {
            pos = 0;
        } else if (pos > tailleMax - taillePoisson) {
            pos = tailleMax - taillePoisson;
        }
        return pos;
    }

    private double adjustSpeed(double v) {
        if (v > 300) v = 300;
        if (v < -300) v = -300;
        return v;
    }

    private double adjustAcceleration(double v, boolean plus, boolean moins) {
        double deceleration = -500;
        double a;
        if (moins) {
            a = -1000;
        } else if (plus) {
            a = 1000;
        } else {
            if ((v <= 5 && v >= 0) || (v >= -5 && v <= 0)) {
                a = 0;
                return a;
            }
            a = deceleration * v / Math.abs(v);
        }
        return a;
    }

    @Override
    protected void updatePhysique(double deltaTime) {
        super.updatePhysique(deltaTime);
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

    public void restart() {
        x = 0;
        y = Main.HEIGHT / 2;
        vy = 0;
        vx = 0;
        ax = 0;
        ay = 0;
        choixProjectile = 1;
        pv = 4;
    }

    public void isTouchee() {
        invincible = true;
        tempsVisible = (double) System.currentTimeMillis() / 1000;
        pv -= 1;
    }
}
