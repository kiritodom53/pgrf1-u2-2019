package filler;

import model.Point;
import rasterOperation.IRaster;

import java.awt.*;

/**
 * Created by Dominik Mandinec on 17/11/2019
 */

public class SeedFillPatern implements IFiller {

    private Color color = Color.WHITE;
    private Color color1 = new Color(0x009fdf);
    private Color color2 = Color.BLACK;
    private IRaster raster;
    private Point seed;

    public SeedFillPatern(IRaster raster) {
        this.raster = raster;
        setSeed(new Point(raster.getWidth() / 2, raster.getHeight() / 2));
    }

    int[][] pattern = {
            {1, 1, 1, 1, 1, 1, 1, 0, 0},
            {1, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 1, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private void seedFill(int x, int y, int bgColor) {
        if (raster.getPixel(x, y) == bgColor) {
            if (x > 0 && y > 0 && x < raster.getWidth() && y < raster.getHeight()) {
                if (pattern[x / 2 % 17][y / 2 % 8] == 1) {
                    raster.drawPixel(x, y, color1);
                    setColor(color1);
                } else {
                    raster.drawPixel(x, y, color2);
                    setColor(color2);
                }
                raster.drawPixel(x, y, color);
                seedFill(x - 1, y, bgColor);
                seedFill(x + 1, y, bgColor);
                seedFill(x, y + 1, bgColor);
                seedFill(x, y - 1, bgColor);
            }
        }
    }

    @Override
    public void fill(int x, int y) {
        seedFill(seed.getX(), seed.getY(), raster.getPixel(x, y));
    }

    public void setSeed(Point seed) {
        this.seed = seed;
    }


    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setRaster(IRaster raster) {
        this.raster = raster;
    }
}
