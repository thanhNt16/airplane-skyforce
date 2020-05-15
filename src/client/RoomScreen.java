package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class RoomScreen extends JPanel implements ActionListener {
	private JButton startGameBtn, backBtn;
    private JPanel wrapper;

	
	public RoomScreen(int width, int height) {
		setSize(width, height);
        setLayout(null);
        initUI();
        setVisible(true);
	}
	
	public void initUI() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
