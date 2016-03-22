package sorts.logic;

import java.util.ArrayList;

/**
 * @author : First created by Evgeniy Kim with code by Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 * Specific object made for storing 'states' of a given sort. For visualisation.
 */

public class SortableComponent {
    public ArrayList<Integer> value; //WHOLE ARRAYLIST at that time
    public int comparing1; //INDEX OF WHATS BEING COMPARED
    public int comparing2;
    public boolean swapped; //SWAP STATE - FLAG FOR ANIMATION

    public ArrayList<Integer> getValue() {
        return value;
    }

    public void setValue(ArrayList<Integer> value) {
        this.value = value;
    }

    public SortableComponent(ArrayList<Integer> value, int comparing1, int comparing2, boolean swapped) {
        this.value = value;
        this.comparing1 = comparing1;
        this.comparing2 = comparing2;
        this.swapped = swapped;
    }

    public boolean isSwapped() {
        return swapped;
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

}
