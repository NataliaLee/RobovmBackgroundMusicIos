package com.natashaleonteva.backgroundmusic;

import org.robovm.apple.avfoundation.AVAudioSession;
import org.robovm.apple.avfoundation.AVAudioSessionCategory;
import org.robovm.apple.avfoundation.AVAudioSessionCategoryOptions;
import org.robovm.apple.avfoundation.AVAudioSessionSetActiveOptions;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSErrorException;
import org.robovm.apple.foundation.NSNotification;
import org.robovm.apple.foundation.NSNotificationCenter;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.objc.Selector;
import org.robovm.objc.annotation.Method;

import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.badlogic.gdx.backends.iosrobovm.objectal.ALBuffer;
import com.badlogic.gdx.backends.iosrobovm.objectal.OALAudioSession;
import com.badlogic.gdx.backends.iosrobovm.objectal.OALAudioTrack;
import com.badlogic.gdx.backends.iosrobovm.objectal.OALSimpleAudio;
import com.natashaleonteva.backgroundmusic.BackgroundMusic;

public class IOSLauncher extends IOSApplication.Delegate {
    IosMusicController iosMusicController=new IosMusicController();
    private IOSApplication iosApplication;

    @Override
    protected IOSApplication createApplication() {

        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        //initAudioSession();
        BackgroundMusic.getInstance().setMusicController(iosMusicController);
        iosApplication= new IOSApplication(BackgroundMusic.getInstance(), config);
        iosApplication.addLifecycleListener(new LifecycleListener() {
            @Override
            public void pause() {
                System.out.println("lifecircle pause");
            }

            @Override
            public void resume() {
                System.out.println("lifecircle resume");

            }

            @Override
            public void dispose() {
                System.out.println("lifecircle dispose");

            }
        });
        return iosApplication;
    }


    public void initAudioSession(){
        //   UIApplication.getSharedApplication().registerForRemoteNotifications();
        System.out.println("init Audio session");
        AVAudioSession instance=AVAudioSession.getSharedInstance();
        try {
            instance.setCategory(AVAudioSessionCategory.Playback, AVAudioSessionCategoryOptions.MixWithOthers);
            // instance.setMode(AVAudioSessionMode.SpokenAudio);
            boolean activated=instance.setActive(true, AVAudioSessionSetActiveOptions.NotifyOthersOnDeactivation);
            System.out.println("session activated=" + activated);

            UIApplication.getSharedApplication().beginReceivingRemoteControlEvents();
        } catch (NSErrorException e) {
            System.out.println("error init audioSession");
            e.printStackTrace();
        }

      //  OALSimpleAudio.sharedInstance().setAllowIpod(true);
        OALSimpleAudio.sharedInstance().setHonorSilentSwitch(false);
       // OALSimpleAudio.sharedInstance().setUseHardwareIfAvailable(false);
        setObservers();
    }

    public void setObservers(){
        NSNotificationCenter.getDefaultCenter().addObserver(this, Selector.register("routeChanged"), AVAudioSession.RouteChangeNotification(), AVAudioSession.getSharedInstance());
        NSNotificationCenter.getDefaultCenter().addObserver(this, Selector.register("audioSessionInterrupted"), AVAudioSession.InterruptionNotification(), AVAudioSession.getSharedInstance());
    }
    public void removeObservers(){
        NSNotificationCenter.getDefaultCenter().removeObserver(this);
    }

    @Method
    private void RouteChanged(NSNotification notification){
        System.out.println("route changed " + notification.getName());
    }

    @Method
    private void audioSessionIterrupted(NSNotification notification){
        System.out.println("audioSessionInterrupted " + notification.getName());
    }

    @Override
    public void willTerminate (UIApplication uiApp) {
       // UIApplication.getSharedApplication().endReceivingRemoteControlEvents();
        //UIApplication.getSharedApplication().unregisterForRemoteNotifications();
       // removeObservers();
        super.willTerminate(uiApp);
    }
    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    @Override
    public void didBecomeActive (UIApplication application){
        super.didBecomeActive(application);
        System.out.println("didbecomeactive");
        iosMusicController.setProgressUpdater(BackgroundMusic.getInstance().playerScreen);
    }

    @Override
    public void willResignActive(UIApplication uiApp){
        System.out.println("willResignActive(background)");
        iosMusicController.setProgressUpdater(null);
        super.willResignActive(uiApp);
    }

}