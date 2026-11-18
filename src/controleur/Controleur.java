package controleur;

import modele.CoupInvalideException;
import modele.Jeu;
import modele.Joueur;
import modele.JoueurIA;
import modele.Morpion;
import modele.Puissance4;
import vue.Ihm;

 
public class Controleur {
    private Ihm ihm;
    private Joueur joueur1;
    private Joueur joueur2;
    private Jeu jeu;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
    }
     
    public void lancerJeu() {

        String nomJ1 = ihm.saisirNomJoueur(1);
        joueur1 = new Joueur(nomJ1);
        
        boolean contreIA = ihm.choisirContreIA();
        if (contreIA) {
            joueur2 = new JoueurIA();
        } else {
            String nomJ2 = ihm.saisirNomJoueur(2);
            joueur2 = new Joueur(nomJ2);
        }
            
        int choixJeu = ihm.choisirJeu();
    
        if (choixJeu == 1) {
            jeu = new Morpion(joueur1, joueur2);
        } else {
            jeu = new Puissance4(joueur1, joueur2);
        }
        
        ihm.afficherMessage("Vous avez choisi : " + jeu.getNomJeu());
        
        boolean continuer = true;
        while (continuer) {
            jouerPartie();
            continuer = ihm.demanderRejouer();
        }
        
        
        ihm.afficherBilan(
            joueur1.getNom(), joueur1.getNbPartiesGagnees(),
            joueur2.getNom(), joueur2.getNbPartiesGagnees()
        );
    }

    
    
     
    private void jouerPartie() {
        
        jeu.initialiserPartie();
        
        boolean partieTerminee = false;
        
        int difficulte = -1;
        if (joueur2.estIA()) {
            difficulte = ihm.choisirDifficulteIA();
        }
        
        ihm.afficherMessage("\nNouvelle partie de " + jeu.getNomJeu() );
        
        while (!partieTerminee) {
            
            ihm.afficherGrille(jeu.toString());
            
            
            boolean coupJoue = false;
            
            while (!coupJoue) {        
                try {
                    if (jeu.getJoueurCourant().estIA()) {
                        int[] coupIA = jeu.genererCoupIA(difficulte);
                        if (jeu instanceof Morpion) {
                            jeu.jouerCoup(coupIA[0], coupIA[1]);
                        } else {
                            jeu.jouerCoup(-1, coupIA[0]);
                        }
                        coupJoue = true;
                    } else {
                        
                    if (jeu instanceof Morpion) {
                        int[] coup = ihm.saisirCoupMorpion(
                            jeu.getJoueurCourant().getNom());
                        
                        jeu.jouerCoup(coup[0], coup[1]);
                    } else {
                        int colonne = ihm.saisirCoupPuissance4(
                            jeu.getJoueurCourant().getNom());
                        
                        jeu.jouerCoup(-1, colonne);
                    }
                    
                    } 
                    coupJoue = true; 
                    
                } catch (CoupInvalideException e) {
                    ihm.afficherMessage(e.getMessage());        
                }
            }
            
            if (jeu.aGagne()) {
                ihm.afficherGrille(jeu.toString());
                ihm.afficherMessage(
                    jeu.getJoueurCourant().getNom() + " a gagné la partie !");
                jeu.getJoueurCourant().incrementerVictoires();
                partieTerminee = true;
                
            } else if (jeu.estGrilleComplete()) {
                ihm.afficherGrille(jeu.toString());
                ihm.afficherMessage("Match nul ! La grille est pleine.");
                partieTerminee = true;
                
            } else {
                
                jeu.changerJoueur();
            }
        }
    }
}


