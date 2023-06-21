package ecs.items;

import graphic.Animation;
import java.util.List;
import java.util.Random;

/** Generator which creates a random ItemData based on the Templates prepared. */
public class ItemDataGenerator {
    private static final List<String> missingTexture = List.of("animation/missingTexture.png");

    private List<ItemData> templates =
            List.of(
                    new ItemData(
                            ItemType.Basic,
                            new Animation(List.of("character/bag/bag.png"), 1),
                            new Animation(List.of("character/bag/bag.png"), 1),
                            "Bag",
                        "Erweitert das Inventar"),
                    new ItemData(
                            ItemType.Basic,
                            new Animation(List.of("character/healpotion/healpotion.png"), 1),
                            new Animation(List.of("character/healpotion/healpotion.png"), 1),
                            "Heal Potion",
                        "Heilt den Helden"),
                    new ItemData(
                            ItemType.Basic,
                            new Animation(List.of("character/bag/bag.png"), 1),
                            new Animation(List.of("character/bag/bag.png"), 1),
                            "Sword",
                        "Starkes Schwert"),
                    new ItemData(
                            ItemType.Basic,
                            new Animation(List.of("character/strengthpotion/strengthpotion.png"), 1),
                            new Animation(List.of("character/strengthpotion/strengthpotion.png"), 1),
                            "Strength Potion",
                        "Erhöht die Stärke des Helden")
    );

    private Random rand = new Random();

    /**
     * @return a new randomItemData
     */
    public ItemData generateItemData() {
        return templates.get(rand.nextInt(templates.size()));
    }
}
