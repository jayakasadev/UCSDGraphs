package roadgraph;

import geography.GeographicPoint;

import java.util.*;


class MapNode implements Comparable{

    private List<MapEdge> edges;
    private Map<GeographicPoint, MapEdge> edgeMap;
    private GeographicPoint coordinate;
    private double distance;
    private double prediction;

    MapNode(GeographicPoint coordinate){
        this.coordinate = coordinate;
        edges = new ArrayList<>();
        edgeMap = new HashMap<>();
    }

    double getPrediction() {
        return prediction;
    }

    void setPrediction(double distance){
        this.prediction = distance;
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

    public double distanceTo(MapNode goal){
        double xCurr = coordinate.getX();
        double yCurr = coordinate.getY();

        double xGoal = goal.getCoordinate().getX();
        double yGoal = goal.getCoordinate().getY();

        double x = xCurr - xGoal;
        double y = yCurr - yGoal;

        //double a = Math.pow(x, 2);
        //double b = Math.pow(y, 2);

        //System.out.println(x + " " + y);
        //System.out.println(a + " " + b);

        //System.out.println("\t\tDistance from " + coordinate + "\t-->\t" + goal.coordinate + "\t= " + Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));

        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
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
