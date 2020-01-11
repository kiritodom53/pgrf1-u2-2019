package rasterOperation;

import java.awt.*;

/**
 * Created by Dominik Mandinec on 22/10/2019
 */

public interface IRaster {
    void drawPixel(int x, int y);
    void drawPixel(int x, int y, Color color);
    int getWidth();
    int getHeight();
    int getPixel(int x, int y);
    void seRaster(IRaster raster);
}
