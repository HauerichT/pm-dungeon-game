package configuration.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import tools.Point;

public class LVup<T extends Actor> extends ScreenController<T> {

    private int counter = 0;
    private ScreenText heroLV;
    /** Creates a new LVup Text with a given Spritebatch */
    public LVup() {
        this(new SpriteBatch());
    }

    /** Creates a new LVup Text with a given Spritebatch */
    public LVup(SpriteBatch batch) {
        super(batch);

        ScreenText newLV =
            new ScreenText(
                "Hero Lv.",
                new Point(0, 0),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.WHITE)
                    .build());
        newLV.setFontScale(1);
        newLV.setPosition(
            5,
            40,
            Align.left | Align.bottom);
        add((T) newLV);
        showMenu(0);
    }


    /** shows the Menu and creates a new Hero level Text */
    public void showMenu(long level) {
        this.heroLV = null;
        this.heroLV =
            new ScreenText(
                ""+level,
                new Point(0, 0),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.WHITE)
                    .build());
        heroLV.setFontScale(1);
        heroLV.setPosition(
            60,
            40,
            Align.left | Align.bottom);
        add((T) heroLV);

        this.forEach((Actor s) -> s.setVisible(true));
    }


    /** deletes the old Hero level Text */
    public void hideMenu() {
        remove((T) this.heroLV);
    }


}
