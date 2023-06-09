package configuration.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import controller.ScreenController;
import ecs.entities.CharacterClasses.Mage;
import ecs.entities.CharacterClasses.Rogue;
import ecs.entities.CharacterClasses.Warrior;
import ecs.entities.Entity;
import ecs.entities.Hero;
import ecs.entities.Monster;
import ecs.entities.monster.MonsterChest;
import ecs.systems.ECS_System;
import java.io.File;
import java.util.Set;
import java.util.logging.Logger;
import saveandload.SerializableDungeon;
import starter.Game;
import tools.Point;

public class ChooseCharakter<T extends Actor> extends ScreenController<T> {

    private BitmapFont BitmapFont = new BitmapFont();
    private ScreenImage charakterclassOverlay;
    private ScreenButton newMageButton;
    private ScreenButton newRogueButton;
    private ScreenButton newWarriorButton;

    private int charakter = 0;

    /** Creates a ChooseCharakter Menü with a given Spritebatch */
    public ChooseCharakter() {
        this(new SpriteBatch());
    }

    /**
     * Creates a ChooseCharakter Menü with a given Spritebatch Creates Start button, End button and
     * load button Creates Wizzard, Warrior and Rogue button
     */
    public ChooseCharakter(SpriteBatch batch) {
        super(batch);

        Logger charakterInfo = Logger.getLogger(Game.getHero().getClass().getName());

        // Game Over Screen Text
        charakterclassOverlay =
                new ScreenImage("hud/CharakterklassenHud/ChooseCharakter.jpg", new Point(0, -10));
        charakterclassOverlay.setScale(0.5f, 0.5f);
        add((T) charakterclassOverlay);

        // Mage button
        TextButtonStyleBuilder mageButton = new TextButtonStyleBuilder(BitmapFont);
        mageButton.setFontColor(Color.BLACK);
        mageButton.setOverFontColor(Color.BLUE);
        mageButton.setDownFontColor(Color.BROWN);
        mageButton.setCheckedImage(
                "hud/CharakterklassenHud/wizzardButton/wizzard_f_run_anim_f1.png");
        mageButton.setUpImage("hud/CharakterklassenHud/wizzardButton/wizzard_f_run_anim_f0.png");
        mageButton.setDownImage("hud/CharakterklassenHud/wizzardButton/wizzard_f_run_anim_f2.png");

        newMageButton =
                new ScreenButton(
                        "",
                        new Point(290, 220),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                charakter = 2;
                                newWarriorButton.setChecked(false);
                                newRogueButton.setChecked(false);
                                charakterInfo.info(
                                        "\u001B[32m" + "Mage wurde ausgewaehlt" + "\u001B[32m");
                            }
                        },
                        mageButton.build());
        newMageButton.setScale(1, 1);

        add((T) newMageButton);

        // Rogue button
        TextButtonStyleBuilder rogueButton = new TextButtonStyleBuilder(BitmapFont);
        rogueButton.setFontColor(Color.BLACK);
        rogueButton.setOverFontColor(Color.BLUE);
        rogueButton.setDownFontColor(Color.BROWN);
        rogueButton.setCheckedImage("hud/CharakterklassenHud/ninjaButton/ninjaButton1.png");
        rogueButton.setUpImage("hud/CharakterklassenHud/ninjaButton/ninjaButton0.png");
        rogueButton.setDownImage("hud/CharakterklassenHud/ninjaButton/ninjaButton2.png");

        newRogueButton =
                new ScreenButton(
                        "",
                        new Point(380, 215),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                charakter = 3;
                                newWarriorButton.setChecked(false);
                                newMageButton.setChecked(false);
                                charakterInfo.info(
                                        "\u001B[32m" + "Rogue wurde ausgewaehlt" + "\u001B[32m");
                            }
                        },
                        rogueButton.build());
        newRogueButton.setScale(1, 1);

        add((T) newRogueButton);

        // Warrior button
        TextButtonStyleBuilder warriorButton = new TextButtonStyleBuilder(BitmapFont);
        warriorButton.setFontColor(Color.BLACK);
        warriorButton.setOverFontColor(Color.BLUE);
        warriorButton.setDownFontColor(Color.BROWN);
        warriorButton.setCheckedImage(
                "character/knight/idleLeft/knight_m_idle_anim_mirrored_f1.png");
        warriorButton.setUpImage("hud/CharakterklassenHud/knight_m_run_anim_f0.png");
        warriorButton.setDownImage("character/knight/runLeft/knight_m_run_anim_mirrored_f3.png");

        newWarriorButton =
                new ScreenButton(
                        "",
                        new Point(170, 220),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                charakter = 1;
                                newRogueButton.setChecked(false);
                                newMageButton.setChecked(false);
                                charakterInfo.info(
                                        "\u001B[32m" + "Warrior wurde ausgewaehlt" + "\u001B[32m");
                            }
                        },
                        warriorButton.build());
        newWarriorButton.setScale(1, 1);

        add((T) newWarriorButton);

        // End button
        TextButtonStyleBuilder endButton = new TextButtonStyleBuilder(BitmapFont);
        endButton.setFontColor(Color.BLACK);
        endButton.setOverFontColor(Color.BLUE);
        endButton.setDownFontColor(Color.BROWN);
        endButton.setCheckedImage("hud/btn_restart.png");
        endButton.setUpImage("hud/btn_restart.png");
        endButton.setDownImage("hud/btn_end.png");
        ScreenButton newEndButton =
                new ScreenButton(
                        "Endgame",
                        new Point(-20, 50),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                Gdx.app.exit();
                            }
                        },
                        endButton.build());
        newEndButton.setScale(1);
        add((T) newEndButton);

        // Start button
        TextButtonStyleBuilder startButton = new TextButtonStyleBuilder(BitmapFont);
        startButton.setFontColor(Color.BLACK);
        startButton.setOverFontColor(Color.BLUE);
        startButton.setDownFontColor(Color.BROWN);
        startButton.setCheckedImage("hud/btn_end.png");
        startButton.setUpImage("hud/btn_end.png");
        startButton.setDownImage("hud/btn_restart.png");
        ScreenButton newStartButton =
                new ScreenButton(
                        "Start",
                        new Point(300, 50),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                if (newWarriorButton.isChecked()) {
                                    // Instanz von warrior
                                    Hero warrior = new Warrior();
                                    Game.setHero(warrior);
                                    hideMenu();
                                    Game.lvUP(0);
                                    PauseMenu<Actor> pauseMenu = new PauseMenu<>();
                                    Game.systems.forEach(ECS_System::toggleRun);
                                } else if (newMageButton.isChecked()) {
                                    // Instanz von mage
                                    Hero mage = new Mage();
                                    Game.setHero(mage);
                                    hideMenu();
                                    Game.lvUP(0);
                                    Game.systems.forEach(ECS_System::toggleRun);
                                } else if (newRogueButton.isChecked()) {
                                    // Instanz von rogue
                                    Hero rogue = new Rogue();
                                    Game.setHero(rogue);
                                    hideMenu();
                                    Game.lvUP(0);
                                    Game.systems.forEach(ECS_System::toggleRun);
                                } else {
                                    Logger noCharChoosed = Logger.getLogger(this.getClass().getName());
                                    noCharChoosed.info("\u001B[33m" + "Pls choose a Charakter first" + "\u001B[31m");
                                }

                                Set<Entity> allEntities = Game.getEntities();
                                for (Entity allEntity : allEntities) {
                                    Game.removeEntity(allEntity);
                                }
                                Game.setHPandMPbarHero();
                                Entity monsterChest = new MonsterChest();
                                Game.addEntity(monsterChest);
                                hideMenu();
                                Game.lvUP(0);

                            }
                        },
                        startButton.build());

        newStartButton.setScale(1);
        add((T) newStartButton);

        // Load button
        TextButtonStyleBuilder loadButton = new TextButtonStyleBuilder(BitmapFont);
        loadButton.setFontColor(Color.BLACK);
        loadButton.setOverFontColor(Color.BLUE);
        loadButton.setDownFontColor(Color.BROWN);
        loadButton.setCheckedImage("hud/btn_end.png");
        loadButton.setUpImage("hud/btn_end.png");
        loadButton.setDownImage("hud/btn_restart.png");
        ScreenButton newLoadButton =
                new ScreenButton(
                        "Load Game",
                        new Point(150, 350),
                        new TextButtonListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                SerializableDungeon data = new SerializableDungeon();
                                Set<Entity> allEntities = Game.getEntities();
                                for (Entity allEntity : allEntities) {
                                    Game.removeEntity(allEntity);
                                }
                                hideMenu();
                                data.loadGame();
                                Game.setHPandMPbarHero();
                                Game.systems.forEach(ECS_System::toggleRun);
                            }
                        },
                        loadButton.build());

        newLoadButton.setScale(1);
        newLoadButton.setVisible(false);

        if (new File("saveGame.ser").exists()) {
            newLoadButton.setVisible(true);
        }
        add((T) newLoadButton);
    }

    /** shows the Menu */
    public void showMenu() {
        this.forEach((Actor s) -> s.setVisible(true));
    }

    /** hides the Menu */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }
}
