package roadgraph;

import geography.GeographicPoint;

/**
 * Created by kasa2 on 9/25/2016.
 */
class MapNode{

    private GeographicPoint destination;
    private String roadName;
    private String roadType;
    private double length;

    MapNode(GeographicPoint destination, String roadName, String roadType, double length) throws IllegalArgumentException{
        if(destination != null && roadName != null && roadType != null && length > 0){
            this.destination = destination;
            this.roadName = roadName;
            this.roadType = roadType;
            this.length = length;
        }
        else{
            throw new IllegalArgumentException("Illegal Arguments: One or more of the arguments may be null, or length may be equal to zero.");
        }
    }

    GeographicPoint getDestination(){
        return destination;
    }

    String getRoadName(){
        return roadName;
    }

    String getRoadType(){
        return roadType;
    }

    double getLength(){
        return length;
    }

    @Override
    public String toString() {
        return destination + " Road Name: " + roadName + " Road Type: " + roadType + " Length: " + length;
    }
}
