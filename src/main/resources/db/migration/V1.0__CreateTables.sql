CREATE TABLE game_result(
 id serial PRIMARY KEY,
 winner VARCHAR (50)   NOT NULL,
 numberOfRounds INT NOT NULL,
 statistics VARCHAR (355) UNIQUE NOT NULL
);