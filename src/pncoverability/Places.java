package pncoverability;

import java.util.LinkedList;

public class Places {
	
	private LinkedList<Place> places;
	
	public Places() {
		this.places = new LinkedList<Place>(); 
	}
	
	public LinkedList<Place> getPlaces() {
		return places;
	}
	
	public void setPlaces(LinkedList<Place> places) {
		this.places = places;
	}
	
	public void addPlace(Place place){
		this.places.add(place);
	}
	
	public Place getPlaceById(int id) {
		for (Place place : places) {
			if (place.getId() == id)
				return place;
		}
		return null;
	}
	
	public int countPlaces(){
		return places.size();
	}
	
	public void printPlaces(){
		for (Place p : places){
			System.out.println("Miesto ID: " + p.getId() + ", meno: " + p.getLabel() + ", tokeny: " + p.getTokens());
		}
	}
}
