package client;

import static common.Constants.NORMAL_FONT;

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

public class HomeScreen extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton refreshBtn;
	private JButton createGameBtn;
    private JButton joinGameBtn;
    private JButton backBtn;
    
    private JLabel titleLb;
    private JList<String> roomList;
    private DefaultListModel<String> rooms = new DefaultListModel<String>();;

    public HomeScreen(int width, int height) {
        setSize(width, height);
        setLayout(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
    	refreshRoomList();
    	
    	refreshBtn = new JButton("Refresh");
        createGameBtn = new JButton("Create Game");
        joinGameBtn = new JButton("Join Game");
        backBtn = new JButton("Back");

        titleLb = new JLabel("Welcome " + AppState.getName(), SwingConstants.CENTER);

        backBtn.setBounds(60, 20, 80, 40);
        backBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 12));
        
        
        refreshBtn.setBounds(250, 320, 150, 50);
        refreshBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 14));
        createGameBtn.setBounds(440, 320, 150, 50);
        createGameBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 14));
        joinGameBtn.setBounds(630, 320, 150, 50);
        joinGameBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 14));

        titleLb.setBounds(250, 50, 390, 70);
        titleLb.setFont(new Font("Serif", Font.BOLD, 32));

        createGameBtn.addActionListener(this);
        joinGameBtn.addActionListener(this);
        backBtn.addActionListener(this);
        refreshBtn.addActionListener(this);

        
        roomList = new JList<String>(rooms);
    	roomList.setBorder(BorderFactory.createLineBorder(Color.black));
        roomList.setBounds(250, 110, 400, 150);

        add(backBtn);
        add(refreshBtn);
        add(createGameBtn);
        add(joinGameBtn);
        add(titleLb);
        add(roomList);
    }
    
    public void refreshRoomList() {
    	rooms.clear();
    	Client.handler.sendMessage("GET_ROOM__");
    	try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	for (int room : AppState.getRoomList()) {
    		rooms.addElement("Room " + room);
    	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createGameBtn) {
            createNewGame();
        } else if (e.getSource() == joinGameBtn) {
            joinGame();
        } else if (e.getSource() == backBtn) {
        	ScreenManager.getInstance().navigate(PREPARE_SCREEN);
        } else if (e.getSource() == refreshBtn) {
        	refreshRoomList();
        }
    }

    private void createNewGame() {
        Client.handler.sendMessage("CREATE_ROOM__" + AppState.getName() + "__");
        ScreenManager.getInstance().navigate(ROOM_SCREEN);
    }

    private void joinGame() {
    	int index = roomList.getSelectedIndex();
    	int room = AppState.getRoomList().get(index);
    	AppState.setRoom(room);
        Client.handler.sendMessage("JOIN_ROOM__" + room + "__"  + AppState.getName() + "__");
        ScreenManager.getInstance().navigate(ROOM_SCREEN);
    }
}
