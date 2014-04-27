package com.opticalcobra.storybear.db;

import java.awt.Point;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hsqldb.server.Server;
import org.hsqldb.types.Types;

import com.opticalcobra.storybear.editor.Story;
import com.opticalcobra.storybear.editor.StoryInfo;
import com.opticalcobra.storybear.main.User;
import com.opticalcobra.storybear.res.Ressources;

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
				System.err.println("Could not establish database connection");
			} catch (ClassNotFoundException e) {
				System.err.println("Could not load jdbc driver");
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
	 * @author Martika
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
	
	
	public void updateStory(Story currentStory) {
		try {
			conn.createStatement().execute("DELETE FROM story WHERE id=" + currentStory.getId() + ";");
			insertStoryToDatabase(currentStory);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<StoryInfo> getAllLevelssFromDatabase(){
		ArrayList<StoryInfo> result = new ArrayList<StoryInfo>();
		try {
			ResultSet rs = query("Select Object, length from levels");
			while(rs.next()){
				
				java.sql.Blob obj = rs.getBlob("OBJECT");
				byte[] ba = obj.getBytes(1, rs.getInt("LENGTH"));
				StoryInfo si = (StoryInfo)(Blob.read(ba));
				result.add(si);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public ArrayList<Story> getAllStoriesFromDatabase(){
		ArrayList<Story> result = new ArrayList<Story>();
		try {
			ResultSet rs = query("Select Object, length from story");
			while(rs.next()){
				
				java.sql.Blob obj = rs.getBlob("OBJECT");
				byte[] ba = obj.getBytes(1, rs.getInt("LENGTH"));
				Story si = (Story)(Blob.read(ba));
				result.add(si);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	public ArrayList<StoryInfo> getAllStorysFromDatabaseWithIds(){
		ArrayList<StoryInfo> result = new ArrayList<StoryInfo>();
		try {
			ResultSet rs = query("Select * from levels");
			while(rs.next()){			
				java.sql.Blob obj = rs.getBlob("OBJECT");
				byte[] ba = obj.getBytes(1, rs.getInt("LENGTH"));
				StoryInfo si = (StoryInfo)(Blob.read(ba));
				si.setId(rs.getInt("ID"));
				result.add(si);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
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
	 * @author Martika
	 * @param id
	 * @return
	 */
	public StoryInfo getStoryInfoFromDatabase(int id){
		byte[] currentBlob = null;
		try {
			currentBlob = getBlob(id, "LEVELS");
		} catch (SQLException e) {
		}
		return ((StoryInfo)Blob.read(currentBlob));
	}
	

	/**
	 * @author Martika
	 * @return
	 */
	public TileResult getTileInfo(int tileType){
		ResultSet rs;
		int height = 0;
		boolean walkableLeft = false;
		boolean walkableRight = false;

		try {
			rs = query("SELECT height_level, walkable_left, walkable_right from foreground_type where id = '" + tileType + "';");
			rs.next();
			height = (int) rs.getObject("HEIGHT_LEVEL");
			walkableLeft = rs.getBoolean("WALKABLE_LEFT");
			walkableRight = rs.getBoolean("WALKABLE_RIGHT");
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch (height){
		case -5:
			height = DBConstants.LEVELHEIGHTMINUSFIVE;
			break;
		case -4:
			height = DBConstants.LEVELHEIGHTMINUSFORE;
			break;
		case -3:
			height = DBConstants.LEVELHEIGHTMINUSTHREE;
			break;
		case -2:
			height = DBConstants.LEVELHEIGHTMINUSTWO;
			break;
		case -1:
			height = DBConstants.LEVELHEIGHTMINUSONE;
			break;
		case 0:
			height = DBConstants.LEVELHEIGHTZERO;
			break;
		case 1:
			height = DBConstants.LEVELHEIGHTPLUSONE;
			break;
		case 2:
			height = DBConstants.LEVELHEIGHTPLUSTWO;
			break;
		}
		return new TileResult(tileType, height, walkableLeft, walkableRight);
	}
	
	
	/**
	 * @author Martika
	 * @param tileTypeId
	 * @param containerTypeId
	 * @return
	 */
	public Point getObjectPos(int tileTypeId, int containerTypeId){
		ResultSet rs;
		Point position = new Point(0, 0);
		try {
			rs = query("SELECT x, y FROM foreground_container WHERE type_id = '" +tileTypeId+ "' AND "
					+ "container_type = '" + containerTypeId + "';");
			rs.next();
			position.x = (int) rs.getObject("X");
			position.y = (int) rs.getObject("Y");
			
			position.x = (int) (position.x / Ressources.SCALE);
			position.y = (int) (position.y / Ressources.SCALE);
			
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return position;
	}
	
	
	public synchronized ResultSet query(String expression) throws SQLException {
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
	public WordResult queryWordType(String word) throws SQLException{

		WordResult result = null;
		int typeId = DBConstants.WORD_OBJECT_TYPE_NO_IMAGE;
		ResultSet rsCollectable;
		ResultSet rsIllustrationBig;
		ResultSet rsIllustrationSmall;
		ResultSet rsCharacter;
		ResultSet rsMiddleground;
		ArrayList[] arrayRS; //needed for the ArrayList of the ResultSet rsFexione
		
		String query = "SELECT DISTINCT t2.word FROM term t "
				+ "LEFT JOIN synset s ON t.synset_id =s.id "
				+ "LEFT JOIN term t2 ON t2.synset_id = s.id "
				+ "LEFT JOIN category_link cl ON t2.synset_id = cl.synset_id "
				+ "LEFT JOIN category c ON c.id = cl.category_id "
				+ "LEFT JOIN term_level tl ON t2.level_id = tl.id "
				+ "WHERE t.word in (SELECT basic FROM morph where reflexive= '" +word+ "' ) "
				+ "OR t.word like '" +word+ "' OR t.normalized_word like '" +word+ "' "
				+ "OR t.normalized_word in (SELECT basic FROM morph where reflexive= '" +word+ "' )";
		
		
		arrayRS = this.toArrayList(query(query));
		
		rsCollectable = query("SELECT DISTINCT IMAGE_ID FROM Collectable_Object WHERE word IN ("+query+") OR word = '"+word+"';");
		rsIllustrationBig = query("SELECT DISTINCT IMAGE_ID FROM Illustration_Object WHERE (word IN ("+query+") AND big = 'true') OR (word = '"+word+"' AND big = 'true');");
		rsIllustrationSmall = query("SELECT DISTINCT IMAGE_ID FROM Illustration_Object WHERE (word IN ("+query+") AND big = 'false') OR (word = '"+word+"' AND big = 'false');");
		rsCharacter = query("SELECT DISTINCT IMAGE_ID FROM Character_Object WHERE word IN ("+query+") OR word = '"+word+"';");
		rsMiddleground= query("SELECT DISTINCT IMAGE_ID FROM Middleground_Object WHERE word IN ("+query+") OR word = '"+word+"';");
		
		if (rsCharacter.next()){
			result = new WordResult(DBConstants.WORD_OBJECT_TYPE_CHARACTER,rsCharacter.getInt("IMAGE_ID"),arrayRS);
		}
		else if (rsCollectable.next()){
			result = new WordResult(DBConstants.WORD_OBJECT_TYPE_COLLECTABLE, rsCollectable.getInt("IMAGE_ID"),arrayRS);
		}
		else if (rsMiddleground.next()){
			result = new WordResult(DBConstants.WORD_OBJECT_TYPE_MIDDLEGROUND, rsMiddleground.getInt("IMAGE_ID"),arrayRS);
		}
		else if (rsIllustrationBig.next()){
			result = new WordResult(DBConstants.WORD_OBJECT_TYPE_ILLUSTRATION_BIG, rsIllustrationBig.getInt("IMAGE_ID"),arrayRS);
		}
		else if (rsIllustrationSmall.next()){
			result = new WordResult(DBConstants.WORD_OBJECT_TYPE_ILLUSTRATION_SMALL, rsIllustrationSmall.getInt("IMAGE_ID"),arrayRS);
		} else{
			result = new WordResult(DBConstants.WORD_OBJECT_TYPE_NO_IMAGE, rsIllustrationSmall.getInt("IMAGE_ID"),arrayRS);
		}
		rsCollectable.close();;
		rsIllustrationBig.close();;
		rsIllustrationSmall.close();;
		rsCharacter.close();;
		rsMiddleground.close();;
		return result;
	}
	
	/**
	 * Returns list of all users
	 * @return list of users
	 * @throws SQLException
	 */
	public List<User> queryUserList() throws SQLException {
		List<User> resultList = new ArrayList<User>();
		
		ResultSet rs = query("SELECT * FROM user;");
		while(rs.next())
			resultList.add(new User((Integer) rs.getObject("ID"), (String) rs.getObject("NAME")));
		rs.close();
		
		return resultList;
	}
	
	/**
	 * Delete user
	 * @param id UserId
	 * @return true if user was deleted successfully
	 * @throws SQLException
	 */
	public boolean deleteUserById(int id) throws SQLException {
		return conn.createStatement().execute("DELETE FROM user WHERE id=" + id + ";");
	}
	
	/**
	 * Add new User to DB
	 * @param user User-Object
	 * @return true if insert was successful
	 * @throws SQLException
	 */
	public boolean addUser(User user) throws SQLException {
		return conn.createStatement().execute("INSERT INTO user(name) VALUES ('" + user.getName() + "')");
	}
	
	
	/*
	 * @author Miriam
	 * inserts the highscore data in the highscore table
	 */
	public void addHighscore(int user_id, int level_id, int score) throws SQLException{
		ResultSet rs = query("INSERT INTO highscore (user_id,level_id,score) values ("+ user_id +", "+ level_id +", " + score +");"); 
	}
	
	
	public ArrayList<HighscoreResult> getHighscoreForLevel(int level) throws SQLException{
		ArrayList<HighscoreResult> hr = new ArrayList();
		ResultSet rs = query("SELECT * FROM highscore WHERE level_id = " + level + ";");
		
		while(rs.next()){
			hr.add(new HighscoreResult((int)(rs.getObject("user_id")), level, (int)(rs.getObject("score"))));
		}
		rs.close();
		
		return hr;
	}
	
	
	public List<SuggestionWord> getRandomSuggestioWord(int numberOfWords) {
		ArrayList<SuggestionWord> words = new ArrayList<SuggestionWord>();
		try {
			ResultSet res = query("SELECT IMAGE_ID, WORD FROM Illustration_Object WHERE big = 'true' ORDER BY RAND() LIMIT "+numberOfWords+";");
			while(res.next()) {
				SuggestionWord s = new SuggestionWord();
				s.setImageId((Integer) res.getObject("IMAGE_ID"));
				s.setWord((String) res.getObject("WORD"));
				words.add(s);
			}
			return words;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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

