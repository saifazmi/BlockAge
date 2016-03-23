package sorts.logic;

import java.util.ArrayList;

/**
 * @author : Evgeniy Kim; Contributors - Evgeniy Kim
 * @version : 23/03/2016;
 *          <p>
 *          Specific object made for storing 'states' of a given sort. For visualisation.
 *
 * @date : 19/02/16
 */
public class SortableComponent {

    //WHOLE ARRAYLIST at that time
    public ArrayList<Integer> value;
    //INDEX OF WHATS BEING COMPARED
    public int comparing1;
    public int comparing2;
    //SWAP STATE - FLAG FOR ANIMATION
    public boolean swapped;

    // GETTER method

    /**
     * get the value of the component
     *
     * @return the value
     */
    public ArrayList<Integer> getValue() {

        return this.value;
    }

    // SETTER method

    /**
     * set the value of the value of the component
     *
     * @param value the new value
     */
    public void setValue(ArrayList<Integer> value) {

        this.value = value;
    }

    /**
     * Constructor for Sortable Components
     *
     * @param value      the value of the component
     * @param comparing1 the flag representing the first thing to swap
     * @param comparing2 the flag representing the second thing to swap
     * @param swapped    the boolean representing whether it has been swapped or not
     */
    public SortableComponent(ArrayList<Integer> value, int comparing1, int comparing2, boolean swapped) {

        this.value = value;
        this.comparing1 = comparing1;
        this.comparing2 = comparing2;
        this.swapped = swapped;
    }
}
