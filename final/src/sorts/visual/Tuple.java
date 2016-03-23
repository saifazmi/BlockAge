package sorts.visual;

/**
 * @author : Evgeniy Kim; Contributors - Evgeniy Kim,Dominic Walters, Paul Popa
 * @version : 23/03/2016;
 *          <p>
 *          Simple tuple class for use in swap indexes for sorting.
 * @date : 19/02/16
 */
public class Tuple {

    private int first;
    private int second;

    // GETTER methods

    /**
     * Gets the first element of the tuple
     *
     * @return the first element of the tuple
     */
    public int getFirst() {

        return this.first;
    }

    /**
     * Gets the second element of the tuple
     *
     * @return the second element of the tuple
     */
    public int getSecond() {

        return this.second;
    }

    // SETTER methods

    /**
     * Sets the first element of the tuple
     *
     * @param first the value that will be set
     */
    public void setFirst(int first) {

        this.first = first;
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
     * Creates a tuple
     *
     * @param first  the first element of the tuple
     * @param second the second element of the tuple
     */
    public Tuple(int first, int second) {

        this.first = first;
        this.second = second;
    }
}
