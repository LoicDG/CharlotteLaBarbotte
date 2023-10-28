package ca.qc.bdeb.sim203.TPCharlotte;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Poisson {
    private Image imagePoisson;
    private ImageView poisson = new ImageView();
    protected double vx, vy, ax, ay;
    protected double x, y;

    public Poisson(Image imagePoisson, double x, double y) {
        this.imagePoisson = imagePoisson;
        this.x = x;
        this.y = y;
        poisson.setImage(imagePoisson);
    }

    protected Image getImagePoisson() {
        return imagePoisson;
    }

    public void update(double deltaTime) {
        updatePhysique(deltaTime);
    }

    protected void updatePhysique(double deltaTime) {
        x += deltaTime * vx;
        y += deltaTime * vy;
        vx += deltaTime * ax;
        vy += deltaTime * ay;
    }

    public void draw(GraphicsContext context) { //TODO: doesnt work if touches both at the same time, and also it sticks for like 2 sec for some reason
        context.drawImage(imagePoisson, x, y);
        if (x < 0) {
            context.clearRect(x, y, imagePoisson.getWidth(), imagePoisson.getHeight());
            context.drawImage(imagePoisson, 0, y);
        } else if (x + imagePoisson.getWidth() > Main.WIDTH) {
            context.clearRect(x, y, imagePoisson.getWidth(), imagePoisson.getHeight());
            context.drawImage(imagePoisson, Main.WIDTH - imagePoisson.getWidth(), y);
        }
        if (y < 0) {
            context.clearRect(x, y, imagePoisson.getWidth(), imagePoisson.getHeight());
            context.drawImage(imagePoisson, x, 0);
        } else if (y + +imagePoisson.getHeight() > Main.HEIGHT) {
            context.clearRect(x, y, imagePoisson.getWidth(), imagePoisson.getHeight());
            context.drawImage(imagePoisson, x, Main.HEIGHT - imagePoisson.getHeight());
        }
    }
}
