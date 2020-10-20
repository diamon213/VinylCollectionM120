package view;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Der Filter dient dazu die Dateien im JFilechooser nach .jpg's zu filtern.
 *
 * @author Aladin Boudouda
 *
 */

public class FileTypeFilter extends FileFilter {

    private final String extension;
    private final String description;


    public FileTypeFilter(String extension, String description) {
        this.extension = extension;
        this.description = description;
    }

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        return file.getName().endsWith(extension);
    }

    @Override
    public String getDescription() {
        return description + String.format(" (*%s)", extension);
    }
}
