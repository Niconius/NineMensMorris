package sample;

public class AiMiddlePlayer implements AiGeneral {
	
	private Player player;
	private Player ai;
	private Board board;
	private Piece[] aiPieces;
	private Piece[] playerPieces;
	private int[][] boardAsArray;
	private AiMiddlePlacing aiPlacing;
	private AiMiddleMoving aiMoving;
	private AiMiddleRemove aiRemove;
	private AiMiddleJump aiJump;
	
	public AiMiddlePlayer(Player player, Player ai, Board board) {
		this.player=player;
		this.ai=ai;
		this.board=board;
		this.aiPieces=this.ai.getPieces();
		this.playerPieces=this.player.getPieces();
		this.boardAsArray=this.board.getBoardAsArray();
		aiPlacing = new AiMiddlePlacing(this.player, this.ai, this.board);
		aiMoving = new AiMiddleMoving(this.player, this.ai, this.board);
		aiRemove = new AiMiddleRemove(this.player, this.ai, this.board);
		aiJump = new AiMiddleJump(this.player, this.ai, this.board);
	}

	// return objekt array: what type of move, old position, new position, did remove / mill happened, old (!) position of piece which got removed, did opponent / player lose
	public Object[] aiTurn() {
		if(ai.getActiveState()==true) {
			// Placing Phase
			if(ai.inPlacePhase()==true) {
				int i=0;
				for(i=0;i<9;i++) {
					if(aiPieces[i].getPosition()==0) {
						break;
					}
				}
				
				int wherePlace=aiPlacing.whereToPlace();
				ai.placePiece(aiPieces[i], wherePlace);
				
				// mill check (and win check) + remove
				
				for(int j=0;j<9;j++) {
        			ai.checkForMill(ai.getPieces()[j]);
        		}
        		boolean completelyBlocked = true;
        		for(int j=0;j<9;j++) {
        			if(board.canPieceMove(player.getPieces()[j])==true || player.hasToJump()==true) {
        				completelyBlocked=false;
        				break;
        			}
        		}
        		if(completelyBlocked==true || player.didPlayerLose()) {
        			Object[] arr = {0,0,aiPieces[i].getPosition(),false,null,true};
        			return arr;
        		}
        		if(ai.checkForMill(aiPieces[i])==true) {
        			Object[] whatHappend = removeAfterMill();
        			if((boolean) whatHappend[1]==true) {
        				Object[] arr = {0,0,aiPieces[i].getPosition(),true,whatHappend[0],true};
        				return arr;
        			}
        			else {
        				Object[] arr = {0,0,aiPieces[i].getPosition(),true,whatHappend[0],false};
        				return arr;
        			}
        		}
        		else {
        			Object[] arr = {0,0,aiPieces[i].getPosition(),false,null,false};
        			return arr;
        		}
			}
			
			// Moves normally
			else if(ai.hasToJump()==false) {
				Object[] arrMov = aiMoving.whatMoveShouldAiDo();
				int oldPosition = ((Piece) arrMov[0]).getPosition();
				int newPosition = boardAsArray[((Piece) arrMov[0]).getPosition()][(int) arrMov[1]];
				ai.movePiece(newPosition, (Piece) arrMov[0]);
				
				// mill check (and win check) + remove
				
				for(int j=0;j<9;j++) {
        			ai.checkForMill(ai.getPieces()[j]);
        		}
        		boolean completelyBlocked = true;
        		for(int j=0;j<9;j++) {
        			if(board.canPieceMove(player.getPieces()[j])==true || player.hasToJump()==true) {
        				completelyBlocked=false;
        				break;
        			}
        		}
        		if(completelyBlocked==true || player.didPlayerLose()) {
        			Object[] arr = {1,oldPosition,((Piece) arrMov[0]).getPosition(),false,null,true};
        			return arr;
        		}
        		if(ai.checkForMill((Piece) arrMov[0])==true) {
        			Object[] whatHappend = removeAfterMill();
        			if((boolean) whatHappend[1]==true) {
        				Object[] arr = {1,oldPosition,((Piece) arrMov[0]).getPosition(),true,whatHappend[0],true};
        				return arr;
        			}
        			else {
        				Object[] arr = {1,oldPosition,((Piece) arrMov[0]).getPosition(),true,whatHappend[0],false};
        				return arr;
        			}
        		}
        		else {
        			Object[] arr = {1,oldPosition,((Piece) arrMov[0]).getPosition(),false,null,false};
        			return arr;
        		}
			}
			
			// Has to jump
			else {
				Piece selectedPiece=(Piece) aiJump.whereToJump()[0];
				int whereToJump=(int) aiJump.whereToJump()[1];
				
				int oldPosition = selectedPiece.getPosition();
				ai.placePiece(selectedPiece, whereToJump);
				
				// mill check (and win check) + remove
				
				for(int j=0;j<9;j++) {
        			ai.checkForMill(ai.getPieces()[j]);
        		}
        		boolean completelyBlocked = true;
        		for(int j=0;j<9;j++) {
        			if(board.canPieceMove(player.getPieces()[j])==true || player.hasToJump()==true) {
        				completelyBlocked=false;
        				break;
        			}
        		}
        		if(completelyBlocked==true || player.didPlayerLose()) {
        			Object[] arr = {2,oldPosition,selectedPiece.getPosition(),false,null,true};
        			return arr;
        		}
        		if(ai.checkForMill(selectedPiece)==true) {
        			Object[] whatHappend = removeAfterMill();
        			if((boolean) whatHappend[1]==true) {
        				Object[] arr = {2,oldPosition,selectedPiece.getPosition(),true,whatHappend[0],true};
        				return arr;
        			}
        			else {
        				Object[] arr = {2,oldPosition,selectedPiece.getPosition(),true,whatHappend[0],false};
        				return arr;
        			}
        		}
        		else {
        			Object[] arr = {2,oldPosition,selectedPiece.getPosition(),false,null,false};
        			return arr;
        		}
			}
		}
		Object[] arr = {-1}; 
		return arr;
	}
	
	
	private Object[] removeAfterMill() {
		Piece selectedPiece=aiRemove.whatRemoveShouldAiDo();
		int oldPosition = selectedPiece.getPosition();
		System.out.println(oldPosition);
		player.removePieceFromThisPlayer(selectedPiece);
		
		
		
		for(int j=0;j<9;j++) {
			player.checkForMill(playerPieces[j]);
		}
		boolean completelyBlocked = true;
		for(int j=0;j<9;j++) {
			if(board.canPieceMove(playerPieces[j])==true || player.hasToJump()==true) {
				completelyBlocked=false;
				break;
			}
		}
		if(completelyBlocked==true || player.didPlayerLose()==true) {
			Object[] arr = {oldPosition,true};
			return arr;
		}
		else {
			Object[] arr = {oldPosition,false};
			return arr;
		}
	}
}
