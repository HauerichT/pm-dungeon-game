package configuration.hud.inventoryHud;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import configuration.hud.ScreenImage;
import controller.ScreenController;
import tools.Point;

public class ScreenInventory<T extends Actor> extends ScreenController<T> {

    /** Creates a new PauseMenu with a new Spritebatch */
    public ScreenInventory() {
        this(new SpriteBatch());
    }

    /** Creates a new PauseMenu with a given Spritebatch */
    public ScreenInventory(SpriteBatch batch) {
        super(batch);
        for (int i = 0; i < 410; i+=41) {
            ScreenImage inv = new ScreenImage("inventory/InvHud.png", new Point(i, 0));
            inv.scaleBy(-0.7f, -0.7f);
            add((T) inv);

        }

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
