import java.util.*;

// Location = vertex
public class LocationGraph {

    ArrayList<Location> locations;
    ArrayList<Location> path;

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

    // 1. addLocation(String location):
    // this should add the location if and only if the location does not already exist,
    // return a true if it is successful and false if it was not
    public boolean addLocation(String location) {
        Location toAdd = new Location(location);
        // or if (!vertices.contains(vertex))
        // vertex.compareTo(String.valueOf(vertices)) == 0
        if (getIndex(location) == -1) {
            this.locations.add(toAdd);
            return true;
        } else {
            //System.out.println("Location " + location + " already exists.");
        }
        return false;
    }

    // 2. addDistance(String locationA, String locationB, Double distance):
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

        } else {
            start = this.locations.get(startPos);
        }
        // check if end vertex exists or not
        int endPos = this.getIndex(locationB);
        if (endPos < 0) {
            this.addLocation(locationB);
            end = this.locations.get(this.getIndex(locationB));
        } else {
            end = this.locations.get(endPos);
        }

        distanceEdge toAddStart = new distanceEdge(start, end, distance);
        distanceEdge toAddEnd = new distanceEdge(end, start, distance);

        for (int i = 0; i < start.distedges.size(); i++) {
            if (start.distedges.get(i).locationA.locationName == locationA &&
                    start.distedges.get(i).locationB.locationName == locationB) {
                //System.out.println("Distance already exists in Graph");
                return false;
            }
        }

        start.distedges.add(toAddStart);
        end.distedges.add(toAddEnd);
        return true;
    }

    // 3. findDistanceBreadthFirst(String locationA, String locationB):
    // this should find the distance between the points using a breadth first algorithm
    public Double findDistanceBreadthFirst(String locationA, String locationB) {
        Queue<Location> searchQ = new LinkedList<>();
        ArrayList<Double> distances = new ArrayList<>();
        ArrayList<Location> visitedVertices = new ArrayList<>();

        if (Objects.equals(locationA, locationB)) {
            return -1.0;
        }

        int index = this.getIndex(locationA);
        int indexB = this.getIndex(locationB);

        if (index == -1 || indexB == -1) {
            return -1.0;
        }

        searchQ.add(this.locations.get(index));
        visitedVertices.add(this.locations.get(index));
        distances.add(0.0);

        while (!searchQ.isEmpty()) {
            Location current = searchQ.remove();
            int pos = visitedVertices.indexOf(current);

            if (current.locationName.equals(locationB))
                return distances.get(pos);

            for (distanceEdge edge : current.distedges) {
                if (!visitedVertices.contains(edge.locationB)) {
                    searchQ.add(edge.locationB);
                    visitedVertices.add(edge.locationB);
                    distances.add(distances.get(pos) + edge.distance);
                }
            }
        }
        return -1.0;
    }

    // 4. findDistanceDepthFirst(String locationA, String locationB):
    // this should find the distance between the points using a depth first algorithm
    public Double findDistanceDepthFirst(String locationA, String locationB) {
        Stack<Location> searchStack = new Stack<>();
        ArrayList<Double> distances = new ArrayList<>();
        ArrayList<Location> visitedVertices = new ArrayList<>();

        if (Objects.equals(locationA, locationB)) {
            return -1.0;
        }

        int index = this.getIndex(locationA);
        int indexB = this.getIndex(locationB);

        if (index == -1 || indexB == -1) {
            return -1.0;
        }

        searchStack.push(this.locations.get(index));
        visitedVertices.add(this.locations.get(index));
        distances.add(0.0);

        while (!searchStack.isEmpty()) {
            Location current = searchStack.pop();
            int pos = visitedVertices.indexOf(current);

            if (current.locationName.equals(locationB)) {
                return distances.get(pos);
            }

            for (distanceEdge edge : current.distedges) {
                if (!visitedVertices.contains(edge.locationB)) {
                    searchStack.push(edge.locationB);
                    visitedVertices.add(edge.locationB);
                    distances.add(distances.get(pos) + edge.distance);
                }
            }
        }

        return -1.0;
    }


    // 5. detectCycle():
    // return true if there is a cycle and false if there is no cycle
    public Boolean detectCycle() {
        Queue<Location> queue = new LinkedList<>();
        ArrayList<Location> visited = new ArrayList<>();
        ArrayList<Location> parent = new ArrayList<>();

        for (Location toCycle : this.locations) {
            visited.add(toCycle);
            queue.add(toCycle);
            parent.add(null);
            while (!queue.isEmpty()) {
                Location current = queue.remove();
                //Location f_current = parent.remove();
                Location f_current = parent.get(0);
                parent.remove(0);
                for (distanceEdge edge : current.distedges) {
                    if (edge.locationA == f_current) {
                        continue;
                    } else if (edge.locationB == f_current) {
                        continue;
                    }
                    if (!visited.contains(edge.locationA)) {
                        queue.add(edge.locationA);
                        visited.add(edge.locationA);
                        parent.add(current);
                    } else if (!visited.contains(edge.locationB)) {
                        queue.add(edge.locationB);
                        visited.add(edge.locationB);
                        parent.add(current);
                    } else {
                        return true; // return true if there is a cycle
                    }
                }
            }
            // delete all the things in arraylist and queue
            queue.clear();
            visited.clear();
            parent.clear();
        }
        return false; // return false if there is no cycle
    }

    // 6. toString(): should return a string of the adjacency graph representation of this graph,
    // use the weight values to indicate that there is an edge and a -1 if there is no edge
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\t\t\t\t");
        for (int x = 0; x < this.locations.size(); x++) {
            Location a = this.locations.get(x);
            str.append(a.getName());
            str.append("\t");
        }
        str.append("\n");
        for (int x = 0; x < this.locations.size(); x++) {
            Location a = this.locations.get(x);
            str.append(a.getName());
            str.append("\t\t");
            for (int y = 0; y < this.locations.size(); y++) {
                Location b = this.locations.get(y);
                str.append(String.format(" %.2f", findDistanceBreadthFirst(a.getName(), b.getName())));
                str.append("\t\t");
                //str.append(String.format(" %.2f",findDistanceDepthFirst(a.getName(),b.getName())));
            }
            str.append('\n');
        }
        return str.toString();
    }

    private double getTotalDistance(ArrayList<Location> path) {
        double distance = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Location current = path.get(i);
            Location next = path.get(i + 1);
            for (distanceEdge edge : current.distedges) {
                if (edge.locationB.equals(next)) {
                    distance += edge.distance;
                    break;
                }
            }
        }
        return distance;
    }

    // 7. (extra credit) findMinimumPath(String locationA, String locationB):
    // this should return the minimum path between two points
    public ArrayList<Location> findMinimumPath (String locationA, String locationB){
        Queue<Location> queue = new LinkedList<>();
        ArrayList<Double> distances = new ArrayList<>(this.locations.size());
        ArrayList<Location> parents = new ArrayList<>(this.locations.size());

        Location start = this.locations.get(this.getIndex(locationA));
        Location end = this.locations.get(this.getIndex(locationB));
        if (start == null || end == null) {
            // return null if one of the locations doesn't exist
            return null;
        }

        for (int i = 0; i < this.locations.size(); i++) {
            if (this.locations.get(i).equals(start)) {
                distances.add(0.0);
            } else {
                distances.add(Double.POSITIVE_INFINITY);
            }
            parents.add(null);
        }

        queue.offer(start);

        while (!queue.isEmpty()) {
            Location current = queue.poll();
            if (current.equals(end)) {
                break;
            }

            for (distanceEdge edge : current.distedges) {
                Location neighbor = edge.locationB;
                double newDistance = distances.get(this.locations.indexOf(current)) + edge.distance;
                if (newDistance < distances.get(this.locations.indexOf(neighbor))) {
                    distances.set(this.locations.indexOf(neighbor), newDistance);
                    parents.set(this.locations.indexOf(neighbor), current);
                    queue.offer(neighbor);
                }
            }
        }
        ArrayList<Location> path = new ArrayList<>();
        Location current = end;
        while (current != null) {
            path.add(current);
            current = parents.get(this.locations.indexOf(current));
        }
        Collections.reverse(path);
        if (path == null || path.size() == 0) {
            System.out.println("No path found.");
        } else {
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i).getName());
                if (i < path.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println("\nTotal distance: ");
            System.out.println(getTotalDistance(path));
            System.out.println("\n");
        }
        return path;
    }
}