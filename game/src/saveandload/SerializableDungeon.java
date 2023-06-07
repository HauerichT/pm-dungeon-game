package saveandload;

import ecs.components.PositionComponent;
import ecs.entities.*;
import ecs.entities.CharacterClasses.Mage;
import ecs.entities.CharacterClasses.Rogue;
import ecs.entities.CharacterClasses.Warrior;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import starter.Game;

/** SerializableDungeon is a class which allows to save and load a game */
public class SerializableDungeon {
    private SerializableDungeonData data = new SerializableDungeonData();
    private Logger serLogger = Logger.getLogger(this.getClass().getName());

    /** Saves the current game */
    public void saveGame() {
        // Saves current level count
        data.setLevel(Game.getLevelCounter());

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

        for (int i = 0; i < data.getEntities().size(); i++) {
            System.out.println(data.getEntities().get(i));
            if (data.getEntities().get(i).getClass().getName().contains("monster")) {
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
            } else if (data.getEntities().get(i).getClass().getName().contains("item")) {
                Item e = (Item) data.getEntities().get(i);
                e.setNewComponentMap();
                new PositionComponent(e);
                Game.addEntity(e);
            } else if (data.getEntities().get(i).getClass().getName().contains("trap")) {
                Trap e = (Trap) data.getEntities().get(i);

                e.setNewComponentMap();

                new PositionComponent(e);
                Game.addEntity(e);
            } else if (data.getEntities().get(i).getClass().getName().contains("Mage")) {
                Game.setHero(new Mage());
            } else if (data.getEntities().get(i).getClass().getName().contains("Rogue")) {
                Game.setHero(new Rogue());
            } else if (data.getEntities().get(i).getClass().getName().contains("Warrior")) {
                Game.setHero(new Warrior());
            }
        }
        new File("saveGame.ser").delete();
        serLogger.info("\u001B[32m" + "Spiel geladen!" + "\u001B[0m");
    }
}
