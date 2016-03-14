package menus;

import sound.SoundManager;

public class Options {

    private boolean showDescription = true;
    private boolean showPath = true;
    private boolean initialBlockades = true;
    private boolean tutorial = false;

    public Options() {
    }

    /**
     * Sets the show Description option for the game that will start
     *
     * @param showDescription - if it shows the description or not
     */
    public void setDescription(boolean showDescription) {
        this.showDescription = showDescription;
    }

    /**
     * Sets the show Description option for the game that will start
     *
     * @param showDescription - if it shows the description or not
     */
    public void setPath(boolean showPath) {
        this.showPath = showPath;
    }

    /**
     * Sets the initial blockades option for the game that will start
     *
     * @param showDescription - if it shows the description or not
     */
    public void setInitialBlockades(boolean initialBlockades) {
        this.initialBlockades = initialBlockades;
    }

    /**
     * Turns the sound on or off
     *
     * @param sound - sets the sound on or off
     */
    public void setSound(boolean sound) {
        if (sound == false) {
            SoundManager.Instance().pauseSoundtrack();
        } else {
            SoundManager.Instance().startSoundtrack();
        }
    }

    /**
     * Gets if the description of a unit will be shown or not
     *
     * @return - the option if the the description of a unit will be shown or not
     */
    public boolean getShowDescription() {
        return showDescription;
    }

    /**
     * Gets if the path will be shown or not
     *
     * @return - the option if the path will be shown or not
     */
    public boolean getShowPath() {
        return showPath;
    }

    /**
     * Gets if the initial blockades will be shown or not
     *
     * @return - the option if the path will be shown or not
     */
    public boolean getInitialBlockades() {
        return initialBlockades;
    }

    public boolean isTutorial() {
        return tutorial;
    }

    public void setTutorial(boolean tutorial) {
        this.tutorial = tutorial;
    }
}
