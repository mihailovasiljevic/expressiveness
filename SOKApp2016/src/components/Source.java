package components;

import java.util.ArrayList;
import java.util.HashMap;

import graph.models.GraphModel;

public interface Source {

	public static final String NAME = "source.name";
	
	public ConfigSourceReq getConfigRequirments();
	public ArrayList<String> setConfig(ConfigSource source);
	public GraphModel getGraph();
	/**prvi argument je naziv featurs-a po kojem se filtrira/pretrazuje, 
	 * a drugi je da li se radi o initgeru ili stringu. 
	 * Za drugi argument moguce vrednosti su: String ili Integer
	 */
	public HashMap<String,String> getFeaturesForFilteringAndSearching();
	
}
