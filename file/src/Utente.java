public class Utente {
	private String Nome, Cognome, eta, username, password;
    private ContoBancario conto;
    
    public Utente(String Nome, String Cognome, String eta, String username, String password) {
        this.Nome = Nome;
        this.Cognome = Cognome;
    	this.eta=eta;
    	this.username = username;
        this.password = password;
        this.conto = new ContoBancario();
    }

    public String getNome() {
        return Nome;
    }

    public String getCognome() {
        return Cognome;
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
        return Nome + "," + Cognome + "," + eta + "," + username + "," + password + "," + conto.getBanca() + "," + conto.getPortafoglio() + "," + conto.getMese() + "," + conto.getAnno();
    }
    
    public static Utente fromCSV(String line) {
        String[] parts = line.split(",");
        if(parts.length < 9) return null;  // Verifica che i dati siano completi
        String Nome = parts[0];
        String Cognome = parts[1];
        String eta = parts[2];
        String username = parts[3];
        String password = parts[4];
        double banca = Double.parseDouble(parts[5]);
        double portafoglio = Double.parseDouble(parts[6]);
        int mese = Integer.parseInt(parts[7]);
        int anno = Integer.parseInt(parts[8]);

        Utente u = new Utente(Nome, Cognome, eta, username, password);
        u.getConto().setBanca(banca);
        u.getConto().setPortafoglio(portafoglio);
        u.getConto().setMese(mese);
        u.getConto().setAnno(anno);
        
        return u;
    }
}