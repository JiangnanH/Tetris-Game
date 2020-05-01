package tetris;

import java.awt.Image;

public class Cell{
	private int row;
	private int col;
	private Image image;
	private String imageNom;
	
	public Cell() {
	}

	public Cell(int row, int col, Image image, String imagenom) {
		super();
		this.row = row;
		this.col = col;
		this.image = image;
		this.imageNom = imagenom;
		
	}

	public void showCell(){
		System.out.print(" (" + row + ", " + col + ") ");
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getImageNom() {
		return imageNom;
	}

	public void setImageNom(String imageNom) {
		this.imageNom = imageNom;
	}
	public void moveRight(){
		col++;
	}
	
	public void moveLeft(){
		col--;
	}
	
	public void moveDown(){
		row++;
	}
	
	@Override
	public String toString() {
		return "["+row+","+col+"]";
	}
	@Override
	public Object clone() {
		Cell cell = null;
		try {
			cell = (Cell)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return cell;
	}
}






