package services;

import entities.Coach;
import entities.Exercice;
import entities.Niveau;
import entities.Regime;
import connection.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegimeService implements IService<Regime> {

    private Connection cnx;

    public RegimeService() {
        cnx = MyConnection.getInstance().getConnection();
    }
    private final ExerciceService exerciceService = new ExerciceService();


    @Override
    public void create(Regime regime) throws SQLException {
        String query = "INSERT INTO regime (nom, description, type_regime, ref_coach) VALUES (?, ?, ?,?)";
        try(PreparedStatement ps = cnx.prepareStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, regime.getNom());
            ps.setString(2, regime.getDescription());
            ps.setString(3, regime.getTypeRegime());
            ps.setInt(4, regime.getRefCoach());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    regime.setId(id);
                    System.out.println("ID generé : " + id);
                } else {
                    throw new SQLException("Creating regime failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void update(Regime regime) throws SQLException {
        String query = "UPDATE regime SET nom = ?, description = ?, type_regime = ?, ref_coach = ? WHERE id = ?";
        try(PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, regime.getNom());
            ps.setString(2, regime.getDescription());
            ps.setString(3, regime.getTypeRegime());
            ps.setInt(4, regime.getRefCoach());
            ps.setInt(5, regime.getId());
            ps.executeUpdate();
        }

    }

    @Override
    public void delete(Regime regime) throws SQLException {
        String query = "DELETE FROM regime WHERE id = ?";
        try(PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, regime.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Regime> readAll() throws SQLException {
        String query = "SELECT r.id AS id_regime, r.nom AS nom_regime, r.description AS desc_regime, r.type_regime, " +
                "r.ref_coach AS ref_coach, c.description AS coach_description, " +
                "e.id_exercice, e.nom AS nom_exercice, e.description AS desc_exercice, " +
                "e.calories_brulees, e.duree, e.niveau_difficulte, e.type_exercice " +
                "FROM regime r "+
                "LEFT JOIN coachs c ON r.ref_coach = c.ref_coach " +
                "LEFT JOIN exercice e ON r.id = e.id_regime";

        Map<Integer, Regime> regimeMap = new HashMap<>();
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                int idRegime = rs.getInt("id_regime");
                Regime regime = getOrCreateRegime(regimeMap, idRegime, rs);

                // Créer ou récupérer le coach associé au régime
                int refCoach = rs.getInt("ref_coach");
                if (!rs.wasNull()) { // Vérifie si un coach est associé
                    String coachDescription = rs.getString("coach_description");


                    // Créer un objet Coach
                    Coach coach = new Coach(
                            coachDescription
                    );
                    regime.setCoach(coach); // Associer le coach au régime
                } else {
                    regime.setCoach(null); // Aucun coach associé
                }


                if (!rs.wasNull()) { // Vérifie si un exercice est associé au régime
                    Exercice exercice = new Exercice(
                            rs.getInt("id_exercice"),
                            rs.getString("nom_exercice"),
                            rs.getString("desc_exercice"),
                            rs.getDouble("calories_brulees"),
                            rs.getInt("duree"),
                            getNiveau(rs.getString("niveau_difficulte")),
                            rs.getString("type_exercice"),
                            regime
                    );
                    regime.addExercice(exercice);
                }
                else {
                    List<Exercice> exercices = exerciceService.getExercisesForRegime(idRegime);

                    regime.setExercices(exercices);

                }
            }
        }

        return new ArrayList<>(regimeMap.values());
    }

    // Méthode utilitaire pour créer ou récupérer un régime dans la map
    private Regime getOrCreateRegime(Map<Integer, Regime> regimeMap, int idRegime, ResultSet rs) throws SQLException {
        if (!regimeMap.containsKey(idRegime)) {
            Regime regime = new Regime(
                    rs.getString("nom_regime"),
                    rs.getString("desc_regime"),
                    rs.getString("type_regime"),
                    idRegime
            );
            regimeMap.put(idRegime, regime);
        }
        return regimeMap.get(idRegime);
    }

    // Méthode utilitaire pour gérer les niveaux NULL
    private Niveau getNiveau(String niveauStr) {
        return (niveauStr != null && !niveauStr.isEmpty()) ? Niveau.valueOf(niveauStr) : null;
    }
        public List<Integer> getAllCoachIds() {
            List<Integer> coachIds = new ArrayList<>();
            String query = "SELECT ref_coach FROM coachs"; // Assure-toi que le nom de la table est correct

            try (PreparedStatement pst = cnx.prepareStatement(query);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    coachIds.add(rs.getInt("ref_coach"));
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des IDs des coachs : " + e.getMessage());
            }

            return coachIds;
        }

}
