package client;

import static common.Constants.*;
import static common.Constants.NORMAL_FONT;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PrepareScreen extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton createGameBtn;
    private JButton quitGameBtn;
    private JLabel titleLb, hostLabel, nameLabel;
    private JTextField host, name;
	
	public PrepareScreen(int width, int height) {
		setSize(width, height);
        setLayout(null);
        initUI();
        setVisible(true);
	}

	public void initUI() {
		createGameBtn = new JButton("Start");
        quitGameBtn = new JButton("Quit");
        name = new JTextField();
        host = new JTextField();

        titleLb = new JLabel("Skywar", SwingConstants.CENTER);
        hostLabel = new JLabel("Host: ", SwingConstants.CENTER);
        nameLabel = new JLabel("Name: ", SwingConstants.CENTER);
        
        createGameBtn.setBounds(340, 400, 200, 50);
        createGameBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 14));
        quitGameBtn.setBounds(340, 472, 200, 50);
        quitGameBtn.setFont(new Font(NORMAL_FONT, Font.PLAIN, 14));

        titleLb.setBounds(250, 50, 390, 70);
        titleLb.setFont(new Font("Serif", Font.BOLD, 46));
        
        hostLabel.setBounds(200, 180, 200, 20);
        hostLabel.setFont(new Font("Serif", Font.BOLD, 14));
        
        nameLabel.setBounds(200, 150, 200, 20);
        nameLabel.setFont(new Font("Serif", Font.BOLD, 14));
        
        name.setBounds(340, 150, 200, 20);
        host.setBounds(340, 180, 200, 20);
        
        quitGameBtn.addActionListener(this);
        createGameBtn.addActionListener(this);

        add(createGameBtn);
        add(quitGameBtn);
        add(name);
        add(host);
        add(titleLb);
        add(hostLabel);
        add(nameLabel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == quitGameBtn) {
            System.exit(0);
        } else if (e.getSource() == createGameBtn) {
            start();
        }
	}
	public void start() {
		AppState.setName(name.getText());
		Client.connect(host.getText());
		ScreenManager.getInstance().navigate(HOME_SCREEN);
	}
}
