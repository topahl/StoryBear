package com.opticalcobra.storybear.db;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.opticalcobra.storybear.editor.Story;
import com.opticalcobra.storybear.editor.StoryInfo;
import com.opticalcobra.storybear.game.Word;
import com.opticalcobra.storybear.main.ILevelAppearance;
import com.opticalcobra.storybear.main.User;

public class SerializableTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Database db = new Database();
		StoryInfo storyinfo = new StoryInfo();
		Story story = new Story();
		story.setAuthor(new User("Martika Schwan"));
		story.setChangeDate(new Date(System.currentTimeMillis()));
		story.setTitle("Häsel & Gretel");
		story.setVersion(2);
		story.setText("Es war einmal ein kleiner ganz dummer Hans der war blöd und sagte \"Du Säckel\"");
		ArrayList<ILevelAppearance> elements= new ArrayList<ILevelAppearance>();
		elements.add(new Word("hallo", 1234));
		storyinfo.setStory(story);
		storyinfo.setElements(elements);
		
//		db.insertStoryToDatabase(story);
//		db.insertStoryInfoToDatabase(storyinfo);
//		System.out.println(db.getStoryInfoFromDatabase(0).getStory().getText());
		
		try {
			db.shutdown();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
