package Tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileManager {

    public static void creaCartella(String DIRECTORY_PATH) {
        File cartella = new File(DIRECTORY_PATH);
        if (!cartella.exists()) {
            cartella.mkdir(); // Crea la cartella se non esiste
        }
    }

    // Scrive su un file, aggiungendo o sovrascrivendo in base al parametro append
    public static void scriviSuFile(String filePath, String contenuto, boolean append) {
        try (FileWriter fw = new FileWriter(filePath, append);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println(contenuto); // Scrive il contenuto nel file
        } catch (IOException e) {
            System.out.println("Errore nel salvataggio della transazione: " + e.getMessage()); // Log
        }
    }

    // Legge tutto il contenuto di un file e lo restituisce come una stringa
    public static String leggiDaFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        File file = new File(filePath);
        if (!file.exists()) { // Se il file non esiste, restituisce una stringa vuota
            return "";
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Errore leggendo dal file: " + e.getMessage()); // Log
        }
        return sb.toString(); // Restituisce il contenuto del file come Stringa
    }
}
