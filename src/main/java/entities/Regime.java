package entities;

import java.util.ArrayList;
import java.util.List;

public class Regime {
    private int id;
    private String nom;
    private String description;
    private String typeRegime;
    private List<Exercice> exercices =  new ArrayList<>();
    private int refCoach;
    private Coach coach;

    // Constructeur sans id
    public Regime(String nom, String description, String typeRegime) {
        this.nom = nom;
        this.description = description;
        this.typeRegime = typeRegime;
        this.exercices = new ArrayList<>();
    }

    public Regime(String nom, String description, String typeRegime, int id) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.typeRegime = typeRegime;
        this.exercices = new ArrayList<>();
    }

    @Override
    public String toString() {
        String coachInfo = "";
        if (coach != null) {
            coachInfo = ", coach='" + coach.getDescription() + "'";
        } else if (refCoach > 0) {
            coachInfo = ", refCoach=" + refCoach;
        }

        return "Regime{id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + typeRegime + '\'' +
                ", exercices=" + exercices.size() +
                coachInfo +
                "}";
    }


    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public int getRefCoach() {
        return refCoach;
    }

    public void setRefCoach(int refCoach) {
        this.refCoach = refCoach;
    }

    public Regime() {
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getTypeRegime() {
        return typeRegime;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTypeRegime(String typeRegime) {
        this.typeRegime = typeRegime;
    }

    public List<Exercice> getExercices() {
        return exercices;
    }

    public void setExercices(List<Exercice> exercices) {
        this.exercices = exercices;
    }

    public void addExercice(Exercice exercice) {
        this.exercices.add(exercice);
        exercice.setRegime(this); // Associer l'exercice au régime
    }
    public Regime(int id) {
        this.id = id;
    }

}
