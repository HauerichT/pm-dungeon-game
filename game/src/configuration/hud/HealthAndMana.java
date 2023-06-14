package configuration.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import controller.ScreenController;
import ecs.components.HealthComponent;
import starter.Game;
import tools.Point;

public class HealthAndMana<T extends Actor> extends ScreenController<T> {


    private ScreenImage healthbarback;
    private ScreenImage healthbar;

    private ScreenImage manaBarback;
    private ScreenImage manabar;


    private static int heroStartHealth;
    private static float heroStartMana;
    private static float heroMana;

    /** Creates a new PauseMenu with a new Spritebatch */
    public HealthAndMana() {
        this(new SpriteBatch());
    }
    public HealthAndMana(int health, float mana) {
        this(new SpriteBatch());
        this.heroStartHealth = 100/health;
        this.heroStartMana = 100/mana;
    }
    public HealthAndMana(float mana) {
        this(new SpriteBatch());
        this.heroMana = mana;
    }

    /** Creates a new PauseMenu with a given Spritebatch */
    public HealthAndMana(SpriteBatch batch) {
        super(batch);

        //Healthbar
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

            manaBarback = new ScreenImage("hud/ManaBarHud/ManaBarBack.png", new Point(2, 72));
            manaBarback.setScale(0.54F, 0.4F);
            remove((T) manaBarback);
            add((T) manaBarback);


            manabar = new ScreenImage("hud/ManaBarHud/Manapoints.png", new Point(7, 75));
            manabar.setScale((heroStartMana*heroMana) /1.96F, 0.45F);
            add((T) manabar);
            if (healh.getCurrentHealthpoints() <= 0) {
                remove((T) manabar);
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
