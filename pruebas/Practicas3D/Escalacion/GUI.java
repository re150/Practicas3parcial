package Escalacion;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class GUI extends JFrame implements KeyListener {

    private Graphic draw;
    private int x, y, z, size;
    Color color;

    public GUI(){

        super("Escalacion 3D");
        x = 375;
        y = 400;
        z = 300;
        size = 50;
        color = Color.RED;
        int width = 600, height = 600;
        draw = new Graphic (this, width, height);
        setSize(width, height);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(this);
    }

    public void paint(Graphics g) {
        g.setColor(Color.white);
        draw.definirGraphics(g);
        draw.direccion(7, 7, 25);
        draw.escala(x, y, z, size, color);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                if (size < 175){
                    size *= 1.25;
                    draw.escala(x, y, z, size, color);
                    repaint();
                }
                break;
            case KeyEvent.VK_S:
                if (size > 4){
                    size /= 1.25;
                    draw.escala(x, y, z, size, color);
                    repaint();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        new main();
    }

}
