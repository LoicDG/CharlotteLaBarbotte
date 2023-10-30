package ca.qc.bdeb.sim203.TPCharlotte;

import ca.qc.bdeb.sim203.TPCharlotte.Poissons.Charlotte;
import ca.qc.bdeb.sim203.TPCharlotte.Poissons.Ennemis;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;
//arraylist des ennemis que charlotte a pas depasse et ajouter un nouveau parametre a charlotte
public class Main extends Application {
    public static double HEIGHT = 520;
    public static double WIDTH = 900;
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Niveau currentLevel;
    ArrayList<Niveau> niveaux = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        stage.setTitle("Charlotte la Barbotte");
        stage.getIcons().add(new Image("code/charlotte.png"));
        stage.setResizable(false);
        Niveau.creerImages();
        Ennemis.creerImageEnnemis();
        var charlotte = new Charlotte(new Image("code/charlotte.png"), WIDTH / 2, HEIGHT / 2);
        var healthBar = new HealthBar(charlotte);
        for (int i = 0; i < 6; i++) {
            niveaux.add(new Niveau());
        }
        currentLevel = niveaux.get(0);

        //region Scène 1, La page titre
        var rootTitre = new VBox();
        rootTitre.setAlignment(Pos.CENTER);
        var titre = new Scene(rootTitre, WIDTH, HEIGHT);
        var logo = new ImageView(new Image("code/logo.png"));
        logo.setPreserveRatio(true);
        logo.setFitWidth(450);
        var jouer = new Button("Jouer!");
        var infos = new Button("Infos");
        var boutons = new HBox(jouer, infos);
        boutons.setAlignment(Pos.CENTER);
        boutons.setSpacing(10);
        rootTitre.getChildren().addAll(logo, boutons);
        rootTitre.setStyle("-fx-background-color: #2A7FFF;");
        rootTitre.setSpacing(10);
        //endregion

        stage.setScene(titre);
        //Scène 2, pour le menu informations
        var rnd = new Random();
        var ennemiImage = new Image("code/poisson" + rnd.nextInt(1, 6) + ".png");
        var sceneInfos = setScreenInfos(titre, stage, ennemiImage);
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = System.nanoTime();
            private long nowMS = System.currentTimeMillis();

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                boolean isNotPaused = !Input.isPressed(KeyCode.D) || !Input.isPressed(KeyCode.P);
                double deltaTime = (now - lastTime) * 1e-9;
                if (isNotPaused) {
                    nowMS = System.currentTimeMillis();
                }
                if (isNotPaused) {
                    context.clearRect(0, 0, WIDTH, HEIGHT);
                    currentLevel.afficherNumNiveau(context);
                    charlotte.update(deltaTime, currentLevel);
                    charlotte.draw(context);
                    healthBar.update();
                    healthBar.draw(context);
                    currentLevel.getBaril().updatePhysique();
                    currentLevel.getBaril().draw(context);
                }
                //Débug mode
                if (Input.isPressed(KeyCode.D)) {
                    context.setFont(Font.font(-1));
                    context.setFill(Color.WHITE);
                    context.fillText("NB poissons: " + currentLevel.getPoissons().size(), 10, 50);
                    context.fillText("NB projectiles: " + charlotte.getProjectilesTires().size(), 10, 65);
                    context.fillText("Position Charlotte: ", 10, 80);
                }
                double tempsPassee = (nowMS - currentLevel.getTempsCreationNiveau()) / 1000;
                if (tempsPassee % (0.75 + 1 / Math.sqrt(currentLevel.getNumNiveau())) <= 0.02 &&
                        (nowMS - currentLevel.getTempsExec()) / 1000 > 0.5) {
                    if (isNotPaused) {
                        currentLevel.spawnEnnemis();
                        currentLevel.setTempsExec(nowMS);
                    }
                }
                double tempsTouchee = (double)
                        (nowMS - charlotte.getTempsTouchee()) / 1000;
                if (!currentLevel.getPoissons().isEmpty()) {
                    for (int i = 0; i < currentLevel.getPoissons().size(); i++) {
                        var poisson = currentLevel.getPoissons().get(i);
                        if (isNotPaused) {
                            poisson.update(deltaTime);
                            poisson.draw(context);
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
                            //TODO: Game logic, déplacer ça dans le modèle ?
                            if (charlotte.isEnCollision(currentLevel.getPoissons().get(i)) && tempsTouchee > 2
                                    && !charlotte.isInvincible()) {
                                charlotte.isTouchee();
                                charlotte.setTempsTouchee(nowMS);
                            }
                            if (charlotte.isEnCollision(currentLevel.getBaril()) && !currentLevel.getBaril().isEstOuvert()){
                                currentLevel.getBaril().setEstOuvert(true);
                                currentLevel.getBaril().setImageBaril(new Image("code/baril-ouvert.png"));
                                if (charlotte.getChoixProjectile() < 3){
                                    charlotte.setChoixProjectile(charlotte.getChoixProjectile()+1);
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
                        retourMenu(titre, stage);
                        return;
                    } else {
                        context.setFill(Color.RED);
                        context.setFont(Font.font("Comic Sans MS", 72));
                        context.fillText("Fin de partie", WIDTH / 4, Main.HEIGHT / 2);
                    }
                }
                lastTime = now;
            }
        };

        //Scène 3, pour jouer
        var sceneJouer = setScreenJouer(titre, stage, currentLevel, timer);

        //region Événementiel
        infos.setOnAction(event -> {
            //change l'image à chaque fois
            var image = (ImageView) sceneInfos.getRoot().getChildrenUnmodifiable().get(1);
            image.setImage(new Image(Ennemis.getUrlImage()));
            stage.setScene(sceneInfos);
        });
        jouer.setOnAction(event -> {
            stage.setScene(sceneJouer);
            charlotte.restart();
            currentLevel.getPoissons().clear();
            charlotte.getProjectilesTires().clear();
            currentLevel = niveaux.get(0);
            timer.start();
        });
        titre.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });

        //endregion
        stage.show();
    }

    private Scene setScreenInfos(Scene originale, Stage stage, Image poisson) {
        var root = new VBox();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var titre = new Text("Charlotte la Barbotte");
        titre.setFont(Font.font(48));
        root.setStyle("-fx-background-color: #2A7FFF;");

        //image poisson
        var imageEnnemi = new ImageView(poisson);
        imageEnnemi.setPreserveRatio(true);
        imageEnnemi.setFitHeight(HEIGHT * 0.4);

        //Collaborateurs
        var collabos = new VBox();
        var loic = new Text("Par Loïc Desrochers-Girard"); //TODO: arranger le style de "Par" et "et" si on veut
        loic.setFont(Font.font(32));
        var sebby = new Text("et Sebastian Crête");
        sebby.setFont(Font.font(32));
        collabos.getChildren().addAll(loic, sebby);
        collabos.setAlignment(Pos.CENTER);

        //Description du travail
        var sources = new Text("""
                Travail remis à Nicolas Hurtubise et Georges Côté. Graphismes adaptés de https://game-icons.net/ et de
                https://openclipart.org/. Dgitéveloppé dans le cadre du cours 420-203-RE - Développement de programmes\s
                dans un environnement graphique, au Collège de Bois-de-Boulogne.""");

        var boutonRetour = new Button("Retour");
        root.getChildren().addAll(titre, imageEnnemi, collabos, sources, boutonRetour);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        //region Événementiel
        boutonRetour.setOnAction(event -> stage.setScene(originale));
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.setScene(originale);
            }
        });
        //endregion
        return scene;
    }

    private Scene setScreenJouer(Scene originale, Stage stage, Niveau niveau, AnimationTimer timer) {
        var root = new Pane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        root.setBackground(niveau.getBg());
        root.getChildren().add(canvas);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                timer.stop();
                retourMenu(originale, stage);
            } else if (event.getCode() == KeyCode.D && Input.isPressed(KeyCode.D)) {
                Input.setKeyPressed(KeyCode.D, false);
            } else if (event.getCode() == KeyCode.P && Input.isPressed(KeyCode.P)) {
                Input.setKeyPressed(KeyCode.P, false);
            } else {
                Input.setKeyPressed(event.getCode(), true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.P) return;
            Input.setKeyPressed(event.getCode(), false);
        });
        return scene;

    }

    private void retourMenu(Scene originale, Stage stage) {
        niveaux.clear();
        Niveau.resetNbNiveau();
        for (int i = 0; i < 6; i++) {
            niveaux.add(new Niveau());
        }
        stage.setScene(originale);
        Input.setKeyPressed(KeyCode.D, false);
        Input.setKeyPressed(KeyCode.P, false);
    }
}
