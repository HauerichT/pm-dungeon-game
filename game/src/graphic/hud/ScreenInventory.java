package graphic.hud;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import controller.ScreenController;
import ecs.components.HealthComponent;
import ecs.components.InventoryComponent;

import ecs.components.ItemComponent;
import ecs.components.skill.SkillTools;
import ecs.entities.Entity;
import ecs.entities.Item;
import ecs.items.IOnCollect;
import starter.Game;
import tools.Point;

import java.util.ArrayList;
import java.util.Arrays;

public class ScreenInventory<T extends Actor> extends ScreenController<T>{

    private ArrayList<ScreenImage> inventoryEmptyList;
    private ArrayList<ScreenImage> inventoryItemList;

    private int xPosInvImg = 4;
    private int xPosInvItemImg = 4;


    /** Creates a new Inventory with a new Spritebatch */
    public ScreenInventory() {
        this(new SpriteBatch());
        this.inventoryEmptyList = new ArrayList<>();
        this.inventoryItemList = new ArrayList<>();
        setInventoryList();
    }

    /** Creates a new Inventory with a given Spritebatch */
    public ScreenInventory(SpriteBatch batch) {
        super(batch);
    }

    public void setInventoryList() {
        Game.getHero().flatMap(hero -> hero.getComponent(InventoryComponent.class)).ifPresent(inv -> {
            for (int i = 0; i < ((InventoryComponent) inv).getMaxSize(); i++) {
                ScreenImage invImg = new ScreenImage("hud/inv.png", new Point(this.xPosInvImg, 4));
                invImg.scaleBy(-0.8f, -0.8f);
                add((T) invImg);
                this.xPosInvImg += (invImg.getWidth()+8);
            }
        });
    }

    /** Copy current inventory of hero */
    public void updateScreenInventory(Entity worldItemEntity, Entity whoTriesCollects) {
        worldItemEntity.getComponent(ItemComponent.class)
            .ifPresent(
                ic -> {
                    ScreenImage invItemImg = new ScreenImage(((ItemComponent) ic).getItemData().getInventoryTexture().getNextAnimationTexturePath(), new Point(this.xPosInvItemImg, 4));
                    invItemImg.scaleBy(-0.8f, -0.8f);
                    add((T) invItemImg);
                    this.xPosInvItemImg += (invItemImg.getWidth()+8);
                });
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
