package com.opticalcobra.storybear.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.hsqldb.server.Server;
import org.hsqldb.server.ServerConstants;
import org.hsqldb.server.ServerProperties;
import org.hsqldb.jdbcDriver;




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
			DBinstance = Server.main(new String[0]);
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
	
	public synchronized void query(String expression) throws SQLException {

        Statement st = null;
        ResultSet rs = null;

        st = conn.createStatement(); 

        rs = st.executeQuery(expression);

        dump(rs);
        st.close();
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
	
	
	//TODO Remove test Method
	public static void main(String[] args){
	  Database DB = new Database();
	  try {
		DB.query("SELECT * FROM category;");
		  DB.shutdown();
	} catch (SQLException e) {
		System.err.println("SQL Error");
	}

	}
	
	
	public static void dump(ResultSet rs) throws SQLException {
        ResultSetMetaData meta   = rs.getMetaData(); // TODO print metadata
        int colmax = meta.getColumnCount();
        Object o = null;

        while(rs.next() ) {
            for (int i = 0; i < colmax; ++i) {
                o = rs.getObject(i + 1);
                System.out.print(o + " ");
            }

            System.out.println(" ");
        }
    }          
}

