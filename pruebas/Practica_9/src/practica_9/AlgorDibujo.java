package practica_9;

import java.awt.Color;
import java.util.*;

public class AlgorDibujo{

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
        c = Color.PINK;
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
    public static void CurveExplicita(ArrayList<Point3D> points, Color c) {
        double x, y, z = 200.0; //Plano de Proyeccion
        double xp = -150.0, yp = -150.0, zp = -150.0;
        ArrayList<Point3D> puntitos = new ArrayList<Point3D>();
        for (Point3D point : points) {
            x = point.x + (xp * ((z - point.z) / zp));
            y = Pixel.height - (point.y + (yp * ((z - point.z) / zp)));
            puntitos.add(new Point3D((int) x, (int) y, 0));
        }
        for(int cont = 3; cont < puntitos.size(); cont++)
        {
            relleno(puntitos.get(cont-3).x, puntitos.get(cont-3).y, puntitos.get(cont - 2).x, puntitos.get(cont - 2).y, puntitos.get(cont-1).x, puntitos.get(cont-1).y, Color.WHITE);
            LineaBresenham(puntitos.get(cont-2).x, puntitos.get(cont-2).y, puntitos.get(cont - 3).x, puntitos.get(cont - 3).y, Color.BLACK); //Division Linea Forma
        }
    }
}