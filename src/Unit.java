import javafx.scene.Node;

import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */
public class Unit extends Entity {

    private static final Logger LOG = Logger.getLogger(Unit.class.getName());

    // @TODO: Place holder
    public Unit(int id, String name, String description, GraphNode position, Node sprite) {
        super(id, name, description, position, sprite);
    }
}
