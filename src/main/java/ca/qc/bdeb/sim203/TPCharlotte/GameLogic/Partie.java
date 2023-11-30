package ca.qc.bdeb.sim203.TPCharlotte.GameLogic;

import ca.qc.bdeb.sim203.TPCharlotte.Main;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Poissons.Charlotte;
import ca.qc.bdeb.sim203.TPCharlotte.ObjetsDuJeu.Projectiles.Projectiles;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Partie {
    private Charlotte charlotte;
    private Niveau niveau;
    private BarreDeVie barreDeVie;
    private boolean partieFinie = false;
    private Camera camera;

    /**
     * Constructeur de Partie, crée un personnage Charlotte, le premier niveau, une caméra et initialise
     * la barre de vie
     */
    public Partie() {
        charlotte = new Charlotte(new Image("code/charlotte.png"), 0, Main.HEIGHT / 2);
        niveau = new Niveau();
        barreDeVie = new BarreDeVie(charlotte);
        camera = new Camera(Main.WIDTH, barreDeVie);
    }

    public boolean isPartieFinie() {
        return partieFinie;
    }

    public void setPartieFinie(boolean partieFinie) {
        this.partieFinie = partieFinie;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public Background getBackground() {
        return niveau.getBg();
    }

    /**
     * Gère toutes les updates du jeu ainsi que la logique
     *
     * @param deltaTime Le temps d'exécution entre 2 appels de la méthode handle du AnimationTimer
     */
    public void update(double deltaTime) {
        long nowMS = System.currentTimeMillis();
        charlotte.verifierVitesse();
        charlotte.update(deltaTime);
        charlotte.creerProjectiles(niveau);
        charlotte.verifierLimites(camera.getX());
        camera.suivre(charlotte);
        barreDeVie.update();
        niveau.getBaril().updatePhysique();
        niveau.faireApparaitreEnnemis(camera);
        double tempsTouchee = (double) (nowMS - charlotte.getTempsTouchee()) / 1000;
        if (!niveau.getPoissons().isEmpty()) {
            for (int i = 0; i < niveau.getPoissons().size(); i++) {
                var poisson = niveau.getPoissons().get(i);
                poisson.update(deltaTime);
                for (int j = 0; j < charlotte.getProjectilesTires().size(); j++) {
                    if (poisson.isEnCollision(charlotte.getProjectilesTires().get(j))) {
                        niveau.getPoissons().remove(poisson);
                        if (i != 0) {
                            i--;
                        }
                        break;
                    }
                }
                if (niveau.getPoissons().isEmpty()) {
                    break;
                }
                if (charlotte.isEnCollision(niveau.getPoissons().get(i)) && tempsTouchee > 2
                        && !charlotte.isInvincible()) {
                    charlotte.estTouchee();
                    charlotte.setTempsTouchee(nowMS);
                }
                if (charlotte.isEnCollision(niveau.getBaril()) && !niveau.getBaril().isOuvert()) {
                    niveau.getBaril().setOuvert(true);
                    niveau.getBaril().setImageBaril(new Image("code/baril-ouvert.png"));
                    int choixNouveauProjectile;
                    do {
                        choixNouveauProjectile = Input.rnd.nextInt(3) + 1;
                    } while (choixNouveauProjectile == charlotte.getChoixProjectile());
                    charlotte.setChoixProjectile(choixNouveauProjectile);
                }
            }
        }
        ArrayList<Projectiles> projectilesTires = charlotte.getProjectilesTires();
        for (int i = projectilesTires.size() - 1; i >= 0; i--) {
            var projectile = projectilesTires.get(i);
            if (projectile.estSortiEcran(camera.getX() + camera.getWidth())) {
                projectilesTires.remove(i);
            } else {
                projectile.updatePhysique(deltaTime);
            }
        }
        niveau.isPlusLa(camera.getX());
        if (!charlotte.estVivante()) {
            if (System.currentTimeMillis() - charlotte.getTempsDeces() > 4000) {
                partieFinie = true;
                return;
            }
        }
        niveau.checkFini(charlotte);
        if (niveau.isFini() || (Input.isPressed(KeyCode.D) && Input.isPressed(KeyCode.T))) {
            nextLevel();
        }

    }

    /**
     * Dessine tous les objets du jeu ainsi que les textes
     *
     * @param context Le GraphicsContext du canvas, sert à dessiner
     */
    public void draw(GraphicsContext context) {
        context.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
        camera.appliquer(context);
        niveau.afficherNumNiveau(context);
        charlotte.draw(context);
        barreDeVie.draw(context);
        for (var decor : niveau.getDecors()) {
            if (decor.getX() + decor.getImage().getWidth() > camera.getX() && decor.getX() < camera.getX() +
                    camera.getWidth()) {
                context.drawImage(decor.getImage(), decor.getX(), Decor.getY());
            }
        }
        for (var projectile : charlotte.getProjectilesTires()) {
            projectile.draw(context);
        }


        if (Input.isPressed(KeyCode.D)) {
            context.setFont(Font.font(-1));
            context.setFill(Color.WHITE);
            double distanceTexte = camera.getX() + 10;
            context.fillText("NB poissons: " + niveau.getPoissons().size(), distanceTexte, 50);
            context.fillText("NB projectiles: " + charlotte.getProjectilesTires().size(), distanceTexte,
                    65);
            context.fillText("Position Charlotte: " + charlotte.getX() / Main.TAILLE_NIVEAU * 100 + "%",
                    distanceTexte, 80);
        }
        niveau.getBaril().draw(context);
        for (var p : niveau.getPoissons()) {
            p.draw(context);
        }
        if (!charlotte.estVivante() && System.currentTimeMillis() - charlotte.getTempsDeces() < 4000) {
            context.setFill(Color.RED);
            context.setFont(Font.font("Comic Sans MS", 72));
            context.fillText("Fin de partie", camera.getX() + camera.getWidth() * 0.3, Main.HEIGHT / 2);
        }
        context.setTransform(1, 0, 0, 1, 0, 0);
    }

    /**
     * Passe au niveau suivant, reset les projectiles et la position de charlotte ainsi que de la caméra
     */
    public void nextLevel() {
        charlotte.getProjectilesTires().clear();
        niveau = new Niveau();
        charlotte.setX(0);
        charlotte.setY(Main.HEIGHT / 2);
        camera.resetX();
    }

}
