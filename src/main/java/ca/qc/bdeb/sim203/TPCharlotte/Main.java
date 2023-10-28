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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {
    public static double HEIGHT = 590;
    public static double WIDTH = 900;
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    GraphicsContext context = canvas.getGraphicsContext2D();

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
        var niveau = new Niveau();

        //region Scène 1, La page titre
        var rootTitre = new VBox();
        rootTitre.setAlignment(Pos.CENTER);
        var titre = new Scene(rootTitre, WIDTH, HEIGHT);
        var logo = new ImageView(new Image("code/logo.png"));
        logo.setPreserveRatio(true);
        logo.setFitWidth(500);
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

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double deltaTime = (now - lastTime) * 1e-9;
                context.clearRect(0, 0, WIDTH, HEIGHT);
                charlotte.update(deltaTime);
                charlotte.draw(context);
                healthBar.update();
                healthBar.draw(context);
                double tempsPassee = (System.currentTimeMillis() - niveau.getTempsCreationNiveau()) / 1000;
                if (tempsPassee % (0.75 + 1 * Math.sqrt(niveau.getNumNiveau())) <= 0.02 &&
                        (System.currentTimeMillis() - niveau.getTempsExec()) / 1000 > 0.5) {
                    niveau.spawnEnnemis();
                }
                if (!niveau.getPoissons().isEmpty()) {
                    for (int i = 0; i < niveau.getPoissons().size(); i++) {
                        niveau.getPoissons().get(i).update(deltaTime);
                        niveau.getPoissons().get(i).draw(context);
                    }
                    niveau.isPlusLa();
                }
                lastTime = now;
            }
        };

        //Scène 3, pour jouer
        var sceneJouer = setScreenJouer(titre, stage, niveau, timer);

        //region Événementiel
        infos.setOnAction(event -> {
            //change l'image à chaque fois
            var image = (ImageView) sceneInfos.getRoot().getChildrenUnmodifiable().get(1);
            image.setImage(new Image(Ennemis.getUrlImage()));
            stage.setScene(sceneInfos);
        });
        jouer.setOnAction(event -> {
            stage.setScene(sceneJouer);
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
                stage.setScene(originale);
            } else if (event.getCode() == KeyCode.D && Input.isPressed(KeyCode.D)) {
                Input.setKeyPressed(KeyCode.D, false);
            } else {
                Input.setKeyPressed(event.getCode(), true);
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.D) return;
            Input.setKeyPressed(event.getCode(), false);
        });
        return scene;

    }
}
