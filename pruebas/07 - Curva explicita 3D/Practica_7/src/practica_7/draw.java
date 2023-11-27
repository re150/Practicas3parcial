package practica_7;
import java.awt.*;
import java.awt.image.*;

public class draw {

    private static BufferedImage pixel;
    private static Color pixelColor;
    private static BufferedImage line;
    private static int xp = 200;
    private static int yp = 200;
    private static int zp = -130;
    private static double xf, yf, zf;
    private static double[][] matrix = new double[4][241];

    static void initialize() {
        if (pixel == null) {
            pixel = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
            pixel.setRGB(0, 0, Color.BLUE.getRGB());
            pixelColor = Color.red;
        }
    }

    static double[][] Rotarx(double[][] m, double angle) {
        double cos = Math.cos(Math.toRadians(angle));
        double sen = Math.sin(Math.toRadians(angle));
        for (int i = 1; i < 241; i++) {
            double y = m[1][i];
            double z = m[2][i];
            m[1][i] = (y * cos) - (z * sen);
            m[2][i] = (y * sen) + (z * cos);
        }

        return m;
    }

    static double[][] translateOrigin(double[][] m) {
        double x = m[0][0];
        double y = m[1][0];
        double z = m[2][0];
        x -= 100;
        y -= 100;
        z -= 50;
        xf = x;
        yf = y;
        zf = z;
        curve(x, y, z);
        return m;
    }

    static double[][] change(double[][] m) {
        for (int i = 0; i < 9; i++) {
            double x = m[0][i];
            double y = m[1][i];
            double z = m[2][i];
            x += 100;
            y += 100;
            z += 50;
            m[0][i] = x;
            m[1][i] = y;
            m[2][i] = z;
        }
        return m;
    }

    static double[][] curve(double x, double y, double z) {
        double[][] escala = { { 20, 0, 0, 0 }, { 0, 20, 0, 0 }, { 0, 0, 20, 0 }, { 0, 0, 0, 1 } };
        matrix[0][0] = x;
        matrix[1][0] = y;
        matrix[2][0] = z;
        matrix[3][0] = 1;
        int a = 1, j = 0;
        for (double i = 0; i < Math.PI * 24; i += (Math.PI * 24) / 240) {
            matrix[j][a] = (Math.cos(i) * 8);
            matrix[1][a] = i + matrix[1][a] - 13;
            matrix[2][a] = (i + z);
            matrix[3][a] = 1;
            a++;
        }
        matrix = multiply(escala, matrix);
        return matrix;
    }

    static double[][] multiply(double[][] a, double[][] b) {
        double[][] c = new double[a.length][b[0].length];
        if (a[0].length == b.length) {
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b[0].length; j++) {
                    for (int k = 0; k < a[0].length; k++) {
                        c[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
        }
        return c;
    }

    static void setColor(Color c) {
        pixel.setRGB(0, 0, c.getRGB());
        pixelColor = Color.BLUE;
        line = null;
    }
    static void drawPixel(int x, int y, Graphics dest) {
        dest.drawImage(pixel, x, y, null);
    }

    static void DDA(double x1, double y1, double z1, double x2, double y2, double z2, Graphics dest) {
        double xUno = x1 - xp * (z1 / zp);
        double yUno = y1 - yp * (z1 / zp);
        double xDos = x2 - xp * (z2 / zp);
        double yDos = y2 - yp * (z2 / zp), xcu, ycu;
        double dx, dy, steps, xf, yf;
        double xinc, yinc, x, y;

        dx = xDos - xUno;
        dy = yDos - yUno;
        if (Math.abs(dx) > Math.abs(dy)) {
            steps = Math.abs(dx);
        } else {
            steps = Math.abs(dy);
        }
        xinc = dx / (double) steps;
        yinc = dy / (double) steps;
        x = xUno;
        y = yUno;
        for (int k = 1; k <= steps; k++) {
            x = x + xinc;
            y = y + yinc;
            drawPixel((int) x, (int) y, dest);
        }
    }

}

