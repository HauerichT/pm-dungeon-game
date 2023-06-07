package saveandload;

import ecs.entities.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** SerializableDungeonData is a class which saves the data to write to file */
public class SerializableDungeonData implements Serializable {
    private List<Entity> entities = new ArrayList<>();
    private int level;

    /**
     * Sets the current entities in a level
     *
     * @param entities entities to save
     */
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    /**
     * Returns the current saved entities
     *
     * @return current saved entities
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * Sets the current level
     *
     * @param level current level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Returns the current saved level
     *
     * @return current saved level
     */
    public int getLevel() {
        return level;
    }
}
