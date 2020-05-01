package tetris;

import java.util.Arrays;
import java.util.Random;

public class Tetromino{
	
	private int index = 100000;
	private static int numberOfCarre;
	
	protected Cell[] cells = new Cell[numberOfCarre];
	protected State[] states;//states pour les blocs de 4 cellules 
	protected State1[] states1;//states pour les blocs de 5 cellules
	

	public void setIndex(int index) {
		this.index = index;
	}

	public void setCells(Cell[] cells) {
		this.cells = cells;
	}

	public void setStates(State[] states) {
		this.states = states;
	}

	public void setStates1(State1[] states1) {
		this.states1 = states1;
	}

	@Override
	public Object clone() {
		Tetromino tetro = null;
		try {
			tetro = (Tetromino)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return tetro;
	}
	public static Tetromino randomTetromino(){
		Random r = new Random();
		
		if(numberOfCarre == 4) {
			int type = r.nextInt(7);
			switch(type){
			case 0: return new T();
			case 1: return new I();
			case 2: return new J();
			case 3: return new L();
			case 4: return new O();
			case 5: return new S();
			case 6: return new Z();
			}
		}else if(numberOfCarre == 5){
			int type = r.nextInt(6);
			switch(type){
			case 0: return new T1();
			case 1: return new I1();
			case 2: return new L1();
			case 3: return new O1();
			case 4: return new S1();
			case 5: return new Z1();
			}
		}
		return null;
	}
	
	public Cell[] getCells() {
		
		return this.cells;
	}

	public Cell[] copyCells() {

		Cell[] c = new Cell[numberOfCarre];

		for (int i =0; i < numberOfCarre; i++) {
			c[i] = Tetris.copyCell(cells[i]);
		}

		return c;
	}

	public void softDrop(){
		for(int i=0; i<cells.length; i++){
			cells[i].moveDown();
		}
	}
	public void moveRight(){
		for(int i=0; i<cells.length; i++){
			this.cells[i].moveRight();
		}
	} 
	public void moveLeft(){
		for(int i=0; i<cells.length; i++){
			cells[i].moveLeft();
		}
	}
	
	public int getnumberOfCarre() {
		return numberOfCarre;
	}
	
	public static void setnumberOfCarre(int n) {
		numberOfCarre = n;
	}	
	
	public int getIndex() {
		return index;
	}

	public State[] getStates() {
		return this.states;
	}

	public State[] copyStates() {
		State[] s = new State[states.length];
		for(int i= 0; i< this.states.length; i++) {
			if (states[i] == null) {
				System.out.print("states is null");
			}
			s[i] = this.states[i].copyState();
		}

		return s;
	}

	public State1[] getStates1() {
		State1[] s1 = new State1[8];
		return s1;
	}
	

	public void rotateRight() {
		index++;
		if(numberOfCarre == 4) {
			State s = states[index%states.length];
			Cell o = cells[0];
			cells[1].setRow(o.getRow()+s.row1);
			cells[1].setCol(o.getCol()+s.col1);
			cells[2].setRow(o.getRow()+s.row2);
			cells[2].setCol(o.getCol()+s.col2);
			cells[3].setRow(o.getRow()+s.row3);
			cells[3].setCol(o.getCol()+s.col3);
		}else {
			State1 s = states1[index%states1.length];
			Cell o = cells[0];
			cells[1].setRow(o.getRow()+s.row1);
			cells[1].setCol(o.getCol()+s.col1);
			cells[2].setRow(o.getRow()+s.row2);
			cells[2].setCol(o.getCol()+s.col2);
			cells[3].setRow(o.getRow()+s.row3);
			cells[3].setCol(o.getCol()+s.col3);
			cells[4].setRow(o.getRow()+s.row4);
			cells[4].setCol(o.getCol()+s.col4);
		}

	}
	
	public void rotateLeft() {
		index--;
		if(numberOfCarre == 4) {
			State s = states[index%states.length];
			Cell o = cells[0];
			cells[1].setRow(o.getRow()+s.row1);
			cells[1].setCol(o.getCol()+s.col1);
			cells[2].setRow(o.getRow()+s.row2);
			cells[2].setCol(o.getCol()+s.col2);
			cells[3].setRow(o.getRow()+s.row3);
			cells[3].setCol(o.getCol()+s.col3);
		}else {
			State1 s = states1[index%states.length];
			Cell o = cells[0];
			cells[1].setRow(o.getRow()+s.row1);
			cells[1].setCol(o.getCol()+s.col1);
			cells[2].setRow(o.getRow()+s.row2);
			cells[2].setCol(o.getCol()+s.col2);
			cells[3].setRow(o.getRow()+s.row3);
			cells[3].setCol(o.getCol()+s.col3);
			cells[4].setRow(o.getRow()+s.row4);
			cells[4].setCol(o.getCol()+s.col4);
		}
	}

	
	@Override
	public String toString() {
		return Arrays.toString(cells); 
	}
	
	public class State{
		public int row0,col0,row1,col1,row2,col2,row3,col3;

		public State(int row0, int col0, int row1, int col1,
				int row2, int col2,
				int row3, int col3) {
			this.row0 = row0;
			this.col0 = col0;
			this.row1 = row1;
			this.col1 = col1;
			this.row2 = row2;
			this.col2 = col2;
			this.row3 = row3;
			this.col3 = col3;
		}

		public State copyState() {
			State copyS = new State(row0, col0, row1,col1,
					row2, col2,
					row3, col3);

			return copyS;
		}
	}
	
	protected class State1 implements Cloneable{
		int row0,col0,row1,col1,row2,col2,row3,col3,row4,col4;

		public State1(int row0, int col0, int row1, int col1,
				int row2, int col2,
				int row3, int col3, 
				int row4, int col4) {
			this.row0 = row0;
			this.col0 = col0;
			this.row1 = row1;
			this.col1 = col1;
			this.row2 = row2;
			this.col2 = col2;
			this.row3 = row3;
			this.col3 = col3;
			this.row4 = row4;
			this.col4 = col4;
		}	  
	}
	
}

//les diff¨¦rentes formes de bloc sont une sous-classe de Tetromino
class T extends Tetromino{
	public T() {
		cells[0] = new Cell(0, 4, Tetris.T, "imageT");
		cells[1] = new Cell(0, 3, Tetris.T, "imageT");
		cells[2] = new Cell(0, 5, Tetris.T, "imageT");
		cells[3] = new Cell(1, 4, Tetris.T, "imageT");
		states = new State[]{
				new State(0,0, 0,-1, 0,1, 1, 0),
				new State(0,0, -1,0, 1,0, 0,-1),
				new State(0,0, 0,1,  0,-1, -1,0),
				new State(0,0, 1,0, -1,0, 0,1)};
	}
}
class I extends Tetromino{
	public I() {
		cells[0] = new Cell(0, 4, Tetris.I, "imageI");
		cells[1] = new Cell(0, 3, Tetris.I, "imageI");
		cells[2] = new Cell(0, 5, Tetris.I, "imageI");
		cells[3] = new Cell(0, 6, Tetris.I, "imageI");
		states = new State[]{
				new State(0,0, 0,-1, 0,1, 0,2),
				new State(0,0, -1,0, 1,0,2,0)};
	}
}
class L extends Tetromino {
	public L() {
		cells[0] = new Cell(0, 4, Tetris.L, "imageL");
		cells[1] = new Cell(0, 3, Tetris.L, "imageL");
		cells[2] = new Cell(0, 5, Tetris.L, "imageL");
		cells[3] = new Cell(1, 3, Tetris.L, "imageL");
		states = new State[]{
				new State(0,0, 0,-1, 0,1, 1,-1 ),
				new State(0,0, -1,0, 1,0, -1,-1),
				new State(0,0, 0,1, 0,-1, -1,1),
				new State(0,0, 1,0, -1,0, 1,1)};	
	}
}

class J extends Tetromino {
	public J() {
		cells[0] = new Cell(0, 4, Tetris.J, "imageJ");
		cells[1] = new Cell(0, 3, Tetris.J, "imageJ");
		cells[2] = new Cell(0, 5, Tetris.J, "imageJ");
		cells[3] = new Cell(1, 5, Tetris.J, "imageJ");
		states = new State[]{
				new State(0,0, 0,-1, 0,1, 1,1),
				new State(0,0, -1,0, 1,0, 1,-1),
				new State(0,0, 0,1, 0,-1, -1,-1),
				new State(0,0, 1,0, -1,0, -1,1 )};
	}
}

class S extends Tetromino {
	public S() {
		cells[0] = new Cell(0, 4, Tetris.S, "imageS");
		cells[1] = new Cell(0, 5, Tetris.S, "imageS");
		cells[2] = new Cell(1, 3, Tetris.S, "imageS");
		cells[3] = new Cell(1, 4, Tetris.S, "imageS");
		states = new State[]{
			new State(0,0, 0,1, 1,-1, 1,0 ),
			new State(0,0, -1,0, 1,1, 0,1 )};
	}
}

class Z extends Tetromino {
	public Z() {
		cells[0] = new Cell(1, 4, Tetris.Z, "imageZ");
		cells[1] = new Cell(0, 3, Tetris.Z, "imageZ");
		cells[2] = new Cell(0, 4, Tetris.Z, "imageZ");
		cells[3] = new Cell(1, 5, Tetris.Z, "imageZ");
		states = new State[]{
				new State(0,0, -1,-1, -1,0, 0,1 ),
				new State(0,0, -1,1, 0,1, 1,0 )};
	}
}

class O extends Tetromino {
	public O() {
		cells[0] = new Cell(0, 4, Tetris.O, "imageO");
		cells[1] = new Cell(0, 5, Tetris.O, "imageO");
		cells[2] = new Cell(1, 4, Tetris.O, "imageO");
		cells[3] = new Cell(1, 5, Tetris.O, "imageO");
		states = new State[]{
				new State(0,0, 0,1, 1,0, 1,1 ),
				new State(0,0, 0,1, 1,0, 1,1 )};
	}
}

class T1 extends Tetromino{
	public T1() {
		cells[0] = new Cell(0, 4, Tetris.T, "imageT");
		cells[1] = new Cell(0, 3, Tetris.T, "imageT");
		cells[2] = new Cell(0, 5, Tetris.T, "imageT");
		cells[3] = new Cell(1, 4, Tetris.T, "imageT");
		cells[4] = new Cell(2, 4, Tetris.T, "imageT");
		states1 = new State1[]{
				new State1(0,0, 0,-1, 0,1, 1,0, 2,0),
				new State1(0,0, -1,0, 1,0, 0,-1, 0,-2),
				new State1(0,0, 0,1,  0,-1, -1,0, -2,0),
				new State1(0,0, 1,0, -1,0, 0,1, 0,2)};
	}
}

class I1 extends Tetromino{
	public I1() {
		cells[0] = new Cell(0, 4, Tetris.I, "imageI");
		cells[1] = new Cell(0, 3, Tetris.I, "imageI");
		cells[2] = new Cell(0, 5, Tetris.I, "imageI");
		cells[3] = new Cell(0, 6, Tetris.I, "imageI");
		cells[4] = new Cell(0, 7, Tetris.I, "imageI");
		states1 = new State1[]{
				new State1(0,0, 0,-1, 0,1, 0,2, 0,3),
				new State1(0,0, -1,0, 1,0,2,0, 3,0)};
	}
}

class L1 extends Tetromino {
	public L1() {
		cells[0] = new Cell(0, 3, Tetris.L, "imageL");
		cells[1] = new Cell(0, 4, Tetris.L, "imageL");
		cells[2] = new Cell(0, 5, Tetris.L, "imageL");
		cells[3] = new Cell(1, 3, Tetris.L, "imageL");
		cells[4] = new Cell(2, 3, Tetris.L, "imageL");
		states1 = new State1[]{
				new State1(0,0, 0,1, 0,2, 1,0, 2,0 ),
				new State1(0,0, 1,0, 2,0, 0,-1, 0,-2),
				new State1(0,0, 0,-1, 0,-2, -1,0, -2,0),
				new State1(0,0, -1,0, -2,0, 0,1, 0,2)};
	}
}

class Z1 extends Tetromino {
	public Z1() {
		cells[0] = new Cell(1, 4, Tetris.L, "imageL");
		cells[1] = new Cell(1, 3, Tetris.L, "imageL");
		cells[2] = new Cell(1, 5, Tetris.L, "imageL");
		cells[3] = new Cell(2, 3, Tetris.L, "imageL");
		cells[4] = new Cell(0, 5, Tetris.L, "imageL");
		states1 = new State1[]{
				new State1(0,0, 0,-1, 0,1, 1,-1, -1,1 ),
				new State1(0,0, -1,0, 1,0, -1,-1, 1,1),
				new State1(0,0, 0,1, 0,-1, -1,1, 1,-1),
				new State1(0,0, 1,0, -1,0, 1,1, -1,-1)};	
	}
}

class S1 extends Tetromino {
	public S1() {
		cells[0] = new Cell(1, 4, Tetris.J, "imageJ");
		cells[1] = new Cell(1, 3, Tetris.J, "imageJ");
		cells[2] = new Cell(1, 5, Tetris.J, "imageJ");
		cells[3] = new Cell(2, 5, Tetris.J, "imageJ");
		cells[4] = new Cell(0, 3, Tetris.J, "imageJ");
		states1 = new State1[]{
				new State1(0,0, 0,-1, 0,1, 1,1, -1,-1),
				new State1(0,0, -1,0, 1,0, 1,-1, -1,1),
				new State1(0,0, 0,1, 0,-1, -1,-1, 1,1),
				new State1(0,0, 1,0, -1,0, -1,1, 1,-1)};
	}
}

class O1 extends Tetromino {
	public O1() {
		cells[0] = new Cell(1, 4, Tetris.O, "imageO");
		cells[1] = new Cell(1, 5, Tetris.O, "imageO");
		cells[2] = new Cell(1, 3, Tetris.O, "imageO");
		cells[3] = new Cell(0, 4, Tetris.O, "imageO");
		cells[4] = new Cell(2, 4, Tetris.O, "imageO");
		states1 = new State1[]{
				new State1(0,0, 0,1, 0,-1, -1,0, 1,0),
				new State1(0,0, 0,1, 0,-1, -1,0, 1,0)};
	}
}









