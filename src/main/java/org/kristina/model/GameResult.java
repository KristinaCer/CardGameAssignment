package org.kristina.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
 

public class GameResult {

	public GameResult() {}
	
	public GameResult(Player winner, int numberOfRounds, Collection<Participant> participants) {
		this.winner = winner;
		this.numberOfRounds = numberOfRounds; 
		this.participants = participants;
	}
	
    public int id;
    public Player winner ;
    public int numberOfRounds; 
    public Collection<Participant> participants;
    
	@Override
	public String toString() {
		return "GameResult [id=" + id + ", winner=" + winner + ", numberOfRounds=" + numberOfRounds + ", participants="
				+ participants + "]";
	} 
	
}
