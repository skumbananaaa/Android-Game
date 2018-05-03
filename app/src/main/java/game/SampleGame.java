package game;

import com.pinnkeyy.gametest.framework.Screen;
import com.pinnkeyy.gametest.framework.implementation.AndroidGame;

public class SampleGame extends AndroidGame
{
    @Override
    public Screen getInitScreen()
    {
        return new LoadingScreen(this);
    }

    @Override
    public void onBackPressed()
    {
        getCurrentScreen().backButton();
    }
}
