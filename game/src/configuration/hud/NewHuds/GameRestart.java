package configuration.hud.NewHuds;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import configuration.hud.TextButtonListener;
import starter.Game;

import java.awt.*;


public class GameRestart extends TextButtonListener {


    @Override
    public void clicked(InputEvent event, float x, float y) {
            Game.main(null);




    }



}
