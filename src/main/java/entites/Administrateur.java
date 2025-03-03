package entites;

public class Administrateur extends Utilisateur{
    private int ref_administrateur;

    public Administrateur(Utilisateur utilisateur, int ref_administrateur) {
        super(utilisateur.getRef_utilisateur(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getEmail(), utilisateur.getMot_de_passe(), utilisateur.getType_utilisateur());
        this.ref_administrateur = ref_administrateur;
    }

    public int getRef_administrateur() {
        return ref_administrateur;
    }

    public void setRef_administrateur(int ref_administrateur) {
        this.ref_administrateur = ref_administrateur;
    }

    @Override
    public String toString() {
        return "Administrateur{" +
                "ref_administrateur=" + ref_administrateur +
                '}';
    }
}
