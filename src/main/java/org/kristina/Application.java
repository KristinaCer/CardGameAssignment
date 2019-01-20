package org.kristina;

import org.flywaydb.core.Flyway;

import java.sql.SQLException;

public class Application {

    public static void main(String... args) throws Exception { 
        String url = "jdbc:postgresql://manny.db.elephantsql.com:5432/onkrajux";
        String user = "onkrajux";
        String password = "toc8a9gWmmZO41dnETiNFS6Ju98QGKzq";
 

        DbDriver driver = new DbDriver(user, password, url );
        Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
        flyway.migrate();
    }

}