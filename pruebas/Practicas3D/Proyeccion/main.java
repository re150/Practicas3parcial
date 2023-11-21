package Proyeccion;

import javax.swing.*;

public class main {

/*
    private static Graphic1 g;

    public static void main(String[] args) {
        new main().start();
    }

    void start() {
        g = new Graphic1();
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        boolean exit = false;
        while (!exit) {
            try  {
                if(g.GetChange() == true) {
                    g.repaint();
                    g.SetChange(false);
                }
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
                exit = true;
            }
        }
    }*/
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        Graphic1  g = new Graphic1();
        g.setVisible(true);
        new Thread(g).start();
    });
}
}
