package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Modella lo schema di una tabella nel database relazionale
 * @author sante
 */
public class TableSchema {
	DbAccess db;
	public class Column{
		private String name;
		private String type;
		public Column(String name,String type){
			this.name=name;
			this.type=type;
		}
		public String getColumnName(){
			return name;
		}
		public boolean isNumber(){
			return type.equals("number");
		}
		public String toString(){
			return name+":"+type;
		}
	}
	//lo schema di una tabella è una lista di colonne
	List<Column> tableSchema=new ArrayList<Column>();
	
	/**
	 * Costruisce lo schema della tabella
	 * @param db riferimento ad oggetto DbAccess
	 * @param tableName nome della tabella
	 * @throws SQLException
	 * @throws DatabaseConnectionException
	 * @throws EmptySetException
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException, DatabaseConnectionException,EmptySetException{
		this.db=db;
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");
		
		
		 Connection con=this.db.getConnection();
		 
		 //Recupera un oggetto DatabaseMetaData che contiene metadati 
		 //relativi al database a cui questo oggetto Connection 
		 //rappresenta una connessione e lo assegna ad un 
		 //riferimento DatabaseMetaData
		 DatabaseMetaData meta = con.getMetaData();
		 
		 //Recupera una descrizione delle colonne della tabella
		 //disponibili nel catalogo specificato e l'assegna ad un riferimento
		 //ResultSet 
		 //res è una tabella di dati che rappresenta un set di risultati del database
		 ResultSet res = meta.getColumns(null, null, tableName, null); 
		 if(!res.first()) {
			 throw new EmptySetException();
		 }
		 res.beforeFirst();
	     while (res.next()) {
	        	 if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
	        		 tableSchema.add(new Column(
	        				 res.getString("COLUMN_NAME"),
	        				 mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
	        				 ); 
	      }
	      res.close();
	    }
	
		public int getNumberOfAttributes(){
			return tableSchema.size();
		}
		
		public Column getColumn(int index){
			return tableSchema.get(index);
		}
}
