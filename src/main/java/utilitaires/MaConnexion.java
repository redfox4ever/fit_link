package utilitaires;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {

    private static final String url = "jdbc:mysql://localhost:3306/fitlink";
    private static final String utilisateur = "root";
    private static final String mot_de_passe = "";
    private static Connection connexion;

    public static Connection obtenirConnexion(){

        if(connexion == null){
            try {
                connexion = DriverManager.getConnection(url, utilisateur, mot_de_passe);
                System.out.println("Connexion établie!");
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return connexion;
    }

}