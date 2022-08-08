package de.shiro.api.blocks;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Area {

    @Getter @Setter
    private Point3 min;
    @Getter @Setter
    private Point3 max;

    public Area() {
        this(0,0,0,0,0,0);
    }

    public Area(Area area) {
        this(area.getMin(), area.getMax());
    }

    public Area(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.min = new Point3(minX, minY, minZ);
        this.max = new Point3(maxX, maxY, maxZ);
    }

    public Area(ChunkPoint from, ChunkPoint to) {
        this(Stream.of(from.setBorder(),to.setBorder()).reduce(new PointStats(), PointStats::consume, PointStats::combine).toArea());
    }

    public Area(Point3 min, Point3 max) {
        this.min = min;
        this.max = max;
    }

    public Area(Location min, Location max) {
        this.min = new Point3((int) min.getX(), (int) min.getY(), (int) min.getZ());
        this.max = new Point3((int) max.getX(), (int) max.getY(), (int) max.getZ());
    }

    public Area(List<ChunkPoint> chunkPoints) {
        this(chunkPoints.stream().reduce(new PointStats(), PointStats::consume, PointStats::combine).toArea());
    }





    public boolean contains(Point3 point) {
        return point.getX() >= min.getX() && point.getX() <= max.getX() && point.getY() >= min.getY() && point.getY() <= max.getY() && point.getZ() >= min.getZ() && point.getZ() <= max.getZ();
    }

    public boolean contains(Area area) {
        return contains(area.getMin()) && contains(area.getMax());
    }

    public boolean intersects(Area area) {
        return contains(area.getMin()) || contains(area.getMax()) || area.contains(this.getMin()) || area.contains(this.getMax());
    }

    public Area getIntersection(Area area) {
        if (!intersects(area)) {
            return null;
        }
        return new Area(
                Math.max(this.getMin().getX(), area.getMin().getX()),
                Math.max(this.getMin().getY(), area.getMin().getY()),
                Math.max(this.getMin().getZ(), area.getMin().getZ()),
                Math.min(this.getMax().getX(), area.getMax().getX()),
                Math.min(this.getMax().getY(), area.getMax().getY()),
                Math.min(this.getMax().getZ(), area.getMax().getZ())
        );
    }

    public Area getUnion(Area area) {
        return new Area(
                Math.min(this.getMin().getX(), area.getMin().getX()),
                Math.min(this.getMin().getY(), area.getMin().getY()),
                Math.min(this.getMin().getZ(), area.getMin().getZ()),
                Math.max(this.getMax().getX(), area.getMax().getX()),
                Math.max(this.getMax().getY(), area.getMax().getY()),
                Math.max(this.getMax().getZ(), area.getMax().getZ())
        );
    }

    public Area getTranslated(Point3 translation) {
        return new Area(
                this.getMin().add(translation),
                this.getMax().add(translation)
        );
    }

    public Area getScaled(double scale) {
        return new Area(
                this.getMin().multiply(scale),
                this.getMax().multiply(scale)
        );
    }

    public Point3 getCenter() {
        return new Point3((min.getX() + max.getX()) / 2, (min.getY() + max.getY()) / 2, (min.getZ() + max.getZ()) / 2);
    }


    public double getWidth() {
        return this.getMax().getX() - this.getMin().getX();
    }

    public double getHeight() {
        return this.getMax().getY() - this.getMin().getY();
    }

    public double getDepth() {
        return this.getMax().getZ() - this.getMin().getZ();
    }

    public double getVolume() {
        return getWidth() * getHeight() * getDepth();
    }

    public boolean isEmpty() {
        return getWidth() <= 0 || getHeight() <= 0 || getDepth() <= 0;
    }

    public List<ChunkPoint> getChunkPoints() {
        List<ChunkPoint> chunkPoints = new ArrayList<>();
        int minChunkX = (int) Math.floor(this.getMin().getX() / 16f);
        int minChunkZ = (int) Math.floor(this.getMin().getZ() / 16f);
        int maxChunkX = (int) Math.ceil(this.getMax().getX() / 16f);
        int maxChunkZ = (int) Math.ceil(this.getMax().getZ() / 16f);
        for (int x = minChunkX; x < maxChunkX; x++) {
            for (int z = minChunkZ; z < maxChunkZ ; z++) {
                ChunkPoint chunkPoint = new ChunkPoint(x, z);
                if(x == minChunkX || x == maxChunkX - 1 || z == minChunkZ || z == maxChunkZ -1 ){
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
        return "Area{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }










}
