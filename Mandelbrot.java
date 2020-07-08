public class Mandelbrot {

	private final int WIDTH;
	private final int HEIGHT;
	protected boolean isInSet;
	private double start_x;
	private double end_x;
	private double start_y;
	private double end_y;
	private static double x;
	private static double y;
	private static int iterator;


	// Constructor of Mandelbrot
	public Mandelbrot(double start_x, double end_x, double start_y, double end_y, int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		this.start_x = start_x;
		this.end_x = end_x;
		this.start_y = start_y;
		this.end_y = end_y;
	}

	// Maps the x,y coordinates of the canvas in to region of interest
	public void pointMap(int i, int j) {
		x = (((double) i * (end_x - start_x)) / WIDTH) + start_x;
		y = start_y - (((double) j * (start_y - end_y)) / HEIGHT);
	}

	public double get_x_coordinate() {
		return x; // returns mapped x coordinate
	}

	public double get_y__coordinate() {
		return y; // returns mapped y coordinate
	}

	public int get_iterator_val() {
		return iterator; // returns the value of iterator to calculate suitable color
	}

	// find mapped coordinates are in the Mandelbrot set
	public boolean isInSet(double x, double y, int iterations) {
		iterator = 0;
		double zX = 0;
		double zY = 0;
		for (; iterator < iterations; iterator++) {
			double new_zX = (zX * zX) - (zY * zY) + x;
			double new_zY = (2 * zX * zY) + y;
			zX = new_zX;
			zY = new_zY;

			// instead of testing ABS(Z) > 2 we can test ABS(Z^2) > 4
			if ((Math.pow(zX, 2) + Math.pow(zY, 2)) > 4) {
				return isInSet = false;
			}
		}
		return isInSet = true;
	}

}
