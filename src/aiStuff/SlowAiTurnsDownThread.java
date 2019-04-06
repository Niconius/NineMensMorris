package aiStuff;

import java.util.logging.Level;
import java.util.logging.Logger;

import controller.BoardVsAiController;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class SlowAiTurnsDownThread extends Thread {
	
	private ImageView from, to;
	private boolean secondsDone;
	private boolean stopWorking;
	private boolean aiWantsToRemove;
	private BoardVsAiController main;
	private long howLong;

	public SlowAiTurnsDownThread(BoardVsAiController main,ImageView from, ImageView to, boolean aiWantsToRemove, long howLong) {
		this.from=from;
		this.to=to;
		secondsDone=false;
		this.main=main;
		stopWorking=false;
		this.aiWantsToRemove=aiWantsToRemove;
		this.howLong=howLong;
	}
	
	@Override
    public void run() {
 
        while (stopWorking==false) {
             
            // UI updaten
            Platform.runLater(new Runnable() {
				@Override
                public void run() {
                	if(secondsDone==true && stopWorking==false) {
                		to.setImage(from.getImage());
                		from.setImage(null);
                		if(aiWantsToRemove==false) {
                			main.setWaitFalse();
                			main.setCommunicationField("Turn Player");
                			if(main.getGameEnded()==true) {
                				main.gameEndedFunction();
                			}
                		}
                		secondsDone=false;
                		stopWorking=true;
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
