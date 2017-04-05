/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.atos.jsonschema;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.control.TextArea;
import org.json.JSONObject;

/**
 *
 * @author A416865
 */
public class TextAreaFocusListener implements ChangeListener<Boolean> {

    private static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");
    TextArea textArea;

    public TextAreaFocusListener(TextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldPropertyValue, Boolean newPropertyValue) {

        if (newPropertyValue) {
            System.out.println("Textfield on focus");
        } else {
            System.out.println("Textfield out focus");
            try {
                JSONObject jo = new JSONObject(textArea.getText());
                textArea.setText(jo.toString(2));
                textArea.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, false);
            } catch (Exception e) {
                // ignore formatting if json is not valid, and set background to red
                textArea.pseudoClassStateChanged(ERROR_PSEUDO_CLASS, true);
            }
        }
    }
}
