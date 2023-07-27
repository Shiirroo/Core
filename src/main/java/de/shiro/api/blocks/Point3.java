package de.shiro.api.blocks;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.Serializable;
import java.util.Objects;

public class Point3  implements Serializable {

    public static final Point3 ZERO = new Point3(0, 0, 0);
    public static final Point3 MAX = new Point3(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    public static final Point3 MIN = new Point3(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

    @Getter @Setter @Expose
    private int x;
    @Getter @Setter @Expose
    private int y;
    @Getter @Setter @Expose
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
    public ChunkPoint getChunkPoint() {
        return new ChunkPoint(x & 0x000F, z & 0x000F);
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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Point3 point3 = (Point3) object;
        return x == point3.x && y == point3.y && z == point3.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
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

    public Point3 getHeightsBlock(World world){
        return new Point3(world.getHighestBlockAt(getX(), getZ()).getLocation());
    }

    public Point3D toPoint3D(){
        return new Point3D(this);
    }

    @Override
    public String toString() {
        return "Point3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public String toChatString() {
        return x + "," + y + "," + z;
    }


    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }



}
