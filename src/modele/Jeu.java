package modele;

 
public abstract class Jeu {
    protected char[][] grille;
    protected int nbLignes;
    protected int nbColonnes;
    protected Joueur joueur1;
    protected Joueur joueur2;
    protected Joueur joueurCourant;
    protected int nbAlignementPourGagner;

    public Jeu(Joueur joueur1, Joueur joueur2, int nbLignes, int nbColonnes, 
               int nbAlignementPourGagner) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.nbAlignementPourGagner = nbAlignementPourGagner;
        this.grille = new char[nbLignes][nbColonnes];
        this.joueurCourant = joueur1; 
    } 
    public void initialiserPartie() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                grille[i][j] = ' ';
            }
        }
        joueurCourant = joueur1;
    }
    public boolean estGrilleComplete() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                if (grille[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    public void changerJoueur() {
        if (joueurCourant == joueur1) {
            joueurCourant = joueur2;
        } else {
            joueurCourant = joueur1;
        }
    }
    public char getSymboleCourant() {
        if (joueurCourant == joueur1) {
            return getSymboleJoueur1();
        } else {
            return getSymboleJoueur2();
        }
    }
    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public Joueur getJoueur1() {
        return joueur1;
    }
    public Joueur getJoueur2() {
        return joueur2;
    }
    public int getNbLignes() {
        return nbLignes;
    }
    public int getNbColonnes() {
        return nbColonnes;
    }

    public abstract void jouerCoup(int ligne, int colonne) throws CoupInvalideException;
    public abstract boolean aGagne();
    public abstract String getNomJeu();
    public abstract char getSymboleJoueur1();
    public abstract char getSymboleJoueur2();

     
    @Override
    public abstract String toString();
    public abstract int[] genererCoupIA(int difficulte);
}
