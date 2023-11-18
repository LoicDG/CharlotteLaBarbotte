package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.canvas.GraphicsContext;

public abstract class ObjetDuJeu {
    protected double x, y, w, h, vx, ax;

    public abstract void update(double deltaTime);

    public abstract void draw(GraphicsContext context);
}
