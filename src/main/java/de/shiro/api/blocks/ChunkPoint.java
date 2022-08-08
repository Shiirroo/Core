package de.shiro.api.blocks;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ChunkPoint {

    @Getter
    private final Point2 min = new Point2();

    @Getter
    private final Point2 max = new Point2(15, 15);

    @Getter
    private final Point2 chunkPoint;

    @Getter
    private boolean border = false;


    public ChunkPoint(int x, int z) {
        this(new Point2(x, z));

    }

    public ChunkPoint setBorder() {
        this.border = true;
        return this;
    }

    public ChunkPoint(Point2 chunkPoint) {
        this.chunkPoint = chunkPoint;
        min.add(chunkPoint.getX() * 16, chunkPoint.getZ() * 16);
        max.add(chunkPoint.getX() * 16, chunkPoint.getZ() * 16);
    }


    public ChunkPoint() {
        this(0,0);
    }
    public ChunkPoint(Chunk chunk) {
        this(chunk.getX(), chunk.getZ());
    }


    public Chunk getChunk(String worldName) {
        World world = Bukkit.getWorld(worldName)    ;
        if(world == null) return null;
        return Optional.of(world.getChunkAt(getX(), getZ())).orElse(null);
    }

    public Chunk getChunk(World world) {
        return Optional.of(world.getChunkAt(getX(), getZ())).orElse(null);
    }

    public double distance(ChunkPoint otherPoint) {
        return Math.sqrt(Math.pow(this.chunkPoint.getX()- otherPoint.getX(), 2) + Math.pow(this.chunkPoint.getZ()- otherPoint.getZ(), 2));
    }

    public boolean sameAs(ChunkPoint otherPoint) {
        return this.chunkPoint.getX()== otherPoint.chunkPoint.getX()&& this.chunkPoint.getZ()== otherPoint.getZ();
    }

    public Location getLocation(String worldName) {
        World world = Bukkit.getWorld(worldName)    ;
        if(world == null) return null;
        return Optional.of(world.getChunkAt(getX(), getZ())).orElse(null).getBlock(0,0,0).getLocation();
    }

    public Location getLocation(World world){
        CompletableFuture<Chunk> chunkAtAsync = world.getChunkAtAsync(getX(), getZ());
        CompletableFuture<Location> locationCompletableFuture =  chunkAtAsync.thenApplyAsync(
                chunk -> chunk.getBlock(this.getMiddlePoint().getX(), this.getMiddlePoint().getY(), this.getMiddlePoint().getZ()).getLocation());
        return locationCompletableFuture.join();
    }

    public Point3 getMiddlePoint() {
        return new Point3(getX() << 4+8, 100, getZ() << 4+8);
    }
    public Location getMiddleLocation(World world, int y) {
        return new Location(world, (getX()  << 4) + 8, y, (getZ()  << 4) +8);
    }

    public static ChunkPoint of(int x, int z) {
        return new ChunkPoint(x, z);
    }

    public static ChunkPoint of(Chunk chunk) {
        return new ChunkPoint(chunk);
    }

    public ChunkPoint add(ChunkPoint otherPoint) {
        return new ChunkPoint(this.chunkPoint.getX()+ otherPoint.getX(), this.chunkPoint.getZ()+ otherPoint.getZ());
    }

    public ChunkPoint subtract(ChunkPoint otherPoint) {
        return new ChunkPoint(this.chunkPoint.getX()- otherPoint.getX(), this.chunkPoint.getZ()- otherPoint.getZ());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChunkPoint point3)) return false;
        return chunkPoint.getX()== point3.chunkPoint.getX()&& chunkPoint.getZ()== point3.getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getZ());
    }

    public int compareTo(ChunkPoint otherPoint) {
        if(this.chunkPoint.getX()< otherPoint.getX()) return -1;
        if(this.chunkPoint.getX()> otherPoint.getX()) return 1;
        return Integer.compare(this.getZ(), otherPoint.getZ());
    }

    public String toString() {
        return "ChunkPoint{" +
                "chunkPoint=" + chunkPoint +
                ", border=" + border +
                ", min=" + min +
                ", max=" + max +
                '}';
    }

    public Integer getX() {
        return chunkPoint.getX();
    }

    public Integer getZ() {
        return chunkPoint.getZ();
    }

    public Integer volume() {
        return (max.getX() - min.getX() + 1) * (max.getZ() - min.getZ() + 1);
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


}
