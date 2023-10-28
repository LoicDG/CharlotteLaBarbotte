package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.image.Image;

import java.util.Random;

public class Hippocampes extends Projectiles{
    private Random random = new Random();
    private double amplitudeSinus;
    private double periode = (random.nextDouble(3)+1)*1000;
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
        amplitudeSinus = random.nextDouble(31)+30;
        int signe = random.nextInt(2);
        if (signe == 1){
            amplitudeSinus = -amplitudeSinus;
        }


    }

    @Override
    protected void updatePhysique(double deltaTime) {
        posX += deltaTime * vitesseX;
        double t = System.currentTimeMillis()-tempsDeLaCreation;
        posY = amplitudeSinus*Math.sin((2*Math.PI*t)/periode)+posYinitial;

    }
}
