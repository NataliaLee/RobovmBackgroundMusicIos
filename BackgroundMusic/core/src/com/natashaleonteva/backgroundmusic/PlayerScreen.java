package com.natashaleonteva.backgroundmusic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by natashaleonteva on 16.02.16.
 */
public class PlayerScreen implements Screen, IProgressUpdater{
    private Stage stage;
    Image control_btn;

    ProgressBar progressBar;
    ProgressBar.ProgressBarStyle pbProgressStyle;

    final int MUSIC_DURATION=149;
    private Label lbl_time;
    private Label lbl_time_past;

    @Override
    public void show() {
        System.out.println(" size " + Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), BackgroundMusic.getInstance().batch);
        control_btn=new Image(new Texture("play_button.png"));
        control_btn.setPosition(Gdx.graphics.getWidth()/2-control_btn.getWidth()/2,Gdx.graphics.getHeight()/2-control_btn.getHeight()/2);
        control_btn.addListener(new ActorGestureListener(){
            @Override
            public void tap (InputEvent event, float x, float y, int count, int button) {
                System.out.println("tap on control btn");
                if (BackgroundMusic.getInstance().musicController==null)
                    return;
                System.out.println("isPlaying=" + BackgroundMusic.getInstance().musicController.isPlaying());
                if (BackgroundMusic.getInstance().musicController.isPlaying()){
                    control_btn.setDrawable(new SpriteDrawable(new Sprite(new Texture("play_button.png"))));
                    BackgroundMusic.getInstance().musicController.pause();
                    return;
                }
                if (!BackgroundMusic.getInstance().musicController.isPlaying()){
                    control_btn.setDrawable(new SpriteDrawable(new Sprite(new Texture("stop_button.png"))));
                    BackgroundMusic.getInstance().musicController.play();
                }
            }
        });
        stage.addActor(control_btn);
        setProgressBar();
        addLblTimes();
        Gdx.input.setInputProcessor(stage);
    }

    private void setProgressBar(){
        //изображения для прогресс баров
        Skin pbSkin = new Skin();
        Pixmap pixmap = new Pixmap(Gdx.files.internal("pb_background.png"));
        pbSkin.add("background", new Texture(pixmap));
        Pixmap pixmap2 = new Pixmap(Gdx.files.internal("pb_tap_progress.png"));
        pbSkin.add("progress", new Texture(pixmap2));
        pbProgressStyle= new ProgressBar.ProgressBarStyle();
        pbProgressStyle.background=pbSkin.getDrawable("progress");
        pbProgressStyle.knobBefore=pbSkin.getDrawable("background");

        progressBar =new ProgressBar(0,MUSIC_DURATION,0.01f,false, pbProgressStyle);
        progressBar.setAnimateDuration(0.1f);
        progressBar.setSize(Gdx.graphics.getWidth(), 40);
        progressBar.setPosition(0, control_btn.getY() - progressBar.getHeight()*2);
        progressBar.setValue(0);
        stage.addActor(progressBar);

        progressBar.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                System.out.println("tap progress bar");
                if (BackgroundMusic.getInstance().musicController == null)
                    return;
                int progress = (int) (progressBar.getMaxValue() / (Gdx.graphics.getWidth() / x));
                if (!BackgroundMusic.getInstance().musicController.isPlaying()) {
                    control_btn.setDrawable(new SpriteDrawable(new Sprite(new Texture("stop_button.png"))));
                }
                BackgroundMusic.getInstance().musicController.scrollTo(progress);
            }
        });
    }

    private void addLblTimes(){
        lbl_time=new Label(getFormatedTime(MUSIC_DURATION), new Label.LabelStyle(FontHandler.getFont(), Color.WHITE));
        lbl_time.setPosition(Gdx.graphics.getWidth() - lbl_time.getWidth() * 3 / 2, progressBar.getY() - progressBar.getHeight() * 2);
        stage.addActor(lbl_time);
        lbl_time_past=new Label("00:00", new Label.LabelStyle(FontHandler.getFont(), Color.WHITE));
        lbl_time_past.setPosition(lbl_time_past.getWidth() / 2, progressBar.getY() - progressBar.getHeight() * 2);
        stage.addActor(lbl_time_past);
    }

    @Override
    public void updateProgress(int progress){
        System.out.println("PlayerScreen updateProgress:"+progress);
        progressBar.setValue(progress);
        lbl_time_past.setText(getFormatedTime(progress));
    }

    public static String getFormatedTime(float seconds){
        SimpleDateFormat sdf=new SimpleDateFormat("mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        return sdf .format(seconds*1000);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.28f, 0.63f, 0.84f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.setDebugAll(true);
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
