package org.lwjglb.AI;

public class AItester {
    public static void main(String[] args) {
        Environment environment = new Environment();
        Reader reader = new Reader(environment,
                "Configuration Files/StartConfigs/Test 1",
                "Configuration Files/TargetConfigs/Test 1",
                "Configuration Files/ObstacleConfigs/Test 1");
        environment.createObstacleMap();
    }
}
