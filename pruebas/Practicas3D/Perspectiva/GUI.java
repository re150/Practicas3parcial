package Perspectiva;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

public class GUI extends JFrame {

    private Graphic MyGraphicsInstance;
    private Boolean checkExistance = Boolean.FALSE;

    private BufferedImage buffer;
    public JPanel myJPanel;
    private Key MyKeyInstance;

    public GUI() {
        super("Prespectiva Paralela");
        int width = 600, height = 600;
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
        myJPanel = new JPanel();
        add(myJPanel);
        setVisible(true);
        MyGraphicsInstance = new Graphic(this);
        MyKeyInstance = new Key();
        addKeyListener(MyKeyInstance);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void paint(Graphics g) {
        if (checkExistance == Boolean.FALSE) {
            buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
            buffer.setRGB(0, 0, Color.blue.getRGB());
            this.getGraphics().drawImage(buffer, getWidth() / 2, getHeight() / 2, this);
            checkExistance = Boolean.TRUE;
            super.paint(g);
        }
        super.paint(g);
        this.update(g);
    }

    public void update(Graphics g) {

        MyGraphicsInstance.ResetBuff();
        MyGraphicsInstance.SetCamara(MyKeyInstance.GetX(), MyKeyInstance.GetY(), MyKeyInstance.GetZ());
        MyGraphicsInstance.SetColor(Color.black);
        MyGraphicsInstance.cube(150, 150, 15, 400, 400, 40);
        this.getGraphics().drawImage(MyGraphicsInstance.GetFondo(), 0, 0, this);

    }

    public boolean GetChange() {
        return MyKeyInstance.GetChange();
    }

    public void SetChange(boolean NewChange) {
        MyKeyInstance.SetChange(NewChange);
    }

}


class Key implements KeyListener {

    public static int X = 10;
    public static int Y = 20;
    public static int Z = -20;

    public int GetX() {
        return X;
    }

    public int GetY() {
        return Y;
    }

    public int GetZ() {
        return Z;
    }

    private boolean Change = false;

    public boolean GetChange() {
        return Change;
    }

    public void SetChange(boolean NewChange) {
        Change = NewChange;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            Y = Y - 1;
            Change = true;
        }
        if (key == KeyEvent.VK_A) {
            X = X - 1;
            Change = true;
        }
        if (key == KeyEvent.VK_S) {
            Y = Y + 1;
            Change = true;
        }
        if (key == KeyEvent.VK_D) {
            X = X + 1;
            Change = true;
        }

        if (key == KeyEvent.VK_LEFT) {
            X = X - 1;
            Change = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            X = X + 1;
            Change = true;
        }
        if (key == KeyEvent.VK_UP) {
            Y = Y - 1;
            Change = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            Y = Y + 1;
            Change = true;
        }
        if (key == KeyEvent.VK_ADD || key == KeyEvent.SHIFT_DOWN_MASK) {
            Z = Z + 1;
            Change = true;
        }
        if (key == KeyEvent.VK_MINUS) {
            Z = Z - 1;
            Change = true;
        }

    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}