package menus;

import javafx.scene.Scene;

/**
 * Interface for the menus in which we put the width and height for all the menus
 *
 * @author paulp
 */
public interface Menu {
    int WIDTH = 1280;
    int HEIGHT = 720;
    Scene getScene();
}
