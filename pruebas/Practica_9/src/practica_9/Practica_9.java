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
        super("Cilindro");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Pixel admin = new Pixel(buffer);
        this.setVisible(true);
        setLocationRelativeTo(null);
    }

    public void paint(Graphics g){
        ArrayList<Point3D> p = new ArrayList<>();
        double lSup=4, lSup1 = 6, curva=0 ,x, y, z, t, w;
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
            AlgorDibujo.CurveExplicita(p, Color.PINK); 
            g.drawImage(buffer, 0, 0, this);
        }
    }
}
