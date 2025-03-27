//package com.example.kidsapplication;
//
//import java.sql.*;
//import spark.Spark;
//import com.google.gson.*;
//
//class Score {
//    String username;
//    String game;
//    int points;
//}
//
//public class GameScoreBackend {
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/game_db?useSSL=false";
//    private static final String DB_USER = "root";
//    private static final String DB_PASSWORD = "password";
//    private static final Gson gson = new Gson();
//
//    public static void main(String[] args) {
//        initializeDatabase();
//
//        Spark.post("/update_score", (req, res) -> {
//            ScoreUpdate data = gson.fromJson(req.body(), ScoreUpdate.class);
//            updateScore(data.username, data.game, data.points);
//            return gson.toJson("Points added to " + data.username);
//        });
//
//        Spark.get("/get_score/:username", (req, res) -> {
//            String username = req.params("username");
//            Integer score = getScore(username);
//            if (score != null) {
//                return gson.toJson(new ScoreUpdate(){ { username = username; points = score; } });
//            } else {
//                res.status(404);
//                return gson.toJson("User not found");
//            }
//        });
//    }
//
//    private static void initializeDatabase() {
//        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
//             Statement stmt = conn.createStatement()) {
//            String sql = "CREATE TABLE IF NOT EXISTS users (" +
//                    "id INT AUTO_INCREMENT PRIMARY KEY," +
//                    "username VARCHAR(255) UNIQUE," +
//                    "score INT DEFAULT 0)";
//            stmt.execute(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void updateScore(String username, String game, int points) {
//        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
//            PreparedStatement selectStmt = conn.prepareStatement("SELECT score FROM users WHERE username = ?");
//            selectStmt.setString(1, username);
//            ResultSet rs = selectStmt.executeQuery();
//
//            if (rs.next()) {
//                int newScore = rs.getInt("score") + points;
//                PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET score = ? WHERE username = ?");
//                updateStmt.setInt(1, newScore);
//                updateStmt.setString(2, username);
//                updateStmt.executeUpdate();
//            } else {
//                PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO users (username, score) VALUES (?, ?)");
//                insertStmt.setString(1, username);
//                insertStmt.setInt(2, points);
//                insertStmt.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static Integer getScore(String username) {
//        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
//            PreparedStatement stmt = conn.prepareStatement("SELECT score FROM users WHERE username = ?");
//            stmt.setString(1, username);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return rs.getInt("score");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
