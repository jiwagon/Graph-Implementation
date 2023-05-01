import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationGraphTest {

    @org.junit.jupiter.api.Test
    void addLocation() {
        LocationGraph graph = new LocationGraph();

        graph.addLocation("New York");
        graph.addLocation("San Diego");
        graph.addLocation("State College");
        graph.addLocation("Philadelphia");

        // Teset case 1: Adding an existing location, return false
        assertFalse(graph.addLocation("New York"));
        // Test case 2: Adding a new location, return true
        assertTrue(graph.addLocation("Pittsburgh"));
    }

    @Test
    void addDistance() {
    }
}