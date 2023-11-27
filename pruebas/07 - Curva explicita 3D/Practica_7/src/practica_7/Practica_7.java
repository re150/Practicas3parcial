package practica_7;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.awt.*;
import javax.swing.*;
import java.awt.Color;

public class Practica_7 extends JFrame implements KeyListener {

    private Image buffer;
    private int direccion = 2;
    private double tethax = 0;
    private double tethay = 0;
    private double tethaz = 0;
    private double[][] matrix = new double[4][241];
    private double xo =0, yo = 0, zo = 0, zi;
    private double x, y, z, xf, yf, zf;

    private Practica_7() {
        setTitle("07 - Curva explicita 3D");
        zi = (int) (zo + (Math.PI * 24 / 30)) - zo;
        int width = 1000, height = 600;
        this.setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buffer = createImage(getWidth(), getHeight());
        System.out.println("zo = " + zo);
        matrix = draw.curve(xo, yo, zi);

    }

    public void update(Graphics g) {
        if (buffer == null)
            return;
        Graphics buffg = buffer.getGraphics();
        buffg.setColor(getBackground());
        buffg.fillRect(0, 0, this.getSize().width, this.getSize().height);
        draw.setColor(new Color(130,0, 4));
        for (int i = 23; i < 200; i++) {
            x = matrix[0][i];
            y = matrix[1][i];
            z = matrix[2][i];
            xf = matrix[0][i + 1];
            yf = matrix[1][i + 1];
            zf = matrix[2][i + 1];
            draw.DDA(x + 350, y + 350, z, xf + 350, yf + 350, zf, buffg);
        }

        g.drawImage(buffer, 0, 0, null);
    }

    public void paint(Graphics g) {
        update(g);
    }

    public void animacion() {
        while (true) {
          // actualiza();
            matrix = draw.translateOrigin(matrix);
            matrix = draw.Rotarx(matrix, tethax);
            matrix = draw.Rotarx(matrix, tethay);
            matrix = draw.Rotarx(matrix, tethaz);
            matrix = draw.change(matrix);
            //direccion = 0;
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void actualiza() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }

        switch (direccion) {
            case 1:
                tethaz++;
                break;
            case 2:
                tethax++;
                break;

            case 3:
                tethay++;
                break;
        }
    }

    public static void main(String[] args) {
        draw.initialize();
        Practica_7 db = new Practica_7();
        db.animacion();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
