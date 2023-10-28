package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.Random;


public class Ennemis extends Poisson {
    private Image imageEnnemi;
    private static ArrayList<String> poissonsEnnemis = new ArrayList<>();


    public Ennemis(double x, double y) {
        super(x, y);
        var random = new Random();
        imageEnnemi = new Image(getUrlImage());
        vx = 100 * Math.pow(Niveau.getNbNiveau(), 0.33) + 200;
        ax = -500;
        ay = random.nextDouble(-100, 101);
    }
    public Image getImageEnnemi() {
        return imageEnnemi;
    }

    public static void creerImageEnnemis() {
        poissonsEnnemis.add("code/poisson1.png");
        poissonsEnnemis.add("code/poisson2.png");
        poissonsEnnemis.add("code/poisson3.png");
        poissonsEnnemis.add("code/poisson4.png");
        poissonsEnnemis.add("code/poisson5.png");
    }

    public static String getUrlImage() {
        Random r = new Random();
        int randomIndex = r.nextInt(poissonsEnnemis.size());
        return poissonsEnnemis.get(randomIndex);
    }

}
