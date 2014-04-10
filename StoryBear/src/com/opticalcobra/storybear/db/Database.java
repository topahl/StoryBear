package com.opticalcobra.storybear.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.hsqldb.server.Server;
import org.hsqldb.types.Types;




/**
 * 
 * @author Tobias
 *
 */
public class Database {
	private static Server DBinstance;
	private static Connection conn;
	
	/**
	 * @author Tobias
	 */
	public Database() {
		if(DBinstance == null){
			DBinstance = Server.start(new String[0]);
		}
		if(conn == null){
			try {
				Class.forName("org.hsqldb.jdbcDriver");
				conn = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/", "sa","");
			} catch (SQLException e) {
				System.err.println("Cound not establish database connection");
			} catch (ClassNotFoundException e) {
				System.err.println("Cound not load jdbc driver");
				e.printStackTrace();
			}
		}
	}

	/**
	 * @author Tobias
	 * @throws SQLException
	 */
	public void shutdown() throws SQLException{
        Statement st = conn.createStatement();
        st.execute("SHUTDOWN");
        conn.close();
	}
	
	private synchronized ResultSet query(String expression) throws SQLException {

        Statement st = null;
        ResultSet rs = null;

        st = conn.createStatement(); 

        rs = st.executeQuery(expression);
        st.close();
        return rs;
	}
	
	/**
	 * Returns current Server status
	 * DBConstants.SERVER_SHUTDOWN
	 * DBConstants.SERVER_OPENING
	 * DBConstants.SERVER_RUNNING
	 * DBConstants.SERVER_CLOSEING
	 * @return Constant for Server Status
	 * @author Tobias
	 */
	public int getState(){
		return DBinstance.getState();
	}
	
	
	/**
	 * Starts the Database for Development purpose
	 */
	public static void main(String[] args){
	  Database DB = new Database();
	  try {
		DB.query("SELECT * FROM category;");
		DB.shutdown();
	} catch (SQLException e) {
		System.err.println("SQL Error");
	}

	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList[] toArrayList(ResultSet rs){
		int colmax = 0;
		ResultSetMetaData meta = null;
		ArrayList[] result = null;
		//Array initialisieren
		try {
			meta   = rs.getMetaData();
			colmax = meta.getColumnCount();
			result = new ArrayList[colmax];
			for (int i = 0; i < colmax; i++) {
				switch (meta.getColumnType(i + 1)) {
				case Types.INTEGER:
					result[i] = new ArrayList<Integer>();
					break;
				case Types.BOOLEAN:
					result[i] = new ArrayList<Boolean>();
					break;
				default:
					result[i] = new ArrayList<String>(); //to String is working always
					break;
				}

			}
		} catch (SQLException e) {
			System.err.println("Error identifying metadata from query result");
		}
		
		//Daten in ArrayList laden
        Object o;
        
        try {
			while (rs.next()) {
				for (int i = 0; i < colmax; ++i) {
					o = rs.getObject(i + 1);
					result[i].add(o);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error while reading SQL results data");
		}
        return result;
	}
	
	/**
	 * Returns all information about an Image stored in the DB
	 * @param query
	 * @return 
	 * @throws SQLException
	 */
	public ImageResult queryImagedata(String query) throws SQLException{		
		
		ResultSet rs = query(query);
				ResultSetMetaData meta = rs.getMetaData();
				int x;
				int y;
				int width;
				int height;
				String url;
				rs.next();
				url =(String) rs.getObject(2);
				x =(Integer) rs.getObject(3);
				y =(Integer) rs.getObject(4);
				height =(Integer) rs.getObject(5);
				width =(Integer) rs.getObject(6);

				rs.close();
				return new ImageResult(x, y, height, width, url);
				
		
	}
	
	
	/**
	 * Returns all information about a Word
	 * @param query
	 * @return 
	 * @throws SQLException
	 */
	public int queryWordType(String word) throws SQLException{
		int typeId = DBConstants.WORD_OBJECT_TYPE_NO_IMAGE;
		ResultSet rsFexione;
		ResultSet rsCollectable;
		ResultSet rsCharacter;
		ResultSet rsMiddleground;
		
		rsFexione = query("SELECT DISTINCT t2.word FROM term t "
				+ "LEFT JOIN synset s ON t.synset_id =s.id "
				+ "LEFT JOIN term t2 ON t2.synset_id = s.id "
				+ "LEFT JOIN category_link cl ON t2.synset_id = cl.synset_id "
				+ "LEFT JOIN category c ON c.id = cl.category_id "
				+ "LEFT JOIN term_level tl ON t2.level_id = tl.id "
				+ "WHERE t.word in (SELECT basic FROM morph where reflexive= '" +word+ "' ) "
				+ "OR t.word like '" +word+ "' OR t.normalized_word like '" +word+ "' "
				+ "OR t.normalized_word in (SELECT basic FROM morph where reflexive= '" +word+ "' );");

		while (rsFexione.next()){ 
			rsCollectable = query("SELECT * FROM Collectable_Object WHERE word = '"+rsFexione.getObject(1)+"';");
			rsCharacter = query("SELECT * FROM Character_Object WHERE word = '"+rsFexione.getObject(1)+"';");
			rsMiddleground= query("SELECT * FROM Middleground_Object WHERE word = '"+rsFexione.getObject(1)+"';");
			
			if (rsCharacter.next()){
				typeId = DBConstants.WORD_OBJECT_TYPE_CHARACTER;
			}
			else if (rsCollectable.next()){
				typeId = DBConstants.WORD_OBJECT_TYPE_COLLECTABLE;
			}
			else if (rsMiddleground.next()){
				typeId = DBConstants.WORD_OBJECT_TYPE_MIDDLEGROUND;
			}
		}

		rsFexione.close();
		return typeId;
	}
	
	
	public void dump(ResultSet rs) throws SQLException {
//        ResultSetMetaData meta   = rs.getMetaData(); // TODO print metadata
//        int colmax = meta.getColumnCount();
//        Object o = null;
//
//        while(rs.next() ) {
//            for (int i = 0; i < colmax; ++i) {
//                o = rs.getObject(i + 1);
//                System.out.print(o + " ");
//            }
//
//            System.out.println(" ");
//        }
		System.out.println(toArrayList(rs));
    }          
}

