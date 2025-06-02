# JDBC-Project
This is a simple program connect between JSE and SQL server in this program can do **DDL** and **DML** on DB
## Hi, in this project will explain how to connect JSE with SQL DB
- add JDBC SQL Driver to folder library in project. [SQL Drivers (jar)](https://learn.microsoft.com/en-us/sql/connect/jdbc/download-microsoft-jdbc-driver-for-sql-server?view=sql-server-ver17#download)
- link with local SQL DB by useing DB url local , username  and password
  like
  ```
   String URL = "jdbc:sqlserver://localhost:{port};databaseName={name of database};encrypt=true;trustServerCertificate=true";
   String USERNAME="**********";
   String PASSWORD = "*******";
  ```
- open connection betweem JSE and DB
   ```
   Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
  ```
## Fature
- connect with DB
- access on DB can do operation on DB from JSE
    - Create table user enter table name and column name and colum datatype
        - this table contains a column Id is primary and idintity
    - Drop table by
        - need table name
    - Insert Data into table
        - need table name
        - need values to this table
    - Update Data in table
        - need table name
        - need Statement Updata
        - need Condation
    - Delete from table
        - delete all data
            - need table name
        - delete  by condation
            - need table name
            - need condation
    - Show All data from table
        - need table name 
    - Show tables names that existing in DB

## Auther

#### Ahmed Mansour
<hr size(5)>
