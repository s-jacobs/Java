DROP DATABASE IF EXISTS cockatoos ;

CREATE DATABASE cockatoos;

USE cockatoos;


CREATE TABLE customers (
    custID int NOT NULL AUTO_INCREMENT,
    lastName varchar(50) NOT NULL,
    firstName varchar(50),
    PRIMARY KEY (custID)
);

ALTER TABLE customers AUTO_INCREMENT = 1000;

INSERT INTO customers(lastName, firstName) VALUES ('Mouse','Mickey');
INSERT INTO customers(lastName, firstName) VALUES ('Duck','Donald');
INSERT INTO customers(lastName, firstName) VALUES ('Dog','Pluto');
INSERT INTO customers(lastName, firstName) VALUES ('Mouse','Minnie');


CREATE TABLE mall(
    mallName	VARCHAR(50) NOT NULL,
    mallID		INT	NOT NULL,
	PRIMARY KEY(mallID)
	);

INSERT INTO mall VALUES("Cockatoo's Shopping Mall",1);



CREATE TABLE storeType(
	storeType	VARCHAR(20) NOT NULL,
	storeTypeID	VARCHAR(10) NOT NULL,
	PRIMARY KEY(storeTypeID)
	);
	
INSERT INTO storeType VALUES('Books','B');
INSERT INTO storeType VALUES('Games','G');
	



CREATE TABLE store(
	storeName	VARCHAR(50) NOT NULL,
	storeID		VARCHAR(20) NOT NULL,
	storeTypeID	VARCHAR(10) NOT NULL,
	PRIMARY KEY(storeID),
	FOREIGN KEY(storeTypeID) REFERENCES storeType(storeTypeID)
	);

INSERT INTO store VALUES('Java Book Store','BS1','B');
INSERT INTO store VALUES('Java Game Store','GS1','G');
INSERT INTO store VALUES('Phoenix Books','BS2','B');
	
CREATE TABLE deletedItems(
    itemName	VARCHAR(50) NOT NULL,
    itemID	VARCHAR(50) NOT NULL,
    storeID	VARCHAR(20)	NOT NULL,
    price	DECIMAL(6,2),
    number	INT,
    PRIMARY KEY(itemID),
    FOREIGN KEY(storeID) REFERENCES store(storeID)
);

CREATE TABLE item(
	itemName	VARCHAR(50) NOT NULL,
	itemID		VARCHAR(50) NOT NULL,
	storeID		VARCHAR(20)	NOT NULL,
	price		DECIMAL(6,2),
	number         INT,
	PRIMARY KEY(itemID),
	FOREIGN KEY(storeID) REFERENCES store(storeID)
);
	
INSERT INTO item VALUES ('Java IS Fun','ISBN45','BS1',25,50);
INSERT INTO item VALUES ('Database for Everyone','ISBN65','BS1',82,30);
INSERT INTO item VALUES ('MySQL Rules','ISBN67','BS1',49,40);
INSERT INTO item VALUES ('Baseball for Dummies','ISBN76','BS1',79,50);
INSERT INTO item VALUES ('Bopit','G100','GS1',25,12);
INSERT INTO item VALUES ('ChutesNLadders','G110','GS1',15,40);
INSERT INTO item VALUES ('Barbie','G120','GS1',20,50);
INSERT INTO item VALUES ('MLB','G130','GS1',45,60);
INSERT INTO item VALUES ('Java Certification','ISBN988','BS2',31,40);
INSERT INTO item VALUES ('MySQL Database Certification','ISBN123','BS2',66,30);
INSERT INTO item VALUES ('MySQL for Dummies','ISBN455','BS2',82.56,23);
INSERT INTO item VALUES ('Server Side Java','ISBN833','BS2',73.54,50);

CREATE TABLE sale(
	saleID	INT NOT NULL,
	saleLineID INT NOT NULL AUTO_INCREMENT,
	custID	INT NOT NULL,
	saleDate	DATETIME,
        itemID VARCHAR(20) NOT NULL,
        numSold INT NOT NULL,
	PRIMARY KEY(saleLineID,saleID),
	FOREIGN KEY(custID) REFERENCES customers(custID)
	);
	
CREATE TABLE admin(
	adminID    INT NOT NULL AUTO_INCREMENT,
    user	VARCHAR(30) NOT NULL,
    pass	VARCHAR(30) NOT NULL,
    PRIMARY KEY(adminID)
);
ALTER TABLE admin AUTO_INCREMENT = 100;

INSERT INTO admin(user, pass) VALUES('user', 'pass');
INSERT INTO admin(user, pass) VALUES('admin', 'admin');






CREATE PROCEDURE printItems(IN storeParam VARCHAR(20))
SELECT CONCAT(itemID,': ',itemName,' in store ',storeID,
             ' cost $',price,' and there are ',number,' in stock.') AS display
FROM item
WHERE storeID = storeParam;



CREATE PROCEDURE printCustomers()
SELECT CONCAT('CustID: ',custID,' ',firstName,' ',lastName) AS display
FROM customers;



CREATE PROCEDURE searchItemNum(IN itemNumParam VARCHAR(20))
SELECT CONCAT(itemID,': ',itemName,' in store ',storeID,
             ' cost $',price,' and there are ',number,' in stock.') AS display
FROM item
WHERE itemID = itemNumParam;



CREATE PROCEDURE searchItemName(IN itemNameParam VARCHAR(20))
SELECT CONCAT(itemID,': ',itemName,' in store ',storeID,
             ' cost $',price,' and there are ',number,' in stock.') AS display
FROM item
WHERE itemName LIKE CONCAT('%',itemNameParam,'%');


CREATE PROCEDURE addQuantity(IN storeIDparam VARCHAR(50),itemParam VARCHAR(50), quanParam INT)
UPDATE item SET number = quanParam
WHERE storeID = storeIDparam AND itemID = itemParam;

CREATE PROCEDURE updateItemQuant(IN storeIDparam VARCHAR(20),itemIDparam VARCHAR(20),itemQuant INT)
UPDATE item SET inStock = number - itemQuant
WHERE storeID = storeIDparam AND itemID = itemIDParam; 



CREATE PROCEDURE addSale(IN custIDparam INT, itemIDparam VARCHAR(20),itemQuant INT)
INSERT INTO sale (custID,itemID,numSold,saleDate)
VALUES(custIDparam,itemIDparam,itemQuant,NOW());


DELIMITER $$
CREATE PROCEDURE completeSale (IN saleIDparam INT,saleLineIDparam INT, custIDparam INT,itemIDparam VARCHAR(20),storeIDparam VARCHAR(20),itemQuant INT)
BEGIN
DECLARE num int;

SELECT number INTO num
FROM item
WHERE itemID = itemIDparam AND storeID = storeIDparam;

START TRANSACTION;
	UPDATE item SET number = number - itemQuant
	WHERE storeID = storeIDparam AND itemID = itemIDParam AND number>=itemQuant; 
	
	IF num>=itemQuant THEN
		INSERT INTO sale (saleID,saleLineID,custID,itemID,numSold,saleDate)
		VALUES(saleIDparam,saleLineIDparam,custIDparam,itemIDparam,itemQuant,NOW());
	END IF;
COMMIT;
END $$
DELIMITER ;

CREATE VIEW saleSummary	AS
SELECT saleID, saleLineID, sale.custID, firstName, lastName, sale.itemID, itemName, storeID, price, numSold
FROM sale INNER JOIN customers ON sale.custID = customers.custID
JOIN item ON sale.itemID = item.itemID
ORDER BY saleID, saleLineID;

CREATE PROCEDURE insertCust(IN lnParam VARCHAR(50), fnParam VARCHAR(50))
INSERT INTO customers(lastName, firstName)
VALUES(lnParam, fnParam);

CREATE PROCEDURE addItem(IN inParam VARCHAR(50), itemIDParam VARCHAR(50), storeIDParam VARCHAR(20), ipParam DECIMAL(6,2), iqParam INT)
INSERT INTO item(itemName, itemID, storeID, price, number)
VALUES(inParam, itemIDParam, storeIDParam, ipParam, iqParam);

DELIMITER $$
CREATE PROCEDURE addStoreAndType(IN nameParam VARCHAR(50), storeIDParam VARCHAR(20), typeID VARCHAR(10), typeName VARCHAR(20))
BEGIN
    INSERT INTO storeType(storeType, storeTypeID)
    VALUES(typeName, typeID);
    
    INSERT INTO store(storeName, storeID, storeTypeID)
    VALUES(nameParam, storeIDParam, typeID);
END $$
DELIMITER ;

CREATE PROCEDURE addStore(IN nameParam VARCHAR(50), storeIDParam VARCHAR(20), typeID VARCHAR(10))
INSERT INTO store(storeName, storeID, storeTypeID)
VALUES(nameParam, storeIDParam, typeID);

CREATE PROCEDURE addAdmin(IN user VARCHAR(30), pass VARCHAR(30))
INSERT INTO admin(user, pass)
VALUES(user, pass);
