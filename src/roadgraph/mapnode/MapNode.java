package roadgraph.mapnode;

import geography.GeographicPoint;

/**
 * Created by kasa2 on 9/25/2016.
 */
public abstract class MapNode{

    protected GeographicPoint destination;

    protected String roadName;
    protected String roadType;

    protected double length;


    public GeographicPoint getDestination() {
        return destination;
    }

    public String getRoadName() {
        return roadName;
    }

    public String getRoadType() {
        return roadType;
    }


    public double getLength() {
        return length;
    }


    @Override
    public String toString() {
        String out = "";
        if(destination != null)
            out += destination.toString();
        if(roadName!= null && roadName.length() > 0)
            out += " Road Name: " + roadName;
        if(roadName!= null && roadName.length() > 0)
            out += " Road Type: " + roadType;
        if(length > 0)
            out += " Length: " + length;
        return out;
    }
}
