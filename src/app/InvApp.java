package app;

import gui.TableView;
import model.DataModel;

/**
 *
 *
 * @author Aladin Boudouda
 *
 */

public class InvApp {
    public static void main(String[] args) {
        DataModel model = new DataModel();
        new TableView(model);
    }
}
