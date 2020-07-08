public class Julia {
	private final int WIDTH;
	private final int HEIGHT;
	protected boolean isInSet;
	private static double cX;
	private static double cY;
	private static double zX;
	private static double zY;
	private static int iterator;

	// Constructor of Julia
	public Julia(double x, double y, int width, int height) {
		this.WIDTH = width;
		this.HEIGHT = height;
		cX = x;
		cY = y;
	}

	// Maps the x,y coordinates of the canvas in to region of interest
	public void pointMap(double i, double j) {
		zX = (((double) i * 2) / WIDTH) - 1;
		zY = 1 - (((double) j * 2) / HEIGHT) ;

	}

	// find mapped coordinates are in the Julia set
	public boolean isInSet(int iterations) {

		iterator = 0;
		for (; iterator < iterations; iterator++) { // use for loop
			double z_new_x = (zX * zX) - (zY * zY) + cX;
			double z_new_y = (2 * zX * zY) + cY;
			zX = z_new_x;
			zY = z_new_y;
			iterator++;

			// instead of testing ABS(Z) > 2 we can test ABS(Z^2) > 4
			if ((Math.pow(zX, 2) + Math.pow(zY, 2)) > 4) {
				return isInSet = false;
			}
		}
		return isInSet = true;
	}

	// returns the value of iterator to calculate suitable color
	public int get_iterator_val() {
		return iterator;
	}
}
