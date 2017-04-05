package net.atos.jsonschema;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class FXMLController implements Initializable {

    @FXML
    private Label messageLabel;

    @FXML
    private TextArea jsonSchema;

    @FXML
    private TextArea jsonText;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("validating json...");
        boolean valid = true;
        try {
            JSONObject rawSchema = new JSONObject(new JSONTokener(jsonSchema.getText()));
            Schema schema = SchemaLoader.load(rawSchema);
            JSONObject jsonObject = new JSONObject(new JSONTokener(jsonText.getText()));
            schema.validate(jsonObject); // throws a ValidationException if this object is invalid
        } catch (ValidationException ex) {
            valid = false;
        } catch (org.json.JSONException je) {
            messageLabel.setStyle("-fx-background-color: yellow;");
            messageLabel.setText("Json Schema is not valid");
            return;
        }

        if (valid) {
            messageLabel.setStyle("-fx-background-color: green;");
            messageLabel.setText("Json is correctly validated!");
        } else {
            messageLabel.setStyle("-fx-background-color: red;");
            messageLabel.setText("Json is not Valid!");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
