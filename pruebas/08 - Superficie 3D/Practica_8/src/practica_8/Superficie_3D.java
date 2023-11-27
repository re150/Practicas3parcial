package practica_8;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.*;

public class Superficie_3D extends JFrame  {
    BufferedImage buffer;
	pixel pixel;
	ArrayList<surface> surfaces, surfaceOriginal;
	public static int degrees;
	public static void main(String[] args) {
		new Superficie_3D();
	}

	public Superficie_3D() {

		this.setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		pixel = new pixel(this, buffer);

		surfaceOriginal = surface.surfacePoint(new surface(0, 0, 0), new surface(260, 0, 250), 80);
		surfaces = surface.surfacePoint(new surface(0, 0, 0), new surface(260, 0, 250), 80);
		setVisible(true);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {

				if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
					degrees -= 10;
					surfaces = surface.doRotationY(surfaceOriginal, degrees);
				}

				if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
					degrees += 10;
					surfaces = surface.doRotationY(surfaceOriginal, degrees);
				}

				if (evt.getKeyCode() == KeyEvent.VK_UP) {
					degrees += 10;
					surfaces = surface.doRotationX(surfaceOriginal, degrees);
				}

				if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
					degrees -= 10;
					surfaces = surface.doRotationX(surfaceOriginal, degrees);
				}

				if (evt.getKeyChar() == '-') {
					degrees -= 10;
					surfaces = surface.doRotationZ(surfaceOriginal, degrees);
				}

				if (evt.getKeyChar() == '+') {
					degrees += 10;
					surfaces = surface.doRotationZ(surfaceOriginal, degrees);
				}
				repaint();
			}
		});


	}

	@Override
	public void paint(Graphics g) {
		pixel.clear();
		surface.drawSurface(surfaces);
		g.drawImage(buffer, 0, 0, this);
	}
}
