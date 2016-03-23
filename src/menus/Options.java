package menus;

import sound.SoundManager;

public class Options {

    private boolean showPath;
    private boolean tutorial;

    public Options() {

        showPath = true;
        tutorial = true;
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
     * Sets the initial blockades option for the game that will start
     *
     * @param initialBlockades - if it shows the blockades or not or not
     */
    public void setInitialBlockades(boolean initialBlockades) {

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
