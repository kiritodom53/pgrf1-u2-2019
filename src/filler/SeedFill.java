package filler;

import model.Point;
import rasterOperation.IRaster;

import java.awt.*;

public class SeedFill implements IFiller {

    private Color color = Color.WHITE;
    private IRaster raster;
    private Point seed;

    public SeedFill(IRaster raster) {
        this.raster = raster;
        setSeed(new Point(raster.getWidth() / 2, raster.getHeight() / 2));
    }

    @Override
    public void fill(int x, int y) {
        seedFill(seed.getX(), seed.getY(), raster.getPixel(x, y));
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setRaster(IRaster raster) {
        this.raster = raster;
    }


    @Override
    public void setSeed(Point seed) {
        this.seed = seed;
    }

    private void seedFill(int x, int y, int bgColor) {
        if (raster.getPixel(x, y) == bgColor) {
            if (x > 0 && y > 0 && x < raster.getWidth() && y < raster.getHeight()) { // sirku a vysku ulozit do proměné aby se pořád nevolala funkce
                raster.drawPixel(x, y, color);

                int uy = 0, dy = raster.getHeight() - 1;

                for (int i = y - 1; i >= 0; i--) {
                    if (raster.getPixel(x, i) != bgColor) {
                        uy = i + 1;
                        break;
                    }
                }

                for (int i = y + 1; i <= raster.getHeight() - 1; i++) {
                    if (bgColor != raster.getPixel(x, i)) {
                        dy = i - 1;
                        break;
                    }
                }
                for (int i = uy; i < dy; i++) {
                    raster.drawPixel(x, i, color);
                }
                //rl.drawLine(lx, y, px, y);

                for (int i = uy; i < dy; i++) {
                    if (bgColor == raster.getPixel(x + 1, i)) {
                        seedFill(x + 1, i, bgColor);
                    }
                    if (bgColor == raster.getPixel(x - 1, i)) {
                        seedFill(x - 1, i, bgColor);
                    }
                }

                /*seedFill(x-1, y, bgColor);
                seedFill(x+1, y, bgColor);
                seedFill(x, y+1, bgColor);
                seedFill(x, y-1, bgColor);*/
            }
        }
    }

}
