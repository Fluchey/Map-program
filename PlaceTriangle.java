/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaceTriangle extends JComponent {
    private Color color;
    private Place place;
    private boolean folded = true;
    private boolean marked = false;

    public PlaceTriangle(Place place) {
        this.place = place;
        try {
            this.color = place.getCategory().getColor();
        } catch (NullPointerException e) {
            this.color = color.BLACK;
        }
        setBounds(place.getPosition().getxPixel() - 20, place.getPosition().getyPixel() - 40, 40, 40);
        addMouseListener(new mouseListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[] xEs = {0, 20, 40};
        int[] yEs = {0, 40, 0};
        if (folded) {
            setBounds(place.getPosition().getxPixel() - 20, place.getPosition().getyPixel() - 40, 40, 40);
            g.setColor(color);
            g.fillPolygon(xEs, yEs, 3);
            if (marked) {
                g.setColor(Color.MAGENTA);
                g.fillPolygon(new int[]{0, 0, 15}, new int[]{5, 40, 40}, 3);
                g.setColor(Color.MAGENTA);
                g.fillPolygon(new int[]{40, 40, 25}, new int[]{5, 40, 40}, 3);
            }
        } else if (!folded) {
            if (place instanceof NamedPlace) {
                g.drawString(place.getPlaceName(), 0, 25);
                if (marked) {
                    g.setColor(Color.MAGENTA);
                    g.fillPolygon(new int[]{20, 40, 40}, new int[]{40, 40, 20}, 3);
                }
            } else if (place instanceof DescribedPlace) {
                setBounds(place.getPosition().getxPixel(), place.getPosition().getyPixel(), 150, 50);
                g.setColor(Color.white);
                g.fillRect(0, 0, 150, 50);
                g.setColor(Color.BLACK);
                g.drawString(place.getPlaceName(), 0, 10);
                g.drawString(((DescribedPlace) place).getDescription(), 0, 25);
                if (marked) {
                    g.setColor(Color.MAGENTA);
                    g.fillPolygon(new int[]{130, 150, 150}, new int[]{50, 50, 30}, 3);
                }
            }
        }
    }

    public void setMarked(boolean b) {
        marked = b;
        repaint();
    }

    public boolean getMarked() {
        return marked;
    }

    public void setVisibility(Boolean b) {
        setVisible(b);
        repaint();
    }

    public void setFolded(boolean folded) {
        this.folded = folded;
        repaint();
    }

    public Place getPlace() {
        return place;
    }

    private class mouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent mev) {
            if (SwingUtilities.isLeftMouseButton(mev)) {
                marked = !marked;
                place.getMainController().getDataStorage().updateMarkedPlaces(place.getPlaceTriangle());
            } else if (SwingUtilities.isRightMouseButton(mev)) {
                folded = !folded;
            }
            repaint();
        }
    }
}
