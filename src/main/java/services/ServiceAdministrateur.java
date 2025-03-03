package services;

import entites.Administrateur;
import entites.Utilisateur;
import utilitaires.MaConnexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServiceAdministrateur {

    public static boolean ajouter(Utilisateur utilisateur) throws SQLException {
        String requete = "insert into administrateurs(ref_utilisateur)" +
                " values('"+ utilisateur.getRef_utilisateur() + "')";
        Statement declaration = MaConnexion.obtenirConnexion().createStatement();
        return declaration.executeUpdate(requete) != 0;
    }

    public static Administrateur obtenir(Utilisateur utilisateur) throws SQLException {
        String requete = "SELECT * FROM administrateurs WHERE ref_utilisateur = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setInt(1, utilisateur.getRef_utilisateur());

        ResultSet ensembleDeResultats = declaration.executeQuery();

        if(ensembleDeResultats.next())
        {
            return new Administrateur(utilisateur, ensembleDeResultats.getInt("ref_administrateur"));
        }
        else {
            return null;
        }

    }

    public static Administrateur obtenir(int reference) throws SQLException {
        String requete = "SELECT * FROM administrateurs WHERE ref_administrateur = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setInt(1, reference);

        ResultSet ensembleDeResultats = declaration.executeQuery();

        if(ensembleDeResultats.next())
        {
            Utilisateur utilisateur = ServiceUtilisateur.obtenir(ensembleDeResultats.getInt("ref_utilisateur"));
            return new Administrateur(utilisateur, ensembleDeResultats.getInt("ref_administrateur"));
        }
        else {
            return null;
        }

    }

    public static Administrateur mettreAJour(Administrateur administrateur) throws SQLException {
        ServiceUtilisateur.mettreAJour(administrateur);
        String requete = "UPDATE administrateurs SET  ref_utilisateur = ? WHERE ref_administrateur = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);

        declaration.setInt(1, administrateur.getRef_utilisateur());
        declaration.setInt(2, administrateur.getRef_administrateur());
        declaration.executeUpdate();

        return obtenir( administrateur.getRef_administrateur() );
    }
}
