package roadgraph;

import geography.GeographicPoint;

import java.util.*;


class MapNode implements Comparable{

    private List<MapEdge> edges;
    private Map<GeographicPoint, MapEdge> edgeMap;
    private GeographicPoint coordinate;
    private double distance;

    MapNode(GeographicPoint coordinate){
        this.coordinate = coordinate;
        edges = new ArrayList<>();
        edgeMap = new HashMap<>();
    }

    double getDistance() {
        return distance;
    }

    void setDistance(double distance){
        this.distance = distance;
    }

    public GeographicPoint getCoordinate(){
        return coordinate;
    }

    public void addEdge(GeographicPoint to, String roadName, String roadType, double length){
        MapEdge edge = new MapEdge(to, roadName, roadType, length);
        edges.add(edge);
        edgeMap.put(to, edge);
    }

    public MapEdge getEdge(GeographicPoint point){
        return edgeMap.get(point);
    }

    public List<MapEdge> getEdges(){
        return edges;
    }

    public void resetNodes(){
        setDistance(0);
    }

    @Override
    public int compareTo(Object o) {
        if(o == null || !(o instanceof MapNode)){
            return -1;
        }
        /*
        double compare = ((MapNode)o).getDistance();
        if(distance < compare){
            return 1;
        }
        if(distance > compare){
            return -1;
        }
        */
        //System.out.println("CompareTo" + coordinate.distance(((MapNode)o).getCoordinate()));
        return (int)coordinate.distance(((MapNode)o).getCoordinate());
    }

    @Override
    public String toString() {
        return coordinate.toString();
    }
}
