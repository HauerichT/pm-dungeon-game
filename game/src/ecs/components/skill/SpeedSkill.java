package ecs.components.skill;

import dslToGame.AnimationBuilder;
import ecs.components.VelocityComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import graphic.Animation;
import starter.Game;
import java.awt.*;
import java.util.Arrays;


/**
 * Speedskill is a skill that increase the speed of the Hero until he get damage.
 */
public class SpeedSkill implements ISkillFunction {
    float speed = 0.5f;
    int manaCost = 5;

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

        heroV = (VelocityComponent) entity.getComponent(VelocityComponent.class).orElseThrow();
        heroV.setXVelocity(speed);
        heroV.setYVelocity(speed);
        System.out.println("Die Faehigkeit SpeedSkill wurde aktiviert! \n Mana -" + manaCost);
        }


    }

