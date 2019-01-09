/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Akshit Ostwal
 */
import java.awt.Color;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;

public class Simplegui implements ActionListener {
    JButton button;
    JFrame frame;
    public static void main(String[] args){
    Simplegui gui=new Simplegui();
    gui.go();
    
    }
    
    public void go(){
        frame=new JFrame();
        button =new JButton();    
     
        button.addActionListener(this);
        
        frame.getContentPane().add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        button.setText("click me");
        frame.setVisible(true);
        
    }
       
  
    @Override
    public void actionPerformed(ActionEvent event){ 
        button.setText("i have been clicked");
        frame.repaint();
        
    }
}
class MyDrawPanel extends JPanel{
    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.orange);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
