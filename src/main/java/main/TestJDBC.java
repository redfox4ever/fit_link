package main;

import entities.Coach;
import entities.Niveau;
import entities.Regime;
import entities.Exercice;
import services.RegimeService;
import services.ExerciceService;
import connection.MyConnection;
import java.sql.SQLException;
import java.util.List;

public class TestJDBC {
    public static void main(String[] args) {
        // Vérifie la connexion à la base de données
        MyConnection.getInstance();

        // Initialisation des services
        RegimeService regimeService = new RegimeService();
        ExerciceService exerciceService = new ExerciceService();

        try {
            // Étape 1 : Créer un coach
            Coach coach = new Coach(1, "Expert en cardio", "homme", "1985-06-15");


// Étape 2 : Créer un régime lié au coach
            Regime regimeCardio = new Regime("Régime Cardio", "Améliore l'endurance cardiovasculaire.", "Hypocalorique");
            regimeCardio.setRefCoach(coach.getRefCoach()); // Associer le coach au régime via sa référence
            regimeService.create(regimeCardio);
            System.out.println("✅ Régime ajouté avec succès ! ID généré : " + regimeCardio.getId());

// Étape 3 : Ajouter des exercices au régime
            Exercice exercice4 = new Exercice(
                    "Squats",
                    "Course à pied pour améliorer la résistance cardiovasculaire.",
                    300,
                    45,
                    Niveau.MOYEN,
                    "Cardio",
                    regimeCardio
            );
            Exercice exercice5 = new Exercice(
                    "Elliptical Trainer",
                    "Pédaler pour renforcer les jambes et améliorer l'endurance.",
                    250,
                    60,
                    Niveau.FACILE,
                    "Cardio",
                    regimeCardio
            );
            Exercice exercice6 = new Exercice(
                    "Treadmill",
                    "Nager pour travailler l'ensemble du corps tout en améliorant la cardio.",
                    400,
                    30,
                    Niveau.DIFFICILE,
                    "Cardio",
                    regimeCardio
            );

            exerciceService.create(exercice4);
            System.out.println("✅ Exercice '" + exercice4.getNom() + "' ajouté avec succès !");
            exerciceService.create(exercice5);
            System.out.println("✅ Exercice '" + exercice5.getNom() + "' ajouté avec succès !");
            exerciceService.create(exercice6);
            System.out.println("✅ Exercice '" + exercice6.getNom() + "' ajouté avec succès !");

            // Étape 4 : Mettre à jour un régime
            regimeCardio.setNom("Nouveau Régime Cardio"); // Modifier le nom du régime
            regimeCardio.setDescription("Nouvelle description du régime."); // Modifier la description
            regimeService.update(regimeCardio);
            System.out.println("✅ Régime mis à jour avec succès.");

            // Étape 5 : Mettre à jour un exercice
            exercice4.setNom("Nouveaux Squats"); // Modifier le nom de l'exercice
            exercice4.setDescription("Description mise à jour pour les squats."); // Modifier la description
            exercice4.setCaloriesBrulees(350); // Augmenter les calories brûlées
            exerciceService.update(exercice4);
            System.out.println("✅ Exercice '" + exercice4.getNom() + "' mis à jour avec succès.");

            // Étape 6 : Supprimer un régime
            Regime regimeASupprimer = new Regime();
            regimeASupprimer.setId(2); // Spécifiez l'ID du régime à supprimer
            regimeService.delete(regimeASupprimer);
            System.out.println("✅ Régime avec ID=" + regimeASupprimer.getId() + " supprimé avec succès.");

            // Étape 7 : Supprimer un exercice
            Exercice exerciceASupprimer = new Exercice();
            exerciceASupprimer.setId(29); // Spécifiez l'ID du régime à supprimer
            exerciceService.delete(exerciceASupprimer);
            System.out.println("✅ Exercice avec ID=" + exerciceASupprimer.getid() + " supprimé avec succès.");

            // Étape 8 : Récupérer et afficher tous les régimes avec leurs exercices associés
            List<Regime> regimes = regimeService.readAll();
            System.out.println("\n📌 Liste des régimes enregistrés :");
            for (Regime r : regimes) {
                System.out.println(r);
                if (r.getCoach() != null) {
                    System.out.println("  Coach : " + r.getCoach().getDescription());
                } else {
                    System.out.println("  Aucun coach associé.");
                }
                List<Exercice> exercices = r.getExercices();
                if (!exercices.isEmpty()) {
                    System.out.println("  📋 Exercices associés :");
                    for (Exercice e : exercices) {
                        System.out.println("    - " + e);
                    }
                } else {
                    System.out.println("  Aucun exercice associé.");
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
        }
    }
}