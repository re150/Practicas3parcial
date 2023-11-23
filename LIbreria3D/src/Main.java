import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pixel g = new Pixel();
            new Thread(g).start();
        });
    }
}
