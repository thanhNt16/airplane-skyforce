package client;

import static common.Constants.NORMAL_FONT;

import static common.Constants.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class HomeScreen extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton createGameBtn;
    private JButton joinGameBtn;
    private JButton quitGameBtn;
    private JLabel titleLb;

    public HomeScreen(int width, int height) {
        setSize(width, height);
        setLayout(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        createGameBtn = new JButton("Create Game");
        joinGameBtn = new JButton("Join Game");
        quitGameBtn = new JButton("Quit");

        titleLb = new JLabel("Du_Th_Sky", SwingConstants.CENTER);

        createGameBtn.setBounds(60, 320, 220, 50);
        createGameBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 24));
        joinGameBtn.setBounds(60, 396, 220, 50);
        joinGameBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 24));
        quitGameBtn.setBounds(60, 472, 220, 50);
        quitGameBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 24));

        titleLb.setBounds(65, 160, 390, 70);
        titleLb.setFont(new Font("Serif", Font.BOLD, 46));

        quitGameBtn.addActionListener(this);
        createGameBtn.addActionListener(this);
        joinGameBtn.addActionListener(this);

        add(createGameBtn);
        add(joinGameBtn);
        add(quitGameBtn);
        add(titleLb);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == quitGameBtn) {
            System.exit(0);
        } else if (e.getSource() == createGameBtn) {
            createNewGame();
        } else if (e.getSource() == joinGameBtn) {
            joinGame();
        }
    }

    private String enterPlayerName() {
        String name = JOptionPane.showInputDialog(
                this,
                "Enter player name:",
                "Player Name",
                JOptionPane.QUESTION_MESSAGE
        );
        return name;
    }

    private boolean validateName(String playerName) {
        if (playerName == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a nickname before starting game!"
            );
            return false;
        }

        if (playerName.length() < 1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Your nickname is too short(must be longer than 1)!"
            );
            return false;
        }

        if (playerName.length() > 10) {
            JOptionPane.showMessageDialog(
                    this,
                    "Your nickname is too long(must be shorter than 10)!"
            );
            return false;
        }
        return true;
    }

    private void createNewGame() {
        String playerName = enterPlayerName();
        if (!validateName(playerName)) {
            return;
        }

        Client.handler.sendMessage("CREATE_ROOM__" + playerName + "__");
        ScreenManager.getInstance().navigate(INGAME_SCREEN);
    }

    private void joinGame() {
        String playerName = enterPlayerName();
        if (!validateName(playerName)) {
            return;
        }

        // TODO: cho chon room
        int room = 1;
        
        Client.handler.sendMessage("JOIN_ROOM__" + room + "__"  + playerName + "__");
        ScreenManager.getInstance().navigate(INGAME_SCREEN);
    }
}
