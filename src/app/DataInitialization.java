package app;

import model.Album;
import model.DataModel;

/**
 * Durch diese Klasse werden die Daten initialisiert.
 *
 * @author Aladin Boudouda
 *
 */

public class DataInitialization {

	public DataInitialization(DataModel dataModel) {

		dataModel.albumInv.addAlbum(new Album("C:\\Users\\Aladin\\Documents\\VinylCollectionM120\\src\\img\\moodyblues.jpg", "Days Of Future Passed", "The Moody Blues", 1967));
		dataModel.albumInv.addAlbum(new Album("C:\\Users\\Aladin\\Documents\\VinylCollectionM120\\src\\img\\kingcrimson.jpg", "In the Court of the Crimson King", "King Crimson", 1969));
		dataModel.albumInv.addAlbum(new Album("C:\\Users\\Aladin\\Documents\\VinylCollectionM120\\src\\img\\cali.jpg", "Hotel California", "Eagles", 1976));
		dataModel.albumInv.addAlbum(new Album("C:\\Users\\Aladin\\Documents\\VinylCollectionM120\\src\\img\\pinkfloyd.jpg", "Atom Heart Mother", "Pink Floyd", 1970));
		dataModel.albumInv.addAlbum(new Album("C:\\Users\\Aladin\\Documents\\VinylCollectionM120\\src\\img\\yes.jpg", "Fragile (Deluxe Edition)", "Yes", 1971));
		dataModel.albumInv.addAlbum(new Album("C:\\Users\\Aladin\\Documents\\VinylCollectionM120\\src\\img\\genesis.jpg", "Selling England By The Pound", "Genesis", 1973));
		dataModel.albumInv.addAlbum(new Album("C:\\Users\\Aladin\\Documents\\VinylCollectionM120\\src\\img\\hmstr.jpg", "The Hampster Experience", "hamster2small", 2020));
		dataModel.albumInv.addAlbum(new Album("C:\\Users\\Aladin\\Documents\\VinylCollectionM120\\src\\img\\vdgg.jpg", "Pawn Hearts", "Van Der Graaf Generator", 1971));

	}
}

