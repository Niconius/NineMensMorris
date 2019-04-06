package aiStuff;

import java.util.logging.Level;
import java.util.logging.Logger;

import controller.BoardVsAiController;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class SlowAiTurnsDownRemoveThread extends Thread {
	
	private ImageView remove;
	private boolean shouldRemove;
	private boolean secondsDone;
	private boolean stopWorking;
	private BoardVsAiController main;
	private long howLong;

	public SlowAiTurnsDownRemoveThread(BoardVsAiController main, ImageView remove, boolean shouldRemove, long howLong) {
		this.remove=remove;
		this.shouldRemove=shouldRemove;
		secondsDone=false;
		this.main=main;
		stopWorking=false;
		this.howLong=howLong;
	}
	
	@Override
    public void run() {
 
        while (stopWorking==false) {
             
            // UI updaten
            Platform.runLater(new Runnable() {
				@Override
                public void run() {
					if(stopWorking==true || shouldRemove==false) {
						interrupt();
						return;
					}
					else if(secondsDone==true && stopWorking==false) {
                		remove.setImage(null);
                		secondsDone=false;
                		stopWorking=true;
                		main.setWaitFalse();
                		main.setCommunicationField("Turn Player");
                		if(main.getGameEnded()==true) {
            				main.gameEndedFunction();
            			}
                		interrupt();
                	}
                    
                }
            });
 
            // Thread schlafen
            try {
                // fuer 3 Sekunden
                sleep(howLong);
                secondsDone=true;
            } catch (InterruptedException ex) {
                Logger.getLogger(SlowAiTurnsDownThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
 
 
    }
	public boolean getDone() {
		return secondsDone;
	}
}
