import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Chapter32B {
    public static void main(String args[]) {
        GfxApp gfx = new GfxApp();
        gfx.setSize(1024, 768);
        gfx.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        gfx.show();
    }
}

class GfxApp extends Frame {
    public void paint(Graphics g) {
        try {
            drawSquare1(g, 1024, 768);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void drawSquare1(Graphics g, int maxX, int maxY) throws InterruptedException {
        int width = (int) (.125 * maxX);
        int height = (int) (.125 * maxY);
        g.fillRect((int)(.375 * maxX),(int)(.375 * maxY),(int)(.25 * maxX),(int)(.25 * maxY));
        ArrayList<Rectangle> holdAllRects = new ArrayList<>();
        holdAllRects.add(new Rectangle((int)(.375 * maxX),(int)(.375 * maxY),(int)(.25 * maxX),(int)(.25 * maxY)));
        //Thread.sleep(1000);
        drawNewSquares(g, (int) (.375 * maxX) - (int) (.125 * maxX), (int) (.375 * maxY) - (int) (.125 * maxY), width, height, holdAllRects);
        drawNewSquares(g, (int) (.625 * maxX), (int) (.375 * maxY) - height, width, height, holdAllRects);
        drawNewSquares(g, (int) (.375 * maxX) - (int) (.125 * maxX), (int) (.625 * maxY), width, height, holdAllRects);
        drawNewSquares(g, (int) (.625 * maxX), (int) (.625 * maxY), width, height, holdAllRects);
    }

    public void drawFirstRectangleProperly(Graphics g, int maxX, int maxY) {
        g.fillRect((int) (.375 * maxX), (int) (.375 * maxY), (int) (.25 * maxX), (int) (.25 * maxY));
    }

    public void drawNewSquares(Graphics g, int xPos, int yPos, int width, int height, ArrayList<Rectangle> rect) throws InterruptedException {
        /*int maxX = g.getClipBounds().width;
        int maxY = g.getClipBounds().height;
        if(rect.size() == 0) {
            System.out.println("size:" + rect.size());
            int tempX = (int)(.375 * maxX);
            int tempY = (int)(.375 * maxY);
            int tempWidth = (int)(.25 * maxX);
            int tempHeight = (int)(.25 * maxY);
            System.out.println("pre draw");
            g.fillRect(tempX,tempY,tempWidth,tempHeight);
            rect.add(new Rectangle((int)(.375 * maxX),(int)(.375 * maxY),(int)(.25 * maxX),(int)(.25 * maxY)));
            delay(1000);
        }*/
        boolean overlap = false;
        Rectangle currentRectangle = new Rectangle(xPos, yPos, width, height);
        for (int x = 0; x < rect.size(); x++) {
            if (rect.get(x).intersects(currentRectangle)) {
                overlap = true;
            }
        }
        if (width >= 1 && height >= 1 && !overlap) {
            delay(200);
            rect.add(currentRectangle);
            g.fillRect(xPos, yPos, width, height);
            drawNewSquares(g, (int) (xPos - (width / 2)), (int) (yPos - (height / 2)), width / 2, height / 2, rect);
            drawNewSquares(g, (int) (xPos - (width / 2)), (int) (yPos + height), width / 2, height / 2, rect);
            drawNewSquares(g, (int) (xPos + width), (int) (yPos + height), width / 2, height / 2, rect);
            drawNewSquares(g, (int) (xPos + width), (int) (yPos - (height / 2)), width / 2, height / 2, rect);
        }
    }

    private void delay(int n) throws InterruptedException {
        Thread.sleep(n);
    }
}