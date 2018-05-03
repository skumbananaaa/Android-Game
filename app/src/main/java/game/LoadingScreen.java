package game;

import com.pinnkeyy.gametest.framework.Game;
import com.pinnkeyy.gametest.framework.Graphics;
import com.pinnkeyy.gametest.framework.Screen;

class LoadingScreen extends Screen
{
    public LoadingScreen(Game game)
    {
        super(game);
    }


    @Override
    public void update(float deltaTime)
    {
        Graphics g = game.getGraphics();
        Assets.menu = g.newImage("menu.jpg", Graphics.ImageFormat.RGB565);
        Assets.click = game.getAudio().createSound("explode.ogg");

        game.setScreen(new MainMenuScreen(game));
    }


    @Override
    public void paint(float deltaTime)
    {

    }


    @Override
    public void pause()
    {

    }


    @Override
    public void resume()
    {

    }


    @Override
    public void dispose()
    {

    }


    @Override
    public void backButton()
    {

    }
}
