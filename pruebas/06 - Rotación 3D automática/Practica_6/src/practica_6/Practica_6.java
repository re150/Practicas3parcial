package practica_6;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 *
 * @author lalo_
 */
public class Practica_6 extends JPanel{
private BufferedImage buffer;
    private Graphics graPixel;
    
    private double vertex[][][][] = {{{{-1, -1, -1}, {1, -1, -1}}, {{-1, 1, -1},{1, 1, -1}}}, {{{-1, -1, 1}, {1, -1, 1}}, {{-1, 1, 1}, {1, 1, 1}}}};

    // rotations in radians
    private double xyR = 0.005, xzR = 0.005, yzR = 0.005;
           
    // view attributes
    private final static int IMAGE_SIZE = 500, SCALE = 100, OFFSET = 200, DIAMETER = 8;

    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, IMAGE_SIZE, IMAGE_SIZE);

        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        graPixel = buffer.createGraphics();
        Color c = Color.BLACK;
        
        // rotate cube
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                for (int z = 0; z < 2; z++) {
                    xyRotate(vertex[x][y][z], Math.sin(xyR), Math.cos(xyR));
                    xzRotate(vertex[x][y][z], Math.sin(xzR), Math.cos(xzR));
                    yzRotate(vertex[x][y][z], Math.sin(yzR), Math.cos(yzR));
                }
            }
        }

        // draw cube edges
        for (int x = 0; x < 2; x++) {
            for (int y = 0; y < 2; y++) {
                drawEdge(vertex[x][y][0][0], vertex[x][y][0][1], vertex[x][y][1][0], vertex[x][y][1][1], buffer, c);
                drawEdge(vertex[x][0][y][0], vertex[x][0][y][1], vertex[x][1][y][0], vertex[x][1][y][1], buffer, c);
                drawEdge(vertex[0][x][y][0], vertex[0][x][y][1], vertex[1][x][y][0], vertex[1][x][y][1], buffer, c);
            }
        }
        
        graphics.drawImage(buffer, 0, 0, this);
        
    }

    // apply plane rotation to 3D point, only 2 coordinates are affected
    final void xyRotate(double p[], double sin, double cos) {
        double temp;
        temp = cos * p[0] + sin * p[1];
        p[1] = -sin * p[0] + cos * p[1];
        p[0] = temp;
    }

    final void xzRotate(double p[], double sin, double cos) {
        double temp;
        temp = cos * p[0] + sin * p[2];
        p[2] = -sin * p[0] + cos * p[2];
        p[0] = temp;
    }

    final void yzRotate(double p[], double sin, double cos) {
        double temp;
        temp = cos * p[1] + sin * p[2];
        p[2] = -sin * p[1] + cos * p[2];
        p[1] = temp;
    }

    final void drawEdge(double x1, double y1, double x2, double y2, BufferedImage bu, Color col) {
        dibujaLinea((int) (x1 * SCALE) + OFFSET, (int) (-y1 * SCALE) + OFFSET, (int) (x2 * SCALE) + OFFSET, (int) (-y2 * SCALE) + OFFSET, col, bu);
    }

    public static void dibujaLinea(int x0, int y0, int x1, int y1, Color col, BufferedImage bu) {
        Color c = col;
        float adyacente = (float) Float.max(x0, x1) - Float.min(x0, x1);
        float opuesto = (float) Float.max(y0, y1) - Float.min(y0, y1);
        float pendiente = (float) opuesto / adyacente;

        int sigX = 0;
        int sigY = 0;
        pendiente = Math.abs(pendiente);

        if (x0 < x1) {
            sigX = 1;
        } else {
            sigX = -1;
        }
        if (y0 < y1) {
            sigY = 1;
        } else {
            sigY = -1;
        }

        if (Math.toDegrees(Math.atan(pendiente)) > 45) {
            pendiente = (float) Math.abs(adyacente / opuesto);
            for (int i = 0; i <= Math.abs(opuesto); i++) {
                bu.setRGB(x0 + (int) (i * pendiente * sigX), y0 + (i * sigY), c.getRGB());
            }
        } else {
            for (int h = 0; h <= Math.abs(adyacente); h++) {
                bu.setRGB(x0 + h * sigX, y0 + (int) (h * pendiente * sigY), c.getRGB());
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Practica_6 jPanel = new Practica_6();
        JFrame application = new JFrame("06 - Rotación 3D automática");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(jPanel);
        application.setSize(500, 500);
        application.setVisible(true);
        while (true) {
            Thread.sleep(10);
            jPanel.repaint();
        }
    }    
}
