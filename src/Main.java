import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Main {
    public static void main(String[] args)
    {
        boolean start = false;
        Server s = new Server(1520,"teszt.txt");
        Graph g = new Graph();
        g.start();


    }
}
