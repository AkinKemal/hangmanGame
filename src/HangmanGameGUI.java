import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;

public class HangmanGameGUI extends JFrame implements ActionListener {

    Operations operations;

    public JPanel mainPanel, main01Panel, main01lifeBarPanel, gameLifePanel, wordLifePanel, main02Panel, hangmanDrawingPanel, main04A1WordPanel, main04A2WordPanel, main04A3WordPanel, main04A4WordPanel, charInputFieldPanel, charButtonPanel, wordPanel, sendLetterPanel, warningMessagesPanel, main05HintPanel, selectHintPanel, outputHintPanel;
    public JLabel imageLabel, gameLifeLabel, wordLifeLabel;
    public JButton levelHint1st, levelHint2nd, levelHint3rd, levelHint4th;
    public JButton sendLetter;
    public JButton[] charLetters;
    public JTextField charInputField, wordOutput, warningMessageOutput;
    public JTextArea hintOutput;

    public char selectedLetter;
    public String fileName;
    public String randomFileName;

    public HangmanGameGUI(String fileName, String randomFileName) {

        this.fileName = fileName;
        this.randomFileName = randomFileName;
        operations = new Operations(fileName, randomFileName);
        operations.create();

        // Set JFrame properties
        setTitle("Doftdare Company");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font font = new Font("Arial", Font.BOLD, 16);

        // main05 panel
        levelHint1st = new JButton("1st Hint Level");
        levelHint2nd = new JButton("2nd Hint Level");
        levelHint3rd = new JButton("3rd Hint Level");
        levelHint4th = new JButton("4th Hint Level");
        sendLetter = new JButton("SEND");

        Font hintButtonsFont = new Font("Arial", Font.BOLD, 20);
        levelHint1st.setFont(hintButtonsFont);
        levelHint2nd.setFont(hintButtonsFont);
        levelHint3rd.setFont(hintButtonsFont);
        levelHint4th.setFont(hintButtonsFont);

        levelHint1st.setBackground(new Color(0, 100, 200));
        levelHint1st.setForeground(new Color(211, 211, 211));
        levelHint1st.setBorderPainted(false);
        levelHint1st.setFocusPainted(false);
        levelHint2nd.setBackground(new Color(0, 100, 200));
        levelHint2nd.setForeground(new Color(211, 211, 211));
        levelHint2nd.setBorderPainted(false);
        levelHint2nd.setFocusPainted(false);
        levelHint3rd.setBackground(new Color(0, 100, 200));
        levelHint3rd.setForeground(new Color(211, 211, 211));
        levelHint3rd.setBorderPainted(false);
        levelHint3rd.setFocusPainted(false);
        levelHint4th.setBackground(new Color(0, 100, 200));
        levelHint4th.setForeground(new Color(211, 211, 211));
        levelHint4th.setBorderPainted(false);
        levelHint4th.setFocusPainted(false);

        Dimension hintButtonsSize = new Dimension(150, 100);
        levelHint1st.setPreferredSize(hintButtonsSize);
        levelHint2nd.setPreferredSize(hintButtonsSize);
        levelHint3rd.setPreferredSize(hintButtonsSize);
        levelHint4th.setPreferredSize(hintButtonsSize);

        Insets hintButtonsMargin = new Insets(10, 10, 10, 10);
        levelHint1st.setMargin(hintButtonsMargin);
        levelHint2nd.setMargin(hintButtonsMargin);
        levelHint3rd.setMargin(hintButtonsMargin);
        levelHint4th.setMargin(hintButtonsMargin);

        selectHintPanel = new JPanel();
        selectHintPanel.setLayout(new GridLayout(1, 4, 10, 10));
        selectHintPanel.add(levelHint1st);
        selectHintPanel.add(levelHint2nd);
        selectHintPanel.add(levelHint3rd);
        selectHintPanel.add(levelHint4th);

        Dimension dimensionHintOutput = new Dimension(300, 200);
        hintOutput = new JTextArea("Hint");
        hintOutput.setEditable(false);
        hintOutput.setFont(font);
        hintOutput.setLineWrap(true);
        hintOutput.setWrapStyleWord(true);

        JScrollPane hintScrollPane = new JScrollPane(hintOutput);
        hintScrollPane.setPreferredSize(dimensionHintOutput);

        outputHintPanel = new JPanel();
        outputHintPanel.setLayout(new GridLayout(1, 1, 20, 20));
        outputHintPanel.add(hintOutput, BorderLayout.CENTER);

        main05HintPanel = new JPanel();
        main05HintPanel.setLayout(new BorderLayout());
        main05HintPanel.add(selectHintPanel, BorderLayout.NORTH);
        main05HintPanel.add(outputHintPanel, BorderLayout.CENTER);

        // main04 panel
        Dimension dimensionWordOutput = new Dimension(500, 200);
        wordOutput = new JTextField();
        wordOutput.setPreferredSize(dimensionWordOutput);
        wordOutput.setEditable(false);
        wordOutput.setFont(font);
        wordOutput.setHorizontalAlignment(JLabel.CENTER);

        wordPanel = new JPanel();
        wordPanel.setLayout(new GridLayout(1, 1, 20, 20));
        wordPanel.add(wordOutput, BorderLayout.CENTER);

        Dimension dimensionCharInputField = new Dimension(500, 100);
        charInputField = new JTextField(" ");
        charInputField.setPreferredSize(dimensionCharInputField);
        charInputField.setEditable(true);
        charInputField.setFont(font);
        charInputField.setHorizontalAlignment(JLabel.CENTER);

        charInputFieldPanel = new JPanel();
        charInputFieldPanel.setLayout(new GridLayout(1, 1, 20, 20));
        charInputFieldPanel.add(charInputField, BorderLayout.CENTER);

        main04A1WordPanel = new JPanel();
        main04A1WordPanel.setLayout(new BorderLayout());
        main04A1WordPanel.add(wordPanel, BorderLayout.NORTH);
        main04A1WordPanel.add(charInputFieldPanel, BorderLayout.CENTER);

        charLetters = new JButton[26];
        for (char c = 'A'; c <= 'Z'; c++) {
            JButton button = new JButton(Character.toString(c));
            charLetters[c - 'A'] = button;
        }

        charButtonPanel = new JPanel();
        charButtonPanel.setLayout(new GridLayout(5, 7, 20, 20));

        for (JButton button : charLetters) {
            charButtonPanel.add(button, BorderLayout.CENTER);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    final String buttonText = button.getText().toLowerCase();
                    selectedLetter = buttonText.charAt(0);
                    charInputField.setText(String.valueOf(selectedLetter).toUpperCase(Locale.ENGLISH));
                }
            });
        }

        charButtonPanel.setEnabled(false);

        main04A2WordPanel = new JPanel();
        main04A2WordPanel.setLayout(new BorderLayout());
        main04A2WordPanel.add(main04A1WordPanel, BorderLayout.NORTH);
        main04A2WordPanel.add(charButtonPanel, BorderLayout.CENTER);

        sendLetter = new JButton("START");

        Font sendButtonsFont = new Font("Arial", Font.BOLD, 16);
        sendLetter.setFont(sendButtonsFont);

        sendLetter.setBackground(new Color(240, 128, 128));
        sendLetter.setForeground(new Color(211, 211, 211));
        sendLetter.setBorderPainted(true);
        sendLetter.setFocusPainted(true);

        Dimension sendButtonsSize = new Dimension(50, 50);
        sendLetter.setPreferredSize(sendButtonsSize);

        Insets sendButtonsMargin = new Insets(10, 10, 10, 10);
        sendLetter.setMargin(sendButtonsMargin);

        sendLetterPanel = new JPanel();
        sendLetterPanel.setLayout(new GridLayout(1, 1, 20, 20));
        sendLetterPanel.add(sendLetter, BorderLayout.CENTER);

        main04A3WordPanel = new JPanel();
        main04A3WordPanel.setLayout(new BorderLayout());
        main04A3WordPanel.add(main04A2WordPanel, BorderLayout.NORTH);
        main04A3WordPanel.add(sendLetterPanel, BorderLayout.CENTER);

        boolean[] isGUIControlsExecuted01 = {false};
        boolean[] isGUIControlsExecuted03 = {false};

        sendLetter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                charButtonPanel.setEnabled(true);
                sendLetter.setText("SEND");
                hintOutput.setText("");
                while (operations.numberOfRounds > 0 && operations.gameLifeCount > 0) {
                    if (!isGUIControlsExecuted01[0]) {
                        operations.GUIControl01();
                        isGUIControlsExecuted01[0] = true;
                        String wordOutputString = operations.printGameTable(operations.booleanArrayList);
                        wordOutput.setText(wordOutputString);
                        updateWordLifePanel();
                        updateGameLifePanel();
                    }
                    while (operations.maxGuesses > 0 && !operations.isFound && operations.gameLifeCount > 0) {
                        boolean temporary;
                        temporary = operations.sameLetterControl(selectedLetter);
                        if (temporary) {
                            if (selectedLetter != '\0') {
                                warningMessageOutput.setText("Warning! You entered a letter you used before!");
                            }
                            // int inputInteger = JOptionPane.showOptionDialog(null, "You entered a letter you used before. Please select a new letter.", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, charLetters, charLetters[0]);
                            // selectedLetter = charLetters[inputInteger].getText().charAt(0);
                            for (JButton button : charLetters) {
                                button.setEnabled(true); // butonların tıklanabilirliğini yeniden aktif hale getir
                                button.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        String buttonText = button.getText().toLowerCase();
                                        selectedLetter = buttonText.charAt(0);
                                        charInputField.setText(String.valueOf(selectedLetter).toUpperCase(Locale.ENGLISH));
                                    }
                                });
                            }
                            return;
                        } else {
                            warningMessageOutput.setText("");
                            operations.updateLetter(selectedLetter);
                            operations.GUIControl02();
                            String wordOutputString = operations.printGameTable(operations.booleanArrayList);
                            wordOutput.setText(wordOutputString);
                            updateWordLifePanel();
                            updateGameLifePanel();
                            if (operations.maxGuesses <= 0 || operations.isFound) {
                                isGUIControlsExecuted01[0] = false;
                                isGUIControlsExecuted03[0] = false;
                                wordOutput.setText("");
                                warningMessageOutput.setText(guessedResulted());
                            }
                        }
                        charInputField.setText("");
                        selectedLetter = '\0';
                    }
                    JFrame frame = new JFrame();
                    if (operations.gameLifeCount <= 0 || operations.numberOfRounds <= 0) {
                        JOptionPane.showMessageDialog(frame,
                                "Thank you for choosing us",
                                "WARNING!",
                                JOptionPane.WARNING_MESSAGE);
                        System.exit(0);
                    }
                    if (!isGUIControlsExecuted03[0]) {
                        operations.GUIControl03();
                        isGUIControlsExecuted03[0] = true;
                    }
                }
            }
        });


        sendLetter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        Dimension dimensionWarningOutput = new Dimension(500, 50);
        warningMessageOutput = new JTextField();
        warningMessageOutput.setPreferredSize(dimensionWarningOutput);
        warningMessageOutput.setEditable(false);
        warningMessageOutput.setFont(font);
        warningMessageOutput.setHorizontalAlignment(JLabel.CENTER);

        warningMessagesPanel = new JPanel();
        warningMessagesPanel.setLayout(new GridLayout(1, 1, 20, 20));
        warningMessagesPanel.add(warningMessageOutput, BorderLayout.CENTER);

        main04A4WordPanel = new JPanel();
        main04A4WordPanel.setLayout(new BorderLayout());
        main04A4WordPanel.add(main04A3WordPanel, BorderLayout.NORTH);
        main04A4WordPanel.add(warningMessagesPanel, BorderLayout.CENTER);

        // main02 panel
        ImageIcon imageIcon = new ImageIcon("hangman.png");
        Image image = imageIcon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(image);
        imageLabel = new JLabel(scaledIcon);
        imageLabel.setPreferredSize(new Dimension(400, 400));

        hangmanDrawingPanel = new JPanel();
        hangmanDrawingPanel.add(imageLabel);

        main02Panel = new JPanel();
        main02Panel.setLayout(new BorderLayout());
        main02Panel.add(main04A4WordPanel, BorderLayout.EAST);
        main02Panel.add(hangmanDrawingPanel, BorderLayout.CENTER);

        // main01 panel
        gameLifePanel = new JPanel();
        wordLifePanel = new JPanel();

        main01lifeBarPanel = new JPanel();
        main01lifeBarPanel.setLayout(new BorderLayout());
        main01lifeBarPanel.add(gameLifePanel, BorderLayout.NORTH);
        main01lifeBarPanel.add(wordLifePanel, BorderLayout.CENTER);

        main01Panel = new JPanel();
        main01Panel.setLayout(new BorderLayout());
        main01Panel.add(main01lifeBarPanel, BorderLayout.NORTH);
        main01Panel.add(main02Panel, BorderLayout.CENTER);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(main01Panel, BorderLayout.NORTH);
        mainPanel.add(main05HintPanel, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.NORTH);

        hintOutputUpdate();

        pack();
        setVisible(true);
        // Set window properties
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void hintOutputUpdate() {
        levelHint1st.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String temporary = operations.hint01ForGuessedLetterAndWords();
                hintOutput.setText(temporary);
            }
        });

        levelHint2nd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> temporaryArrayList = operations.hint02ForGuessedLetterAndWords();
                String temporary = convertArrayListToString(temporaryArrayList);
                hintOutput.setText(temporary);
            }
        });

        levelHint3rd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> temporaryArrayList = operations.hint03ForGuessedLetterAndWords();
                String temporary = convertArrayListToString(temporaryArrayList);
                hintOutput.setText(temporary);
            }
        });

        levelHint4th.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> temporaryArrayList = operations.hint04ForGuessedLetterAndWords();
                String temporary = convertArrayListToString(temporaryArrayList);
                hintOutput.setText(temporary);
            }
        });
    }

    public String convertArrayListToString(ArrayList<String> stringArrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : stringArrayList) {
            stringBuilder.append(s).append(", ");
        }
        // Son virgül ve boşluk karakterlerini silme
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }
        return stringBuilder.toString();
    }

    public void updateWordLifePanel() {
        int maxGuesses = operations.maxGuesses;
        wordLifePanel.removeAll();
        for (int i = 0; i < maxGuesses; i++) {
            ImageIcon imageIconDiamond = new ImageIcon("diamond.png");
            Image imageDiamond = imageIconDiamond.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
            ImageIcon scaledIconDiamond = new ImageIcon(imageDiamond);
            wordLifeLabel = new JLabel(scaledIconDiamond);
            wordLifeLabel.setPreferredSize(new Dimension(30, 30));
            wordLifePanel.add(wordLifeLabel, BorderLayout.CENTER);
        }
        wordLifePanel.revalidate();
        wordLifePanel.repaint();
    }

    public void updateGameLifePanel() {
        int numberOfRounds = operations.gameLifeCount;
        gameLifePanel.removeAll();
        for (int i = 0; i < numberOfRounds; i++) {
            ImageIcon imageIconHeart = new ImageIcon("redHeart.png");
            Image imageHeart = imageIconHeart.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
            ImageIcon scaledIconHeart = new ImageIcon(imageHeart);
            gameLifeLabel = new JLabel(scaledIconHeart);
            gameLifeLabel.setPreferredSize(new Dimension(30, 30));
            gameLifePanel.add(gameLifeLabel, BorderLayout.CENTER);
        }
        gameLifePanel.revalidate();
        gameLifePanel.repaint();
    }

    public String guessedResulted() {
        StringBuilder temporary = new StringBuilder();
        temporary.setLength(0);
        if (operations.maxGuesses > 0 && operations.gameLifeCount > 0) {
            temporary.append(" Congratulations, you guessed the word correctly!: ");
        } else {
            temporary.append(" Unfortunately, you couldn't guess the word!: ");
        }
        return String.valueOf(temporary);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}