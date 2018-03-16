import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

public class Board extends JComponent implements MouseInputListener, ComponentListener {
  private Point[][] points;
  private int size = 14;

  public Board(int length, int height) {
    addMouseListener(this);
    addComponentListener(this);
    addMouseMotionListener(this);
    setBackground(Color.WHITE);
    setOpaque(true);
  }

  public void iteration() {
    for (Point[] point : points) {
      for (Point aPoint : point) {
        aPoint.calculateNewState();
      }
    }

    for (Point[] point : points) {
      for (Point aPoint : point) {
        aPoint.changeState();
      }
    }
    this.repaint();
  }

  public void clear() {
    for (Point[] point : points) {
      for (Point aPoint : point) {
        aPoint.setState(0);
      }
    }
    this.repaint();
  }

  private void initialize(int length, int height) {
    points = new Point[length][height];

    for (int x = 0; x < points.length; ++x) {
      for (int y = 0; y < points[x].length; ++y) {
        points[x][y] = new Point();
      }
    }

    for (int x = 0; x < points.length; ++x) {
      for (int y = 0; y < points[x].length; ++y) {
        tryToAdd(points[x][y],x-1,y+1); tryToAdd(points[x][y],x, y+1); tryToAdd(points[x][y],x+1,y+1);
        tryToAdd(points[x][y],x-1,y); /*                            */ tryToAdd(points[x][y], x+1, y);
        tryToAdd(points[x][y],x-1,y-1); tryToAdd(points[x][y],x, y-1); tryToAdd(points[x][y], x+1, y-1);
      }
    }
  }

  private void tryToAdd(Point addTo, int x, int y){
    try{
    addTo.addNeighbor(points[x][y]);
    }catch(ArrayIndexOutOfBoundsException ignore){
    }
  }

  protected void paintComponent(Graphics g) {
    if (isOpaque()) {
      g.setColor(getBackground());
      g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
    g.setColor(Color.GRAY);
    drawNetting(g, size);
  }

  private void drawNetting(Graphics g, int gridSpace) {
    Insets insets = getInsets();
    int firstX = insets.left;
    int firstY = insets.top;
    int lastX = this.getWidth() - insets.right;
    int lastY = this.getHeight() - insets.bottom;

    int x = firstX;
    while (x < lastX) {
      g.drawLine(x, firstY, x, lastY);
      x += gridSpace;
    }

    int y = firstY;
    while (y < lastY) {
      g.drawLine(firstX, y, lastX, y);
      y += gridSpace;
    }

    for (x = 0; x < points.length; ++x) {
      for (y = 0; y < points[x].length; ++y) {
        if (points[x][y].getState() != 0) {

          switch (points[x][y].getState()) {
            case 0:
              g.setColor(new Color(0xffffff));
            case 1:
              g.setColor(new Color(0x000000));
              break;
          }
          g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
        }
      }
    }
  }

  public void mouseClicked(MouseEvent e) {
    int x = e.getX() / size;
    int y = e.getY() / size;
    if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
      points[x][y].clicked();
      this.repaint();
    }
  }

  public void componentResized(ComponentEvent e) {
    int dlugosc = (this.getWidth() / size) + 1;
    int wysokosc = (this.getHeight() / size) + 1;
    initialize(dlugosc, wysokosc);
  }

  public void mouseDragged(MouseEvent e) {
    int x = e.getX() / size;
    int y = e.getY() / size;
    if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
      points[x][y].setState(1);
      this.repaint();
    }
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void componentShown(ComponentEvent e) {
  }

  public void componentMoved(ComponentEvent e) {
  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseMoved(MouseEvent e) {
  }

  public void componentHidden(ComponentEvent e) {
  }

  public void mousePressed(MouseEvent e) {
  }

}
