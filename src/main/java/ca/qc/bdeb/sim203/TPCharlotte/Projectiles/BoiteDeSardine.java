package ca.qc.bdeb.sim203.TPCharlotte.Projectiles;

import ca.qc.bdeb.sim203.TPCharlotte.Main;
import ca.qc.bdeb.sim203.TPCharlotte.Poissons.Ennemis;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class BoiteDeSardine extends Projectiles{
    private final static double K = 1000;
    ArrayList<Ennemis> listeEnnemis;
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
    public void updatePhysique(double deltaTime) {
        forceEnX = 0;
        forceEnY = 0;
        for (int i = 0; i < listeEnnemis.size();i++){
            double distance = Math.sqrt(Math.pow(posX - listeEnnemis.get(i).getX(), 2) + Math.pow(posY - listeEnnemis.get(i).getY(), 2));
    //Checker si l'ennemi a depasse charlotte et/ou le projectile lui meme
            if (distance < 0){
                continue;
            }
            if (distance <0.1){
                distance = 0.1;
            }
            double deltaX = posX - listeEnnemis.get(i).getX();
            double deltaY = posY - listeEnnemis.get(i).getY();
            double proportionX = deltaX/distance;
            double proportionY = deltaY/distance;
            double forceElectrique = (K*-100*200)/(Math.pow(distance,2));
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
        validerLimite();
    }

    protected void validerLimite() {
        if (vitesseY > 0 && posY + hauteur >= Main.HEIGHT) {
            posY = Main.HEIGHT - hauteur;
            vitesseY *= -1;
        } else if (vitesseY < 0 && posY <= 0) {
            posY = 0;
            vitesseY *= -1;
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
}
