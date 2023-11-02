package ca.qc.bdeb.sim203.TPCharlotte;

import ca.qc.bdeb.sim203.TPCharlotte.Poissons.Charlotte;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Partie {
    public void setNiveauCourant(Niveau niveauCourant) {
        this.niveauCourant = niveauCourant;
    }

    private Niveau niveauCourant;
    private HealthBar healthBar;
    private Charlotte charlotte;
    public Partie(Charlotte charlotte,HealthBar healthBar){
        this.charlotte = charlotte;
        this.healthBar = healthBar;
    }

    public void update(double deltaTime, Niveau niveauCourant){
        charlotte.update(deltaTime, niveauCourant);
        healthBar.update();

    }
    public void draw(GraphicsContext context){
        charlotte.draw(context);
        healthBar.draw(context);
    }
    public void afficherNumNiveau(GraphicsContext context){
        niveauCourant.afficherNumNiveau(context);
    }
    public void debugMode(GraphicsContext context){
        context.setFont(Font.font(-1));
        context.setFill(Color.WHITE);
        context.fillText("NB poissons: " + niveauCourant.getPoissons().size(), 10, 50);
        context.fillText("NB projectiles: " + charlotte.getProjectilesTires().size(), 10, 65);
        context.fillText("Position Charlotte: ", 10, 80);
    }
}
