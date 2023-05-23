package ecs.components.skill;

import ecs.components.VelocityComponent;
import ecs.entities.Entity;

public class SpeedSkill implements ISkillFunction {
    float speed = 0.5f;
    int manaCost = 5;
    VelocityComponent heroV;

    /** Konstruktor Params mana costs of the Skill * */
    public SpeedSkill(int manaCost) {
        this.manaCost = manaCost;
    }

    /** Methode to use the Skill
     * @Params: Entity which uses the Skill * */
    @Override
    public void execute(Entity hero) {
        VelocityComponent heroV =
                (VelocityComponent) hero.getComponent(VelocityComponent.class).orElseThrow();

        heroV.setCurrentXVelocity(speed);
        heroV.setCurrentYVelocity(speed);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        heroV.setCurrentXVelocity(0.25f);
        heroV.setCurrentYVelocity(0.25f);
        System.out.println("Die Fähigkeit SpeedSkill wurde aktiviert! \n Mana - " + manaCost);
    }
}
