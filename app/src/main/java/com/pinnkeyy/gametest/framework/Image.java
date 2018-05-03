package com.pinnkeyy.gametest.framework;

import com.pinnkeyy.gametest.framework.Graphics.ImageFormat;

public interface Image
{
    public int getWidth();
    public int getHeight();
    public ImageFormat getFormat();

    public void dispose();
}
