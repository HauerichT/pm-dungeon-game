package starter;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static logging.LoggerConfig.initBaseLogger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import configuration.Configuration;
import configuration.KeyboardConfig;
import configuration.hud.GameOver;
import configuration.hud.LVup;
import configuration.hud.PauseMenu;
import controller.AbstractController;
import controller.SystemController;
import ecs.components.MissingComponentException;
import ecs.components.PlayableComponent;
import ecs.components.PositionComponent;
import ecs.components.ai.AIComponent;
import ecs.components.ai.fight.IFightAI;
import ecs.components.ai.fight.MeleeAI;
import ecs.components.skill.MeleeComponent;
import ecs.components.skill.Skill;
import ecs.entities.*;
import ecs.systems.*;
import graphic.DungeonCamera;
import graphic.Painter;
import graphic.hud.ScreenInventory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import level.IOnLevelLoader;
import level.LevelAPI;
import level.elements.ILevel;
import level.elements.tile.Tile;
import level.generator.IGenerator;
import level.generator.postGeneration.WallGenerator;
import level.generator.randomwalk.RandomWalkGenerator;
import level.tools.LevelSize;
import saveandload.SerializableDungeon;
import tools.Constants;
import tools.Point;

/** The heart of the framework. From here all strings are pulled. */
public class Game extends ScreenAdapter implements IOnLevelLoader {

    private final LevelSize LEVELSIZE = LevelSize.SMALL;

    /**
     * The batch is necessary to draw ALL the stuff. Every object that uses draw need to know the
     * batch.
     */
    protected SpriteBatch batch;

    /** Contains all Controller of the Dungeon */
    protected List<AbstractController<?>> controller;

    public static DungeonCamera camera;

    /** Draws objects */
    protected Painter painter;

    protected LevelAPI levelAPI;
    /** Generates the level */
    protected IGenerator generator;

    private boolean doSetup = true;
    private static boolean paused = false;
    private static boolean gameOverIsActive = false;
    /** All entities that are currently active in the dungeon */
    private static final Set<Entity> entities = new HashSet<>();
    /** All entities to be removed from the dungeon in the next frame */
    private static final Set<Entity> entitiesToRemove = new HashSet<>();
    /** All entities to be added from the dungeon in the next frame */
    private static final Set<Entity> entitiesToAdd = new HashSet<>();

    /** List of all Systems in the ECS */
    public static SystemController systems;

    public static ILevel currentLevel;

    private static PauseMenu<Actor> pauseMenu;
    private static LVup<Actor> lvUPscreen;
    private static final SerializableDungeon serializableDungeon = new SerializableDungeon();
    private static Entity hero;
    private int counterGhost;

    private static Ghost ghost;

    private static RandomEntityGenerator randomEntityGenerator;
    private static boolean inventoryShown = false;
    private static ScreenInventory<Actor> inv;

    /** Counter to save current level */
    private static int levelCounter;

    private Logger gameLogger;
    private static GameOver<Actor> gameOver;

    public static void main(String[] args) {
        // start the game
        try {
            Configuration.loadAndGetConfiguration("dungeon_config.json", KeyboardConfig.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DesktopLauncher.run(new Game());
    }

    /**
     * Main game loop. Redraws the dungeon and calls the own implementation (beginFrame, endFrame
     * and onLevelLoad).
     *
     * @param delta Time since last loop.
     */
    @Override
    public void render(float delta) {
        if (doSetup) setup();
        batch.setProjectionMatrix(camera.combined);
        frame();
        clearScreen();
        levelAPI.update();
        manageSkillCooldowns();
        controller.forEach(AbstractController::update);
        camera.update();
    }

    /** Called once at the beginning of the game. */
    protected void setup() {
        doSetup = false;
        controller = new ArrayList<>();
        setupCameras();
        painter = new Painter(batch, camera);
        generator = new RandomWalkGenerator();
        levelAPI = new LevelAPI(batch, painter, generator, this);
        initBaseLogger();
        gameLogger = Logger.getLogger(this.getClass().getName());
        systems = new SystemController();
        controller.add(systems);
        pauseMenu = new PauseMenu<>();
        controller.add(pauseMenu);
        randomEntityGenerator = new RandomEntityGenerator();
        hero = new Hero();
        inv = new ScreenInventory<>();
        lvUPscreen = new LVup<>();
        controller.add(lvUPscreen);
        controller.add(inv);
        gameOver = new GameOver<>();
        controller.add(gameOver);
        levelAPI = new LevelAPI(batch, painter, new WallGenerator(new RandomWalkGenerator()), this);
        levelAPI.loadLevel(LEVELSIZE);
        createSystems();
        if (new File("save.ser").exists()) {
            serializableDungeon.loadGame();
        }
    }

    /** Called at the beginning of each frame. Before the controllers call <code>update</code>. */
    protected void frame() {
        setCameraFocus();
        manageEntitiesSets();
        updateMeleeSkills();
        getHero().ifPresent(this::loadNextLevelIfEntityIsOnEndTile);
        Hero.addMana(0.005f);
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) togglePause();

        if (!new File("gameSer.ser").exists()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                serializableDungeon.saveGame();
                gameLogger.info("Spielstand gespeichert! Spiel wird verlassen...");
                Gdx.app.exit();
            }
        } else {
            gameLogger.info("Spielstand bereits gespeichert!");
        }

        // check if ghost is active
        if (ghost != null) {
            counterGhost++;
            if (counterGhost == 200) {
                ghost.movement();
                counterGhost = 0;
            }
        }
    }

    @Override
    public void onLevelLoad() {
        levelCounter++;
        currentLevel = levelAPI.getCurrentLevel();
        entities.clear();
        randomEntityGenerator.spawnRandomMonster();
        randomEntityGenerator.spawnRandomTrap();
        randomEntityGenerator.spwanRandomItems();
        randomEntityGenerator.spawnGhostAndGravestone();
        getHero().ifPresent(this::placeOnLevelStart);
    }

    private void manageEntitiesSets() {
        entities.removeAll(entitiesToRemove);
        entities.addAll(entitiesToAdd);
        for (Entity entity : entitiesToRemove) {
            gameLogger.info("Entity '" + entity.getClass().getSimpleName() + "' was deleted.");
        }
        for (Entity entity : entitiesToAdd) {
            gameLogger.info("Entity '" + entity.getClass().getSimpleName() + "' was added.");
        }
        entitiesToRemove.clear();
        entitiesToAdd.clear();
    }

    private void setCameraFocus() {
        if (getHero().isPresent()) {
            PositionComponent pc =
                    (PositionComponent)
                            getHero()
                                    .get()
                                    .getComponent(PositionComponent.class)
                                    .orElseThrow(
                                            () ->
                                                    new MissingComponentException(
                                                            "PositionComponent"));
            camera.setFocusPoint(pc.getPosition());

        } else camera.setFocusPoint(new Point(0, 0));
    }

    private void loadNextLevelIfEntityIsOnEndTile(Entity hero) {
        if (isOnEndTile(hero)) levelAPI.loadLevel(LEVELSIZE);
    }

    private boolean isOnEndTile(Entity entity) {
        PositionComponent pc =
                (PositionComponent)
                        entity.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        Tile currentTile = currentLevel.getTileAt(pc.getPosition().toCoordinate());
        return currentTile.equals(currentLevel.getEndTile());
    }

    private void placeOnLevelStart(Entity hero) {
        entities.add(hero);
        PositionComponent pc =
                (PositionComponent)
                        hero.getComponent(PositionComponent.class)
                                .orElseThrow(
                                        () -> new MissingComponentException("PositionComponent"));
        pc.setPosition(currentLevel.getStartTile().getCoordinate().toPoint());
    }

    /** Toggle between pause and run */
    public static void togglePause() {
        paused = !paused;
        if (systems != null) {
            systems.forEach(ECS_System::toggleRun);
        }
        if (pauseMenu != null) {
            if (paused) pauseMenu.showMenu();
            else pauseMenu.hideMenu();
        }
    }

    /**
     * Hide and Show the LV. Text
     *
     * @param level new Level
     */
    public static void lvUP(long level) {
        lvUPscreen.hideMenu();
        lvUPscreen.showMenu(level);
    }
    /** Toggle game over screen when the Hero dies */
    public static void toggleGameOver() {
        gameOverIsActive = !gameOverIsActive;
        if (systems != null) {
            systems.forEach(ECS_System::toggleRun);
        }
        if (gameOver != null) {
            if (gameOverIsActive) gameOver.showMenu();
            else gameOver.hideMenu();
        }
    }

    /** Toggle inventory menu */
    public static void toggleInventory() {
        inventoryShown = !inventoryShown;
        if (inv != null) {
            if (inventoryShown) inv.showMenu();
            else inv.hideMenu();
        }
    }

    /** Update inventory menu */
    public static void updateInventory(Entity worldItemEntity, int emptySlots) {
        inv.addItemToScreenInventory(worldItemEntity, emptySlots);
    }

    /** Reduces the cool-downs for all Skills for each entity */
    public void manageSkillCooldowns() {
        // reduce skill cooldown of hero
        PlayableComponent pc =
                (PlayableComponent) hero.getComponent(PlayableComponent.class).orElse(null);
        if (pc != null) {
            pc.getSkillSlot1().ifPresent(Skill::reduceCoolDown);
            pc.getSkillSlot2().ifPresent(Skill::reduceCoolDown);
        }
        // reduce skill cooldown of monsters
        entities.stream()
                .filter(entity -> entity.getComponent(AIComponent.class).isPresent())
                .map(entity -> (AIComponent) entity.getComponent(AIComponent.class).orElse(null))
                .filter(Objects::nonNull)
                .forEach(this::reduceSkillCooldown);
    }

    /* Reduces the cooldown time of a skill */
    private void reduceSkillCooldown(AIComponent aiComponent) {
        IFightAI fightAI = aiComponent.getFightAI();
        if (fightAI.getClass() == MeleeAI.class) {
            ((MeleeAI) fightAI).getFightSkill().reduceCoolDown();
        }
    }

    /* Updates all MeleeSkills for each entity that has one */
    private void updateMeleeSkills() {
        List<Entity> l =
                Game.entities.stream()
                        .filter(a -> a.getComponent(MeleeComponent.class).orElse(null) != null)
                        .toList();
        for (Entity a : l) {
            MeleeComponent mc = (MeleeComponent) a.getComponent(MeleeComponent.class).orElseThrow();
            mc.getMeleeSkill().update(a);
        }
    }

    /**
     * Given entity will be added to the game in the next frame
     *
     * @param entity will be added to the game next frame
     */
    public static void addEntity(Entity entity) {
        entitiesToAdd.add(entity);
    }

    /**
     * Given entity will be removed from the game in the next frame
     *
     * @param entity will be removed from the game next frame
     */
    public static void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

    /**
     * @return Set with all entities currently in game
     */
    public static Set<Entity> getEntities() {
        return entities;
    }

    /**
     * @return Set with all entities that will be added to the game next frame
     */
    public static Set<Entity> getEntitiesToAdd() {
        return entitiesToAdd;
    }

    /**
     * @return Set with all entities that will be removed from the game next frame
     */
    public static Set<Entity> getEntitiesToRemove() {
        return entitiesToRemove;
    }

    /**
     * @return the player character, can be null if not initialized
     */
    public static Optional<Entity> getHero() {
        return Optional.ofNullable(hero);
    }

    /**
     * @return current level
     */
    public static int getLevelCounter() {
        return levelCounter;
    }

    /**
     * set the reference of the playable character careful: old hero will not be removed from the
     * game
     *
     * @param hero new reference of hero
     */
    public static void setHero(Entity hero) {
        Game.hero = hero;
    }

    /** set ghost */
    public static void setGhost(Ghost ghost) {
        Game.ghost = ghost;
    }

    public void setSpriteBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }

    private void setupCameras() {
        camera = new DungeonCamera(null, Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.zoom = Constants.DEFAULT_ZOOM_FACTOR;

        // See also:
        // https://stackoverflow.com/questions/52011592/libgdx-set-ortho-camera
    }

    private void createSystems() {
        new VelocitySystem();
        new DrawSystem(painter);
        new PlayerSystem();
        new AISystem();
        new CollisionSystem();
        new HealthSystem();
        new XPSystem();
        new SkillSystem();
        new ProjectileSystem();
    }
}
