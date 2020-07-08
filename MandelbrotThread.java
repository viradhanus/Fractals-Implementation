import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public final class MandelbrotThread implements Runnable {

    private final int i;
    private final Mandelbrot mandelbrot;
    private final int iterations;
    private boolean isInSet;
    private final Graphics2D g;
    private final int HEIGHT;

    public MandelbrotThread(final int i, final Mandelbrot mandelbrot, final int iterations,
            final Graphics2D canvas_frame, final int height) {

        this.i = i;
        this.mandelbrot = mandelbrot;
        this.iterations = iterations;
        this.g = canvas_frame;
        this.HEIGHT = height;

    }

    @Override
    public void run() {
        Color color = null;

        for (int j = 0; j < HEIGHT; j++) {
            mandelbrot.pointMap(i, j);
            this.isInSet = mandelbrot.isInSet(mandelbrot.get_x_coordinate(), mandelbrot.get_y__coordinate(),
                    iterations);
            // color is changed according to the iterator value
            final float colorHueValue = (float) mandelbrot.get_iterator_val() * 50.0f / (float) iterations;
            color = Color.getHSBColor(colorHueValue, 0.9f, 0.5f);

            // mark the points in canvas according to the point is in the set or not
            if (isInSet) {
                markOnCanvas(i, j, Color.BLACK, (Graphics2D) g);
            } else {
                markOnCanvas(i, j, color, (Graphics2D) g);

            }

        }

    }

    // function to mark a point in the canvas according to the choosen color
    private static void markOnCanvas(final double x, final double y, final Color color, final Graphics2D canvas_frame) {
        canvas_frame.setColor(color);
        canvas_frame.draw(new Line2D.Double(x, y, x, y));
    }

}