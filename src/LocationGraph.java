import java.util.ArrayList;

// Location = vertex
public class LocationGraph {

    /**
     * addLocation(String location): this should add the location
     * if and only if the location does not already exist,
     * return a true if it is successful and false if it was not
     *
     * addDistance(String locationA, String locationB, Double distance):
     * this should add an edge between the two locations,
     * if one of the locations doesn't exist,
     * then add the location, return false if the edge already exists and true if it was successful
     */

    ArrayList<Location> locations;
    public int getIndex(String vertex) {
        for (int i = 0; i < this.locations.size(); i++) {
            if (this.locations.get(i).locationName == vertex)
                return i;
        }
        return -1;
    }

    public Location addLocation(String location) {
        Location toAdd = new Location(location);
        // or if (!vertices.contains(vertex))
        // vertex.compareTo(String.valueOf(vertices)) == 0
        if (getIndex(location) == -1) {
            this.locations.add(toAdd);
        }
        else {
            System.out.println("Location" + location + " already exists.");
        }
        return toAdd;
    }

    public void addDistance(String locationA, String locationB, Double distance) {
        Location start;
        Location end;
        // check if start vertex exists or not
        int startPos = this.getIndex(locationA);
        if (startPos < 0) {
            start = this.addLocation(locationA);
        }
        else {
            start = this.locations.get(startPos);
        }
        // check if end vertex exists or not
        int endPos = this.getIndex(locationB);
        if (endPos < 0) {
            end = this.addLocation(locationB);
        }
        else {
            end = this.locations.get(endPos);
        }

        for (int i = 0; i < start.distedges.size(); i++) {
            if (start.distedges.get(i).locationB.locationName == locationB) {
                    System.out.println("End Edge already exists in Graph");
                    return;
                }
            }

        for (int i = 0; i < start.distedges.size(); i++) {
            if (end.distedges.get(i).locationA.locationName.equals(locationA)) {
                System.out.println("Start Edge already exists in Graph");
                return;
            }
        }

        // Add same edge from end to start
        start.distedges.add(new distanceEdge(start, end, distance));
        end.distedges.add(new distanceEdge(end, start, distance));
        // add vertex to startVertex arraylist of edges if edge with endVertex is not existing
        // add vertex to endVertex arraylist of edges if edge with startVertex is not existing
    }
    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < this.locations.size(); i++) {
            for (distanceEdge edge: this.locations.get(i).distedges) {
                stb = stb.append("[" + this.locations.get(i).locationName);
                stb = stb.append(", " + edge.locationA.locationName);
                stb = stb.append(", " + edge.locationB.locationName);
                stb = stb.append(", " + edge.weight + "]; ");
            }
            stb.append("\n");
        }
        return stb.toString();
    }

//    public double distanceBFS(String startName, String endName){
//        Queue<Vertex> searchQ = new LinkedList<>();
//        ArrayList<Double> distances = new ArrayList<>();
//        ArrayList<Vertex> visitedVertices = new ArrayList<>();
//        int index = this.getIndexOfVertex(startName);
//        searchQ.add(this.locations.get(index));
//        visitedVertices.add(this.locations.get(index));
//        distances.add(0.0);
//        while(!searchQ.isEmpty()){
//            Vertex current = searchQ.remove();
//            int pos = visitedVertices.indexOf(current);
//            if(current.locationName.equals(endName))
//                return distances.get(pos);
//            for(Edge edge: current.edges){
//                if(!visitedVertices.contains(edge.vertex)){
//                    searchQ.add(edge.vertex);
//                    visitedVertices.add(edge.vertex);
//                    distances.add(distances.get(pos)+ edge.weight);
//                    //dist = dist + edge.weight;
//                }
//            }
//        }
//        return -1;
//    }

}
