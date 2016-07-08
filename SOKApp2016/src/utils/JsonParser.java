package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {
	
	/**
	 * Method is used to extract option names for json cinfiguration file of source plugin. 
	 * @param path        is path to json configuration file.
	 * @param sourceName  
	 * @return {@link ArrayList} 
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static ArrayList<String> getFeatures(String path, String sourceName) throws JsonProcessingException, IOException{
		ArrayList<String> retList = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(new File(path));
		
		JsonNode sourceNode = root.path(sourceName);
		
		JsonNode featuresNames = sourceNode.path("featuresNames");
		
		if (featuresNames.isArray()) {
		    for (final JsonNode objNode : featuresNames) {
		    	retList.add(objNode.toString().substring(1, objNode.toString().length()-1));
		    	System.out.println("IZ PETLJE: " + objNode);
		    }
		}
		//retList = (ArrayList<String>)sourceNode.findValuesAsText("featuresNames");
		return retList;
	}
	
	/**
	 * Method is used to extract option names for json cinfiguration file of source plugin. 
	 * @param path        is path to json configuration file.
	 * @param sourceName  
	 * @return {@link ArrayList} 
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static ArrayList<String> getValues(File file, String sourceName, String nodeArrayName) throws JsonProcessingException, IOException{
		ArrayList<String> retList = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(file);
		//JsonNode root = mapper.readTree(new File(context.getBundle().getResource("/myfile")));
		
		JsonNode sourceNode = root.path(sourceName);
		
		JsonNode featuresNames = sourceNode.path(nodeArrayName);
		
		if (featuresNames.isArray()) {
		    for (final JsonNode objNode : featuresNames) {
		    	retList.add(objNode.toString().substring(1, objNode.toString().length()-1));
		    	System.out.println("IZ PETLJE: " + objNode);
		    }
		}
		//retList = (ArrayList<String>)sourceNode.findValuesAsText("featuresNames");
		return retList;
	}

}
