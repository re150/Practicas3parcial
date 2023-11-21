import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pixel g = new Pixel();
           // g.  cubeProyection(200, 100, 5, 400, 400, 40, Color.BLACK);
            new Thread(g).start();
        });
    }
}
