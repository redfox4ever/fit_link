package entites;

public class Utilisateur {
    private int ref_utilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String mot_de_passe;
    private String type_utilisateur;


    public Utilisateur(int ref_utilisateur, String nom, String prenom, String email, String mot_de_passe, String type_utilisateur) {
        this.ref_utilisateur = ref_utilisateur;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.type_utilisateur = type_utilisateur;
    }

    public int getRef_utilisateur() {
        return ref_utilisateur;
    }

    public void setRef_utilisateur(int ref_utilisateur) {
        this.ref_utilisateur = ref_utilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }



    public String getType_utilisateur() {
        return type_utilisateur;
    }

    public void setType_utilisateur(String type_utilisateur) {
        this.type_utilisateur = type_utilisateur;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "ref_utilisateur=" + ref_utilisateur +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", type_utilisateur='" + type_utilisateur + '\'' +
                '}';
    }
}
