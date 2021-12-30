import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBUpdater {
	 static Scanner ein=new Scanner(System.in);//Scanner fuer das ganze Programm

	final static String tableName="LIEFERANT";
	public static void main(String[] args) throws Exception {

		try(Connection cn= new ConnectDB().connect();){
			Update(cn);//aufruf um die Sachen zu aendern
				
			} catch (SQLException e) {
				System.err.println("Verbindung fehlaufschlagen:" + e.toString());
			}

	}
	//hier macht der Anwender bzw gibt der Anwender,die Aenderungen,die er durchfuehren will
	public static List<Object> eingabe() {//um den LNR und den neuen Name einzugeben
		List<Object> li = new ArrayList<>();
		
		System.out.println("Start der Aenderung");
		System.out.println("Geben Sie Bitte den LNR ein");
	    final int LNR=ein.nextInt();
	    li.add(LNR);
	   System.out.println("Geben Sie bitte den neuen Name ein");
		final String Name=ein.next();
		li.add(Name);
	   
		
		
		return li;
	}
	public static void Read(Connection cn) throws SQLException {//LNR und Name werden ausgelesen
		try(Statement st=cn.createStatement()){
			String sql="Select LNr,Name from Lieferant";
		try(ResultSet rs =st.executeQuery(sql)){
			System.out.println("Tabelle:Lieferant");
			System.out.println("LNr"+","+"Name");
			while(rs.next())
				System.out.println(rs.getInt("LNr")+","+rs.getString("Name"));
			
		}
		}
	}


	public static void Update(Connection cn) throws IOException {
		try {
			cn.setAutoCommit(false);
			Read(cn);//Auslesen der Datensaetze
		    List<Object> li=eingabe();//aenderung der Anwender
		    int LNR=(int) li.get(0);
		    String Name=(String) li.get(1);
		    String sql="UPDATE LIEFERANT"+" "+"SET NAME="+"'"+Name+"'"+" "+"WHERE LNr="+LNR;//Aenderung wird vorbereitet
		   
		    try(Statement stmt=cn.createStatement();){
		    	try{
		    		stmt.executeUpdate(sql);//UPDATE wird durchgefuehrt
		    		Read(cn);
		    		System.out.println("============================================================");
		    		System.out.println("Wollen Sie Weiter aendern?(j/n)");
		    			String eingabe=ein.next();
		    			if(eingabe.equals("j")) {
		    				Update(cn);//man mach eine andere Aenderung(Spiel begin von vorne wieder)
		    			}else if(eingabe.equals("n")) {
		    				
		    				System.out.println("bestaetigen oder zuruck rollen?(j/n)");
		    				String in=ein.next();
		    				if(in.equals("j")) {
		    					cn.commit();
		    					Read(cn);
		    					ein.close();//jedes Mal wird Scanner geschlossen
		    					System.out.println("=============================ende======================================");
		    				}else if(in.equals("n")) {
		    					cn.rollback();
		    					Read(cn);
		    					ein.close();
		    					System.out.println("=============================ende======================================");
		    				}
		    				else {
		    					System.out.println("nicht gueltige eingabe");
		    					ein.close();
		    					System.out.println("=============================ende======================================");
		    				}
		    			}
		    			else {
		    				System.out.println("nicht gueltige eingabe");
		    				ein.close();
		    				System.out.println("=============================ende======================================");
		    			}
		    			
		    		}
		    	finally {
		    		try {
		    		if(stmt!=null)	
		    			stmt.close();
		    		}catch(Exception e) {
		    			
		    		}
		    		try{if(cn!=null);
		    		cn.close();
		    		}catch(Exception e) {
		    			
		    		}
		    	}
		    }
			
			
		    
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		
	}
}
