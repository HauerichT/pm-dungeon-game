package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Animation;
import starter.Game;
import java.awt.*;
import java.util.Arrays;
import java.util.logging.Logger;


/**
 * Speedskill is a skill that increase the speed of the Hero until he get damage.
 */
public class SpeedSkill implements ISkillFunction {
    float speed = 0.5f;
    int manaCost;

    VelocityComponent heroV;

    /** Konstruktor
     * @Params mana costs of the Skill * */
    public SpeedSkill(int manaCost) {
        this.manaCost = manaCost;
    }

    /** Methode to use the Skill
     * @Params: Entity which uses the Skill * */
    @Override
    public void execute(Entity entity) {
        Logger l = Logger.getLogger(Game.getHero().getClass().getName());
        if (Hero.getMana() >= manaCost){
            Hero.reduceMana(manaCost);
            heroV = (VelocityComponent) entity.getComponent(VelocityComponent.class).orElseThrow();
            heroV.setXVelocity(speed);
            heroV.setYVelocity(speed);
            System.out.println("Die Faehigkeit SpeedSkill wurde aktiviert! \n Mana -" + manaCost);
            l.info("\u001B[34m" + "Hero used Speedskill" + "\u001B[0m");

        }
        else {
            l.info("\u001B[32m" + "your mana capacity is to low" + "\u001B[0m");
        }


    }

}

