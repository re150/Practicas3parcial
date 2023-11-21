package Proyeccion;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Graphic1 extends JFrame  implements  Runnable{

    private BufferedImage buffer;
    private Color MyColor = Color.BLACK;
    private int viewX, viewY, viewZ;
    private static BufferedImage back;
    private static Graphics backd;
    private Boolean checkExistance = Boolean.FALSE;

    public JPanel myJPanel;
    private Key MyKeyInstance;

    public Graphic1() {
        super("Prespectiva de Proyeccion");
        int width = 600, height = 600;
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myJPanel = new JPanel();
        add(myJPanel);
        setVisible(true);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        back = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        backd = back.getGraphics();

        MyKeyInstance = new Key();
        addKeyListener(MyKeyInstance);
    }

    public BufferedImage Back() {
        return back;
    }

    public void color(Color C) {
        this.MyColor = Color.red;
    }

    public void setBack(int x, int y, int z) {
        this.viewX = x;
        this.viewY = y;
        this.viewZ = z;
    }

    public void Pixel(int x, int y) {
        buffer.setRGB(0, 0, MyColor.getRGB());
        backd.drawImage(buffer, x, y, this);
    }

    public void ResetBuff() {
        back = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        backd = back.getGraphics();
    }

    public void middlePoint(int x1, int y1, int x2, int y2) {
        int pk, A, B, pxlx, pxly;

        int dx = (x2 - x1);
        int dy = (y2 - y1);

        if (dy < 0) {
            dy = -dy;
            pxly = -1;
        }
        else {
            pxly = 1;
        }
        if (dx < 0) {
            dx = -dx;
            pxlx = -1;
        } else {
            pxlx = 1;
        }

        int X = x1;
        int Y = y1;
        Pixel(x1, y1);

        if(dx>dy){
            pk = 2*dy - dx;
            A = 2*dy;
            B = 2*(dy-dx);
            while (X != x2){
                X = X + pxlx;
                if (pk < 0){
                    pk = pk + A;
                } else {
                    Y = Y + pxly + 1/2;
                    pk = pk + B;
                }
                Pixel(X, Y);
            }
        } else {
            pk = 2*dx - dy;
            A = 2*dx;
            B = 2*(dx-dy);
            while (Y != y2){
                Y = Y + pxly + 1/2;
                if (pk < 0){
                    pk = pk + A;
                } else {
                    X = X + pxlx;
                    pk = pk + B;
                }
                Pixel(X, Y);
            }
        }
    }
    public void Cuadrado(int x1, int y1, int x2, int y2) {
        middlePoint(x1, y1, x2, y1);
        middlePoint(x1, y1, x1, y2);
        middlePoint(x2, y1, x2, y2);
        middlePoint(x1, y2, x2, y2);
    }
    public void cubeProyection(int x1, int y1, int z1, int x2, int y2, int z2) {

        Point[] cubeOnePoints;
        Point[] cubeTwoPoints;

        cubeOnePoints = oneProyection(x1, y1, z1, x2, y2, z2);
        cubeTwoPoints = twoProyection(x1, y1, z1, x2, y2, z2);

        Cuadrado(cubeOnePoints[0].x, cubeOnePoints[0].y, cubeOnePoints[3].x, cubeOnePoints[3].y);
        Cuadrado(cubeTwoPoints[0].x, cubeTwoPoints[0].y, cubeTwoPoints[1].x, cubeTwoPoints[1].y);

        for (int i = 0; i < 4; i++) {
            Pixel(cubeOnePoints[i].x, cubeOnePoints[i].y);
            Pixel(cubeTwoPoints[i].x, cubeTwoPoints[i].y);

            if( i != 3)
                middlePoint(cubeOnePoints[i].x, cubeOnePoints[i].y, cubeTwoPoints[i+1].x, cubeTwoPoints[i+1].y);
            else
                middlePoint(cubeOnePoints[i].x, cubeOnePoints[i].y, cubeTwoPoints[0].x, cubeTwoPoints[0].y);
        }
    }


    private Point[] oneProyection(int x1, int y1, int z1, int x2, int y2, int z2) {
        Point[] points = new Point[4];

        Point Punto = convert(x1, y1, z1);
        points[0] = new Point(Punto.x, Punto.y);

        Punto = convert(x1, y2, z1);
        points[1] = new Point(Punto.x, Punto.y);

        Punto = convert(x2, y1, z1);
        points[2] = new Point(Punto.x, Punto.y);

        Punto = convert(x2, y2, z1);
        points[3] = new Point(Punto.x, Punto.y);

        return points;
    }
    private Point[] twoProyection(int x1, int y1, int z1, int x2, int y2, int z2) {
        Point[] points = new Point[4];

        Point Punto = convert(x2, y2, z2);
        points[0] = new Point(Punto.x, Punto.y);

        Punto = convert(x1, y1, z2);
        points[1] = new Point(Punto.x, Punto.y);

        Punto = convert(x1, y2, z2);
        points[2] = new Point(Punto.x, Punto.y);

        Punto = convert(x2, y1, z2);
        points[3] = new Point(Punto.x, Punto.y);

        return points;
    }

    private Point convert(int x, int y, int z){
        int xTemp, yTemp;
        System.out.println("x= " + x );
        xTemp = viewX -( ( viewZ * ( x - viewX ) ) / ( z - viewZ ));
        yTemp = viewY -( ( viewZ * ( y - viewY ) ) / ( z - viewZ ));

        Point converted = new Point(xTemp, yTemp);

        return converted;
    }
    @Override
    public void run() {
        boolean exit = false;
        while (!exit) {
            try {
                if (GetChange()) {
                    repaint();
                    SetChange(false);
                }
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
                exit = true;
            }
        }
    }

    public void paint(Graphics g) {
        if (checkExistance == Boolean.FALSE) {
            buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

            buffer.setRGB(0, 0, Color.blue.getRGB());
            g.drawImage(buffer, getWidth() / 2, getHeight() / 2, this);

            checkExistance = Boolean.TRUE;
            super.paint(g);
        }
        super.paint(g);
        this.update(g);
    }

    public void update(Graphics g) {
        ResetBuff();
        setBack(MyKeyInstance.GetX(), MyKeyInstance.GetY(), MyKeyInstance.GetZ());
        color(Color.black);
        cubeProyection(200, 100, 5, 400, 400, 40);

        g.drawImage(Back(), 0, 0, this);
    }

    public boolean GetChange() {
        return MyKeyInstance.GetChange();
    }

    public void SetChange(boolean NewChange) {
        MyKeyInstance.SetChange(NewChange);
    }

}
