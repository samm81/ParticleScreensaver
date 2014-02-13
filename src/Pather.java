import java.awt.Graphics;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Random;


/**
 * 
 * @author owner
 * Specific implementation of DoubleBufferedCanvas that draws the screensaver content.
 * Creates and draws multiple PathFollowingBalls.
 */
public class Pather extends DoubleBufferedCanvas {

	//to get rid of the annoying yellow underscore
	private static final long serialVersionUID = 5442520047160155682L;

	private ArrayList<PathFollowingBall> balls = new ArrayList<PathFollowingBall>();

	private int curves;
	private int objects;

	public Pather() {
		this(30, 15, 3); //default values
	}

	public Pather(int fps, int curves, int objects){
		super(fps);
		this.curves = curves;
		this.objects = objects;
	}

	/**
	 * paints all the balls every frame
	 */
	@Override
	void prepare(Graphics g) {
		if(balls.size() == 0){
			for(int i=0;i<objects;i++){
				GeneralPath path = generatePath();
				balls.add(new PathFollowingBall(path, .1, 8, 12));
			}
		}

		for(PathFollowingBall ball : balls){
			ball.paintSelf(g);
		}
	}

	/**
	 * Generates a random general path with the number of curves specified in the global variable.
	 * @return a randomly generated GeneralPath
	 */
	private GeneralPath generatePath() {
		GeneralPath path = new GeneralPath();
		Random r = new Random();
		int width = this.getWidth();
		int height = this.getHeight();

		int startx = r.nextInt(width);
		int starty = r.nextInt(height);

		path.moveTo(startx, starty);
		for(int i=1;i<=curves;i++){
			int x1 = r.nextInt(width);
			int y1 = r.nextInt(height);
			int x2 = r.nextInt(width);
			int y2 = r.nextInt(height);
			int x3 = r.nextInt(width);
			int y3 = r.nextInt(height);
			if(i == curves){
				x3 = startx;
				y3 = starty;
			}
			path.curveTo(x1, y1, x2, y2, x3, y3);
		}

		return path;
	}

	/**
	 * makes the balls update every frame
	 */
	@Override
	protected void updateVars() {
		if(balls != null)
			for(PathFollowingBall ball : balls)
				ball.updateVars();
	}

}
