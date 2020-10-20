package view;

import model.Album;
import model.DataModel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Die Klasse gui.TableView bringt die Daten des Modells model.DataModel zur Anzeige.
 * Die Daten werden über das MVC-Pattern von der Model-Klasse gelesen.
 * 
 * @author Aladin Boudouda
 *
 */

public class TableView extends JFrame {

	private JTable table;
	private DataModel model;

	private JTextField searchbar;
	
	public TableView(DataModel m){
		super("Plattensammlung");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		model = m;

		init();

		this.pack();
		this.setVisible(true);
		this.setResizable(false);
	}
	
	private void init(){
		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(75);
		table.getColumnModel().getColumn(0).setPreferredWidth(75);
		table.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
		table.getColumnModel().getColumn(1).setPreferredWidth(190);
		table.getColumnModel().getColumn(2).setPreferredWidth(110);
		table.getColumnModel().getColumn(3).setPreferredWidth(75);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for (int x = 1; x < 4; x++) {
			table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );
		}

		JScrollPane scroller = new JScrollPane(table);
		this.getContentPane().add(scroller, BorderLayout.EAST);

		JButton addBtn = new JButton("Album hinzufügen");
		addBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				InputDialog dialog = new InputDialog(model);
			}
		});

		JButton deleteBtn = new JButton("Album löschen");
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {

				try {
					model.getAlbumInv().removeAlbum(table.getSelectedRow());
					model.fireTableDataChanged();
				}catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Bitte wähle ein zu löschendes Album");
				}
			}
		});

		JButton editBtn = new JButton("Album bearbeiten");
		editBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				try{
				UpdateDialog dialog = new UpdateDialog(model, table.getSelectedRow());
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Bitte wähle ein zu bearbeitendes Album");
				}
			}
		});

		searchbar = new JTextField("Suchen...               ");
		searchbar.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent focusEvent) {
				searchbar.setText("");
			}

			@Override
			public void focusLost(FocusEvent focusEvent) {
				searchbar.setText("Suchen...              ");
			}
		});
		searchbar.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent keyEvent) {
			}

			@Override
			public void keyPressed(KeyEvent keyEvent) {
			}

			@Override
			public void keyReleased(KeyEvent keyEvent) {
				String query = searchbar.getText();
				filter(query);
			}
		});

		JPanel btnPanel = new JPanel(new GridLayout(1,3));
		JPanel searchbarPanel = new JPanel(new BorderLayout());
		searchbarPanel.add(searchbar, BorderLayout.EAST);
		btnPanel.add(addBtn);
		btnPanel.add(deleteBtn);
		btnPanel.add(editBtn);
		this.getContentPane().add(btnPanel, BorderLayout.SOUTH);
		this.getContentPane().add(searchbarPanel, BorderLayout.NORTH);

	}

	private void filter(String query) {
		TableRowSorter<DataModel> tr = new TableRowSorter<DataModel>(model);
		table.setRowSorter(tr);

		tr.setRowFilter(RowFilter.regexFilter(query));
	}
	
	
	public static void main(String[] args){
		DataModel model = new DataModel();
		new TableView(model);
	}
	
}
/**
 * Gui zum Dialog bei der Erfassung eines Albums
 */
class InputDialog extends JDialog{
	
	private JTextField[] eingabeFelder;
	private JDialog dialog;
	private JLabel preview;
	
	InputDialog(DataModel model){

		this.dialog = this;
		this.setTitle("Album erfassen");
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//
		this.getContentPane().add(new JLabel(" Album erfassen"), BorderLayout.NORTH);
		//
		eingabeFelder = new JTextField[model.getColumnCount()];
		Font font = new Font("Courier", Font.BOLD, 60);
		preview = new JLabel("i.");
		preview.setFont(font);
		preview.setBorder(LineBorder.createGrayLineBorder());
		preview.setMinimumSize(new Dimension(75,75));
		JButton fileBtn = new JButton("File");
		fileBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.home")));
				fileChooser.setDialogTitle("Albumcover auswählen");
				fileChooser.setFileFilter(new FileTypeFilter(".jpg", "Jpg Files"));
				int result = fileChooser.showSaveDialog(null);
				if(result==JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					eingabeFelder[0].setText(file.toString());
					ImageIcon ico = new ImageIcon(file.toString());
					preview.setText("");
					preview.setIcon(model.scaleImage(ico));
				}
				else if(result==JFileChooser.CANCEL_OPTION) {
					System.out.println("Kein File ausgewählt");
				}
			}
		});

		JPanel inputPanel = new JPanel(new GridLayout(model.getColumnCount(),2));
		JPanel filePanel = new JPanel(new BorderLayout());

		filePanel.add(preview, BorderLayout.CENTER);
		filePanel.add(fileBtn, BorderLayout.NORTH);

		for (int i=0; i < model.getColumnCount(); i++){
			eingabeFelder[i] = new JTextField();
			eingabeFelder[i].setColumns(10);
			inputPanel.add(new JLabel("      "+ model.getColumnName(i) +": "));
			inputPanel.add(eingabeFelder[i]);
		}

		this.getContentPane().add(inputPanel, BorderLayout.CENTER);
		this.getContentPane().add(filePanel, BorderLayout.EAST);
		//
		JButton okBtn = new JButton("OK");
		okBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try{((DataModel)model).addRow(new Album(eingabeFelder[0].getText(),
						                                eingabeFelder[1].getText(),
						                                eingabeFelder[2].getText(),
														new Integer(eingabeFelder[3].getText())));
					dialog.dispose();
				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Bitte korrekte Daten eingeben");
				};
			}
		});

		JButton abortBtn = new JButton("Abbrechen");
		abortBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});
		
		JPanel btnPanel = new JPanel(new GridLayout(1,2));
		btnPanel.add(abortBtn);
		btnPanel.add(okBtn);
		this.getContentPane().add(btnPanel, BorderLayout.SOUTH);
		//
		pack();
		setVisible(true);
		setResizable(false);

	}
}

/**
 * Gui zum Dialog bei der Bearbeitung eines Albums
 */
class UpdateDialog extends JDialog {

	private JTextField[] eingabeFelder;
	private JDialog dialog;
	private JLabel preview;

	UpdateDialog(DataModel model, int row) {

		this.dialog = this;
		this.setTitle("Album bearbeiten");
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//
		this.getContentPane().add(new JLabel(" Album bearbeiten"), BorderLayout.NORTH);
		//
		eingabeFelder = new JTextField[model.getColumnCount()];

		Font font = new Font("Courier", Font.BOLD, 60);
		preview = new JLabel("i.");
		preview.setFont(font);
		preview.setBorder(LineBorder.createGrayLineBorder());

		JButton fileBtn = new JButton("File");
		fileBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.home")));
				fileChooser.setDialogTitle("Albumcover auswählen");
				fileChooser.setFileFilter(new FileTypeFilter(".jpg", "Jpg Files"));
				int result = fileChooser.showSaveDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					eingabeFelder[0].setText(file.toString());
					ImageIcon ico = new ImageIcon(file.toString());
					preview.setText("");
					preview.setIcon(model.scaleImage(ico));
				} else if (result == JFileChooser.CANCEL_OPTION) {
					System.out.println("Kein File ausgewählt");
				}
			}
		});

		JPanel inputPanel = new JPanel(new GridLayout(model.getColumnCount(), 2));
		JPanel filePanel = new JPanel(new BorderLayout());

		filePanel.add(preview, BorderLayout.CENTER);
		filePanel.add(fileBtn, BorderLayout.NORTH);

		for (int i = 0; i < model.getColumnCount(); i++) {
			eingabeFelder[i] = new JTextField();
			if (i != 0) {
				eingabeFelder[i].setText(model.getValueAt(row, i).toString());
			}else {
				eingabeFelder[i].setText(model.getAlbumInv().getAlbum(row).getImagePath());
				preview.setIcon(model.getAlbumInv().getAlbum(row).getAlbumIcon());
				preview.setText("");
			}
			eingabeFelder[i].setColumns(10);
			inputPanel.add(new JLabel("      " + model.getColumnName(i) + ": "));
			inputPanel.add(eingabeFelder[i]);
		}

		this.getContentPane().add(inputPanel, BorderLayout.CENTER);
		this.getContentPane().add(filePanel, BorderLayout.EAST);
		//
		JButton okBtn = new JButton("OK");
		okBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					model.albumInv.getAlbum(row).setImagePath(eingabeFelder[0].getText());
					model.albumInv.getAlbum(row).setTitle(eingabeFelder[1].getText());
					model.albumInv.getAlbum(row).setInterpret(eingabeFelder[2].getText());
					model.albumInv.getAlbum(row).setReleaseDate(new Integer(eingabeFelder[3].getText()));
					model.fireTableDataChanged();
					dialog.dispose();
				}catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Bitte korrekte Daten eingeben");
				}
				;
			}
		});

		JButton abortBtn = new JButton("Abbrechen");
		abortBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
			}
		});

		JPanel btnPanel = new JPanel(new GridLayout(1, 2));
		btnPanel.add(abortBtn);
		btnPanel.add(okBtn);
		this.getContentPane().add(btnPanel, BorderLayout.SOUTH);
		//
		pack();
		setVisible(true);
		setResizable(false);
	}
}
