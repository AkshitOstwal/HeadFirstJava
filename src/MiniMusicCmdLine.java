/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.sound.midi.*;
/**
 *
 * @author Akshit Ostwal
 */
public class MiniMusicCmdLine {
    public static void main(String[] args){
        MiniMusicCmdLine mini =new MiniMusicCmdLine();
        if(args.length<2){
            System.out.println("don't forget the instrument and note args");    
        }
        else{
            int instrument = Integer.parseInt(args[0]);
            int note = Integer.parseInt(args[1]);
            mini.play(instrument,note);
        }
    }
    public void play(int instrument,int note){
        try{
            Sequencer player = MidiSystem.getSequencer();
            player.open();
            Sequence seq =new Sequence(Sequence.PPQ,4);
            Track track =seq.createTrack();
            
            MidiEvent event=null;
            ShortMessage first=new ShortMessage();
            first.setMessage(192,1,instrument,0);
            MidiEvent changeInstrument = new MidiEvent(first,1);
            track.add(changeInstrument);

            ShortMessage a=new ShortMessage();
            first.setMessage(144,1,note,0);
            MidiEvent noteOn = new MidiEvent(a,1);
            track.add(noteOn);

            ShortMessage b=new ShortMessage();
            first.setMessage(128,1,note,0);
            MidiEvent noteOff = new MidiEvent(b,16);
            track.add(noteOff);
            
            player.setSequence(seq);
            player.start();
            
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}