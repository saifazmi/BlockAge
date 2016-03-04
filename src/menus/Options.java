package menus;

public class Options {
	
	private boolean showDescription = false;
	private boolean showPath = false;
	private boolean initialBlockades = false;

	public Options() {}
	
	/**
	 * Sets the show Description option for the game that will start
	 * @param showDescription - if it shows the description or not
	 */
	public void setDescription(boolean showDescription) {
		this.showDescription = showDescription;
	}
	
	/**
	 * Sets the show Description option for the game that will start
	 * @param showDescription - if it shows the description or not
	 */
	public void setPath(boolean showPath) {
		this.showPath = showPath;
	}
	
	/**
	 * Sets the initial blockades option for the game that will start
	 * @param showDescription - if it shows the description or not
	 */
	public void setInitialBlockades(boolean initialBlockades) {
		this.initialBlockades = initialBlockades;
	}
	
	
	/**
	 * Gets if the description of a unit will be shown or not
	 * @return - the option if the the description of a unit will be shown or not
	 */
	public boolean getShowDescription() {
		return showDescription;
	}
	
	/**
	 * Gets if the path will be shown or not
	 * @return - the option if the path will be shown or not
	 */
	public boolean getShowPath() {
		return showPath;
	}
	
	/**
	 * Gets if the initial blockades will be shown or not
	 * @return - the option if the path will be shown or not
	 */
	public boolean getInitialBlockades() {
		return initialBlockades;
	}

}
