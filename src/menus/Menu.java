package menus;

import javafx.scene.Scene;

/**
 * Interface for the all menus in which we specify the width and height for all the menus.
 *
 * @author : First created by Paul Popa with code by Paul Popa
 * @date : 09/02/16, last edited by Paul Popa on 09/02/16
 */
public interface Menu {
    int WIDTH = 1280;
    int HEIGHT = 720;

    Scene getScene();
}
