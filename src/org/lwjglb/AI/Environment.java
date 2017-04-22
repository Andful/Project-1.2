package org.lwjglb.AI;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

public class Environment {

	private Vector3f environmentSize;
	private List<Vector3f> obstaclesPositions;
	private List<Vector3f> agentStartConfigurations;
	private List<Vector3f> agentEndConfigurations;
	
	public Environment(File environment, File obstacles, File configurations) throws IOException{
		
		try(DataInputStream in = new DataInputStream(new FileInputStream(environment))) {
			
			environmentSize = new Vector3f(in.readFloat(), in.readFloat(), in.readFloat());
			
		}
		try(DataInputStream in = new DataInputStream(new FileInputStream(obstacles))){
			final int size=in.readInt();
			obstaclesPositions = new ArrayList<Vector3f>(size);
			for(int i=0;i<size;i++)
			{
				obstaclesPositions.add(new Vector3f(in.readFloat(),in.readFloat(),in.readFloat()));
			}
			
		}
		try(DataInputStream in = new DataInputStream(new FileInputStream(configurations)))
		{
			int sizeStartConf = in.readInt();
			agentStartConfigurations = new ArrayList<Vector3f>(sizeStartConf);
			for(int i=0; i<sizeStartConf;i++)
			{
				agentEndConfigurations.add(new Vector3f(in.readFloat(),in.readFloat(),in.readFloat()));
			}

			int sizeEndConf = in.readInt();
			agentEndConfigurations = new ArrayList<Vector3f>(sizeEndConf);
			for(int i=0; i<sizeEndConf;i++){

				agentEndConfigurations.add(new Vector3f(in.readFloat(),in.readFloat(),in.readFloat()));

			}

		}
		
	}
	
	public Environment(Vector3f environmentSize, List<Vector3f> obstaclesPositions, List<Vector3f> agentStartConfigurations, List<Vector3f> agentEndConfigurations){
		this.environmentSize = environmentSize;
		this.obstaclesPositions = obstaclesPositions;
		this.agentEndConfigurations = agentEndConfigurations;
		this.agentStartConfigurations = agentStartConfigurations;
	}

	public void environmentToFile(File environment, File obstacles, File configurations) throws IOException{

		try(DataOutputStream out = new DataOutputStream(new FileOutputStream(environment))){

			out.writeFloat(environmentSize.x);
			out.writeFloat(environmentSize.y);
			out.writeFloat(environmentSize.z);
		}
		try(DataOutputStream out = new DataOutputStream(new FileOutputStream(obstacles))){

			out.writeInt(obstaclesPositions.size());

			for(Vector3f vec:obstaclesPositions)
			{
				out.writeFloat(vec.x);
				out.writeFloat(vec.y);
				out.writeFloat(vec.z);
			}
		}
		try(DataOutputStream out = new DataOutputStream(new FileOutputStream(configurations)))
		{

			out.writeInt(agentStartConfigurations.size());

			for(Vector3f vec:agentStartConfigurations){
				out.writeFloat(vec.x);
				out.writeFloat(vec.y);
				out.writeFloat(vec.z);
			}

			out.writeInt(agentEndConfigurations.size());

			for(Vector3f vec:agentEndConfigurations){
				out.writeFloat(vec.x);
				out.writeFloat(vec.y);
				out.writeFloat(vec.z);
			}

		}
	}
	
	public static void main(String[] args)
	{
		List a=new ArrayList<Vector3f>(5);
		System.out.println(a.size());
	}
}
