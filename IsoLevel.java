import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class IsoLevel {

	public static void main(String[] args)throws Exception {
		
		try(Connection cn= new ConnectDB().connect();){
		Iso(cn);
			
		} catch (SQLException e) {
			System.err.println("Verbindung fehlaufschlagen:" + e.toString());
		}
		

	}
	public static void Iso(Connection cn) {
		try {
		    DatabaseMetaData mtD=cn.getMetaData();
			System.out.println("Aktueller Iso level:"+cn.getTransactionIsolation());//ISO-level
			System.out.println("wenn eine Einstellung moglich  ist, wird true ausgegeben sonst false");
			System.out.println( "TRANSACTION_READ_UNCOMMITTED:" + mtD.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED) );//wenn true .heisst,Dass die Datenbank Transaktion ertragen kann,wenn nicht ,dann sind Transaktionen nciht moglich
		      System.out.println( "TRANSACTION_READ_COMMITTED:" + mtD.supportsTransactionIsolationLevel(Connection.TRANSACTION_READ_COMMITTED) );
		      System.out.println( "TRANSACTION_REPEATABLE_READ:" + mtD.supportsTransactionIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ) );
		      System.out.println( "TRANSACTION_SERIALIZABLE:" + mtD.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE) );
		      System.out.println( "Default transaction isolation: " + mtD.getDefaultTransactionIsolation() );
		      System.out.println("Hersteller Name:"+mtD.getDatabaseProductName());//Name der Hersteller
			System.out.println("Version des Treibers:"+mtD.getDriverVersion());//Version des JDBC-Treibers
			System.out.println("========================ende=================================");
		} catch (SQLException e) {

			e.printStackTrace();
			System.out.println("========================ende=================================");
		}
	}

}
