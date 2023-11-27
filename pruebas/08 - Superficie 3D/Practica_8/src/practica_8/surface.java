package practica_8;

import java.awt.*;
import java.awt.Color;
import java.util.*;

public class surface {
	public int x, y, z;

	public surface(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public static ArrayList<surface> surfacePoint(surface origin, surface destiny, int div) {
		ArrayList<surface> points = new ArrayList<surface>();
		int x = 0;
		int y = 0;
		int z = 0;

		ArrayList<Integer> pointsX = new ArrayList<Integer>();
		ArrayList<Integer> pointsY = new ArrayList<Integer>();
		ArrayList<Integer> pointsZ = new ArrayList<Integer>();

		double incX = (((double) destiny.x - (double) origin.x) / (double) div);
		double incY = (((double) destiny.y - (double) origin.y) / (double) div);
		double incZ = (((double) destiny.z - (double) origin.z) / (double) div);

		if (destiny.x == 0) {
			for (double index = origin.y; index <= destiny.y; index += incY) {
				pointsY.add((int) index);
			}
			for (double index2 = origin.z; index2 <= destiny.z; index2 += incZ) {
				pointsZ.add((int) index2);
			}
			for (int varZ = 1; varZ < pointsZ.size(); varZ++) {
				for (int varY = 1; varY < pointsY.size(); varY++) {

					points.add(new surface(x, pointsY.get(varY - 1), pointsZ.get(varZ - 1)));
					points.add(new surface(x, pointsY.get(varY), pointsZ.get(varZ - 1)));
					points.add(new surface(x, pointsY.get(varY), pointsZ.get(varZ)));
					points.add(new surface(x, pointsY.get(varY - 1), pointsZ.get(varZ)));
				}
			}
		}
		if (destiny.y == 0) {
			for (double index = origin.x; index <= destiny.x; index += incX) {
				pointsX.add((int) index);
			}
			for (double index2 = origin.z; index2 <= destiny.z; index2 += incZ) {
				pointsZ.add((int) index2);
			}
			for (int varZ = 1; varZ < pointsZ.size(); varZ++) {
				for (int varX = 1; varX < pointsX.size(); varX++) {

					points.add(new surface(pointsX.get(varX - 1), y, pointsZ.get(varZ - 1)));
					points.add(new surface(pointsX.get(varX), y, pointsZ.get(varZ - 1)));
					points.add(new surface(pointsX.get(varX), y, pointsZ.get(varZ)));
					points.add(new surface(pointsX.get(varX - 1), y, pointsZ.get(varZ)));
				}
			}
		}

		if (destiny.z == 0) {
			for (double index = origin.x; index <= destiny.x; index += incX) {
				pointsX.add((int) index);
			}
			for (double index2 = origin.y; index2 <= destiny.y; index2 += incY) {
				pointsY.add((int) index2);
			}
			for (int varY = 1; varY < pointsY.size(); varY++) {
				for (int varX = 1; varX < pointsX.size(); varX++) {

					points.add(new surface(pointsX.get(varX - 1), pointsY.get(varY - 1), z));
					points.add(new surface(pointsX.get(varX), pointsY.get(varY - 1), z));
					points.add(new surface(pointsX.get(varX), pointsY.get(varY), z));
					points.add(new surface(pointsX.get(varX - 1), pointsY.get(varY), z));
				}
			}
		}
		return points;
	}

	public static void drawSurface(ArrayList<surface> points) {
		int limit = 1;
		double x, y, z = 400.0;
		double xp = -50.0;
		double yp = -90.0;
		double zp = -80.0;
		ArrayList<surface> tempBase = new ArrayList<surface>();
		ArrayList<surface> tempDeep = new ArrayList<surface>();

		for (int index = 0; index < points.size(); index++) {

			x = points.get(index).x + (xp * ((z - points.get(index).z) / zp));
			y = pixel.height - (points.get(index).y + (yp * ((z - points.get(index).z) / zp)));

			tempBase.add(new surface((int) x, (int) y, 0));

			double tempY = (Math.sin(Math.toRadians(90 + ((points.get(index).x + points.get(index).z) / 2 * 1.125)))
					* 80);

			x = points.get(index).x + (xp * ((z - points.get(index).z) / zp));
			y = pixel.height - (tempY + (yp * ((z - points.get(index).z) / zp)));

			tempDeep.add(new surface((int) x, (int) y, 0));
		}

		for (int index = 1; index <= tempDeep.size(); index++) {
			if (limit % 4 == 0) {
				drawLine(tempDeep.get(index - 1).x, tempDeep.get(index - 1).y, tempDeep.get(index - 4).x,
						tempDeep.get(index - 4).y, Color.red);
				limit = 1;
			} else {
				drawLine(tempDeep.get(index - 1).x, tempDeep.get(index - 1).y, tempDeep.get(index).x,
						tempDeep.get(index).y, Color.red);
				limit++;
			}
		}
	}

	public static ArrayList<surface> doRotationX(ArrayList<surface> points, double degree) {

		int x, y, z;
		ArrayList<surface> jelp = new ArrayList<surface>();

		for (int index = 0; index < points.size(); index++) {

			x = points.get(index).x;
			y = (int) (((double) points.get(index).y * Math.cos(Math.toRadians(degree)))
					+ ((double) points.get(index).z * Math.sin(Math.toRadians(degree))));
			z = (int) (((double) points.get(index).z * Math.cos(Math.toRadians(degree)))
					- ((double) points.get(index).y * Math.sin(Math.toRadians(degree))));

			jelp.add(new surface(x, y, z));
		}

		return jelp;
	}

	public static ArrayList<surface> doRotationY(ArrayList<surface> points, double degree) {

		int x, y, z;
		ArrayList<surface> jelp = new ArrayList<surface>();
		for (int index = 0; index < points.size(); index++) {

			y = points.get(index).y;
			x = (int) (((double) points.get(index).x * Math.cos(Math.toRadians(degree)))
					- ((double) points.get(index).z * Math.sin(Math.toRadians(degree))));
			z = (int) (((double) points.get(index).x * Math.sin(Math.toRadians(degree)))
					+ ((double) points.get(index).z * Math.cos(Math.toRadians(degree))));

			jelp.add(new surface(x, y, z));
		}

		return jelp;
	}

	public static ArrayList<surface> doRotationZ(ArrayList<surface> points, double degree) {

		int x, y, z;
		ArrayList<surface> jelp = new ArrayList<surface>();
		for (int index = 0; index < points.size(); index++) {

			z = points.get(index).z;

			x = (int) (((double) points.get(index).x * Math.cos(Math.toRadians(degree)))
					+ ((double) points.get(index).y * Math.sin(Math.toRadians(degree))));
			y = (int) (((double) points.get(index).y * Math.cos(Math.toRadians(degree)))
					- ((double) points.get(index).x * Math.sin(Math.toRadians(degree))));
			jelp.add(new surface(x, y, z));
		}

		return jelp;
	}

	public static void drawLine(int x1, int y1, int x2, int y2, Color color) {
		int x, y;
		int dx, dy, A, B, pk;
		dx = (x2 - x1);
		dy = (y2 - y1);

		int stepy = (dy < 0) ? -1 : 1;
		int stepx = (dx < 0) ? -1 : 1;

		dy *= stepy;
		dx *= stepx;

		x = x1;
		y = y1;

		pixel.canvas.putPixel(x, y, color);
		if (dx > dy) {
			pk = (2 * dy) - dx;
			A = 2 * dy;
			B = (2 * dy) - (2 * dx);

			while (x != x2) {
				x = x + stepx;
				if (pk < 0) {
					pk = pk + A;
				} else {
					y = y + stepy;
					pk = pk + B;
				}

				pixel.canvas.putPixel(x, y, color);
			}
		} else {
			pk = (2 * dx) - dy;
			A = 2 * dx;
			B = (2 * dx) - (2 * dy);
			while (y != y2) {
				y = y + stepy;
				if (pk < 0) {
					pk = pk + A;
				} else {
					x = x + stepx;
					pk = pk + B;
				}

				pixel.canvas.putPixel(x, y, color);
			}
		}
	}
}