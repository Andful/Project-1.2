package org.lwjglb.game;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjglb.AI.EnvironmentInt;
import org.lwjglb.AI.pathFindingAlgorithms.PathFindingAlgorithm;
import org.lwjglb.AI.pathFindingAlgorithms.PathFindingAlgorithmReDo;
import org.lwjglb.Physics.Core;
import org.lwjglb.engine.*;
import org.lwjglb.engine.graph.Camera;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import static org.lwjgl.glfw.GLFW.*;

public class PhysicsGame implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final Camera camera;

    private List<GameItem> gameItems;

    private List<Block> blocks;

    List<Vector3f> blocksPosition= new LinkedList();

    private List<Core.Velocity> toRender=new LinkedList<>();

    private static final float CAMERA_POS_STEP = 0.05f;

    public PhysicsGame() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);

        gameItems = new LinkedList<>();
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        blocks=new LinkedList<>();
        blocks.add(new Block());
        blocksPosition=new LinkedList<>();
        for(Block b:blocks)
        {
            blocksPosition.add(b.getPosition());
            gameItems.add(b);
        }
        gameItems.add(new Floor(new Vector3i(10,10,10)));
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
            Vector3f position=blocks.get(0).getPosition();
            Vector3f endPosition=new Vector3f(position.x,position.y,position.z+1);
            toRender.add(new Core.Velocity(blocks.get(0),endPosition,blocksPosition,1.0f/GameEngine.TARGET_UPS));
        }
        if((window.isKeyPressed(GLFW_KEY_LEFT)||inputGui.previouseTimeStep()) && !left)
        {
            Vector3f position=blocks.get(0).getPosition();
            Vector3f endPosition=new Vector3f(position.x,position.y,position.z-1);
            toRender.add(new Core.Velocity(blocks.get(0),endPosition,blocksPosition,0.000001f));
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

        ListIterator<Core.Velocity> iter=toRender.listIterator();
        while(iter.hasNext())
        {
            Core.Velocity toUpdate=iter.next();
            if(toUpdate.update())
            {
                iter.remove();
            }
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
