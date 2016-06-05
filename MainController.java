/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

public class MainController {
    private SuperFrame superFrame;
    private FileStorage fileStorage;
    private DataStorage dataStorage;

    MainController() {
        this.superFrame = new SuperFrame(this);
        this.fileStorage = new FileStorage(this);
        this.dataStorage = new DataStorage(this);
    }

    public SuperFrame getSuperFrame() {
        return superFrame;
    }

    public FileStorage getFileStorage() {
        return fileStorage;
    }

    public DataStorage getDataStorage() {
        return dataStorage;
    }

}
