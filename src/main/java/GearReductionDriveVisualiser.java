import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.concurrent.Semaphore;

public class GearReductionDriveVisualiser extends JComponent {

    private GearVisualiser driveGear;
    private GearVisualiser drivenGear;

    private final Semaphore animControl;
    private Double animStep;
    private boolean animEnabled;

    public void updateRotation(double step) {

        double ratio = driveGear.radius / drivenGear.radius;
        driveGear.angle += step;
        drivenGear.angle -= step * ratio;

        repaint();
    }

    public void animationThreadBody() {
        boolean useAnim = false;
        double step = 0.d;

        for (; ; ) {
            try {
                Thread.sleep(1000 / 100);
            } catch (InterruptedException ignored) {

            }

            try {
                animControl.acquire();

                useAnim = this.animEnabled;
                step = animStep;

                animControl.release();

            } catch (InterruptedException ignored) {

            }

            if (useAnim) {
                updateRotation(step);
            }

        }

    }

    public GearReductionDriveVisualiser() {
        animControl = new Semaphore(1);

        driveGear = new GearVisualiser(10, Color.GREEN, 10);
        drivenGear = new GearVisualiser(10, Color.RED, 10);

        animStep = 0.0;
        animEnabled = false;

        Thread animationThread = new Thread(this::animationThreadBody);
        animationThread.start();
    }

    public void setDriveGear(GearVisualiser value) {
        driveGear = value;
    }

    public void setDrivenGear(GearVisualiser value) {
        drivenGear = value;
    }

    public void setAnimStep(Double value) {
        try {
            animControl.acquire();
            animStep = value;
            animControl.release();
        } catch (InterruptedException ignored) {

        }
    }

    public void setAnimEnabled(boolean value) {
        try {
            animControl.acquire();
            animEnabled = value;
            animControl.release();
        } catch (InterruptedException ignored) {

        }
    }

    @Override
    public void paint(Graphics originalGraphics) {

        int length = (int) (driveGear.radius + drivenGear.radius);
        int height = this.getHeight() / 2;
        int begin = (this.getWidth() - length) / 2;


        driveGear.center = new Point2D.Double(begin, height);
        drivenGear.center = new Point2D.Double(begin + driveGear.radius + drivenGear.radius, height);


        driveGear.paint(originalGraphics);
        drivenGear.paint(originalGraphics);
    }
}
