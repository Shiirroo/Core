package de.shiro.api.blocks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Objects;

public class Point3D {

    @Getter @Setter
    private double x;
    @Getter @Setter
    private double y;
    @Getter @Setter
    private double z;

    public Point3D() {
        this(0,0,0);
    }

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(Point3D otherPoint) {
        this.x = otherPoint.x;
        this.y = otherPoint.y;
        this.z = otherPoint.z;
    }

    public Point3D(Location location) {
        this( location.getX(),  location.getY(),  location.getZ());
    }

    @JsonIgnore
    public ChunkPoint getChunkPoint() {
        return new ChunkPoint((int) x & 0x000F, (int) z & 0x000F);
    }


    public boolean sameAs(Point3D otherPoint) {
        return this.x == otherPoint.x && this.y == otherPoint.y && this.z == otherPoint.z;
    }

    public static Point3D of(int x, int y, int z) {
        return new Point3D(x, y, z);
    }

    public Point3D add(Point3D otherPoint) {
        return new Point3D(this.x + otherPoint.x, this.y + otherPoint.y, this.z + otherPoint.z);
    }

    public Point3D subtract(Point3D otherPoint) {
        return new Point3D(this.x - otherPoint.x, this.y - otherPoint.y, this.z - otherPoint.z);
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

    public int compareTo(Point3D otherPoint) {
        if(this.x < otherPoint.x) return -1;
        if(this.x > otherPoint.x) return 1;
        if(this.y < otherPoint.y) return -1;
        if(this.y > otherPoint.y) return 1;
        return Double.compare(this.z, otherPoint.z);
    }

    public double distance(Point3D otherPoint) {
        return Math.sqrt(Math.pow(this.x - otherPoint.x, 2) + Math.pow(this.y - otherPoint.y, 2) + Math.pow(this.z - otherPoint.z, 2));
    }

    public double distanceSquared(Point3D otherPoint) {
        return Math.pow(this.x - otherPoint.x, 2) + Math.pow(this.y - otherPoint.y, 2) + Math.pow(this.z - otherPoint.z, 2);
    }

    public Point3 multiply(double scale) {
        return new Point3((int) (this.x * scale), (int) (this.y * scale), (int) (this.z * scale));
    }

    public Point3D min(Point3D otherPoint) {
        return new Point3D(Math.min(this.x, otherPoint.x), Math.min(this.y, otherPoint.y), Math.min(this.z, otherPoint.z));
    }

    public Point3D max(Point3D otherPoint) {
        return new Point3D(Math.max(this.x, otherPoint.x), Math.max(this.y, otherPoint.y), Math.max(this.z, otherPoint.z));
    }

    public String toChatString() {
        return x + "," + y + "," + z;
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }




}
