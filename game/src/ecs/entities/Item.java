package ecs.entities;

import ecs.components.*;
import ecs.items.ItemData;
import ecs.items.ItemType;
import ecs.items.WorldItemBuilder;
import graphic.Animation;
import java.util.List;

public abstract class Item extends Entity {

    private final String pathToTexture;
    private final String name;
    private final String description;

    public Item(String pathToTexture, String name, String description) {
        super();
        this.pathToTexture = pathToTexture;
        this.name = name;
        this.description = description;
        setupItemData();
    }

    /** Set up the item data */
    public void setupItemData() {
        ItemData item =
                new ItemData(
                        ItemType.Active,
                        new Animation(List.of(pathToTexture), 1, true),
                        new Animation(List.of(pathToTexture), 1, true),
                        name,
                        description);
        WorldItemBuilder.buildWorldItem(item);
    }
}
