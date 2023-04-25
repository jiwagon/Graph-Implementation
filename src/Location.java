import java.util.ArrayList;

public class Location {

    String locationName;
    ArrayList<distanceEdge> distedges;

    public Location(String name) {
        this.locationName = name;
        this.edges = new ArrayList<distanceEdge>();
    }

    public String getName() {
        return this.locationName;
    }
}