package de.shiro.api.blocks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Objects;

public class Point3 {

    public static final Point3 ZERO = new Point3(0, 0, 0);
    public static final Point3 MAX = new Point3(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    public static final Point3 MIN = new Point3(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

    @Getter @Setter
    private int x;
    @Getter @Setter
    private int y;
    @Getter @Setter
    private int z;

    public Point3() {
        this(0,0,0);
    }

    public Point3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3(Location location) {
        this((int) location.getX(), (int) location.getY(), (int) location.getZ());
    }

    public Point3(Point3 point3) {
        this( point3.getX(), point3.getY(), point3.getZ());
    }
    @JsonIgnore
    public ChunkPoint getChunkPoint() {
        return new ChunkPoint((int) x & 0x000F, (int) z & 0x000F);
    }


    public boolean sameAs(Point3 otherPoint) {
        return this.x == otherPoint.x && this.y == otherPoint.y && this.z == otherPoint.z;
    }

    public static Point3 of(int x, int y, int z) {
        return new Point3(x, y, z);
    }

    public Point3 add(Point3 otherPoint) {
        return new Point3(this.x + otherPoint.x, this.y + otherPoint.y, this.z + otherPoint.z);
    }

    public Point3 subtract(Point3 otherPoint) {
        return new Point3(this.x - otherPoint.x, this.y - otherPoint.y, this.z - otherPoint.z);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3 point3)) return false;
        return Double.compare(point3.getX(), getX()) == 0 && Double.compare(point3.getY(), getY()) == 0 && Double.compare(point3.getZ(), getZ()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getZ());
    }

    public int compareTo(Point3 otherPoint) {
        if(this.x < otherPoint.x) return -1;
        if(this.x > otherPoint.x) return 1;
        if(this.y < otherPoint.y) return -1;
        if(this.y > otherPoint.y) return 1;
        return Double.compare(this.z, otherPoint.z);
    }

    public double distance(Point3 otherPoint) {
        return Math.sqrt(Math.pow(this.x - otherPoint.x, 2) + Math.pow(this.y - otherPoint.y, 2) + Math.pow(this.z - otherPoint.z, 2));
    }

    public double distanceSquared(Point3 otherPoint) {
        return Math.pow(this.x - otherPoint.x, 2) + Math.pow(this.y - otherPoint.y, 2) + Math.pow(this.z - otherPoint.z, 2);
    }

    public Point3 multiply(double scale) {
        return new Point3((int) (this.x * scale), (int) (this.y * scale), (int) (this.z * scale));
    }

    public Point3 min(Point3 otherPoint) {
        return new Point3(Math.min(this.x, otherPoint.x), Math.min(this.y, otherPoint.y), Math.min(this.z, otherPoint.z));
    }

    public Point3 max(Point3 otherPoint) {
        return new Point3(Math.max(this.x, otherPoint.x), Math.max(this.y, otherPoint.y), Math.max(this.z, otherPoint.z));
    }

    public String toString() {
        return "(x:" + x + ",y:" + y + ",z:" + z + ")";
    }

    public String toChatString() {
        return x + "," + y + "," + z;
    }


    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }


}
