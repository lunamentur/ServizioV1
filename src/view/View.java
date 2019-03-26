package view;
/**
 * @author Reda , Simona
 * Classe View col compito di generare il menu' iniziale con cui si interfaccera' l'utente finale e
 * altri metodi del tipo .toString() al fine di visualizzare a video messaggi di errore, domande, simboli e altro.
 */
public class View {

    public final static String FINE_MENU ="Arrivederci, grazie per aver usato i Servizi di prestito temporaneo";
    public final static String CORNICETTA ="**************************************";
    public final static String RICHIESTA="Quale opzione desideri svolgere? ";
    public final static String MG_ERRORE="Riprova, hai inserito un valore non valido\n";
    public final static String MG_ANCORA ="Cosa desideri fare?";
    public final static String MG_SCADUTA_ISCRIZIONE ="La sua iscrizione è scaduta poiche\' ha superato i termini prescritti.";
    public final static String MG_INIZIALE="Benvenuto nel Pannello dei Servizi di prestiti temporanei";
    public final static String AUTENTICAZIONE_SUCCESSO="Autenticazione avvenuta con successo, bentornato!";
    public final static String MG_AZIONE_SUCCESSO="L'azione appena completata e\' avvenuta con successo, grazie!";
    public final static String GRAZIE_ISCRIZIONE="Grazie per esserti Iscritto ai Servizi di Prestito Temporaneo.";
    /**
     * Creazione di una stringa COSTANTE
     * @param RICHIESTE_MENU rappresenta le voci del menu che si vuole creare nel momento iniziale di accesso da parte dell'utente: ovvero se iscriversi come nuovo
     * utente oppure, se gia\' in possesso di nome utente e password
     */
    public final static String [] RICHIESTE_MENU_INIZIALE ={" ISCRIVITI ai servizi di prestito temporaneo se sei un nuovo utente;",
            " AUTENTICAZIONE se sei gia\' un'utente registrato ai servizi di prestito temporaneo;"};

    /**
     * @param RINNOVO rappresenta la richiesta di rinnovo dell'iscrizione ai servizi di prestito temporaneo da sottoporre all'utente.
     */
    public final static String RINNOVO ="Desideri RINNOVARE l'iscrizione ai servizi? premi 0";
    public final static String NOME="Inserisci NOME:";
    public final static String COGNOME="Inserisci COGNOME:";
    public final static String USER_NAME="Inserisci NICKNAME:";
    public final static String DATA_NASCITA="Inserisci DATA DI NASCITA:";
    public final static String PASSWORD ="Inserisci PASSWORD:";
    public final static String MONTH ="Inserisci MESE:";
    public final static String DAY ="Inserisci GIORNO:";
    public final static String YEAR ="Inserisci ANNO:";


    /**
     * Metodo che permette di stampare il Menu'
     */
    public static void stampaMenuSpecifico(String[] richiesteMenu)
    {
        System.out.println(CORNICETTA);
        for (int k=0; k < richiesteMenu.length; k++)
        {
            System.out.println((k+1) + " - " + richiesteMenu[k]);
        }
        System.out.println(CORNICETTA);
    }

    /**
     * Metodo che permette di stampare una singola richiesta con cornicette annesse.
     */
    public void stampaRichiestaSingola(String richiestaMenu)
    {
        System.out.println(CORNICETTA);
        System.out.println(richiestaMenu);
        System.out.println(CORNICETTA);
    }


    /**
     * Metodo che permette di stampare piu\' richieste e menu\' in base al tipo di interazione con l'utente.
     */
    public void stampaMenu(int scelta)
    {
        boolean finito = false;
        int i;

        do
        {
            switch(scelta)
            {
                /**
                 * Iscrizione o Autenticazione
                 */
                case 1:
                    System.out.println(MG_INIZIALE);
                    stampaMenuSpecifico(RICHIESTE_MENU_INIZIALE);
                    scelta=0;
                    break;

                /**
                 * Rinnovo
                 */
                case 4:
                    stampaRichiestaSingola(RINNOVO);
                    scelta=0;
                    break;

                /**
                 * Iscrizione Scaduta, l'utente viene mandato direttamente a iscriversi.
                 */
                case 5:
                    System.out.println(MG_SCADUTA_ISCRIZIONE);
                    scelta=2;
                    break;

                /**
                 * Fine del programma dei Servizi per prestiti temporanei.
                 */
                case 6:
                    System.out.println(FINE_MENU);
                    scelta=0;
                    break;

                /**
                 * Fine della stampa delle richieste del Menu.
                 */
                case 0:
                    finito = false;
                    break;

                /**
                 * Errore, l'inserimento non è corretto
                 */
                case 10:
                    System.out.println(MG_ERRORE);
                    break;
            }
        }while(!finito);
    }

		/**
		 * Iscrizione utente alternativo a quello principale
		 */
		public void stampaMenuIscrizione(int choise){
            boolean finito = false;
            int i;

            do
            {
                switch(choise)
                {
                    /**
                     * Iscrizione o Autenticazione
                     */
                    case 1:
                        System.out.println(MG_INIZIALE);
                        stampaMenuSpecifico(RICHIESTE_MENU_INIZIALE);
                        choise=0;
                        break;

                    /**
                     * Stampa richiesta nome dell'user
                     */
                    case 2:
                        System.out.println(NOME);
                        choise=0;
                        break;

                    /**
                     * Stampa richiesta cognome dell'user
                     */
                    case 3:
                        System.out.println(COGNOME);
                        choise=0;
                        break;

                    /**
                     * Stampa richiesta password
                     */
                    case 4:
                        stampaRichiestaSingola(PASSWORD);
                        choise=0;
                        break;

                    /**
                     * Stampa richiesta data di nascita dell'user
                     */
                    case 5:
                        stampaRichiestaSingola(DATA_NASCITA);
                        choise=0;
                        break;

                    /**
                     * Stampa richiesta anno di nascita dell'user
                     */
                    case 6:
                        stampaRichiestaSingola(YEAR);
                        choise=0;
                        break;

                    /**
                     * Stampa richiesta mese di nascita dell'user
                     */
                    case 7:
                        stampaRichiestaSingola(MONTH);
                        choise=0;
                        break;

                    /**
                     * Stampa richiesta giorno di nascita dell'user
                     */
                    case 8:
                        stampaRichiestaSingola(DAY);
                        choise=0;
                        break;

                    /**
                     * Stampa richiesta nickname dell'user
                     */
                    case 9:
                        stampaRichiestaSingola(USER_NAME);
                        choise=0;
                        break;

                    /**
                     * Fine della stampa delle richieste del Menu.
                     */
                    case 0:
                        finito = false;
                        break;

                    /**
                     * Errore, l'inserimento non è corretto
                     */
                    case 10:
                        System.out.println(MG_ERRORE);
                        break;
                }
            }while(!finito);
		}
//
//		/**
//		 * Generico
//		 */
//		public void stampaAltro(){
//
//		}


}
