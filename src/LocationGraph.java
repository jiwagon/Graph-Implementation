import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

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

        for (int i = 0; i < start.distedges.size(); i++) {
            if (start.distedges.get(i).locationB.locationName == locationB) {
                    System.out.println("End Edge already exists in Graph");
                    return false;
                }
            }

        for (int i = 0; i < start.distedges.size(); i++) {
            if (end.distedges.get(i).locationA.locationName.equals(locationA)) {
                System.out.println("Start Edge already exists in Graph");
                return false;
            }
        }

        // Add same edge from end to start
        start.distedges.add(new distanceEdge(start, end, distance));
        end.distedges.add(new distanceEdge(end, start, distance));
        return true;
        // add vertex to startVertex arraylist of edges if edge with endVertex is not existing
        // add vertex to endVertex arraylist of edges if edge with startVertex is not existing
    }

    public Double findDistanceBreadthFirst(String locationA, String locationB){
        Queue<Location> searchQ = new LinkedList<>();
        ArrayList<Double> distances = new ArrayList<>();
        ArrayList<Location> visitedVertices = new ArrayList<>();

        int index = this.getIndex(locationA);
        searchQ.add(this.locations.get(index));
        visitedVertices.add(this.locations.get(index));
        distances.add(0.0);

        while(!searchQ.isEmpty()){
            Location current = searchQ.remove();
            int pos = visitedVertices.indexOf(current);
            if(current.locationName.equals(locationB))
                return distances.get(pos);

            for(distanceEdge edge: current.distedges){
                if(!visitedVertices.contains(edge.locationB)){
                    searchQ.add(edge.locationA);
                    visitedVertices.add(edge.locationB);
                    distances.add(distances.get(pos)+ edge.distance);
                    //dist = dist + edge.weight;
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
                    stack.push(edge.locationA);
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
                stb = stb.append("[" + this.locations.get(i).locationName);
                stb = stb.append(", " + edge.locationA.locationName);
                stb = stb.append(", " + edge.locationB.locationName);
                stb = stb.append(", " + edge.distance + "]; ");
            }
            stb.append("\n");
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
