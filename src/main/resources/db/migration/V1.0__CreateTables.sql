CREATE TABLE player(
 id serial PRIMARY KEY,
 name VARCHAR (255) NOT NULL UNIQUE,
 type VARCHAR (255) NOT NUll
);


CREATE TABLE game_result(
 id serial PRIMARY KEY,
 winner INTEGER REFERENCES player(id),
 numberOfRounds INT NOT NULL 
); 


CREATE TABLE participation(
 id serial PRIMARY KEY,
 roundsWon INTEGER NOT NULL,
 playerId INTEGER REFERENCES player(id) NOT NULL,
 gameId INTEGER REFERENCES game_result(id) NOT NULL
);