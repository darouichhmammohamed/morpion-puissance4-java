package modele;

 
public class Joueur {
    private String nom;
    private int nbPartiesGagnees;
    public Joueur(String nom) {
        this.nom = nom;
        this.nbPartiesGagnees = 0;
    }
    public String getNom() {
        return nom;
    }
    public int getNbPartiesGagnees() {
        return nbPartiesGagnees;
    }
    public void incrementerVictoires() {
        nbPartiesGagnees++;
    }
    @Override
    public String toString() {
        return nom + " (" + nbPartiesGagnees + " victoire(s))";
    }
    public boolean estIA() {
        return false;
    }
}
