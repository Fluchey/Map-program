/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

public class Position {
    int xPixel;
    int yPixel;

    public Position(int x, int y) {
        this.xPixel = x;
        this.yPixel = y;
    }

    public int getxPixel() {
        return xPixel;
    }

    public int getyPixel() {
        return yPixel;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Position) {
            Position otherPos = (Position) other;
            return xPixel == otherPos.xPixel && yPixel == otherPos.yPixel;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (xPixel * 100) + yPixel;
    }
}
