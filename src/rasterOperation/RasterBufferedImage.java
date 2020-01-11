package rasterOperation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Dominik Mandinec on 22/10/2019
 */

public class RasterBufferedImage implements IRaster {

    private BufferedImage bufferedImage;

    public RasterBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void drawPixel(int x, int y) {
        if (x > 0 && x < getWidth() && y > 0 && y < getHeight()) {
            bufferedImage.setRGB(x, y, 0xffff00);
        }
    }

    @Override
    public void drawPixel(int x, int y, Color color) {
        if (x > 0 && x < getWidth() && y > 0 && y < getHeight()) {
            bufferedImage.setRGB(x, y, color.getRGB());
        }
    }

    @Override
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    @Override
    public int getPixel(int x, int y) {
        //return 0;
        if (x > 0 && x < getWidth() && y > 0 && y < getHeight())
            return bufferedImage.getRGB(x, y);
        return 0;

    }

    @Override
    public void seRaster(IRaster raster) {

    }
}
