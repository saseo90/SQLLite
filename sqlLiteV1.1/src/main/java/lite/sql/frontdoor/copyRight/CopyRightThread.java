package lite.sql.frontdoor.copyRight;

import java.io.File;
import java.net.URL;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import lite.sql.backdoor.session.SessionThread;

public class CopyRightThread extends Thread{
	
	public static Object cc = null;
	
	public static boolean swicth = true;
	
	public void run(){
		try {
			//계속해서 시간을 보여줘야 하기 때문에 while()문 이용
			final String path = "Pictures/bgm.mp3";
		    final Media media = new Media(new File(path).toURI().toString());
		    final MediaPlayer mediaPlayer = new MediaPlayer(media);
		    mediaPlayer.play();
		    SessionThread.sleep(3000);
			
			while(swicth){
				
				Platform.runLater(()->{
					CopyRightController crc = (CopyRightController) cc;
					
					crc.settingScroll();
					
				});
				SessionThread.sleep(1);//5초(밀리단위)동안 잠시 대기
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
