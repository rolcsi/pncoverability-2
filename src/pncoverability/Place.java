package pncoverability;

public class Place {
	
	private int id, tokens;
	private String label;
	
	public Place(int id, int tokens, String label) {
		this.id = id;
		this.tokens = tokens;
		this.label = label;
	}
	
	public Place(Place place) {
		this.id = place.getId();
		this.tokens = place.getTokens(); 
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setTokens(int tokens) {
		this.tokens = tokens;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	
	public int getTokens() {
		return tokens;
	}
	
	public String getLabel() {
		return label;
	}
}
