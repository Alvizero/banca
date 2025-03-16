import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StoricoTransazioni {
    private static final String DIRECTORY_PATH = "File/Transazioni";

    // Aggiunge (in append) una transazione nel file storico specifico per l'utente
    public static void aggiungiTransazione(String username, String transazione) {
        Tools.FileManager.creaCartella(DIRECTORY_PATH); // Verifica anche l'esistenza della cartella

        String fileName = DIRECTORY_PATH + "/" + username + "_storico.txt";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String now = LocalDateTime.now().format(formatter);
        String transazioneCompleta = now + " - " + transazione;

        Tools.FileManager.scriviSuFile(fileName, transazioneCompleta, true);
    }

    // Visualizza lo storico delle transazioni di un utente
    public static String visualizzaStorico(String username) {
        String fileName = DIRECTORY_PATH + "/" + username + "_storico.txt";
        File file = new File(fileName);
        if (!file.exists()) {
            return "Nessuno storico presente per l'utente " + username;
        }
        return Tools.FileManager.leggiDaFile(fileName);
    }
}