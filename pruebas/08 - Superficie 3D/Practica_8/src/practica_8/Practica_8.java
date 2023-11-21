package practica_8;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.*;

public class Practica_8 extends JFrame implements KeyListener {
BufferedImage buffer;
	pixel pixel;
	ArrayList<surface> surfaces, surfaceOriginal;

	public static int degrees;
	int x = 260;
	int z = 250;

	public static void main(String[] args) {
		new Practica_8();
	}

	public Practica_8() {
		setTitle("08 - Superficie 3D");
		int width = 600, height = 600;
		this.setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		buffer = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		pixel = new pixel(this, buffer);

		surfaceOriginal = surface.surfacePoint(new surface(0, 0, 0), new surface(260, 0, 250), 10);
		surfaces = surface.surfacePoint(new surface(0, 0, 0), new surface(260, 0, 250), 10);
		setVisible(true);
		addKeyListener(this);

	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

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

	@Override
	public void paint(Graphics g) {
		pixel.clear();
		surface.drawSurface(surfaces);
		g.drawImage(buffer, 0, 0, this);
	}
}
