package sorts.visual;

/**
 * @author : Evgeniy Kim
 * @date : 19/02/16, last edited by Evgeniy Kim on 19/02/16
 * Simple tuple class for use in swap indexes for sorting.
 */
public class Tuple {

    private int first;
    private int second;


    /**
     * Creates a tuple
     * 
     * @param first the first element of the tuple
     * @param second the second element of the tuple
     */
    public Tuple(int first, int second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Gets the second element of the tuple
     * 
     * @return the second element of the tuple
     */
    public int getSecond() {
        return second;
    }

    /**
     * Sets the second element of the tuple
     * 
     * @param second the value that will be set
     */
    public void setSecond(int second) {
        this.second = second;
    }

    /**
     * Gets the first element of the tuple
     * 
     * @return the first element of the tuple
     */
    public int getFirst() {
        return first;
    }

    /**
     * Sets the first element of the tuple
     * 
     * @param first the value that will be set
     */
    public void setFirst(int first) {
        this.first = first;
    }
}
