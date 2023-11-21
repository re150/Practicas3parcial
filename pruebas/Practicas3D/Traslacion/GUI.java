package Traslacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class GUI extends JFrame implements KeyListener {


    private Graphic draw;
    private int x, y, z, size;
    Color color;

    public GUI(){
        x = 525;
        y = 325;
        z = 300;
        size = 50;
        color = Color.RED;
        int width = 1000, height = 600;
        draw = new Graphic(this, width, height);
        setSize(width, height);
        setTitle("Traslacion");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        g.setColor(Color.white);
        draw.definirGraphics(g);
        draw.direccion(7, 7, 25);
        draw.dibujarCubo(x, y, z, size, color);
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                x-= 40;
                draw.dibujarCubo(x, y, z, size, color);
                repaint();
                break;
            case KeyEvent.VK_D:
                x+= 40;
                draw.dibujarCubo(x, y, z, size, color);
                repaint();
                break;
            case KeyEvent.VK_W:
                y-= 40;
                draw.dibujarCubo(x, y, z, size, color);
                repaint();
                break;
            case KeyEvent.VK_S:
                y+= 40;
                draw.dibujarCubo(x, y, z, size, color);
                repaint();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
