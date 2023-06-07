package ecs.entities;

import ecs.components.Component;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Logger;
import semanticAnalysis.types.DSLContextPush;
import semanticAnalysis.types.DSLType;
import starter.Game;

/** Entity is a unique identifier for an object in the game world */
@DSLType(name = "game_object")
@DSLContextPush(name = "entity")
public class Entity implements Serializable {
    private static int nextId = 0;
    public final int id = nextId++;
    private transient HashMap<Class, Component> components;
    private transient final Logger entityLogger;
    private transient boolean isBoomerang = false;

    public Entity() {
        components = new HashMap<>();
        Game.addEntity(this);
        entityLogger = Logger.getLogger(this.getClass().getName());
        entityLogger.info("The entity '" + this.getClass().getSimpleName() + "' was created.");
    }

    /**
     * Add a new component to this entity
     *
     * @param component The component
     */
    public void addComponent(Component component) {
        components.put(component.getClass(), component);
    }

    /**
     * Remove a component from this entity
     *
     * @param klass Class of the component
     */
    public void removeComponent(Class klass) {
        components.remove(klass);
    }

    /**
     * Get the component
     *
     * @param klass Class of the component
     * @return Optional that can contain the requested component
     */
    public Optional<Component> getComponent(Class klass) {
        return Optional.ofNullable(components.get(klass));
    }

    /**
     * Set if entity is a boomerang
     *
     * @param isBoomerang Class of the component
     */
    public void setIsBoomerang(boolean isBoomerang) {
        this.isBoomerang = isBoomerang;
    }

    /**
     * Get if entity is a boomerang
     *
     * @return if entity is a boomerang
     */
    public boolean getIsBoomerang() {
        return this.isBoomerang;
    }
}
