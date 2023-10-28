package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class BoiteDeSardine extends Projectiles{
    private final static double LARGEUR_ECRAN = 900;
    private final static double HAUTEUR_ECRAN = 590;
    private final static double K = 1000;
    ArrayList<Ennemis> listeEnnemis = new ArrayList<>();
    double forceEnX;//acceleration en y
    double forceEnY;//acceleration en x
    public BoiteDeSardine(double posX, double posY, ArrayList<Ennemis> listeEnnemis) {
        super(posX, posY);
        this.largeur = 35;
        this.hauteur = 29;
        this.vitesseX = 300;
        this.vitesseY = 0;
        this.imageProjectile = new Image("code/sardines.png");
        this.listeEnnemis = listeEnnemis;
    }

    @Override
    protected void updatePhysique(double deltaTime) {
        forceEnX = 0;
        forceEnY = 0;
        for (Ennemis ennemi : listeEnnemis){
           double distance = Math.sqrt(Math.pow(posX-ennemi.x,2)+Math.pow(posY-ennemi.y,2));
            if (distance < 0.01){
                distance = 0.01;
            }
            double deltaX = posX-ennemi.x;
            double deltaY = posY-ennemi.y;
            double proportionX = deltaX/distance;
            double proportionY = deltaY/distance;
            double forceElectrique = (K*-100*200)/Math.pow(distance,2);
            forceEnX += forceElectrique/proportionX;
            forceEnY += forceElectrique/proportionY;
        }
        vitesseX += (forceEnX*deltaTime);
        if (vitesseX < 300){
            vitesseX = 300;
        } else if(vitesseX > 500){
            vitesseX = 500;
        }

        vitesseY += (forceEnY*deltaTime);
        if (vitesseY < -500){
            vitesseY = -500;
        } else if (vitesseY > 500){
            vitesseY = 500;
        }

        posX += vitesseX*deltaTime;
        posY += vitesseY*deltaTime;
        validerLimite(deltaTime);
    }
    protected void validerLimite(double deltaTime) {
        if (vitesseX > 0 && posX + largeur >= LARGEUR_ECRAN) {
            posX = LARGEUR_ECRAN - largeur;
            vitesseX *= -1;
        } else if (vitesseX < 0 && posX <= 0) {
            posX = 0;
            vitesseX *= -1;
        }

        if (vitesseY > 0 && posY + hauteur >= HAUTEUR_ECRAN) {
            posY = HAUTEUR_ECRAN - hauteur;
            vitesseY *= -1;
        } else if (vitesseY < 0 && posY <= 0) {
            posY = 0;
            vitesseY *= -1;
        }
    }
}
