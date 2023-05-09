package configuration.hud.NewHuds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import configuration.hud.TextButtonListener;


public class GameEnd extends TextButtonListener {


    @Override
    public void clicked(InputEvent event, float x, float y) {
        Gdx.app.exit();
    }



}
