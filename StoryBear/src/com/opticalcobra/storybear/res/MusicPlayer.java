package com.opticalcobra.storybear.res;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 
 * @author Nicolas
 *
 */
public class MusicPlayer {
	public static final String musicFile = "music\\StoryBear_Menu.wav";
	
	private static MusicPlayer instance;
	
	private Clip clip;
	private AudioInputStream audio;
	private boolean running;
	private boolean disabled = false;
	
	/**
	 * 
	 */
	private MusicPlayer() {
		try {
			audio = AudioSystem.getAudioInputStream(new File(Ressources.RESPATH+musicFile).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audio);
		} catch (IllegalArgumentException | LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			disabled = true;
		}
	}
	
	/**
	 * play sound in loop
	 */
	public void start() {
		if (disabled) return;
		running = true;
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * pause
	 */
	public void pause() {
		if (disabled) return;
		running = false;
		clip.stop();
	}
	
	/**
	 * toggle
	 */
	public boolean toggle()  {
		if (disabled) return disabled;
		if(running)
			pause();
		else
			start();
		return running;
	}
	
	/**
	 * 
	 */
	public boolean isRunning() {
		return running;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public static MusicPlayer getInstance() {
		return (instance == null) ? instance = new MusicPlayer() : instance;
	}
}
