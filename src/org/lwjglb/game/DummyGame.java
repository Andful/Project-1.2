package org.lwjglb.game;

import org.joml.Vector2f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3i;
import org.lwjglb.AI.*;
import org.lwjglb.AI.pathFindingAlgorithms.PathFindingAlgorithm;
import org.lwjglb.AI.pathFindingAlgorithms.PathFindingAlgorithmReDo;
import org.lwjglb.AI.pathFindingAlgorithms.furthestFirst;
import org.lwjglb.AI.pathFindingAlgorithms.furthestFirstRedo;
import org.lwjglb.engine.GameItem;
import org.lwjglb.engine.IGameLogic;
import org.lwjglb.engine.MouseInput;
import org.lwjglb.engine.Window;
import org.lwjglb.engine.graph.Camera;
import org.lwjglb.engine.MainMenu;

import java.util.LinkedList;
import java.util.List;

public class DummyGame implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final Camera camera;

    private List<GameItem> gameItems;

    private AgentDrawer agentDrawer;

    private static final float CAMERA_POS_STEP = 0.05f;

    public DummyGame() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);

        gameItems = new LinkedList<>();
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        List<Vector3i> agentsPosition;

        List<Vector3i> endPosition;

        List<Vector3i> blocksPosition;

        Vector3i enviromentSize;

        List<List<PathFindingAlgorithm.Movment<Integer>>> movments=new LinkedList<>();

        EnvironmentInt enviroment=new EnvironmentInt(new java.io.File("resources\\levels\\1\\"));

        enviromentSize=enviroment.getEnvironmentSize();

        agentsPosition=enviroment.getAgentStartConfigurations();

        endPosition=enviroment.getAgentEndConfigurations();

        List<Vector3i> obstacles=enviroment.getObstaclesPositions();

        gameItems.add(new ObstacleDrawer(obstacles));
        gameItems.add(new StartConfigurationDrawer(agentsPosition));
        gameItems.add(new Floor(enviromentSize));
        gameItems.add(new EndConfigurationDrawer(endPosition));
        gameItems.add(agentDrawer=new AgentDrawer(agentsPosition,movments));
        new Thread()
        {
            public void run()
            {
                new PathFindingAlgorithmReDo<Integer>().

                        computeMovments(enviromentSize, agentsPosition, new LinkedList<Integer>()
                        {
                            {
                                int size = agentsPosition.size();
                                for (int i = 0; i < size; i++)
                                {
                                    add(i);
                                }
                            }
                        }, endPosition, obstacles, movments);
            }
        }.start();
    }

    @Override
    public void input(Window window, MouseInput mouseInput, MainMenu inputGui) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)||inputGui.cameraForwards()) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)||inputGui.cameraBackwards()) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)||inputGui.cameraLeft()) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)||inputGui.cameraRight()) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)||inputGui.cameraDown()) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_Q)||inputGui.cameraUp()) {
            cameraInc.y = 1;
        }

        if((window.isKeyPressed(GLFW_KEY_RIGHT)||inputGui.nextTimeStep()) && !right)
        {
            agentDrawer.nextMove();
        }
        if((window.isKeyPressed(GLFW_KEY_LEFT)||inputGui.previouseTimeStep()) && !left)
        {
            agentDrawer.previouseMove();
        }
        right=(window.isKeyPressed(GLFW_KEY_RIGHT)||inputGui.nextTimeStep());
        left=(window.isKeyPressed(GLFW_KEY_LEFT)||inputGui.previouseTimeStep());
    }
    boolean left;
    boolean right;

    @Override
    public void update(float interval, MouseInput mouseInput, MainMenu inputGui) {
        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);
        // Update camera based on mouse
        if (mouseInput.isLeftButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, gameItems);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }

}
