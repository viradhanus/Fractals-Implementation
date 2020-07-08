import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import java.util.concurrent.*;

// suppress warnings relative to missing serialVersionUID field for a serializable class
@SuppressWarnings("serial")
public class Fractal extends JPanel { // inherit JPanel

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	private static final int THREADS = 800;
	private int iterations;

	private boolean isMandelbrot = false;
	private boolean isJulia = false;

	Mandelbrot mandelbrot = null; // initialize object from Mandelbrot class
	Julia julia = null; // initialize object from Julia class

	public static void main(String[] args) {

		JFrame canvas_frame = null; // initialize canvas from JFrame

		if (args.length >= 1) {

			if (args[0].equals("Mandelbrot")) { // if true need to plot a Mandelbrot set

				// set initial values of start_x, end_x,start_y, end_y, iterations to 0
				double start_x = 0;
				double end_x = 0;
				double start_y = 0;
				double end_y = 0;
				int iterations = 0;

				if (args.length == 1) { // use the default values

					start_x = -1;
					end_x = 1;
					start_y = 1;
					end_y = -1;					
					iterations = 1000;

				} else if (args.length == 5) {

					start_x = Double.parseDouble(args[1]);
					end_x = Double.parseDouble(args[2]);
					end_y = Double.parseDouble(args[3]);					
					start_y = Double.parseDouble(args[4]);
					iterations = 1000; // use the default value 1000

				} else if (args.length == 6) {
					start_x = Double.parseDouble(args[1]);
					end_x = Double.parseDouble(args[2]);
					end_y = Double.parseDouble(args[3]);
					start_y = Double.parseDouble(args[4]);
					iterations = Integer.parseInt(args[5]);
				} else {
					System.out.println("\nError: Arguments are Invalid.");
					usageExplanation();
				}
				canvas_frame = new JFrame("The Mandelbrot Set");
				// set the content of the canvas_frame
				canvas_frame.setContentPane(new Fractal(start_x, end_x, start_y, end_y, iterations));

			} else if (args[0].equals("Julia")) { // if true need to plot a Julia set

				// set initial values of start_x, start_y, iterations to 0
				double start_x = 0;
				double start_y = 0;
				int iterations = 0;

				if (args.length == 1) { // use the default values

					start_x = -0.4;
					start_y = 0.6;
					iterations = 1000;

				} else if (args.length == 3) {

					start_x = Double.parseDouble(args[1]);
					start_y = Double.parseDouble(args[2]);
					iterations = 1000; // use the default value 1000

				} else if (args.length == 4) {

					start_x = Double.parseDouble(args[1]);
					start_y = Double.parseDouble(args[2]);
					iterations = Integer.parseInt(args[3]);

				} else {

					System.out.println("\nError: Arguments are Invalid.");
					usageExplanation();
				}
				canvas_frame = new JFrame("The Julia Set");
				// set the content of the canvas_frame as one of this panel
				canvas_frame.setContentPane(new Fractal(start_x, start_y, iterations));
			} else {
				System.out.println("\nError: Arguments are Invalid.");
				usageExplanation();
			}
			canvas_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			canvas_frame.pack();
			canvas_frame.setLocationRelativeTo(null);
			canvas_frame.setVisible(true);

		} else { // no argument entered

			System.out.println("\nPlease enter argument / arguments");
			usageExplanation();
		}
	}

	// construct fractal as mandelbrot
	public Fractal(double start_x, double end_x, double start_y, double end_y, int iterations) {

		this.iterations = iterations;
		isMandelbrot = true;
		mandelbrot = new Mandelbrot(start_x, end_x, start_y, end_y, WIDTH, HEIGHT);
		// set the panel size
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

	}

	// constructor fractal as Julia
	public Fractal(double x, double y, int iterations) {

		this.iterations = iterations;
		isJulia = true;
		julia = new Julia(x, y, WIDTH, HEIGHT);
		// set the panel size
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	// call paintComponent from parent class
	public void paintComponent(Graphics g) {
		// call paintComponent from parent class
		super.paintComponent(g);

		try {
			// ExecutorService automatically provides a pool of threads and API for
			// assigning tasks to it.
			ExecutorService executorService = Executors.newFixedThreadPool(THREADS);

			for (int thread = 0; thread < THREADS; thread++) {
				if (isMandelbrot)// checks the type of fractal is Mandelbrot
				{
					executorService.submit(new MandelbrotThread(thread, mandelbrot, iterations, (Graphics2D) g, HEIGHT));
				} else if (isJulia)// checks the type of fractal is Julia
				{
					executorService.submit(new JuliaThread(thread, julia, iterations, (Graphics2D) g, HEIGHT));

				}

			}
			// initiates an orderly shutdown
			executorService.shutdown();
			// blocks until all tasks have completed execution after a shutdown request
			executorService.awaitTermination(7, TimeUnit.SECONDS);

		} catch (Exception err) {
			err.printStackTrace();
		}

	}

	// command line / terminal usage explanation
	public static void usageExplanation() {
		System.out.println("Usage:");
		System.out.println("java Fractal Mandelbrot <start_x> <end_x> <start_y> <end_y> <iterations>");
		System.out.println("java Fractal Julia <x> <y> <iterations>");
		System.exit(-1); // exit value -1 unsuccessful completion
	}

}
