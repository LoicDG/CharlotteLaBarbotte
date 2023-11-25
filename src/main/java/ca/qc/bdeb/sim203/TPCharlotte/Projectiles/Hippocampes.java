package ca.qc.bdeb.sim203.TPCharlotte.Projectiles;

import javafx.scene.image.Image;

import java.util.Random;

public class Hippocampes extends Projectiles{
    private Random random = new Random();
    private double amplitudeSinus;
    private double periode = (random.nextDouble(1, 4)) * 1000;
    private double posYinitial;
    private double tempsDeLaCreation;
    public Hippocampes(double posX, double posY) {
        super(posX, posY);
        posYinitial = posY;
        tempsDeLaCreation = System.currentTimeMillis();
        largeur = 20;
        hauteur = 36;
        vitesseX = 500;
        imageProjectile = new Image("code/hippocampe.png");
        amplitudeSinus = random.nextDouble(30, 61);
        int signe = random.nextInt(2);
        if (signe == 1){
            amplitudeSinus = -amplitudeSinus;
        }


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
        double t = System.currentTimeMillis()-tempsDeLaCreation;
        posY = amplitudeSinus*Math.sin((2*Math.PI*t)/periode)+posYinitial;

    }
}
