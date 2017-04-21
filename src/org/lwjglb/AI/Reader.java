package org.lwjglb.AI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
    private Environment environment;
    private BufferedReader br;
    private FileReader fr;
    private Scanner sc;

    public Reader(Environment environment,String startConfigFile, String targetConfigFile, String obstacleFile){
        this.environment = environment;
        readStartConfig(startConfigFile);
        readTargetConfig(targetConfigFile);
        readObstacleConfig(obstacleFile);

    }

    private void readStartConfig(String startConfigFile){
        try {
            br = new BufferedReader(fr = new FileReader(startConfigFile));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sc = new Scanner(currentLine).useDelimiter("\\s*,\\s*");
                String ID = sc.next();
                int x = sc.nextInt() + Environment.worldOffset;
                int y = sc.nextInt();
                int z = sc.nextInt() + Environment.worldOffset;
                environment.updateHighestXYZ(x,y,z);
                environment.getCrowd().addAgent(ID,x,y,z);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void readTargetConfig(String targetConfigFile){
        ArrayList<xyzStore> Xlist = new ArrayList<>();
        try {
            br = new BufferedReader(fr = new FileReader(targetConfigFile));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sc = new Scanner(currentLine).useDelimiter("\\s*,\\s*");
                String ID = sc.next();
                int x = sc.nextInt() + Environment.worldOffset;
                int y = sc.nextInt();
                int z = sc.nextInt() + Environment.worldOffset;
                environment.updateHighestXYZ(x,y,z);
                if(ID.equals("X")) Xlist.add(new xyzStore(x,y,z));
                else environment.getCrowd().getAgentByID(ID).setTarget(x,y,z);
            }
            for (Agent A : environment.getCrowd().getCrowdList()){
                if(!A.hasTarget()){
                    A.setTarget(Xlist.get(0).getX(),Xlist.get(0).getY(),Xlist.get(0).getZ());
                    Xlist.remove(0);
                    if(Xlist.size()==0){
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    private void readObstacleConfig(String ObstacleFile){
        try {
            br = new BufferedReader(fr = new FileReader(ObstacleFile));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                sc = new Scanner(currentLine).useDelimiter("\\s*,\\s*");
                String ID = sc.next();
                int x = sc.nextInt() + Environment.worldOffset;
                int y = sc.nextInt();
                int z = sc.nextInt() + Environment.worldOffset;
                environment.updateHighestXYZ(x,y,z);
                environment.addObstacle(ID,x,y,z);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class xyzStore{
        int x;
        int y;
        int z;
        public xyzStore(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void setX(int x) {
            this.x = x;
        }
        public void setY(int y) {
            this.y = y;
        }
        public void setZ(int z) {
            this.z = z;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public int getZ() {
            return z;
        }
    }
}
