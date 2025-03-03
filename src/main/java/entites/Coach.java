package entites;

import java.util.Date;

public class Coach extends Utilisateur {
    private int ref_coach;
    private String description;
    private String sexe;
    private Date date_de_naissance;


    public Coach(Utilisateur utilisateur, int ref_coach, String description, String sexe, Date date_de_naissance) {

        super( utilisateur.getRef_utilisateur(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getMot_de_passe(),
                utilisateur.getType_utilisateur() );

        this.ref_coach = ref_coach;
        this.description = description;
        this.sexe = sexe;
        this.date_de_naissance = date_de_naissance;

    }

    public int getRef_coach() {
        return ref_coach;
    }

    public void setRef_coach(int ref_coach) {
        this.ref_coach = ref_coach;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public Date getDate_de_naissance() {
        return date_de_naissance;
    }

    public void setDate_de_naissance(Date date_de_naissance) {
        this.date_de_naissance = date_de_naissance;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "ref_coach=" + ref_coach +
                ", description='" + description + '\'' +
                ", sexe='" + sexe + '\'' +
                ", date_de_naissance=" + date_de_naissance +
                '}';
    }
}
