package roadgraph;

import geography.GeographicPoint;

/**
 * Created by kasa2 on 10/9/2016.
 */
public class MapEdge{

    private GeographicPoint destination;

    private String roadName;
    private String roadType;

    private double length;

    MapEdge(GeographicPoint point, String roadName, String roadType, double length)throws IllegalArgumentException{
        if(point != null && roadName != null && roadType != null && length > 0){
            this.destination = point;
            this.length = length;
            this.roadName = roadName;
            this.roadType = roadType;
        }
        else{
            throw new IllegalArgumentException("Illegal Arguments: One or more of the arguments may be null, or length may be equal to zero.");
        }
    }

    String getRoadName() {
        return roadName;
    }

    GeographicPoint getDestination(){
        return destination;
    }

    String getRoadType() {
        return roadType;
    }

    double getLength() {
        return length;
    }

    void setLength(double length){
        this.length = length;
    }

    @Override
    public String toString() {
        String out = "";
        if(destination != null)
            out += destination.toString();
        if(roadName != null && roadName.length() > 0)
            out += " Road Name: " + roadName;
        if(roadType!= null && roadType.length() > 0)
            out += " Road Type: " + roadType;
        if(length > 0)
            out += " Length: " + length;
        return out;
    }


}
