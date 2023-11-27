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
    private Niveau currentLevel;
    private HealthBar healthBar;
    private boolean partieFinie = false;
    private Camera camera;

    public Partie() {
        charlotte = new Charlotte(new Image("code/charlotte.png"), 0, Main.HEIGHT / 2);
        currentLevel = new Niveau();
        healthBar = new HealthBar(charlotte);
        camera = new Camera(Main.WIDTH, healthBar);
    }

    public boolean isPartieFinie() {
        return partieFinie;
    }

    public void setPartieFinie(boolean partieFinie) {
        this.partieFinie = partieFinie;
    }

    public Niveau getCurrentLevel() {
        return currentLevel;
    }

    public Background getBackground() {
        return currentLevel.getBg();
    }

    public void update(double deltaTime) {
        boolean isNotPaused = !Input.isPressed(KeyCode.D) || !Input.isPressed(KeyCode.P);
        if (isNotPaused) {
            long nowMS = System.currentTimeMillis();
            charlotte.checkVelocity();
            charlotte.update(deltaTime);
            charlotte.addProjectiles(currentLevel);
            charlotte.checkLimits(camera.getX());
            camera.follow(charlotte);
            healthBar.update();
            currentLevel.getBaril().updatePhysique();
            currentLevel.spawnEnnemis(camera);
            double tempsTouchee = (double) (nowMS - charlotte.getTempsTouchee()) / 1000;
            if (!currentLevel.getPoissons().isEmpty()) {
                for (int i = 0; i < currentLevel.getPoissons().size(); i++) {
                    var poisson = currentLevel.getPoissons().get(i);
                    poisson.update(deltaTime);
                    for (int j = 0; j < charlotte.getProjectilesTires().size(); j++) {
                        if (poisson.isEnCollision(charlotte.getProjectilesTires().get(j))) {
                            currentLevel.getPoissons().remove(poisson);
                            if (i != 0) {
                                i--;
                            }
                            break;
                        }
                    }
                    if (currentLevel.getPoissons().isEmpty()) {
                        break;
                    }
                    if (charlotte.isEnCollision(currentLevel.getPoissons().get(i)) && tempsTouchee > 2
                            && !charlotte.isInvincible()) {
                        charlotte.isTouchee();
                        charlotte.setTempsTouchee(nowMS);
                    }
                    if (charlotte.isEnCollision(currentLevel.getBaril()) && !currentLevel.getBaril().isEstOuvert()) {
                        currentLevel.getBaril().setEstOuvert(true);
                        currentLevel.getBaril().setImageBaril(new Image("code/baril-ouvert.png"));
                        if (charlotte.getChoixProjectile() < 3) {
                            charlotte.setChoixProjectile(charlotte.getChoixProjectile() + 1);
                        } else {
                            charlotte.setChoixProjectile(1);
                        }
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
            currentLevel.isPlusLa(camera.getX());
        }
        if (!charlotte.isAlive()) {
            if (System.currentTimeMillis() - charlotte.getDeathTime() > 4000) {
                partieFinie = true;
                return;
            }
        }
        currentLevel.checkFini(charlotte);
        if (currentLevel.isOver() || (Input.isPressed(KeyCode.D) && Input.isPressed(KeyCode.T))) {
            nextLevel();
        }

    }

    public void draw(GraphicsContext context) {
        boolean isNotPaused = !Input.isPressed(KeyCode.D) || !Input.isPressed(KeyCode.P);
        if (isNotPaused) {
            context.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
            camera.apply(context);
            currentLevel.afficherNumNiveau(context);
            charlotte.draw(context);
            healthBar.draw(context);
            for (var decor : currentLevel.getDecors()) {
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
                context.fillText("NB poissons: " + currentLevel.getPoissons().size(), distanceTexte, 50);
                context.fillText("NB projectiles: " + charlotte.getProjectilesTires().size(), distanceTexte,
                        65);
                context.fillText("Position Charlotte: " + charlotte.getX() / Main.TAILLE_NIVEAU * 100 + "%",
                        distanceTexte, 80);
            }
            currentLevel.getBaril().draw(context);
            for (var p : currentLevel.getPoissons()) {
                p.draw(context);
            }
            if (!charlotte.isAlive() && System.currentTimeMillis() - charlotte.getDeathTime() < 4000) {
                context.setFill(Color.RED);
                context.setFont(Font.font("Comic Sans MS", 72));
                context.fillText("Fin de partie", camera.getX() + camera.getWidth() * 0.3, Main.HEIGHT / 2);
            }
            context.setTransform(1, 0, 0, 1, 0, 0);
        }
    }

    private void nextLevel() {
        charlotte.getProjectilesTires().clear();
        currentLevel = new Niveau();
        currentLevel.setTempsCreationNiveau(System.currentTimeMillis());
        charlotte.setX(0);
        charlotte.setY(Main.HEIGHT / 2);
        camera.resetX();
    }

}
