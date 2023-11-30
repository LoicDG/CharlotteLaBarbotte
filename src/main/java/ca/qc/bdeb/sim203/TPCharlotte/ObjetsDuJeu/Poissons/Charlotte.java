package ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons;

import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.Input;
import ca.qc.bdeb.sim203.TPCharlotte.GameLogic.Niveau;
import ca.qc.bdeb.sim203.TPCharlotte.Main;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles.BoiteDeSardine;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles.EtoileDeMer;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles.Hippocampes;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles.Projectiles;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class Charlotte extends Poisson {

    private int choixProjectile;
    private ArrayList<Projectiles> projectilesTires;
    private Image[] imagesCharlotte = new Image[3];
    private long tempsDuDernierTir;
    private final long FREQUENCE_TIRS = 500; // 0.5 seconds in milliseconds
    private int pv = 4;
    private boolean invincible = false;
    private long tempsTouchee = 0;
    private double tempsVisible = 0;
    private long tempsDeces;
    private boolean premiereFois = true;

    public Charlotte(Image imagePoisson, double x, double y) {
        super(imagePoisson, x, y);
        projectilesTires = new ArrayList<>();
        tempsDuDernierTir = 0;
        choixProjectile = 1;
        imagesCharlotte[0] = new Image("code/charlotte.png");
        imagesCharlotte[1] = new Image("code/charlotte-avant.png");
        imagesCharlotte[2] = new Image("code/charlotte-outch.png");
    }

    public void setChoixProjectile(int choixProjectile) {
        this.choixProjectile = choixProjectile;
    }

    public int getChoixProjectile() {
        return choixProjectile;
    }

    public ArrayList<Projectiles> getProjectilesTires() {
        return projectilesTires;
    }

    public int getPv() {
        return pv;
    }

    /**
     * Vérifie si Charlotte a encore des points de vie, si elle n'en a plus défini le temps de mort
     *
     * @return True si elle est vivante, False si elle est morte
     */
    public boolean estVivante() {
        //première fois vise à éviter de reset le temps de mort à chaque collision après la mort
        if (pv == 0 && premiereFois) {
            tempsDeces = System.currentTimeMillis();
            premiereFois = false;
        }
        return pv > 0;
    }

    public long getTempsDeces() {
        return tempsDeces;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public long getTempsTouchee() {
        return tempsTouchee;
    }

    public void setTempsTouchee(long tempsTouchee) {
        this.tempsTouchee = tempsTouchee;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Update la positiom, la vitesse, l'image de Charlotte et son état (invincible ou non)
     *
     * @param deltaTime Le temps d'exécution entre 2 appels de la méthode handle du AnimationTimer
     */
    public void update(double deltaTime) {
        super.update(deltaTime);
        if ((double) System.currentTimeMillis() / 1000 - tempsVisible >= 0.5 && invincible) {
            tempsVisible = (double) System.currentTimeMillis() / 1000;
        }
        invincible = (System.currentTimeMillis() - tempsTouchee) / 1000 < 2;
        if ((vx != 0 || vy != 0) && !invincible) {
            image = imagesCharlotte[1];
        } else if (invincible) {
            image = imagesCharlotte[2];
        } else {
            image = imagesCharlotte[0];
        }
    }

    /**
     * Crée un nouveau projectile lorsque espace est appuyé
     *
     * @param niveauCourant le niveau courant
     */
    public void creerProjectiles(Niveau niveauCourant) {
        //Mode débug
        if (Input.isPressed(KeyCode.D)) {
            if (Input.isPressed(KeyCode.Q)) {
                choixProjectile = 1;
            } else if (Input.isPressed(KeyCode.W)) {
                choixProjectile = 2;
            } else if (Input.isPressed(KeyCode.E)) {
                choixProjectile = 3;
            }
            if (Input.isPressed(KeyCode.R)) {
                pv = 4;
            }
        }
        long tempsMaintenant = System.currentTimeMillis();
        if (Input.isPressed(KeyCode.SPACE) && tempsMaintenant - tempsDuDernierTir >= FREQUENCE_TIRS) {
            switch (choixProjectile) {
                case 1 -> {
                    projectilesTires.add(new EtoileDeMer(x + w / 2, y + h / 2));
                }
                case 2 -> {
                    for (int i = 0; i < 3; i++) {
                        projectilesTires.add(new Hippocampes(x + w / 2, y + h / 2));
                    }
                }
                case 3 -> {
                    projectilesTires.add(new BoiteDeSardine(x + w / 2, y + h / 2, niveauCourant.getPoissons()));
                }
            }
            tempsDuDernierTir = tempsMaintenant;
        }
    }

    /**
     * Dessine charlotte, et la dessine seulement à chaque 0,25 seconde si elle est invincible (clignotement)
     *
     * @param context Le GraphicsContext du canvas, pour dessiner
     */
    @Override
    public void draw(GraphicsContext context) {
        if ((double) System.currentTimeMillis() / 1000 - tempsVisible <= 0.25 || !invincible) {
            super.draw(context);
        }
    }

    /**
     * Vérifie si la vitesse de Charlotte respecte les limites
     */
    public void verifierVitesse() {
        boolean left = Input.isPressed(KeyCode.LEFT);
        boolean right = Input.isPressed(KeyCode.RIGHT);
        boolean up = Input.isPressed(KeyCode.UP);
        boolean down = Input.isPressed(KeyCode.DOWN);

        ax = verifierAcceleration(vx, right, left);
        if (ax == 0) vx = 0;
        vx = verifierVitesse(vx);

        ay = verifierAcceleration(vy, down, up);
        if (ay == 0) vy = 0;
        vy = verifierVitesse(vy);
    }

    /**
     * Vérifie si charlotte est dans les bordures de l'écran
     *
     * @param xCam La position en x de la caméra
     */
    public void verifierLimites(double xCam) {
        x = verifierPosition(x, Main.TAILLE_NIVEAU, w, xCam);
        y = verifierPosition(y, Main.HEIGHT, h, 0);
    }

    /**
     * Ajuste la position de charlotte si nécessaire
     *
     * @param pos           la position à vérifier
     * @param tailleMax     la taille max à ne pas dépasser
     * @param taillePoisson la taille de l'image
     * @param limites       la taille min à ne pas dépasser
     * @return la position ajustée
     */
    private double verifierPosition(double pos, double tailleMax, double taillePoisson, double limites) {
        if (pos < limites) {
            pos = limites;
        } else if (pos > tailleMax - taillePoisson) {
            pos = tailleMax - taillePoisson;
        }
        return pos;
    }

    private double verifierVitesse(double v) {
        if (v > 300) v = 300;
        if (v < -300) v = -300;
        return v;
    }

    private double verifierAcceleration(double v, boolean plus, boolean moins) {
        double deceleration = -500;
        double a;
        if (moins) {
            a = -1000;
        } else if (plus) {
            a = 1000;
        } else {
            if ((v <= 5 && v >= 0) || (v >= -5 && v <= 0)) {
                a = 0;
                return a;
            }
            a = deceleration * v / Math.abs(v);
        }
        return a;
    }

    /**
     * Rends charlotte invincible pour 2 secondes et lui enlève une vie
     */
    public void estTouchee() {
        invincible = true;
        tempsVisible = (double) System.currentTimeMillis() / 1000;
        pv -= 1;
    }
}
