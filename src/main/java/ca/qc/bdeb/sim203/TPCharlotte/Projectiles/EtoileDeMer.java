package ca.qc.bdeb.sim203.TPCharlotte.Projectiles;

import javafx.scene.image.Image;

public class EtoileDeMer extends Projectiles{
    public EtoileDeMer(double posX, double posY) {
        super(posX, posY);
        largeur = 36;
        hauteur = 35;
        imageProjectile = new Image("code/etoile.png");
        vitesseX = 800;
    }

    @Override
    public double getX() {
        return posX;
    }

    @Override
    public double getY() {
        return posY;
    }

    @Override
    public double getW() {
        return imageProjectile.getWidth();
    }

    @Override
    public double getH() {
        return imageProjectile.getHeight();
    }

    @Override
    public void updatePhysique(double deltaTime) {
        posX += deltaTime * vitesseX;
    }
}
