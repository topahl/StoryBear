package com.opticalcobra.storybear.game;

import java.sql.SQLException;

import com.opticalcobra.storybear.db.Database;

public class Main {
	public static void main(String args[]){
		//Window gui = new Window();
		
		Database db2 = new Database();
		try {
			int dummy = db2.queryWordType("Baum");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
