package com.opticalcobra.storybear.game;

import java.sql.SQLException;
import java.util.Date;

import com.opticalcobra.storybear.db.Database;
import com.opticalcobra.storybear.editor.Loadingscreen;
import com.opticalcobra.storybear.editor.Story;
import com.opticalcobra.storybear.editor.StoryInfo;
import com.opticalcobra.storybear.editor.TextAnalyzer;
import com.opticalcobra.storybear.main.User;
import com.opticalcobra.storybear.menu.Menu;


public class Main {
	public static void main(String args[]){
		Loadingscreen ls = new Loadingscreen();
//		Menu menu = new Menu();
//		Window gui = new Window();
//		
//		TextAnalyzer ta = new TextAnalyzer();
//		Database db = new Database();
//		StoryInfo storyInfo = new StoryInfo();
//		Story currentStory = new Story();
//
//		String story = "";
//		User a = new User("Tiki");
//		currentStory.setAuthor(a);
//		currentStory.setTitle("Character Story");
//		currentStory.setChangeDate(new Date());
//		currentStory.setVersion(1);
//		story = "Fee Frau Geist Hexe J�ger Junge Sohn Kaufmann H�ndler K�nig Herrscher K�nigin Herrscherin Kind M�dchen Jungfrau Mann Oma Gro�mutter, Opa Gro�vater, Prinz K�nigssohn, Prinzessin, R�uber Dieb Teufel Zauberer Zauberin Zwerg Sandmann Vater Mutter Agent Spion ";
//		for (int i=1; i<4; i++){
//			story = story + story;
//		}
//		currentStory.setText(story);
//		db.insertStoryToDatabase(currentStory);
//		storyInfo = ta.analyzeText(currentStory);
//		db.insertStoryInfoToDatabase(storyInfo);
//		
//		System.out.print("feddisch!1");
//		
//		currentStory.setTitle("Things Story");
//		story = "Baum Blume Stein Sternschnuppe Teich Brot Feuer Lagerfeuer Gold M�nze Perle Edelstein Silber Grab Sarg Kraut Kr�uter Leiter Schatztruhe Schl�ssel S��igkeiten Stuhl Tisch Tuch Krone 3 7 100 Teddyb�r ";
//		for (int i=1; i<5; i++){
//			story = story + story;
//		}
//		currentStory.setText(story);
//		db.insertStoryToDatabase(currentStory);
//		storyInfo = ta.analyzeText(currentStory);
//		db.insertStoryInfoToDatabase(storyInfo);
//		
//		System.out.print("feddisch!1");
//		currentStory.setTitle("Animals Story");
//		story = "B�r Ente Frosch Froschk�nig Fuchs Gans Hund Rabe Schlange K�nguru Katze Vogel ";
//		for (int i=1; i<6; i++){
//			story = story + story;
//		}
//		currentStory.setText(story);
//		db.insertStoryToDatabase(currentStory);
//		storyInfo = ta.analyzeText(currentStory);
//		db.insertStoryInfoToDatabase(storyInfo);
//		
//		System.out.print("feddisch!1");
//		currentStory.setTitle("Collectables Story");
//		story = "M�nze Apfel Brot 1 2 3 4 5 ";
//		for (int i=1; i<7; i++){
//			story = story + story;
//		}
//		currentStory.setText(story);
//		db.insertStoryToDatabase(currentStory);
//		storyInfo = ta.analyzeText(currentStory);
//		db.insertStoryInfoToDatabase(storyInfo);
//		System.out.print("feddisch!1");
//		
//		currentStory.setTitle("Buildings Story");
//		story = "Brunnen Burg H�tte Haus Lebkuchenhaus M�hle Ofen H�hle Grube Mauer Turm Fahnen ";
//		for (int i=1; i<6; i++){
//			story = story + story;
//		}
//		currentStory.setText(story);
//		db.insertStoryToDatabase(currentStory);
//		storyInfo = ta.analyzeText(currentStory);
//		db.insertStoryInfoToDatabase(storyInfo);
//		System.out.print("feddisch!1");
//		
//		currentStory.setTitle("Vehicle Story");
//		story = "Kutsche Schiff Wagen Boot Flo� ";
//		for (int i=1; i<6; i++){
//			story = story + story;
//		}
//		currentStory.setText(story);
//		db.insertStoryToDatabase(currentStory);
//		storyInfo = ta.analyzeText(currentStory);
//		db.insertStoryInfoToDatabase(storyInfo);
//		
//		System.out.print("feddisch!1");
//		currentStory.setTitle("Landscape Story");
//		story = "Wald Wald Wald Stadt Stadt Dorf Schloss Felder Land Felder Land ";
//		for (int i=1; i<5; i++){
//			story = story + story;
//		}
//		currentStory.setText(story);
//		db.insertStoryToDatabase(currentStory);
//		storyInfo = ta.analyzeText(currentStory);
//		db.insertStoryInfoToDatabase(storyInfo);
//
//		
//		System.out.print("feddisch!1");
//		currentStory.setTitle("Grafik Test alles");
//		currentStory.setText("Charaktere: Fee Frau Geist Hexe J�ger Junge Sohn Kaufmann H�ndler K�nig Herrscher K�nigin Herrscherin Kind M�dchen Jungfrau Mann Oma Gro�mutter, Opa Gro�vater, Prinz K�nigssohn, Prinzessin, R�uber Dieb Teufel Zauberer Zauberin Zwerg Sandmann Vater Mutter Agent Spion "
//				+ " Things: Baum Blume Stein Sternschnuppe Teich Brot Feuer Lagerfeuer Gold M�nze Perle Edelstein Silber Grab Sarg Kraut Kr�uter Leiter Schatztruhe Schl�ssel S��igkeiten Stuhl Tisch Tuch Krone 3 7 100 Teddyb�r "
//				+ " Tiere: B�r Ente Frosch Froschk�nig Fuchs Gans Hund Rabe Schlange K�nguru Katze Vogel "
//				+ " Collectables: M�nze Apfel Brot 1 2 3 4 5 "
//				+ " Geb�ude: Brunnen Burg H�tte Haus Lebkuchenhaus M�hle Ofen H�hle Grube Mauer Turm Fahnen "
//				+ " Fahrzeuge: Kutsche Schiff Wagen Boot Flo� "
//				+ " Landscape: Wald Wald Wald Stadt Stadt Dorf Schloss Felder Land Felder Land");
//		currentStory.setText(story);
//		db.insertStoryToDatabase(currentStory);
//		storyInfo = ta.analyzeText(currentStory);
//		db.insertStoryInfoToDatabase(storyInfo);
//		
//		System.out.print("feddisch!1");
//		
//		
//		currentStory.setTitle("Rotk�ppchen");
//		currentStory.setText("Es war einmal ein kleines s��es M�dchen, das hatte jedermann lieb, der sie nur ansah, am allerliebsten aber ihre Gro�mutter, die wusste gar nicht, was sie alles dem Kinde geben sollte. Einmal schenkte sie ihm ein K�ppchen von rotem Samt, und weil ihm das sowohl stand, und es nichts anders mehr tragen wollte, hie� es nur das Rotk�ppchen. Eines Tages sprach seine Mutter zu ihm: \"Komm, Rotk�ppchen, da hast du ein St�ck Kuchen und eine Flasche Wein, bring das der Gro�mutter hinaus; sie ist krank und schwach und wird sich daran laben. Mach dich auf, bevor es hei� wird, und wenn du hinauskommst, so geh h�bsch sittsam und lauf nicht vom Wege ab, sonst f�llst du und zerbrichst das Glas, und die Gro�mutter hat nichts. Und wenn du in ihre Stube kommst, so vergiss nicht guten Morgen zu sagen und guck nicht erst in allen Ecken herum!\" \"Ich will schon alles richtig machen�, sagte Rotk�ppchen zur Mutter, und gab ihr die Hand darauf. Die Gro�mutter aber wohnte drau�en im Wald, eine halbe Stunde vom Dorf. Wie nun Rotk�ppchen in den Wald kam, begegnete ihm der Wolf. Rotk�ppchen aber wusste nicht, was das f�r ein b�ses Tier war, und f�rchtete sich nicht vor ihm. \"Guten Tag, Rotk�ppchen!\" sprach er. \"Sch�nen Dank, Wolf!\" - \"Wo hinaus so fr�h, Rotk�ppchen?\" - \"Zur Gro�mutter.\" - \"Was tr�gst du unter der Sch�rze?\" - \"Kuchen und Wein. Gestern haben wir gebacken, da soll sich die kranke und schwache Gro�mutter etwas zugut tun und sich damit st�rken.\" - \"Rotk�ppchen, wo wohnt deine Gro�mutter?\" - \"Noch eine gute Viertelstunde weiter im Wald, unter den drei gro�en Eichb�umen, da steht ihr Haus, unten sind die Nusshecken, das wirst du ja wissen, � sagte Rotk�ppchen. Der Wolf dachte bei sich: Das junge, zarte Ding, das ist ein fetter Bissen, der wird noch besser schmecken als die Alte. Du musst es listig anfangen, damit du beide schnappst. Da ging er ein Weilchen neben Rotk�ppchen her, dann sprach er: \"Rotk�ppchen, sieh einmal die sch�nen Blumen, die ringsumher stehen. Warum guckst du dich nicht um? Ich glaube, du h�rst gar nicht, wie die V�glein so lieblich singen? Du gehst ja f�r dich hin, als wenn du zur Schule gingst, und ist so lustig drau�en in dem Wald.\" Rotk�ppchen schlug die Augen auf, und als es sah, wie die Sonnenstrahlen durch die B�ume hin und her tanzten und alles voll sch�ner Blumen stand, dachte es: Wenn ich der Gro�mutter einen frischen Strau� mitbringe, der wird ihr auch Freude machen; es ist so fr�h am Tag, dass ich doch zu rechter Zeit ankomme, lief vom Wege ab in den Wald hinein und suchte Blumen. Und wenn es eine gebrochen hatte, meinte es, weiter hinaus st�nde eine sch�nere, und lief danach und geriet immer tiefer in den Wald hinein. Der Wolf aber ging geradewegs nach dem Haus der Gro�mutter und klopfte an die T�re. \"Wer ist drau�en?\" - \"Rotk�ppchen, das bringt Kuchen und Wein, mach auf!\" - \"Dr�ck nur auf die Klinke!\" rief die Gro�mutter, \"ich bin zu schwach und kann nicht aufstehen.\" Der Wolf dr�ckte auf die Klinke, die T�re sprang auf und er ging, ohne ein Wort zu sprechen, gerade zum Bett der Gro�mutter und verschluckte sie. Dann tat er ihre Kleider an, setzte ihre Haube auf, legte sich in ihr Bett und zog die Vorh�nge vor. Rotk�ppchen aber, war nach den Blumen herumgelaufen, und als es so viel zusammen hatte, dass es keine mehr tragen konnte, fiel ihm die Gro�mutter wieder ein, und es machte sich auf den Weg zu ihr. Es wunderte sich, dass die T�r aufstand, und wie es in die Stube trat, so kam es ihm so seltsam darin vor, dass es dachte: Ei, du mein Gott, wie �ngstlich wird mir's heute zumut, und bin sonst so gerne bei der Gro�mutter! Es rief: \"Guten Morgen�, bekam aber keine Antwort. Darauf ging es zum Bett und zog die Vorh�nge zur�ck. Da lag die Gro�mutter und hatte die Haube tief ins Gesicht gesetzt und sah so wunderlich aus. \"Ei, Gro�mutter, was hast du f�r gro�e Ohren!\" - \"Dass ich dich besser h�ren kann!\" - \"Ei, Gro�mutter, was hast du f�r gro�e Augen!\" - \"Dass ich dich besser sehen kann!\" - \"Ei, Gro�mutter, was hast du f�r gro�e H�nde!\" - \"Dass ich dich besser packen kann!\" - \"Aber, Gro�mutter, was hast du f�r ein entsetzlich gro�es Maul!\" - \"Dass ich dich besser fressen kann!\" Kaum hatte der Wolf das gesagt, so tat er einen Satz aus dem Bette und verschlang das arme Rotk�ppchen. Wie der Wolf seinen Appetit gestillt hatte, legte er sich wieder ins Bett, schlief ein und fing an, �berlaut zu schnarchen. Der J�ger ging eben an dem Haus vorbei und dachte: Wie die alte Frau schnarcht! Du musst doch sehen, ob ihr etwas fehlt. Da trat er in die Stube, und wie er vor das Bette kam, so sah er, dass der Wolf darin lag. \"Finde ich dich hier, du alter S�nder�, sagte er, \"ich habe dich lange gesucht.\" Nun wollte er seine B�chse anlegen, da fiel ihm ein, der Wolf k�nnte die Gro�mutter gefressen haben und sie w�re noch zu retten, schoss nicht, sondern nahm eine Schere und fing an, dem schlafenden Wolf den Bauch aufzuschneiden. Wie er ein paar Schnitte getan hatte, da sah er das rote K�ppchen leuchten, und noch ein paar Schnitte, da sprang das M�dchen heraus und rief: \"Ach, wie war ich erschrocken, wie war's so dunkel in dem Wolf seinem Leib!\" Und dann kam die alte Gro�mutter auch noch lebendig heraus und konnte kaum atmen. Rotk�ppchen aber holte geschwind gro�e Steine, damit f�llten sie dem Wolf den Leib, und wie er aufwachte, wollte er fortspringen, aber die Steine waren so schwer, dass er gleich niedersank und sich totfiel. Da waren alle drei vergn�gt. Der J�ger zog dem Wolf den Pelz ab und ging damit heim, die Gro�mutter a� den Kuchen und trank den Wein, den Rotk�ppchen gebracht hatte, und erholte sich wieder; Rotk�ppchen aber dachte: Du willst dein Lebtag nicht wieder allein vom Wege ab in den Wald laufen, wenn dir's die Mutter verboten hat. Es wird auch erz�hlt, dass einmal, als Rotk�ppchen der alten Gro�mutter wieder Gebackenes brachte, ein anderer Wolf es angesprochen und vom Wege habe ableiten wollen. Rotk�ppchen aber h�tete sich und ging geradefort seines Wegs und sagte der Gro�mutter, dass es dem Wolf begegnet w�re, der ihm guten Tag gew�nscht, aber so b�s aus den Augen geguckt h�tte: \"Wenn's nicht auf offener Stra�e gewesen w�re, er h�tte mich gefressen.\" - \"Komm, � sagte die Gro�mutter, \"wir wollen die T�re verschlie�en, dass er nicht hereinkann.\" Bald danach klopfte der Wolf an und rief: \"Mach auf, Gro�mutter, ich bin das Rotk�ppchen, ich bring dir Gebackenes.\" Sie schwiegen aber und machten die T�re nicht auf. Da schlich der Graukopf etlichemal um das Haus, sprang endlich aufs Dach und wollte warten, bis Rotk�ppchen abends nach Hause ginge, dann wollte er ihm nachschleichen und wollt's in der Dunkelheit fressen. Aber die Gro�mutter merkte, was er im Sinne hatte. Nun stand vor dem Haus ein gro�er Steintrog, Da sprach sie zu dem Kind: \"Nimm den Eimer, Rotk�ppchen, gestern hab ich W�rste gekocht, da trag das Wasser, worin sie gekocht sind, in den Trog!\" Rotk�ppchen trug so lange, bis der gro�e, gro�e Trog ganz voll war. Da stieg der Geruch von den W�rsten dem Wolf in die Nase. Er schnupperte und guckte hinab, endlich machte er den Hals so lang, dass er sich nicht mehr halten konnte, und anfing zu rutschen; so rutschte er vom Dach herab, gerade in den gro�en Trog hinein und ertrank. Rotk�ppchen aber ging fr�hlich nach Haus, und von nun an tat ihm niemand mehr etwas zuleide.");
//		currentStory.setText(story);
//		db.insertStoryToDatabase(currentStory);
//		storyInfo = ta.analyzeText(currentStory);
//		db.insertStoryInfoToDatabase(storyInfo);
//		System.out.print("feddisch!1");
//		
//		currentStory.setTitle("Apfel Story");
//		currentStory.setText("Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel Apfel");
//		currentStory.setText(story);
//		db.insertStoryToDatabase(currentStory);
//		storyInfo = ta.analyzeText(currentStory);
//		db.insertStoryInfoToDatabase(storyInfo);
//		System.out.print("feddisch!1");
//		
//	
//		currentStory.setTitle("Lange W�rter Geschichte");
//		story = "Laaaaaaaaaaaaaaaaaaaaaaaange W������������������������������������������������������������������rter siiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiind einfach vooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooll nervig! Das stimmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmt ihr mir alle zuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu, oder? ";
//		currentStory.setText(story);
//		db.insertStoryToDatabase(currentStory);
//		storyInfo = ta.analyzeText(currentStory);
//		db.insertStoryInfoToDatabase(storyInfo);
//		System.out.print("feddisch!!!");
	}
}
