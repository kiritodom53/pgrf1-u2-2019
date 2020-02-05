package filler;

import model.Point;
import model.Polygon;
import rasterOperation.IRaster;
import renderOperation.RendererLine;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScanLineFill implements IFiller {
    // Dodělat, je to docela pokurvené
    private class Line {
        private Point a, b; //k, g

        public Line(Point a, Point b) {
            this.a = a;
            this.b = b;
        }

        public Boolean isIntersection(int y) {
            //return ((y > a.getY() && y < b.getY()) || (y < a.getY() && y > b.getY()));
            return y > a.getY() && y < b.getY();
        }

        public int getIntersection(int y) {
            //return 0;
            float dx = (float) (b.getX() - a.getX());
            float dy = (float) (b.getY() - a.getY());
            float k = dx / dy;
            float q = (float) (a.getX() - (k * a.getY()));
            // Obec. rovnice podmínky k určení průsečíku
            float x = (k * y) + q;
            return (int) x;
        }

        public boolean isHorizontal() {
            return (a.getY() == b.getY());
        }

        //public int isHorizontal
        //void setOrientation
        //void cutLine
    }

    private List<Line> lines = new ArrayList<>();
    private Polygon polygon;
    private int minY, maxY;
    private IRaster raster;
    private Color color = Color.WHITE;

    public ScanLineFill(IRaster raster) {
        this.raster = raster;
    }

    @Override
    public void fill(int ex, int ey) {
        findMax();
        findMin();
        System.out.println("Max : " + maxY + "\nMin : " + minY);
        for (Line line : lines) {
            System.out.println("lines : " + lines);
        }

        List<Integer> intersections = new ArrayList<>();

        for (int y = minY; y < maxY; y++) {

            for (Line ln : lines) {
                if (ln.isIntersection(y)) {
                    intersections.add(ln.getIntersection(y));
                }
            }


            //TODO: Uspořádat interactions podle x
            Collections.sort(intersections);


            //zkouska
            for (Integer intersection : intersections) {
                System.out.println("inte : " + intersections);
            }


            //zkouska
            RendererLine rl = new RendererLine(raster);
            for (int i = 0; i < intersections.size(); i = i + 2) {
                rl.drawLine(intersections.get(i), y, intersections.get(i + 1), y);
                for (int j = intersections.get(i); j < intersections.get(i + 1); j++) {
                    raster.drawPixel(j, y, color);
                }
            }
            intersections.clear();
        }
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

    }


    public void setBounds(Polygon polygon) {
        this.polygon = polygon;
        lines.add(new Line(polygon.getList().get(0), polygon.getList().get(polygon.getList().size() - 1)));

        for (int i = 0; i < polygon.getList().size(); i += 2) {
            lines.add(new Line(polygon.getList().get(i), polygon.getList().get(i + 1)));
        }
    }

    private void findMax() {
        int maxY = polygon.getList().get(0).getY();
        for (int i = 0; i < polygon.getList().size(); i++) {
            if (polygon.getList().get(i).getY() > maxY) {
                maxY = polygon.getList().get(i).getY();
            }
        }
        this.maxY = maxY;
        //return maxY;
    }

    private void findMin() {
        int minY = polygon.getList().get(0).getY();
        for (int i = 0; i < polygon.getList().size(); i++) {
            if (polygon.getList().get(i).getY() < minY) {
                minY = polygon.getList().get(i).getY();
            }
        }
        this.minY = minY;
        //return minY;
    }
}
