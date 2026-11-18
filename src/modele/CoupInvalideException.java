package modele;

 
public class CoupInvalideException extends Exception {
    private static final long serialVersionUID = 1L;

    public CoupInvalideException(String message) {
        super(message);  
    }
}
