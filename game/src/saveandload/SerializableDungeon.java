package saveandload;

import ecs.components.PositionComponent;
import ecs.components.xp.XPComponent;
import ecs.entities.*;
import ecs.entities.CharacterClasses.Mage;
import ecs.entities.CharacterClasses.Rogue;
import ecs.entities.CharacterClasses.Warrior;
import ecs.entities.monster.MonsterChest;
import ecs.entities.trap.SpawnTrap;
import ecs.entities.trap.SpikeTrap;
import ecs.entities.trap.TpTrap;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import starter.Game;

/** SerializableDungeon is a class which allows to save and load a game */
public class SerializableDungeon {
    private SerializableDungeonData data = new SerializableDungeonData();
    private final Logger serLogger = Logger.getLogger(this.getClass().getName());

    /** Saves the current game */
    public void saveGame() {
        // Saves current level count
        data.setLevel(Game.getLevelCounter());
        Game.getHero()
                .flatMap(hero -> hero.getComponent(XPComponent.class))
                .ifPresent(
                        xp -> {
                            data.setHeroXPLevel((int) ((XPComponent) xp).getCurrentLevel());
                            data.setHeroXP(((XPComponent) xp).getCurrentXP());
                        });

        // Saves current entities
        List<Entity> entities = new ArrayList<>(Game.getEntities());
        data.setEntities(entities);

        FileOutputStream fos;
        ObjectOutputStream out;
        try {
            fos = new FileOutputStream("saveGame.ser");
            out = new ObjectOutputStream(fos);
            out.writeObject(data);
            serLogger.info("\u001B[32m" + "Spiel gespeichert!" + "\u001B[0m");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** Loads the entities of the last saved game in new game */
    public void loadGame() {
        FileInputStream fis;
        ObjectInputStream in;
        try {
            fis = new FileInputStream("saveGame.ser");
            in = new ObjectInputStream(fis);
            data = (SerializableDungeonData) in.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Game.setLevelCounter(data.getLevel());

        // Loads the saved entities with their components
        for (int i = 0; i < data.getEntities().size(); i++) {
            System.out.println(data.getEntities().get(i).getClass().getName());
            if (data.getEntities().get(i).getClass().getName().contains("monster")
                    && !data.getEntities().get(i).getClass().getName().contains("MonsterChest")) {
                Monster e = (Monster) data.getEntities().get(i);
                e.setNewComponentMap();
                e.setupPositionComponent();
                e.setupHitboxComponent();
                e.setupAnimationComponent();
                e.setupVelocityComponent();
                e.setupXPComponent();
                e.setupHealthComponent();
                e.setupAIComponent();
                Game.addEntity(e);
            } else if (data.getEntities().get(i).getClass().getName().contains("items")) {
                Item e = (Item) data.getEntities().get(i);
                e.setNewComponentMap();
                e.setupItemData();
                new PositionComponent(e);
                Game.addEntity(e);
            } else if (data.getEntities().get(i).getClass().getName().contains("TpTrap")) {
                TpTrap e = (TpTrap) data.getEntities().get(i);
                e.setNewComponentMap();
                new PositionComponent(e);
                e.setupAnimationComponent(false);
                e.setupHitboxComponent();
                Game.addEntity(e);
            } else if (data.getEntities().get(i).getClass().getName().contains("SpikeTrap")) {
                SpikeTrap e = (SpikeTrap) data.getEntities().get(i);
                e.setNewComponentMap();
                new PositionComponent(e);
                e.setupAnimationComponent(0);
                e.setupHitboxComponent();
                Game.addEntity(e);
            } else if (data.getEntities().get(i).getClass().getName().contains("SpawnTrap")) {
                SpawnTrap e = (SpawnTrap) data.getEntities().get(i);
                e.setNewComponentMap();
                new PositionComponent(e);
                e.setupAnimationComponent(0);
                e.setupHitboxComponent();
                Game.addEntity(e);
            } else if (data.getEntities().get(i).getClass().getName().contains("Mage")) {
                Mage e = (Mage) data.getEntities().get(i);
                e.setNewComponentMap();
                e.setupPositionComponent();
                e.setupPlayableComponent();
                e.setupInventoryComponent();
                e.setupVelocityComponent();
                e.setupAnimationComponent();
                e.setupHealthComponent();
                e.setupHitBoxComponent();
                e.setupXPComponent(data.getHeroXPLevel(), data.getHeroXP());
                e.onLevelUp(data.getHeroXPLevel());
                e.setupSkillComponent();
                Game.addEntity(e);
                Game.setHero(e);
            } else if (data.getEntities().get(i).getClass().getName().contains("Rogue")) {
                Rogue e = (Rogue) data.getEntities().get(i);
                e.setNewComponentMap();
                e.setupPositionComponent();
                e.setupPlayableComponent();
                e.setupInventoryComponent();
                e.setupVelocityComponent();
                e.setupAnimationComponent();
                e.setupHealthComponent();
                e.setupHitBoxComponent();
                e.setupXPComponent(data.getHeroXPLevel(), data.getHeroXP());
                e.onLevelUp(data.getHeroXPLevel());
                e.setupSkillComponent();
                Game.addEntity(e);
                Game.setHero(e);
            } else if (data.getEntities().get(i).getClass().getName().contains("Warrior")) {
                Warrior e = (Warrior) data.getEntities().get(i);
                e.setNewComponentMap();
                e.setupPositionComponent();
                e.setupPlayableComponent();
                e.setupInventoryComponent();
                e.setupVelocityComponent();
                e.setupAnimationComponent();
                e.setupHealthComponent();
                e.setupHitBoxComponent();
                e.setupXPComponent(data.getHeroXPLevel(), data.getHeroXP());
                e.onLevelUp(data.getHeroXPLevel());
                e.setupSkillComponent();
                Game.addEntity(e);
                Game.setHero(e);
            } else if (data.getEntities().get(i).getClass().getName().contains("Gravestone")) {
                Gravestone e = (Gravestone) data.getEntities().get(i);
                e.setNewComponentMap();
                new PositionComponent(e);
                e.setupRandomEntityGenerator();
                e.setupHitboxComponent();
                e.setupAnimationComponent(false);
                Game.addEntity(e);
            } else if (data.getEntities().get(i).getClass().getName().contains("Ghost")) {
                Ghost e = (Ghost) data.getEntities().get(i);
                e.setNewComponentMap();
                new PositionComponent(e);
                e.setupVelocityComponent();
                e.setupAIComponent();
                e.setupAnimationComponent();
                Game.addEntity(e);
            } else if (data.getEntities().get(i).getClass().getName().contains("MonsterChest")) {
                MonsterChest e = (MonsterChest) data.getEntities().get(i);
                e.setNewComponentMap();
                e.setupAnimationComponent();
                e.setupPositionComponent();
                e.setupInteractionComponent();
                Game.addEntity(e);
            }
        }
        new File("saveGame.ser").delete();
        serLogger.info("\u001B[32m" + "Spiel geladen!" + "\u001B[0m");
    }
}
