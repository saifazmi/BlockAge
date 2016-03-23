package sound;

/**
 * Created by hung on 01/03/16.
 */
public class CircularBufferNode<M> {

    private M value;
    private CircularBufferNode<M> next;

    /**
     * A node in the circular buffer
     *
     * @param data the data for the node
     */
    public CircularBufferNode(M data) {

        this.value = data;
    }

    //GETTER methods

    /**
     * Get the data in the node
     *
     * @return the data
     */
    public M getValue() {
        return this.value;
    }

    /**
     * Gets the next node in the circular buffer
     *
     * @return the next node
     */
    public CircularBufferNode<M> getNext() {

        return this.next;
    }

    // SETTER methods

    /**
     * Sets the value of the node
     *
     * @param value the new value
     */
    public void setValue(M value) {

        this.value = value;
    }

    /**
     * Sets the next node of the circular buffer
     *
     * @param next the next node
     */
    public void setNext(CircularBufferNode<M> next) {

        this.next = next;
    }
}
