package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles;

import ca.qc.bdeb.sim203.TPCharlotte.Main;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons.Ennemis;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class BoiteDeSardine extends Projectiles {
    private final static double K = 1000;
    ArrayList<Ennemis> listeEnnemis;
    double forceEnX;//acceleration en y
    double forceEnY;//acceleration en x

    /**
     * Constructeur de la classe BoiteDeSardine
     *
     * @param posX         Position horizontale initiale du projectile.
     * @param posY         Position verticale initiale du projectile.
     * @param listeEnnemis Un ArrayList contenant des ennemis à calculer (ou pas) pour
     *                     influencer le comportement d'une BoiteDeSardine
     */
    public BoiteDeSardine(double posX, double posY, ArrayList<Ennemis> listeEnnemis) {
        super(posX, posY);
        image = new Image("code/sardines.png");
        w = image.getWidth();
        h = image.getHeight();
        super.x -= w / 2;
        super.y -= h / 2;
        vx = 300;
        vy = 0;
        this.listeEnnemis = listeEnnemis;
    }

    /**
     * Met à jour la physique du projectile en fonction du temps écoulé depuis la dernière mise à jour
     * en applicant la Loi de Coulomb.
     *
     * @param deltaTime Temps écoulé depuis la dernière mise à jour.
     */
    @Override
    public void updatePhysique(double deltaTime) {
        forceEnX = 0;
        forceEnY = 0;
        for (int i = 0; i < listeEnnemis.size(); i++) {
            double distance = Math.sqrt(Math.pow(x - listeEnnemis.get(i).getX(), 2) +
                    Math.pow(y - listeEnnemis.get(i).getY(), 2));
            //Regarder si l'ennemi à depassé charlotte et/ou le projectile lui même
            if (distance < 0) {
                continue;
            }
            if (distance < 0.1) {
                distance = 0.1;
            }
            double deltaX = x - listeEnnemis.get(i).getX();
            double deltaY = y - listeEnnemis.get(i).getY();
            double proportionX = deltaX / distance;
            double proportionY = deltaY / distance;
            double forceElectrique = (K * -100 * 200) / (Math.pow(distance, 2));
            forceEnX += forceElectrique / proportionX;
            forceEnY += forceElectrique / proportionY;
        }
        vx += (forceEnX * deltaTime);
        if (vx < 300) {
            vx = 300;
        } else if (vx > 500) {
            vx = 500;
        }

        vy += (forceEnY * deltaTime);
        if (vy < -500) {
            vy = -500;
        } else if (vy > 500) {
            vy = 500;
        }

        x += vx * deltaTime;
        y += vy * deltaTime;
        validerLimite();
    }

    /**
     * Valide les limites de l'écran et ajuste la position et la vitesse en conséquence.
     */
    protected void validerLimite() {
        if (vy > 0 && y + h >= Main.HEIGHT) {
            y = Main.HEIGHT - h;
            vy *= -1;
        } else if (vy < 0 && y <= 0) {
            y = 0;
            vy *= -1;
        }
    }
}
