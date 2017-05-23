package com.Davi.poems.gui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import static java.lang.Thread.sleep;

/**
 * Created by David on 2017/3/22.
 */
public class MusicThread implements Runnable{
    Controller c ;
    public MusicThread(Controller c){
        this.c = c;
    }
    public void run() {
        String path = this.getClass().getResource("/Loveless.mp3").toString();
        //media = new Media("file:////"+path);
        if (c.media == null){
            c.media = new Media(path);
            c.mp = new MediaPlayer(c.media);
        }
        c.mp.play();


    }
    public void stop(){
        c.mp.stop();
    }
    public void pause(){c.mp.pause();}
    public void restart(){
        c.mp.stop();
        c.mp.play();
    }

}
