public class distanceEdge {

    Location locationA; // Source vertex
    Location locationB; // Destination vertex

    Location location;
    Double distance; // Amount owed from source to destination

    public distanceEdge(Location locationA, Location locationB, double distance) {
        this.locationA = locationA;
        this.locationB = locationB;
        //this.location = location;
        this.distance = distance; // weight
    }

    @Override
    public String toString() {
        return "[" + locationA.locationName + ", " + locationB.locationName + ", " + distance + "]";
    }
}
