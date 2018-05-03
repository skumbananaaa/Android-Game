package com.pinnkeyy.gametest.framework.implementation;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.pinnkeyy.gametest.framework.Audio;
import com.pinnkeyy.gametest.framework.FileIO;
import com.pinnkeyy.gametest.framework.Game;
import com.pinnkeyy.gametest.framework.Graphics;
import com.pinnkeyy.gametest.framework.Input;
import com.pinnkeyy.gametest.framework.Screen;

public abstract class AndroidGame extends Activity implements Game
{
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int frameBufferWidth = isPortrait ? 800 : 1280;
        int frameBufferHeight = isPortrait ? 1280 : 800;

        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565);
        float scaleX = (float)frameBufferWidth / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float)frameBufferHeight / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getInitScreen();
        setContentView(renderView);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyGame");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        wakeLock.release();
        screen.pause();
        renderView.pause();

        if (isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput()
    {
        return input;
    }

    @Override
    public FileIO getFileIO()
    {
        return fileIO;
    }

    @Override
    public Graphics getGraphics()
    {
        return graphics;
    }

    @Override
    public Audio getAudio()
    {
        return audio;
    }

    @Override
    public void setScreen(Screen newScreen)
    {
        if (screen == null)
            throw new IllegalArgumentException("Screen is null");

        screen.pause();
        screen.dispose();
        newScreen.resume();
        newScreen.update(0);
        screen = newScreen;
    }

    @Override
    public Screen getCurrentScreen()
    {
        return screen;
    }
}
