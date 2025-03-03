package services;

import entites.Coach;
import entites.Utilisateur;
import utilitaires.MaConnexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ServiceCoach {

    public static boolean ajouter(Utilisateur utilisateur, String description, String sexe, Date date_de_naissance) throws SQLException {
        String requete = "insert into coachs(description, ref_utilisateur, sexe, date_de_naissance) values(?, ?, ?, ?)";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setString(1, description);
        declaration.setInt(2, utilisateur.getRef_utilisateur());
        declaration.setString(3, sexe);
        declaration.setDate(4, new java.sql.Date(date_de_naissance.getTime()));

        return declaration.executeUpdate() != 0;
    }

    public static Coach obtenir(Utilisateur utilisateur) throws SQLException {
        String requete = "SELECT * FROM coachs WHERE ref_utilisateur = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setInt(1, utilisateur.getRef_utilisateur());

        ResultSet ensembleDeResultats = declaration.executeQuery();

        if(ensembleDeResultats.next())
        {
            return new Coach(utilisateur, ensembleDeResultats.getInt("ref_coach"), ensembleDeResultats.getString("description"), ensembleDeResultats.getString("sexe"), ensembleDeResultats.getDate("date_de_naissance"));
        }
        else {
            return null;
        }

    }

    public static Coach obtenir(int reference) throws SQLException {
        String requete = "SELECT * FROM coachs WHERE ref_coach = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setInt(1, reference);

        ResultSet ensembleDeResultats = declaration.executeQuery();

        if(ensembleDeResultats.next())
        {
            Utilisateur utilisateur = ServiceUtilisateur.obtenir(ensembleDeResultats.getInt("ref_utilisateur"));
            return new Coach(utilisateur, ensembleDeResultats.getInt("ref_coach"),
                                          ensembleDeResultats.getString("description"),
                                          ensembleDeResultats.getString("sexe"),
                                          ensembleDeResultats.getDate("date_de_naissance"));
        }
        else {
            return null;
        }

    }

    public static List<Coach> obtenirTout() throws SQLException {
        List<Coach> coachs = new java.util.ArrayList<>();
        String requete = "SELECT * FROM coachs";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        ResultSet ensembleDeResultats = declaration.executeQuery();

        while (ensembleDeResultats.next())
        {
            Utilisateur utilisateur = ServiceUtilisateur.obtenir(ensembleDeResultats.getInt("ref_utilisateur"));
            Coach coach = new Coach(utilisateur, ensembleDeResultats.getInt("ref_coach"),
                    ensembleDeResultats.getString("description"),
                    ensembleDeResultats.getString("sexe"),
                    ensembleDeResultats.getDate("date_de_naissance"));
            coachs.add(coach);
        }
        return coachs;

    }

    public static Coach mettreAJour(Coach coach) throws SQLException {
        ServiceUtilisateur.mettreAJour(coach);
        String requete = "UPDATE coachs SET  description = ? , ref_utilisateur = ? , sexe = ? , date_de_naissance = ? WHERE ref_coach = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);

        declaration.setString(1, coach.getDescription());
        declaration.setInt(2, coach.getRef_utilisateur());
        declaration.setString(3, coach.getSexe());
        declaration.setDate(4, new java.sql.Date(coach.getDate_de_naissance().getTime()));
        declaration.setInt(5, coach.getRef_coach());

        declaration.executeUpdate();

        return obtenir( coach.getRef_coach() );
    }
}
