package ca.qc.bdeb.sim203.TPCharlotte.Poissons;

import ca.qc.bdeb.sim203.TPCharlotte.Projectiles.Projectiles;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Random;


public class Ennemis extends Poisson {
    private static ArrayList<String> poissonsEnnemis = new ArrayList<>();

    public Ennemis(double x, double y) {
        super(x, y);
        var random = new Random();
        imagePoisson = new Image(getUrlImage());
        double ratio = imagePoisson.getHeight() / imagePoisson.getWidth();
        double taille = random.nextInt(50, 121);
        w = taille / ratio;
        h = taille;
        ax = -500;
        ay = random.nextDouble(-100, 101);
    }
    public void setVx(double vx) {
        this.vx = vx;
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
