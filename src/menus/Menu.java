package menus;

import javafx.scene.Scene;

/**
 * Interface for the menus in which we put the width and height for all the menus
 *
 * @author paulp
 */
public interface Menu {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    public Scene getScene();
}
