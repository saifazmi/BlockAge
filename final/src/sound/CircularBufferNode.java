package sound;

/**
 * Created by hung on 01/03/16.
 */
public class CircularBufferNode<M> {

    private M value;
    private CircularBufferNode<M> next;

    //@TODO: every method needs doc

    /**
     * @param data
     */
    public CircularBufferNode(M data) {

        this.value = data;
    }

    //GETTER methods

    /**
     * @return
     */
    public M getValue() {
        return this.value;
    }

    /**
     * @return
     */
    public CircularBufferNode<M> getNext() {

        return this.next;
    }

    // SETTER methods

    /**
     * @param value
     */
    public void setValue(M value) {

        this.value = value;
    }

    /**
     * @param next
     */
    public void setNext(CircularBufferNode<M> next) {

        this.next = next;
    }
}
