package ecs.components.skill;

import ecs.entities.Entity;
import ecs.entities.monster.FriendlyMonster;
import starter.Game;

/** Spawns a friendly Monster to help the Hero. The Hero will learn to use it on a higher lv. */
public class FriendlyMonsterSkill implements ISkillFunction {

    @Override
    public void execute(Entity entity) {
        Game.addEntity(new FriendlyMonster());
        System.out.println("new FirendlyMonster");
    }
}
