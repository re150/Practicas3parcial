import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static java.lang.Math.round;

public class Pixel extends JPanel implements Runnable {
    private BufferedImage buffer;
    private BufferedImage fondo;
    private Graphics fondoG;
    private double vertex[][][][] = {{{{-1, -1, -1}, {1, -1, -1}}, {{-1, 1, -1},{1, 1, -1}}}, {{{-1, -1, 1}, {1, -1, 1}}, {{-1, 1, 1}, {1, 1, 1}}}};
    // rotations in radians
    private double xyR = 0, xzR = 0, yzR = 0;

    // view attributes
    private  int IMAGE_SIZE = 500,  OFFSET = 200, DIAMETER = 8, scala = 50 ;
    private int viewX = 5 , viewY = 20 , viewZ = -20 , tX =0, tY =0;




    public Pixel() {
        init();
        repaint();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setDoubleBuffered(true);

        addKeyListener(new KeyAdapter() {
             @Override
                 public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_LEFT) {
                        viewX -=5;

                    }
                    if (key == KeyEvent.VK_RIGHT) {
                        viewX += 5;

                    }
                    if (key == KeyEvent.VK_UP) {
                        viewY -= 5;

                    }
                    if (key == KeyEvent.VK_DOWN) {
                        viewY +=5;

                    }
                    if (key == KeyEvent.VK_Z ){
                        viewZ += 5;
                        // Ajustar rotación izquierda
                        xyR = Math.max(xyR - 0.005, -Math.PI); // Limitar a -π
                    }
                    if (key == KeyEvent.VK_X ){
                        viewZ -= 5;
                        // Ajustar rotación derecha
                        xyR = Math.min(xyR + 0.005, Math.PI); // Limitar a π
                    }
                 if (key == KeyEvent.VK_W ){
                     tX += 5;
                     xzR = Math.min(xzR + 0.005, Math.PI); // Limitar a π
                 }
                 if (key == KeyEvent.VK_S ){
                    tX -= 5;
                     xzR = Math.max(xzR - 0.005, -Math.PI); // Limitar a -π
                 }
                 if (key == KeyEvent.VK_D ){
                     if(scala < 100) {
                         scala += 10;
                     }
                 }
                 if(key == KeyEvent.VK_A){
                     if(scala > 10) {
                         scala -= 10;
                     }
                 }

                }
        });
    }

    public void init () {
        setSize(700, 700);
        setVisible(true);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        fondo = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        fondoG = fondo.getGraphics();
        fondoG.setColor(Color.black);
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
        g.drawImage(fondo, 0, 0, this);
    }

    @Override
    public void paint (Graphics g){
        update(g);
        clear();
        try {
            // practica 1

            //proyeccionParalela(30, 20, 0, 50, 40, 5, Color.white);
            //proyeccionParalela(200, 100, 5, 500, 400, 0, Color.white);
          //  proyeccionParalela(600, 600, 5, 400, 800, 8, Color.RED);

            drawCuboR();
        }catch (Exception e){}
    }


    public void clear () {
        fondoG.setColor(Color.black);
        fondoG.fillRect(0, 0, getWidth(), getHeight());
        repaint();
    }

    public void putPixel ( int x, int y, Color c){
       buffer.setRGB(0, 0, c.getRGB());
       fondo.getGraphics().drawImage(buffer, x, y, this);
       repaint();
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
    public void drawCuadrado ( int x1, int y1, int x2, int y2, Color c){
        AlgoritmoDDALine(x1, y1, x2, y1, c);
        AlgoritmoDDALine(x1, y1, x1, y2, c);
        AlgoritmoDDALine(x2, y1, x2, y2, c);
        AlgoritmoDDALine(x1, y2, x2, y2, c);
    }

    public void proyeccionParalela(int x1, int y1, int z1, int x2, int y2, int z2, Color c) {

        int[][] cubeOnePoints;
        int[][] cubeTwoPoints;

        cubeOnePoints = cuboProyecion1(x1, y1, z1, x2, y2);
        cubeTwoPoints = cuboProyecion2(x1, y1, x2, y2, z2);

        drawCuadrado(cubeOnePoints[0][0], cubeOnePoints[0][1], cubeOnePoints[3][0], cubeOnePoints[3][1],c);
        drawCuadrado(cubeTwoPoints[0][0], cubeTwoPoints[0][1], cubeTwoPoints[1][0], cubeTwoPoints[1][1], c);

        // se dibujan las uniones de los cuadrados
        AlgoritmoDDALine(cubeOnePoints[0][0], cubeOnePoints[0][1], cubeTwoPoints[1][0], cubeTwoPoints[1][1], Color.green);
        AlgoritmoDDALine(cubeOnePoints[1][0], cubeOnePoints[1][1], cubeTwoPoints[2][0], cubeTwoPoints[2][1], Color.green);
        AlgoritmoDDALine(cubeOnePoints[2][0], cubeOnePoints[2][1], cubeTwoPoints[0][0], cubeTwoPoints[3][1], Color.green);
        AlgoritmoDDALine(cubeOnePoints[3][0], cubeOnePoints[3][1], cubeTwoPoints[0][0], cubeTwoPoints[0][1], Color.green);


    }

    private int[][] cuboProyecion1(int x1, int y1, int z1, int x2, int y2) {
        int[][] points = new int[4][2];

        points[0] = convert(x1, y1, z1);
        points[1] = convert(x1, y2, z1);
        points[2] = convert(x2, y1, z1);
        points[3] = convert(x2, y2, z1);

        return points;
    }

    private int[][] cuboProyecion2(int x1, int y1, int x2, int y2, int z2) {
        int[][] points = new int[4][2];

        points[0] = convert(x2, y2, z2);
        points[1] = convert(x1, y1, z2);
        points[2] = convert(x1, y2, z2);
        points[3] = convert(x2, y1, z2);

        return points;
    }

    private int[] convert(int x, int y, int z) {
        int xconvert, yconvert;
        xconvert = viewX - ((viewZ * (x - viewX)) / (z - viewZ) + tX) ;
        yconvert = viewY - ((viewZ * (y - viewY)) / (z - viewZ)  + tY) ;

        return new int[]{xconvert, yconvert };
    }


    public  void drawCuboR(){
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
                drawEdge(vertex[x][y][0][0], vertex[x][y][0][1], vertex[x][y][1][0], vertex[x][y][1][1],  Color.blue);
                drawEdge(vertex[x][0][y][0], vertex[x][0][y][1], vertex[x][1][y][0], vertex[x][1][y][1], Color.blue );
                drawEdge(vertex[0][x][y][0], vertex[0][x][y][1], vertex[1][x][y][0], vertex[1][x][y][1],  Color.blue);
            }
        }
    }
    final void drawEdge(double x1, double y1, double x2, double y2, Color c) {
        AlgoritmoDDALine((int) (x1 * scala) + OFFSET, (int) (-y1 * scala) + OFFSET, (int) (x2 * scala) + OFFSET, (int) (-y2 * scala) + OFFSET, c);
    }

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
    @Override
    public void run () {
        try {
            repaint();
            clear();
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
    }




}