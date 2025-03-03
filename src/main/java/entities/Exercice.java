package entities;

public class Exercice {

    private int id;
    private String nom;
    private String description;
    private double caloriesBrulees;
    private int duree; // en minutes
    private Niveau niveauDifficulte;
    private String typeExercice;
    private Regime regime; // Clé étrangère vers la table Regime

    // Constructeur sans ID (pour insertion)
    public Exercice(String nom, String description, double caloriesBrulees, int duree, Niveau niveauDifficulte, String typeExercice, Regime regime) {
        this.nom = nom;
        this.description = description;
        this.caloriesBrulees = caloriesBrulees;
        this.duree = duree;
        this.niveauDifficulte = niveauDifficulte;
        this.typeExercice = typeExercice;
        this.regime = regime;
    }

    // Constructeur avec ID (pour récupération depuis la base)
    public Exercice(int id, String nom, String description, double caloriesBrulees, int duree, Niveau niveauDifficulte, String typeExercice, Regime regime) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.caloriesBrulees = caloriesBrulees;
        this.duree = duree;
        this.niveauDifficulte = niveauDifficulte;
        this.typeExercice = typeExercice;
        this.regime = regime;
    }

    public Exercice() {
    }

    // Getters et Setters
    public int getid() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCaloriesBrulees() {
        return caloriesBrulees;
    }

    public void setCaloriesBrulees(double caloriesBrulees) {
        if (caloriesBrulees < 0){
            throw new IllegalArgumentException("Les calories brulees doivent etre positives.");
        }
        this.caloriesBrulees = caloriesBrulees;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        if (duree < 0){
            throw new IllegalArgumentException("La duree doit etre positive.");
        }
        this.duree = duree;
    }

    public Niveau getNiveauDifficulte() {
        return niveauDifficulte;
    }

    public void setNiveauDifficulte(Niveau niveauDifficulte) {
        this.niveauDifficulte = niveauDifficulte;
    }

    public String getTypeExercice() {
        return typeExercice;
    }


    public void setTypeExercice(String typeExercice) {
        this.typeExercice = typeExercice;
    }

    public Regime getRegime() {
        return regime;
    }

    public void setRegime(Regime regime) {
        this.regime = regime;
    }

    @Override
    public String toString() {
        return "Exercice{" +
                "nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", caloriesBrulees=" + caloriesBrulees +
                ", duree=" + duree +
                ", niveauDifficulte=" + niveauDifficulte +
                ", typeExercice='" + typeExercice + '\'' +
                ", regime=" + (regime != null ? regime.getNom() : "null" )+
                '}';
    }
}
