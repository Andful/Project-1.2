package org.lwjglb.game;

import org.joml.Vector2f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjglb.engine.GameItem;
import org.lwjglb.engine.IGameLogic;
import org.lwjglb.engine.MouseInput;
import org.lwjglb.engine.Window;
import org.lwjglb.engine.graph.Camera;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.Texture;

import java.util.LinkedList;
import java.util.List;

public class DummyGame implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final Camera camera;

    private List<GameItem> gameItems;

    private List<Agent> agents;

    private List<Block> blocks;

    private static final float CAMERA_POS_STEP = 0.05f;

    public DummyGame() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);

        gameItems = new LinkedList<>();
        blocks=new LinkedList<>();
        agents=new LinkedList<>();
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);



        Obstacle box1 = new Obstacle();
        box1.setPosition(5,0,5);

        gameItems.add(new Floor());
        blocks.add(box1);

        agents=new LinkedList<>();
        agents.add(new Agent(blocks));
        agents.get(0).setPosition(0,1.5f,0);
        agents.add(new Agent(blocks));

        mergeLists();
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_Q)) {
            cameraInc.y = 1;
        }
    }

    public void mergeLists()
    {
        for(Agent agent:agents)
        {
            blocks.add(agent);
        }

        for(Block block:blocks)
        {
            gameItems.add((GameItem) block);
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        //System.out.println(interval);
        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);
        //System.out.println(mouseInput.getDeltaPosition());
        // Update camera based on mouse
        if (mouseInput.isLeftButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }
        if(mouseInput.isRightButtonPressed())
            for (Agent agent: agents)
            {
                agent.update(interval,blocks);
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
        for(Agent agent:agents )
        {
            agent.getMesh().cleanUp();
        }
    }

}
