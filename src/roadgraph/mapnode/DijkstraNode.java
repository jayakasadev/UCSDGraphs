package roadgraph.mapnode;

import geography.GeographicPoint;

/**
 * Created by kasa2 on 10/8/2016.
 */
@Deprecated
public class DijkstraNode implements Comparable{

    private GeographicPoint point;
    private double distance;

    public DijkstraNode(GeographicPoint destination) throws IllegalArgumentException{
        this.point = destination;
        distance = 0.0;
    }

    public void setDistance(double distance){
        this.distance = distance;
    }

    public double getDistance(){
        return distance;
    }

    public GeographicPoint getPoint(){
        return point;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof DijkstraNode)){
            return false;
        }
        return point.distance(((DijkstraNode)obj).getPoint()) == 0;
    }

    @Override
    public String toString() {
        return point.toString();
    }

    @Override
    public int compareTo(Object o) {
        if(o == null || !(o instanceof DijkstraNode)){
            return -1;
        }
        return (int)point.distance(((DijkstraNode)o).getPoint());
    }
}
