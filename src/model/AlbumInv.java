package model;

import java.util.Vector;

/**
 * Die Sammlung aller Alben
 *
 * @author Aladin Boudouda
 *
 */

public class AlbumInv {
    private Vector<Album> alben = new Vector<>();

    public AlbumInv() {

    }

    public int getAlbenSize() {
        return alben.size();
    }
    public void addAlbum(Album album) {
        alben.add(album);
    }
    public void removeAlbum(Album album) {
        alben.remove(album);
    }
    public void removeAlbum(int index) {
        alben.remove(index);
    }
    public Album getAlbum(int index) {
        return alben.get(index);
    }
    public String toString() {
        return alben.toString();
    }
    public Vector<Album> getAlben() {
        return alben;
    }
}
