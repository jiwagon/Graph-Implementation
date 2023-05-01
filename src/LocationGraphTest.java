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
        assertTrue(graph.addLocation("Fort Lauderdale"));
    }

    @Test
    void addDistance() {
        LocationGraph graph = new LocationGraph();

        graph.addLocation("New York");
        graph.addLocation("San Diego");
        graph.addLocation("State College");
        graph.addLocation("Philadelphia");

        graph.addDistance("New York", "Philadelphia", 1000.00);
        graph.addDistance("Philadelphia", "State College", 200.00);
        // Test case 1: adding a new edge, return true
        assertTrue(graph.addDistance("State College", "San Diego", 600.00));
        // Test case 2: adding an existing edge, return false
        assertFalse(graph.addDistance("New York", "Philadelphia", 500.00));

        System.out.print(graph);
    }
}