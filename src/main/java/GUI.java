import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.JSlider;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;

public class GUI {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(new Color(212, 167, 255));
		frame.getContentPane().setBackground(new Color(174, 129, 255));
		frame.setBounds(100, 100, 752, 571);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(629, 6, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(212, 172, 255));
		panel.setBounds(36, 118, 243, 305);
		frame.getContentPane().add(panel);
		
		table = new JTable();
		panel.add(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(341, 118, 313, 110);
		frame.getContentPane().add(panel_1);
		
		JSlider slider = new JSlider();
		slider.setBounds(412, 240, 190, 29);
		frame.getContentPane().add(slider);
		
		JSlider slider_1 = new JSlider();
		slider_1.setBounds(412, 435, 190, 29);
		frame.getContentPane().add(slider_1);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(86, 435, 146, 20);
		frame.getContentPane().add(progressBar);
		
		JLabel lblNewLabel = new JLabel("WallStreetBits");
		lblNewLabel.setForeground(new Color(219, 199, 255));
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		lblNewLabel.setBounds(36, 29, 243, 57);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(341, 313, 313, 110);
		frame.getContentPane().add(panel_1_1);
	}
}
