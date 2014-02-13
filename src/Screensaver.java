import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;

/**
 * 
 * @author Sam Maynard
 * Main runner class for the Screensaver project.  Instantiates the frame and the Pather canvas/thread.
 *
 */
public class Screensaver {

	private static DoubleBufferedCanvas c = new Pather(30, 15, 15);
	
	static int mouseMovements = 0;  //required for a windows bug
	
	/**
	 * main.  Runs everything.
	 * @param args
	 */
	public static void main(String[] args) {
		System.gc();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)screen.getWidth();
		int screenHeight = (int)screen.getHeight();
		JFrame f = new JFrame();
		f.setBounds(0,0,screenWidth,screenHeight);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setUndecorated(true);
		
		
		c.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				System.exit(0);
			}
		});
		c.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e){
				// required for a windows bug
				mouseMovements++;
				if(mouseMovements > 2)
					System.exit(0);
			}
		});
		f.add(c);
		c.setBackground(Color.BLACK);
		f.setVisible(true);
		f.toFront();
	}
	
}
