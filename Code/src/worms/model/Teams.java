package worms.model;

import java.util.ArrayList;

public class Teams {

	private String teamName;
	
	public ArrayList<String> teamCollection;
	
	public void setTeamName(String newName) {
		if (!Worm.isValidName(newName))
			throw new ModelException("Invalid name specified.");
		teamName = newName;
	}
	
	public String getTeamNameAt(int index) {
		return teamCollection.get(index);
	}
	
	public ArrayList<String> returnAllTeams() {
		return teamCollection;
	}
	
	public void addTeam(String newName) {
		setTeamName(newName);
		teamCollection.add(teamName);
	}
}
