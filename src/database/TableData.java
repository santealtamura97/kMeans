package database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.TableSchema.Column;

/**
 * Modella l’insieme di transazioni collezionate in una tabella. 
 * La singola transazione è modellata dalla classe Example.
 * @author sante
 */
public class TableData {

	DbAccess db;

	public TableData(DbAccess db) {
		this.db=db;
	}

	/**
	 * Ricava lo schema della tabella con nome table. 
	 * Esegue una interrogazione per estrarre le tuple distinte da tale
	 *  tabella. Per ogni tupla del resultset, si crea un oggetto, 
	 *  4 istanza della classe Example, il cui riferimento va incluso 
	 *  nella lista da restituire. In particolare, per la tupla corrente 
	 *  nel resultset, si estraggono i valori dei singoli campi 
	 *  (usando getFloat() o getString()), e li si aggiungono all’oggetto 
	 *  istanza della classe Example che si sta costruendo. 
	 * @param table nome della tabella del database
	 * @return Lista di transazioni distinte memorizzate nella tabella
	 * @throws SQLException in presenza di errori nella esecuzione della query
	 * @throws EmptySetException se il resultset è vuoto
	 * @throws DatabaseConnectionException 
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException, DatabaseConnectionException{
		TableSchema tableSchema=new TableSchema(db,table);
		Statement s = db.getConnection().createStatement();
		String query="SELECT DISTINCT ";
		for(int i=0;i<tableSchema.getNumberOfAttributes();i++) {
			if(i==tableSchema.getNumberOfAttributes()-1)
				query+=tableSchema.getColumn(i).getColumnName()+" ";
			else
				query+=tableSchema.getColumn(i).getColumnName()+", ";
		}
		query+="from " + table;
		ResultSet rs = s.executeQuery(query);
		List<Example> listaTransazioni= new ArrayList<Example>();
		Example transazione;
		//accesso posizionale
		int k;
		while(rs.next()) {
			k=1; //parto da uno perchè le colonne della tabella sono enumerate a partire da 1
			transazione = new Example();
			while(k<=tableSchema.getNumberOfAttributes()) {
				//riempio ogni transazione
				if(!tableSchema.getColumn(k-1).isNumber()) {
					transazione.add(rs.getString(k));
				}else {
					transazione.add(rs.getDouble(k));
				}	
				k++;
			}
			//riempio listaTransazioni
			listaTransazioni.add(transazione);
		}
		s.close();
		db.closeConnection();
		return listaTransazioni;
	}

	/**
	 * Formula ed esegue una interrogazione SQL per estrarre i valori 
	 * distinti ordinati di column e popolare un insieme da restituire
	 * @param table nome della tabella
	 * @param column nome della colonna nella tabella
	 * @return Insieme di valori distinti ordinati in modalità ascendente che l’attributo identificato da nome column assume nella tabella identificata dal nome table. 
	 * @throws SQLException in presenza di errori nella esecuzione della query
	 */
	public Set<Object> getDistinctColumnValues(String table,Column column) throws SQLException,DatabaseConnectionException{
		Statement s = db.getConnection().createStatement();
		String query="SELECT DISTINCT " + column.getColumnName() + " from " + table;
		ResultSet rs = s.executeQuery(query);
			Set<Object> listaValoriDistinti = new TreeSet<Object>();
			while(rs.next()) {
				listaValoriDistinti.add(rs.getString(column.getColumnName()));
			}
			s.close();
			db.closeConnection();
			return listaValoriDistinti;
	}

	/**
	 * Formula ed esegue una interrogazione SQL per estrarre il valore 
	 * aggregato (valore minimo o valore massimo) cercato nella colonna 
	 * di nome column della tabella di nome table.  Il metodo solleva e 
	 * propaga una NoValueException se il resultset è vuoto o il valore
	 * calcolato è pari a null
	 * @param table  nome di tabella
	 * @param column nome di colonna
	 * @param aggregate operatore SQL di aggregazione (min,max) 
	 * @return aggregato cercato
	 * @throws SQLException in presenza di errori nella esecuzione della query
	 * @throws NoValueException se il resultset è vuoto o il valore calcolato è pari a null
	 */
	public  Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException,DatabaseConnectionException{
		Statement s = db.getConnection().createStatement();
		String query;
		if(aggregate==aggregate.MAX) {
			query="SELECT max(" + column.getColumnName() + ")" + " from " + table;
		}else {
			query="SELECT min(" + column.getColumnName() + ")" + " from " + table;
		}
		
			ResultSet rs = s.executeQuery(query);
			Double d=null;
				while(rs.next()) {
					d=rs.getDouble(1);//metto 1 perchè ci puo' essere solo una tupla
				}
			if(d==null)
				throw new NoValueException(); 
				
			s.close();
			db.closeConnection();
			return d;
	}
}