package roadgraph.mapnode;

import geography.GeographicPoint;

/**
 * Created by kasa2 on 10/8/2016.
 */
public class AStarNode extends MapNode{
    public AStarNode(GeographicPoint destination, int length) throws IllegalArgumentException{
        if(destination != null && length > 0){
            this.destination = destination;
            this.length = length;
        }
        else{
            throw new IllegalArgumentException("Illegal Arguments: One or more of the arguments may be null, or length may be equal to zero.");
        }
    }
}
