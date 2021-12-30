import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBReaderGen {
	


	public static void main(String[] args) throws Exception{

		try (Connection cn= new ConnectDB().connect();) {
      Ausgabe(cn);
      }
		 catch (Exception e) {
		      System.err.println("Verbindung fehlaufschlagen:" + e.toString());
		    }

	}
	private static void Ausgabe(Connection cn) throws SQLException {
		
		 String schema=cn.getSchema();
          DatabaseMetaData mtD=cn.getMetaData();
          try(ResultSet rs=mtD.getTables(null, schema, "%", null)){
        	 
        	  System.out.print("Tabellen:");
        	  while(rs.next()) {
        		  System.out.print(rs.getString("TABLE_NAME")+" ");
        		  
        		  }
        	 try( Scanner ein=new Scanner(System.in);){
        	 System.out.println();
        	  System.out.println("Geben Sie bitte den Name(IN GROSSBUCHSTABEN)der auszulesenen Relation ein(Mit enter-Taste bestaetigen) ");
        	 String eingabe=ein.next();
        	 if(eingabe.equals("ARTIKEL")||eingabe.equals("LIEFERANT")||eingabe.equals("LIEFERUNG")) {
        		 
        		 try(ResultSet rs1= mtD.getColumns(null, schema, eingabe, "%");){
        		 
        		 int i=0;
        		 System.out.println("==========================="+" "+"Die Attributen der Tabelle "+eingabe+" "+"sind:"+"=============================================");
        		while(rs1.next()) {
        			 System.out.print(rs1.getString("COLUMN_NAME")+";");
        			 i++;//um die Anzahl von Columns zu bestimmen
        			
        			 }
        		System.out.println();
        		System.out.println("==============="+"Die Datensaetze von "+eingabe+" "+"werden ausgegeben:"+"====================");
        		
        	 String sql="Select * from";
        	 String query=sql+" "+eingabe;
        
        		try(Statement stmt=cn.createStatement();
        				ResultSet rst=stmt.executeQuery(query);){
        			while(rst.next()) {
        				for(int j=1;j<=i;j++) {
        					System.out.print(rst.getString(j));
        					if(j<i)
        						System.out.print(";");
        				}
        				System.out.println();
        			}
        			System.out.println("========================ende=================================");
        			}
        		
        		 }
        		 
        		 }//if
        	 
        	 else {
        		 System.out.println("Die Tabelle:"+eingabe+" existiert  in unserer Datenbank nicht ");
        		 System.out.println("========================ende=================================");
        	 }
        	 } 
      		
          }

				
		        
		      
		   


	}
	
}
