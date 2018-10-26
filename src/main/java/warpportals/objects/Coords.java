package warpportals.objects;

import cn.nukkit.Nukkit;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;

public class Coords {

    public double x, y, z;
    public Level level;

    public Coords(Level world, double i, double j, double k) {
        this.level = level;
        x = i;
        y = j;
        z = k;
    }

    public Coords(Block b) {
        this(b.getLevel(), b.getX(), b.getY(), b.getZ());
    }

    public Coords(Location loc) {
        this(loc.getLevel(), loc.getX(), loc.getY(), loc.getZ());
    }

    public static Coords createCourse(Location loc) {
        return new Coords(loc.getLevel(), loc.getFloorX(), loc.getFloorY(), loc.getFloorZ());
    }

    public Coords(String coordsString) throws Exception {
        String t = coordsString.trim();
        if (t.matches("\\(.+,-*[0-9]+\\.*[0-9]*,-*[0-9]+\\.*[0-9]*,-*[0-9]+\\.*[0-9]*\\)")) {
            String n = coordsString.substring(1, coordsString.length() - 1);
            String[] s = n.split(",");

//            level = Nukkit.getWorld(s[0]);
//            if (level == null)
//                throw NullWorldException.createForWorldName(s[0]);

            x = Double.parseDouble(s[1]);
            y = Double.parseDouble(s[2]);
            z = Double.parseDouble(s[3]);
        } else {
            throw new Exception("Invalid Coordinate String");
        }
    }

//    @Override
//    public int hashCode() {
//        return new HashCodeBuilder(17, 31).append(level.getName()).append(x).append(y).append(z).toHashCode();
//    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Coords))
            return false;
        else {
            Coords crd = (Coords) obj;
            if (crd.level.getName().equals(this.level.getName()) && crd.x == this.x && crd.y == this.y && crd.z == this.z)
                return true;
            return false;
        }
    }

    public String toString() {
        return "(" + level.getName() + "," + String.valueOf(x) + "," + String.valueOf(y) + "," + String.valueOf(z) + ")";
    }

    public Coords clone() {
        return new Coords(this.level, this.x, this.y, this.z);
    }
}
