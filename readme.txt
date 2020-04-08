						*****************************************************************************    
								Best Deal -  Run Manual
						*****************************************************************************


Deployment and server intiation information

 	1. Start tomcat server
  2. Copy "homework1" folder to the tomcat webapp folder.
  3. In order to start the application open the browser and type http://localhost/homework1
  4. Start the MySQL database with database name as bestdealdatabase.
  5. Start the Mongodb database


	
------------------------------------------------------
MySql Queries to Create Database and tables	
------------------------------------------------------

create database bestdealdatabase;

use bestdealdatabase;

Create table Productdetails
(
ProductType varchar(50),
Id varchar(50),
productName varchar(50),
productPrice double,
productImage varchar(40),
productManufacturer varchar(40),
productCondition varchar(40),
productDiscount double,
Primary key(Id)
);

Create table CustomerOrders
(
OrderId integer,
userName varchar(40),
orderName varchar(40),
orderPrice double,
userAddress varchar(40),
creditCardNo varchar(40),
orderdate varchar(40),
Primary key(OrderId,userName,orderName)
);


---------------------------------------------------
Features in this project:
---------------------------------------------------

1.  Deal Match Guarantees feature

2.  Recommender feature 
   

	



