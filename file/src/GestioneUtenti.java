import java.io.*;
import java.util.*;

public class GestioneUtenti {
    
    public static Map<String, Utente> loadUtenti() {
        Map<String, Utente> utenti = new HashMap<>();
        // Crea la cartella File se non esiste
        File cartella = new File("File");
        if (!cartella.exists()) {
            cartella.mkdir();  // Crea la cartella se non esiste
        }
    
        // Percorso del file utenti.csv all'interno della cartella File
        File file = new File("File/utenti.csv");
        
        if (!file.exists()) {
            System.out.println("File utenti.csv non trovato. Creazione automatica.");
            return utenti;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();  // Elimina spazi indesiderati
                if (line.isEmpty()) continue;
                Utente u = Utente.fromCSV(line);
                if (u != null) {
                    utenti.put(u.getUsername().trim(), u);  // Assicura che username sia senza spazi
                }
            }
        } catch (IOException e) {
            System.out.println("Errore nel caricamento degli utenti: " + e.getMessage());
        }
        return utenti;
    }
    
    public static void saveUtenti(Map<String, Utente> utenti) {
        // Crea la cartella File se non esiste
        File cartella = new File("File");
        if (!cartella.exists()) {
            cartella.mkdir();  // Crea la cartella se non esiste
        }
    
        // Percorso del file utenti.csv all'interno della cartella File
        try (PrintWriter pw = new PrintWriter(new FileWriter("File/utenti.csv", false))) {
            for (Utente u : utenti.values()) {
                pw.println(u.toCSV().trim());  // Rimuove spazi indesiderati
            }
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio degli utenti: " + e.getMessage());
        }
    }
}
