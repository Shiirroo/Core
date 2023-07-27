package de.shiro.api.blocks;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.checkerframework.checker.units.qual.min;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ChunkArea implements Serializable {

    @Getter @Setter @Expose
    private ChunkPoint min;
    @Getter @Setter @Expose
    private ChunkPoint max;

    public ChunkArea() {
        this(0,0,0,0);
    }

    public ChunkArea(ChunkArea area) {
        this(area.getMin(), area.getMax());
    }

    public ChunkArea(int minX, int minZ, int maxX, int maxZ) {
        this.min = new ChunkPoint(minX,minZ);
        this.max = new ChunkPoint(maxX,maxZ);
    }

    public ChunkArea(ChunkPoint from, ChunkPoint to) {
       this.min = from;
       this.max = to;
    }


    public ChunkArea(Location min, Location max) {
        this.min = new ChunkPoint(min.getChunk().getX(), min.getChunk().getZ());
        this.max = new ChunkPoint(max.getChunk().getX(),  max.getChunk().getZ());
    }


    public boolean contains(Point3 point) {
        return point.getChunkPoint().getX() >= min.getX() && point.getChunkPoint().getX() <= max.getX() && point.getChunkPoint().getZ() >= min.getZ() && point.getChunkPoint().getZ() <= max.getZ();
    }

    public boolean contains(ChunkPoint point) {
        return point.getX() >= min.getX() && point.getX() <= max.getX() && point.getZ() >= min.getZ() && point.getZ() <= max.getZ();
    }

    public boolean contains(ChunkArea area) {
        return contains(area.getMin()) && contains(area.getMax());
    }

    public boolean intersects(ChunkArea area) {
        return contains(area.getMin()) || contains(area.getMax()) || area.contains(this.getMin()) || area.contains(this.getMax());
    }

    public ChunkArea getIntersection(ChunkArea area) {
        if (!intersects(area)) {
            return null;
        }
        return new ChunkArea(
                Math.max(this.getMin().getX(), area.getMin().getX()),
                Math.max(this.getMin().getZ(), area.getMin().getZ()),
                Math.min(this.getMax().getX(), area.getMax().getX()),
                Math.min(this.getMax().getZ(), area.getMax().getZ())
        );
    }





    public ChunkPoint getCenter() {
        return new ChunkPoint((min.getX() + max.getX()) / 2,  (min.getZ() + max.getZ()) / 2);
    }


    public double getWidth() {
        return this.getMax().getX() - this.getMin().getX();
    }

    public double getDepth() {
        return this.getMax().getZ() - this.getMin().getZ();
    }


    public boolean isEmpty() {
        return getWidth() <= 0 || getDepth() <= 0;
    }

    public List<ChunkPoint> getChunkPoints() {
        List<ChunkPoint> chunkPoints = new ArrayList<>();
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int z = min.getZ(); z <= max.getZ() ; z++) {
                ChunkPoint chunkPoint = new ChunkPoint(x, z);
                if(x == min.getX() || x == max.getX() - 1 || z == min.getZ() || z == max.getZ() -1 ){
                    chunkPoint.setBorder();
                }
                chunkPoints.add(chunkPoint);
            }
        }
        return chunkPoints;
    }




    public Point2 getPoint1() {
        return new Point2(min.getX(), min.getZ());
    }

    public Point2 getPoint2() {
        return new Point2(max.getX(), min.getZ());
    }

    public Point2 getPoint3() {
        return new Point2(max.getX(), max.getZ());
    }

    public Point2 getPoint4() {
        return new Point2(min.getX(), max.getZ());
    }

    public Point3 getPoint1(int y) {
        return getPoint1().toPoint3(y);
    }

    public Point3 getPoint2(int y) {
        return getPoint2().toPoint3(y);
    }

    public Point3 getPoint3(int y) {
        return getPoint3().toPoint3(y);
    }

    public Point3 getPoint4(int y) {
        return getPoint4().toPoint3(y);
    }






    public String toString() {
        return "ChunkArea{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }










}
