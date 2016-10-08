package roadgraph.mapnode;

import geography.GeographicPoint;

/**
 * Created by kasa2 on 10/8/2016.
 */
public class SimpleNode extends MapNode{

    public SimpleNode(GeographicPoint destination) throws IllegalArgumentException{
        if(destination != null){
            this.destination = destination;
        }
        else{
            throw new IllegalArgumentException("Illegal Arguments: One or more of the arguments may be null, or length may be equal to zero.");
        }
    }
}
