import java.util.Random;

public class ContoBancario {
    // Stato del conto
    private double banca, portafoglio;
    private int mese, anno;
    
    // Variabili per investimento
    private int mesiInvestimento;
    private double saldoFinale, variazione;
    private boolean durataInvestimento, haGuadagnato, rosso;
    
    public ContoBancario() {
        this.banca = 0.0;
        this.portafoglio = 100.0;
        this.mese = 1;
        this.anno = 2025;
        this.mesiInvestimento = 0;
        this.saldoFinale = 0.0;
        this.variazione = 0.0;
        this.durataInvestimento = false;
        this.haGuadagnato = false;
        this.rosso = false;
    }
    
    // Metodi getter e setter (necessari per la persistenza su CSV)
    public double getBanca() {
        return banca;
    }
    public void setBanca(double banca) {
        this.banca = banca;
    }
    public double getPortafoglio() {
        return portafoglio;
    }
    public void setPortafoglio(double portafoglio) {
        this.portafoglio = portafoglio;
    }
    public int getMese() {
        return mese;
    }
    public void setMese(int mese) {
        this.mese = mese;
    }
    public int getAnno() {
        return anno;
    }
    public void setAnno(int anno) {
        this.anno = anno;
    }
    public boolean getInvestimento() {
        return durataInvestimento;
    }
    
    // Operazioni sul conto
    public void preleva(double preleva) {
        portafoglio += preleva;
        banca -= preleva;
    }
    
    public void deposita(double amount) {
        banca += amount;
        portafoglio -= amount;
    }
    
    // Simula un investimento
    public void investe(double importoInvestito, int investimentiMesi, Random random) {        
        durataInvestimento = true;
        mesiInvestimento = investimentiMesi;
        banca -= importoInvestito;
        
        String tipoInvestimento;
        if (mesiInvestimento <= 12) {
            tipoInvestimento = "Breve";
        } else if (mesiInvestimento <= 60) {
            tipoInvestimento = "Medio";
        } else {
            tipoInvestimento = "Lungo";
        }
        
        double percentualeGuadagno = 0;
        double percentualePerdita = 0;
        haGuadagnato = false;
        
        int esito = random.nextInt(100) + 1;
        switch (tipoInvestimento) {
            case "Breve": {
                if (esito <= 80) {
                    haGuadagnato = true;
                    percentualeGuadagno = random.nextDouble() * 20;
                } else {
                    percentualePerdita = random.nextDouble() * 15;
                }
                break;
            }
            case "Medio": {

                if (esito <= 60) {
                    haGuadagnato = true;
                    percentualeGuadagno = random.nextDouble() * 25 + 25;
                } else {
                    percentualePerdita = random.nextDouble() * 40 + 40;
                }
                break;
            }
            case "Lungo": {

                if (esito <= 35) {
                    haGuadagnato = true;
                    percentualeGuadagno = random.nextDouble() * 20 + 60;
                } else {
                    percentualePerdita = random.nextDouble() * 40 + 80;
                }
                break;
            }
        }
        
        if (haGuadagnato) {
            variazione = importoInvestito * (percentualeGuadagno / 100);
        } else {
            variazione = importoInvestito * (percentualePerdita / 100);
        }
        variazione = Math.round(variazione * 100.0) / 100.0;
        
        if (haGuadagnato) {
            rosso = false;
            saldoFinale = importoInvestito + variazione;
        } else {
            if (variazione <= importoInvestito) {
                rosso = false;
                saldoFinale = importoInvestito - variazione;
            } else {
                rosso = true;
                saldoFinale = variazione - importoInvestito;
            }
        }
    }
    
    // Conclude l'investimento e aggiorna il conto
    public void fineInvestimento() {
        if (durataInvestimento) {
            mese += mesiInvestimento;
            portafoglio += (100 * mesiInvestimento);
            
            if (!rosso) {
                banca += saldoFinale;
            } else {
                banca -= saldoFinale;
            }
            durataInvestimento = false;
            mesiInvestimento = 0;
            saldoFinale = 0.0;
            variazione = 0.0;
            haGuadagnato = false;
            rosso = false;
        }
    }
    
    // Avanza di un mese
    public void nextMonth() {
        mese++;
        portafoglio += 100;
        
        if (durataInvestimento && mesiInvestimento > 0) {
            mesiInvestimento--;
        }
        if (mese > 12) {
            mese = mese - 12;
            anno++;
        }
    }
    
    // Restituisce lo stato corrente del conto
    public String statoConto() {
        return "Data: " + mese + "/" + anno + "\n" + "Soldi in banca: " + banca + "€\n" + "Soldi nel portafoglio: " + portafoglio + "€\n";
    }
}
