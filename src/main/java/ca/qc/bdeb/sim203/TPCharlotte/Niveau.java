package ca.qc.bdeb.sim203.TPCharlotte;

import ca.qc.bdeb.sim203.TPCharlotte.Poissons.Ennemis;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Niveau {
    private Background bg;
    private static int nbNiveau = 0;
    private int numNiveau;
    private static ArrayList<Image> images = new ArrayList<>();
    private ArrayList<Ennemis> poissons = new ArrayList<>();
    private double tempsCreationNiveau;
    private double tempsExec;
    private boolean isOver = false;

    public Niveau() {
        var rnd = new Random();
        bg = new Background(new BackgroundFill(Color.hsb(rnd.nextDouble(190, 271), 0.84,
                1.0), null, null));
        nbNiveau++;
        numNiveau = nbNiveau;
        tempsCreationNiveau = System.currentTimeMillis();
    }

    public int getNumNiveau() {
        return numNiveau;
    }

    public static void creerImages() {
        for (int i = 1; i <= 6; i++) {
            images.add(new Image("code/decor" + i + ".png"));
        }
    }

    public Background getBg() {
        return bg;
    }

    public double getTempsExec() {
        return tempsExec;
    }

    public ArrayList<Ennemis> getPoissons() {
        return poissons;
    }

    public double getTempsCreationNiveau() {
        return tempsCreationNiveau;
    }

    public void spawnEnnemis() {
        tempsExec = System.currentTimeMillis();
        var random = new Random();
        int nbPoissons = random.nextInt(1, 6);
        for (int i = 0; i < nbPoissons; i++) {
            poissons.add(new Ennemis(Main.WIDTH, random.nextDouble(Main.HEIGHT * 0.2, Main.HEIGHT * 0.81)));
            poissons.get(i).setVx(-(100 * Math.pow(numNiveau, 0.33) + 200));
        }
    }

    public void isPlusLa() {
        for (int i = 0; i < poissons.size(); i++) {
            if (poissons.get(i).getX() + poissons.get(i).getImagePoisson().getWidth() < 0) {
                poissons.remove(poissons.get(i));
                i--;
            }
        }
    }

    public boolean isOver() {
        return isOver;
    }
}
