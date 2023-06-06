package saveandload;

import ecs.entities.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** SerializableDungeon is a class which allows to save and load a game */
public class SerializableDungeon implements Serializable {
    private List<Entity> entities = new ArrayList<>();
    private int level;

    /** Saves the current game */
    public void saveGame() {}

    /** Loads the entities of the last saved game in new game */
    public void loadGame() {}

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
