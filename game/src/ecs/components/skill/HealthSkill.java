package ecs.components.skill;

import ecs.components.HealthComponent;
import ecs.entities.Entity;
import ecs.entities.Hero;
import java.util.logging.Logger;
import starter.Game;

public class HealthSkill implements ISkillFunction {

    private int manaCost;

    /**
     * Konstruktor
     *
     * @param manaCost
     */
    public HealthSkill(int manaCost) {
        this.manaCost = manaCost;
    }

    /**
     * Methode to use the Skill
     *
     * @param entity which uses the skill
     */
    @Override
    public void execute(Entity entity) {

        HealthComponent healh =
                (HealthComponent)
                        Game.getHero().get().getComponent(HealthComponent.class).orElseThrow();
        if (healh.getMaximalHealthpoints() - healh.getCurrentHealthpoints() > 0
                && Hero.getMana() >= manaCost) {
            healh.setCurrentHealthpoints(
                    healh.getCurrentHealthpoints()
                            + (healh.getMaximalHealthpoints() - healh.getCurrentHealthpoints()));
            Logger l = Logger.getLogger(Game.getHero().getClass().getName());
            l.info("\u001B[32m" + "your healthpoints are fully charged" + "\u001B[0m");
        } else {
            if (Hero.getMana() < manaCost) {
                Logger l = Logger.getLogger(Game.getHero().getClass().getName());
                l.info("\u001B[32m" + "your mana capacity is to low" + "\u001B[0m");
            } else if (healh.getMaximalHealthpoints() - healh.getCurrentHealthpoints() > 0) {
                Logger l = Logger.getLogger(Game.getHero().getClass().getName());
                l.info(
                        "\u001B[32m"
                                + "Your healthpoints are already fully charged you doo't need to charge them"
                                + "\u001B[0m");
            }
        }
    }
}
