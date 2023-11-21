package practica_8;
import java.awt.*;
import java.awt.Color;
import java.util.*;

public class line {

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