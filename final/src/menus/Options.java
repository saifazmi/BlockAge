package menus;

import sound.SoundManager;

/**
 * @author : Paul Popa; Contributors -  Paul Popa
 * @version : 23/03/2016;
 *          <p>
 *          Handles the options for the game.
 * @date : 13/03/16
 */
public class Options {

    private boolean showPath;
    private boolean tutorial;
    private static Options instance = null;

    public static Options Instance() {
        if (instance == null) {
            instance = new Options();
        }
        return instance;
    }

    private Options() {

        showPath = true;
        tutorial = false;
    }

    /**
     * Sets the show path option for the game that will start
     *
     * @param showPath - if it shows the path or not
     */
    public void setPath(boolean showPath) {

        this.showPath = showPath;
    }

    /**
     * Turns the sound on or off
     *
     * @param sound - sets the sound on or off
     */
    public void setSound(boolean sound) {

        if (!sound) {
            SoundManager.Instance().pauseSoundtrack();
        } else {
            SoundManager.Instance().startSoundtrack();
        }
    }

    /**
     * Gets if the path will be shown or not
     *
     * @return - the option if the path will be shown or not
     */
    public boolean getShowPath() {

        return this.showPath;
    }

    /**
     * Gets the boolean representing if the tutorial is active
     *
     * @return the boolean value
     */
    public boolean isTutorial() {

        return this.tutorial;
    }

    /**
     * Sets the boolean representing if the tutorial is active
     *
     * @param tutorial the boolean to set to
     */
    public void setTutorial(boolean tutorial) {

        this.tutorial = tutorial;
    }
}
