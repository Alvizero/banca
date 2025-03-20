import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// Classe principale per la GUI
public class BankGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel, loginPanel, mainPanel;

    private Map<String, Utente> utenti;
    private Utente currentUser;
    private Random random;

    // Componenti per la vista principale
    private JTextArea infoArea;
    private JPanel buttonPanel;

    public BankGUI() {
        super("Gestione Banca");
        random = new Random();
        utenti = GestioneUtenti.loadUtenti();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Crea i due pannelli: login/registrazione e pannello principale
        loginPanel = createLoginPanel();
        mainPanel = createMainPanel();

        cardPanel.add(loginPanel, "login");
        cardPanel.add(mainPanel, "main");
        add(cardPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Crea il pannello di login e registrazione usando un JTabbedPane
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab "Login"
        JPanel loginTab = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton loginButton = new JButton("Accedi");

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginTab.add(userLabel, gbc);
        gbc.gridx = 1;
        loginTab.add(userField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginTab.add(passLabel, gbc);
        gbc.gridx = 1;
        loginTab.add(passField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginTab.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();
                if (utenti.containsKey(username) && utenti.get(username).getPassword().equals(password)) {
                    currentUser = utenti.get(username);
                    JOptionPane.showMessageDialog(BankGUI.this, "Login effettuato!");
                    refreshUserInfo();
                    cardLayout.show(cardPanel, "main");
                } else {
                    JOptionPane.showMessageDialog(BankGUI.this, "Credenziali errate. Riprova.");
                }
            }
        });

        tabbedPane.addTab("Login", loginTab);

        // Tab "Registrazione"
        JPanel regTab = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.fill = GridBagConstraints.HORIZONTAL;

        JLabel nomeLabel = new JLabel("Nome:");
        JLabel cognomeLabel = new JLabel("Cognome:");
        JLabel etaLabel = new JLabel("Età:");
        JLabel userRegLabel = new JLabel("Username:");
        JLabel passRegLabel = new JLabel("Password:");
        JTextField nomeField = new JTextField(15);
        JTextField cognomeField = new JTextField(15);
        JTextField etaField = new JTextField(15);
        JTextField userRegField = new JTextField(15);
        JPasswordField passRegField = new JPasswordField(15);
        JButton regButton = new JButton("Registrati");

        gbc2.gridx = 0;
        gbc2.gridy = 0;
        regTab.add(nomeLabel, gbc2);
        gbc2.gridx = 1;
        regTab.add(nomeField, gbc2);
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        regTab.add(cognomeLabel, gbc2);
        gbc2.gridx = 1;
        regTab.add(cognomeField, gbc2);
        gbc2.gridx = 0;
        gbc2.gridy = 2;
        regTab.add(etaLabel, gbc2);
        gbc2.gridx = 1;
        regTab.add(etaField, gbc2);
        gbc2.gridx = 0;
        gbc2.gridy = 3;
        regTab.add(userRegLabel, gbc2);
        gbc2.gridx = 1;
        regTab.add(userRegField, gbc2);
        gbc2.gridx = 0;
        gbc2.gridy = 4;
        regTab.add(passRegLabel, gbc2);
        gbc2.gridx = 1;
        regTab.add(passRegField, gbc2);
        gbc2.gridx = 1;
        gbc2.gridy = 5;
        regTab.add(regButton, gbc2);

        regButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText().trim();
                String cognome = cognomeField.getText().trim();
                String eta = etaField.getText().trim();
                String username = userRegField.getText().trim();
                String password = new String(passRegField.getPassword()).trim();
                if (utenti.containsKey(username)) {
                    JOptionPane.showMessageDialog(BankGUI.this, "Username già esistente. Riprova.");
                } else if (!eta.matches("\\d+") || Integer.parseInt(eta) < 18) {
                    JOptionPane.showMessageDialog(BankGUI.this, "Devi almeno avere 18 anni. Riprova.");
                } else {
                    currentUser = new Utente(nome, cognome, eta, username, password);
                    utenti.put(username, currentUser);
                    GestioneUtenti.saveUtenti(utenti);
                    JOptionPane.showMessageDialog(BankGUI.this, "Registrazione effettuata!");
                    refreshUserInfo();
                    cardLayout.show(cardPanel, "main");
                }
            }
        });

        tabbedPane.addTab("Registrazione", regTab);
        panel.add(tabbedPane, BorderLayout.CENTER);
        return panel;
    }

    // Crea il pannello principale con la parte superiore (info utente) e quella
    // inferiore (bottoni opzioni)
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panel.add(new JScrollPane(infoArea), BorderLayout.NORTH);

        // Pannello bottoni: 8 opzioni distribuite in 2 righe
        buttonPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        JButton btnPreleva = new JButton("Preleva dalla banca");
        JButton btnDeposita = new JButton("Deposita in banca");
        JButton btnInvesti = new JButton("Investi soldi");
        JButton btnConcludi = new JButton("Concludi investimento");
        JButton btnAvanza = new JButton("Avanza di un mese");
        JButton btnStorico = new JButton("Visualizza storico");
        JButton btnLogout = new JButton("Logout");
        JButton btnEsci = new JButton("Esci");

        buttonPanel.add(btnPreleva);
        buttonPanel.add(btnDeposita);
        buttonPanel.add(btnInvesti);
        buttonPanel.add(btnConcludi);
        buttonPanel.add(btnAvanza);
        buttonPanel.add(btnStorico);
        buttonPanel.add(btnLogout);
        buttonPanel.add(btnEsci);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Azioni dei bottoni
        btnPreleva.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sPrelevare = JOptionPane.showInputDialog(BankGUI.this, "Inserisci l'importo da prelevare:");
                if (sPrelevare != null) {
                    try {
                        double preleva = Double.parseDouble(sPrelevare);
                        if (preleva > currentUser.getConto().getBanca() || preleva < 0) {
                            JOptionPane.showMessageDialog(BankGUI.this, "Operazione non valida: importo errato!");
                        } else {
                            currentUser.getConto().preleva(preleva);
                            JOptionPane.showMessageDialog(BankGUI.this, "Hai prelevato: " + preleva + " €");
                            StoricoTransazioni.aggiungiTransazione(currentUser.getUsername(),
                                    "Prelievo: " + preleva + "€ (Banca: " + currentUser.getConto().getBanca()
                                            + ", Portafoglio: " + currentUser.getConto().getPortafoglio() + ")");
                            GestioneUtenti.saveUtenti(utenti);
                            refreshUserInfo();
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(BankGUI.this, "Il valore inserito non è un numero decimale!");
                    }
                }
            }
        });

        btnDeposita.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sDepositare = JOptionPane.showInputDialog(BankGUI.this, "Inserisci l'importo da depositare:");
                if (sDepositare != null) {
                    try {
                        double deposita = Double.parseDouble(sDepositare);
                        if (deposita > currentUser.getConto().getPortafoglio() || deposita < 0) {
                            JOptionPane.showMessageDialog(BankGUI.this, "Operazione non valida: importo errato!");
                        } else {
                            currentUser.getConto().deposita(deposita);
                            JOptionPane.showMessageDialog(BankGUI.this, "Hai depositato: " + deposita + " €");
                            StoricoTransazioni.aggiungiTransazione(currentUser.getUsername(),
                                    "Deposito: " + deposita + "€ (Banca: " + currentUser.getConto().getBanca()
                                            + ", Portafoglio: " + currentUser.getConto().getPortafoglio() + ")");
                            GestioneUtenti.saveUtenti(utenti);
                            refreshUserInfo();
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(BankGUI.this, "Il valore inserito non è un numero decimale!");
                    }
                }
            }
        });

        btnInvesti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentUser.getConto().getInvestimento() == true) {
                    JOptionPane.showMessageDialog(BankGUI.this, "Hai gia' un investimento aperto!");
                } else {
                    if (currentUser.getConto().getBanca() <= 0) {
                        JOptionPane.showMessageDialog(BankGUI.this, "Fondi insufficienti per investire!");
                        return;
                    }
                    String sInvestiti = JOptionPane.showInputDialog(BankGUI.this, "Inserisci l'importo da investire:");
                    if (sInvestiti != null) {
                        try {
                            double importoInvestito = Double.parseDouble(sInvestiti);
                            if (importoInvestito > currentUser.getConto().getBanca() || importoInvestito <= 0) {
                                JOptionPane.showMessageDialog(BankGUI.this,
                                        "Importo non valido o fondi insufficienti.");
                                return;
                            }
                            String mesiInput = JOptionPane.showInputDialog(BankGUI.this,
                                    "Inserisci il periodo di investimento (in mesi):");
                            if (mesiInput != null) {
                                int investimentiMesi = Integer.parseInt(mesiInput);
                                if (investimentiMesi <= 0) {
                                    JOptionPane.showMessageDialog(BankGUI.this, "Periodo non valido.");
                                    return;
                                }
                                currentUser.getConto().investe(importoInvestito, investimentiMesi, random);
                                JOptionPane.showMessageDialog(BankGUI.this,
                                        "Hai iniziato l'investimento! \nSoldi investiti: " + importoInvestito
                                                + " € \nDurata Investimento: " + investimentiMesi);
                                StoricoTransazioni.aggiungiTransazione(currentUser.getUsername(),
                                        "Investimento iniziato: " + importoInvestito + "€ per " + investimentiMesi
                                                + " mesi");
                                GestioneUtenti.saveUtenti(utenti);
                                refreshUserInfo();
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(BankGUI.this, "Errore: inserisci un numero valido.");
                        }
                    }
                }
            }
        });

        btnConcludi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentUser.getConto().getInvestimento() == true) {
                    currentUser.getConto().fineInvestimento();
                    JOptionPane.showMessageDialog(BankGUI.this, "Hai concluso l'investimento!");
                    StoricoTransazioni.aggiungiTransazione(currentUser.getUsername(), "Investimento concluso.");
                } else {
                    JOptionPane.showMessageDialog(BankGUI.this, "Non c'è un investimento in corso!");
                }

                GestioneUtenti.saveUtenti(utenti);
                refreshUserInfo();
            }
        });

        btnAvanza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentUser.getConto().nextMonth();
                JOptionPane.showMessageDialog(BankGUI.this, "Sei avanzato di un mese");
                StoricoTransazioni.aggiungiTransazione(currentUser.getUsername(),
                        "Avanzamento mese: nuovo stato - " + currentUser.getConto().statoConto().replace("\n", " | "));
                GestioneUtenti.saveUtenti(utenti);
                refreshUserInfo();
            }
        });

        btnStorico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String storico = StoricoTransazioni.visualizzaStorico(currentUser.getUsername());
                JTextArea textArea = new JTextArea(storico);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 400));
                JOptionPane.showMessageDialog(BankGUI.this, scrollPane, "Storico Transazioni",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(BankGUI.this, "Sei uscito!");
                currentUser = null;
                cardLayout.show(cardPanel, "login");
            }
        });

        btnEsci.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        return panel;
    }

    // Aggiorna la parte superiore della finestra con le informazioni dell'utente
    private void refreshUserInfo() {
        if (currentUser != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Utente: ").append(currentUser.getUsername()).append("\n");
            // Si assume l'esistenza dei getter per Nome, Cognome ed Età
            sb.append("Nome: ").append(currentUser.getNome()).append(" ").append(currentUser.getCognome()).append("\n");
            sb.append("Età: ").append(currentUser.getEta()).append("\n");
            sb.append(currentUser.getConto().statoConto());
            infoArea.setText(sb.toString());
        }
    }

    /*
     * public static void main(String[] args) {
     * SwingUtilities.invokeLater(new Runnable(){
     * public void run() {
     * new BankGUI();
     * }
     * });
     * }
     */
}