import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationGraphTest {

    @org.junit.jupiter.api.Test
    void addLocation() {
        LocationGraph graph = new LocationGraph();

        graph.addLocation("New York");
        graph.addLocation("San Diego");
        graph.addLocation("State College");
        graph.addLocation("Philadelphia");

        // Test case 1: adding an existing location, return false
        assertFalse(graph.addLocation("New York"));
        assertFalse(graph.addLocation("San Diego"));
        assertFalse(graph.addLocation("State College"));
        assertFalse(graph.addLocation("Philadelphia"));
        // Test case 2: adding a new location, return true
        assertTrue(graph.addLocation("Pittsburgh"));
        assertTrue(graph.addLocation("San Francisco"));
    }

    @Test
    void addDistance() {
        LocationGraph graph = new LocationGraph();

        graph.addLocation("New York");
        graph.addLocation("San Diego");
        graph.addLocation("State College");
        graph.addLocation("Philadelphia");

        graph.addDistance("New York", "Philadelphia", 1000.00);
        graph.addDistance("New York", "San Diego", 400.00);
        graph.addDistance("Philadelphia", "State College", 200.00);
        // Test case 1: adding a new edge, return true
        assertTrue(graph.addDistance("State College", "San Diego", 600.00));
        // Test case 2: adding an existing edge, return false
        assertFalse(graph.addDistance("New York", "Philadelphia", 500.00));
        // Test case 3: adding an existing edge in reverse order, return false
        assertFalse(graph.addDistance("State College", "Philadelphia", 600.00));

        System.out.print(graph);
    }

    @Test
    void findDistanceBreadthFirst() {
        LocationGraph graph = new LocationGraph();

        graph.addLocation("New York");
        graph.addLocation("San Diego");
        graph.addLocation("State College");
        graph.addLocation("Philadelphia");
        graph.addLocation("Los Angeles");

        graph.addDistance("New York", "Philadelphia", 100.0);
        graph.addDistance("New York", "San Diego", 500.0);
        graph.addDistance("Philadelphia", "State College", 50.0);

        // return distance if there is a distance between two locations
        assertEquals(500.0, graph.findDistanceBreadthFirst("New York","San Diego"));
        assertEquals(150.0, graph.findDistanceBreadthFirst("New York","State College"));
        assertEquals(650.0, graph.findDistanceBreadthFirst("San Diego","State College"));
        assertEquals(100.0, graph.findDistanceBreadthFirst("New York","Philadelphia"));
        //return -1.0 if there is no distance between two locations
        assertEquals(-1.0, graph.findDistanceBreadthFirst("New York","Los Angeles"));
        assertEquals(-1.0, graph.findDistanceBreadthFirst("Los Angeles","San Diego"));
        // return -1.0 if either location does not exist
        assertEquals(-1.0, graph.findDistanceBreadthFirst("Miami","State College"));
        assertEquals(-1.0, graph.findDistanceBreadthFirst("New York","Boston"));
        assertEquals(-1.0, graph.findDistanceBreadthFirst("New York","Boston"));
        assertEquals(-1.0, graph.findDistanceBreadthFirst("Boston","Los Angeles"));
        // return -1.0 if start and end locations are the same
        assertEquals(-1.0, graph.findDistanceBreadthFirst("New York","New York"));

        System.out.println(graph);
    }

    @Test
    void findDistanceDepthFirst() {
        LocationGraph graph = new LocationGraph();

        graph.addLocation("New York");
        graph.addLocation("San Diego");
        graph.addLocation("State College");
        graph.addLocation("Philadelphia");

        graph.addDistance("New York", "Philadelphia", 100.0);
        graph.addDistance("New York", "San Diego", 500.0);
        graph.addDistance("Philadelphia", "State College", 50.0);

        // return distance if there is a distance between two locations
        assertEquals(500.0, graph.findDistanceBreadthFirst("New York","San Diego"));
        assertEquals(150.0, graph.findDistanceBreadthFirst("New York","State College"));
        assertEquals(650.0, graph.findDistanceBreadthFirst("San Diego","State College"));
        assertEquals(100.0, graph.findDistanceBreadthFirst("New York","Philadelphia"));
        //return -1.0 if there is no distance between two locations
        assertEquals(-1.0, graph.findDistanceBreadthFirst("New York","Los Angeles"));
        assertEquals(-1.0, graph.findDistanceBreadthFirst("Los Angeles","San Diego"));
        // return -1.0 if either location does not exist
        assertEquals(-1.0, graph.findDistanceBreadthFirst("Miami","State College"));
        assertEquals(-1.0, graph.findDistanceBreadthFirst("New York","Boston"));
        assertEquals(-1.0, graph.findDistanceBreadthFirst("New York","Boston"));
        assertEquals(-1.0, graph.findDistanceBreadthFirst("Boston","Los Angeles"));
        // return -1.0 if start and end locations are the same
        assertEquals(-1.0, graph.findDistanceBreadthFirst("New York","New York"));
    }
    @Test
    void detectCycle() {
        LocationGraph graph = new LocationGraph();
        graph.addLocation("State College");
        graph.addDistance("New York", "Philadelphia", 10.0);
        graph.addDistance("New York", "San Diego", 20.0);
        graph.addDistance("Atlanta", "Miami", 40.0);
        graph.addDistance("Philadelphia", "San Diego", 20.0);

        LocationGraph graph1 = new LocationGraph();
        graph1.addDistance("New York", "Philadelphia", 10.0);
        graph1.addDistance("New York", "San Diego", 20.0);
        graph1.addDistance("Atlanta", "Miami", 40.0);
        //return true if detecting cycle
        assertTrue(graph.detectCycle());
        //return false if there is no cycle
        assertFalse(graph1.detectCycle());
    }

    @Test
    void findMinimumPath() {

        LocationGraph graph = new LocationGraph();

        graph.addLocation("A");
        graph.addLocation("B");
        graph.addLocation("C");
        graph.addLocation("D");
        graph.addLocation("E");
        graph.addDistance("A", "B", 5.0);
        graph.addDistance("B", "C", 10.0);
        graph.addDistance("D", "C", 2.0);
        Double expected1 = 5.0;
        Double actual1 = graph.findMinimumPath("A", "B");
        assertEquals(expected1, actual1);

        // Test case 2: shortest path requires multiple connections
        Double expected2 = 15.0;
        Double actual2 = graph.findMinimumPath("A", "C");
        assertEquals(expected2, actual2);

        // Test case 3: no path from start to end
        Double expected3 = -1.0;
        Double actual3 = graph.findMinimumPath("A", "E");
        assertEquals(expected3, actual3);

        // Test case 4: start and end are the same location
        Double expected4 = 0.0;
        Double actual4 = graph.findMinimumPath("A", "A");
        assertEquals(expected4, actual4);

        // Test case 5: one location only
        Double expected5 = -1.0;
        Double actual5 = graph.findMinimumPath("A", "B");
        assertEquals(expected5, actual5);
    }
}