public class ConnectDB {
	

	
	public Connection connect() throws SQLException{
		final  String propfile = "/praktikum2/db.properties";
        System.out.println("ConnectDB.Connect ENTER");
        Connection cn=null;
        try {
        	final java.io.InputStream propFile=getClass().getResourceAsStream(propfile);
        	final Properties props= new Properties(System.getProperties());
        	props.load(propFile);
        	
        	final String url=props.getProperty("url");
        	cn=DriverManager.getConnection(url, props);
        	cn.setAutoCommit(false);
        	cn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        	if(cn!=null)
        		System.out.println("Verbindung erfolgreich aufgestellt");
        	
        }catch(IOException e) {
        	System.out.println("Connect-Error-Propfile:"+e.toString());
        }catch(SQLException e) {
        	System.out.println("Connect-Error:"+e.toString());
        	throw e;
        	
        }
        
     return cn;   
}
	public static void main(String[] args) {
	     
		try(Connection cn= new ConnectDB().connect()){
	    	 System.out.println("Erfolgreich verbunden");
	     } catch (SQLException e) {
	      System.out.println("Error:"+e.toString());
			
		}



	}
}
