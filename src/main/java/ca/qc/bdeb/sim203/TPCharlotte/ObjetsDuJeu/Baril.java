package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu;

import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.CanCollide;
import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.Input;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Baril extends ObjetDuJeu implements CanCollide {
    private boolean ouvert;
    private long tempsDeCreation;

    public Baril(double x, double y, long tempsDeCreation) {
        super(x, y);
        this.tempsDeCreation = tempsDeCreation;
        image = new Image("code/baril.png");
        w = image.getWidth();
        h = image.getHeight();
        ouvert = false;
    }

    public void setImageBaril(Image imageBaril) {
        this.image = imageBaril;
    }

    public boolean isOuvert() {
        return ouvert;
    }

    public void setOuvert(boolean ouvert) {
        this.ouvert = ouvert;
    }

    public void updatePhysique() {
        double t = System.currentTimeMillis() - tempsDeCreation;
        y = ((590 - 166) / 2) * Math.sin((2 * Math.PI * t) / 3000) + ((590 - 166) / 2);
    }
}
