package org.kristina;

import org.flywaydb.core.Flyway;
import org.kristina.model.GameResult;
import org.kristina.model.PlayerType;
import org.kristina.repository.GameResultRepository;

import java.sql.SQLException;

public class Application {

    public static void main(String... args) throws Exception { 
        String url = "jdbc:postgresql://manny.db.elephantsql.com:5432/onkrajux";
        String user = "onkrajux";
        String password = "toc8a9gWmmZO41dnETiNFS6Ju98QGKzq";
 

        DbDriver driver = new DbDriver(user, password, url );
        Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
        flyway.migrate(); 
        
        GameResultRepository repo = new GameResultRepository(driver);
        
        //repo.save(new GameResult(PlayerType.human, 3, new int[] {2,1}));
        GameResult result = repo.get(1); 
    }

}