package javaFile;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class SoundManager {

		public void soundOutPut(String sound) {
	        File bgm = null;
	        AudioInputStream stream;
	        AudioFormat format;
	        DataLine.Info info;
	        
	        if (sound.equals("bgm")) {             // πË∞Ê¿Ωæ«
	        	bgm = new File("src/sounds/bgm.wav"); 
	        }else if (sound.equals("effect1")) {   // ¿Ã∆Â∆Æ1
	        	bgm = new File("src/sounds/effect_temp1.wav"); 
	        }else if (sound.equals("effect2")) {   // ¿Ã∆Â∆Æ2
	        	bgm = new File("src/sounds/effect_temp2.wav"); 
	        }else if (sound.equals("crash")) {   // √Êµπ
	        	bgm = new File("src/sounds/effect_crash.wav"); 
	        }else if (sound.equals("clear")) {   // ≈¨∏ÆæÓ
	        	bgm = new File("src/sounds/effect_clear.wav"); 
	        }else if (sound.equals("key")) {   // ø≠ºË
	        	bgm = new File("src/sounds/effect_key.wav"); 
	        }else if (sound.equals("disappear")) {   // ø≠ºË
	        	bgm = new File("src/sounds/effect_disappear.wav"); 
	        }
	        
	        Clip clip;
	        
	        try {
	               stream = AudioSystem.getAudioInputStream(bgm);
	               format = stream.getFormat();
	               info = new DataLine.Info(Clip.class, format);
	               clip = (Clip)AudioSystem.getLine(info);
	               clip.open(stream);
	               clip.start();
	               if (sound.equals("bgm")) clip.loop(-1);
	               
	        } catch (Exception e) {
	               System.out.println("err : " + e);
	               }
		
		}
}
