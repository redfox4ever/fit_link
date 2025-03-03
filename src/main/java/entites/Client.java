package entites;

import java.util.Date;

public class Client extends Utilisateur{
    private int ref_client;
    private String objectif;
    private int taille;
    private int poids;
    private Date date_de_naissance;
    private String sexe;
    private int ref_coach;


    public Client(Utilisateur utilisateur, int ref_client, String objectif, int taille, int poids, Date date_de_naissance, String sexe, int ref_coach) {

        super( utilisateur.getRef_utilisateur(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getMot_de_passe(),
                utilisateur.getType_utilisateur() );

        this.ref_client = ref_client;
        this.objectif = objectif;
        this.taille = taille;
        this.poids = poids;
        this.date_de_naissance = date_de_naissance;
        this.sexe = sexe;
        this.ref_coach = ref_coach;

    }

    @Override
    public String toString() {
        return "Client{" +
                "ref_client=" + ref_client +
                ", objectif='" + objectif + '\'' +
                ", taille=" + taille +
                ", poids=" + poids +
                ", date_de_naissance=" + date_de_naissance +
                ", sexe='" + sexe + '\'' +
                ", ref_coach='" + ref_coach + '\'' +
                '}';
    }

    public int getRef_client() {
        return ref_client;
    }

    public void setRef_client(int ref_client) {
        this.ref_client = ref_client;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public Date getDate_de_naissance() {
        return date_de_naissance;
    }

    public void setDate_de_naissance(Date date_de_naissance) {
        this.date_de_naissance = date_de_naissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getRef_coach() {
        return ref_coach;
    }

    public void setRef_coach(int ref_coach) {
        this.ref_coach = ref_coach;
    }
}
