package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public abstract class Projectiles {
    private final double LONGUEUR_ECRAN = 900;
    private final double HAUTEUR_ECRAN = 590;
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

    protected abstract void updatePhysique(double deltaTime);

    protected void dessiner(GraphicsContext gc) {
        gc.drawImage(imageProjectile,posX,posY);
    }

    protected boolean estSortiEcran(){
        return posX + largeur < 0 || posX > LONGUEUR_ECRAN || posY + hauteur < 0 || posY > HAUTEUR_ECRAN;
    }
}

