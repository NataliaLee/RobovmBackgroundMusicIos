package com.natashaleonteva.backgroundmusic;

import com.badlogic.gdx.backends.iosrobovm.objectal.AVAudioPlayerDelegateAdapter;
import org.robovm.apple.foundation.NSObject;

/**
 * Created by natashaleonteva on 16.02.16.
 */
public class PlayerDelegate extends AVAudioPlayerDelegateAdapter {
    IosMusicController musicController;

    public PlayerDelegate(IosMusicController musicController){
        this.musicController=musicController;
    }

    @Override
    public void didFinishPlaying (NSObject player, boolean success) {
        System.out.println("didFinishPlaying");
        if (musicController!=null)
            musicController.didPlayToFinish();
    }
}