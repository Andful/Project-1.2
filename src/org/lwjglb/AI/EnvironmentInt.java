package org.lwjglb.AI;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.joml.Vector3i;

public class EnvironmentInt {

    private Vector3i environmentSize;
    private List<Vector3i> obstaclesPositions;
    private List<Vector3i> agentStartConfigurations;
    private List<Vector3i> agentEndConfigurations;

    public EnvironmentInt(File environment, File obstacles, File configurations) throws IOException{

        try(DataInputStream in = new DataInputStream(new FileInputStream(environment))) {

            environmentSize = new Vector3i(in.readInt(), in.readInt(), in.readInt());

        }
        try(DataInputStream in = new DataInputStream(new FileInputStream(obstacles))){
            final int size=in.readInt();
            obstaclesPositions = new ArrayList<Vector3i>(size);
            for(int i=0;i<size;i++)
            {
                obstaclesPositions.add(new Vector3i(in.readInt(),in.readInt(),in.readInt()));
            }

        }
        try(DataInputStream in = new DataInputStream(new FileInputStream(configurations)))
        {
            int sizeStartConf = in.readInt();
            agentStartConfigurations = new ArrayList<Vector3i>(sizeStartConf);
            for(int i=0; i<sizeStartConf;i++)
            {
                agentStartConfigurations.add(new Vector3i(in.readInt(),in.readInt(),in.readInt()));
            }

            int sizeEndConf = in.readInt();
            agentEndConfigurations = new ArrayList<Vector3i>(sizeEndConf);
            for(int i=0; i<sizeEndConf;i++){

                agentEndConfigurations.add(new Vector3i(in.readInt(),in.readInt(),in.readInt()));

            }

        }

    }
    public EnvironmentInt(File directory) throws IOException
    {
        this(new File(directory.toString() + File.separator + "enviroment size.project"),
                new File(directory.toString() + File.separator + "obstacles.project"),
                new File(directory.toString() + File.separator + "configuration.project"));
    }
    public EnvironmentInt(Vector3i environmentSize, List<Vector3i> obstaclesPositions, List<Vector3i> agentStartConfigurations, List<Vector3i> agentEndConfigurations){
        this.environmentSize = environmentSize;
        this.obstaclesPositions = obstaclesPositions;
        this.agentEndConfigurations = agentEndConfigurations;
        this.agentStartConfigurations = agentStartConfigurations;
    }

    public void environmentToFile(File environment, File obstacles, File configurations) throws IOException{

        try(DataOutputStream out = new DataOutputStream(new FileOutputStream(environment))){

            out.writeInt(environmentSize.x);
            out.writeInt(environmentSize.y);
            out.writeInt(environmentSize.z);
        }
        try(DataOutputStream out = new DataOutputStream(new FileOutputStream(obstacles))){

            out.writeInt(obstaclesPositions.size());

            for(Vector3i vec:obstaclesPositions)
            {
                out.writeInt(vec.x);
                out.writeInt(vec.y);
                out.writeInt(vec.z);
            }
        }
        try(DataOutputStream out = new DataOutputStream(new FileOutputStream(configurations)))
        {

            out.writeInt(agentStartConfigurations.size());

            for(Vector3i vec:agentStartConfigurations){
                out.writeInt(vec.x);
                out.writeInt(vec.y);
                out.writeInt(vec.z);
            }

            out.writeInt(agentEndConfigurations.size());

            for(Vector3i vec:agentEndConfigurations){
                out.writeInt(vec.x);
                out.writeInt(vec.y);
                out.writeInt(vec.z);
            }

        }
    }
    public static void main(String[] args)
    {
        //testWrite(args);
        testRead(args);
    }
    public static void testWrite(String[] args)
    {
        Vector3i enviromentSize=new Vector3i(4,5,6);
        List<Vector3i> obstacles=new ArrayList();
        obstacles.add(new Vector3i(1,2,3));
        obstacles.add(new Vector3i(4,5,6));
        List<Vector3i> start=new ArrayList<>();
        start.add(new Vector3i(7,8,9));
        start.add(new Vector3i(10,11,12));
        List<Vector3i> end=new ArrayList<>();
        end.add(new Vector3i(13,14,15));
        end.add(new Vector3i(16,17,18));
        EnvironmentInt env=new EnvironmentInt(enviromentSize,obstacles,start,end);
        try
        {
            env.environmentToFile(new File("C:\\Users\\Andrea Nardi\\Desktop\\test\\a"),
                    new File("C:\\Users\\Andrea Nardi\\Desktop\\test\\b"),
                    new File("C:\\Users\\Andrea Nardi\\Desktop\\test\\c"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public Vector3i getEnvironmentSize()
    {
        return environmentSize;
    }
    public List<Vector3i> getObstaclesPositions()
    {
        return obstaclesPositions;
    }
    public List<Vector3i> getAgentStartConfigurations()
    {
        return agentStartConfigurations;
    }
    public List<Vector3i> getAgentEndConfigurations()
    {
        return agentEndConfigurations;
    }
    public static void testRead(String[] args){
    }
}
