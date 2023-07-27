package de.shiro.api.blocks;

import java.io.Serializable;

public class PointStats implements Serializable {

    Point3 minPoint = Point3.MAX;
    Point3 maxPoint = Point3.MIN;

    public Area toArea() {
        return new Area(minPoint, maxPoint);
    }

    public PointStats consume(Area area){
        return consumeMin(area.getMin()).consumeMax(area.getMax());
    }

    public PointStats consume(ChunkPoint chunkPoint){
        return consumeMin(chunkPoint.getWorldMin()).consumeMax(chunkPoint.getWorldMax());
    }

   public PointStats consumeMin(Point3 point){
        minPoint = minPoint.min(point);
        return this;
   }

    public PointStats consumeMin(Point2 point2){
        minPoint = minPoint.min(point2.toPoint3(0));
        return this;
    }

    public PointStats consumeMax(Point2 point2){
        maxPoint = maxPoint.max(point2.toPoint3(0));
        return this;
    }

    public PointStats consumeMax(Point3 point){
        maxPoint = maxPoint.max(point);
        return this;
    }

    public PointStats combine(PointStats other){
        minPoint = minPoint.min(other.minPoint);
        maxPoint = maxPoint.max(other.maxPoint);
        return this;
    }


    public String toString(){
        return "min: " + minPoint + " max: " + maxPoint;
    }

}
