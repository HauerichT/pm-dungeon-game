package saveandload;

import ecs.entities.Entity;
import starter.Game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** SerializableDungeon is a class which allows to save and load a game */
public class SerializableDungeon {
    private SerializableDungeonData data = new SerializableDungeonData();


    /** Saves the current game */
    public void saveGame() {
        // Saves current level count
        data.setLevel(Game.getLevelCounter());

        // Saves current entities
        List<String> entities = new ArrayList<>();
        for (Entity entity : Game.getEntities()) {
            entities.add(entity.getClass().getSimpleName());
        }
        data.setEntities(entities);

        FileOutputStream fos;
        ObjectOutputStream out;
        try {
            fos = new FileOutputStream("saveGame.ser");
            out = new ObjectOutputStream(fos);
            out.writeObject(data);
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
            System.out.println(data.getEntities().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for(int i = 0; i < data.getEntities().size(); i++) {
            System.out.println(data.getEntities().get(i));
        }

        new File("saveGame.ser").delete();
    }
}
