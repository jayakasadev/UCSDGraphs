package roadgraph;

import geography.GeographicPoint;

import java.util.*;

/**
 * Created by kasa2 on 9/25/2016.
 */
class MapAdjList{

    //private Map<GeographicPoint, List<MapEdge>> listMap;
    private Map<GeographicPoint, MapNode> nodes;
    private int numEdges;

    MapAdjList(){
        nodes = new HashMap<>();
        numEdges = 0;
    }

    public MapNode getMapNode(GeographicPoint point){
        return nodes.get(point);
    }

    public double getDistance(GeographicPoint node){
        return nodes.get(node).getDistance();
    }

    public boolean containsNode(GeographicPoint point){
        return nodes.containsKey(point);
    }

    public boolean implementAddVertex(GeographicPoint node) {
        //System.out.println("implementAddVertex " + edge);
        if (!nodes.containsKey(node) && node != null){
            //System.out.println("Adding Vertex " + edge);
            nodes.put(node, new MapNode(node));

            //System.out.println("Adding " + nodes.get(node));
            return true;
        }
        return false;
    }

    public Set<GeographicPoint> getVertices(){
        return nodes.keySet();
    }

    public int numVertices(){
        return nodes.keySet().size();
    }

    public void implementAddEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length) {

        MapNode node = nodes.get(from);
        //System.out.println("from " + from + " to " + to + " length " + length);
        node.addEdge(to, roadName, roadType, length);

        numEdges++;
    }

    public int numEdges(){
        return numEdges;
    }

    public List<MapEdge> getNeighbors(GeographicPoint vertex) {
        return nodes.get(vertex).getEdges();
    }

    public List<MapNode> getInNeighbors(GeographicPoint v) {
        List<MapNode> inNeighbors = new ArrayList<>();
        for (GeographicPoint u : nodes.keySet()) {
            //iterate through all edges in u's adjacency list and
            //add u to the inNeighbor list of v whenever an edge
            //with startpoint u has endpoint v.
            for (MapEdge w : nodes.get(u).getEdges()) {
                if (v.distance(w.getDestination()) == 0) {
                    inNeighbors.add(nodes.get(u));
                }
            }
        }
        return inNeighbors;
    }

    public void resetNodes(){
        nodes.keySet().stream().forEach(s -> nodes.get(s).resetNodes());
    }

    public String adjacencyString() {
        String s = "Adjacency list";
        s += " (size " + numVertices() + "+" + numEdges() + " Nodes):";

        for (GeographicPoint v : nodes.keySet()) {
            s += "\n\t"+v+": ";
            for (MapEdge w : nodes.get(v).getEdges()) {
                s += w.getDestination() + ", ";
            }
        }
        return s;
    }


}
