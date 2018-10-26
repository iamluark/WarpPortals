package warpportals.objects;

import cn.nukkit.level.Level;
import cn.nukkit.level.Location;

public class CoordsPY {

    public double x, y, z;
    public double pitch, yaw;
    public Level level;

    public String getWorldName() {
        if (level != null)
            return level.getName();
        return null;
    }

    public CoordsPY(Level level, double i, double j, double k, double pitch, double yaw) {
        this.level = level;
        x = i;
        y = j;
        z = k;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public CoordsPY(Location loc) {
        this(loc.getLevel(), loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
    }

    public CoordsPY(Coords crd) {
        this(crd.level, crd.x, crd.y, crd.z, 0, 0);
    }

    public CoordsPY(String coordsString) throws Exception {
        String t = coordsString.trim();
        if (t.matches("\\(.+,-*[0-9]+\\.*[0-9]*,-*[0-9]+\\.*[0-9]*,-*[0-9]+\\.*[0-9]*,-*[0-9]+\\.*[0-9]*,-*[0-9]+\\.*[0-9]*\\)")) {
            String n = coordsString.substring(1, coordsString.length() - 1);
            String[] s = n.split(",");

//            world = Bukkit.getWorld(s[0]);
//            if (world == null)
//                throw NullWorldException.createForWorldName(s[0]);

            x = Double.parseDouble(s[1]);
            y = Double.parseDouble(s[2]);
            z = Double.parseDouble(s[3]);
            pitch = Float.parseFloat(s[4]);
            yaw = Float.parseFloat(s[5]);
        } else {
            throw new Exception("Invalid Coordinate String");
        }
    }

//    @Override
//    public int hashCode() {
//        return new HashCodeBuilder(17, 31).append(getWorldName()).append(x).append(y).append(z).append(pitch).append(yaw).toHashCode();
//    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof CoordsPY))
            return false;
        else {
            CoordsPY crd = (CoordsPY) obj;
            if (crd.getWorldName().equals(this.getWorldName()) && crd.x == this.x && crd.y == this.y && crd.z == this.z && crd.pitch == this.pitch
                    && crd.yaw == this.yaw)
                return true;
            return false;
        }
    }

    public String toString() {
        return "(" + getWorldName() + "," + String.valueOf(x) + "," + String.valueOf(y) + "," + String.valueOf(z) + "," + String.valueOf(pitch) + ","
                + String.valueOf(yaw) + ")";
    }

    public String toNiceString() {
        return "(" + getWorldName() + ", " + String.valueOf(Math.floor(x)) + ", " + String.valueOf(Math.floor(y)) + ", " + String.valueOf(Math.floor(z)) + ")";
    }

    public CoordsPY clone() {
        return new CoordsPY(this.level, this.x, this.y, this.z, this.pitch, this.yaw);
    }
}
