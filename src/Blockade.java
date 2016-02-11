import javafx.scene.Node;

import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */
public class Blockade extends Entity {

    private static final Logger LOG = Logger.getLogger(Blockade.class.getName());

    // @TODO: Place holder
    public Blockade(int id, String name, String description, GraphNode position, Node sprite) {
        super(id, name, description, position, sprite);
    }

    @Override
    public void update(long deltaTime) {

    }
}
