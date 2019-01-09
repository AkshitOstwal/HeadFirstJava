/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Akshit Ostwal
 */
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestPaint extends JPanel {
    

    @Override
    public void paintComponent(Graphics g) {
        
        Graphics2D ged =(Graphics2D) g;
        GradientPaint gradient =new GradientPaint(70,70,Color.blue,150,150,Color.orange);
        ged.setPaint(gradient);
        ged.fillOval(70, 70, 100, 100);
        g.setColor(Color.red);
        g.drawOval(0, 0, getWidth(), getHeight());
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setBackground(Color.yellow);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(300, 300);
        jFrame.add(new TestPaint());
        jFrame.setVisible(true);
    }
}
