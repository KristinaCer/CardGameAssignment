package org.kristina;

import org.flywaydb.core.Flyway;
import org.kristina.model.GameResult;
import org.kristina.model.Participant;
import org.kristina.model.Player;
import org.kristina.model.PlayerType;
import org.kristina.repository.GameResultRepository;
import org.kristina.repository.PlayerRepository;

import java.sql.SQLException;
import java.util.Arrays;

public class Application {

    public static void main(String... args) throws Exception { 
        String url = "jdbc:postgresql://manny.db.elephantsql.com:5432/onkrajux";
        String user = "onkrajux";
        String password = "toc8a9gWmmZO41dnETiNFS6Ju98QGKzq";
 

        DbDriver driver = new DbDriver(user, password, url );
        Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
        flyway.migrate(); 
        
       // GameResultRepository repo = new GameResultRepository(driver);
        
        //repo.save(new GameResult(PlayerType.human, 3, new int[] {2,1}));
        //GameResult result = repo.get(1);  
        
        PlayerRepository playerRepository = new PlayerRepository(driver);
        GameResultRepository gameRepository = new GameResultRepository(driver);
        
        Player player1 = playerRepository.save(new Player("Alex", PlayerType.human));
        Player player2 = playerRepository.save(new Player("John", PlayerType.ai));
        Player player3 = playerRepository.save(new Player("Larissa", PlayerType.ai));
        
        GameResult game1 = new GameResult(player1, 5, 
        		Arrays.asList(new Participant(player1,3), new Participant(player2,2)));

        GameResult game2 = new GameResult(player3, 3, 
        		Arrays.asList(new Participant(player1,0), new Participant(player3,3)));

        GameResult game3 = new GameResult(null, 9, 
        		Arrays.asList(new Participant(player1,3), new Participant(player3,3), new Participant(player2, 3)));

        GameResult game4 = new GameResult(player2, 3, 
        		Arrays.asList(new Participant(player1,0), new Participant(player2,3)));
        
        gameRepository.save(game1);
        gameRepository.save(game2);
        gameRepository.save(game3);
        gameRepository.save(game4);
        
        
        System.out.println("Largest number of rounds: "+gameRepository.getLargestNumberOfRounds());
        System.out.println("Number of draws: "+gameRepository.getNumberOfDraws());
        System.out.println("AI wins: "+gameRepository.getWinsByPlayerType(PlayerType.ai));
        System.out.println("Human wins: "+gameRepository.getWinsByPlayerType(PlayerType.human));
        System.out.println("Total games: "+gameRepository.totalGames());
    }
   
}