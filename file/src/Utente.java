public class Utente {
    private String nome, cognome, eta, username, password;
    private ContoBancario conto;

    public Utente(String nome, String cognome, String eta, String username, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.username = username;
        this.password = password;
        this.conto = new ContoBancario();
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEta() {
        return eta;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ContoBancario getConto() {
        return conto;
    }

    public String toCSV() {
        return nome + "," + cognome + "," + eta + "," + username + "," + password + "," + conto.getBanca() + ","
                + conto.getPortafoglio() + "," + conto.getMese() + "," + conto.getAnno();
    }

    public static Utente fromCSV(String line) {
        String[] parts = line.split(","); // Carattere separatore
        if (parts.length < 9)
            return null; // Restituisce null se i dati sono incompleti

        // Assegna i valori presi dalla stringa (csv) alle variabili
        String nome = parts[0];
        String cognome = parts[1];
        String eta = parts[2];
        String username = parts[3];
        String password = parts[4];
        double banca = Double.parseDouble(parts[5]);
        double portafoglio = Double.parseDouble(parts[6]);
        int mese = Integer.parseInt(parts[7]);
        int anno = Integer.parseInt(parts[8]);

        Utente utente = new Utente(nome, cognome, eta, username, password);
        utente.getConto().setBanca(banca);
        utente.getConto().setPortafoglio(portafoglio);
        utente.getConto().setMese(mese);
        utente.getConto().setAnno(anno);

        return utente; // Restituisce l'oggetto utente
    }
}