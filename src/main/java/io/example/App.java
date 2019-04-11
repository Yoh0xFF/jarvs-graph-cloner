package io.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.example.cloner.GraphCloner;
import io.example.cloner.bean.Graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please execute program as follows: <programName> <inputFilePath> <entityId>");
            return;
        }

        File inputFile = new File(args[0]);
        if (!inputFile.exists()) {
            System.out.println("Input file does not exists!");
            return;
        }

        long entityId;
        try {
            entityId = Long.parseLong(args[1]);
        } catch (NumberFormatException ex) {
            System.out.println("Entity id must be number!");
            return;
        }


        ObjectMapper objectMapper = new ObjectMapper();
        Graph graph;

        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
            graph = objectMapper.readValue(inputStream, Graph.class);
        } catch (IOException ex) {
            System.out.println("Cannot read input file, error: " + ex.getMessage());
            return;
        }

        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(System.out, new GraphCloner(graph).cloneSource(entityId));
        }  catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
