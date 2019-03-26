import database.Database;
import library.Library;
import view.View;

import java.time.LocalDate;

import static library.Library.readStringNotNull;
import static library.Library.registrationProcess;
//import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

    	 //View view=new View();
         String name, surname, username, password;
         LocalDate birthDate, registrationDate;
         

         /**
          * Scelta da parte dell'utente di iscriversi oppure di autenticarsi.
          */
		System.out.println(View.MG_INIZIALE);
		View.stampaMenuSpecifico(View.RICHIESTE_MENU_INIZIALE);
		int choise=Library.readInt();
         
         boolean end = false;
     	do
     	{	
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
					System.out.println(View.GRAZIE_ISCRIZIONE);
     				
         		    choise=0;
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
         			choise=0;
         			break;
         	
         		default:
					System.out.println(View.MG_ERRORE);
         			break;
         		case 0:
         		    end=true;
         			break;
     		}
     	}while(!end);
     }
    }

