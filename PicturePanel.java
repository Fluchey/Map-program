/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PicturePanel extends JPanel {
    private SuperFrame superFrame;
    private ImageIcon picture = null;

    public PicturePanel(SuperFrame superFrame) {
        this.superFrame = superFrame;
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (picture != null) {
            g.drawImage(picture.getImage(), 0, 0, this);
        }
    }

    public void setPicture(String IMG_PATH) {
        this.picture = new ImageIcon(IMG_PATH);
        setPreferredSize(new Dimension(picture.getIconWidth(), picture.getIconHeight()));
        repaint();
    }

    public void initNewPlace(String typeOfPlace) {
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mev) {
                int xPixel = mev.getX();
                int yPixel = mev.getY();
                setCursor(Cursor.getDefaultCursor());
                superFrame.addNewPlace(typeOfPlace, xPixel, yPixel);
                removeMouseListener(this);
            }
        });
    }

    public void initShowPlacesByPosition() {
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        superFrame.getEastPanel().getHideCategoryBtn().setEnabled(false);
        superFrame.getEastPanel().getCategoryList().setEnabled(false);

        superFrame.getTopPanel().getTypeOfPlaceBox().setEnabled(false);
        superFrame.getTopPanel().getSearchField().setEnabled(false);
        superFrame.getTopPanel().getSearchBtn().setEnabled(false);
        superFrame.getTopPanel().getHideBtn().setEnabled(false);
        superFrame.getTopPanel().getRemoveBtn().setEnabled(false);
        superFrame.getTopPanel().getWhatIsHereBtn().setEnabled(false);

        superFrame.getTheMenuBar().getArchive().setEnabled(false);
        /** Avaktivera alla knappar tillfälligt */

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mev) {
                int xPixel = mev.getX();
                int yPixel = mev.getY();
                setCursor(Cursor.getDefaultCursor());
                superFrame.getMainController().getDataStorage().showPlacesByPosition(xPixel, yPixel);
                removeMouseListener(this);

                setCursor(Cursor.getDefaultCursor());

                superFrame.getEastPanel().getHideCategoryBtn().setEnabled(true);
                superFrame.getEastPanel().getCategoryList().setEnabled(true);

                superFrame.getTopPanel().getTypeOfPlaceBox().setEnabled(true);
                superFrame.getTopPanel().getSearchField().setEnabled(true);
                superFrame.getTopPanel().getSearchBtn().setEnabled(true);
                superFrame.getTopPanel().getHideBtn().setEnabled(true);
                superFrame.getTopPanel().getRemoveBtn().setEnabled(true);
                superFrame.getTopPanel().getWhatIsHereBtn().setEnabled(true);

                superFrame.getTheMenuBar().getArchive().setEnabled(true);
                /** Aktivera alla knappar igen när man klickat på kartan */
            }
        });
    }

    public ImageIcon getPicture() {
        return picture;
    }
}
