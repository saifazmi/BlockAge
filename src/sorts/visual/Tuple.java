package sorts.visual;

/**
 * @author : Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 * Simple tuple class for use in swap indexes for sorting.
 */
public class Tuple {

    private int first;
    private int second;


    public Tuple(int first, int second) {
        this.first = first;
        this.second = second;

    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }
}
