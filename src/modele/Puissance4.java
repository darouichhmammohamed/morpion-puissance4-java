package modele;

 
public class Puissance4 extends Jeu {

    public Puissance4(Joueur joueur1, Joueur joueur2) {
        super(joueur1, joueur2, 6, 7, 4);
    }
 
    private int trouverLigneDisponible(int colonne) {
        
        for (int i = nbLignes - 1; i >= 0; i--) {
            if (grille[i][colonne] == ' ') {
                return i; 
            }
        }
        return -1; 
    }

    @Override
    public void jouerCoup(int ligne, int colonne) throws CoupInvalideException {
        
        if (colonne < 0 || colonne >= nbColonnes) {
            throw new CoupInvalideException(
                "Colonne invalide ! Choisissez une colonne entre 1 et 7.");
        }
        
        
        int ligneDisponible = trouverLigneDisponible(colonne);
        if (ligneDisponible == -1) {
            throw new CoupInvalideException(
                "Cette colonne est pleine ! Choisissez une autre colonne.");
        }
        
        
        grille[ligneDisponible][colonne] = getSymboleCourant();
    }
    @Override
    public boolean aGagne() {
        char symbole = getSymboleCourant();
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                if (grille[i][j] == symbole) {
                    if (verifierAlignement(i, j, 0, 1)) {
                        return true;
                    }
                    if (verifierAlignement(i, j, 1, 0)) {
                        return true;
                    }
                    if (verifierAlignement(i, j, 1, 1)) {
                        return true;
                    }
                    if (verifierAlignement(i, j, 1, -1)) {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

     
    private boolean verifierAlignement(int ligne, int colonne, 
                                        int deltaLigne, int deltaColonne) {
        char symbole = grille[ligne][colonne];
        for (int k = 1; k < nbAlignementPourGagner; k++) {
            int nouvelleLigne = ligne + k * deltaLigne;
            int nouvelleColonne = colonne + k * deltaColonne;
            if (nouvelleLigne < 0 || nouvelleLigne >= nbLignes 
                || nouvelleColonne < 0 || nouvelleColonne >= nbColonnes) {
                return false;
            }
            if (grille[nouvelleLigne][nouvelleColonne] != symbole) {
                return false;
            }
        }
        
        return true; 
    }

     
    @Override
    public String getNomJeu() {
        return "Puissance 4";
    }

     
    @Override
    public char getSymboleJoueur1() {
        return 'R';
    }

     
    @Override
    public char getSymboleJoueur2() {
        return 'J';
    }

     
    @Override
    public String toString() {
        String affichage = "";
        
        
        affichage += "  ";
        for (int j = 0; j < nbColonnes; j++) {
            affichage += "  " + (j + 1) + " ";
        }
        affichage += "\n";
        affichage += ligneSeparateur();
        for (int i = 0; i < nbLignes; i++) {
            affichage += "| ";
            for (int j = 0; j < nbColonnes; j++) {
                affichage += grille[i][j] + " | ";
            }
            affichage += "\n";
            affichage += ligneSeparateur();
        }
        
        return affichage;
    }

     
    private String ligneSeparateur() {
        String sep = "";
        for (int j = 0; j < nbColonnes; j++) {
            sep += "+---";
        }
        sep += "+\n";
        return sep;
    }

    @Override 
    public int[] genererCoupIA(int difficulte) { 
        if (difficulte == 1) {
            java.util.List<Integer> c = new java.util.ArrayList<>();
            for (int i = 0; i < nbColonnes; i++) { 
                if (grille[0][i] == ' ') {
                    c.add(i);
                }
            } 
            java.util.Random rand = new java.util.Random();
            return new int[]{c.get(rand.nextInt(c.size())), 0}; 
        } else {
            return genererCoupIADifficile();
        }
    }

    private int[] genererCoupIADifficile() {
        int meilleurScore = -10000;
        java.util.List<Integer> meilleuresColonnes = new java.util.ArrayList<>();
        
        char moi = getSymboleCourant();
        char adversaire = (moi == getSymboleJoueur1()) ? getSymboleJoueur2() : getSymboleJoueur1();

        for (int c = 0; c < nbColonnes; c++) {
            if (grille[0][c] == ' ') {
                int score = 0;
                
                if (c == 3) score += 5;
                else if (c == 2 || c == 4) score += 3;
                else if (c == 1 || c == 5) score += 2;
                else score += 1;
                
                int l = trouverLigneDisponible(c);
                
                grille[l][c] = adversaire;
                if (verifierVictoirePour(adversaire)) {
                    score += 900;
                }
                grille[l][c] = ' ';
                
                grille[l][c] = moi;
                if (verifierVictoirePour(moi)) {
                    score += 1000;
                } else {
                    score += evaluerAlignements(l, c, moi);
                }
                grille[l][c] = ' ';
                
                if (score > meilleurScore) {
                    meilleurScore = score;
                    meilleuresColonnes.clear();
                    meilleuresColonnes.add(c);
                } else if (score == meilleurScore) {
                    meilleuresColonnes.add(c);
                }
            }
        }
        
        java.util.Random rand = new java.util.Random();
        int rep = meilleuresColonnes.get(rand.nextInt(meilleuresColonnes.size()));
        return new int[]{rep, 0};
    }

    private boolean verifierVictoirePour(char symbole) {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                if (grille[i][j] == symbole) {
                    if (j + 3 < nbColonnes && 
                        grille[i][j+1] == symbole && 
                        grille[i][j+2] == symbole && 
                        grille[i][j+3] == symbole) {
                        return true;
                    }
                    if (i + 3 < nbLignes && 
                        grille[i+1][j] == symbole && 
                        grille[i+2][j] == symbole && 
                        grille[i+3][j] == symbole) {
                        return true;
                    }
                    if (i + 3 < nbLignes && j + 3 < nbColonnes && 
                        grille[i+1][j+1] == symbole && 
                        grille[i+2][j+2] == symbole && 
                        grille[i+3][j+3] == symbole) {
                        return true;
                    }
                    if (i + 3 < nbLignes && j - 3 >= 0 && 
                        grille[i+1][j-1] == symbole && 
                        grille[i+2][j-2] == symbole && 
                        grille[i+3][j-3] == symbole) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int evaluerAlignements(int l, int c, char symbole) {
        int score = 0;
        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};

        for (int[] direction : directions) {
            int dl = direction[0];
            int dc = direction[1];

            int avant = 0;
            while (avant < 3) {
                int li = l + (avant + 1) * dl;
                int co = c + (avant + 1) * dc;
                if (li < 0 || li >= nbLignes || co < 0 || co >= nbColonnes || grille[li][co] != symbole) {
                    break;
                }
                avant++;
            }

            int arriere = 0;
            while (arriere < 3) {
                int li = l - (arriere + 1) * dl;
                int co = c - (arriere + 1) * dc;
                if (li < 0 || li >= nbLignes || co < 0 || co >= nbColonnes || grille[li][co] != symbole) {
                    break;
                }
                arriere++;
            }

            int total = 1 + avant + arriere;
            int lApres = l + (avant + 1) * dl;
            int cApres = c + (avant + 1) * dc;
            int lAvant = l - (arriere + 1) * dl;
            int cAvant = c - (arriere + 1) * dc;
            boolean extremiteLibre =
                    (lApres >= 0 && lApres < nbLignes && cApres >= 0 && cApres < nbColonnes
                            && grille[lApres][cApres] == ' ')
                    || (lAvant >= 0 && lAvant < nbLignes && cAvant >= 0 && cAvant < nbColonnes
                            && grille[lAvant][cAvant] == ' ');

            if (total == 3 && extremiteLibre) {
                score += 100;
            } else if (total == 2 && extremiteLibre) {
                score += 10;
            }
        }

        return score;
    }
}
