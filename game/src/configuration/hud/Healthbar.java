package configuration.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import controller.ScreenController;
import ecs.components.HealthComponent;
import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import graphic.Painter;
import starter.Game;
import tools.Constants;
import tools.Point;

public class Healthbar<T extends Actor> extends ScreenController<T> {


    private ScreenImage healthbarback;
    private ScreenImage healthbar;


    private static int heroStartHealth;

    /** Creates a new PauseMenu with a new Spritebatch */
    public Healthbar() {
        this(new SpriteBatch());
    }
    public Healthbar(int health) {
        this(new SpriteBatch());
        this.heroStartHealth = 100/health;
    }

    /** Creates a new PauseMenu with a given Spritebatch */
    public Healthbar(SpriteBatch batch) {
        super(batch);


        if (Game.getHero() != null) {
            // Game Over Screen Text

            HealthComponent healh =
                (HealthComponent)
                    Game.getHero().get().getComponent(HealthComponent.class).orElseThrow();



            healthbarback = new ScreenImage("hud/HealthbarHud/HealthBarBack.png", new Point(2,57));
            healthbarback.setScale(0.54F, 0.4F);
            remove((T) healthbarback);
            add((T) healthbarback);


            healthbar = new ScreenImage("hud/HealthbarHud/Lifepoint.png",  new Point(8,60));
            healthbar.setScale((healh.getCurrentHealthpoints()*(heroStartHealth))/1.79F, 0.45F);
            add((T) healthbar);
            if (healh.getCurrentHealthpoints() <= 0){
                remove((T)healthbar);
            }


            showMenu();


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
