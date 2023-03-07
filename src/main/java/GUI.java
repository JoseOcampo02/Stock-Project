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
import javax.swing.JTextField;

public class GUI {

	private JFrame frame;
	private JTable table;
	private JTable table_1;

	
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
		btnNewButton.setBackground(new Color(212, 172, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(629, 16, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(212, 172, 255));
		panel.setBounds(108, 118, 313, 376);
		frame.getContentPane().add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("Graph");
		panel.add(lblNewLabel_1);
		
		table = new JTable();
		panel.add(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(212, 172, 255));
		panel_1.setBounds(448, 103, 243, 290);
		frame.getContentPane().add(panel_1);
		
		JLabel lblNewLabel_4 = new JLabel("Favorite Stocks");
		panel_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel = new JLabel("WallStreetBits");
		lblNewLabel.setForeground(new Color(212, 172, 255));
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 35));
		lblNewLabel.setBounds(20, 16, 289, 70);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(212, 172, 255));
		panel_2.setBounds(6, 98, 78, 417);
		frame.getContentPane().add(panel_2);
		
		JLabel lblNewLabel_2 = new JLabel("Selections");
		panel_2.add(lblNewLabel_2);
		
		table_1 = new JTable();
		panel_2.add(table_1);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBackground(new Color(212, 172, 255));
		panel_1_1.setBounds(448, 417, 243, 98);
		frame.getContentPane().add(panel_1_1);
		
		JLabel lblNewLabel_3 = new JLabel("Statistics");
		panel_1_1.add(lblNewLabel_3);
	}
}
