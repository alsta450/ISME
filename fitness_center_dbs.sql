CREATE TABLE Person(
svnr INTEGER PRIMARY KEY,
lastname VARCHAR(20) NOT NULL,
firstname VARCHAR(20) NOT NULL,
birthday DATE NOT NULL,
iban VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE Branch(
street VARCHAR(30),
city VARCHAR(30),
zip CHAR(4),
name VARCHAR(15),
area VARCHAR(10),
PRIMARY KEY(street,city,zip)
);

CREATE TABLE Room(
roomNumber INTEGER,
street VARCHAR(30),
city VARCHAR(30),
zip CHAR(4),
name VARCHAR(15),
interior VARCHAR(20),
PRIMARY KEY(roomNumber,street,city,zip),
FOREIGN KEY(street, city, zip) REFERENCES Branch(street, city, zip) ON DELETE CASCADE
);

CREATE TABLE FitnessEquipment(
description VARCHAR(5) PRIMARY KEY,
muscleGroup VARCHAR(25),
fitnessType VARCHAR(10),
street VARCHAR(30),
city VARCHAR(30),
zip CHAR(4),
FOREIGN KEY(street, city, zip) REFERENCES Branch(street, city, zip) ON DELETE CASCADE
);

CREATE TABLE Employee(
svnr INTEGER PRIMARY KEY, 
hours NUMBER,
wage INTEGER CHECK(wage >900),
qualification VARCHAR(30),
street VARCHAR(30),
city VARCHAR(30),
zip CHAR(4),
FOREIGN KEY(svnr) REFERENCES Person(svnr) ON DELETE CASCADE,
FOREIGN KEY(street, city, zip) REFERENCES Branch(street, city, zip) ON DELETE CASCADE
);

CREATE TABLE tutor(
employee_svnr INTEGER,
svnr INTEGER PRIMARY KEY,
FOREIGN KEY(employee_svnr) REFERENCES Employee(svnr) ON DELETE CASCADE,
FOREIGN KEY(svnr) REFERENCES Employee(svnr) ON DELETE CASCADE
);

CREATE TABLE Member(
svnr INTEGER PRIMARY KEY, 
abo VARCHAR(10),
fee Integer,
FOREIGN KEY(svnr) REFERENCES Person(svnr) ON DELETE CASCADE
);

CREATE TABLE TrainingSession(
trainings_id INTEGER PRIMARY KEY,
price INTEGER NOT NULL,
duration INTEGER NOT NULL,
employee_svnr INTEGER, 
member_svnr INTEGER, 
FOREIGN KEY(employee_svnr) REFERENCES Employee(svnr) ON DELETE CASCADE,
FOREIGN KEY(member_svnr) REFERENCES Member(svnr) ON DELETE CASCADE

);

CREATE TABLE visit(
member_svnr INTEGER, 
street VARCHAR(30),
city VARCHAR(30),
zip CHAR(7),
PRIMARY KEY(member_svnr,street, city, zip),
FOREIGN KEY(member_svnr) REFERENCES Member(svnr) ON DELETE CASCADE,
FOREIGN KEY(street, city, zip) REFERENCES Branch(street, city, zip) ON DELETE CASCADE
);

CREATE SEQUENCE PRODUKT_SEQ START WITH 123456780;

CREATE OR REPLACE TRIGGER PRODUKT_TRI 
BEFORE INSERT ON produkt 
FOR EACH ROW

BEGIN
  SELECT PRODUKT_SEQ.NEXTVAL
  INTO   :new.seriennummer
  FROM   dual;
END;
/

CREATE OR REPLACE PROCEDURE p_delete_person(
   p_svnr IN person.svnr%TYPE,
   p_error_code OUT NUMBER
)
AS
  BEGIN
    DELETE
    FROM person
    WHERE p_svnr = person.svnr;
 
    p_error_code := SQL%ROWCOUNT;
    IF (p_error_code = 1)
    THEN
      COMMIT;
    ELSE
      ROLLBACK;
    END IF;
    EXCEPTION
    WHEN OTHERS
    THEN
      p_error_code := SQLCODE;
  END p_delete_person;
/

-- Es werden die Mitarbeiter angezeigt, die mehr als 9 Produkte verkauft haben. Absteigend nach top(COUNT(seriennummer)) sortiert
CREATE OR REPLACE VIEW top_seller AS
SELECT svnr,vorname,nachname, COUNT(seriennummer) AS top
FROM Person NATURAL JOIN Mitarbeiter
NATURAL JOIN verkauft
WHERE svnr = mitarbeiter_svnr
GROUP BY svnr,vorname,nachname
HAVING COUNT(seriennummer)>9
Order by top DESC;