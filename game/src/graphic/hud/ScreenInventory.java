package graphic.hud;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import controller.ScreenController;
import ecs.components.HealthComponent;
import ecs.components.InventoryComponent;
import ecs.components.PositionComponent;
import ecs.items.ItemData;
import starter.Game;
import tools.Point;

public class ScreenInventory<T extends Actor> extends ScreenController<T> {

    private List<ItemData> inventoryList;

    /** Creates a new Inventory with a new Spritebatch */
    public ScreenInventory() {
        this(new SpriteBatch());
    }

    /** Creates a new Inventory with a given Spritebatch */
    public ScreenInventory(SpriteBatch batch) {
        super(batch);
        setCurrentInventoryList();
        System.out.println(this.inventoryList);
        for (int i = 0; i < 410; i+=41) {
            ScreenImage inv = new ScreenImage("hud/inv.png", new Point(i, 0));
            inv.scaleBy(-0.7f, -0.7f);
            add((T) inv);
        }
    }

    /** Copy current inventory of hero */
    public void setCurrentInventoryList() {
        Game.getHero()
            .ifPresent(
                hero -> {
                    hero.getComponent(InventoryComponent.class)
                        .ifPresent(
                            inv -> {
                                this.inventoryList = (List<ItemData>) ((InventoryComponent) inv).getItems();
                            }
                        );
                }
            );
    }

    /** shows the Menu */
    public void showMenu() {
        this.forEach((Actor s) -> s.setVisible(true));
    }

    /** hides the Menu */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }
}
