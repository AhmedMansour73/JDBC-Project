
package jdbc_project;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This file contains methods that you can use on the DB
 * Operations are (Create the table, Drop the table, Insert data into the table, Update the table, Delete from the table,
 * Show all data in the table, Get the names of the table)
 * @author Ahmed Mansour
 */
public class OperationOnDB {
    static Scanner  input = new Scanner(System.in);

    private static Connection con =null;
    private static Statement stat =null;
    private static ResultSet rs =null;
    

    public OperationOnDB() {
   
    }
   
    

    
    public static void dropTableByStat(String nameTable) throws SQLException
    {
        String query="drop table ";
        con = ConnectWithDB.connectDB();
        stat = con.createStatement();
        query = query + nameTable;
        stat.executeUpdate(query);
    }
    
  
    
    private static void getconction() throws SQLException
    {
        con = ConnectWithDB.connectDB();
        stat = con.createStatement();
    }
    
    public static void createTable(String name) throws SQLException
    {
        ArrayList columnsName= new ArrayList();
        ArrayList columnsDatatype = new ArrayList();
        ArrayList tableNames= new ArrayList();
        
        System.out.println("note 1 .....");
        System.out.println("If name of this table is existing Will by DROP it:");
        System.out.println("note 2  .....");
        System.out.print("To continue press 1, else press any number: ");
        int flag = input.nextInt();
        if(flag == 1)
        {
            tableNames= OperationOnDB.tablesNames();
            if(tableNames.contains(name))
            {
                OperationOnDB.dropTableByStat(name);
            }

            System.out.println();
            System.out.print("Please enter number of columns: ");
            int numberOfColumns =input.nextInt();
            System.out.println();
            System.out.println("your table will by existin on it column (Id) is primary and identity.");
            System.out.println("--------------------------------------------------------------------------------");

            for (int i = 0; i < numberOfColumns; i++) {
                System.out.println();
                System.out.print("please Enter name of column : ");
                columnsName.add(input.next());
                System.out.print("please Enter Datatype(int or varchar(length) or date) of column : ");
                columnsDatatype.add(input.next());
                System.out.println();
            }
        
        }
        else{
            System.out.println("Thanks!");
        }
        
        
        
        
        String queryToCreateTable="create table "+name+" "
                + "(Id int primary key identity,"
                ;
        StringBuilder queryToBuildColumns= new StringBuilder();

        if(columnsName.size() != 0)
        {
            for (int i = 0; i < columnsName.size(); i++) {
                if(i == columnsName.size()-1)
                       queryToBuildColumns.append( ""+columnsName.get(i)+"" + " "+columnsDatatype.get(i)+"" + ")"); 
                else
                    queryToBuildColumns.append( ""+columnsName.get(i)+"" + " "+columnsDatatype.get(i)+"" + ",");
            }
        }else
        {
            queryToBuildColumns.append(queryToCreateTable + ")");
        }
        
        queryToCreateTable = queryToCreateTable + queryToBuildColumns.toString();
        
        
        getconction();
        stat.executeUpdate(queryToCreateTable);
        System.out.println("Table Created !");
    }
    
    public static ArrayList tablesNames() throws SQLException
    {
        ArrayList nameOfTable = new ArrayList();
        String query ="SELECT name\n" +
                        "FROM sys.Tables";
        
        getconction();
        stat.executeQuery(query);
        rs = stat.getResultSet();
        
        while(rs.next())
        {
            nameOfTable.add(rs.getString("name"));
        }
        
        return nameOfTable;
    }
    
    
    public static void insertData(String nametable) throws SQLException
    {
        String queryToGetColumnsNames = "SELECT COLUMN_NAME\n" +
                                        "FROM INFORMATION_SCHEMA.COLUMNS \n" +
                                        "WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = '"+nametable+"'";
        String queryTGetDatatype= "SELECT DATA_TYPE\n" +
                                    "FROM INFORMATION_SCHEMA.COLUMNS \n" +
                                    "WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = '"+nametable+"'";
        ArrayList columns =new ArrayList() ;
        ArrayList dataType =new ArrayList() ;
        ArrayList values =new ArrayList();
        
        
        
        
        getconction();

        // get nams of column will by make on it insert
        stat.executeQuery(queryToGetColumnsNames);
        rs = stat.getResultSet();
        
        while(rs.next())
        {
            columns.add(rs.getString("COLUMN_NAME"));
        }
        columns.remove(0);
        
        //  get data type of columns
        stat.executeQuery(queryTGetDatatype);
        rs = stat.getResultSet();
        
        while(rs.next())
        {
            dataType.add(rs.getString("DATA_TYPE"));
        }
        dataType.remove(0);
        
        ///  add values from user
        ///

        for (int i = 0; i < dataType.size(); i++) {
            if(dataType.get(i).equals("varchar") || dataType.get(i).equals("date") )
            {
                System.out.println("please enter [ String ] "+columns.get(i)+" : " );
                values.add(input.next());
            }
            
            if(dataType.get(i).equals("int"))
            {
                System.out.println("please enter [ Integer ] "+columns.get(i)+" : " );
                values.add(input.nextInt());
            }
            
        }
        
        
        ///
        ///
        
        // make query to insert
        String cols =String.join(", ",columns); 
        
        StringBuilder queryToInsertValues= new StringBuilder();
        String queryToInsertIntoTable= "insert into "+nametable+" ( "+cols+" ) "
                + "values ( " ;
        
        if(values.size() != 0)
        {
            for (int i = 0; i < values.size(); i++) {
                if(i == values.size()-1){
                if(values.get(i).getClass().getName().equals("java.lang.Integer"))
                    queryToInsertValues.append(values.get(i) + ")");
                if(values.get(i).getClass().getName().equals("java.lang.String"))
                    queryToInsertValues.append("'"+values.get(i)+"'" + ")");
                }
                        
                else
                    {
                if(values.get(i).getClass().getName().equals("java.lang.Integer"))
                    queryToInsertValues.append(values.get(i) + ",");
                if(values.get(i).getClass().getName().equals("java.lang.String"))
                    queryToInsertValues.append("'"+values.get(i)+"'" + ",");
                }
                    
            }
        }else
        {
            queryToInsertValues.append(")");
        }
        
        queryToInsertIntoTable = queryToInsertIntoTable+queryToInsertValues.toString();

        stat.executeUpdate(queryToInsertIntoTable);
        System.out.println("Insert completed");        
         
    }
    
    
    public static void deleteALLData(String nametable) throws SQLException
    {
        String queryTruncateData="TRUNCATE TABLE "+nametable+"";
        int flag = 0;
        System.out.println("Are you sure you want to delete all data from table?");
        System.out.println("Yes press : 1          , No press : any number");
        if(flag==1)
        {
         getconction();
         stat.executeUpdate(queryTruncateData);
        }
        else
            System.out.println("Thans!");
        
        System.out.println("Delet completed  ");
            
    }
    
    
   
    public static void deleteData(String nametable) throws SQLException
    {
        String condition;
        
        ArrayList columnsName =new ArrayList();
        String queryToGetColumnsNames = "SELECT COLUMN_NAME\n" +
                                        "FROM INFORMATION_SCHEMA.COLUMNS \n" +
                                        "WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = '"+nametable+"'";
        // get nams of column will by make on it insert
        getconction();
        stat.executeQuery(queryToGetColumnsNames);
        rs = stat.getResultSet();
        
        while(rs.next())
        {
            columnsName.add(rs.getString("COLUMN_NAME"));
        }
        
        System.out.println("this table contains of " +columnsName );
        System.out.println("Enter your condition : ");
        condition=input.nextLine();
        
        String queryDeleteData="DELETE FROM "+nametable+" WHERE "+condition+"";
        
        stat.executeLargeUpdate(queryDeleteData);
        System.out.println("Delet completed  ");
    }
    
    
    public static void updateData(String nametable) throws SQLException
    {
        String condition;
        String updateStatement;
        
        ArrayList columnsName =new ArrayList();
        String queryToGetColumnsNames = "SELECT COLUMN_NAME\n" +
                                        "FROM INFORMATION_SCHEMA.COLUMNS \n" +
                                        "WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = '"+nametable+"'";
        // get nams of column will by make on it insert
        getconction();
        stat.executeQuery(queryToGetColumnsNames);
        rs = stat.getResultSet();
        
        while(rs.next())
        {
            columnsName.add(rs.getString("COLUMN_NAME"));
        }
        
        System.out.println("this table contains of " +columnsName );
        System.out.println("enter your update statement like--> column_name = 'string' , column_name= 123 ...... " );
        updateStatement=input.nextLine();
        System.out.println("Enter your condition : ");
        condition=input.nextLine();
        
        String queryUpdateData="UPDATE "+nametable+"\n" +
                                "SET "+updateStatement+"\n" +
                                "WHERE "+condition+";";

        stat.executeLargeUpdate(queryUpdateData);
        System.out.println("Update completed  ");
    }
    
    
    public static void displayAllData(String nametable) throws SQLException
    {
        
        ArrayList columnsName =new ArrayList();
        String queryToGetColumnsNames = "SELECT COLUMN_NAME\n" +
                                        "FROM INFORMATION_SCHEMA.COLUMNS \n" +
                                        "WHERE TABLE_SCHEMA = 'dbo' AND TABLE_NAME = '"+nametable+"'";
        
// get nams of column will by make on it insert
        getconction();
        stat.executeQuery(queryToGetColumnsNames);
        rs = stat.getResultSet();
        
        while(rs.next())
        {
            columnsName.add(rs.getString("COLUMN_NAME"));
        }
        
        String querySelectData="SELECT * FROM "+nametable+";";

        stat.executeQuery(querySelectData);
        rs = stat.getResultSet();
        while(rs.next())
        {
            for (int i = 0; i <columnsName.size() ; i++) {
                System.out.print(columnsName.get(i)+"="+rs.getString(columnsName.get(i).toString())+ ", ");
            }
            System.out.println();
        }
        System.out.println("Select completed  ");
    }


    
}
