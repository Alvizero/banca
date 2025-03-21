import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

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

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }

        random = new Random();
        utenti = GestioneUtenti.loadUtenti();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Crea header istituzionale
        JPanel headerPanel = createHeaderPanel();

        // Crea i due pannelli: login/registrazione e pannello principale
        loginPanel = createLoginPanel();
        mainPanel = createMainPanel();

        // Organizza la struttura della finestra usando BorderLayout
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.add(headerPanel, BorderLayout.NORTH);
        mainContainer.add(cardPanel, BorderLayout.CENTER);

        cardPanel.add(loginPanel, "login");
        cardPanel.add(mainPanel, "main");
        add(mainContainer);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Crea un header istituzionale con logo o nome della banca
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setBackground(new Color(10, 60, 120)); // Blu scuro istituzionale
        header.setLayout(new BorderLayout());

        JLabel title = new JLabel("Banca di Alvise Sacconato & Alessandro Zago", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(10, 0, 5, 0));

        JLabel subtitle = new JLabel("Gestione sicura e affidabile del tuo conto", SwingConstants.CENTER);
        subtitle.setForeground(Color.LIGHT_GRAY);
        subtitle.setFont(new Font("SansSerif", Font.ITALIC, 16));
        subtitle.setBorder(new EmptyBorder(0, 0, 10, 0));

        header.add(title, BorderLayout.NORTH);
        header.add(subtitle, BorderLayout.SOUTH);

        return header;
    }

    // Crea il pannello di login e registrazione usando un JTabbedPane
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab "Login"
        JPanel loginTab = new JPanel(new GridBagLayout());
        loginTab.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);
        JButton loginButton = new JButton("Accedi");
        loginButton.setBackground(new Color(10, 60, 120));
        loginButton.setForeground(Color.WHITE);

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
        regTab.setBackground(Color.WHITE);
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(10, 10, 10, 10);
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
        regButton.setBackground(new Color(10, 60, 120));
        regButton.setForeground(Color.WHITE);

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
                    JOptionPane.showMessageDialog(BankGUI.this, "Devi avere almeno 18 anni. Riprova.");
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

    // Crea il pannello principale con informazioni utente e pulsanti di operazioni
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Area informazioni utente con font migliorato
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        infoArea.setBackground(new Color(245, 245, 245));
        infoArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        panel.add(new JScrollPane(infoArea), BorderLayout.CENTER);

        // Pannello bottoni con layout a griglia e stile uniforme
        buttonPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnPreleva = new JButton("Preleva");
        JButton btnDeposita = new JButton("Deposita");
        JButton btnInvesti = new JButton("Investi");
        JButton btnConcludi = new JButton("Concludi Inv.");
        JButton btnAvanza = new JButton("Avanza Mese");
        JButton btnStorico = new JButton("Storico");
        JButton btnLogout = new JButton("Logout");
        JButton btnEsci = new JButton("Esci");

        // Imposta colori e stile ai pulsanti
        JButton[] buttons = { btnPreleva, btnDeposita, btnInvesti, btnConcludi, btnAvanza, btnStorico, btnLogout,
                btnEsci };
        for (JButton btn : buttons) {
            btn.setBackground(new Color(10, 60, 120));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        }

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
                        double preleva = Math.round(Double.parseDouble(sPrelevare));
                        if (preleva > currentUser.getConto().getBanca() || preleva <= 0) {
                            JOptionPane.showMessageDialog(BankGUI.this, "Operazione non valida: importo errato!");
                        } else {
                            currentUser.getConto().preleva(preleva);
                            JOptionPane.showMessageDialog(BankGUI.this, "Hai prelevato: " + preleva + " €");
                            StoricoTransazioni.aggiungiTransazione(currentUser.getUsername(),
                                    "Prelievo: " + preleva + "€ (Banca: " + currentUser.getConto().getBanca() +
                                            ", Portafoglio: " + currentUser.getConto().getPortafoglio() + ")");
                            GestioneUtenti.saveUtenti(utenti);
                            refreshUserInfo();
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(BankGUI.this, "Valore non numerico!");
                    }
                }
            }
        });

        btnDeposita.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sDepositare = JOptionPane.showInputDialog(BankGUI.this, "Inserisci l'importo da depositare:");
                if (sDepositare != null) {
                    try {
                        double deposita = Math.round(Double.parseDouble(sDepositare));
                        if (deposita > currentUser.getConto().getPortafoglio() || deposita <= 0) {
                            JOptionPane.showMessageDialog(BankGUI.this, "Operazione non valida: importo errato!");
                        } else {
                            currentUser.getConto().deposita(deposita);
                            JOptionPane.showMessageDialog(BankGUI.this, "Hai depositato: " + deposita + " €");
                            StoricoTransazioni.aggiungiTransazione(currentUser.getUsername(),
                                    "Deposito: " + deposita + "€ (Banca: " + currentUser.getConto().getBanca() +
                                            ", Portafoglio: " + currentUser.getConto().getPortafoglio() + ")");
                            GestioneUtenti.saveUtenti(utenti);
                            refreshUserInfo();
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(BankGUI.this, "Valore non numerico!");
                    }
                }
            }
        });

        btnInvesti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentUser.getConto().getInvestimento()) {
                    JOptionPane.showMessageDialog(BankGUI.this, "Hai già un investimento aperto!");
                } else {
                    if (currentUser.getConto().getBanca() <= 0) {
                        JOptionPane.showMessageDialog(BankGUI.this, "Fondi insufficienti per investire!");
                        return;
                    }
                    String sInvestiti = JOptionPane.showInputDialog(BankGUI.this, "Inserisci l'importo da investire:");
                    if (sInvestiti != null) {
                        try {
                            double importoInvestito = Math.round(Double.parseDouble(sInvestiti));
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
                                        "Investimento avviato: " + importoInvestito + " € per " + investimentiMesi
                                                + " mesi");
                                StoricoTransazioni.aggiungiTransazione(currentUser.getUsername(),
                                        "Investimento iniziato: " + importoInvestito + "€ per " + investimentiMesi
                                                + " mesi");
                                GestioneUtenti.saveUtenti(utenti);
                                refreshUserInfo();
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(BankGUI.this, "Inserisci un numero valido.");
                        }
                    }
                }
            }
        });

        btnConcludi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentUser.getConto().getInvestimento()) {
                    currentUser.getConto().fineInvestimento();
                    JOptionPane.showMessageDialog(BankGUI.this, "Investimento concluso!");
                    StoricoTransazioni.aggiungiTransazione(currentUser.getUsername(), "Investimento concluso.");
                } else {
                    JOptionPane.showMessageDialog(BankGUI.this, "Nessun investimento in corso!");
                }
                GestioneUtenti.saveUtenti(utenti);
                refreshUserInfo();
            }
        });

        btnAvanza.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentUser.getConto().nextMonth();
                JOptionPane.showMessageDialog(BankGUI.this, "Avanzamento di un mese");
                StoricoTransazioni.aggiungiTransazione(currentUser.getUsername(),
                        "Avanzamento mese: " + currentUser.getConto().statoConto().replace("\n", " | "));
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
                scrollPane.setPreferredSize(new Dimension(850, 500));
                JOptionPane.showMessageDialog(BankGUI.this, scrollPane, "Storico Transazioni",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(BankGUI.this, "Logout effettuato!");
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

    // Aggiorna la sezione info con i dati dell'utente
    private void refreshUserInfo() {
        if (currentUser != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Utente: ").append(currentUser.getUsername()).append("\n");
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