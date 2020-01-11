package filler;

import model.Point;
import rasterOperation.IRaster;

import java.awt.*;

public interface IFiller {
    void fill(int x, int y);

    void setColor(Color color);

    void setRaster(IRaster raster);

    void setSeed(Point seed);
    //seed filler?
}
