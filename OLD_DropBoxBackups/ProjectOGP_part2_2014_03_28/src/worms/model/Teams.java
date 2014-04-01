package worms.model;

import java.util.ArrayList;

public class Teams {

	Worm worm;
	
	private String teamName;
	
	public ArrayList<Teams> teamCollection;
	
	public void setTeamName(String newName) {
		if (!Worm.isValidName(newName))
			throw new ModelException("Invalid name specified.");
		teamName = newName;
	}
	
	public String getTeamName() {
		return teamName;
	}
}
