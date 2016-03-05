package sound;

/**
 * Created by hung on 01/03/16.
 */
public class CircularBufferNode<M> {

    private M value;
    private CircularBufferNode<M> next;

    public CircularBufferNode(M data) {
        this.value = data;
    }

    public M getValue() {
        return value;
    }

    public void setValue(M value) {
        this.value = value;
    }

    public CircularBufferNode<M> getNext() {
        return next;
    }

    public void setNext(CircularBufferNode<M> next) {
        this.next = next;
    }
}
