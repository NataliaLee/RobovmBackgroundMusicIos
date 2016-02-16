package com.natashaleonteva.backgroundmusic;

import com.badlogic.gdx.Gdx;

import org.robovm.apple.avfoundation.AVAudioPlayer;
import org.robovm.apple.dispatch.DispatchQueue;
import org.robovm.apple.foundation.NSErrorException;
import org.robovm.apple.foundation.NSURL;

import java.io.File;
import java.util.Timer;

/**
 * Created by natashaleonteva on 05.02.16.
 */
public class IosMusicController implements MusicController {
    AVAudioPlayer player;
    private Timer progressTimer=new Timer();

    PlayerDelegate playerDelegate=new PlayerDelegate();

    @Override
    public void play() {
        File file= Gdx.files.internal("sadday.mp3").file();
        NSURL url=new NSURL(file);
        try {
            player=new AVAudioPlayer(url);
            player.setDelegate(playerDelegate);
            player.prepareToPlay();
            DispatchQueue.getGlobalQueue(DispatchQueue.PRIORITY_HIGH, 0).async(new Runnable() {
                @Override
                public void run() {
                    player.play();
                }
            });
        } catch (NSErrorException e) {
            e.printStackTrace();
        }
    }

    public void didPlayToFinish(){
        System.out.println("did play to finish");
    }

    @Override
    public void pause() {
        player.pause();
    }

    @Override
    public boolean isPlaying() {
        if (player==null)
            return false;
        /*return player.getRate()==1.0;*/
        return player.isPlaying();
    }
}
