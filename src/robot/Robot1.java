package robot;

import java.util.ArrayList;
import tetris.Cell;
import tetris.Tetris;

public class Robot1{
	private int width;
	private int height;
	private Tetris t1;
	private int[] ScoreRow ;
	private int ScoreSide = 100;
	private int ScoreUP = 5000;
	private int Scoreblock = 1000;
	private int checkBoard = 0;
	public ArrayList<Integer> HandlerList = new ArrayList<Integer>();//tableau pour enrgistrer le meilleur chemin calcule par le mode automatique
	public Robot1(int height, int width) {
		super();
		this.width = width;
		this.height = height;
		ScoreRow = new int[height];
		setScoreRow();
	}
	
	//calculer les points de chaque chemin possible
	public void calcWay(Tetris tetris) {
		HandlerList.clear();
		int minScore = 0x7FFFFFFF;
		int handler = 0;
		int k = 0;
		int typetimes = 0;
		for(int i= -width+1;i<width;i++) {
			t1 = tetris.copy();
			int type = t1.getTetromino().getStates().length;
			for(int t = 0; t<type;t++) {
				k=i;
				t1 = tetris.copy();
				Cell[] cells = t1.getTetromino().getCells();
				for(int m=0; m<cells.length; m++){
					Cell cell = cells[m];
					int row = cell.getRow();
					int col = cell.getCol();
					if(row == 19 || col == 0) {
						checkBoard = 1;
						break;
					}
				}
				
				if(checkBoard == 0) {
					for(int kt = 0; kt<t; kt++) {
						t1.handler(0);
					}
				}
				
				while(k!=0){
					if(k<0) {
						t1.handler(3);
						k++;
					}
					else {
						t1.handler(1);					
						k--;
					}
				}
				t1.handler(2);
				int Score = calcScore();
				checkBoard = 0;
				if(minScore>Score) {
					minScore = Score;
					handler = i;
					typetimes = t;
				}
			}
		}

		for(int i=0;i<typetimes;i++) {
			HandlerList.add(0);
		}
		if(handler<0) {
			HandlerList.add(3);
		}
		else if(handler>0) {
			HandlerList.add(1);
		}

	}
	
	//fonction pour calculer les points total d'un chemin
	private int calcScore() {
		int score = 0;
		
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				if(t1.wall[i][j]!=null) {
					score += Scoreblock-ScoreRow[i];
				}
				else if(t1.wall[i][j]==null) {
					score += getsurroundScore(i, j);
				}
			}
		}
		return score;
	}
	
	private void setScoreRow() {
		for(int i=0;i<height;i++) {
			ScoreRow[i] = i*10;
		}
	}
	
	private int getsurroundScore(int y,int x) {
		int score = 0;
		if(x==0) {
			score += ScoreSide;
			if(t1.wall[y][x+1]!=null) {
				score += ScoreSide;
			}
			
		}
		else if(x==(width-1)) {
			score += ScoreSide;
			if(t1.wall[y][x-1]!=null) {
				score += ScoreSide;
			}
		}
		else {
			if(t1.wall[y][x-1]!=null) {
				score += ScoreSide;
			}
			if(t1.wall[y][x+1]!=null) {
				score += ScoreSide;
			}
		}
		
		if(y!=0&&t1.wall[y-1][x]!=null) {
			score += ScoreUP;
		}
		
		return score;
	}
	
}
