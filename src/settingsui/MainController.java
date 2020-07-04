/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settingsui;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class MainController implements Initializable, ChangeListener<Object>{
    
    @FXML
    private ImageView btn_settings, btn_user,btn_exit;
    @FXML
    private AnchorPane h_settings, h_user;
    @FXML
    private JFXSlider brightnessSlider;
    @FXML
    private JFXToggleButton toggleTouch,toggleSubscription;
    @FXML
    private JFXCheckBox autoBrightness;
    private int brightnessLevel=0;
    
    Model model =new Model();
    
    
    @FXML
    private void handleButtonAction(MouseEvent event) throws IOException{
        if(event.getTarget()== btn_settings){
            h_settings.setVisible(true);
            h_user.setVisible(false);
        }else if(event.getTarget()== btn_user){
            h_user.setVisible(true);
            h_settings.setVisible(false);
        }else if(event.getTarget()== btn_exit){
            // get a handle to the stage
            Stage stage = (Stage) btn_exit.getScene().getWindow();
            // close window
            stage.close();
        }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // Brightness Slider
        brightnessSlider.valueProperty().addListener(this);
        
        // Enable Touch Button
        toggleTouch.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(toggleTouch.isSelected()){
                    toggleTouch.setText("ON");
                }else{
                    toggleTouch.setText("OFF");
                }
            }   
        });
        
        // Auto Subscribe Button
        toggleSubscription.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(toggleSubscription.isSelected()){
                    toggleSubscription.setText("ON");
                }else{
                    toggleSubscription.setText("OFF");
                }
            }     
        });
        
        // Auto Brightness Checkbox
        autoBrightness.selectedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(autoBrightness.isSelected()){
                    // Get slider value before you change it
                    if(brightnessLevel==0){
                        Double sliderValue=brightnessSlider.getValue();
                        brightnessLevel=sliderValue.intValue();
                    }
                    brightnessSlider.setValue(100);
                    try {
                        model.setBrightness(100);
                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    // If unchecked restore to previous brightness level
                    brightnessSlider.setValue(brightnessLevel);
                    try {
                        model.setBrightness(brightnessLevel);
                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    brightnessLevel=0;
                }
            }
        });

    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
        
        /*
            Following Lines of code handles change event for brightness slider
        */
        
        Double sliderValue=brightnessSlider.getValue();
        try {
            model.setBrightness(sliderValue.intValue());
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
}
