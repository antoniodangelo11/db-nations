package org.lessons.java.sql;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        // Connessione
        String url = "jdbc:mysql://localhost:3306/db_nations";
        String user = "root";
        String password = "root";

        try { Connection con = DriverManager.getConnection(url, user, password);

            // Inizializzo lo Scanner
            Scanner input = new Scanner(System.in);
            System.out.println("Fai un ricerca: ");
            String searchString = input.nextLine();

            String query = "SELECT c.country_id, c.name, r.name, c2.name "
                    + "FROM countries c "
                    + "JOIN regions r ON r.region_id = c.region_id "
                    + "JOIN continents c2 ON c2.continent_id = r.continent_id "
                    + "WHERE c.name LIKE ? "
                    + "ORDER BY c.name ";

            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {

                // Qui metto i parametri di ricerca
                preparedStatement.setString(1, "%" + searchString + "%");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        int countryId        = resultSet.getInt("country_id");
                        String countryName   = resultSet.getString("c.name");
                        String regionName    = resultSet.getString("r.name");
                        String continentName = resultSet.getString("c2.name");

                        System.out.println(
                                countryId + " - "
                                + countryName + " - "
                                + regionName + " - "
                                + continentName);
                    }
                } catch (SQLException e) {
                    System.out.println("Unable to execute query");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("Unable to prepare statement");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("Unable to open connection");
            e.printStackTrace();
        }
    }
}
