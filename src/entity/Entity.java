package entity;

import java.util.Observable;
import java.util.logging.Logger;

/**
 * @author : saif
 * @project : bestRTS
 * @date : 28/01/16
 */
public abstract class Entity extends Observable {

    private static final Logger LOG = Logger.getLogger(Entity.class.getName());

    private final int id;
    private String name;
    private String description;
    private GraphNode position;


    protected double currentPixelX;
    protected double currentPixelY;

    protected SpriteImage sprite;


    public Entity(int id, String name, String description, GraphNode position, SpriteImage sprite) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.position = position;
        this.sprite = sprite;
    }

    public Entity(int id, String name, GraphNode position, SpriteImage sprite) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.description = null;
        this.sprite = sprite;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public GraphNode getPosition() {
        return position;
    }

    public SpriteImage getSprite() {
        return sprite;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSprite(SpriteImage sprite) {
        this.sprite = sprite;
    }

    public double getPixelX()
    {
        return currentPixelX;
    }

    public double getPixelY()
    {
        return currentPixelY;
    }

    public boolean idEquals(Entity e) {
        return id == e.id;
    }

    public abstract void update();

    @Override
    public int hashCode() {
        return id;
    }

	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Entity entity = (Entity) o;

		if(id != entity.id) return false;
		if(!name.equals(entity.name)) return false;
		if(!description.equals(entity.description)) return false;
		return position.equals(entity.position);

	}

    public void setPosition(GraphNode position) {
        this.position = position;
    }
}
