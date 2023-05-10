package configuration.hud.NewHuds;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import configuration.hud.TextButtonListener;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.systems.ECS_System;
import level.LevelAPI;
import starter.Game;
import java.util.Iterator;
import java.util.Set;





public class GameRestart extends TextButtonListener {


    @Override
    public void clicked(InputEvent event, float x, float y) {
        //Removes all Entities from the current Level and replaces the old dead Hero with a new one.
        Hero hero = new Hero();
        Set<Entity> allEntities = Game.getEntities();
        Iterator<Entity> entityIterator = allEntities.iterator();
        while (entityIterator.hasNext()) {
            Game.removeEntity(entityIterator.next());
        }
        Game.toggleGameOver();
        Game.setHero(hero);


    }

}






