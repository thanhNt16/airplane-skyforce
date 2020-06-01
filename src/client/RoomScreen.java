package client;

import static common.Constants.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class RoomScreen extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton startGameBtn, backBtn, refreshBtn;
    private JLabel titleLb;

    private JList<String> playerList;
    private DefaultListModel<String> players = new DefaultListModel<String>();

	
	public RoomScreen(int width, int height) {
		setSize(width, height);
        setLayout(null);
        initUI();
        setVisible(true);
	}
	
	public void initUI() {
		refreshPlayerList();
    	
    	startGameBtn = new JButton("Start Game");
        backBtn = new JButton("Back");
        refreshBtn = new JButton("Refresh");
        
        titleLb = new JLabel("Room  " + AppState.getRoom(), SwingConstants.CENTER);
        backBtn.setBounds(60, 20, 80, 40);
        backBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 12));
        
        refreshBtn.setBounds(250, 320, 150, 50);
        refreshBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 14));
        
        startGameBtn.setBounds(440, 320, 150, 50);
        startGameBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 14));

        titleLb.setBounds(250, 50, 390, 70);
        titleLb.setFont(new Font("Serif", Font.BOLD, 32));

        startGameBtn.addActionListener(this);
        backBtn.addActionListener(this);
        refreshBtn.addActionListener(this);
        
        playerList = new JList<String>(players);
        playerList.setBorder(BorderFactory.createLineBorder(Color.black));
        playerList.setBounds(250, 110, 400, 150);

        add(backBtn);
        add(refreshBtn);
        add(startGameBtn);
        add(titleLb);
        add(playerList);
	}
	
	public void refreshPlayerList() {
    	players.clear();
    	Client.handler.sendMessage("GET_PLAYERS__");
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	for (String p : AppState.getPlayerList()) {
    		players.addElement(p);
    	}
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == startGameBtn) {
			startGame();
        } else if (e.getSource() == backBtn) {
        	ScreenManager.getInstance().navigate(HOME_SCREEN);
        } else if (e.getSource() == refreshBtn) {
        	refreshPlayerList();
        }
	}
	
	public void startGame() {
		ScreenManager.getInstance().navigate(INGAME_SCREEN);
	}

}
