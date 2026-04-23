package currencyconverter;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import java.text.DecimalFormat;


/**
 * CurrencyConverter - A modern Java Swing app with real-time exchange rates.
 * Uses ExchangeRate-API (free tier) to fetch live rates.
 *
 * HOW TO RUN:
 *   1. Compile:  javac -cp "lib/json-20231013.jar" -d out src/currencyconverter/*.java
 *   2. Run:      java  -cp "out;lib/json-20231013.jar" currencyconverter.CurrencyConverter
 *      (On Mac/Linux use ':' instead of ';' in classpath)
 *
 * FREE API KEY:
 *   Get your free key at https://app.exchangerate-api.com/sign-up
 *   Replace YOUR_API_KEY_HERE below with your key.
 *   Without a key the app uses offline fallback rates automatically.
 */
public class CurrencyConverter extends JFrame {

    // ─── API ──────────────────────────────────────────────────────────────────
    private static final String API_KEY = "YOUR_API_KEY_HERE";   // ← paste your key
    private static final String API_URL =
            "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    // ─── Currencies ───────────────────────────────────────────────────────────
    private static final String[] CURRENCIES = {
        "USD","EUR","GBP","INR","JPY","AUD","CAD","CHF","CNY","AED",
        "SGD","HKD","SEK","NOK","DKK","NZD","ZAR","BRL","MXN","KRW",
        "TRY","SAR","THB","IDR","MYR","PHP","PKR","EGP","NGN","BDT"
    };

    private static final String[] CURRENCY_NAMES = {
        "US Dollar","Euro","British Pound","Indian Rupee","Japanese Yen",
        "Australian Dollar","Canadian Dollar","Swiss Franc","Chinese Yuan","UAE Dirham",
        "Singapore Dollar","Hong Kong Dollar","Swedish Krona","Norwegian Krone","Danish Krone",
        "New Zealand Dollar","South African Rand","Brazilian Real","Mexican Peso","South Korean Won",
        "Turkish Lira","Saudi Riyal","Thai Baht","Indonesian Rupiah","Malaysian Ringgit",
        "Philippine Peso","Pakistani Rupee","Egyptian Pound","Nigerian Naira","Bangladeshi Taka"
    };

    private static final String[] CURRENCY_SYMBOLS = {
        "$","€","£","₹","¥","A$","C$","CHF","¥","د.إ",
        "S$","HK$","kr","kr","kr","NZ$","R","R$","MX$","₩",
        "₺","﷼","฿","Rp","RM","₱","₨","£","₦","৳"
    };

    // ─── UI Components ────────────────────────────────────────────────────────
    private JComboBox<String> fromCurrencyBox;
    private JComboBox<String> fromNameBox;
    private JComboBox<String> toCurrencyBox;
    private JComboBox<String> toNameBox;
    private JTextField amountField;
    private JLabel resultLabel;
    private JLabel rateLabel;
    private JLabel statusLabel;
    private JButton convertBtn;
    private JButton swapBtn;

    // ─── Colors ───────────────────────────────────────────────────────────────
    private static final Color BG_DARK    = new Color(10, 14, 26);
    private static final Color CARD_BG    = new Color(18, 24, 42);
    private static final Color ACCENT     = new Color(99, 179, 237);
    private static final Color ACCENT2    = new Color(154, 117, 243);
    private static final Color TEXT_WHITE = new Color(237, 242, 247);
    private static final Color TEXT_MUTED = new Color(113, 128, 150);
    private static final Color SUCCESS    = new Color(72, 199, 142);
    private static final Color BORDER_CLR = new Color(45, 55, 75);

    // ─── Constructor ──────────────────────────────────────────────────────────
    public CurrencyConverter() {
        setTitle("Currency Converter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 680);
        setResizable(false);
        setLocationRelativeTo(null);
        setBackground(BG_DARK);

        buildUI();
        setVisible(true);
    }

    // ─── UI Builder ───────────────────────────────────────────────────────────
    private void buildUI() {
        JPanel root = new GradientPanel();
        root.setLayout(new BorderLayout());
        root.setBorder(new EmptyBorder(30, 30, 30, 30));
        setContentPane(root);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Currency Converter");
        title.setFont(new Font("Georgia", Font.BOLD, 28));
        title.setForeground(TEXT_WHITE);

        JLabel subtitle = new JLabel("Real-Time Exchange Rates");
        subtitle.setFont(new Font("Georgia", Font.ITALIC, 13));
        subtitle.setForeground(ACCENT);

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 2));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(subtitle);
        header.add(titlePanel, BorderLayout.WEST);

        statusLabel = new JLabel("● Ready");
        statusLabel.setFont(new Font("Monospaced", Font.PLAIN, 11));
        statusLabel.setForeground(SUCCESS);
        header.add(statusLabel, BorderLayout.EAST);

        root.add(header, BorderLayout.NORTH);

        // Main card
        JPanel card = new RoundedPanel(20, CARD_BG);
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(28, 28, 28, 28));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // ── Amount ────────────────────────────────────────────────────────────
        gbc.gridy = 0;
        card.add(makeLabel("Amount"), gbc);

        gbc.gridy = 1;
        amountField = makeTextField("Enter amount...");
        card.add(amountField, gbc);

        // ── From Currency ─────────────────────────────────────────────────────
        gbc.gridy = 2;
        card.add(makeLabel("From Currency"), gbc);

        gbc.gridy = 3;
        JPanel fromPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        fromPanel.setOpaque(false);
        fromCurrencyBox = makeComboBox(CURRENCIES);
        fromNameBox     = makeComboBox(CURRENCY_NAMES);
        syncComboBoxes(fromCurrencyBox, fromNameBox);
        fromPanel.add(fromCurrencyBox);
        fromPanel.add(fromNameBox);
        card.add(fromPanel, gbc);

        // ── Swap Button ───────────────────────────────────────────────────────
        gbc.gridy = 4;
        gbc.insets = new Insets(14, 80, 14, 80);
        swapBtn = new JButton("⇅  Swap Currencies");
        styleSwapButton(swapBtn);
        swapBtn.addActionListener(e -> swapCurrencies());
        card.add(swapBtn, gbc);
        gbc.insets = new Insets(8, 0, 8, 0);

        // ── To Currency ───────────────────────────────────────────────────────
        gbc.gridy = 5;
        card.add(makeLabel("To Currency"), gbc);

        gbc.gridy = 6;
        JPanel toPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        toPanel.setOpaque(false);
        toCurrencyBox = makeComboBox(CURRENCIES);
        toNameBox     = makeComboBox(CURRENCY_NAMES);
        syncComboBoxes(toCurrencyBox, toNameBox);
        toCurrencyBox.setSelectedIndex(3); // default INR
        toNameBox.setSelectedIndex(3);
        toPanel.add(toCurrencyBox);
        toPanel.add(toNameBox);
        card.add(toPanel, gbc);

        // ── Convert Button ────────────────────────────────────────────────────
        gbc.gridy = 7;
        gbc.insets = new Insets(20, 0, 8, 0);
        convertBtn = new JButton("Convert Now →");
        styleConvertButton(convertBtn);
        convertBtn.addActionListener(e -> performConversion());
        card.add(convertBtn, gbc);
        gbc.insets = new Insets(8, 0, 8, 0);

        // ── Result Panel ──────────────────────────────────────────────────────
        gbc.gridy = 8;
        JPanel resultPanel = new RoundedPanel(12, new Color(26, 35, 55));
        resultPanel.setLayout(new GridLayout(3, 1, 0, 6));
        resultPanel.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel resultTitle = new JLabel("CONVERTED AMOUNT");
        resultTitle.setFont(new Font("Monospaced", Font.PLAIN, 10));
        resultTitle.setForeground(TEXT_MUTED);

        resultLabel = new JLabel("—");
        resultLabel.setFont(new Font("Georgia", Font.BOLD, 30));
        resultLabel.setForeground(ACCENT);

        rateLabel = new JLabel("Enter amount and click Convert");
        rateLabel.setFont(new Font("Georgia", Font.ITALIC, 12));
        rateLabel.setForeground(TEXT_MUTED);

        resultPanel.add(resultTitle);
        resultPanel.add(resultLabel);
        resultPanel.add(rateLabel);
        card.add(resultPanel, gbc);

        JPanel center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(20, 0, 0, 0));
        center.add(card, BorderLayout.CENTER);
        root.add(center, BorderLayout.CENTER);

        // Footer
        JLabel footer = new JLabel("Powered by ExchangeRate-API  •  Java Swing", SwingConstants.CENTER);
        footer.setFont(new Font("Monospaced", Font.PLAIN, 10));
        footer.setForeground(TEXT_MUTED);
        footer.setBorder(new EmptyBorder(14, 0, 0, 0));
        root.add(footer, BorderLayout.SOUTH);
    }

    // ─── Conversion Logic ─────────────────────────────────────────────────────
    private void performConversion() {
        String amountText = amountField.getText().trim();

        if (amountText.isEmpty() || amountText.equals("Enter amount...")) {
            showError("Please enter an amount.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText.replaceAll(",", ""));
        } catch (NumberFormatException ex) {
            showError("Invalid amount. Please enter a number.");
            return;
        }

        if (amount <= 0) { showError("Amount must be greater than 0."); return; }

        String from = (String) fromCurrencyBox.getSelectedItem();
        String to   = (String) toCurrencyBox.getSelectedItem();

        if (from.equals(to)) {
            displayResult(amount, from, to, 1.0);
            return;
        }

        convertBtn.setText("Fetching rates...");
        convertBtn.setEnabled(false);
        statusLabel.setText("● Fetching...");
        statusLabel.setForeground(new Color(246, 173, 85));

        SwingWorker<Double, Void> worker = new SwingWorker<>() {
            @Override protected Double doInBackground() throws Exception {
                return fetchRate(from, to);
            }

            @Override protected void done() {
                try {
                    double rate = get();
                    displayResult(amount, from, to, rate);
                    statusLabel.setText("● Live Rate");
                    statusLabel.setForeground(SUCCESS);
                } catch (Exception ex) {
                    double rate = getOfflineRate(from, to);
                    displayResult(amount, from, to, rate);
                    statusLabel.setText("● Offline Rate");
                    statusLabel.setForeground(new Color(252, 129, 74));
                }
                convertBtn.setText("Convert Now →");
                convertBtn.setEnabled(true);
            }
        };
        worker.execute();
    }

    private double fetchRate(String from, String to) throws Exception {
        if (API_KEY.equals("YOUR_API_KEY_HERE")) throw new Exception("No API key");
        String urlStr = API_URL + from;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();

        JSONObject json = new JSONObject(sb.toString());
        if (!"success".equals(json.getString("result")))
            throw new Exception("API error: " + json.optString("error-type"));

        return json.getJSONObject("conversion_rates").getDouble(to);
    }

    // ─── Offline fallback rates (USD base) ───────────────────────────────────
    private static final double[] USD_RATES = {
        1.0, 0.92, 0.79, 83.12, 149.50, 1.53, 1.36, 0.89, 7.24, 3.67,
        1.34, 7.82, 10.42, 10.55, 6.89, 1.63, 18.63, 4.97, 17.15, 1325.0,
        30.45, 3.75, 35.12, 15650.0, 4.72, 56.50, 278.5, 30.90, 1470.0, 109.5
    };

    private double getOfflineRate(String from, String to) {
        int fromIdx = indexOf(CURRENCIES, from);
        int toIdx   = indexOf(CURRENCIES, to);
        if (fromIdx < 0 || toIdx < 0) return 1.0;
        double fromUSD = USD_RATES[fromIdx];
        double toUSD   = USD_RATES[toIdx];
        return toUSD / fromUSD;
    }

    private int indexOf(String[] arr, String val) {
        for (int i = 0; i < arr.length; i++) if (arr[i].equals(val)) return i;
        return -1;
    }

    private void displayResult(double amount, String from, String to, double rate) {
        double converted = amount * rate;
        String toSymbol  = CURRENCY_SYMBOLS[indexOf(CURRENCIES, to)];
        DecimalFormat df = new DecimalFormat("#,##0.00");
        resultLabel.setText(toSymbol + " " + df.format(converted));
        rateLabel.setText("1 " + from + " = " + df.format(rate) + " " + to
                + "   |   " + df.format(amount) + " " + from);
    }

    private void showError(String msg) {
        resultLabel.setText("Error");
        rateLabel.setText(msg);
        resultLabel.setForeground(new Color(252, 129, 74));
        statusLabel.setText("● Error");
        statusLabel.setForeground(new Color(252, 129, 74));
        resultLabel.setForeground(ACCENT); // reset for next time
    }

    // ─── Swap ─────────────────────────────────────────────────────────────────
    private void swapCurrencies() {
        int fi = fromCurrencyBox.getSelectedIndex();
        int ti = toCurrencyBox.getSelectedIndex();
        fromCurrencyBox.setSelectedIndex(ti);
        toCurrencyBox.setSelectedIndex(fi);
    }

    // ─── Combo sync ───────────────────────────────────────────────────────────
    private void syncComboBoxes(JComboBox<String> codeBox, JComboBox<String> nameBox) {
        codeBox.addActionListener(e -> {
            if (!e.getActionCommand().equals("bypass"))
                nameBox.setSelectedIndex(codeBox.getSelectedIndex());
        });
        nameBox.addActionListener(e -> {
            if (!e.getActionCommand().equals("bypass"))
                codeBox.setSelectedIndex(nameBox.getSelectedIndex());
        });
    }

    // ─── Styling helpers ──────────────────────────────────────────────────────
    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Monospaced", Font.BOLD, 11));
        lbl.setForeground(TEXT_MUTED);
        return lbl;
    }

    private JTextField makeTextField(String placeholder) {
        JTextField tf = new JTextField() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(TEXT_MUTED);
                    g2.setFont(new Font("Georgia", Font.ITALIC, 13));
                    g2.drawString(placeholder, 12, getHeight() / 2 + 5);
                }
            }
        };
        tf.setFont(new Font("Georgia", Font.PLAIN, 18));
        tf.setForeground(TEXT_WHITE);
        tf.setBackground(new Color(26, 35, 55));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(10, 12, 10, 12)));
        tf.setCaretColor(ACCENT);
        return tf;
    }

    private JComboBox<String> makeComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(new Font("Georgia", Font.PLAIN, 13));
        cb.setForeground(TEXT_WHITE);
        cb.setBackground(new Color(26, 35, 55));
        cb.setBorder(BorderFactory.createLineBorder(BORDER_CLR, 1));
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean sel, boolean focus) {
                super.getListCellRendererComponent(list, value, index, sel, focus);
                setBackground(sel ? BORDER_CLR : new Color(26, 35, 55));
                setForeground(TEXT_WHITE);
                setBorder(new EmptyBorder(6, 10, 6, 10));
                return this;
            }
        });
        return cb;
    }

    private void styleConvertButton(JButton btn) {
        btn.setFont(new Font("Georgia", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(ACCENT);
        btn.setBorder(new EmptyBorder(14, 20, 14, 20));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(66, 153, 225));
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBackground(ACCENT);
            }
        });
    }

    private void styleSwapButton(JButton btn) {
        btn.setFont(new Font("Georgia", Font.PLAIN, 13));
        btn.setForeground(ACCENT2);
        btn.setBackground(new Color(30, 22, 50));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT2, 1, true),
                new EmptyBorder(8, 14, 8, 14)));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(45, 32, 70));
            }
            @Override public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(30, 22, 50));
            }
        });
    }

    // ─── Custom Panels ────────────────────────────────────────────────────────
    static class GradientPanel extends JPanel {
        @Override protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, BG_DARK, getWidth(), getHeight(),
                    new Color(15, 20, 40));
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
            // subtle radial glow
            g2.setColor(new Color(99, 179, 237, 18));
            g2.fillOval(-100, -100, 400, 400);
        }
    }

    static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color bg;
        RoundedPanel(int radius, Color bg) {
            this.radius = radius; this.bg = bg;
            setOpaque(false);
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
        }
    }

    // ─── Main ─────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(CurrencyConverter::new);
    }
}
