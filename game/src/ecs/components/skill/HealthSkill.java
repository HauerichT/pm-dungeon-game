package ecs.components.skill;

import ecs.components.HealthComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import starter.Game;

import java.util.logging.Logger;

public class HealthSkill implements ISkillFunction{


    /** Konstruktor
     * @Params heal amount  * */
    public HealthSkill(){

    }
    /** Methode to use the Skill
     * @Params: Entity which uses the Skill * */
    @Override
    public void execute(Entity entity) {

        HealthComponent healh = (HealthComponent) Game.getHero().get().getComponent(HealthComponent.class).orElseThrow();
        if (healh.getMaximalHealthpoints() - healh.getCurrentHealthpoints() > 0) {
            Logger l = Logger.getLogger(Game.getHero().getClass().getName());
            l.info("\u001B[32m" + healh.getCurrentHealthpoints() + "\u001B[0m");
            healh.setCurrentHealthpoints(healh.getCurrentHealthpoints()+ (healh.getMaximalHealthpoints() - healh.getCurrentHealthpoints()));
            l.info("\u001B[32m" + healh.getCurrentHealthpoints() + "\u001B[0m");
            l.info("\u001B[32m" + "your healthpoints are fully charged" + "\u001B[0m");
        }
    }

    }

