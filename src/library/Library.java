package library;
import user.User;
import database.Database;
import view.View;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Classe intermedia che permette l'interazione tra la classe User e la classe Database.
 * @author Reda Kassame, Simona Ramazzotti.
 * @version 1
 */
public class Library {
	
	private static boolean end;
	private static String  password, username, string;
	private static int year, month, day, choise;
	private static long rangeYear=5;
	private static long rangeDay=-10;
	public static LocalDate birthDate;

	/**
	 * Metodo che assembla i metodi per la registrazione con i relativi controlli e salva il nuovo user,
	 * oggetto di tipo User, all'interno del Database.
	 */
    public static void registrationProcess(){
        User user = new User(insertString(View.NOME), insertString(View.COGNOME), insertString(View.USER_NAME), insertString(View.PASSWORD), insertDate(), LocalDate.now());
        if(Database.checkIf18(user.getBirthDate()) != false){
            Database.insertUser(user);
			System.out.println(View.GRAZIE_ISCRIZIONE);
		}else System.out.println(View.MINORENNE);
    }

	/**
	 * Medoto per inserire la data di nascita, di tipo LocalDate, dell'user.
	 * Prende da tastiera anno, mese e giorno e crea una data di tipo LocalDate.
	 * @return birthDate
	 */
	public static LocalDate insertDate(){
		boolean end= false;
		View.stampaRichiestaSingola(View.DATA_NASCITA);
		while(!end){
			View.stampaRichiestaSingola(View.YEAR);
			year= readInt();
			if(String.valueOf(year).length()==4)
			{
				View.stampaRichiestaSingola(View.MONTH);
				month= readInt();
				View.stampaRichiestaSingola(View.DAY);
				day=readInt();
				if((String.valueOf(month).length()<=2 && month <= 12 ) && (( String.valueOf(day).length() <= 2) && day <= 31)) {
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
	 * Metodo che permette di acquisire da tastiera una stringa, purche\' non sia vuota.
	 * @param tipoInserimento stringa che permette di generalizzare il metodo di inserimento, stampandola a video.
	 * @return stringa di caratteri.
	 */
	public static String insertString(String tipoInserimento) {
		View.stampaRichiestaSingola(tipoInserimento);
		try {
			string = readStringNotNull();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}

	/**
	 * Metodo che permette all'admin di visualizzare a video la lista di utenti oppure no.
	 */
	public static void stampaUser(){
		System.out.println(View.VISUALIZZARE_USER);
		choise= readInt();
		if (choise==0) Database.listUsers();
	}

	/**
	 * Metodo che permette di controllare se l'utente che si sta autenticando &egrave; un admin,
	 * ovvero un operatore. Se lo &egrave; allora pu&ograve;
	 */
	public static boolean checkIfAdmin(String username, String password){
		if(username.equals("admin") && password.equals("admin")) return true;
		else return false;
	}



    /**
	 * Metooo di controllo del Login da parte dell'User. L'autenticazione va a buon fine se l'username &egrave; presente all'interno
	 * del Databese e se la password inserita corrisponde. Altrimenti si pu&ograve; uscire dal metodo o continuare a riprovare l'inserimento.
	 */
	public static void checkLoginIfTrue(){
		boolean end = false;
		do
		{
			username=insertString(View.USER_NAME);
			password=insertString(View.PASSWORD);

			/**
			 * Si controlla per&ograve; prima se l'utente non sia un operatore,
			 * e lo si autentica come admin e gli viene permesso di visualizzare l'elenco degli utenti.
			 */
			if(checkIfAdmin(username,password)){
				System.out.println(View.BENVENUTO_ADMIN);
				stampaUser();
				//esce dal ciclo
				end=true;
			}
			else{
				if(Database.checkLogin(username,password)){
					System.out.println(View.AUTENTICAZIONE_SUCCESSO);
					/**
					 * Controllo se l'iscrizione è scaduta.
					 */
					renewalRegistration(Database.getUser(username));
					end=true;
				}
				else {
					System.out.println(View.MG_ERRORE+ View.MG_ANCORA);
					choise= readInt();
					/**
					 * Continua a ciclare se viene premuto 0, altrimenti si esce dal ciclo.
					 */
					if(choise!=0) end=true;
				}
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
			System.out.println(View.MG_SCADUTA_ISCRIZIONE);
			System.out.println(View.RINNOVO);
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
		return user.getRegistrationDate().plusYears(rangeYear).isBefore(LocalDate.now()) && user.getRegistrationDate().plusDays(rangeDay).isBefore(LocalDate.now());
	}
    
    /**
	 * Metodi di controllo dell'inserimento da tastiera di numeri di tipo interi.
	 * @return numeroInserito numero valido inserito da tastiera.
	 */
	public static int readInt() {
		Scanner readInt = new Scanner(System.in);
	  	boolean end = false;
	  	int numInserito = 0;
	  	do
	  	{	
	  		if(readInt.hasNextInt())
	  		{
	  			end = true ;
	  			numInserito = readInt.nextInt();
	  		}
	  		else System.out.println(View.MG_ERRORE);
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
	   boolean end = false;
	   String stringa = null;
	   
	   do
	   {
		 stringa = readString();
		 if (stringa.length() > 0)
		  {
			 end = true;
		  }
		 else System.out.println(View.MG_ERRORE);
	   } 
	   while(!end);
	   return stringa;
	}
}
