package ca.qc.bdeb.sim203.TPCharlotte.Projectiles;

import ca.qc.bdeb.sim203.TPCharlotte.CanCollide;
import ca.qc.bdeb.sim203.TPCharlotte.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Projectiles implements CanCollide {
    protected double posX;
    protected double posY;
    protected Image imageProjectile;
    protected double vitesseX;
    protected double vitesseY;
    protected double largeur;
    protected double hauteur;


    public Projectiles(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;

    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public Image getImageProjectile() {
        return imageProjectile;
    }

    public abstract void updatePhysique(double deltaTime);

    public void draw(GraphicsContext gc) {
        gc.drawImage(imageProjectile, posX, posY);
    }

    public boolean estSortiEcran() {
        return posX + largeur < 0 || posX > Main.WIDTH || posY + hauteur < 0 || posY > Main.HEIGHT;
    }
}

