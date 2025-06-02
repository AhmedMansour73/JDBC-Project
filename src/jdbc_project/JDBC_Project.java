
package jdbc_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Scanner;

/**
 * this main file Content in the code to invoke methods in the program
 * @author Ahmed Mansour
 */

public class JDBC_Project {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String nameOfTable;
        ArrayList tableNames= new ArrayList();
        
         
         
        Connection con =null;
        Statement stat =null;
        ResultSet rs =null;
        
        int numberOfOperation=1;
        
        try {
            
            con = ConnectWithDB.connectDB();
            
            con.setAutoCommit(false);
            while(true)
                {
                    System.out.println("------------------------------------------------------------------");
                    System.out.println("****** In this Program you can connect with Database ******");
                    System.out.println("* this program can do this: ");
                    System.out.print("* 1- Create table \t");
                    System.out.println("* 2- Drop table ");
                    System.out.print("* 3- Insert data \t");
                    System.out.println("* 4- Delete data");
                    System.out.print("* 5- Update data \t");
                    System.out.println("* 6- Display all data");
                    System.out.print("* 7- show tables names \t");
                    System.out.println("* 8- Exit");
                    System.out.println("------------------------------------------------------------------");
                    
                    System.out.println();
                    System.out.println("Please enter number of operation from 1 --> 8 : " );
                    numberOfOperation=input.nextInt();
                    System.out.println();
                    
                    if(numberOfOperation<= 0 || numberOfOperation >= 8)
                    {
                        System.out.println("thanks for use the program");
                        break;
                    }
                    
                    if(numberOfOperation == 1)
                    {
                        System.out.println();
                        System.out.print("Enter your table name to create table : ");
                        nameOfTable  = input.next();

                        OperationOnDB.createTable(nameOfTable);
                        System.out.println();
                    }
                        
                    if(numberOfOperation == 2)
                    {
                        System.out.println();
                        System.out.print("Enter your table name will be drop it : ");
                        nameOfTable  = input.next();
                        OperationOnDB.dropTableByStat(nameOfTable);
                        System.out.println();
                    }
                    
                    if(numberOfOperation == 3)
                    {
                        System.out.println();
                        System.out.print("Enter your table name will be insert data on it : ");
                        nameOfTable  = input.next();
                        OperationOnDB.insertData(nameOfTable);
                        System.out.println();
                    }
                    
                    if(numberOfOperation == 4)
                    {
                        System.out.println();
                        System.out.print("Enter your table name will be delete data from it : ");
                        nameOfTable  = input.next();
                        OperationOnDB.deleteData(nameOfTable);
                        System.out.println();
                    }

                    if(numberOfOperation == 5)
                    {
                        System.out.println();
                        System.out.print("Enter your table name will be Update data from it : ");
                        nameOfTable  = input.next();
                        OperationOnDB.updateData(nameOfTable);
                        System.out.println();
                    }
                    
                    
                     if(numberOfOperation == 6)
                    {
                        System.out.println();
                        System.out.print("Enter your table name will be display data : ");
                        nameOfTable  = input.next();
                        OperationOnDB.displayAllData(nameOfTable);
                        System.out.println();
                    }
                    
                    if(numberOfOperation == 7)
                    {
                        System.out.println();
                        System.out.print("Display All Tables Names : ");
                        tableNames= OperationOnDB.tablesNames();
                        System.out.println(tableNames);
                        System.out.println();
                    }
                    
                }
            
            
            con.commit();
            
            
        } catch (Exception e) {
            System.out.println("not conect " + e.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex) {
                
            }
        }finally
        {
            try {
                ///  clean up
                ConnectWithDB.colseconnect();
                
            } catch (SQLException ex) {
                Logger.getLogger(JDBC_Project.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        
    }
    }
    
}
