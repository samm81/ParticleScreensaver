import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.Random;


public class PathFollowingBall {

	private ArrayList<Point> points = new ArrayList<Point>();
	private Color color;

	private int bloom;
	private int width;
	private double speed;
	
	private int index = 0;
	
	public PathFollowingBall(GeneralPath path) {
		this(path, .2, 10, 15); //default values
	}
	
	public PathFollowingBall(GeneralPath path, double speed, int bloom, int width){
		this.speed = speed;
		this.bloom = bloom;
		this.width = width;
		this.points = getPoints(path);
		this.color = generateColor();
	}
	
	/**
	 * converts the GeneralPath into an ArrayList<Point> for the ball to follow
	 * @param path the GeneralPath to convert
	 * @return ArrayList<Point> the coordinates for the ball to follow
	 */
	private ArrayList<Point> getPoints(GeneralPath path) {
		FlatteningPathIterator iter = new FlatteningPathIterator(path.getPathIterator(null), speed);
		ArrayList<Point> points = new ArrayList<Point>();
		float[] coords = new float[6];
		while(!iter.isDone()){
			iter.currentSegment(coords);
			int x = (int) coords[0];
			int y = (int) coords[1];
			points.add(new Point(x, y));
			iter.next();
		}
		return points;
	}
	
	/**
	 * generates a random color
	 * @return a random color
	 */
	private Color generateColor() {
		Random r = new Random();
		float hue = r.nextFloat();
		float saturation = 1f;
		float brightness = 1f;
		return Color.getHSBColor(hue, saturation, brightness);
	}

	/**
	 * paints the ball and it's onion skin
	 * @param g graphics to paint with
	 */
	void paintSelf(Graphics g) {
		for(int i=bloom;i>0;i--){
			Point p = null;
			try{
				p = points.get(index - i);
			}catch(ArrayIndexOutOfBoundsException e){
				p = points.get(points.size() - i);
			}
			int x = (int) p.getX();
			int y = (int) p.getY();

			float step = 1f / (bloom + 1);
			float alpha = (float) (1f - (step * i));
			float red = color.getRed() / 255f;
			float green = color.getGreen() / 255f;
			float blue = color.getBlue() / 255f;
			g.setColor(new Color(red, green, blue, alpha));

			g.fillOval(x, y, width, width);
		}
	}

	/**
	 * slightly shifts a color's hue randomly
	 * @param color color to shift
	 * @return shifted color
	 */
	private Color shiftColor(Color color) {
		int red = color.getRed();
		int blue = color.getBlue();
		int green = color.getGreen();
		
		float[] hsb = Color.RGBtoHSB(red, green, blue, null);
		float hue = hsb[0];
		float saturation = hsb[1];
		float brightness = hsb[2];
		
		Random r = new Random();
		hue += r.nextFloat()/500f;
		if(hue > 1)
			hue -= 1;
		
		return Color.getHSBColor(hue, saturation, brightness);
	}
	
	/**
	 * moves the position forward one and shifts the color
	 */
	public void updateVars() {
		index++;
		if(index >= points.size())
			index = 0;

		if(color != null){
			color = shiftColor(color);
		}
	}
	
	
	// getters and setters
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
