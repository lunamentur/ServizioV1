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
	
	private static String  password, username, string;
	private static int year, month, day, choise;
	private static long rangeYear=5;
	private static long rangeDay=10;
	public static LocalDate birthDate;

	/**
	 * Metodo che assembla i metodi per la registrazione con i relativi controlli e salva il nuovo user,
	 * oggetto di tipo User, all'interno del Database.
	 */
    public static void registrationProcess(){
    	username= insertUserName();
        User user = new User(insertString(View.NOME), insertString(View.COGNOME), username, insertString(View.PASSWORD), insertDate(), LocalDate.now());
        if(Database.checkIf18(user.getBirthDate())){
            Database.insertUser(user);
			System.out.println(View.GRAZIE_ISCRIZIONE);
		}else System.out.println(View.MINORENNE);
    }

	/**
	 * Metodo che controlla se l'username e\' gia presente nel Database.
	 * Se si continua a ciclare finche\' non ne viene inserito uno nuovo (inesistente).
	 * @return username
	 */
	public static String insertUserName(){
		boolean end=false;
		do{
			username=insertString(View.USER_NAME);
			if(Database.checkIfUser(username)){
				View.stampaRichiestaSingola(View.USERNAME_ESISTE);
			} else end=true;
		}while(!end);
		return username;
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
					userExpired(Database.getUser(username));
					if(!Database.getUser(username).getName().equals("_expired_")){
						renewalRegistration(Database.getUser(username));
					} else Database.removeUser(username);
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
		if(isRenewal(user)){
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
	public static boolean isRenewal(User user){
		if(user.getRegistrationDate().plusYears(rangeYear).isEqual(LocalDate.now()) || (LocalDate.now().minusYears(rangeYear).isBefore(user.getRegistrationDate()) && LocalDate.now().minusYears(rangeYear).isAfter(user.getRegistrationDate().minusDays(rangeDay))))
		{
			return true;
		}
		return false;
	}

	/**
	 * Metodo che controlla che l'iscrizione dell'user sia scaduta.
	 * @return true se l'iscrizione dell'user è scaduta.
	 * @return false se l'iscrizione dell'user non è scaduta.
	 */
	public static boolean isExpired(User user){
		boolean equalYearCondition= user.getRegistrationDate().plusYears(rangeYear).getYear()== LocalDate.now().getYear() && LocalDate.now().isAfter(user.getRegistrationDate().plusYears(rangeYear));
		boolean differentYearCondition= LocalDate.now().getYear()>user.getRegistrationDate().plusYears(rangeYear).getYear();
		if( equalYearCondition || differentYearCondition)
		{
			return true;
		}
		return false;
	}

	/**
	 * Metodo che se l'utente ha l'iscrizione scaduta, lo contrassegna come scaduto tramite il name e non la chiave primaria username.
	 * l'utente sarà costretto registrarsi nuovamente.
	 */
	public static void userExpired(User user){
		if(isExpired(user)){
			View.stampaRichiestaSingola(View.SCADUTA_NON_RINNOVABILE);
			user.setName("_expired_");
		}
	}

    
    /**
	 * Metodi di controllo dell'inserimento da tastiera di numeri di tipo interi.
	 * @return numeroInserito numero valido inserito da tastiera.
	 */
	public static int readInt() {
		Scanner readInt = new Scanner(System.in);
		int numeroInserito;
		do {
			while (!readInt.hasNextInt()) {
				String input = readInt.next();
				System.out.println(View.MG_ERRORE);
			}
			numeroInserito = readInt.nextInt();
		} while (numeroInserito < 0 && !(String.valueOf(numeroInserito).equals(null)));
		return numeroInserito;
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
