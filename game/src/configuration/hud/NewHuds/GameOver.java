package configuration.hud.NewHuds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import configuration.hud.ScreenButton;
import configuration.hud.ScreenImage;
import configuration.hud.TextButtonStyleBuilder;
import controller.ScreenController;
import tools.Point;

public class GameOver<T extends Actor> extends ScreenController<T>  {

    ScreenImage gameend = new ScreenImage("gameover/pngegg.png",new Point(0,-10));
    private BitmapFont BitmapFont = new BitmapFont();

    public GameOver() {
        this(new SpriteBatch());
    }

    public GameOver(SpriteBatch batch) {
        super(batch);
        gameend.setScale(1.25F,1.25F);
        add((T) gameend);

        //Button to end the game
        GameEnd enden = new GameEnd();
        TextButtonStyleBuilder endButton = new TextButtonStyleBuilder(BitmapFont);
        endButton.setFontColor(Color.BLACK);
        endButton.setOverFontColor(Color.BLUE);
        endButton.setDownFontColor(Color.BROWN);
        endButton.setCheckedImage("gameover/Button restart.png");
        endButton.setUpImage("gameover/Button restart.png");
        endButton.setDownImage("gameover/Button ende.png");
        ScreenButton ende = new ScreenButton("Endgame",new Point(10,50),enden,endButton.build());
        ende.setScale(1.2555F,1.25F);
        add((T) ende);

        //Button to restart the game
        GameRestart restart = new GameRestart();
        TextButtonStyleBuilder restartButton = new TextButtonStyleBuilder(BitmapFont);
        restartButton.setFontColor(Color.BLACK);
        restartButton.setOverFontColor(Color.BLUE);
        restartButton.setDownFontColor(Color.BROWN);
        restartButton.setCheckedImage("gameover/Button ende.png");
        restartButton.setUpImage("gameover/Button ende.png");
        restartButton.setDownImage("gameover/Button restart.png");
        ScreenButton restartbuttonready = new ScreenButton("Restart",new Point(300,50),restart,restartButton.build());
        restartbuttonready.setScale(1.25F,1.25F);
        add((T) restartbuttonready);




    }
    public void showMenu() {
        this.forEach((Actor s) -> s.setVisible(true));
    }

    /** hides the Menu */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }
}
