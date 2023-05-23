package ecs.systems;

import ecs.components.*;
import ecs.components.skill.*;
import ecs.entities.Entity;
import starter.Game;
import tools.Point;

public class ProjectileSystem extends ECS_System {
    private static boolean boomerangIsComingBack = false;

    // private record to hold all data during streaming
    private record PSData(
            Entity e, ProjectileComponent prc, PositionComponent pc, VelocityComponent vc) {}

    /** sets the velocity and removes entities that reached their endpoint */
    @Override
    public void update() {
        Game.getEntities().stream()
                // Consider only entities that have a ProjectileComponent
                .flatMap(e -> e.getComponent(ProjectileComponent.class).stream())
                .map(prc -> buildDataObject((ProjectileComponent) prc))
                .map(this::setVelocity)
                // Filter all entities that have reached their endpoint
                .filter(
                        psd -> {
                            // checks if the entity is not a boomerang or the point to take the
                            // boomerang back is reached
                            if (!psd.e.getIsBoomerang() || boomerangIsComingBack) {
                                return hasReachedEndpoint(
                                        psd.prc.getStartPosition(),
                                        psd.prc.getGoalLocation(),
                                        psd.pc.getPosition());
                            } else {
                                hasReachedMiddlePoint(
                                        psd.prc.getStartPosition(),
                                        psd.prc.getGoalLocation(),
                                        psd.pc.getPosition(),
                                        psd.e);
                                return false;
                            }
                        })
                // Remove all entities who reached their endpoint
                .forEach(this::removeEntitiesOnEndpoint);
    }

    private PSData buildDataObject(ProjectileComponent prc) {
        Entity e = prc.getEntity();

        PositionComponent pc =
                (PositionComponent)
                        e.getComponent(PositionComponent.class)
                                .orElseThrow(ProjectileSystem::missingAC);
        VelocityComponent vc =
                (VelocityComponent)
                        e.getComponent(VelocityComponent.class)
                                .orElseThrow(ProjectileSystem::missingAC);

        return new PSData(e, prc, pc, vc);
    }

    private PSData setVelocity(PSData data) {
        data.vc.setCurrentYVelocity(data.vc.getYVelocity());
        data.vc.setCurrentXVelocity(data.vc.getXVelocity());

        return data;
    }

    private void removeEntitiesOnEndpoint(PSData data) {
        Game.removeEntity(data.pc.getEntity());
    }

    /**
     * checks if the endpoint is reached
     *
     * @param start position to start the calculation
     * @param end point to check if projectile has reached its goal
     * @param current current position
     * @return true if the endpoint was reached or passed, else false
     */
    public boolean hasReachedEndpoint(Point start, Point end, Point current) {
        float dx = start.x - current.x;
        float dy = start.y - current.y;
        double distanceToStart = Math.sqrt(dx * dx + dy * dy);

        dx = start.x - end.x;
        dy = start.y - end.y;
        double totalDistance = Math.sqrt(dx * dx + dy * dy);

        if (distanceToStart > totalDistance) {
            // The point has reached or passed the endpoint
            boomerangIsComingBack = false;
            return true;
        } else {
            // The point has not yet reached the endpoint
            return false;
        }
    }

    /**
     * checks if the projectile has reached the endpoint to take it back to the start (boomerang)
     *
     * @param start position to start the calculation
     * @param end point to check if projectile has reached its goal
     * @param current current position
     * @param projectile the entity that can return to its owner
     */
    public void hasReachedMiddlePoint(Point start, Point end, Point current, Entity projectile) {
        float dx = start.x - current.x;
        float dy = start.y - current.y;
        double distanceToStart = Math.sqrt(dx * dx + dy * dy);

        dx = start.x - end.x;
        dy = start.y - end.y;
        double totalDistance = Math.sqrt(dx * dx + dy * dy);

        if (distanceToStart > totalDistance && !boomerangIsComingBack) {
            boomerangIsComingBack = true;
            Game.removeEntity(projectile);
            BoomerangSkill boomerangSkill = new BoomerangSkill(SkillTools::getHeroPosition);
            boomerangSkill.execute(projectile, true);
        }
    }

    /**
     * sets if boomerang reached endpoint and is coming back
     *
     * @param boomerangIsComingBack boolean to check if boomerang is coming back
     */
    public static void setBoomerangIsComingBack(boolean boomerangIsComingBack) {
        ProjectileSystem.boomerangIsComingBack = boomerangIsComingBack;
    }

    private static MissingComponentException missingAC() {
        return new MissingComponentException("AnimationComponent");
    }
}
