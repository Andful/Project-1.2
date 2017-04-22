package org.lwjglb.AI;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

public class Environment {

	private Vector3f environmentSize;
	private List<Vector3f> obstaclesPositions;
	private List<Vector3f> agentStartConfigurations;
	private List<Vector3f> agentEndConfigurations;
	
	public Environment(File environment, File obstacles, File configurations) throws Exception{
		
		try(DataInputStream in = new DataInputStream(new FileInputStream(environment))) {
			
			environmentSize = new Vector3f(in.readFloat(), in.readFloat(), in.readFloat());
			
		}
		
		try(DataInputStream in = new DataInputStream(new FileInputStream(obstacles))){
			
			obstaclesPositions = new ArrayList<Vector3f>(in.readInt());
			
		}
		
	}
	
	public Environment(Vector3f environmentSize, List<Vector3f> obstaclesPositions, List<Vector3f> agentStartConfigurations, List<Vector3f> agentEndConfigurations){
		this.environmentSize = environmentSize;
		this.obstaclesPositions = obstaclesPositions;
		this.agentEndConfigurations = agentEndConfigurations;
		this.agentStartConfigurations = agentStartConfigurations;
	}
	
	public static void main(String[] args)
	{
		List a=new ArrayList<Vector3f>(5);
		System.out.println(a.size());
	}
}
