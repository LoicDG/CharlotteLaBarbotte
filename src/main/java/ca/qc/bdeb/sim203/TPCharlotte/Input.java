package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.input.KeyCode;

import java.util.HashMap;

public class Input {
    private static HashMap<KeyCode, Boolean> touches = new HashMap<>();

    public static boolean isPressed(KeyCode code, boolean isPressed) {
        return touches.getOrDefault(code, false);
    }

    public static void setKeyPressed(KeyCode code, boolean isPressed) {
        touches.put(code, isPressed);
    }
}
