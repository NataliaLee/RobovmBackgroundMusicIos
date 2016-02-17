package com.natashaleonteva.backgroundmusic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.objectal.AVAudioPlayerDelegate;
import com.badlogic.gdx.backends.iosrobovm.objectal.OALAudioTrack;
import com.badlogic.gdx.backends.iosrobovm.objectal.OALSimpleAudio;

import org.robovm.apple.avfoundation.AVAudioPlayer;
import org.robovm.apple.dispatch.DispatchQueue;
import org.robovm.apple.foundation.NSErrorException;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSURL;

import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by natashaleonteva on 05.02.16.
 */
public class IosMusicController implements MusicController {
    OALAudioTrack audioTrack;
    private Timer progressTimer=new Timer();
    private double currentProgress;
    private IProgressUpdater progressUpdater;
    private double paused_time;

    @Override
    public void play() {
        if (audioTrack!=null && audioTrack.isPaused()) {
            audioTrack.setCurrentTime(paused_time);
            audioTrack.play();
            progressUpdater=BackgroundMusic.getInstance().playerScreen;
            startUpdatingProgress();
            return;
        }
        audioTrack=OALAudioTrack.create();
        audioTrack.preloadFile("sadday.mp3");
        audioTrack.setDelegate(new PlayerDelegate(this));
        OALSimpleAudio.sharedInstance().setHonorSilentSwitch(false);
        audioTrack.play();
        progressUpdater=BackgroundMusic.getInstance().playerScreen;
        startUpdatingProgress();
    }

    public void didPlayToFinish(){
        System.out.println("did play to finish.start again");
        progressTimer.cancel();
        currentProgress=0;
        play();
    }

    @Override
    public void pause() {
        if (audioTrack!=null){
            paused_time=audioTrack.getCurrentTime();
            audioTrack.setPaused(true);
            progressTimer.cancel();
        }
    }

    @Override
    public boolean isPlaying() {
        if (audioTrack==null)
            return false;
        return !audioTrack.isPaused();
    }

    @Override
    public void scrollTo(int time) {
        if (audioTrack==null)
            play();
        audioTrack.setCurrentTime(time);
        currentProgress=audioTrack.getCurrentTime();
        if (audioTrack.isPaused()) {
            audioTrack.play();
            startUpdatingProgress();
        }
    }

    private void startUpdatingProgress(){
        System.out.println(" startUpdatingProgress");
        progressTimer.cancel();
        progressTimer=new Timer();
        progressTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateProgress();
            }
        }, 0, 500);
    }

    private void updateProgress(){
        System.out.println("update progress " + audioTrack.isPaused());
        if (audioTrack.isPaused() || progressUpdater==null) {
            progressTimer.cancel();
        }
        currentProgress=audioTrack.getCurrentTime();
        System.out.println(" updateProgress " + currentProgress);
        if(progressUpdater!=null)
            progressUpdater.updateProgress((int)currentProgress);
    }

    public void setProgressUpdater(IProgressUpdater progressUpdater) {
        this.progressUpdater = progressUpdater;
        if (audioTrack!=null)
            startUpdatingProgress();
    }
}
