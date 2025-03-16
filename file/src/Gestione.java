import javax.swing.SwingUtilities;

public class Gestione {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BankGUI(); // Apre la GUI
            }
        });
    }
}