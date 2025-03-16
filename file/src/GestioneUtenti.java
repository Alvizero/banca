import java.io.*;
import java.util.*;

public class GestioneUtenti {
    private static final String DIRECTORY_PATH = "File";
    private static final String FILE_PATH = DIRECTORY_PATH + "/utenti.csv";

    // Carica gli utenti dal file utenti.csv
    public static Map<String, Utente> loadUtenti() {
        Map<String, Utente> utenti = new HashMap<>();
        Tools.FileManager.creaCartella(DIRECTORY_PATH); // Verifica anche l'esistenza della cartella

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("File utenti.csv non trovato. Creazione automatica."); // Log
            return utenti;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Elimina eventuali spazi indesiderati
                if (line.isEmpty())
                    continue;
                Utente u = Utente.fromCSV(line);
                if (u != null) {
                    utenti.put(u.getUsername().trim(), u); // Assicura che l'username sia privo di spazi
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nel caricamento degli utenti: " + e.getMessage()); // Log
        }
        return utenti; // Restituisce gli utenti
    }

    // Salva gli utenti nel file utenti.csv
    public static void saveUtenti(Map<String, Utente> utenti) {
        Tools.FileManager.creaCartella(DIRECTORY_PATH); // Verifica anche l'esistenza della cartella

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH, false))) {
            for (Utente u : utenti.values()) {
                pw.println(u.toCSV().trim()); // Rimuove eventuali spazi indesiderati
            }
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio degli utenti: " + e.getMessage()); // Log
        }
    }
}
