import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public final class JuliaThread implements Runnable {

    private final int i;
    private final Julia julia;
    private final int iterations;
    private boolean isInSet;
    private final Graphics2D g;
    private final int HEIGHT;

    public JuliaThread(final int i, final Julia julia, final int iterations, final Graphics2D canvas_frame,
            final int height) {
        this.i = i;
        this.julia = julia;
        this.iterations = iterations;
        this.g = canvas_frame;
        this.HEIGHT = height;

    }

    @Override
    public void run() {
        Color color = null;

        for (int j = 0; j < HEIGHT; j++) {
            julia.pointMap(i, j);
            this.isInSet = julia.isInSet(iterations);
            // color is changed according to the iterator value
            final float colorHueValue = (float) julia.get_iterator_val() * 12.0f / (float) iterations;
            color = Color.getHSBColor(colorHueValue, 0.9f, 0.5f);

            // mark the points in canvas according to the point is in the set or not
            if (isInSet) {
                markOnCanvas((Graphics2D) g, Color.BLACK, i, j);
            } else {
                markOnCanvas((Graphics2D) g, color, i, j);

            }

        }

    }

    // function to mark a point in the canvas according to the choosen color
    private static void markOnCanvas(final Graphics2D canvas_frame, final Color c, final double x, final double y) {
        canvas_frame.setColor(c);
        canvas_frame.draw(new Line2D.Double(x, y, x, y));
    }

}