package services;

import entities.Exercice;
import connection.MyConnection;
import entities.Niveau;
import entities.Regime;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciceService implements IService<Exercice> {

    private Connection cnx;

    public ExerciceService() {
        cnx = MyConnection.getInstance().getConnection();
    }

    @Override
    public void create(Exercice exercice) throws SQLException {
        String query = "INSERT INTO exercice (nom, description, calories_brulees, duree, niveau_difficulte, type_exercice, id_regime) " +
                "VALUES (?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, exercice.getNom());
            ps.setString(2, exercice.getDescription());
            ps.setDouble(3, exercice.getCaloriesBrulees());
            ps.setInt(4, exercice.getDuree());
            ps.setString(5, exercice.getNiveauDifficulte().name());
            ps.setString(6, exercice.getTypeExercice());
            ps.setInt(7, exercice.getRegime().getId());
            ps.executeUpdate();
        }

    }

    @Override
    public void update(Exercice exercice) throws SQLException {
        String query = "UPDATE exercice SET nom = ?, description = ?, calories_brulees = ?, duree = ?, niveau_difficulte = ?, type_exercice  = ?, id_regime = ? WHERE id_exercice = ?";

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, exercice.getNom());
            ps.setString(2, exercice.getDescription());
            ps.setDouble(3, exercice.getCaloriesBrulees());
            ps.setInt(4, (int) exercice.getDuree());
            ps.setString(5,  exercice.getNiveauDifficulte().name());
            ps.setString(6, exercice.getTypeExercice());
            ps.setInt(7, exercice.getRegime().getId());
            ps.setInt(8, exercice.getid());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Exercice exercice) throws SQLException {
        String query = "DELETE FROM exercice WHERE id_exercice = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, exercice.getid());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Exercice> readAll() throws SQLException {
        List<Exercice> exercices = new ArrayList<>();
        String query = "SELECT * FROM exercice";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query)){

        while (rs.next()) {
            int idExercice = rs.getInt("id_exercice");
            String nom = rs.getString("nom");
            String description = rs.getString("description");
            String type = rs.getString("type_exercice");
            Niveau niveau = Niveau.valueOf(rs.getString("niveau_difficulte"));
            double caloriesBrulees = rs.getDouble("calories_brulees");
            int duree = rs.getInt("duree");
            Regime regime = new Regime();
            regime.setId(rs.getInt("id_regime"));

            Exercice e = new Exercice( idExercice, nom, description,  caloriesBrulees, duree, niveau,  type, regime);
            exercices.add(e);
        }
        }
        return exercices;
    }
    public List<Exercice> getExercisesForRegime(int regimeId) throws SQLException {
        List<Exercice> exercises = new ArrayList<>();
        String query = "SELECT * FROM exercice WHERE id_regime = ?";

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, regimeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Exercice e = new Exercice(
                            rs.getInt("id_exercice"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getDouble("calories_brulees"),
                            rs.getInt("duree"),
                            Niveau.valueOf(rs.getString("niveau_difficulte")), // Ensure this method exists
                            rs.getString("type_exercice"),
                            new Regime(regimeId) // Associate the exercise with the given regime
                    );
                    exercises.add(e);
                }
            }
        }
        return exercises;
    }


}
