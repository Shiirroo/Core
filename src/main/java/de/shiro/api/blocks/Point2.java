package de.shiro.api.blocks;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.Serializable;

public class Point2  implements Serializable {

    @Getter @Expose
    private int x;
    @Getter @Expose
    private int z;


    public Point2() {
        this(0,0);
    }

    public Point2(int x, int z) {
        this.x = x;
        this.z = z;
    }


    public void add(int x, int z) {
        this.x += x;
        this.z += z;
    }

    public void subtract(int x, int z) {
        this.x -= x;
        this.z -= z;
    }

    public void add(Point2 otherPoint) {
        this.x += otherPoint.x;
        this.z += otherPoint.z;
    }

    public void subtract(Point2 otherPoint) {
        this.x -= otherPoint.x;
        this.z -= otherPoint.z;
    }

    public boolean sameAs(Point2 otherPoint) {
        return this.x == otherPoint.x && this.z == otherPoint.z;
    }

    public void multiply(double factor) {
        this.x *= factor;
        this.z *= factor;
    }

    public void divide(double factor) {
        this.x /= factor;
        this.z /= factor;
    }

    public void multiplyX(double factor) {
        this.x *= factor;
    }

    public void multiplyZ(double factor) {
        this.x *= factor;
    }

    public void divideX(double factor) {
        this.x /= factor;
    }

    public void divideZ(double factor) {
        this.z /= factor;
    }

    public static Point2 of(int x, int z) {
        return new Point2(x, z);
    }

    public String toString() {
        return "(x:" + x + ",z:" + z + ")";
    }

    public Point3 toPoint3(int y) {
        return Point3.of(x, y, z);
    }


    public Location toLocation(World world, int y) {
        return new Location(world, x, y, z);
    }







}
