package org.lwjglb.engine;

public interface IGameLogic {

    void init(Window window) throws Exception;
    
    void input(Window window, MouseInput mouseInput, MainMenu inputGui);

    void update(float interval, MouseInput mouseInput, MainMenu inputGui);
    
    void render(Window window);
    
    void cleanup();
}