package components;

import java.util.ArrayList;

public class ConfigSourceReq {
	
	private ArrayList<String> config = new ArrayList<String>();
	private String main;  //jedna vrednost iz configa po kojoj tab i cvor u stablu dobijaju vrednost
	
	public ConfigSourceReq() {
		
	}
	
	public ConfigSourceReq(ArrayList<String> config) {
		this.config = config;
	}

	public ArrayList<String> getConfig() {
		return config;
	}

	public void setConfig(ArrayList<String> config) {
		this.config = config;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}
	
	
}
