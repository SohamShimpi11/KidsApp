package com.example.kidsapplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Backend {
    // Simulated player data
    private static List<Player> players = new ArrayList<>();

    static {
        // Sample player data
        players.add(new Player("Alice", 95));
        players.add(new Player("Bob", 85));
        players.add(new Player("Charlie", 90));
        players.add(new Player("David", 70));
        players.add(new Player("Eve", 80));
    }

    // Method to fetch top players (top 3 based on scores)
    public static List<Player> getTopPlayers() {
        // Sort players by score in descending order
        Collections.sort(players, Comparator.comparingInt(Player::getScore).reversed());
        return players.subList(0, Math.min(3, players.size())); // Return top 3 players
    }

    // Method to fetch all players
    public static List<Player> getAllPlayers() {
        return new ArrayList<>(players); // Return a copy of all players
    }
}
