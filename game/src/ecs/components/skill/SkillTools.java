package ecs.components.skill;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ecs.components.HealthComponent;
import ecs.components.PositionComponent;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import starter.Game;
import tools.Point;

public class SkillTools {

    /**
     * calculates the last position in range regardless of aimed position
     *
     * @param startPoint position to start the calculation
     * @param aimPoint point to aim for
     * @param range range from start to
     * @return last position in range if you follow the directon from startPoint to aimPoint
     */
    public static Point calculateLastPositionInRange(
            Point startPoint, Point aimPoint, float range) {

        // calculate distance from startPoint to aimPoint
        float dx = aimPoint.x - startPoint.x;
        float dy = aimPoint.y - startPoint.y;

        // vector from startPoint to aimPoint
        Vector2 scv = new Vector2(dx, dy);

        // normalize the vector (length of 1)
        scv.nor();

        // resize the vector to the length of the range
        scv.scl(range);

        return new Point(startPoint.x + scv.x, startPoint.y + scv.y);
    }

    public static Point calculateVelocity(Point start, Point goal, float speed) {
        float x1 = start.x;
        float y1 = start.y;
        float x2 = goal.x;
        float y2 = goal.y;

        float dx = x2 - x1;
        float dy = y2 - y1;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        float velocityX = dx / distance * speed;
        float velocityY = dy / distance * speed;
        return new Point(velocityX, velocityY);
    }

    /**
     * gets the current cursor position as Point
     *
     * @return mouse cursor position as Point
     */
    public static Point getCursorPositionAsPoint() {
        Vector3 mousePosition =
                Game.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        return new Point(mousePosition.x, mousePosition.y);
    }

    /**
     * Calculates a new Point that represents the direction limited by 1
     *
     * @param entity start point
     * @param target target point
     * @return a new Point with values ranging from -1 to 1
     */
    public static Point getMeleeSkillOffsetPositon(Point entity, Point target) {
        float newx = target.x - entity.x;
        float newy = target.y - entity.y;
        float offset = 1;
        if (newx > offset) newx = offset;
        if (newx < (offset * -1)) newx = (offset * -1);
        if (newy > offset) newy = offset;
        if (newy < (offset * -1)) newy = (offset * -1);

        return new Point(newx, newy);
    }

    public static Point getHeroPosition() {
        Entity h = Game.getHero().orElseThrow();
        PositionComponent pc =
            (PositionComponent) h.getComponent(PositionComponent.class).orElseThrow();
        return pc.getPosition();
    }

    /**
     * Gets the position from the nearest entity to a starting entity
     *
     * @param startingEntity as the starting point
     * @return position of the nearest entity as a point
     */
    public static Point getNearestEntityPosition(Entity startingEntity) {
        PositionComponent startingEntitypc =
            (PositionComponent)
                startingEntity.getComponent(PositionComponent.class).orElseThrow();
        Point nearestEntityPoint = SkillTools.getCursorPositionAsPoint();
        float max = 999f;
        for (Entity targetEntitys : Game.getEntities()) {
            // continue only if the Entity has a Healthcomponent and Positioncomponent
            if (targetEntitys.getComponent(HealthComponent.class).orElse(null) == null
                || targetEntitys.getComponent(PositionComponent.class).orElse(null) == null)
                continue;

            PositionComponent targetEntitypc =
                (PositionComponent)
                    targetEntitys.getComponent(PositionComponent.class).orElseThrow();
            Point startingEntityPoint = startingEntitypc.getPosition();
            Point targetEntityPoint = targetEntitypc.getPosition();
            float distance = Point.calculateDistance(startingEntityPoint, targetEntityPoint);

            if (distance < max && distance >= 0.1 && distance < 10) {
                max = distance;
                nearestEntityPoint = targetEntityPoint;
            }
        }
        return nearestEntityPoint;
    }

    public static void recieveKnockback(Point projectileStartPosition, Entity entity) {
        PositionComponent epc = (PositionComponent) entity.getComponent(PositionComponent.class).orElseThrow();
        Point entityPosition = epc.getPosition();
        Point direction = Point.getUnitDirectionalVector(entityPosition, projectileStartPosition);

        entity.getComponent(VelocityComponent.class)
            .ifPresent(
                vc -> {
                    ((VelocityComponent) vc).setCurrentXVelocity(direction.x * 0.4f);
                    ((VelocityComponent) vc).setCurrentYVelocity(direction.y * 0.4f);
                });
    }
}
