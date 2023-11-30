package ca.qc.bdeb.sim203.TPCharlotte.GameLogic;
/**
 * Enregistre les inputs de l'utilisateur, soit les touches de claviers qui sont appuyées
 */

import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Random;

/**
 *
 */
public class Input {
    private static HashMap<KeyCode, Boolean> touches = new HashMap<>();
    public static final Random rnd = new Random();

    public static boolean isPressed(KeyCode code) {
        return touches.getOrDefault(code, false);
    }

    public static void setKeyPressed(KeyCode code, boolean isPressed) {
        touches.put(code, isPressed);
    }
}
