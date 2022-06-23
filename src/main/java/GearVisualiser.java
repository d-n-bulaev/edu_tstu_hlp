import java.awt.*;
import java.awt.geom.Point2D;

public class GearVisualiser {
    Point2D center;
    double angle;

    double radius;
    Color color;
    int steps;

    public GearVisualiser(double radius, Color color, int steps) {
        this.radius = radius;
        this.color = color;
        this.steps = steps;
    }

    public GearVisualiser() {
        this.center = new Point2D.Float();
        this.radius = 10.d;
        this.color = new Color(0, 0, 0);
        this.angle = 0;
    }

    private void paintRotor(Graphics originalGraphics, int step, int steps) {

        if (steps == 0) {
            return;
        }

        Point2D.Double rotorEnd = new Point2D.Double(
                center.getX() + radius * Math.sin(angle + ((double) step * 2 * Math.PI / (double) steps)),
                center.getY() + radius * Math.cos(angle + ((double) step * 2 * Math.PI / (double) steps))
        );

        Point2D.Double rotorBeg = new Point2D.Double(
                center.getX() + radius * 0.8 * Math.sin(angle + ((double) step * 2 * Math.PI / (double) steps)),
                center.getY() + radius * 0.8 * Math.cos(angle + ((double) step * 2 * Math.PI / (double) steps))
        );

        originalGraphics.drawLine(
                (int) rotorBeg.getX(), (int) rotorBeg.getY(),
                (int) rotorEnd.getX(), (int) rotorEnd.getY()
        );
    }

    public void paint(Graphics originalGraphics) {
        Color orgnColor = originalGraphics.getColor();
        originalGraphics.setColor(this.color);
        originalGraphics.fillOval((int) (center.getX() - radius), (int) (center.getY() - radius), (int) (radius * 2), (int) (radius * 2));
        originalGraphics.setColor(Color.BLACK);
        for (int i = 0; i < steps; ++i) {
            paintRotor(originalGraphics, i, steps);
        }
        originalGraphics.setColor(orgnColor);
    }
}
