package database;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import user.User;

/**
 * Classe Database che racchiude metodi e gestione del database in cui sono raccolti tutti gli user e oggetti inerenti.
 * @author Reda Kassame, Simona Ramazzotti.
 * @version 1
 */
public class Database {

	/**
	 * Lista di user, oggetti di tipo User, con key il nome utente, username di tipo String.
	 * Mediante l'utilizzo di tale HashMap siamo in grado di collegare ogni utente (con le relative informazioni anagrafiche) con una key, ovvero username.
	 */
    static Map<String, User> userList= new HashMap<>();
    
    /**
     * Medoto che permette l'inserimento di un nuovo user, oggetto di tipo User, all'interno della nostra lista di user, HashMap.
     */
    public static void insertUser(User newuser){
        userList.put(newuser.getUsername(),newuser);
        System.out.println("<+> New user added!");
    }
    /**
     * Metodo, di stampa, che permette di visualizzare a video la lista di tutti gli user all'interno del database, l'HashMap.
     */
    public static void listUsers(){
        for (Map.Entry<String,User> user: userList.entrySet()){
            System.out.println(user.toString());
        }
    }

    /**
     * Metodo che permette all'user di effettuare il login. Verifica che esista il nome utente, username, e controlla che la password sia la medesima.
     * Se il login va a buon fine, ritorna true, l'user ha accesso ai servizi di prestito temporaneo, altrimenti false.
     * @param username {@link User}
     * @param password {@link User}
     * @return false login non riuscito, true login riuscito corettamente.
     */
    public static boolean checkLogin(String username, String password){
        if(userList.containsKey(username) && userList.get(username).getPassword().equals(password)) return true;
        else return false;
    }

    /**
     * Metodo che ritorna l'user, l'oggetto di tipo User, preso dal Database.
     * @param username {@link User}
     * @return user oggetto di tipo User {@link User}
     */
    public static User getUser(String username){
        return userList.get(username);
    }

    /**
     * Metodo di controllo che verifica che l'user, oggetto di tipo User, sia maggiorenne.
     * Pertanto confronta la data di nascita, di tipo LocalDate, con la data attuale, LocalDate.now(), affinche\' sia 
     * maggiore o uguale a 18 anni.
     * @param birthDate  Data di nascita dell'user, di tipo LocalDate. E\' richiesto che sia maggiorenne per poter diventare utente dei servizi.
     * @return false se l'user non e\' maggiorenne. (e quindi non ha accesso ai servizi di prestito temporaneo)
     * @return true se l'user e\' maggiorenne.
     */
    public static boolean checkIf18(LocalDate birthDate){
        LocalDate now = LocalDate.now();
        int age = Period.between(birthDate,now).getYears();
        if(age > 18){
            return true;
        }
        else{
            return false;
        }
    }


    /**
     * Creazione di oggetti preimpostati.
     */
    public static void initAllObject() {
        //genero utenti
        User user1 = new User("test", "test", "test1", "test1", LocalDate.of(1996, 12, 01), LocalDate.of(2018, 01, 01));
        User user2 = new User("test", "test", "test2", "test2", LocalDate.of(2000, 12, 01), LocalDate.of(1996, 01, 01));
        User user3 = new User("test", "test", "test3", "test3", LocalDate.of(1988, 12, 01), LocalDate.of(2019, 01, 01));
        userList.put(user1.getUsername(), user1);
        userList.put(user2.getUsername(), user2);
        userList.put(user3.getUsername(), user3);
    }


}