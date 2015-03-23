package utils;

public class GraphObject {
	public String label;
	public String[] values = new String[1];
	
	public GraphObject(String label,String values) {
		this.label = label;
		this.values[0] = values;
	}
}
