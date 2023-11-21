package practica_7;
public class line {
    public point start;
    public point end;
    
    public line(point start, point end){
        this.start = start;
        this.end = end;
    }
    
    public line(int x1, int y1, int z1, int x2, int y2, int z2){
        this.start = new point(x1,y1,z1);
        this.end = new point(x2,y2,z2);
    }
    
    public void rotarAristaYZ(float angulo){
        start.rotarPuntoYZ(angulo);
        end.rotarPuntoYZ(angulo);
    }
    
    public void rotarAristaXZ(float angulo){
        start.rotarPuntoXZ(angulo);
        end.rotarPuntoXZ(angulo);
    }
    
    public void rotarAristaXY(float angulo){
        start.rotarPuntoXY(angulo);
        end.rotarPuntoXY(angulo);
    }
    
}
