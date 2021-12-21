CREATE TABLE Person(
svnr INTEGER PRIMARY KEY,
nachname VARCHAR(20) NOT NULL,
vorname VARCHAR(20) NOT NULL,
geburtsdatum DATE NOT NULL,
iban VARCHAR(30) UNIQUE NOT NULL
);

CREATE TABLE Filiale(
strasse VARCHAR(30),
ort VARCHAR(30),
plz CHAR(7),
name VARCHAR(15),
grundflaeche CHAR(3),
PRIMARY KEY(strasse,ort,plz)
);

CREATE TABLE Raum(
raumnummer INTEGER,
strasse VARCHAR(30),
ort VARCHAR(30),
plz CHAR(7),
name VARCHAR(15),
raumaustattung VARCHAR(20),
PRIMARY KEY(raumnummer,strasse,ort,plz),
FOREIGN KEY(strasse, ort, plz) REFERENCES Filiale(strasse, ort, plz) ON DELETE CASCADE
);

CREATE TABLE Fitnessgeraet(
bezeichnung VARCHAR(5) PRIMARY KEY,
muskelgruppe VARCHAR(25),
typ VARCHAR(10),
strasse VARCHAR(30),
ort VARCHAR(30),
plz CHAR(7),
FOREIGN KEY(strasse, ort, plz) REFERENCES Filiale(strasse, ort, plz) ON DELETE CASCADE
);

CREATE TABLE Mitarbeiter(
svnr INTEGER PRIMARY KEY, 
stunden NUMBER,
gehalt INTEGER CHECK(gehalt >900),
qualifikation VARCHAR(30),
strasse VARCHAR(30),
ort VARCHAR(30),
plz CHAR(7),
FOREIGN KEY(svnr) REFERENCES Person(svnr) ON DELETE CASCADE,
FOREIGN KEY(strasse,ort,plz) REFERENCES Filiale(strasse,ort,plz) ON DELETE CASCADE
);

CREATE TABLE schult(
mitarberiter_svnr INTEGER,
svnr INTEGER PRIMARY KEY,
FOREIGN KEY(mitarberiter_svnr) REFERENCES Mitarbeiter(svnr) ON DELETE CASCADE,
FOREIGN KEY(svnr) REFERENCES Mitarbeiter(svnr) ON DELETE CASCADE
);

CREATE TABLE Mitglied(
svnr INTEGER PRIMARY KEY, 
abo VARCHAR(10),
gebuehr Integer,
FOREIGN KEY(svnr) REFERENCES Person(svnr) ON DELETE CASCADE
);

CREATE TABLE trainiert(
mitarbeiter_svnr INTEGER, 
mitglied_svnr INTEGER, 
PRIMARY KEY(mitarbeiter_svnr, mitglied_svnr),
FOREIGN KEY(mitarbeiter_svnr) REFERENCES Mitarbeiter(svnr) ON DELETE CASCADE,
FOREIGN KEY(mitglied_svnr) REFERENCES Mitglied(svnr) ON DELETE CASCADE
);

CREATE TABLE besucht(
mitglied_svnr INTEGER, 
strasse VARCHAR(30),
ort VARCHAR(30),
plz CHAR(7),
PRIMARY KEY(mitglied_svnr,strasse, ort, plz),
FOREIGN KEY(mitglied_svnr) REFERENCES Mitglied(svnr) ON DELETE CASCADE,
FOREIGN KEY(strasse, ort, plz) REFERENCES Filiale(strasse, ort, plz) ON DELETE CASCADE
);

CREATE TABLE Produkt(
seriennummer INTEGER PRIMARY KEY,
marke VARCHAR(15),
typ VARCHAR(20),
preis INTEGER
);


CREATE TABLE verkauft(
mitarbeiter_svnr INTEGER,
seriennummer INTEGER,
mitglied_svnr INTEGER,
PRIMARY KEY (seriennummer),
FOREIGN KEY(mitglied_svnr) REFERENCES Mitglied(svnr) ON DELETE CASCADE,
FOREIGN KEY(seriennummer) REFERENCES Produkt(seriennummer) ON DELETE CASCADE,
FOREIGN KEY(mitarbeiter_svnr) REFERENCES Mitarbeiter(svnr) ON DELETE CASCADE
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