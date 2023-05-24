package configuration.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import controller.ScreenController;
import ecs.entities.Entity;
import ecs.entities.Hero;
import java.util.Set;
import starter.Game;
import tools.Point;

public class GameOver<T extends Actor> extends ScreenController<T> {

    private BitmapFont BitmapFont = new BitmapFont();
    private ScreenImage gameOverImg;
    private ScreenButton endButtonReady;
    private ScreenButton restartButtonReady;

    /**
     * Creates a GameOver Menü with a given Spritebatch
     */
    public GameOver() {
        this(new SpriteBatch());
    }


    /**
     * Creates a GameOver Menü with a given Spritebatch
     */
    public GameOver(SpriteBatch batch) {
        super(batch);

        // Game Over Screen Text
        gameOverImg = new ScreenImage("hud/gameover.png", new Point(0, -10));
        gameOverImg.setScale(1.25f, 1.25f);
        add((T) gameOverImg);

        // End button
        TextButtonStyleBuilder endButton = new TextButtonStyleBuilder(BitmapFont);
        endButton.setFontColor(Color.BLACK);
        endButton.setOverFontColor(Color.BLUE);
        endButton.setDownFontColor(Color.BROWN);
        endButton.setCheckedImage("hud/btn_restart.png");
        endButton.setUpImage("hud/btn_restart.png");
        endButton.setDownImage("hud/btn_end.png");
        endButtonReady =
                new ScreenButton(
                        "Endgame",
                        new Point(10, 50),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Gdx.app.exit();
                            }
                        },
                        endButton.build());
        endButtonReady.setScale(1F, 1F);
        add((T) endButtonReady);

        // restart button
        TextButtonStyleBuilder restartButton = new TextButtonStyleBuilder(BitmapFont);
        restartButton.setFontColor(Color.BLACK);
        restartButton.setOverFontColor(Color.BLUE);
        restartButton.setDownFontColor(Color.BROWN);
        restartButton.setCheckedImage("hud/btn_end.png");
        restartButton.setUpImage("hud/btn_end.png");
        restartButton.setDownImage("hud/btn_restart.png");
        restartButtonReady =
                new ScreenButton(
                        "Restart",
                        new Point(300, 50),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {

                                Set<Entity> allEntities = Game.getEntities();
                                for (Entity allEntity : allEntities) {
                                    Game.removeEntity(allEntity);
                                }

                                Game.toggleGameOver();
                                Game.lvUP(0);
                                Hero hero = new Hero();
                                Game.setHero(hero);
                            }
                        },
                        restartButton.build());
        restartButtonReady.setScale(1F, 1F);
        add((T) restartButtonReady);

        hideMenu();
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
