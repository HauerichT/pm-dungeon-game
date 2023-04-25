package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import level.elements.tile.Tile;

public class FollowHeroWalk implements IIdleAI {
    private GraphPath<Tile> path;

    /**
     * Finds path to the Hero and follows him.
     */
    @Override
    public void idle(Entity entity) {
        if (path == null || AITools.pathFinishedOrLeft(entity, path)) {
            path = AITools.calculatePathToHero(entity);
            idle(entity);

        } else {
            AITools.move(entity, path);
        }
    }
}
