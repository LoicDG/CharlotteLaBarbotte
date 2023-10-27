package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.image.Image;

public class Hippocampes extends Projectiles{
    private double amplitudeSinus;
    private double periode;
    private double posYinitial;
    public Hippocampes(double posX, double posY) {
        super(posX, posY);
        largeur = 20;
        hauteur = 36;
        vitesseX = 500;
        imageProjectile = new Image("code/hippocampe.png");
    }

    @Override
    protected void updatePhysique(double deltaTime) {
        posX += deltaTime * vitesseX;

    }
}
