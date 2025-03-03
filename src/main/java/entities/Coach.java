package entities;

import java.util.ArrayList;
import java.util.List;

public class Coach {
    private int refCoach;
    private String description;
    private String sexe;
    private String dateDeNaissance;
    private List<Regime> regimesGeres = new ArrayList<>();

    // Constructeur par défaut
    public Coach() {}

    // Constructeur avec paramètres
    public Coach(int refCoach, String description, String sexe, String dateDeNaissance) {
        this.refCoach = refCoach;
        this.description = description;
        this.sexe = sexe;
        this.dateDeNaissance = dateDeNaissance;
    }

    public Coach(Integer value) {
    }

    // Méthode pour ajouter un régime géré par le coach
    public void addRegime(Regime regime) {
        this.regimesGeres.add(regime);
        regime.setRefCoach(this.refCoach); // Associer le coach au régime
    }

    // Getters et Setters
    public int getRefCoach() {
        return refCoach;
    }

    public void setRefCoach(int refCoach) {
        this.refCoach = refCoach;
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

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public List<Regime> getRegimesGeres() {
        return regimesGeres;
    }

    public void setRegimesGeres(List<Regime> regimesGeres) {
        this.regimesGeres = regimesGeres;
    }

    public Coach(String description) {

        this.description = description;
    }

    @Override
    public String toString() {
        // Construire la liste des régimes gérés
        String regimesInfo = "";
        if (!regimesGeres.isEmpty()) {
            regimesInfo = "[";
            for (Regime regime : regimesGeres) {
                regimesInfo += regime.toString() + ", ";
            }
            regimesInfo += "]";
        } else {
            regimesInfo = "Aucun régime géré";
        }


        // Construire la chaîne finale
        return "Coach{" +
                "refCoach=" + refCoach +
                ", description='" + description + '\'' +
                ", sexe='" + sexe + '\'' +
                ", dateDeNaissance='" + dateDeNaissance + '\'' +
                ", regimesGeres=" + regimesInfo +
                "}";
    }
}