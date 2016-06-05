/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

import javax.swing.*;

public class MenuBar extends JMenuBar {
    private SuperFrame superFrame;
    private JMenuBar menuBar;
    private JMenu archive;
    private JMenuItem newMap;
    private JMenuItem loadPlaces;
    private JMenuItem save;
    private JMenuItem exit;

    public MenuBar(SuperFrame superFrame) {
        this.superFrame = superFrame;
        menuBar = new JMenuBar();
        archive = new JMenu("Archive");
        newMap = new JMenuItem("New Map");
        loadPlaces = new JMenuItem("Load Places");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");

        archive.add(newMap);
        archive.add(loadPlaces);
        archive.add(save);
        archive.add(exit);
        menuBar.add(archive);

        loadPlaces.setEnabled(false);
        save.setEnabled(false);

        newMap.addActionListener(
                ave -> superFrame.loadNewMapPicture()
        );
        loadPlaces.addActionListener(
                ave -> superFrame.getMainController().getFileStorage().loadNewPlaces()
        );
        save.addActionListener(
                ave -> superFrame.getMainController().getFileStorage().savePlaces(superFrame.getMainController().getDataStorage().getAllPlaces())
                /** Kommunicering sker via maincontroller som finns i vår lokala superFrame
                 * Initiera metoden savePlaces i FileStorage
                 * Skicka med data från getAllPlaces som finns i DataStorage */
        );
        exit.addActionListener(
                ave -> superFrame.finish()
        );
    }

    public JMenu getArchive() {
        return archive;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public JMenuItem getSave() {
        return save;
    }

    public JMenuItem getLoadPlaces() {
        return loadPlaces;
    }
}
