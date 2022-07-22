CREATE TABLE Library(
libraryID INT PRIMARY KEY,
libraryName VARCHAR(50),
libraryAddress VARCHAR(100));

CREATE TABLE Employees(
employeeID INT PRIMARY KEY,
ename VARCHAR(40),
libraryID INT,
address VARCHAR(40),
phnum VARCHAR(10),
age INT,
FOREIGN KEY (libraryID) REFERENCES Library(libraryID));

CREATE TABLE Inventory(
inventoryID INT PRIMARY KEY,
inventoryType VARCHAR(50),
iname VARCHAR (50),
author VARCHAR(50),
availability BIT(1),
libraryID INT,
FOREIGN KEY (libraryID) REFERENCES Library(libraryID));

CREATE TABLE Members(
memberID INT PRIMARY KEY,
mname VARCHAR (40),
address VARCHAR(40),
phnum VARCHAR(50),
age INT);

CREATE TABLE BorrowRecord(
borrowID INT PRIMARY KEY, 
inventoryID INT, 
memberID INT, 
memberName VARCHAR(50),
employeeID INT,
employeeName VARCHAR(40),
libraryID INT, 
borrowDate date, 
FOREIGN KEY (inventoryID) REFERENCES Inventory(inventoryID),
FOREIGN KEY (memberID) REFERENCES Members(memberID),
FOREIGN KEY (employeeID) REFERENCES Employees(employeeID),
FOREIGN KEY (libraryID) REFERENCES Library(libraryID));

CREATE TABLE ReturnRecord(
returnID INT PRIMARY KEY,
inventoryID INT, 
borrowID INT, 
libraryID INT, 
returnDate date,
FOREIGN KEY (inventoryID) REFERENCES Inventory(inventoryID),
FOREIGN KEY (borrowID) REFERENCES BorrowRecord(borrowID),
FOREIGN KEY (libraryID) REFERENCES Library(libraryID));

