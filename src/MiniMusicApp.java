 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.awt.event.*;
import javax.sound.midi.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author Akshit Ostwal
 */
public class MiniMusicApp{
    static JFrame f =new JFrame("my first music video");
    static JButton button;
    static MyDrawPanel ml;
    public static void main (String[] args){
        MiniMusicApp mini=new MiniMusicApp();
        mini.setupGUI();
    }
    public void setupGUI(){
        button = new JButton("click me to start!!!");
        button.addActionListener( new buttonAction());
        ml=new MyDrawPanel();
        f.getContentPane().add(BorderLayout.SOUTH,button);
        System.out.println("button called");
        f.getContentPane().add(BorderLayout.CENTER,ml);
      //  f.setContentPane(ml);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(30, 30, 300, 300);
        f.setVisible(true);
        
    }
    
    public void go(){
        try {
            Sequencer sequencer =  MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addControllerEventListener(ml,new int[] {127});
            Sequence seq = new Sequence(Sequence.PPQ,4);
            Track track = seq.createTrack();
            int r=0;
            for(int i=0;i<60;i+=4)
            {
                r=(int)((Math.random()*50)+1);
                track.add(event(144,1,r,100,i));
                track.add(event(176,1,127,100,i));
                track.add(event(128,1,r,100,i));
                track.add(event(160,1,i+10,100,i+4));
                track.add(event(190,1,i+2,100,i));
            }
            sequencer.setSequence(seq);
            sequencer.setTempoInBPM(220);
            sequencer.start();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("failed to make sequencer");
        }
        
    }
    public static MidiEvent event(int comd,int chan,int one,int two,int tick){
        MidiEvent event =null;
        try{
            ShortMessage a= new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event= new MidiEvent(a,tick);
                     
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.out.println("failed to create midievent");
            
        }  
        return event;
    }
    
    class buttonAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            go();
        }
        
    }
    
    class MyDrawPanel extends JPanel implements ControllerEventListener{

    boolean msg=false;

        @Override
        public void controlChange(ShortMessage event) {
           msg=true;
           repaint();
           System.out.println("la ");
        }
        @Override
        public void paintComponent(Graphics g){
            if(msg){
                Graphics2D g2d=(Graphics2D) g;
                int r=(int)(Math.random()*250);
                int b=(int)(Math.random()*250);
                int gr=(int)(Math.random()*250);
                
                g.setColor(new Color(r,gr,b));
                
                int ht=(int)((Math.random()*120)+10);
                int width=(int)((Math.random()*120)+10);
                int x=(int)((Math.random()*90)+10);
                int y=(int)((Math.random()*90)+10);
                
                g.fill3DRect(x, y, width, ht, msg);
                msg=false;
            }
        }//method end
    }//innerclass end
}//class end

