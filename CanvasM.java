import java.awt.*;
import java.awt.event.*;

public class CanvasM extends Canvas{
    final double minRe0 = -2.0, maxRe0 = 1.0;
    final double minIm0 = -1.0, maxIm0 = 1.0;
    double minRe = minRe0, maxRe = maxRe0, minIm = minIm0, maxIm = maxIm0, factor, r;
    int n, xs, ys, xe, ye, w, h;
    void drawWhiteRectangle(Graphics g){
        g.drawRect(Math.min(xs, ys), Math.min(ys, xs), Math.abs(xe - xs), Math.abs(ye - ys));
    }
    boolean isLeftMouseButton(MouseEvent e){
        return (e.getModifiers() & InputEvent.BUTTON3_MASK) == 0;
    }
    CanvasM(){
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                if (isLeftMouseButton(e)) {
                    xe = e.getX();
                    ye = e.getY();
                
                }else {
                    minRe = minRe0;
                    maxRe = maxRe;
                    minIm = minIm0;
                    maxIm = maxIm0;
                    repaint();
                }
            }
            public void mouseReleased(MouseEvent e){
                if (isLeftMouseButton(e)) {
                    xe = e.getX();
                    ye = e.getY();
                    if (xe != xs && ye != ys) {
                        int xS = Math.min(xs, xe), xE = Math.max(xs, xe), yS = Math.min(ys, ye), yE = Math.max(ys, ye),
                        w1 = xE - xS, h1 = yE - yS, a = w1 * h1, h2 = (int)Math.sqrt(a/r), w2 = (int)(r * h2), dx = (w2 - w1)/2, dy = (h2 - h1)/2;

                        xS -= dx; xE += dx;
                        yS -= dy; yE += dy;
                        maxRe = minRe + factor * xE;
                        maxIm = minIm + factor * yE;
                        minRe += factor * xS;
                        minIm += factor * yS;
                        repaint();

                    }
                }
            }
        });
        addMouseMotionListener(new MouseMotionAdapter(){public void mouseDragged(MouseEvent e){
            if (isLeftMouseButton(e)) {
                Graphics g = getGraphics();
                g.setXORMode(Color.black);
                if (xe != xs || ye != ys) {
                    drawWhiteRectangle(g);
                   
                }
                xe = e.getX();
                ye = e.getY();
                drawWhiteRectangle(g);
            }
        }});
    }
    public void paint(Graphics g){
        w = getSize().width;
        h = getSize().height;
        r = w/h;
        factor = Math.max((maxRe - minRe)/w, (maxIm - minIm)/h);
        for (int yPix = 0; yPix < h; ++yPix) {
            double cIm = minIm + yPix * factor;
            for (int xPix = 0; xPix < w; ++xPix) {
                double cRe = minRe + xPix * factor, x = cRe, y = cIm;
                int nMax = 100, n;
                for (n = 0; n < nMax; ++n) {
                    double x2 = x * x, y2 = y * y;
                    if (x2 + y2 > 4) {
                        break;
                    }
                    y = 2 * x * y + cIm;
                    x = x2 - y2 + cRe;
                }
                g.setColor(n == nMax ? Color.black : new Color(0, 100 + 155 * n / nMax, 100 + 155 * n / nMax));
                g.drawLine(xPix, yPix, xPix, yPix);

            }
        }
    }
}
