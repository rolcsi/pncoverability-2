package pncoverability;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class State {

	private List<State> parents;
	private Places places;

	public State() {
		this.places = new Places();
		this.parents = new LinkedList<State>();
	}

	public State(State state) {
		this.places = new Places();
		this.parents = new LinkedList<State>();
		
		for (Place place : state.places.getPlaces()) {
			places.addPlace(new Place(place));
		}
	}
	
	public Places getStatePlaces(){
		return places;
	}

	
	public State fireTransition(Transition t) {
		State next = new State(this);

		for (Entry<Integer, Integer> entry : t.getIn().entrySet()) {
			int placeId = entry.getKey();
			int multi = entry.getValue();
			Place actual = places.getPlaceById(placeId);
			//System.out.println("place id" + placeId);
			if (actual.getTokens() < multi) return null;
			//System.out.println("decrease from: " + next.places.getPlaceById(placeId).getTokens()+" minus "+multi);
			next.places.getPlaceById(placeId).setTokens(next.places.getPlaceById(placeId).getTokens()-multi);
		}

		for (Entry<Integer, Integer> entry : t.getOut().entrySet()) {
			int placeId = entry.getKey();
			int multi = entry.getValue();
			//System.out.println("place id" + placeId);
			//System.out.println(" increase to: " + next.places.getPlaceById(placeId).getTokens()+" plus "+multi);
			next.places.getPlaceById(placeId).setTokens(next.places.getPlaceById(placeId).getTokens()+multi);
		}
		
		next.parents.add(this);
		next.parents.addAll(parents);
		return next;
	}

	public boolean isBiggerThan(State state) {
		for (Place place : places.getPlaces()) {
			Place small = state.getStatePlaces().getPlaceById(place.getId());
			if (place.getTokens() < small.getTokens())
				return false;
		}
		
		for (Place place : places.getPlaces()) {
			Place small = state.getStatePlaces().getPlaceById(place.getId());
			if(place.getTokens() > small.getTokens()) return true;
			
		}
		return false;
	}

	public boolean isBiggerThanOneOfParents() {
		for (State state : parents) {
			if (this.isBiggerThan(state))
				return true;
		}
		return false;
	}
	
	public boolean isSameAs(State s) {
		for (Place place : s.places.getPlaces()) {
			if (places.getPlaceById(place.getId()).getTokens() != place.getTokens())
				return false;
		}
		return true;
	} 
	
	public int getTokensByPlace(int id) {
		for(Place temp: places.getPlaces()){
			if(temp.getId()==id) return temp.getTokens();
		}
		return -1;
	}
	
	public void printPlaces() {
		places.printPlaces();
	}
	
	public int[] getPlacesVec(){
		int[] pole = new int[3];
		int i = 0;
		for (Place place : this.places.getPlaces()) {
			pole[i] = place.getTokens();
			i++;
		}
		return pole;
	}
}
