import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class Pixel extends JPanel implements Runnable {
        private BufferedImage buffer;
        private BufferedImage fondo;
        private Graphics fondoG;
        private int viewX, viewY, viewZ;
        private Boolean checkExistance = false;
        private Key MyKeyInstance;

    public Pixel() {
        init();
        repaint();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setDoubleBuffered(true);
        MyKeyInstance = new Key();
        addKeyListener(MyKeyInstance);
    }

    public void init () {
        setSize(700, 700);
        setVisible(true);
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        fondo = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        fondoG = fondo.getGraphics();
        fondoG.setColor(Color.white);
        fondoG.fillRect(0, 0, getWidth(), getWidth());
            JFrame frame = new JFrame("Pixel");
            frame.setResizable(false);
            frame.add(this);
            frame.setSize(this.getWidth(), this.getHeight());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
    }


    @Override
    public void update (Graphics g){
            clear();
            setBack(MyKeyInstance.GetX(), MyKeyInstance.GetY(), MyKeyInstance.GetZ());
            cubeProyection(300, 100, 0, 500, 400, 5, Color.BLACK);
            g.drawImage(fondo, 0, 0, this);
    }

    @Override
    public void paint (Graphics g){
        if (checkExistance == false) {
            buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
            buffer.setRGB(0, 0, Color.blue.getRGB());
            g.drawImage(buffer, getWidth() / 2, getHeight() / 2, this);
            checkExistance = Boolean.TRUE;
            //update(g);
        }
          update(g);
        clear();
    }

    public void clear () {
        fondoG.setColor(Color.white);
        fondoG.fillRect(0, 0, getWidth(), getHeight());
        repaint();
    }

    public void putPixel ( int x, int y, Color c){
        buffer.setRGB(0, 0, c.getRGB());
        fondo.getGraphics().drawImage(buffer, x, y, this);
        repaint();
    }
    public void setBack ( int x, int y, int z){
        this.viewX = x;
        this.viewY = y;
        this.viewZ = z;
    }
    public void AlgoritmoDDALine ( int x0, int y0, int x1, int y1, Color c){
        float dx = x1 - x0;
        float dy = y1 - y0;
        float x = x0;
        float y = y0;
        float length;
        float xinc;
        float yinc;

        if (Math.abs(dx) > Math.abs(dy)) {

            length = Math.abs(dx);

        } else length = Math.abs(dy);

        xinc = dx / length;
        yinc = dy / length;

        putPixel(Math.round(x), Math.round(y), c);

        for (int k = 1; k < length; k++) {
            x = x + xinc;
            y = y + yinc;
            putPixel(Math.round(x), Math.round(y), c);
        }
    }
    public void drawCuadrado ( int x, int y, int a, int l, Color c){

        int x0 = x;
        int x1 = x + l;
        int y0 = y;
        int y1 = y + a;

        AlgoritmoDDALine(x0, y0, x0, y1, c);
        AlgoritmoDDALine(x1, y0, x1, y1, c);
        AlgoritmoDDALine(x0, y1, x1, y1, c);
        AlgoritmoDDALine(x0, y0, x1, y0, c);

    }
    public void Cuadrado ( int x1, int y1, int x2, int y2, Color c){
        AlgoritmoDDALine(x1, y1, x2, y1, c);
        AlgoritmoDDALine(x1, y1, x1, y2, c);
        AlgoritmoDDALine(x2, y1, x2, y2, c);
        AlgoritmoDDALine(x1, y2, x2, y2, c);
    }
    public void cubeProyection ( int x1, int y1, int z1, int x2, int y2, int z2, Color c){

        Point[] cubeOnePoints;
        Point[] cubeTwoPoints;

        cubeOnePoints = oneProyection(x1, y1, z1, x2, y2, z2);
        cubeTwoPoints = twoProyection(x1, y1, z1, x2, y2, z2);

        Cuadrado(cubeOnePoints[0].x, cubeOnePoints[0].y, cubeOnePoints[3].x, cubeOnePoints[3].y, c);
        Cuadrado(cubeTwoPoints[0].x, cubeTwoPoints[0].y, cubeTwoPoints[1].x, cubeTwoPoints[1].y, c);

        for (int i = 0; i < 4; i++) {
            putPixel(cubeOnePoints[i].x, cubeOnePoints[i].y, c);
            putPixel(cubeTwoPoints[i].x, cubeTwoPoints[i].y, c);

            if (i != 3)
                AlgoritmoDDALine(cubeOnePoints[i].x, cubeOnePoints[i].y, cubeTwoPoints[i + 1].x, cubeTwoPoints[i + 1].y, c);
            else
                AlgoritmoDDALine(cubeOnePoints[i].x, cubeOnePoints[i].y, cubeTwoPoints[0].x, cubeTwoPoints[0].y, c);
        }
    }

    private Point[] oneProyection ( int x1, int y1, int z1, int x2, int y2, int z2){
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
    private Point[] twoProyection ( int x1, int y1, int z1, int x2, int y2, int z2){
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

    private Point convert ( int x, int y, int z){
        int xTemp, yTemp;
        System.out.println("x= " + x);
        xTemp = viewX - ((viewZ * (x - viewX)) / (z - viewZ));
        yTemp = viewY - ((viewZ * (y - viewY)) / (z - viewZ));

        Point converted = new Point(xTemp, yTemp);

        return converted;
    }
    @Override
    public void run () {
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

    public boolean GetChange () {
        return MyKeyInstance.GetChange();
    }

        public void SetChange ( boolean NewChange){
        MyKeyInstance.SetChange(NewChange);
    }

}