package ca.qc.bdeb.sim203.TPCharlotte;

import ca.qc.bdeb.sim203.TPCharlotte.Poissons.Charlotte;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

//TODO: Il n'y a pas 6 niveaux, le jeu continue à 
//l'infini jusqu'à ce qu'on meure (regarder PDF
//au cas où)

public class Partie {
    private Charlotte charlotte;
    private ArrayList<Niveau> niveaux = new ArrayList<>();
    private Niveau currentLevel;
    private HealthBar healthBar;
    private boolean partieFinie = false;

    public Partie() {
        charlotte = new Charlotte(new Image("code/charlotte.png"), 0, Main.HEIGHT / 2);
        for (int i = 0; i < 6; i++) {
            niveaux.add(new Niveau());
        }
        currentLevel = niveaux.get(0);
        healthBar = new HealthBar(charlotte);
    }

    public boolean isPartieFinie() {
        return partieFinie;
    }

    public Niveau getCurrentLevel() {
        return currentLevel;
    }

    public ArrayList<Niveau> getNiveaux() {
        return niveaux;
    }

    public Charlotte getCharlotte() {
        return charlotte;
    }


    public void update(double deltaTime) {
        boolean isNotPaused = !Input.isPressed(KeyCode.D) || !Input.isPressed(KeyCode.P);
        if (isNotPaused) {
            long nowMS = System.currentTimeMillis();

            charlotte.update(deltaTime, currentLevel);
            healthBar.update();
            currentLevel.getBaril().updatePhysique();
            currentLevel.spawnEnnemis();
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
            currentLevel.isPlusLa();
        }
        if (!charlotte.isAlive()) {
            if (System.currentTimeMillis() - charlotte.getDeathTime() > 4000) {
                partieFinie = true;
                return;
            }
        }
        currentLevel.checkFini(charlotte);
        if (currentLevel.isOver() && currentLevel.getNumNiveau() < 6) {
            currentLevel = niveaux.get(currentLevel.getNumNiveau() - 1);
            nextLevel();
        }

    }

    public void draw(GraphicsContext context) {
        boolean isNotPaused = !Input.isPressed(KeyCode.D) || !Input.isPressed(KeyCode.P);
        if (isNotPaused) {
            context.clearRect(0, 0, Main.WIDTH, Main.HEIGHT);
            currentLevel.afficherNumNiveau(context);
            charlotte.draw(context);
            healthBar.draw(context);
            if (Input.isPressed(KeyCode.D)) {
                context.setFont(Font.font(-1));
                context.setFill(Color.WHITE);
                context.fillText("NB poissons: " + currentLevel.getPoissons().size(), 10, 50);
                context.fillText("NB projectiles: " + charlotte.getProjectilesTires().size(), 10, 65);
                context.fillText("Position Charlotte: ", 10, 80);
            }
            currentLevel.getBaril().draw(context);
            for (var p : currentLevel.getPoissons()) {
                p.draw(context);
            }
            if (!charlotte.isAlive() && System.currentTimeMillis() - charlotte.getDeathTime() < 4000) {
                context.setFill(Color.RED);
                context.setFont(Font.font("Comic Sans MS", 72));
                context.fillText("Fin de partie", Main.WIDTH / 4, Main.HEIGHT / 2);
            }
        }
    }

    private void nextLevel() {
        charlotte.getProjectilesTires().clear();
        currentLevel = niveaux.get(currentLevel.getNumNiveau());
        currentLevel.setTempsCreationNiveau(System.currentTimeMillis());
        charlotte.setX(0);
        charlotte.setY(Main.HEIGHT / 2);
    }

}
