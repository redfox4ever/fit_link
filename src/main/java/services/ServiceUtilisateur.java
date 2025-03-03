package services;

import entites.Utilisateur;
import utilitaires.MaConnexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServiceUtilisateur {

    public static boolean ajouter(String email, String mot_de_passe) throws SQLException {
        String requete = "insert into utilisateurs(email, mot_de_passe)" +
                " values('"+ email +
                "','"+ mot_de_passe + "')";
        Statement declaration = MaConnexion.obtenirConnexion().createStatement();
        return declaration.executeUpdate(requete) != 0;
    }

    public static Utilisateur obtenir(String email, String mot_de_passe) throws SQLException {
        String requete = "SELECT * FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setString(1, email);
        declaration.setString(2, mot_de_passe);

        ResultSet ensembleDeResultats = declaration.executeQuery();

        if(ensembleDeResultats.next())
        {
            return new Utilisateur(ensembleDeResultats.getInt("ref_utilisateur"), ensembleDeResultats.getString("nom"), ensembleDeResultats.getString("prenom"), ensembleDeResultats.getString("email"), ensembleDeResultats.getString("mot_de_passe"), ensembleDeResultats.getString("type_utilisateur"));
        }
        else {
            return null;
        }

    }

    public static Utilisateur obtenir(String email) throws SQLException {
        String requete = "SELECT * FROM utilisateurs WHERE email = ? ";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setString(1, email);

        ResultSet ensembleDeResultats = declaration.executeQuery();

        if(ensembleDeResultats.next())
        {
            return new Utilisateur(ensembleDeResultats.getInt("ref_utilisateur"), ensembleDeResultats.getString("nom"), ensembleDeResultats.getString("prenom"), ensembleDeResultats.getString("email"), ensembleDeResultats.getString("mot_de_passe"), ensembleDeResultats.getString("type_utilisateur"));
        }
        else {
            return null;
        }

    }

    public static Utilisateur obtenir(int reference) throws SQLException {
        String requete = "SELECT * FROM utilisateurs WHERE ref_utilisateur = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setInt(1, reference);

        ResultSet ensembleDeResultats = declaration.executeQuery();

        if(ensembleDeResultats.next())
        {
            return new Utilisateur(ensembleDeResultats.getInt("ref_utilisateur"), ensembleDeResultats.getString("nom"), ensembleDeResultats.getString("prenom"), ensembleDeResultats.getString("email"), ensembleDeResultats.getString("mot_de_passe"), ensembleDeResultats.getString("type_utilisateur"));
        }
        else {
            return null;
        }

    }

    public static Utilisateur mettreAJour(Utilisateur utilisateur) throws SQLException {
        String requete = "UPDATE utilisateurs SET nom = ?, prenom = ?, email = ?, mot_de_passe = ?, type_utilisateur= ? WHERE ref_utilisateur = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);

        declaration.setString(1, utilisateur.getNom());
        declaration.setString(2, utilisateur.getPrenom());
        declaration.setString(3, utilisateur.getEmail());
        declaration.setString(4, utilisateur.getMot_de_passe());
        declaration.setString(5, utilisateur.getType_utilisateur());
        declaration.setInt(6, utilisateur.getRef_utilisateur());

        declaration.executeUpdate();

        return obtenir( utilisateur.getRef_utilisateur() );
    }

    public static boolean supprimer(int ref_utilisateur) throws SQLException {
        String requete = "DELETE FROM utilisateurs WHERE ref_utilisateur = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setInt(1, ref_utilisateur);

        return declaration.executeUpdate() != 0;

    }

    public static boolean supprimer(Utilisateur utilisateur) throws SQLException {
        String requete = "DELETE FROM utilisateurs WHERE ref_utilisateur = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setInt(1, utilisateur.getRef_utilisateur());

        return declaration.executeUpdate() != 0;

    }
}
