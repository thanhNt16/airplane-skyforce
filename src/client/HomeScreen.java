package client;

import static common.Constants.NORMAL_FONT;

import static common.Constants.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class HomeScreen extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton createGameBtn;
    private JButton joinGameBtn;
    private JButton quitGameBtn, backBtn;
    private JLabel titleLb;
    private JTextField host, port;
    private JPanel wrapper;

    public HomeScreen(int width, int height) {
        setSize(width, height);
        setLayout(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
    	wrapper = new JPanel();
    	JLabel room = new JLabel("Room 1");
    	wrapper.add(room);
    	
        createGameBtn = new JButton("Create Game");
        joinGameBtn = new JButton("Join Game");
        backBtn = new JButton("Back");

        titleLb = new JLabel("Welcome " + AppState.getName(), SwingConstants.CENTER);

        backBtn.setBounds(60, 20, 80, 40);
        backBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 12));
        
        wrapper.setBorder(BorderFactory.createLineBorder(Color.black));
        wrapper.setBounds(250, 110, 400, 150);
        
        createGameBtn.setBounds(250, 320, 150, 50);
        createGameBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 14));
        joinGameBtn.setBounds(440, 320, 150, 50);
        joinGameBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 14));

        titleLb.setBounds(250, 50, 390, 70);
        titleLb.setFont(new Font("Serif", Font.BOLD, 32));

        createGameBtn.addActionListener(this);
        joinGameBtn.addActionListener(this);
        backBtn.addActionListener(this);

        add(wrapper);
        add(backBtn);
        add(createGameBtn);
        add(joinGameBtn);
        add(titleLb);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createGameBtn) {
            createNewGame();
        } else if (e.getSource() == joinGameBtn) {
            joinGame();
        } else if (e.getSource() == backBtn) {
        	ScreenManager.getInstance().navigate(PREPARE_SCREEN);
        }  
        
    }

    private ArrayList<String> enterPlayerName() {
    	host = new JTextField();
		port = new JTextField();
		JTextField name = new JTextField();
		
		Object[] message = {
				"host", host,
				"port", port,
				"name", name
		};
		int result = JOptionPane.showConfirmDialog(null, message, 
	               "Please Enter X and Y Values", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			System.out.println(host.getText());
			System.out.println(port.getText());
		}
		ArrayList<String> resultArr = new ArrayList<String>();
		resultArr.add(name.getText());
		resultArr.add(host.getText());
		resultArr.add(port.getText());
		return resultArr;
//        String name = JOptionPane.showInputDialog(
//                this,
//                message,
//                "Player Name",
//                JOptionPane.QUESTION_MESSAGE
//        );
//        return name;
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
    	// get(0) là name get(1) host get(2) port
//        String playerName = enterPlayerName().get(0);
//        if (!validateName(playerName)) {
//            return;
//        }

//        Client.handler.sendMessage("CREATE_ROOM__" + playerName + "__");
        ScreenManager.getInstance().navigate(ROOM_SCREEN);
    }

    private void joinGame() {
    	// get(0) là name get(1) host get(2) port

//        String playerName = enterPlayerName().get(0);
//        if (!validateName(playerName)) {
//            return;
//        }

        // TODO: cho chon room
//        int room = 1;
//        
//        Client.handler.sendMessage("JOIN_ROOM__" + room + "__"  + playerName + "__");
        ScreenManager.getInstance().navigate(ROOM_SCREEN);
    }
}
