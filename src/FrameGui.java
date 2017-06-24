import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import javax.swing.JToggleButton;

public class FrameGui extends Thread{

	private JFrame frame;
	private JTable runnerTable;
	private JScrollPane runnerScrollPane;
	private JButton cancelButton;
	private JLabel ipLabel;
	private JTextField ipTextField;
	private JLabel portLabel;
	private JTextField portTextField;
	private JToggleButton connectButton;
	private JLabel antennaButton;
	private JComboBox antennaComboBox;
	private JToggleButton startButton;
	private JButton setButton;
	private JLabel tagLabel;
	private JLabel tagSetLabel;
	
	private String[] tableHeader = {"user_event_id", "user_name"};
	private String[][] tableData = null;
	
	private Database db;
	

	public static void main(String[] args) {
		FrameGui frame = new FrameGui();
		frame.frame.setVisible(true);
		frame.start();
	}
	
	public void run() {
		while (true) {
			try {
				update();
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public FrameGui() {
		initialize();
		db = new Database();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        int reply = JOptionPane.showConfirmDialog(frame, 
		            "Are you sure to close this window?", "Really Closing?", 
		            JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION){
		        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        }
		    }
		});
		runnerScrollPane = new JScrollPane();
		runnerScrollPane.setBounds(0, 0, 563, 565);
		frame.getContentPane().add(runnerScrollPane);
		
		
		runnerTable = new JTable(tableData, tableHeader);
		runnerTable.setModel(new DefaultTableModel(tableData, tableHeader) {
			private static final long serialVersionUID = -9098365186691267440L;
			public boolean isCellEditable(int row, int column){
				return false;
			}
		});
		runnerScrollPane.setViewportView(runnerTable);
		
		tagSetLabel = new JLabel("Tag ID");
		tagSetLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tagSetLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tagSetLabel.setBounds(653, 173, 50, 20);
		frame.getContentPane().add(tagSetLabel);
		
		tagLabel = new JLabel("ABCABCABCABCABCABCABCABCABCABC");
		tagLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tagLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tagLabel.setBounds(575, 201, 207, 16);
		frame.getContentPane().add(tagLabel);
		
		setButton = new JButton("Set");
		setButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int reply = JOptionPane.showConfirmDialog(frame, 
		            "Are you sure to set tag to runner ?", "Really Set Tag?", 
		            JOptionPane.YES_NO_OPTION);
		        if (reply == JOptionPane.YES_OPTION){
		        	System.out.println("Set");
		        }
			}
		});
		setButton.setBounds(575, 230, 103, 25);
		frame.getContentPane().add(setButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(679, 230, 103, 25);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Cancel");
			}
		});
		frame.getContentPane().add(cancelButton);
		
		ipLabel = new JLabel("IP");
		ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ipLabel.setBounds(575, 16, 25, 16);
		frame.getContentPane().add(ipLabel);
		
		ipTextField = new JTextField();
		ipTextField.setBounds(612, 13, 170, 22);
		frame.getContentPane().add(ipTextField);
		ipTextField.setColumns(10);
		
		portLabel = new JLabel("Port");
		portLabel.setHorizontalAlignment(SwingConstants.CENTER);
		portLabel.setBounds(575, 46, 25, 16);
		frame.getContentPane().add(portLabel);
		
		portTextField = new JTextField();
		portTextField.setBounds(612, 43, 170, 22);
		frame.getContentPane().add(portTextField);
		portTextField.setColumns(10);
		
		connectButton = new JToggleButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (connectButton.isSelected()) {
					System.out.println("Connect");
					connectButton.setText("Disconnect");
				} else {
					System.out.println("DisConnect");
					connectButton.setText("Connect");
				}
					
			}
		});
		connectButton.setBounds(575, 73, 207, 25);
		frame.getContentPane().add(connectButton);
		
		antennaButton = new JLabel("Antenna");
		antennaButton.setBounds(575, 106, 56, 16);
		frame.getContentPane().add(antennaButton);
		
		antennaComboBox = new JComboBox();
		antennaComboBox.setBounds(635, 106, 147, 22);
		frame.getContentPane().add(antennaComboBox);
		
		startButton = new JToggleButton("Start");
		startButton.setBounds(575, 135, 207, 25);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (startButton.isSelected()) {
					System.out.println("Start");
					startButton.setText("Stop");
				} else {
					System.out.println("Stop");
					startButton.setText("Start");
				}
					
			}
		});
		frame.getContentPane().add(startButton);
	}
	
	private void update() {
		tableData = db.getTable();
		DefaultTableModel model = (DefaultTableModel)runnerTable.getModel();
		model.setNumRows(tableData.length);
		for (int i = 0; i < tableData.length; i++) {
			for (int j = 0; j < tableData[i].length; j++) {
				model.setValueAt(tableData[i][j], i, j);
			}
		}
	}
	
}
