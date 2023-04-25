public class distanceEdge {

    Location locationA; // Source vertex
    Location locationB; // Destination vertex
    double weight; // Amount owed from source to destination

    public distanceEdge(Location locationA, Location locationB, double distance) {
        this.locationA = locationA;
        this.locationB = locationB;
        this.weight = distance;
    }

    @Override
    public String toString() {
        return "[" + locationA.locationName + ", " + locationB.locationName + ", " + weight + "]";
    }
}
