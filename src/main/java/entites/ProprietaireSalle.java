package entites;

public class ProprietaireSalle extends Utilisateur{
    private int ref_proprietaire_salle;

    public ProprietaireSalle(Utilisateur utilisateur, int ref_proprietaire_salle) {
        super(utilisateur.getRef_utilisateur(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getEmail(), utilisateur.getMot_de_passe(), utilisateur.getType_utilisateur());
        this.ref_proprietaire_salle = ref_proprietaire_salle;
    }

    public int getRef_proprietaire_salle() {
        return ref_proprietaire_salle;
    }

    public void setRef_proprietaire_salle(int ref_proprietaire_salle) {
        this.ref_proprietaire_salle = ref_proprietaire_salle;
    }

    @Override
    public String toString() {
        return "ProprietaireSalle{" +
                "ref_proprietaire_salle=" + ref_proprietaire_salle +
                '}';
    }
}
