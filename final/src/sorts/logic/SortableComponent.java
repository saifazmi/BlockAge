package sorts.logic;

import java.util.ArrayList;

/**
 * @author : First created by Evgeniy Kim with code by Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 * Specific object made for storing 'states' of a given sort. For visualisation.
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

    //@TODO: complete doc

    /**
     * @return
     */
    public ArrayList<Integer> getValue() {

        return this.value;
    }

    // SETTER method

    //@TODO: complete doc

    /**
     * @param value
     */
    public void setValue(ArrayList<Integer> value) {

        this.value = value;
    }

    //@TODO: complete doc

    /**
     * @param value
     * @param comparing1
     * @param comparing2
     * @param swapped
     */
    public SortableComponent(ArrayList<Integer> value, int comparing1, int comparing2, boolean swapped) {

        this.value = value;
        this.comparing1 = comparing1;
        this.comparing2 = comparing2;
        this.swapped = swapped;
    }

    //@TODO: never used, DELETE? =========================
    public boolean isSwapped() {

        return this.swapped;
    }

    public void setSwapped(boolean swapped) {

        this.swapped = swapped;
    }

    public int getComparing1() {

        return comparing1;
    }

    public void setComparing1(int comparing1) {

        this.comparing1 = comparing1;
    }

    public int getComparing2() {

        return comparing2;
    }

    public void setComparing2(int comparing2) {

        this.comparing2 = comparing2;
    }

    //@TODO: ==============================================
}
