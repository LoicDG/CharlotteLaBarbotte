package ca.qc.bdeb.sim203.TPCharlotte.GameLogic;

import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons.Charlotte;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BarreDeVie {
    private Charlotte charlotte;
    private double w = 150, h = 20, x = 10, y = 15;
    private double pvRestants;
    private Image projectileChoisi;
    private Image[] imagesProjectiles = new Image[3];

    /**
     * Constructeur de la classe BarreDeVie.
     *
     * @param charlotte Instance de la classe Charlotte associée à cette barre de vie.
     */
    public BarreDeVie(Charlotte charlotte) {
        this.charlotte = charlotte;
        pvRestants = 150;
        imagesProjectiles[0] = new Image("code/etoile.png");
        imagesProjectiles[1] = new Image("code/hippocampe.png");
        imagesProjectiles[2] = new Image("code/sardines.png");
        projectileChoisi = imagesProjectiles[0];
    }


    public void setX(double x) {
        this.x = x;
    }

    /**
     * Méthode qui met à jour l'affichage de la barre de vie en fonction des points de vie de Charlotte
     * et du projectile choisi.
     */
    public void update() {
        pvRestants = charlotte.getPv() * 37.5;
        int choix = charlotte.getChoixProjectile();
        switch (choix) {
            case 1 -> projectileChoisi = imagesProjectiles[0];
            case 2 -> projectileChoisi = imagesProjectiles[1];
            case 3 -> projectileChoisi = imagesProjectiles[2];
        }
    }

    /**
     * Méthode qui dessine la barre de vie avec la quantité de points de vie restants
     * et l'image du projectile choisi.
     *
     * @param context Contexte graphique dans lequel dessiner la barre de vie.
     */
    public void draw(GraphicsContext context) {
        context.setFill(Color.WHITE);
        context.setStroke(Color.WHITE);
        context.strokeRect(x, y, w, h);
        context.fillRect(x, y, pvRestants, h);
        context.drawImage(projectileChoisi, x + w + 15, y - 5);
    }
}
