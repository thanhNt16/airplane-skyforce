package client;

import static common.Constants.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class RoomScreen extends JPanel implements ActionListener {
	private JButton startGameBtn, backBtn;
    private JPanel wrapper;
    private JLabel titleLb;


	
	public RoomScreen(int width, int height) {
		setSize(width, height);
        setLayout(null);
        initUI();
        setVisible(true);
	}
	
	public void initUI() {
		wrapper = new JPanel();
    	JLabel room = new JLabel("Room 1");
    	wrapper.add(room);
    	startGameBtn = new JButton("Start Game");
        backBtn = new JButton("Back");
        
        titleLb = new JLabel("Room  ", SwingConstants.CENTER);
        backBtn.setBounds(60, 20, 80, 40);
        backBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 12));
        
        wrapper.setBorder(BorderFactory.createLineBorder(Color.black));
        wrapper.setBounds(250, 110, 400, 150);
        
        startGameBtn.setBounds(250, 320, 150, 50);
        startGameBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 14));

        titleLb.setBounds(250, 50, 390, 70);
        titleLb.setFont(new Font("Serif", Font.BOLD, 32));

        startGameBtn.addActionListener(this);
        backBtn.addActionListener(this);

        add(wrapper);
        add(backBtn);
        add(startGameBtn);
        add(titleLb);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == startGameBtn) {
			startGame();
        } else if (e.getSource() == backBtn) {
        	ScreenManager.getInstance().navigate(HOME_SCREEN);
        }  
	}
	public void startGame() {
		Client.handler.sendMessage("CREATE_ROOM__" + AppState.getName() + "__");
		ScreenManager.getInstance().navigate(INGAME_SCREEN);
	}

}
