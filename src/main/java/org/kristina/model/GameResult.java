package org.kristina.model;


public class GameResult {

	public GameResult() {}
	
	public GameResult(PlayerType winner, int numberOfRounds, int[] winStatistics) {
		this.winner = winner;
		this.numberOfRounds = numberOfRounds;
		this.winStatistics = winStatistics;
	}
	
    public int id;
    public PlayerType winner ;
    public int numberOfRounds;
    public int[] winStatistics;  
    
    public String statisticsAsString() {
    	String result = "";
    	if (winStatistics == null) {
    		return result;
    	}
    	 
    	for (int val : winStatistics) {
    		result += val+" ";
    	} 
    	
    	return result.trim();
    }
    
}
