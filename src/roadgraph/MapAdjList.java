package roadgraph;

import geography.GeographicPoint;

import java.util.*;

/**
 * Created by kasa2 on 9/25/2016.
 */
class MapAdjList{

    private Map<GeographicPoint, List<MapNode>> listMap;
    private int numEdges;

    MapAdjList(){
        listMap = new HashMap<>();
        numEdges = 0;
    }

    public boolean implementAddVertex(GeographicPoint edge) {
        if (!listMap.containsKey(edge) && edge != null){
            listMap.put(edge, new ArrayList<MapNode>());
            return true;
        }
        return false;
    }

    public Set<GeographicPoint> getVertices(){
        return listMap.keySet();
    }

    public boolean containsEdge(GeographicPoint edge){
        return listMap.containsKey(edge);
    }

    public int numVertices(){
        return listMap.keySet().size();
    }

    public void implementAddEdge(GeographicPoint vertex, MapNode edge) {
        List<MapNode> list = listMap.get(vertex);
        list.add(edge);
    }

    public int numEdges(){
        return numEdges;
    }

    public List<MapNode> getNeighbors(GeographicPoint vertex) {
        return listMap.get(vertex);
    }

    public List<GeographicPoint> getInNeighbors(GeographicPoint v) {
        List<GeographicPoint> inNeighbors = new ArrayList<>();
        for (GeographicPoint u : listMap.keySet()) {
            //iterate through all edges in u's adjacency list and
            //add u to the inNeighbor list of v whenever an edge
            //with startpoint u has endpoint v.
            for (MapNode w : listMap.get(u)) {
                if (v.distance(w.getDestination()) == 0) {
                    inNeighbors.add(u);
                }
            }
        }
        return inNeighbors;
    }

    public List<Integer> getDistance2(int v) {
        return null;
    }

    public String adjacencyString() {
        String s = "Adjacency list";
        s += " (size " + numVertices() + "+" + numEdges() + " Nodes):";

        for (GeographicPoint v : listMap.keySet()) {
            s += "\n\t"+v+": ";
            for (MapNode w : listMap.get(v)) {
                s += w+", ";
            }
        }
        return s;
    }
}