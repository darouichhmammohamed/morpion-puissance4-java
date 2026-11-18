package modele;

 
public class Morpion extends Jeu {
    public Morpion(Joueur joueur1, Joueur joueur2) {   
        super(joueur1, joueur2, 3, 3, 3);
    }
    @Override
    public void jouerCoup(int ligne, int colonne) throws CoupInvalideException {
        
        if (ligne < 0 || ligne >= nbLignes || colonne < 0 || colonne >= nbColonnes) {
            throw new CoupInvalideException(
                "Coordonnées invalides ! Ligne et colonne doivent être entre 1 et 3.");
        }
        
        
        if (grille[ligne][colonne] != ' ') {
            throw new CoupInvalideException(
                "Cette case est déjà occupée ! Choisissez une autre case.");
        }
        
        
        grille[ligne][colonne] = getSymboleCourant();
    }

     
    @Override
    public boolean aGagne() {
        char symbole = getSymboleCourant();
        
        
        
        for (int i = 0; i < nbLignes; i++) {
            if (grille[i][0] == symbole && grille[i][1] == symbole && grille[i][2] == symbole) {
                return true;
            }
        }
        
        
        for (int j = 0; j < nbColonnes; j++) {
            if (grille[0][j] == symbole && grille[1][j] == symbole && grille[2][j] == symbole) {
                return true;
            }
        }
        
        
        if (grille[0][0] == symbole && grille[1][1] == symbole && grille[2][2] == symbole) {
            return true;
        }
        
        
        if (grille[0][2] == symbole && grille[1][1] == symbole && grille[2][0] == symbole) {
            return true;
        }
        
        return false;
    }

     
    @Override
    public String getNomJeu() {
        return "Morpion";
    }

     
    @Override
    public char getSymboleJoueur1() {
        return 'X';
    }

     
    @Override
    public char getSymboleJoueur2() {
        return 'O';
    }

     
    @Override
    public String toString() {
        String affichage = "";
        
        
        affichage += "    1   2   3\n";
        affichage += "  +---+---+---+\n";
        
        for (int i = 0; i < nbLignes; i++) {
            
            affichage += (i + 1) + " ";
            for (int j = 0; j < nbColonnes; j++) {
                affichage += "| " + grille[i][j] + " ";
            }
            affichage += "|\n";
            affichage += "  +---+---+---+\n";
        }
        
        return affichage;
    }

    @Override
    public int[] genererCoupIA(int difficulte) {
        if (difficulte == 1) {
            java.util.List<int[]> casesVides = new java.util.ArrayList<>();
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (grille[i][j] == ' ') {
                        casesVides.add(new int[]{i, j});
                    }
                }
            }
            java.util.Random rand = new java.util.Random();
            return casesVides.get(rand.nextInt(casesVides.size()));
        } else {
            if (compteCasesVides() <= 4) {
                return calculerMinMax();
            } else {
                return calculerHeuristique();
            }
        }
    }

    private int compteCasesVides() {
        int c = 0;
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (grille[i][j] == ' ') c++;
            }
        }
        return c;
    }

    private int[] calculerHeuristique() {
        char moi = getSymboleCourant();
        char adversaire = (moi == getSymboleJoueur1()) ? getSymboleJoueur2() : getSymboleJoueur1();

        int[] coup = chercherCoupGagnant(moi);
        if (coup != null) return coup;

        coup = chercherCoupGagnant(adversaire);
        if (coup != null) return coup;

        if (grille[1][1] == ' ') return new int[]{1, 1};

        int[][] coins = {{0,0}, {0,2}, {2,0}, {2,2}};
        for(int[] coin : coins) {
            if (grille[coin[0]][coin[1]] == ' ') return coin;
        }

        int[][] cotes = {{0,1}, {1,0}, {1,2}, {2,1}};
        for(int[] cote : cotes) {
            if (grille[cote[0]][cote[1]] == ' ') return cote;
        }

        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (grille[i][j] == ' ') return new int[]{i,j};
            }
        }
        return new int[]{0,0};
    }

    private int[] chercherCoupGagnant(char symbole) {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (grille[i][j] == ' ') {
                    grille[i][j] = symbole;
                    boolean gagne = verifierVictoirePour(symbole);
                    grille[i][j] = ' ';
                    if (gagne) return new int[]{i,j};
                }
            }
        }
        return null;
    }

    private boolean verifierVictoirePour(char symbole) {
        for (int i = 0; i < 3; i++) {
            if (grille[i][0] == symbole && grille[i][1] == symbole && grille[i][2] == symbole) return true;
            if (grille[0][i] == symbole && grille[1][i] == symbole && grille[2][i] == symbole) return true;
        }
        if (grille[0][0] == symbole && grille[1][1] == symbole && grille[2][2] == symbole) return true;
        if (grille[0][2] == symbole && grille[1][1] == symbole && grille[2][0] == symbole) return true;
        return false;
    }

    private int[] calculerMinMax() {
        int meilleurScore = -10000;
        int[] meilleurCoup = new int[2];
        char moi = getSymboleCourant();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grille[i][j] == ' ') {
                    grille[i][j] = moi;
                    int score = minMax(false, moi);
                    grille[i][j] = ' ';
                    if (score > meilleurScore) {
                        meilleurScore = score;
                        meilleurCoup[0] = i;
                        meilleurCoup[1] = j;
                    }
                }
            }
        }
        return meilleurCoup;
    }

    private int minMax(boolean isMaximizing, char moi) {
        char adversaire = (moi == getSymboleJoueur1()) ? getSymboleJoueur2() : getSymboleJoueur1();

        if (verifierVictoirePour(moi)) return 1;
        if (verifierVictoirePour(adversaire)) return -1;
        if (compteCasesVides() == 0) return 0;

        if (isMaximizing) {
            int meilleurScore = -10000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (grille[i][j] == ' ') {
                        grille[i][j] = moi;
                        int score = minMax(false, moi);
                        grille[i][j] = ' ';
                        meilleurScore = max(score, meilleurScore);
                    }
                }
            }
            return meilleurScore;
        } else {
            int meilleurScore = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (grille[i][j] == ' ') {
                        grille[i][j] = adversaire;
                        int score = minMax(true, moi);
                        grille[i][j] = ' ';
                        meilleurScore = min(score, meilleurScore);
                    }
                }
            }
            return meilleurScore;
        }
    }
    
    private int max(int a, int b) { return a > b ? a : b; }
    private int min(int a, int b) { return a < b ? a : b; }
}
