package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Graph {
    private Map<String, List<String>> adjacencyList;
    private Map<String, Set<String>> movieToActorsMap;
    private Map<String, String> movieTitles;
    private Map<String, String> movieCache;

    public Graph() {
        adjacencyList = new HashMap<>();
        movieToActorsMap = new HashMap<>();
        movieTitles = new HashMap<>();
        movieCache = new HashMap<>();
    }

    public void loadFromFile() throws IOException {
        loadTitles();
        loadActors();
        createEdges();
    }

    private void loadTitles() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream("/filtered_title.akas.tsv"))))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length > 1) {
                    String titleId = parts[0];
                    String title = parts[1];
                    movieTitles.put(titleId, title);
                }
            }
        }
        System.out.println("Loaded titles: " + movieTitles.size());
    }

    private void loadActors() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream("/filtered_name.basics.tsv"))))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length > 5) {
                    String actorName = parts[1];
                    String[] movies = parts[5].split(",");

                    adjacencyList.putIfAbsent(actorName, new ArrayList<>());

                    for (String movie : movies) {
                        movieToActorsMap.putIfAbsent(movie, new HashSet<>());
                        movieToActorsMap.get(movie).add(actorName);
                    }
                }
            }
        }
        System.out.println("Loaded actors: " + adjacencyList.size());
    }

    private void createEdges() {
        for (Set<String> actors : movieToActorsMap.values()) {
            for (String actor1 : actors) {
                for (String actor2 : actors) {
                    if (!actor1.equals(actor2)) {
                        adjacencyList.get(actor1).add(actor2);
                    }
                }
            }
        }
    }

    public Map<String, List<String>> getAdjacencyList() {
        return adjacencyList;
    }

    public String findShortestPath(String start, String end) {
        if (!adjacencyList.containsKey(start) || !adjacencyList.containsKey(end)) {
            return "One or both names do not exist.";
        }

        // BFS for shortest path
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (current.equals(end)) {
                return reconstructPath(previous, start, end);
            }
            for (String neighbor : adjacencyList.get(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    previous.put(neighbor, current);
                }
            }
        }

        return "No path found.";
    }

    private String reconstructPath(Map<String, String> previous, String start, String end) {
        List<String> path = new ArrayList<>();
        for (String at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);

        StringBuilder result = new StringBuilder(String.join(" -> ", path)).append("\n");
        for (int i = 0; i < path.size() - 1; i++) {
            String movie = findCommonMovie(path.get(i), path.get(i + 1));
            result.append(path.get(i)).append(" is in ").append(movie).append(" with ").append(path.get(i + 1)).append("\n");
        }

        return result.toString();
    }

    private String findCommonMovie(String actor1, String actor2) {
        String key = actor1 + "-" + actor2;
        if (movieCache.containsKey(key)) {
            return movieCache.get(key);
        }
        for (Map.Entry<String, Set<String>> entry : movieToActorsMap.entrySet()) {
            if (entry.getValue().contains(actor1) && entry.getValue().contains(actor2)) {
                String movieTitle = movieTitles.get(entry.getKey());
                movieCache.put(key, movieTitle);
                return movieTitle;
            }
        }
        return "Unknown Movie";
    }
}
