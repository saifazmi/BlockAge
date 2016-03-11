package menus;

import gui.CoreGUI;
import javafx.scene.Scene;

/**
 * Interface for the all menus in which we specify the width and height for all the menus.
 *
 * @author : First created by Paul Popa with code by Paul Popa
 * @date : 09/02/16, last edited by Paul Popa on 09/02/16
 */
public interface Menu {
    Integer WIDTH = CoreGUI.WIDTH;
    Integer HEIGHT = CoreGUI.HEIGHT;

    Scene getScene();
}
