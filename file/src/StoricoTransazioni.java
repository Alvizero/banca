import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StoricoTransazioni {

    // Aggiunge (in append) una riga al file di storico specifico per l'utente nella cartella "File/Transazioni"
    public static void aggiungiTransazione(String username, String transazione) {
        // Crea la cartella "File/Transazioni" se non esiste
        File cartella = new File("File/Transazioni");
        if (!cartella.exists()) {
            cartella.mkdir();  // Crea la cartella se non esiste
        }

        String fileName = "File/Transazioni/" + username + "_storico.txt";
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            // Aggiungiamo anche data e ora attuali
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String now = LocalDateTime.now().format(formatter);
            out.println(now + " - " + transazione);
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio della transazione: " + e.getMessage());
        }
    }

    public static String visualizzaStorico(String username) {
        StringBuilder storico = new StringBuilder();
        String fileName = "File/Transazioni/" + username + "_storico.txt";
        File file = new File(fileName);
        if (!file.exists()) {
            return "Nessuno storico presente per l'utente " + username;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                storico.append(line).append("\n");
            }
        } catch (IOException e) {
            return "Errore nella lettura dello storico: " + e.getMessage();
        }
        return storico.toString();
    }
}