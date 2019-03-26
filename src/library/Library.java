package library;
import user.User;
import database.Database;
import view.View;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Classe intermedia che permette l'interazione tra la classe User e la classe Database.
 * @author Reda , Simona
 */
public class Library {
	
	public final static String MG_ERRORE="Hai inserito un valore non valido\n";
	private static boolean end;
	private static String surname, name, password, username;
	private static int year, month, day, choise;
	private static long rangeYear=5;
	private static long rangeDay=-10;
	public static LocalDate birthDate;
	static View view=new View();

	//System.out.println(View.);

	/**
	 * Metodo che assembla i metodi per la registrazione con i relativi controlli e salva il nuovo user,
	 * oggetto di tipo User, all'interno del Database.
	 */
    public static void registrationProcess(){
        User user = new User(insertName(), insertSurname(), insertUsername(), insertPass(), insertDate(), LocalDate.now());
        if(Database.checkIf18(user.getBirthDate()) != false){
            Database.insertUser(user);
        }
    }
    
    /**
     * Medoto per inserire la data di nascita, di tipo LocalDate, dell'user.
     * Prende da tastiera anno, mese e giorno e crea una data di tipo LocalDate.
	 * @return birthDate
     */
    public static LocalDate insertDate(){
    	end= false;
		System.out.println(View.DATA_NASCITA);
		view.stampaMenuIscrizione(5); //
    	while(!end){
			System.out.println(View.YEAR);
    		year= readInt();
    		if(String.valueOf(year).length()==4)
    		{
				System.out.println(View.MONTH);
    			month= readInt();
				System.out.println(View.DAY);
    			day=readInt();
    			if(String.valueOf(month).length()==2 && String.valueOf(day).length()==2)
    			{
    				birthDate= LocalDate.of(year,month,day);
    				end=true;
    			}
    		}
    		else {
				System.out.println(View.MG_ERRORE);
			}
    	}
    	return birthDate;
    }

	/**
	 * Medoto per inserire il nome, di tipo String, dell'user.
	 * @return name
	 */
	public static String insertName(){
		view.stampaMenuIscrizione(2);
		try {
			name = readStringNotNull();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	/**
	 * Medoto per inserire il cognome, di tipo String, dell'user.
	 * @return surname
	 */
	public static String insertSurname(){
		view.stampaMenuIscrizione(3);
		try {
			surname = readStringNotNull();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return surname;
	}

	/**
	 * Medoto per inserire il nick name, di tipo String, dell'user.
	 * @return username
	 */
	public static String insertUsername(){
		view.stampaMenuIscrizione(9);
		try {
			username = readStringNotNull();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return username;
	}

	/**
	 * Medoto per inserire la password, di tipo String, dell'user.
	 * @return password
	 */
	public static String insertPass(){
		view.stampaMenuIscrizione(4);
		try {
			password = readStringNotNull();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}

    /**
     * Metooo di controllo del Login da parte dell'User. L'autenticazione va a buon fine se l'username è presente all'interno
	 * del Databese e se la password inserita corrisponde. Altrimenti si può uscire dal metodo o continuare a riprovare l'inserimento.
     */
	public static void checkLoginIfTrue(){
		end = false;
		do
		{
			username= insertUsername();
			password= insertPass();
			if(Database.checkLogin(username,password)==true){
				System.out.println(view.AUTENTICAZIONE_SUCCESSO);
				/**
				 * Controllo se l'iscrizione è scaduta.
				 */
				renewalRegistration(Database.getUser(username));
				end=true;
			}
			else {
				System.out.println(MG_ERRORE+ "Desideri riprovare? premi 0");
				choise= readInt();
				/**
				 * Continua a ciclare se viene premuto 0, altrimenti si esce dal ciclo.
				 */
				if(choise==0);
				else end=true;
			}
		}while(!end);
	}

	/**
	 * Metodo che permette il rinnovo dell'iscrizione dell'user se scaduta (o in procinto di scadere).
	 * @param user
	 */
	public static void renewalRegistration(User user){
		if(isExpired(user)==true){
			//possiamo rinnovare
			view.stampaMenu(6);
			view.stampaMenu(5);
			choise= readInt();
			if(choise==0){
				user.setRegistrationDate(LocalDate.now());
			}
		}
	}

	/**
	 * Metodo che controlla che l'iscrizione dell'user sia scaduta o nel range impostato per il rinnovo anticipato.
	 * @return true se l'iscrizione dell'user è scaduta, quindi può essere rinnovata.
	 * @return false se l'iscrizione dell'user non è scaduta e non è nel range dei giorni di rinnovo.
	 */
	public static boolean isExpired(User user){
		if(user.getRegistrationDate().plusYears(rangeYear).isAfter(LocalDate.now()) || user.getRegistrationDate().plusDays(rangeDay).isAfter(LocalDate.now())){
			return true;
		}
		return false;
	}

    
    /**
	 * Metodi di controllo dell'inserimento da tastiera di numeri di tipo interi.
	 * @return numeroInserito numero valido inserito da tastiera.
	 */
	public static int readInt() {
		Scanner readInt = new Scanner(System.in);
	  	end = false;
	  	int numInserito = 0;
	  	do
	  	{	
	  		if(readInt.hasNextInt())
	  		{
	  			end = true ;
	  			numInserito = readInt.nextInt();
	  		}
	  		else System.out.println(view.MG_ERRORE);
	  	}while(!end);
	  	return numInserito;
	}
	
	/**
     * Metodo di controllo lettura da tastiera di una stringa.
     * @return stringa tipo String che contiene la stringa inserita da tastiera dall'user.
     */
    private static BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
	
	public static String readString() throws Exception
	{
		String stringa = read.readLine();
		return stringa;
	}
	
	/**
     * Metodo di controllo lettura da tastiera di una stringa, possibilmente non vuota. Richiama il metodo readString().
     * @return stringa tipo String che contiene la stringa inserita da tastiera dall'user.
     */
	public static String readStringNotNull() throws Exception
	{
	   end = false;
	   String stringa = null;
	   
	   do
	   {
		 stringa = readString();
		 if (stringa.length() > 0)
		  {
			 end = true;
		  }
		 else System.out.println(view.MG_ERRORE);
	   } 
	   while(!end);
	   return stringa;
	}
}
