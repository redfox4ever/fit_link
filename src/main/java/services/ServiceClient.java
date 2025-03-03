package services;

import entites.Client;
import entites.Utilisateur;
import utilitaires.MaConnexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;


public class ServiceClient {

    public static boolean ajouter(Utilisateur utilisateur, String objectif, int taille, int poids, Date date_de_naissance, String sexe, int ref_coach) throws SQLException {
        String requete = "insert into clients(objectif, taille, poids, date_de_naissance, sexe, ref_coach, ref_utilisateur) values(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setString(1, objectif);
        declaration.setInt(2, taille);
        declaration.setInt(3, poids);
        declaration.setDate(4, new java.sql.Date(date_de_naissance.getTime()));
        declaration.setString(5, sexe);
        declaration.setInt(6, ref_coach);
        declaration.setInt(7, utilisateur.getRef_utilisateur());

        return declaration.executeUpdate() != 0;
    }

    public static boolean ajouter(Utilisateur utilisateur, String objectif, int taille, int poids, Date date_de_naissance, String sexe) throws SQLException {
        String requete = "insert into clients(objectif, taille, poids, date_de_naissance, sexe, ref_utilisateur) values(?, ?, ?, ?, ?, ?)";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setString(1, objectif);
        declaration.setInt(2, taille);
        declaration.setInt(3, poids);
        declaration.setDate(4, new java.sql.Date(date_de_naissance.getTime()));
        declaration.setString(5, sexe);
        declaration.setInt(6, utilisateur.getRef_utilisateur());

        return declaration.executeUpdate() != 0;
    }

    public static Client obtenir(Utilisateur utilisateur) throws SQLException {
        String requete = "SELECT * FROM clients WHERE ref_utilisateur = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setInt(1, utilisateur.getRef_utilisateur());

        ResultSet ensembleDeResultats = declaration.executeQuery();

        if(ensembleDeResultats.next())
        {
            return new Client(utilisateur, ensembleDeResultats.getInt("ref_client"),
                                           ensembleDeResultats.getString("objectif"),
                                           ensembleDeResultats.getInt("taille"),
                                           ensembleDeResultats.getInt("poids"),
                                           ensembleDeResultats.getDate("date_de_naissance"),
                                           ensembleDeResultats.getString("sexe"),
                                           ensembleDeResultats.getInt("ref_coach"));
        }
        else {
            return null;
        }

    }

    public static Client obtenir(int reference) throws SQLException {
        String requete = "SELECT * FROM clients WHERE ref_client = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setInt(1, reference);

        ResultSet ensembleDeResultats = declaration.executeQuery();

        if(ensembleDeResultats.next())
        {
            Utilisateur utilisateur = ServiceUtilisateur.obtenir(ensembleDeResultats.getInt("ref_utilisateur"));
            return new Client(utilisateur, ensembleDeResultats.getInt("ref_client"),
                    ensembleDeResultats.getString("objectif"),
                    ensembleDeResultats.getInt("taille"),
                    ensembleDeResultats.getInt("poids"),
                    ensembleDeResultats.getDate("date_de_naissance"),
                    ensembleDeResultats.getString("sexe"),
                    ensembleDeResultats.getInt("ref_coach"));
        }
        else {
            return null;
        }

    }

    public static Client mettreAJour(Client client) throws SQLException {
        ServiceUtilisateur.mettreAJour(client);
        String requete = "UPDATE clients SET  objectif = ? , taille = ? , poids = ? , date_de_naissance = ? , sexe = ? , ref_coach = ? , ref_utilisateur = ? WHERE ref_client = ?";
        PreparedStatement declaration = MaConnexion.obtenirConnexion().prepareStatement(requete);
        declaration.setString(1, client.getObjectif());
        declaration.setInt(2, client.getTaille());
        declaration.setInt(3, client.getPoids());
        declaration.setDate(4, new java.sql.Date(client.getDate_de_naissance().getTime()));
        declaration.setString(5, client.getSexe());
        System.out.println(client.getRef_coach());
        //declaration.setInt(6, client.getRef_coach());
        declaration.setObject(6, client.getRef_coach() == 0 ? null : Integer.valueOf(client.getRef_coach()), Types.INTEGER);
        declaration.setInt(7, client.getRef_utilisateur());
        declaration.setInt(8, client.getRef_client());


        declaration.executeUpdate();

        return obtenir( client.getRef_client() );
    }
}
