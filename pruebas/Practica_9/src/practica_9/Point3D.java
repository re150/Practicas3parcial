package practica_9;

public class Point3D {
    public int x, y, z;
    public static Point3D instance;

    public Point3D(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void resetPoint(Point3D point) {
        point.x = 0;
        point.y = 0;
        point.z = 0;
    }
  }