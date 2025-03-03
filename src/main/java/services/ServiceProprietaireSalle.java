package services;

import entites.ProprietaireSalle;
import entites.Utilisateur;
import utilitaires.MaConnexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServiceProprietaireSalle {

    public static boolean ajouter(Utilisateur utilisateur) throws SQLException {
        String requete = "insert into proprietaires_salle(ref_utilisateur)" +
                " values('"+ utilisateur.getRef_utilisateur() + "')";
        Statement declaration = MaConnexion.obtenirConnexion().createStatement();
        return declaration.executeUpdate(requete) != 0;
    }

    public static ProprietaireSalle obtenir(Utilisateur utilisateur) throws SQLException {
        String requete = "SELECT * FROM proprietaires_salle WHERE ref_utilisateur = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setInt(1, utilisateur.getRef_utilisateur());

        ResultSet ensembleDeResultats = declaration.executeQuery();

        if(ensembleDeResultats.next())
        {
            return new ProprietaireSalle(utilisateur, ensembleDeResultats.getInt("ref_proprietaire_salle"));
        }
        else {
            return null;
        }

    }

    public static ProprietaireSalle obtenir(int reference) throws SQLException {
        String requete = "SELECT * FROM proprietaires_salle WHERE ref_proprietaire_salle = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setInt(1, reference);

        ResultSet ensembleDeResultats = declaration.executeQuery();

        if(ensembleDeResultats.next())
        {
            Utilisateur utilisateur = ServiceUtilisateur.obtenir(ensembleDeResultats.getInt("ref_utilisateur"));
            return new ProprietaireSalle(utilisateur, ensembleDeResultats.getInt("ref_proprietaire_salle"));
        }
        else {
            return null;
        }

    }

    public static ProprietaireSalle mettreAJour(ProprietaireSalle proprietaireSalle) throws SQLException {
        ServiceUtilisateur.mettreAJour(proprietaireSalle);
        String requete = "UPDATE proprietaires_salle SET  ref_utilisateur = ? WHERE ref_proprietaire_salle = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);

        declaration.setInt(1, proprietaireSalle.getRef_utilisateur());
        declaration.setInt(2, proprietaireSalle.getRef_proprietaire_salle());
        declaration.executeUpdate();

        return obtenir( proprietaireSalle.getRef_proprietaire_salle() );
    }
}
