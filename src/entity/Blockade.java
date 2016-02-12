package entity;
import java.util.logging.Logger;

import graph.GraphNode;
import sceneElements.SpriteImage;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */
public class Blockade extends Entity {

    private static final Logger LOG = Logger.getLogger(Blockade.class.getName());

	private boolean breakable;

	public Blockade(int id, String name, GraphNode position, SpriteImage sprite)
	{
		super(id, name, position, sprite);
		setBreakable(false);
	}

    public Blockade(int id, String name, String description, GraphNode position, SpriteImage sprite)
    {
        super(id, name, description, position, sprite);
		setBreakable(false);
    }

	public boolean isBreakable()
	{
		return breakable;
	}

	public void setBreakable(boolean breakable)
	{
		this.breakable = breakable;
	}
}
