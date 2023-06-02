package ecs.components.skill;

import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import java.util.logging.Logger;
import starter.Game;

/** Speedskill is a skill that increase the speed of the Hero until he get damage. */
public class SpeedSkill implements ISkillFunction {
    private float speed = 0.5f;
    private int manaCost;

    private VelocityComponent heroV;

    /** Konstruktor @Params mana costs of the Skill * */
    public SpeedSkill(int manaCost) {
        this.manaCost = manaCost;
    }

    /** Methode to use the Skill @Params: Entity which uses the Skill * */
    @Override
    public void execute(Entity entity) {
        Logger l = Logger.getLogger(Game.getHero().getClass().getName());
        if (Hero.getMana() >= manaCost) {
            Hero.reduceMana(manaCost);
            heroV = (VelocityComponent) entity.getComponent(VelocityComponent.class).orElseThrow();
            heroV.setXVelocity(speed);
            heroV.setYVelocity(speed);
            l.info("\u001B[34m" + "Hero used Speedskill" + "\u001B[0m");

        } else {
            l.info("\u001B[32m" + "your mana capacity is to low" + "\u001B[0m");
        }
    }
}
