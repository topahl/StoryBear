package com.opticalcobra.storybear.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.hsqldb.server.Server;
import org.hsqldb.types.Types;

import com.opticalcobra.storybear.editor.Story;
import com.opticalcobra.storybear.editor.StoryInfo;



/**
 * 
 * @author Tobias
 *
 */
public class Database {
	private static Server DBinstance;
	private static Connection conn;
	public static int requestnum;
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
	
	
	/**
	 * @author Tobias & Martika
	 * @param ba
	 * @param tableName
	 * @throws SQLException
	 */
	private void insertBlob(byte[] ba, String tableName) throws SQLException{
		java.sql.Blob obj = conn.createBlob();
		obj.setBytes(1, ba);
		PreparedStatement pstmt = conn.prepareStatement("INSERT INTO " + tableName + " (OBJECT, LENGTH) VALUES(?,"+ba.length+")");
		pstmt.setBlob(1, obj);
		pstmt.execute();
	}
	
	/**
	 * @author Tobias & Martika
	 * @param id
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private byte[] getBlob(int id, String tableName) throws SQLException{
		ResultSet rs = query("SELECT OBJECT, LENGTH FROM " + tableName + " WHERE ID = "+id+";");
		rs.next();
		java.sql.Blob obj = rs.getBlob("OBJECT");
		return obj.getBytes(1, rs.getInt("LENGTH"));
	}
	
	
	/**
	 * @Martika
	 * @param currentStory
	 */
	public void insertStoryToDatabase(Story currentStory){
		byte[] ba = Blob.create(currentStory);
		
		try {
			insertBlob(ba, "STORY");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Story getStoryFromDatabase(int id){
		byte[] currentBlob = null;
		try {
			currentBlob = getBlob(id, "STORY");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ((Story)Blob.read(currentBlob));
	}
	
	
	/**
	 * @Martika
	 * @param currentStory
	 */
	public void insertStoryInfoToDatabase(StoryInfo currentStoryInfo){
		byte[] ba = Blob.create(currentStoryInfo);
		
		try {
			insertBlob(ba, "LEVELS");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public StoryInfo getStoryInfoFromDatabase(int id){
		byte[] currentBlob = null;
		try {
			currentBlob = getBlob(id, "LEVELS");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ((StoryInfo)Blob.read(currentBlob));
	}
	

	private synchronized ResultSet query(String expression) throws SQLException {
		requestnum++;
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
	public ImageResult queryImagedata(int id) throws SQLException{		
		String query = "SELECT * FROM IMAGES WHERE ID = "+id;
		ResultSet rs = query(query);
		int x;
		int y;
		int width;
		int height;
		String url;
		rs.next();
		url =(String) rs.getObject("URL");
		x =(Integer) rs.getObject("X");
		y =(Integer) rs.getObject("Y");
		height =(Integer) rs.getObject("HEIGHT");
		width =(Integer) rs.getObject("WIDTH");

		rs.close();
		return new ImageResult(id, x, y, height, width, url);
	}
	
	public Integer[] queryNumberResultOnly(String query) throws SQLException{
		ResultSet rs = query(query);
		ResultSetMetaData meta = rs.getMetaData();
		ArrayList<Integer> result = new ArrayList<Integer>();
		while(rs.next()){
			result.add((int) rs.getObject(1));
		}
		rs.close();
		return (Integer[])result.toArray(new Integer[0]);
	}
	
	
	/**
	 * Returns an 0,1,2 or -1 weather we'ye got an image for the word as a collectable, character or landscape
	 *
	 * @author Martika
	 * 
	 * @param word 
	 * @return int 0,1,2 or -1 as Constants 
	 * @throws SQLException
	 */
	public int queryWordType(String word) throws SQLException{
		int typeId = DBConstants.WORD_OBJECT_TYPE_NO_IMAGE;
		ResultSet rsFexione;
		ResultSet rsCollectable;
		ResultSet rsCharacter;
		ResultSet rsMiddleground;
		String flexinom = "";
		
		
		String q1 = "";
		String q2 = "";
		String q3 = "";
		
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

			flexinom= (String) rsFexione.getObject(1);
			flexinom.replace("'", "");
			
			q1 = "SELECT * FROM Collectable_Object WHERE word = '"+flexinom+"';" ;
			System.out.println(q1);
			rsCollectable = query(q1);
			rsCharacter = query("SELECT * FROM Character_Object WHERE word = '"+flexinom+"';");
			rsMiddleground= query("SELECT * FROM Middleground_Object WHERE word = '"+flexinom+"';");
			
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

