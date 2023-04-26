package ecs.entities;

import ecs.components.InventoryComponent;
import ecs.items.ItemData;

public class Inventory extends Entity {

    private static InventoryComponent inventoryComponent;
    public Inventory() {
        super();
        inventoryComponent = new InventoryComponent(this, 5);
    }

    public static void addItemToInventory(ItemData itemData) {
        inventoryComponent.addItem(itemData);
    }


}
