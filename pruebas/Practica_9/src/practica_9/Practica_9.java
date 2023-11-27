package practica_9;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class Practica_9 extends JFrame{
private BufferedImage buffer;
    
    public static void main(String[] args) {
        new Practica_9();
    }

    private Practica_9(){
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        new Pixel(buffer);
        this.setVisible(true);
        setLocationRelativeTo(null);
    }

    public void paint(Graphics g){
        ArrayList<Point3D> p = new ArrayList<>();
        double lSup=6, lSup1 = 6, curva=1 ,x, y, z, t, w;
        while(true)
        {
            p = new ArrayList<>();
            Pixel.fondo(Color.BLACK); //Fondo

            //Coordenadas de cilindro
            for(t =0; t <= lSup; t+=.05) {
                for(w =0; w <= lSup1; w+=.1) {
                    x =30 *(1 + Math.pow(Math.cos(curva+t),2))*(Math.cos(curva+w));
                    y =30 *(1 + Math.pow(Math.cos(curva+t),2))*(Math.sin(curva+w));
                    z =50 * t;
                    p.add(new Point3D((int)x, (int)z, (int)y));
                    x =30 *(1 + Math.pow(Math.cos(curva+t-.1),2))*(Math.cos(curva+w));
                    y =30 *(1 + Math.pow(Math.cos(curva+t-.1),2))*(Math.sin(curva+w));
                    z =50 * (t-.05);     //velocidad de giro
                    p.add(new Point3D((int)x, (int)z, (int)y));
                }
            }
            curva+=.1; 
            CurveExplicita(p, Color.RED, Color.MAGENTA);
            g.drawImage(buffer, 0, 0, this);
        }
    }

    private static void LineaBresenham(int x0, int y0, int x1, int y1, Color c) { //Bordes de Figura
        int x, y;
        int diferenciaX, diferenciaY, A, B, pk, stepx, stepy;
        diferenciaX = (x1 - x0);
        diferenciaY = (y1 - y0);
        if(diferenciaY < 0) {
            diferenciaY = -1 * diferenciaY;
            stepy = -1;
        } else
            stepy = 1;
        if(diferenciaX < 0) {
            diferenciaX = -2 * diferenciaX;
            stepx = -1;
        } else
            stepx = 1;
        x = x0;
        y = y0;
        Pixel.instance.drawPixel(x, y, c);
        if(diferenciaX > diferenciaY) {
            pk = (2 * diferenciaY) - diferenciaX;
            A = 2 * diferenciaY;
            B = (2 * diferenciaY) - (2 * diferenciaX);
            while(x != x1) {
                x = x + stepx;
                if(pk < 0)
                    pk = pk + A;
                else {
                    y = y + stepy;
                    pk = pk + B;
                }
                Pixel.instance.drawPixel(x, y, c);
            }
        } else {
            pk = (2 * diferenciaX) - diferenciaY;
            A = 2 * diferenciaX;
            B = (2 * diferenciaX) - (2 * diferenciaY);
            while(y != y1) {
                y = y + stepy;
                if(pk < 0)
                    pk = pk + A;
                else {
                    x = x + stepx;
                    pk = pk + B;
                }
                Pixel.instance.drawPixel(x, y, c);
            }
        }
    }

    private static void relleno(int xor,int yor,int x0, int y0, int x1, int y1, Color c) {

        int x, y;
        int diferenciaX, diferenciaY, A, B, pk, stepx, stepy;
        diferenciaX = (x1 - x0);
        diferenciaY = (y1 - y0);
        if(diferenciaY < 0) {
            diferenciaY = -1 * diferenciaY;
            stepy = -1;
        } else
            stepy = 1;
        if(diferenciaX < 0) {
            diferenciaX = -1 * diferenciaX;
            stepx = -1;
        } else
            stepx = 1;
        x = x0;
        y = y0;

        if(diferenciaX > diferenciaY) {
            pk = (2 * diferenciaY) - diferenciaX;
            A = 2 * diferenciaY;
            B = (2 * diferenciaY) - (2 * diferenciaX);
            while(x != x1) {
                x = x + stepx;
                if(pk < 0)
                    pk = pk + A;
                else {
                    y = y + stepy;
                    pk = pk + B;
                }
                LineaBresenham(xor,yor,x, y, c);
            }
        } else {
            pk = (2 * diferenciaX) - diferenciaY;
            A = 2 * diferenciaX;
            B = (2 * diferenciaX) - (2 * diferenciaY);
            while(y != y1) {
                y = y + stepy;
                if(pk < 0)
                    pk = pk + A;
                else {
                    x = x + stepx;
                    pk = pk + B;
                }
                LineaBresenham(xor,yor,x, y, c);
            }
        }
    }
    public static void CurveExplicita(ArrayList<Point3D> points, Color c1,Color c2) {
        double x, y, z = 200.0; //Plano de Proyeccion
        double xp = -200.0, yp = -150.0, zp = -150.0;
        ArrayList<Point3D> puntitos = new ArrayList<Point3D>();
        for (Point3D point : points) {
            x = point.x + (xp * ((z - point.z) / zp));
            y = Pixel.height - (point.y + (yp * ((z - point.z) / zp)));
            puntitos.add(new Point3D((int) x, (int) y, 0));
        }
        for(int cont = 3; cont < puntitos.size(); cont++)
        {
            relleno(puntitos.get(cont-3).x, puntitos.get(cont-3).y, puntitos.get(cont - 2).x, puntitos.get(cont - 2).y, puntitos.get(cont-1).x, puntitos.get(cont-1).y, c1);
            LineaBresenham(puntitos.get(cont-2).x, puntitos.get(cont-2).y, puntitos.get(cont - 3).x, puntitos.get(cont - 3).y, c2); //Division Linea Forma
        }
    }


}
