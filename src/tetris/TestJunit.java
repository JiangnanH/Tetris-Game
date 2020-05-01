package tetris;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestJunit {
	String message = "MyTetris";
	Tetris tetris = new Tetris();
	
	@Test
	public void testPrintMessage() {
		assertEquals(message,tetris.printMessage());
	}
}
