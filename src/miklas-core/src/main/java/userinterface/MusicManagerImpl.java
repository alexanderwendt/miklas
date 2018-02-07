package userinterface;

import java.io.File;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Format;
import javax.media.GainControl;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.Time;
import javax.media.format.AudioFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gameengine.GameEngineImpl;

public class MusicManagerImpl implements MusicManager {
	
	private static final Logger	log	= LoggerFactory.getLogger(MusicManagerImpl.class);
	
	private Player mediaPlayer;
	
	public MusicManagerImpl() {
		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn(
			"com.sun.media.codec.audio.mp3.JavaDecoder",
			new Format[]{input1, input2},
			new Format[]{output},
			PlugInManager.CODEC
		);
	}

	@Override
	public void playMusic(String path) {
		try{
			MediaLocator clipAddress = new MediaLocator(new File(path).toURI().toURL());
			
			mediaPlayer = Manager.createRealizedPlayer(clipAddress);
			
			mediaPlayer.addControllerListener(new ControllerListener() {
	            public void controllerUpdate(ControllerEvent event) {
	               if (event instanceof EndOfMediaEvent) {
	            	   log.warn("Music ended");
	            	   mediaPlayer.setMediaTime(new Time(0));
	            	   mediaPlayer.start();
	            	   
	       				//Wait for thread to be created
	       				synchronized (this) {
	       					try {
								this.wait(500);
							} catch (InterruptedException e) {

							}
	       				}
	               }
	            }
	         });
			
			
			//mediaPlayer.realize();
			//mediaPlayer.prefetch();

			//Wait for thread to be created
			synchronized (this) {
				this.wait(500);
			}
			
			//Lower volume
			GainControl x = mediaPlayer.getGainControl();
			float level = x.getLevel();
			float newLevel = level * 0.5f;
			x.setLevel(newLevel);
		
			mediaPlayer.start();

			log.debug("MusicManager> Play {}", path);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	@Override
	public void stopMusic() {
		mediaPlayer.stop();
		mediaPlayer.close();
		
	}

}
