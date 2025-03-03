package utilitaires;

import entites.Utilisateur;

public class SessionUtilisateur {
    private static SessionUtilisateur instance;
    private Utilisateur utilisateurActuel;

    private SessionUtilisateur() {

    }

    public static synchronized SessionUtilisateur getInstance() {
        if (instance == null) {
            instance = new SessionUtilisateur();
        }
        return instance;
    }

    public void setUtilisateurActuel(Utilisateur utilisateur) {
        System.out.println("Utilisateur actuel est: " + utilisateur);
        this.utilisateurActuel = utilisateur;
    }

    public Utilisateur getUtilisateurActuel() {
        return utilisateurActuel;
    }

    public void clearSession() {
        this.utilisateurActuel = null;
    }
}