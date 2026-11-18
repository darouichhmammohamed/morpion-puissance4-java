# 🎮 Morpion & Puissance 4 – Projet ACA (Itération 3)

Application **Java en console** qui permet de jouer au **Morpion** et au **Puissance 4**, à deux joueurs ou contre une **intelligence artificielle** (niveau facile ou difficile).

Projet réalisé en binôme dans le cadre de l'**ACA – Itération 3** (TP B).

| | |
|---|---|
| **Auteurs** | Houcine Sfar, Aziz Hmem |
| **Groupe** | TP B |
| **Langage** | Java (sans bibliothèque externe) |
| **Architecture** | Modèle – Vue – Contrôleur (MVC) |

## Fonctionnalités

- Choix du jeu : **Morpion** (grille 3×3, 3 alignés) ou **Puissance 4** (grille 6×7, 4 alignés)
- Mode **2 joueurs** ou **contre l'ordinateur**
- IA en deux niveaux :
  - **Facile** : joue un coup aléatoire parmi les coups possibles
  - **Difficile** :
    - *Morpion* : stratégie (gagner, bloquer, centre, coins, côtés), puis algorithme **MinMax** quand il reste 4 cases vides ou moins
    - *Puissance 4* : évaluation de chaque colonne (gagner, bloquer l'adversaire, alignements ouverts, préférence pour le centre)
- Vérification des coups : case déjà occupée, colonne pleine, coordonnées hors de la grille (`CoupInvalideException`)
- Saisies sécurisées : une saisie invalide est redemandée sans arrêter le programme
- Plusieurs parties à la suite, puis **bilan final** des victoires

## Architecture (MVC)

```
src/
├── main/
│   └── Main.java                    Point d'entrée : crée la vue et le contrôleur
├── controleur/
│   └── Controleur.java              Déroulement des parties (tours, victoire, match nul, rejouer)
├── vue/
│   └── Ihm.java                     Affichage et saisies au clavier (Scanner)
└── modele/
    ├── Jeu.java                     Classe abstraite : grille, joueurs, tour courant
    ├── Morpion.java                 Règles du Morpion + IA
    ├── Puissance4.java              Règles du Puissance 4 + IA
    ├── Joueur.java                  Nom et nombre de victoires
    ├── JoueurIA.java                Joueur contrôlé par l'ordinateur
    └── CoupInvalideException.java   Exception levée pour un coup interdit
```

Le diagramme de classes de conception est disponible dans
[`DiagrammeDeClassesConception-Itération_3.png`](DiagrammeDeClassesConception-Itération_3.png).

### Notions de programmation objet utilisées
- **Héritage** : `Morpion` et `Puissance4` héritent de la classe abstraite `Jeu` ; `JoueurIA` hérite de `Joueur`
- **Polymorphisme** : le contrôleur manipule un `Jeu` sans savoir s'il s'agit d'un Morpion ou d'un Puissance 4 (`jouerCoup`, `aGagne`, `genererCoupIA`…)
- **Méthodes abstraites** : chaque jeu définit ses propres règles et son propre affichage
- **Exceptions personnalisées** : `CoupInvalideException`
- **Séparation des responsabilités** : le modèle ne fait aucun affichage, la vue ne contient aucune règle du jeu

## Compilation et lancement

Prérequis : un **JDK** (Java 17 ou plus).

Depuis le dossier du projet :

```bash
javac -encoding UTF-8 -d out src/main/Main.java src/controleur/*.java src/vue/*.java src/modele/*.java
java -cp out main.Main
```

## Exemple de partie

```
Entrez le nom du joueur 1 : Ali
Voulez-vous jouer :
1 - À deux joueurs
2 - Contre l'ordinateur (IA)
Votre choix (1 ou 2) : 1
Entrez le nom du joueur 2 : Sara
Choisissez un jeu :
  1 - Morpion
  2 - Puissance 4
Votre choix : 1
...
    1   2   3
  +---+---+---+
1 | X | O | O |
  +---+---+---+
2 |   | X |   |
  +---+---+---+
3 |   |   | X |
  +---+---+---+

Ali a gagné la partie !
Voulez-vous rejouer ? (o/n) : n

========== BILAN FINAL ==========
Ali : 1 victoire(s)
Sara : 0 victoire(s)
Le vainqueur est Ali ! Félicitations !
```

## Règles de saisie

| Jeu | Saisie attendue | Exemple |
|---|---|---|
| Morpion | ligne puis colonne (de 1 à 3) | `2 3` |
| Puissance 4 | numéro de colonne (de 1 à 7) | `4` |

## Pistes d'amélioration

- Interface graphique (JavaFX ou Swing) en réutilisant le même modèle, grâce à l'architecture MVC
- MinMax avec élagage alpha-bêta pour l'IA difficile du Puissance 4
- Tests unitaires JUnit sur les règles de victoire et sur l'IA
- Choix du joueur qui commence la partie

## Utilisation de l'IA générative

L'utilisation d'outils d'IA générative pendant le projet est décrite dans [`utilisationIAG.txt`](utilisationIAG.txt).
