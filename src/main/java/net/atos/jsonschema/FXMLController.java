package net.atos.jsonschema;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import org.json.JSONObject;

public class FXMLController implements Initializable {

    static final PseudoClass ERROR_PSEUDO_CLASS = PseudoClass.getPseudoClass("error");
    static final PseudoClass VALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("valid");
    static final PseudoClass WARN_PSEUDO_CLASS = PseudoClass.getPseudoClass("warn");

    public static void cleanCSSPseudoClass(TextArea ta) {
        ta.pseudoClassStateChanged(FXMLController.ERROR_PSEUDO_CLASS, false);
        ta.pseudoClassStateChanged(FXMLController.VALID_PSEUDO_CLASS, false);
        ta.pseudoClassStateChanged(FXMLController.WARN_PSEUDO_CLASS, false);
    }

    public static void formatJSON(TextArea textArea) {
        String formattedJSON = JSONValidator.formatJSON(textArea.getText());
        cleanCSSPseudoClass(textArea);
        if (formattedJSON == null) {
            textArea.pseudoClassStateChanged(FXMLController.ERROR_PSEUDO_CLASS, true);
        } else {
            textArea.setText(formattedJSON);
            textArea.pseudoClassStateChanged(FXMLController.ERROR_PSEUDO_CLASS, false);
        }
    }

    @FXML
    private Label messageLabel;

    @FXML
    private TextArea jsonSchema;

    @FXML
    private TextArea jsonText;

    @FXML
    private void formatAction() {
        formatJSON(jsonSchema);
        formatJSON(jsonText);
    }

    @FXML
    private void validateAction(ActionEvent event) {
        System.out.println("validating json...");
        boolean valid = true;
        
        cleanCSSPseudoClass(jsonText);
        cleanCSSPseudoClass(jsonSchema);
        
        JSONObject oJSONSchema = JSONValidator.getJSONObjectOrNULL(jsonSchema.getText());
        JSONObject oJSONText = JSONValidator.getJSONObjectOrNULL(jsonText.getText());

        // check json format of both json and jsonschema
        if (oJSONText == null) {
            jsonText.pseudoClassStateChanged(FXMLController.ERROR_PSEUDO_CLASS, true);
        }

        if (oJSONSchema == null) {
            jsonSchema.pseudoClassStateChanged(FXMLController.ERROR_PSEUDO_CLASS, true);
        }

        // exit if at least one json is not correct
        if (oJSONText == null || oJSONSchema == null) {
            messageLabel.setText("text is not JSON.");
            return;
        }

        if (JSONValidator.isJSONValidatedWithSchema(oJSONText, oJSONSchema)) {
            jsonText.pseudoClassStateChanged(VALID_PSEUDO_CLASS, true);
            jsonSchema.pseudoClassStateChanged(VALID_PSEUDO_CLASS, true);
            messageLabel.setText("JSON is validated against JSON Schema.");
        } else {
            jsonText.pseudoClassStateChanged(WARN_PSEUDO_CLASS, true);
            jsonSchema.pseudoClassStateChanged(WARN_PSEUDO_CLASS, true);
            messageLabel.setText("JSON is not validated against JSON Schema");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        final String TEXT_AREA_CLASS = "text-area";

        jsonSchema.getStyleClass().add(TEXT_AREA_CLASS);
        jsonText.getStyleClass().add(TEXT_AREA_CLASS);

        jsonSchema.focusedProperty().addListener(new TextAreaFocusListener(jsonSchema));
        jsonText.focusedProperty().addListener(new TextAreaFocusListener(jsonText));
    }
}
