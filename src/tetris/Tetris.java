package tetris;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import robot.Robot1;

@SuppressWarnings("deprecation")
public class Tetris extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private String message;
	private Tetromino tetromino;

	private Tetromino nextOne;
	public static final int ROWS = 20;
	public static final int COLS = 10;
	public Cell[][] wall = new Cell[ROWS][COLS];
	private int score = 0;
	private int level = 1;
	private int languageLabel;
	private static int record = 0;
	private Robot1 ai = new Robot1(ROWS,COLS);
	private static int compteur = 300;//compteur utilisé pour que le mode automatique peut durer certain temps
	private static int checkCompteur = 0;
	private static int checkSport = 0;//un index pour distinguer si c'est le botton "S" qui est appuyé, si oui on ignore le compteur 


	public static final int CELL_SIZE = 26;

	private static Image background;
	public static Image I;
	public static Image J;
	public static Image L;
	public static Image S;
	public static Image Z;
	public static Image O;
	public static Image T;
	
	public static String help;

	public static String imageI;
	public static String imageJ;
	public static String imageL;
	public static String imageO;
	public static String imageS;
	public static String imageT;
	public static String imageZ;

	
	//fonction pour tester si tous les cellules sont bien enregistrer dans le wall[][] 
	public void showWall(){
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (wall[i][j] == null) {
					continue;
				}
				wall[i][j].showCell();
			}
			System.out.println(" ");
		}
	}
	
	public Tetromino getTetromino() {
		return this.tetromino;
	}

	//copier l'objet Tetromino
	public Tetromino  copyTetromino(Tetromino tetromino) {
		Tetromino tetro = new Tetromino();
		tetro.setCells(tetromino.copyCells());
		for (int i=0; i<tetromino.states.length; i++) {
			if (tetromino.states[i]==null) {
				System.out.println("before null");
			}
		}
		tetro.setStates(tetromino.copyStates());
		for (int i=0; i<tetro.states.length; i++) {
			if (tetro.states[i]==null) {
				System.out.println("after null");
			}
		}
		tetro.setStates1(tetromino.getStates1());
		return tetro;
	}

	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	static {
		try {
			 Properties props=new Properties();
			 FileInputStream iFile = new FileInputStream("C:\\Users\\jn\\Desktop\\MyTetris\\src\\tetris\\a.properties");
			 props.load(iFile);//télécharger la fiche des resources
			 
			 help=props.getProperty("help");
			 imageI=props.getProperty("imageI");
			 imageJ=props.getProperty("imageJ");
			 imageL=props.getProperty("imageL");
			 imageO=props.getProperty("imageO");
			 imageS=props.getProperty("imageS");
			 imageT=props.getProperty("imageT");
			 imageZ=props.getProperty("imageZ");
			 iFile.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	static{
		try{
			background = ImageIO.read(
				Tetris.class.getResource(".\\texture\\tetris.png"));
			I=ImageIO.read(Tetris.class.getResource(imageI));
			J=ImageIO.read(Tetris.class.getResource(imageJ));
			L=ImageIO.read(Tetris.class.getResource(imageL));
			O=ImageIO.read(Tetris.class.getResource(imageO));
			S=ImageIO.read(Tetris.class.getResource(imageS));
			T=ImageIO.read(Tetris.class.getResource(imageT));
			Z=ImageIO.read(Tetris.class.getResource(imageZ));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Tetris() {}
	
	//copier l'objet Tetris 
	public Tetris copy() {
		
		Tetris t = new Tetris();
		for(int i=0; i<ROWS; i++) {
			for(int j=0; j<COLS; j++)
				if (wall[i][j] == null) {
					t.wall[i][j] = null;
				} else {
					Cell c = copyCell(wall[i][j]);
					t.wall[i][j] = c;
				}
		}
		t.tetromino = this.copyTetromino(this.tetromino);
		t.nextOne = this.copyTetromino(this.nextOne);

		return t;		
	}

	//copier l'objet Cell
	 public static Cell copyCell(Cell c) {
		 Cell copyC = new Cell();
		 copyC.setRow(c.getRow());
		 copyC.setCol(c.getCol());
		 copyC.setImage(c.getImage());
		 copyC.setImageNom(c.getImageNom());
		 return copyC;
	 }
 
	//démarrer le jeu(commencer a donner la fenetre)
	public void action(){
		int load = JOptionPane.showConfirmDialog(null, "Load last game?", "Reload",JOptionPane.YES_NO_OPTION);//fenetre qui demande le joueur de reprendre des donnees enregistrees
		if(load == 0) {
			load();
			startAction();
			repaint();
			bgm();//démarrer la musique du fond 
		}else {
			Object[] possibleValues = { 4, 5 };
			Object selectedValue = JOptionPane.showInputDialog(null, "4 ou 5 carres dans un bloc?", "Input",JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);//choisir de jouer avec 4 ou 5 cellules dans un bloc
			int n = Integer.parseInt(selectedValue.toString());
			Tetromino.setnumberOfCarre(n);
			
			Object[] possibleLanguages = { "简体中文", "Français", "English" };
			Object selectedLanguage = JOptionPane.showInputDialog(null, "Quelle langue vous voulez utiliser?", "Input",JOptionPane.INFORMATION_MESSAGE, null, possibleLanguages, possibleLanguages[0]);//choisir de jouer avec quelle langage
			
			if(selectedLanguage.equals(possibleLanguages[0]))
			{
				languageLabel = 0;
			}
			else if (selectedLanguage.equals(possibleLanguages[1]))
			{
				languageLabel = 1;
			}
			else if (selectedLanguage.equals(possibleLanguages[2]))
			{
				languageLabel = 2;
			}
		
			clearWall();
			startAction();
			repaint();
			bgm();//démarrer la musique du fond 
		}
		

		
		KeyAdapter l = new KeyAdapter() {
		//activer les bouttons
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_Q){
				int save = JOptionPane.showConfirmDialog(null, "Save this game?", "Save",JOptionPane.YES_NO_OPTION);//choisir de stocker le jeu ou pas
				if(save==0) save();
				System.exit(0);
			}
			if(gameOver){
				if(key==KeyEvent.VK_Z){
					clearWall();
					startAction();
				}
				return;
			}
			if(pause){
				if(key==KeyEvent.VK_C){
					continueAction();
				}
				return;
			}

			switch(key){
			case KeyEvent.VK_RIGHT: moveRightAction(); break;
			case KeyEvent.VK_LEFT: moveLeftAction(); break;
			case KeyEvent.VK_DOWN: softDropAction() ; break;
			case KeyEvent.VK_UP: rotateRightAction() ; break;
			case KeyEvent.VK_Z: rotateLeftAction() ; break;
			case KeyEvent.VK_SPACE: hardDropAction() ; break;
			case KeyEvent.VK_P: pauseAction() ; break;
			case KeyEvent.VK_A: try {
				checkCompteur = 1;
				AIAction() ;
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			} break;
			case KeyEvent.VK_S: 
				if(checkSport ==0) {
					try {
						checkSport = 1;
						AIAction() ;
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}else {
					checkSport =0;
					pauseAction();
					continueAction();
				}
			}
			repaint();
		    }
		};
		this.requestFocus();
		this.addKeyListener(l);
	}

	public void paint(Graphics g){
		g.drawImage(background, 0, 0, null);
		g.translate(15, 15);
		paintTetromino(g);
		paintWall(g);
		paintNextOne(g);
		paintScore(g);
	}

	public static final int FONT_COLOR = 0x667799;
	public static final int FONT_SIZE = 0x20;
	private void paintScore(Graphics g) {
		Font f = getFont();
		Font font = new Font(
				f.getName(), Font.BOLD, FONT_SIZE);
		int x = 290;
		int y = 162;
		g.setColor(new Color(FONT_COLOR));
		g.setFont(font);
		if(languageLabel == 0) {
			String str = "分数:"+this.score;
			g.drawString(str, x, y);
			y+=56;
			str = "等级:"+this.level;
			g.drawString(str, x, y);
			y+=56;
			str = "[P]暂停";
			g.drawString(str, x, y);
			y+=56;
			str = "[Q]退出";
			if(pause){str = "[C]继续";}
			if(gameOver){str = "[Z]重新开始";}
			g.drawString(str, x, y);
		}else if(languageLabel == 1) {
			String str = "Points:"+this.score;
			g.drawString(str, x, y);
			y+=56;
			str = "Niveaux:"+this.level;
			g.drawString(str, x, y);
			y+=56;
			str = "[P]Pause";
			g.drawString(str, x, y);
			y+=56;
			str = "[Q]Quittez";
			if(pause){str = "[C]Continuez";}
			if(gameOver){str = "[S]Redémarrez!";}
			g.drawString(str, x, y);
		}else if(languageLabel == 2) {
			String str = "Score:"+this.score;
			g.drawString(str, x, y);
			y+=56;
			str = "Level:"+this.level;
			g.drawString(str, x, y);
			y+=56;
			str = "[P]Pause";
			g.drawString(str, x, y);
			y+=56;
			str = "[Q]Quit";
			if(pause){str = "[C]Continue";}
			if(gameOver){str = "[S]Start!";}
			g.drawString(str, x, y);
		}
	}

	private void paintNextOne(Graphics g) {
		Cell[] cells = nextOne.getCells();
		for(int i=0; i<cells.length; i++){
			Cell c = cells[i];
			int x = (c.getCol()+10) * CELL_SIZE-1;
			int y = (c.getRow()+1) * CELL_SIZE-1;
			g.drawImage(c.getImage(), x, y, null);
		}
	}

	private void paintTetromino(Graphics g) {
		Cell[] cells = tetromino.getCells();
		for(int i=0; i<cells.length; i++){
			Cell c = cells[i];
			int x = c.getCol() * CELL_SIZE-1;
			int y = c.getRow() * CELL_SIZE-1;
			g.drawImage(c.getImage(), x, y, null);
		}
	}

	private void paintWall(Graphics g){
		for(int row=0; row<wall.length; row++){
			Cell[] line = wall[row];
			for(int col=0; col<line.length; col++){
				Cell cell = line[col];
				int x = col*CELL_SIZE;
				int y = row*CELL_SIZE;
				if(cell==null){
				}else{
					g.drawImage(cell.getImage(), x-1, y-1, null);
				}
			}
		}
	}

	public void softDropAction(){
		if(tetrominoCanDrop()){
			tetromino.softDrop();
		}else{
			tetrominoLandToWall();

			destroyLines();
			checkGameOver();
			tetromino = nextOne;
			nextOne = Tetromino.randomTetromino();
		}
	}

	public void destroyLines(){
		int lines = 0;
		for(int row = 0; row<wall.length; row++){
			if(fullCells(row)){
				deleteRow(row);
				lines++;
				music();
			}
		}

		this.score += SCORE_TABLE[lines];
		if(this.score <= 20) {
			this.level = 1;
		}else if(this.score <=60) {
			this.level = 2;
		}else if(this.score <= 150) {
			this.level = 3;
		}else {
			this.level = 4;
		}
	}
	private static final int[] SCORE_TABLE={0,1,10,30,200};

	//détecter si il y a une ligne qui est complete
	public boolean fullCells(int row){
		Cell[] line = wall[row];
		for(int i=0; i<line.length; i++){
			if(line[i]==null){
				return false;
			}
		}
		return true;
	}

	//enlever une ligne
	public void deleteRow(int row){ 
		for(int i=row; i>=1; i--){
			System.arraycopy(wall[i-1], 0, wall[i], 0, COLS);
		}
		Arrays.fill(wall[0], null);
	}

	public boolean tetrominoCanDrop(){
		Cell[] cells = tetromino.getCells();
		for(int i = 0; i<cells.length; i++){
			Cell cell = cells[i];
			int row = cell.getRow();
			if(row == ROWS-1){
				return false;
			}
		}
		for(int i = 0; i<cells.length; i++){
			Cell cell = cells[i];
			int row = cell.getRow();
			int col = cell.getCol();
			if(wall[row+1][col] != null){
				return false;
			}
		}
		return true;
	}

	//si un bloc ne peut plus tomber, on l'ajoute dans le wall[][]
	public void tetrominoLandToWall(){
		Cell[] cells = tetromino.getCells();
		for(int i=0; i<cells.length; i++){
			Cell cell = cells[i];
			int row = cell.getRow();
			int col = cell.getCol();
			wall[row][col] = cell;
		}
	}

	public void moveRightAction(){
		tetromino.moveRight();
		if(outOfBound() || coincide()){
			tetromino.moveLeft();
		}
	}
	public void moveLeftAction(){
		tetromino.moveLeft();
		if(outOfBound() || coincide()){
			tetromino.moveRight();
		}
	}

	private boolean outOfBound(){
		Cell[] cells = tetromino.getCells();
		for(int i=0; i<cells.length; i++){
			Cell cell = cells[i];
			int col = cell.getCol();
			if(col<0 || col>=COLS){
				return true;
			}
		}
		return false;
	}
	private boolean coincide(){
		Cell[] cells = tetromino.getCells();
		for(Cell cell: cells){
			int row = cell.getRow();
			int col = cell.getCol();
			if(row<0 || row>=ROWS || col<0 || col>=COLS ||
					wall[row][col]!=null){
				return true;
			}
		}
		return false;
	}

	public void rotateRightAction(){
		tetromino.rotateRight();
		if(outOfBound() || coincide()){
			tetromino.rotateLeft();
		}
	}

	public void rotateLeftAction(){
		tetromino.rotateLeft();
		if(outOfBound() || coincide()){
			tetromino.rotateRight();
		}
	}
	public void hardDropAction(){
		while(tetrominoCanDrop()){
			tetromino.softDrop();
		}
		tetrominoLandToWall();
		destroyLines();
		checkGameOver();
		tetromino = nextOne;
		nextOne = Tetromino.randomTetromino();
	}
	
	//fonction fabriqué pour le mode automatique 
	public boolean handler(int dir) {
		if(dir == 0) {
			rotateRightAction();
		}
		if(dir == 10) {
			rotateLeftAction();
		}
		if(dir == 1) {
			moveRightAction();
		}
		if(dir == 2) {
			while(tetrominoCanDrop()){
				tetromino.softDrop();
			}
			tetrominoLandToWall();
		}
		if(dir == 3) {
			moveLeftAction();
		}
		return true;
	}
		
		public void music(){  
	    	try {      
	    		File f;
	    		URI uri;
	    		URL url; 	    			    
	    		f = new File("C:\\Users\\jn\\Desktop\\MyTetris\\src\\tetris\\music\\eat.wav"); 
	    		uri = f.toURI();
	    		url = uri.toURL();  
	    		AudioClip aau; 
	    		aau = Applet.newAudioClip(url);
	    		aau.play();  
	    	} catch (Exception e) { 
	    		e.printStackTrace();
	    	}
	    }
	    
	    public void bgm(){  
			try {     
	    	   File f;
	    	   URI uri;
	    	   URL url; 
	    	   f = new File("C:\\Users\\jn\\Desktop\\MyTetris\\src\\tetris\\music\\bgm.wav"); 
	    	   uri = f.toURI();
	    	   url = uri.toURL();
	    	   AudioClip aau; 
	    	   aau = Applet.newAudioClip(url);
	    	   aau.loop();  
			} catch (Exception e) { 
				e.printStackTrace();
			}
	}

	private boolean pause;
	private boolean gameOver;
	private Timer timer;
	//démarrer le programme principal du jeu (bloc commencer a tomber)
	public void startAction(){
		tetromino = Tetromino.randomTetromino();
		nextOne = Tetromino.randomTetromino();
		pause=false; gameOver=false;
		timer = new Timer();
		int level1 = level;
		timer.schedule(new TimerTask(){
			public void run() {
				softDropAction();
				if(level != level1) {
					pauseAction();
					continueAction();
				}
				repaint();
			}
		}, 700, 700);
	}

	private void clearWall(){
		for(int row=0; row<ROWS; row++){
			Arrays.fill(wall[row], null);
		}
	}

	public void pauseAction(){
		compteur = 300;
		timer.cancel();
		pause = true;
		repaint();
	}
	public void continueAction(){
		timer = new Timer();
		if(level == 1) {
			timer.schedule(new TimerTask(){
				public void run() {
					softDropAction();
					repaint();
				}
			}, 700, 700);
		}else if(level == 2){
			int level1 = level;
			timer.schedule(new TimerTask(){
				public void run() {
					softDropAction();
					if(level != level1) {
						pauseAction();
						continueAction();
					}
					repaint();
				}
			}, 500, 500);				
		}else if(level == 3) {
			int level1 = level;
			timer.schedule(new TimerTask(){
				public void run() {
					softDropAction();
					if(level != level1) {
						pauseAction();
						continueAction();
					}
					repaint();
				}
			}, 300, 300);
		}else {
			int level1 = level;
			timer.schedule(new TimerTask(){
				public void run() {
					softDropAction();
					if(level != level1) {
						pauseAction();
						continueAction();
					}
					repaint();
				}
			}, 200, 200);
		}
		pause = false;
		repaint();
	}
	
	public void checkGameOver(){
		if(wall[0][4]==null){
			return;
		}
		gameOver = true;
		timer.cancel();
		repaint();
	}

	public String printMessage(){
		System.out.println(message);
		return message;
	}
	
	//utiliser le chemin calculé par le mode automatique 
	public void AImove() throws InterruptedException {

		ai.calcWay(this);
		ArrayList<Integer> hArrayList = ai.HandlerList;
		for(Integer a:hArrayList) {
			this.handler(a);
			repaint();
		}

	}
	
	//mode automatique activé
	public void AIAction() throws InterruptedException {
		timer.cancel();
		timer = new Timer();
		
		timer.schedule(new TimerTask(){
			public void run() {				
					try {
						AImove();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					compteur-- ;
					if(compteur == 0 && checkCompteur ==1) {
						pauseAction();
						continueAction();
						compteur = 300;
						checkCompteur = 0;
					}
					softDropAction();
					repaint();
					if(gameOver) timer.cancel();
				}
		}, 50, 50);
	}

	//enregistrer les données
	public void save(){
		
    	System.out.println("--------save begin--------");

        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        dbf.setIgnoringElementContentWhitespace(false);
        
        try{
        
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document xmldoc=db.newDocument();
            Element tetris = xmldoc.createElement("Tetris");
            Element t = xmldoc.createElement("tetris_parametre");
            t.setAttribute("score", String.valueOf(this.score));
            t.setAttribute("record", String.valueOf(Tetris.record));
            t.setAttribute("level", String.valueOf(this.level));
            t.setAttribute("numberofcarre", String.valueOf(this.tetromino.getnumberOfCarre()));
            t.setAttribute("languagelabel", String.valueOf(this.languageLabel));
            for(int m = 0; m < 20; m++) {
            	for(int n = 0; n < 10; n++) {
            		if(wall[m][n]!=null) {
                		Element cell =xmldoc.createElement("cell");
                		cell.setAttribute("row", String.valueOf(m));
                		cell.setAttribute("col", String.valueOf(n));
                		cell.setAttribute("image", wall[m][n].getImageNom());
                		t.appendChild(cell);
            		}
            	}
            }
            tetris.appendChild(t);
            xmldoc.appendChild(tetris);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer former = factory.newTransformer();
            former.setOutputProperty(OutputKeys.INDENT, "yes");
            former.transform(new DOMSource(xmldoc), new StreamResult("memory1.xml"));
         	System.out.println("--------save successful!--------");

            
            
        }catch(Exception e){
            e.printStackTrace();
        }
	}
			
	//reprendre des données
	public void load() {
    	System.out.println("--------load begin--------");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse("memory1.xml");
            document.getDocumentElement().normalize();
            NodeList root = document.getElementsByTagName("tetris_parametre");
            Node node = root.item(0);
            Element ele = (Element)node;
            if(root.item(0).getNodeName()=="tetris_parametre") {
            	this.score = Integer.parseInt(ele.getAttribute("score"));
            	Tetris.record = Integer.parseInt(ele.getAttribute("record"));
            	this.level = Integer.parseInt(ele.getAttribute("level"));
            	this.languageLabel = Integer.parseInt(ele.getAttribute("languagelabel"));
            	Tetromino.setnumberOfCarre(Integer.parseInt(ele.getAttribute("numberofcarre")));
            }
            
            NodeList cellList = document.getElementsByTagName("cell");
            Properties props=new Properties();
			FileInputStream iFile = new FileInputStream("C:\\Users\\jn\\Desktop\\MyTetris\\src\\tetris\\a.properties");
			props.load(iFile);
            for (int i = 0; i < cellList.getLength(); i++) {
                Node cell = cellList.item(i);
                NamedNodeMap attrs = cell.getAttributes();
                for(int m = 0; m < 20; m++) {
                	for(int n = 0; n < 10; n++) {
                		if(Integer.parseInt(attrs.item(0).getNodeValue())==n && Integer.parseInt(attrs.item(2).getNodeValue())==m) {
        					this.wall[m][n] = new Cell(m,n,ImageIO.read(Tetris.class.getResource(props.getProperty(attrs.item(1).getNodeValue()))),attrs.item(1).getNodeValue());
            			}              			
            		}
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("--------load successful--------");
    }
		


}
