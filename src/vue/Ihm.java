package vue;

import java.util.Scanner;

 
public class Ihm {
    private Scanner scanner;     
    public Ihm() {
        this.scanner = new Scanner(System.in);
    }
     
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public void afficherGrille(String grille) {
        System.out.println(grille);
    }

    public String saisirNomJoueur(int numero) {
        System.out.print("Entrez le nom du joueur " + numero + " : ");
        return scanner.nextLine().trim();
    }

     
    public boolean choisirContreIA() {
        int choix = 0;
        while (choix != 1 && choix != 2) {
            System.out.println("Voulez-vous jouer :");
            System.out.println("1 - À deux joueurs");
            System.out.println("2 - Contre l'ordinateur (IA)");
            System.out.print("Votre choix (1 ou 2) : ");
            String saisie = scanner.nextLine().trim();
            try {
                choix = Integer.parseInt(saisie);
                if (choix != 1 && choix != 2) {
                    System.out.println("Erreur : veuillez taper 1 ou 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erreur : veuillez saisir un nombre (1 ou 2).");
            }
        }
        return choix == 2;
    }

    public int choisirDifficulteIA() {
        int choix = 0;
        while (choix != 1 && choix != 2) {
            System.out.println("Choisissez le niveau de l'IA :");
            System.out.println("1 - Facile");
            System.out.println("2 - Difficile");
            System.out.print("Votre choix (1 ou 2) : ");
            String saisie = scanner.nextLine().trim();
            try {
                choix = Integer.parseInt(saisie);
                if (choix != 1 && choix != 2) {
                    System.out.println("Erreur : veuillez taper 1 ou 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erreur : veuillez saisir un nombre (1 ou 2).");
            }
        }
        return choix;
    }

     
    public int choisirJeu() {
        int choix = 0;
        
        while (choix != 1 && choix != 2) {
            System.out.println("Choisissez un jeu :");
            System.out.println("  1 - Morpion");
            System.out.println("  2 - Puissance 4");
            System.out.print("Votre choix : ");
            
            
            if (scanner.hasNextInt()) {
                choix = scanner.nextInt();
                scanner.nextLine(); 
                
                if (choix != 1 && choix != 2) {
                    System.out.println("Choix invalide ! Entrez 1 ou 2.");
                }
            } else {
                System.out.println("Veuillez entrer un nombre (1 ou 2).");
                scanner.nextLine(); 
            }
        }
        
        return choix;
    }
    public int[] saisirCoupMorpion(String nomJoueur) {
        System.out.println(nomJoueur + " : à vous de jouer.");
        System.out.print("Entrez ligne et colonne (ex: 2 3) : ");
        
        while (true) {
            
            String saisie = scanner.nextLine().trim();
            String[] parties = saisie.split("\\s+");
            
            if (parties.length == 2) {
                try {
                    int ligne = Integer.parseInt(parties[0]);
                    int colonne = Integer.parseInt(parties[1]);
                    
                    
                    if (ligne >= 1 && ligne <= 3 && colonne >= 1 && colonne <= 3) {
                        
                        return new int[]{ligne - 1, colonne - 1};
                    }
                } catch (NumberFormatException e) {    
                }
            }
            
            System.out.print("Saisie invalide ! Entrez ligne (1-3) et colonne (1-3) : ");
        }
    }

     
    public int saisirCoupPuissance4(String nomJoueur) {
        System.out.println(nomJoueur + " : à vous de jouer.");
        System.out.print("Entrez le numéro de colonne (1 à 7) : ");
        
        while (true) {
            String saisie = scanner.nextLine().trim();
            
            try {
                int colonne = Integer.parseInt(saisie);
                
                if (colonne >= 1 && colonne <= 7) {
                    return colonne - 1; 
                }
            } catch (NumberFormatException e) {
                
            }
            
            System.out.print("Saisie invalide ! Entrez une colonne entre 1 et 7 : ");
        }
    }

     
    public boolean demanderRejouer() {
        System.out.print("Voulez-vous rejouer ? (o/n) : ");
        
        while (true) {
            String reponse = scanner.nextLine().trim().toLowerCase();
            
            if (reponse.equals("o") ) {
                return true;
            } else if (reponse.equals("n") ) {
                return false;
            }
            
            System.out.print("Répondez par 'o' (oui) ou 'n' (non) : ");
        }
    }
    
    public void afficherBilan(String nomJ1, int scoreJ1, String nomJ2, int scoreJ2) {
        System.out.println("\n========== BILAN FINAL ==========");
        System.out.println(nomJ1 + " : " + scoreJ1 + " victoire(s)");
        System.out.println(nomJ2 + " : " + scoreJ2 + " victoire(s)");
       
        
        if (scoreJ1 > scoreJ2) {
            System.out.println("Le vainqueur est " + nomJ1 + " ! Félicitations !");
        } else if (scoreJ2 > scoreJ1) {
            System.out.println("Le vainqueur est " + nomJ2 + " ! Félicitations !");
        } else {
            System.out.println("Égalité !");
        }
    }
}
