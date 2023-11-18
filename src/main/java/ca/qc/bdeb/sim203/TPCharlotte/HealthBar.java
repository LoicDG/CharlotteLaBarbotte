package ca.qc.bdeb.sim203.TPCharlotte;

import ca.qc.bdeb.sim203.TPCharlotte.Poissons.Charlotte;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class HealthBar {
    private Charlotte charlotte;
    private double w = 150, h = 20, x = 10, y = 15;
    private double pvRestants;
    private Image currentProjectile;

    public HealthBar(Charlotte charlotte) {
        this.charlotte = charlotte;
        pvRestants = 150;
        currentProjectile = new Image("code/etoile.png");
    }

    public void setCurrentProjectile(Image currentProjectile) { //TODO: This method will be useful when adding barrel
        this.currentProjectile = currentProjectile;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void update() {
        pvRestants = charlotte.getPv() * 37.5;
        int choix = charlotte.getChoixProjectile();
        switch (choix) {
            case 1 -> currentProjectile = new Image("code/etoile.png");
            case 2 -> currentProjectile = new Image("code/hippocampe.png");
            case 3 -> currentProjectile = new Image("code/sardines.png");
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
