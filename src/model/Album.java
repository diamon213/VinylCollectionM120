package model;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

/**
 * Das Objekt zum Album
 *
 * @author Aladin Boudouda
 *
 */

public class Album {
    private UUID albumUUID;
    private String title;
    private String interpret;
    private int releaseDate;
    private ImageIcon albumIcon;
    private String imagePath;

    public Album(String imagePath, String title, String interpret, int releaseDate){
        this.title = title;
        this.interpret = interpret;
        this.releaseDate = releaseDate;
        this.albumIcon = scaleImageFromPath(imagePath);
        this.albumUUID = UUID.randomUUID();
        this.imagePath = imagePath;
        System.out.println();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInterpret() {
        return interpret;
    }

    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ImageIcon getAlbumIcon() {
        return albumIcon;
    }

    public void setAlbumIcon(ImageIcon albumIcon) {
        this.albumIcon = albumIcon;
    }

    public UUID getAlbumUUID() {
        return albumUUID;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        this.albumIcon = scaleImageFromPath(imagePath);
    }

    public String toString(){
        return title;
    }

    public ImageIcon scaleImageFromPath(String path) {
        ImageIcon ico = new ImageIcon(path);
        Image image = ico.getImage();
        Image newimg = image.getScaledInstance(75, 75,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(newimg);
        return imageIcon;
    }
}
