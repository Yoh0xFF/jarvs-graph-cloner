package io.example.cloner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.example.cloner.bean.Graph;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GraphClonerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Graph load(String fileName) {
        try (InputStream inputStream = this.getClass().getResourceAsStream("/json/" + fileName)) {
            return objectMapper.readValue(inputStream, Graph.class);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private void executeTest(String inputFile, String expectedOutputFile, long source) {
        Graph input, output, expectedOutput;

        input = load(inputFile);
        expectedOutput = load(expectedOutputFile);

        output = new GraphCloner(input).cloneSource(source);

        try {
            assertEquals(objectMapper.writeValueAsString(expectedOutput), objectMapper.writeValueAsString(output));
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Test
    public void exampleTest() {
        int source = 5;
        String inputFile = "example.json";
        String expectedOutputFile = "example-result.json";

        executeTest(inputFile, expectedOutputFile, source);
    }

    @Test
    public void singleInstanceUsedTwiceTest() {
        final int SOURCE = 5;
        Graph input = load("example.json");
        GraphCloner graphCloner = new GraphCloner(input);

        assertThrows(IllegalStateException.class, () -> {
            graphCloner.cloneSource(SOURCE);
            graphCloner.cloneSource(SOURCE);
        });
    }

    @Test
    public void sourceDoesNotExistsTest() {
        int source = 4;
        String inputFile = "source-does-not-exists.json";
        String expectedOutputFile = "source-does-not-exists-result.json";

        executeTest(inputFile, expectedOutputFile, source);
    }

    @Test
    public void loop1Test() {
        int source = 3;
        String inputFile = "loop1.json";
        String expectedOutputFile = "loop1-result.json";

        executeTest(inputFile, expectedOutputFile, source);
    }

    @Test
    public void loop2Test() {
        int source = 4;
        String inputFile = "loop2.json";
        String expectedOutputFile = "loop2-result.json";

        executeTest(inputFile, expectedOutputFile, source);
    }

    @Test
    public void loop3Test() {
        int source = 2;
        String inputFile = "loop3.json";
        String expectedOutputFile = "loop3-result.json";

        executeTest(inputFile, expectedOutputFile, source);
    }

    @Test
    public void loop4Test() {
        int source = 1;
        String inputFile = "loop4.json";
        String expectedOutputFile = "loop4-result.json";

        executeTest(inputFile, expectedOutputFile, source);
    }

    @Test
    public void singleVertexTest() {
        int source = 4;
        String inputFile = "single-vertex.json";
        String expectedOutputFile = "single-vertex-result.json";

        executeTest(inputFile, expectedOutputFile, source);
    }
}
