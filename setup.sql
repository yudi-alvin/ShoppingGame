
create schema cookingDb;
use cookingDb;

#--------------------------------------------------------------------------------------------------------
CREATE TABLE member (
    username VARCHAR(20) NOT NULL,
    pwd text NOT NULL,
    fullname VARCHAR(20) NOT NULL,
    balance DOUBLE NOT NULL,
    XP INT NOT NULL,
    CONSTRAINT member_pk1 PRIMARY KEY (username)
);

CREATE TABLE stove (
    stoveID INT NOT NULL,
    memberUsername VARCHAR(20) NOT NULL,
    starttime bigint,
    cookingTime int,
    eaten boolean DEFAULT false,
    dishname VARCHAR(20) NOT NULL,
    CONSTRAINT stove_pk PRIMARY KEY (stoveID , memberUsername),
    CONSTRAINT stove_fk FOREIGN KEY (memberUsername)
        REFERENCES member (username)
);


CREATE TABLE recipe (
    dishname VARCHAR(20) NOT NULL,
    ingredient1 VARCHAR(20) NOT NULL,
    ingredient2 VARCHAR(20) NOT NULL,
    ingredient3 VARCHAR(20) NOT NULL,
    ingredient4 VARCHAR(20) NOT NULL,
    cookingtime INT NOT NULL,
    XP INT NOT NULL,
    moneyearned DOUBLE NOT NULL,
    CONSTRAINT recipe_pk PRIMARY KEY (dishname)
);

CREATE TABLE ingredient (
    ingredientName VARCHAR(20) NOT NULL,
    price DOUBLE NOT NULL,
    CONSTRAINT ingredient_pk PRIMARY KEY (ingredientName)
);

CREATE TABLE inventory (
    memberUsername VARCHAR(20) NOT NULL,
    ingredientName VARCHAR(20) NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT inventory_fk1 FOREIGN KEY (memberUsername)
        REFERENCES member (username),
    CONSTRAINT inventory_fk2 FOREIGN KEY (ingredientName)
        REFERENCES ingredient (ingredientName)
);

CREATE TABLE memberrecipe (
    memberUsername VARCHAR(20) NOT NULL,
    dishname VARCHAR(20) NOT NULL,
    CONSTRAINT memberrecipe_fk1 FOREIGN KEY (memberUsername)
        REFERENCES member (username),
    CONSTRAINT memberrecipe_fk2 FOREIGN KEY (dishname)
        REFERENCES recipe (dishname)
);

CREATE TABLE Rank (
    title VARCHAR(20) NOT NULL,
    XP INT NOT NULL,
    CONSTRAINT rank_pk PRIMARY KEY (title)
);


#--------------------------------------------------------------------------------------------------------

#please insert your own classpath accordingly.

LOAD DATA local infile '<CLASSPATH>/data/ingredient.csv' 
INTO TABLE ingredient fields TERMINATED BY ',' LINES TERMINATED BY '\r\n' IGNORE 1 LINES;

LOAD DATA local infile '<CLASSPATH>/data/recipe.csv' 
INTO TABLE recipe fields TERMINATED BY ',' LINES TERMINATED BY '\r\n' IGNORE 1 LINES;

LOAD DATA local infile '<CLASSPATH>/data/title.csv' 
INTO TABLE rank fields TERMINATED BY ',' LINES TERMINATED BY '\r\n' IGNORE 1 LINES;


#--------------------------------------------------------------------------------------------------------
# DATA TO POPULATE DATABASE

#pwd is 123 for all members, the database stores a salted and hashed pwd 

INSERT INTO member(username, pwd, fullname, balance, XP) VALUES
('player1','p4CT51AAMfOhh7SUV9/ClinGyJ8td4xc6EBQaK3gcPo=$m3Mn91m26xcXxwS104vU0SsFOz0YnFzVdFJX2/aMvpc=', 'player1 Goh',100, 10999), 
('player2','YDtojp+dw1xYkOv21RBVD5whMQ6v1A5XamrNByGdyLY=$+buNCfvEvGqeMNo1URJgMAmq5xSfgs8GGHb2LJvNCfo=', 'Goh Yu Xin',100, 5000), 
('player3','UxAHscymqz8Ezkdf17pzTk2t53Z7o0qyrzRhPIIT8s4=$IzttwJ2sbVfN2osGNmxuK90AJVZ5aIGtk4VhPrrIL3I=', 'Jiang player3',100, 300),
('hackerman','dNmArznddykQpWpjeXEzoEiOiwZH5vCr23o7A6RDX2E=$ufkXYFlM5WOOjyt1UnCbX6FsdOCwmConWzHea3MNZV4=', 'hackerman Alvin',100, 0);

INSERT INTO inventory (memberUsername, ingredientName, quantity) VALUES
('player1','beef', 12), ('player1','egg', 5), 
('player2','onion', 7), ('player2','coconut', 7), 
('player3','feather', 20), ('player3','coconut', 4), ('player3','chicken', 5),
('hackerman','beef', 3), ('hackerman','chicken', 5), ('hackerman','feather', 20);

INSERT INTO memberRecipe(memberUsername, dishname) VALUES
('player1', 'bak kut teh'), ('player1', 'steak'), 
('player2', 'lobster salad'), ('player2', 'burger & fries'), 
('player3', 'fluff'), ('player3', 'caesar salad'),
('hackerman', 'chicken korma'), ('hackerman', 'caesar salad');

INSERT INTO stove(stoveID, memberUsername, starttime, cookingtime, dishname) values
(1, 'player2', 0, 0, '- AVAILABLE -'), (2, 'player2', 0, 0, '- AVAILABLE -'), (3, 'player2', 0, 0, '- AVAILABLE -'),
(1, 'player1', 0, 0, '- AVAILABLE -'), (2, 'player1', 0, 0, '- AVAILABLE -'), (3, 'player1', 0, 0, '- AVAILABLE -'),
(1, 'player3', 0, 0, '- AVAILABLE -'), (2, 'player3', 0, 0, '- AVAILABLE -'), (3, 'player3', 0, 0, '- AVAILABLE -'),
(1, 'hackerman', 0, 0, '- AVAILABLE -'), (2, 'hackerman', 0, 0, '- AVAILABLE -'), (3, 'hackerman', 0, 0, '- AVAILABLE -'); 

