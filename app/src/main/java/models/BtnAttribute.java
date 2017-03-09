package models;

import android.widget.Button;

/**
 * Created by asus on 12.02.2017.
 */

public class BtnAttribute {
   public Button button_att;
    Boolean flag_selection;

    public BtnAttribute(Boolean flag_selection, Button button_att) {
        this.flag_selection = flag_selection;
        this.button_att = button_att;
    }

    public Boolean getFlag_selection() {
        return flag_selection;
    }

    public void setFlag_selection(Boolean flag_selection) {
        this.flag_selection = flag_selection;
    }

}
