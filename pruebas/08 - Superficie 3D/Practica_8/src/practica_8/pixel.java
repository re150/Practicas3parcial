package practica_8;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.LinkedList;
import static java.lang.System.in;

public class pixel {
    private final BufferedImage buffer;
    public static pixel canvas;
    public ImageObserver m_IO;
    public static int width, height;
    public boolean canSetColor = false;

    public pixel(ImageObserver iO, BufferedImage bI) {
        buffer = bI;
        m_IO = iO;

        width = this.buffer.getWidth();
        height = this.buffer.getHeight();

        canSetColor = false;
        canvas = this;
    }

    public void putPixel(int x, int y, Color c) {
        if (!canSetColor) {
            try {
                buffer.setRGB(x, y, c.getRGB());
            } catch (Exception e) {

            }
        }
    }

    public void putPixel(int x, int y, Color c, boolean s) {
        if (s || !canSetColor) {
            try {
                buffer.setRGB(x, y, c.getRGB());
            } catch (Exception e) {
            }
        }
    }

    public static void clear() {
        for (int i = 0; i < canvas.buffer.getWidth(); i++) {
            for (int j = 0; j < canvas.buffer.getHeight(); j++) {
                canvas.putPixel(i, j, Color.BLACK, false);
            }
        }
    }
}