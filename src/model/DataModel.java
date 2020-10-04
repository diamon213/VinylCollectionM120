package model; /**
 * Die Klasse model.DataModel stellt eine Menge von Objekten (in dem Fall Alben) bereit.
 * Die Alben sind alle im AlbenInv gespeichert
 *
 * Die Überschriften der Tabelle werden in einem String-Array gespeichert
 * 
 * @author Aladin Boudouda
 *
 */
import app.DataInitialization;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

public class DataModel extends AbstractTableModel{
	public AlbumInv albumInv = new AlbumInv();
	private final String[] title = {"Albumcover", "Titel", "Interpret", "Datum"};

	public DataModel(){
		new DataInitialization(this);
	}

	@Override
	public int getColumnCount() {
		return title.length;
	}

	@Override
	public int getRowCount() {
		return albumInv.getAlbenSize();
	}
	
	@Override
	public String getColumnName(int column) {
		return title[column];
	}
	

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Album dc = albumInv.getAlbum(rowIndex);
		switch (columnIndex){
		case 0: return dc.getAlbumIcon();
		case 1: return dc.getTitle();
		case 2: return dc.getInterpret();
		case 3: return dc.getReleaseDate();
		default: return null;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Album dc = albumInv.getAlbum(rowIndex);
		switch(columnIndex){
		case 0: dc.setAlbumIcon((ImageIcon) aValue); break;
		case 1: dc.setTitle((String) aValue); break;
		case 2: dc.setInterpret((String) aValue); break;
		case 3: dc.setReleaseDate((int) aValue); break;
		}
		this.fireTableDataChanged();
	}

	public void addRow(Album obj){
		albumInv.addAlbum(obj);
		this.fireTableDataChanged();
	}

	public AlbumInv getAlbumInv() {
		return albumInv;
	}

	public ImageIcon scaleImage(ImageIcon ico) {
		Image image = ico.getImage();
		Image newimg = image.getScaledInstance(75, 75,  java.awt.Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(newimg);
		return imageIcon;
	}
}
