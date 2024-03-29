package ca.qc.bdeb.sim203.TPCharlotte.GameLogic;

import ca.qc.bdeb.sim203.TPCharlotte.Main;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Baril;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons.Charlotte;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons.Ennemis;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Niveau {
    private Background bg;
    private static int nbNiveau = 0;
    private int numNiveau;
    private static ArrayList<Image> images = new ArrayList<>();
    private ArrayList<Ennemis> poissons = new ArrayList<>();
    private long tempsCreationNiveau;
    private long tempsExec;
    private double tempsDepuisDisparition = 0;
    private boolean fini = false;
    private Baril baril; //chaque niveau a 1 baril
    private ArrayList<Decor> decors = new ArrayList<>();

    /**
     * Constructeur de Niveau, crée les décors, un BackGround et un baril
     */
    public Niveau() {
        bg = new Background(new BackgroundFill(Color.hsb(Input.rnd.nextDouble(190, 271), 0.84,
                1.0), null, null));
        nbNiveau++;
        numNiveau = nbNiveau;
        tempsCreationNiveau = System.currentTimeMillis();
        double distanceMinimum = Main.TAILLE_NIVEAU / 5;
        double distanceMaximum = (4 * Main.TAILLE_NIVEAU) / 5;
        double xBaril = distanceMinimum + Input.rnd.nextDouble() * (distanceMaximum - distanceMinimum);
        baril = new Baril(xBaril, 0, tempsCreationNiveau);
        double espacement = Input.rnd.nextInt(50, 101);
        double xDecors = espacement;
        while (xDecors < Main.TAILLE_NIVEAU) {
            decors.add(new Decor(xDecors));
            espacement = Input.rnd.nextInt(50, 101) + Decor.getW();
            xDecors += espacement;
        }
    }

    public Baril getBaril() {
        return baril;
    }

    public ArrayList<Decor> getDecors() {
        return decors;
    }

    public boolean isFini() {
        return fini;
    }

    public Background getBg() {
        return bg;
    }

    /**
     * Met le nombre total de niveau à 0
     */
    public static void resetNbNiveau() {
        nbNiveau = 0;
    }

    /**
     * Crée une ArrayList d'Image pour les décors
     */
    public static void creerImages() {
        for (int i = 1; i <= 6; i++) {
            images.add(new Image("code/decor" + i + ".png"));
        }
    }

    public void setTempsCreationNiveau(long tempsCreationNiveau) {
        this.tempsCreationNiveau = tempsCreationNiveau;
    }

    public ArrayList<Ennemis> getPoissons() {
        return poissons;
    }

    /**
     * Fait apparaitre les ennemis à chaque 0.75 + 1/racine carrée du numéro du niveau
     *
     * @param cam La caméra du jeu
     */
    public void faireApparaitreEnnemis(Camera cam) {
        int nbPoissons = Input.rnd.nextInt(1, 6);
        if (tempsDepuisDisparition >= (0.75 + 1 / Math.sqrt(numNiveau))) {
            for (int i = 0; i < nbPoissons; i++) {
                poissons.add(new Ennemis(cam.getX() + cam.getWidth(),
                        Input.rnd.nextDouble(Main.HEIGHT * 0.2, Main.HEIGHT * 0.81), numNiveau));
            }
            tempsExec = System.currentTimeMillis();
            tempsDepuisDisparition = 0;
        } else {
            tempsDepuisDisparition = (double) (System.currentTimeMillis() - tempsExec) / 1000;
        }
    }

    /**
     * Vérifie si les ennemis sont sortis de l'écran, si oui les supprime
     *
     * @param x Le x de la caméra, la limite de l'écran
     */
    public void isPlusLa(double x) {
        for (int i = 0; i < poissons.size(); i++) {
            if (poissons.get(i).getX() + poissons.get(i).getImage().getWidth() < x) {
                poissons.remove(poissons.get(i));
                i--;
            }
        }
    }

    /**
     * Affiche le niveau courant pendant 4 secondes
     *
     * @param context le GraphicsContext qui sert à dessiner
     */
    public void afficherNumNiveau(GraphicsContext context) {
        if (System.currentTimeMillis() - tempsCreationNiveau <= 4000) {
            context.setFont(Font.font("Comic Sans MS", 72));
            context.fillText("NIVEAU " + numNiveau, Main.WIDTH / 3, Main.HEIGHT / 2);
        }
    }

    /**
     * Vérifie si le niveau est complété
     *
     * @param charlotte Le personnage du jeu
     */
    public void checkFini(Charlotte charlotte) {
        if (charlotte.getX() + charlotte.getW() >= Main.TAILLE_NIVEAU) {
            fini = true;
        }
    }
}
