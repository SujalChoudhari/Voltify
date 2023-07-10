package com.sujal.voltify;

import java.sql.SQLException;

import com.sujal.voltify.database.Database;
import com.sujal.voltify.ui.UIManager;

public class Application {

	public static Database database;
	public static UIManager uiManager;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		database = new Database("jdbc:mysql://localhost:3306/voltify", System.getenv("user"), System.getenv("pass"));
		uiManager = new UIManager();
		
		
		uiManager.launch();
	}

}
