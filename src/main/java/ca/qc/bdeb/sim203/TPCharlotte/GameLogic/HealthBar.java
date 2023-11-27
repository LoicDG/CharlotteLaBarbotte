package ca.qc.bdeb.sim203.TPCharlotte.GameLogic;

import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons.Charlotte;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class HealthBar {
    private Charlotte charlotte;
    private double w = 150, h = 20, x = 10, y = 15;
    private double pvRestants;
    private Image currentProjectile;
    private Image[] imagesProjectiles = new Image[3];

    public HealthBar(Charlotte charlotte) {
        this.charlotte = charlotte;
        pvRestants = 150;
        imagesProjectiles[0] = new Image("code/etoile.png");
        imagesProjectiles[1] = new Image("code/hippocampe.png");
        imagesProjectiles[2] = new Image("code/sardines.png");
        currentProjectile = imagesProjectiles[0];
    }

    public void setX(double x) {
        this.x = x;
    }

    public void update() {
        pvRestants = charlotte.getPv() * 37.5;
        int choix = charlotte.getChoixProjectile();
        switch (choix) {
            case 1 -> currentProjectile = imagesProjectiles[0];
            case 2 -> currentProjectile = imagesProjectiles[1];
            case 3 -> currentProjectile = imagesProjectiles[2];
        }
    }

    public void draw(GraphicsContext context) {
        context.setFill(Color.WHITE);
        context.setStroke(Color.WHITE);
        context.strokeRect(x, y, w, h);
        context.fillRect(x, y, pvRestants, h);
        context.drawImage(currentProjectile, x + w + 15, y - 5);
    }
}
