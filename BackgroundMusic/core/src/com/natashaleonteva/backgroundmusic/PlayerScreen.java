package com.natashaleonteva.backgroundmusic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by natashaleonteva on 16.02.16.
 */
public class PlayerScreen implements Screen{
    private Stage stage;
    Image control_btn;

    @Override
    public void show() {
        System.out.println(" size " + Gdx.graphics.getWidth() + " " + Gdx.graphics.getHeight());
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), BackgroundMusic.getInstance().batch);
        control_btn=new Image(new Texture("play_button.png"));
        control_btn.setPosition(Gdx.graphics.getWidth()/2-control_btn.getWidth()/2,Gdx.graphics.getHeight()/2-control_btn.getHeight()/2);
        control_btn.addListener(new ActorGestureListener(){
            @Override
            public void tap (InputEvent event, float x, float y, int count, int button) {
                System.out.println("tap pn control btn");
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
        Gdx.input.setInputProcessor(stage);
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
