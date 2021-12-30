import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;


public class MetaData {
	
	public static void main(String[] args)throws Exception {

		try (Connection cn= new ConnectDB().connect();) {
		      Auslesen(cn);
		      }
				 catch (Exception e) {
				      System.err.println("Verbindung fehlaufschlagen:" + e.toString());
				    }

	}
 public static void Auslesen(Connection cn)throws Exception {
	
	     
	        String schema=cn.getSchema();
	        DatabaseMetaData mtD=cn.getMetaData();
	        try(ResultSet rs=mtD.getTables(null, schema, "%", null)){
	        	while(rs.next()) {
	        		String tableName=rs.getString("TABLE_NAME");
	        		System.out.println("DROP TABLE"+" "+tableName+";");
	        		System.out.println();
	        		System.out.println("CREATE TABLE "+tableName+" "+"(");
	        	
	        		try(ResultSet rs1=mtD.getColumns(null, schema, tableName, "%")){
	        			
	        			while(rs1.next()) {
	        				String q1=null;
	        				
	        				String q=rs1.getString("COLUMN_NAME")+" "+rs1.getString("TYPE_NAME");
	        				String b=rs1.getString("TYPE_NAME");
	        				int c=rs1.getInt("COLUMN_SIZE");
	        				String c1="("+c;
	        				int a=rs1.getInt("DECIMAL_DIGITS");
	        				if(a==0&&!(b.equals("DATE"))) {
	        					q1=q+c1;
	        				}
	        				if(a!=0&&!(b.equals("DATE"))) {
	        					q1=q+c1+","+a+")";
	        				}
	        				if(b.equals("DATE")) {
	        					q1=q;
	        				}
	        			    int nullable=rs1.getInt("NULLABLE");
	        				if(nullable==0&&!(b.equals("DATE")))
	        					System.out.println(q1+")"+" "+"NOT NULL ,");
	        				else if(nullable==0&&b.equals("DATE"))//Date braucht keine Eingabe wie COLUMN_SIZE oder DECIMAL_DIGITS
	        					System.out.println(q1+" "+"NOT NULL ,");
	        				else
	        					System.out.println(q1+",");
	        				}
	     
	        			}
	        			try(ResultSet rs2=mtD.getPrimaryKeys(cn.getCatalog(), schema ,tableName)){//Primarykeys suchen
	        				String s1=null;String s2=null;String s3=null;
		        			int i=0;
	        		while(rs2.next()) {
	        		s1=rs2.getString("COLUMN_NAME");
	        		    i++;
	        		    if(i==2)
	        		    	s3=s1;
	        		    s2=rs2.getString("COLUMN_NAME");
	        		    if(!(s2.equals("ANR")))
	        		    	s2="ANR";
	        	}
	        					if(s1!=null&&i==1)
	        						System.out.println("PRIMARY KEY"+" "+"("+s1+")");
	        					else
	        				
	        						System.out.println("PRIMARY KEY"+" "+"("+s1+","+s2+")");
	        		
	        			}
	        			System.out.println(");");
	        			
	        		}
	        	}
	        	
	        }
	      }
	    
