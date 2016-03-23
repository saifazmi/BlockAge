package menus;

import gui.CoreGUI;
import javafx.scene.Scene;

/**
 * @author : Paul Popa; Contributors - Paul Popa
 * @version : 23/03/2016;
 *          <p>
 *          Interface for the all menus in which we specify the width and height for all the menus.
 *
 * @date : 09/02/16
 */
public interface Menu {

    Integer WIDTH = CoreGUI.WIDTH;
    Integer HEIGHT = CoreGUI.HEIGHT;

    Scene getScene();
}
