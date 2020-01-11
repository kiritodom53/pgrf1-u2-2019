import filler.IFiller;
import filler.SeedFill;
import filler.SeedFillPatern;
import model.*;
import model.Point;
import model.Polygon;
import rasterOperation.IRaster;
import rasterOperation.RasterBufferedImage;
import renderOperation.RendererLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi
 *
 * @author PGRF FIM UHK
 * @version 2017
 */
public class CanvasMouse {

    private JPanel panel;
    private BufferedImage img;

    private int regPol = 0;
    private Boolean regTrue = false;

    private RendererLine rl;
    private IGeoObject line = new Line();

    private IRaster raster;

    private int rPolIn = 0;

    private Boolean polygonFirst = true;
    private IGeoObject pl;
    private int lPolIn = 0;
    private List<IGeoObject> lPol = new ArrayList<>();
    private List<IGeoObject> rPol = new ArrayList<>();

    private int count = 3;
    private double alpha = 0.0000001;
    private int progStart = 0;

    private IGeoObject rpl;

    private int x, y;
    private float r;

    //pokusy
    private SeedFill sf;
    private SeedFillPatern sfp;

    private List<IFiller> lFil = new ArrayList<>();
    private List<IFiller> lFilPat = new ArrayList<>();
    private List<Point> lFilPoint = new ArrayList<>();
    private List<Point> lFilPointPat = new ArrayList<>();


    //konec pokusy


    public CanvasMouse(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("Dominik Mandinec - UKOL 01 - UHK FIM 2019");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        panel.setFocusable(true);
        panel.requestFocusInWindow();

        raster = new RasterBufferedImage(img);
        rl = new RendererLine(raster);

        draw();
    }

    private void reset() {
        line.getList().clear();
        lPol.clear();
        rPol.clear();
        lFil.clear();
        lFilPat.clear();
        lFilPoint.clear();
        lFilPointPat.clear();
        rPolIn = 0;
        lPolIn = 0;
        clear();
        panel.repaint();
    }

    private void drawPoint(int x, int y) {
        lPol.get(lPolIn).addPoint(x, y);
        clear();
        lPol.get(lPolIn).draw(rl);
        panel.repaint();
    }


    public void draw() {


        /*pl = new Polygon();
        //Polygon pl2 = new Polygon();
        pl.addPoint(100, 100);
        pl.addPoint(100, 200);
        pl.addPoint(200, 200);
        pl.addPoint(200, 100);

        lPol.add(pl);*/
        //pl.draw(rl);


        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_Q) {
                    //progStart = 0;
                    reset();
                    System.out.println(progStart);
                } else if (keyCode == KeyEvent.VK_W) {
                    progStart = 1;
                    System.out.println(progStart);
                } else if (keyCode == KeyEvent.VK_E) {
                    progStart = 2;
                    System.out.println(progStart);
                } else if (keyCode == KeyEvent.VK_R) {
                    progStart = 3;
                    System.out.println(progStart);
                } else if (keyCode == KeyEvent.VK_M) {
                    progStart = 4;
                    System.out.println(progStart);
                } else if (keyCode == KeyEvent.VK_N) {
                    progStart = 5;
                    System.out.println(progStart);
                }
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                clear();
                int xe = e.getX();
                int ye = e.getY();
                if (progStart == 4){
                    lFilPoint.add(new Point(xe, ye));
                    sf = new SeedFill(raster);
                    sf.setColor(Color.BLUE);
                    sf.setSeed(new model.Point(xe, ye));
                    sf.fill(xe, ye);
                    lFil.add(sf);

                }else if (progStart == 5){
                    lFilPoint.add(new Point(xe, ye));
                    sfp = new SeedFillPatern(raster);
                    //lFilPat.add(sfp);
                    sfp.setColor(Color.GREEN);
                    sfp.setSeed(new model.Point(xe, ye));
                    sfp.fill(xe, ye);
                    lFil.add(sfp);
                }
                panel.repaint();
            }
        });


        /*panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent k) {
                int keyCode = k.getKeyCode();
                if (keyCode == KeyEvent.VK_CONTROL){
                    clear();
                    System.out.println(keyCode);
                    int xe = MouseInfo.getPointerInfo().getLocation().x;
                    int ye = MouseInfo.getPointerInfo().getLocation().y;
                    lFilPoint.add(new Point(xe, ye));
                    System.out.println("xe : " + xe + "\nye : " + ye);
                    sf = new SeedFill(raster);
                    lFil.add(sf);
                    lFil.get(lFil.size()-1).setColor(Color.GREEN);
                    System.out.println("f1");
                    lFil.get(lFil.size()-1).setSeed(new model.Point(xe, ye));
                    System.out.println("f2");
                    lFil.get(lFil.size()-1).fill(xe, ye);
                    System.out.println("f3");
                    panel.repaint();
                }
            }
        });*/

        panel.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                RegularPolygon rpTemp;
                if (regPol == 3 || regPol == 1) {
                    int notches = e.getWheelRotation();
                    System.out.println();

                    if (notches < 0) {
                        System.out.println(notches);
                        count += 1;
                    } else if (notches > 0 && count > 3) {
                        System.out.println(notches);
                        count -= 1;
                    }
                } else if (regPol == 2) {
                    System.out.println("Jede kolečko regPol 2");
                    int notches = e.getWheelRotation();
                    System.out.println();

                    if (notches < 0) {
                        alpha += 0.05;
                    } else if (notches > 0) {
                        alpha -= 0.05;
                    }
                }

                clear();
                rpTemp = new RegularPolygon(new Point(x, y), r, alpha, count);
                rpTemp.draw(rl);
                panel.repaint();
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (progStart == 3) {

                    if (regPol == 0 && e.getButton() == MouseEvent.BUTTON1) {
                        count = 3; // nastavý výchozí hodnotu na 3
                        // Kliknutí pro uložení středu
                        x = e.getX();
                        y = e.getY();
                        regPol = 1;
                        regTrue = true;
                        System.out.println("konec reg pol 0");
                    } else if (regPol == 1 && e.getButton() == MouseEvent.BUTTON1) {
                        // Kliknutí pro uložení délky
                        r = (float) Math.sqrt((e.getY() - y) * (e.getY() - y) + (e.getX() - x) * (e.getX() - x));
                        regPol = 2;
                        System.out.println("konec reg pol 1");
                    } else if (regPol == 2 && e.getButton() == MouseEvent.BUTTON1) {
                        regPol = 3;
                    } else if (regPol == 3 && e.getButton() == MouseEvent.BUTTON1) {
                        clear();
                        rpl = new RegularPolygon(new Point(x, y), r, alpha, count);
                        rPol.add(rpl);
                        regTrue = false;
                        rPol.get(rPolIn).draw(rl);
                        panel.repaint();
                        System.out.println("konec reg pol 2");
                        rPolIn += 1;
                        regPol = 0;
                        alpha = 0.0000001;
                    }
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (progStart == 3) {
                    clear();
                    if (regTrue && regPol == 1) {
                        clear();
                        RegularPolygon rp = new RegularPolygon(new Point(x, y), (float) Math.sqrt((e.getY() - y) * (e.getY() - y) + (e.getX() - x) * (e.getX() - x)), Math.PI / alpha, count);
                        rp.draw(rl);
                        panel.repaint();
                    }
                }
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (progStart == 1) {
                    // Line
                    drawLine(e.getX(), e.getY());
                    panel.repaint();
                } else if (progStart == 2) {
                    if (polygonFirst && e.getButton() == MouseEvent.BUTTON1) {
                        pl = new Polygon();
                        lPol.add(pl);
                        drawPoint(e.getX(), e.getY());
                        polygonFirst = false;
                    }

                    if (e.getButton() == MouseEvent.BUTTON3) {
                        if (!polygonFirst) {
                            polygonFirst = true;
                            lPolIn += 1;
                        }
                    }
                }
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (progStart == 2) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        drawPoint(e.getX(), e.getY());
                    }
                }
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent g) {
                if (progStart == 1) {
                    clear();
                    line.draw(rl);
                    line.currentDraw(rl, g.getX(), g.getY());
                    panel.repaint();
                } else if (progStart == 2) {
                    System.out.println("draged buton : " + g.getButton());
                    clear();
                    pl.draw(rl);
                    pl.currentDraw(rl, g.getX(), g.getY());
                    panel.repaint();
                }
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (progStart == 1) {
                    drawLine(e.getX(), e.getY());
                    panel.repaint();
                }
            }
        });
    }

    public void drawLine(int x, int y) {
        line.addPoint(x, y);
        clear();
        line.draw(rl);
        panel.repaint();
        //}
    }

    /***
     * Vykreslení objektů co byly vykresleny
     */
    public void drawSave() {
        line.draw(rl);

        for (int i = 0; i < lPol.size(); i++) {
            for (int j = 0; j < lPol.get(i).getList().size(); j++) {
                if (lPol.get(i).getList().size() > 2) {
                    lPol.get(i).draw(rl);
                }
            }
        }

        for (int i = 0; i < rPol.size(); i++) {
            for (int j = 0; j < rPol.get(i).getList().size(); j++) {
                if (rPol.get(i).getList().size() > 2) {
                    rPol.get(i).draw(rl);
                }
            }
        }

        for (int i = 0; i < lFil.size(); i++) {
            lFil.get(i).fill(lFilPoint.get(i).getX(), lFilPoint.get(i).getY());
        }

        for (int i = 0; i < lFilPat.size(); i++) {
            lFilPat.get(i).fill(lFilPointPat.get(i).getX(), lFilPointPat.get(i).getY());
        }

    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());

        drawSave();
    }

    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void start() {
        clear();
        img.getGraphics().drawString("Use mouse buttons", 5, img.getHeight() - 5);
        panel.repaint();
    }

}