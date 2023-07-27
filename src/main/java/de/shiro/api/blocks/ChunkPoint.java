package de.shiro.api.blocks;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ChunkPoint  implements Serializable {

    @Getter @Expose
    private final Point2 worldMin = new Point2();

    @Getter @Expose
    private final Point2 worldMax = new Point2(15, 15);

    @Getter @Expose
    private final Point2 chunkPoint;

    @Getter @Expose
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
        worldMin.add(chunkPoint.getX() * 16, chunkPoint.getZ() * 16);
        worldMax.add(chunkPoint.getX() * 16, chunkPoint.getZ() * 16);
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

    public ChunkBlocks getChunkBlocks(World world){
        ChunkBlocks chunkBlocks = new ChunkBlocks(this);
        for (int x = worldMin.getX(); x <= worldMax.getX(); x++) {
            for (int z = worldMin.getZ(); z <= worldMin.getZ() ; z++) {
                int hightesBlock = world.getHighestBlockYAt(x, z);
                    for (int y = 0; y <= hightesBlock; y++) {
                        chunkBlocks.addBlock(new GameBlock(x, y, z, world.getBlockAt(x, y, z).getBlockData().getAsString()));
                }
            }
        }
        return chunkBlocks;
    }


    public Location getLocation(World world){
        Chunk chunk = world.getChunkAt(getX(), getZ());
        return chunk.getBlock(this.getMiddlePoint().getX(), this.getMiddlePoint().getY(), this.getMiddlePoint().getZ()).getLocation();
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

    public ChunkPoint add(int x, int z) {
        return new ChunkPoint(this.chunkPoint.getX()+ x, this.chunkPoint.getZ()+ z);
    }
    public ChunkPoint subtract(int x, int z) {
        return new ChunkPoint(this.chunkPoint.getX()- x, this.chunkPoint.getZ()- z);
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
                ", worldMin=" + worldMin +
                ", worldMax=" + worldMax +
                '}';
    }

    public Integer getX() {
        return chunkPoint.getX();
    }

    public Integer getZ() {
        return chunkPoint.getZ();
    }

    public Integer volume() {
        return (worldMax.getX() - worldMin.getX() + 1) * (worldMax.getZ() - worldMin.getZ() + 1);
    }

    public Point2 getPoint1() {
        return new Point2(worldMin.getX(), worldMin.getZ());
    }

    public Point2 getPoint2() {
        return new Point2(worldMax.getX(), worldMin.getZ());
    }

    public Point2 getPoint3() {
        return new Point2(worldMax.getX(), worldMax.getZ());
    }

    public Point2 getPoint4() {
        return new Point2(worldMin.getX(), worldMax.getZ());
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
