import java.util.*;

// Location = vertex
public class LocationGraph {

    ArrayList<Location> locations;

    public LocationGraph() {
        this.locations = new ArrayList<Location>();
    }

    public int getIndex(String vertex) {
        for (int i = 0; i < this.locations.size(); i++) {
            if (this.locations.get(i).locationName == vertex)
                return i;
        }
        return -1;
    }

    // addLocation(String location):
    // this should add the location if and only if the location does not already exist,
    // return a true if it is successful and false if it was not
    public boolean addLocation(String location) {
        Location toAdd = new Location(location);
        // or if (!vertices.contains(vertex))
        // vertex.compareTo(String.valueOf(vertices)) == 0
        if (getIndex(location) == -1) {
            this.locations.add(toAdd);
            return true;
        }
        else {
            System.out.println("Location " + location + " already exists.");
        }
        return false;
    }


    // addDistance(String locationA, String locationB, Double distance):
    // this should add an edge between the two locations,
    // if one of the locations doesn't exist, then add the location,
    // return false if the edge already exists and true if it was successful
    public boolean addDistance(String locationA, String locationB, Double distance) {
        Location start;
        Location end;
        // check if start vertex exists or not
        int startPos = this.getIndex(locationA);
        if (startPos < 0) {
            this.addLocation(locationA);
            start = this.locations.get(this.getIndex(locationA));

        }
        else {
            start = this.locations.get(startPos);
        }
        // check if end vertex exists or not
        int endPos = this.getIndex(locationB);
        if (endPos < 0) {
            this.addLocation(locationB);
            end = this.locations.get(this.getIndex(locationB));
        }
        else {
            end = this.locations.get(endPos);
        }

        distanceEdge toAddStart = new distanceEdge(start, end,distance);
        distanceEdge toAddEnd = new distanceEdge(end, start, distance);

        for (int i = 0; i < start.distedges.size(); i++) {
            if (start.distedges.get(i).locationA.locationName == locationA &&
                    start.distedges.get(i).locationB.locationName == locationB) {
                    System.out.println("Distance already exists in Graph");
                    return false;
            }
        }

        start.distedges.add(toAddStart);
        end.distedges.add(toAddEnd);
        return true;
    }

    public Double findDistanceBreadthFirst(String locationA, String locationB){
        Queue<Location> searchQ = new LinkedList<>();
        ArrayList<Double> distances = new ArrayList<>();
        ArrayList<Location> visitedVertices = new ArrayList<>();

        if(Objects.equals(locationA, locationB)){
            return -1.0;
        }

        int index = this.getIndex(locationA);
        int indexB = this.getIndex(locationB);

        if (index ==-1 || indexB == -1) {
            return -1.0;
        }

        searchQ.add(this.locations.get(index));
        visitedVertices.add(this.locations.get(index));
        distances.add(0.0);

        while(!searchQ.isEmpty()){
            Location current = searchQ.remove();
            int pos = visitedVertices.indexOf(current);

//            if(current.locationName.equals(locationA))
//                return distances.get(pos);

            if (current.locationName.equals(locationB))
                return distances.get(pos);

            for(distanceEdge edge: current.distedges) {
                if(!visitedVertices.contains(edge.locationB)) {
                    searchQ.add(edge.locationB);
                    visitedVertices.add(edge.locationB);
                    distances.add(distances.get(pos)+ edge.distance);
                }
            }
        }
        return -1.0;
    }

    public Double findDistanceDepthFirst(String locationA, String locationB){
        ArrayList<Location> visited = new ArrayList<>();
        Stack<Location> stack = new Stack();
        Stack<Double> distance = new Stack();

        int index = this.getIndex(locationA);
        stack.push(this.locations.get(index));
        visited.add(this.locations.get(index));
        distance.add(0.0);

        while(!stack.isEmpty()){
            Location current = stack.pop();
            Double curr_dis = distance.pop();
            if(current.locationName.equals(locationB)){
                return curr_dis;
            }

            for(distanceEdge edge: current.distedges){
                if(!visited.contains(edge.locationB)){
                    stack.push(edge.locationB);
                    visited.add(edge.locationB);
                    Double new_dis = 0.0;
                    new_dis = curr_dis + edge.distance;
                    distance.add(new_dis);

                }
            }

        }
        return -1.0;
    }

    public Boolean detectCycle(){
        ArrayList<Location> visited = new ArrayList<>();
        Queue<Location> queue = new LinkedList<>();
        Queue<Location> parent = new LinkedList<>();
        for (Location toCycle: this.locations){
            visited.add(toCycle);
            queue.add(toCycle);
            parent.add(toCycle);
            while(!queue.isEmpty()) {
                Location current = queue.remove();
                Location f_current = parent.remove();
                for(distanceEdge edge: current.distedges){
                    if(edge.locationA.getName().equals(f_current.getName())){
                        continue;
                    }
                    if(!visited.contains(edge.locationB)){
                        queue.add(edge.locationA);
                        visited.add(edge.locationB);
                        parent.add(current);
                    }else{
                        return true;
                    }
                }
            }
            //delete all the things in arraylist and queue
            queue.clear();
            visited.clear();
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i < this.locations.size(); i++) {
            for (distanceEdge edge: this.locations.get(i).distedges) {
                //stb = stb.append("[" + this.locations.get(i).locationName);
                stb = stb.append("[" + edge.locationA.locationName
                        + ", " + edge.locationB.locationName
                        + ", " + edge.distance + "]; \n");
            }
        }
        return stb.toString();
    }
    //    @Override
//    public String toString(){
//        StringBuilder str = new StringBuilder();
//        str.append("\t");
//        for(int x = 0; x < this.vertices.size(); x++){
//            Vertex a = this.vertices.get(x);
//            str.append(a.getName());
//            str.append("\t");
//        }
//        str.append("\n");
//        for(int x = 0; x < this.vertices.size(); x++){
//            Vertex a = this.vertices.get(x);
//            str.append(a.getName());
//            str.append("\t");
//            for(int y = 0; y < this.vertices.size(); y++){
//                Vertex b = this.vertices.get(y);
//                str.append(String.format(" %.2f",findDistanceBreadthFirst(a.getName(),b.getName())));
//                str.append("\t");
//            }
//            str.append('\n');
//        }
//        return str.toString();
//    }
}
