/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.*;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
    private MapAdjList adjList;
	
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph(){
        adjList = new MapAdjList();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices(){
		return adjList.numVertices();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices(){

        Set<GeographicPoint> vertices = adjList.getVertices();
        Set<GeographicPoint> points = new HashSet<>();

        for(GeographicPoint node : vertices){
            points.add(node);
        }

		return points;
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges(){
		return adjList.numEdges();
	}

	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location){
        //System.out.println("addVertex " + location);
		boolean ret = adjList.implementAddVertex(location);
        //System.out.println("addVertex " + getNumEdges());
        return ret;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length) throws IllegalArgumentException {
		//map doesnt contain the point
		if(!adjList.containsNode(from) && !adjList.containsNode(to)){
            return;
        }

        adjList.implementAddEdge(from, to, roadName, roadType, length);
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched){

        Queue<GeographicPoint> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<GeographicPoint, GeographicPoint> parents = new HashMap<>();

        queue.add(start);
        visited.add(start.toString());
        GeographicPoint point = null;

        while(!queue.isEmpty()){
            point = queue.poll();

            if(point.distance(goal) == 0) break;

            List<MapEdge> neighbors = adjList.getNeighbors(point);
            //neighbors.addAll(adjList.getInNeighbors(point));

            for(MapEdge neighbor : neighbors){

                GeographicPoint node = neighbor.getDestination();
                if(visited.contains(node.toString())){
                    continue;
                }

                // Hook for visualization.  See writeup.
                // nodeSearched.accept(next.getLocation());
                nodeSearched.accept(node);

                visited.add(node.toString());

                parents.put(node, point);
                queue.add(node);
            }
        }

        //goal was not found
        if(point.distance(goal) != 0){
            return null;
        }

		return getPath(parents, goal);
	}

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched){
        if(!adjList.containsNode(start) || !adjList.containsNode(goal)){
            return null;
        }

        //resetting distances
        adjList.resetNodes();

        PriorityQueue<MapNode> queue = new PriorityQueue<>(new Comparator<MapNode>() {
            @Override
            public int compare(MapNode o1, MapNode o2) {
                if(o1 == null){
                    return 1;
                }
                if(o2 == null){
                    return -1;
                }
                double compare = o1.getDistance() - o2.getDistance();
                if(0 < compare){
                    return 1;
                }
                if(0 > compare){
                    return -1;
                }
                return 0;
            }
        });
        Set<MapNode> visited = new HashSet<>();
        Map<GeographicPoint, GeographicPoint> parents = new HashMap<>();

        MapNode curr = adjList.getMapNode(start);
        queue.add(curr);

        while(!queue.isEmpty()){
            curr = queue.poll();
            //System.out.println("Curr " + curr + " distance " + curr.getDistance());
            if(visited.contains(curr)){
                continue;
            }
            visited.add(curr);

            GeographicPoint point = curr.getCoordinate();
            if(point.distance(goal) == 0){
                break;
            }

            List<MapEdge> neighbors = adjList.getNeighbors(point);
            //neighbors.addAll(adjList.getInNeighbors(point));

            for(MapEdge neighbor : neighbors){
                GeographicPoint neighPoint = neighbor.getDestination();
                MapNode neighNode = adjList.getMapNode(neighPoint);
                if(visited.contains(neighNode)){
                    continue;
                }
                // Hook for visualization.  See writeup.
                nodeSearched.accept(neighPoint);

                double distance = curr.getDistance();
                double length = neighbor.getLength();

                if(neighNode.getDistance() == 0 || (distance + length) < neighNode.getDistance()){
                    neighNode.setDistance(distance + length);
                    //System.out.println("\tNeighbor " + neighNode + " distance " + neighNode.getDistance());
                    parents.put(neighPoint, point);
                    queue.add(neighNode);
                }

            }

        }

        System.out.println("Visited " + visited.size());

        //no path exists
        if(curr.getCoordinate().distance(goal) != 0){
            return null;
        }
        //System.out.println("Getting Path");
        //List<GeographicPoint> path = getPath(parents, goal);
        //System.out.println("Got Path");

        return getPath(parents, goal);
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal, Consumer<GeographicPoint> nodeSearched){
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());

        if(!adjList.containsNode(start) || !adjList.containsNode(goal)){
            return null;
        }

        //resetting distances
        adjList.resetNodes();

        PriorityQueue<MapNode> queue = new PriorityQueue<>(new Comparator<MapNode>() {
            @Override
            public int compare(MapNode o1, MapNode o2) {
                if(o1 == null){
                    return 1;
                }
                if(o2 == null){
                    return -1;
                }
                double compare = o1.getPrediction() - o2.getPrediction();
                if(0 < compare){
                    return 1;
                }
                if(0 > compare){
                    return -1;
                }
                return 0;
            }
        });
        Set<MapNode> visited = new HashSet<>();
        Map<GeographicPoint, GeographicPoint> parents = new HashMap<>();

        MapNode startNode = adjList.getMapNode(start);
        MapNode finish = adjList.getMapNode(goal);

        MapNode curr = adjList.getMapNode(start);
        queue.add(curr);

        while(!queue.isEmpty()){
            curr = queue.poll();
            //System.out.println("Curr " + curr + " distance " + curr.getDistance());
            if(visited.contains(curr)){
                continue;
            }
            visited.add(curr);

            GeographicPoint point = curr.getCoordinate();
            if(point.distance(goal) == 0){
                break;
            }

            List<MapEdge> neighbors = adjList.getNeighbors(point);
            //neighbors.addAll(adjList.getInNeighbors(point));

            for(MapEdge neighbor : neighbors){
                GeographicPoint neighPoint = neighbor.getDestination();
                MapNode neighNode = adjList.getMapNode(neighPoint);
                if(visited.contains(neighNode)){
                    continue;
                }
                // Hook for visualization.  See writeup.
                nodeSearched.accept(neighPoint);

                double distance = curr.getDistance();
                double length = neighbor.getLength();

                double hueristic = hueristic(neighNode, finish, length, distance);

                if(neighNode.getDistance() == 0 || (distance+length) < neighNode.getDistance()){
                    neighNode.setDistance(distance+length);
                    neighNode.setPrediction(hueristic);
                    //System.out.println("\tNeighbor " + neighNode + " distance " + neighNode.getDistance() + " predicted " + neighNode.getPrediction());
                    parents.put(neighPoint, point);
                    queue.add(neighNode);
                }

            }

        }

        System.out.println("Visited " + visited.size());

        //no path exists
        if(curr.getCoordinate().distance(goal) != 0){
            return null;
        }

        return getPath(parents, goal);
	}

	private double hueristic(MapNode curr, MapNode goal, double length, double distance){
        return (length + distance) * curr.distanceTo(goal);
        //return (start.distanceTo(curr) + curr.distanceTo(goal));
    }

    private List<GeographicPoint> getPath(Map<GeographicPoint, GeographicPoint> parents, GeographicPoint curr){
        //System.out.println("GETTING PATH");
        List<GeographicPoint> path = new ArrayList<>();
        while(curr != null){
            GeographicPoint parent = parents.get(curr);
            path.add(0, curr);
            curr = parent;
        }

        //path.stream().forEach(s -> System.out.println(adjList.getMapNode(s).getDistance()));
        //System.out.println();

        return path;
    }
	
	public static void main(String[] args){
		System.out.print("Making a new map...");
		/*
        MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		*/
		// You can use this method for testing.  
		
		
		/* Here are some test cases you should try before you attempt 
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 */

		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		
		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5 \t\t" + testStart + " to " + testEnd);
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
        System.out.println("Dijkstra: " + testroute.size());
        //testroute.stream().forEach(s -> System.out.println(s));
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
        System.out.println("AStar: " + testroute2.size());
        //testroute2.stream().forEach(s -> System.out.println(s));


		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
        System.out.println("Dijkstra: " + testroute.size());
		testroute2 = testMap.aStarSearch(testStart,testEnd);
        System.out.println("AStar: " + testroute2.size());
		
		
		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
        System.out.println("Dijkstra: " + testroute.size());
		testroute2 = testMap.aStarSearch(testStart,testEnd);
        System.out.println("AStar: " + testroute2.size());
		
		/* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/
		
	}
	
}
