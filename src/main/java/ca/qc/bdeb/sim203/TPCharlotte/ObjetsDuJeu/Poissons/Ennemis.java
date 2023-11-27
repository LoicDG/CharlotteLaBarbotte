package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons;

import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.Input;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Ennemis extends Poisson {
    private static ArrayList<String> poissonsEnnemis = new ArrayList<>();

    public Ennemis(double x, double y, int numNiveau) {
        super(x, y);
        image = new Image(getUrlImage());
        double ratio = image.getHeight() / image.getWidth();
        double taille = Input.rnd.nextInt(50, 121);
        w = taille / ratio;
        h = taille;
        ax = -500;
        vx = -(100 * Math.pow(numNiveau, 0.33) + 200);
        ay = Input.rnd.nextDouble(-100, 101);
    }


    public static void creerImageEnnemis() {
        poissonsEnnemis.add("code/poisson1.png");
        poissonsEnnemis.add("code/poisson2.png");
        poissonsEnnemis.add("code/poisson3.png");
        poissonsEnnemis.add("code/poisson4.png");
        poissonsEnnemis.add("code/poisson5.png");
    }

    public static String getUrlImage() {
        int randomIndex = Input.rnd.nextInt(poissonsEnnemis.size());
        return poissonsEnnemis.get(randomIndex);
    }
}
