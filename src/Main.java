import database.Database;
import library.Library;
import view.View;

import java.time.LocalDate;

import static library.Library.*;

/**
 * Classe main del programma servizio temporaneo di prestiti.
 * @author Reda Kassame, Simona Ramazzotti.
 * @version 1
 */
public class Main {
    public static void main(String[] args) {

		/**
		 * credo degli oggetti preimpostati e li carico nel programma per i test.
		 */
		Database.initAllObject();

		/**
		 * Scelta da parte dell'utente di iscriversi oppure di autenticarsi.
         */

         boolean end = false;
     	do
     	{
			System.out.println(View.MG_INIZIALE);
			View.stampaMenuSpecifico(View.RICHIESTE_MENU_INIZIALE);
			int choise=Library.readInt();

			switch(choise){
         		
     		/**
     		 * Iscrizione
     		 */
     			case 1:
     				
     				/**
     				 * L'utente inserisce il nome, il cognome, la password e la data di nascita.
					 * Quando inserisce il nickname viene ricercato all'interno del database e, se vi sono doppioni, viene richiesto l'inserimento.
					 * Sulla data di nascita viene fatto un controllo: se l'utente è maggiorenne allora può essere inserito nel database.
     				 */

					Library.registrationProcess();
					break;
         			
         	/**
         	 * Autenticazione (Login)
         	 */
         		case 2:
         			/**
         			 * L'utente inserisce il proprio nickname e viene cercato all'interno del databese.
					 * Se la password è la stessa allora viene autenticato con successo, altrimenti
         			 * continua a ciclare oppure si termina il programma.
                     * Una volta che il Login è andato a buon fine controlliamo che l'iscrizione dell'user sia ancora valida.
                     * Se non lo è, ovvero sono decaduti i privilegi, può avvenire il rinnovo dell'iscrizione.
         			 */
					Library.checkLoginIfTrue();
         			break;

         		default:
					System.out.println(View.MG_ERRORE);
         			break;
         		case 0:
         		    end=true;
         		    System.out.println(View.FINE_MENU);
         			break;
     		}
     	}while(!end);
     }
    }

