import java.awt.*; 
import javax.swing.*;
import javax.sound.midi.*;
import java.util.*;
import java.awt.event.*;

public class BeatBox{
    JPanel mainPanel;
    ArrayList<JCheckBox> checkboxList;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame theFrame;
    String[] instrumentNames={
        "bass drum","closed hi-hat","open hi hat","acoustic snare","crash cymbal"
        ,"hand clap","high tom","hi bongo","maracas","whistle","low congo","cowbell","vibraslap",
        "low mid tom","high agogo","open hi congo"
    };
    int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
    
    public static void main(String[] args) {
        new BeatBox().buildGUI();
    }
    public void buildGUI(){
        theFrame = new JFrame("cyber BeatBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        checkboxList =new ArrayList<JCheckBox>();
        Box buttonBox =new Box(BoxLayout.Y_AXIS);
        
        JButton start =new JButton("Start");
        start.addActionListener(new MyStartListener());
        buttonBox.add(start);

        JButton stop =new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo =new JButton("upTempo");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo =new JButton("downTempo");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);  
        
        JButton clearAll =new JButton("clear all");
        clearAll.addActionListener(new MyClearAllListener());
        buttonBox.add(clearAll); 
        
        Box nameBox =new Box(BoxLayout.Y_AXIS);
        for(int i=0;i<16;i++){
            nameBox.add(new Label(instrumentNames[i]));
        }
        background.add(BorderLayout.EAST,buttonBox);
        background.add(BorderLayout.WEST,nameBox);
        
        theFrame.getContentPane().add(background);
        
        GridLayout grid =new GridLayout(16,16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel =new JPanel(grid);
        background.add(BorderLayout.CENTER,mainPanel);
        for(int i=0;i<256;i++){
            JCheckBox c=new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }
        
        setUpMidi();
        theFrame.setBounds(50, 50, 300, 300);
        theFrame.pack();
        theFrame.setVisible(true);
    }
    public void setUpMidi(){
        try{
            sequencer =MidiSystem.getSequencer();
            sequencer.open();
            sequence =new Sequence(Sequence.PPQ,4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("fail setupmidi");
        }
    }
    public void buildTrackAndStart(){
        @SuppressWarnings("UnusedAssignment")
        int[] trackList = null;
        sequence.deleteTrack(track);
        track = sequence.createTrack();
        for( int i=0;i<16;i++)
        {
            trackList = new int[16];
            int key =instruments[i];
            
            for(int j=0;j<16;j++){
              JCheckBox jc = (JCheckBox) checkboxList.get(j+(16*i));
              if(jc.isSelected())
                  trackList[j]=key;
              else
                  trackList[j]=0;
            }
            makeTracks(trackList);
            track.add(makeEvent(176,1,127,0,16));
        }
        track.add(makeEvent(176,1,127,0,16));
        try{
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("fail build track");
        }
    }
    public void makeTracks(int[] list){
        for(int i=0;i<16;i++){
            int key =list[i];
            if(key!=0){
                track.add(makeEvent(144,9,key,100,i));
                track.add(makeEvent(128,9,key,100,i+1));
            }
        }
    }
    public MidiEvent makeEvent(int comd,int chan,int one,int two,int tick){
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
    public class MyStartListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) { //To change body of generated methods, choose Tools | Templates.
            buildTrackAndStart();
        }
        
    }
    public class MyStopListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) { //To change body of generated methods, choose Tools | Templates.
           if(sequencer.isRunning())
           {sequencer.stop();
            System.out.println("stop button working");
           // sequencer.close();
           }
        }
    }
    public class MyUpTempoListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) { //To change body of generated methods, choose Tools | Templates.
            float tempoFactor = sequencer.getTempoFactor();
            System.out.println("current tempo is"+tempoFactor);
            sequencer.setTempoFactor((float) (tempoFactor +.5));
        }
    }    
    public class MyDownTempoListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) { //To change body of generated methods, choose Tools | Templates.
            float tempoFactor = sequencer.getTempoFactor();
            System.out.println("current tempo is"+tempoFactor);
            sequencer.setTempoFactor((float) (tempoFactor -.5));
        }   
    }
    public class MyClearAllListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) { //To change body of generated methods, choose Tools | Templates.
            for(int i=0;i<256;i++){
            JCheckBox c=(JCheckBox)checkboxList.get(i);
            c.setSelected(false);
            }
        }   
    }
}