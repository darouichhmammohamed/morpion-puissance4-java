package modele;

 
public class JoueurIA extends Joueur {
    public JoueurIA() {
        super("IA");
    }

    @Override
    public boolean estIA() {
        return true;
    }
}
