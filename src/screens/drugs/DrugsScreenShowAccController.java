/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.drugs;

import assets.classes.AlertDialogs;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import screens.drugs.assets.DrugsPatients;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class DrugsScreenShowAccController implements Initializable {

    @FXML
    private Label total;
    @FXML
    private ComboBox<DrugsPatients> patient;
    @FXML
    private ProgressIndicator progress;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            public void run() {
                                try {
                                    clear();
                                    fillCombo();
                                } catch (Exception ex) {
                                    AlertDialogs.showErrors(ex);
                                } finally {
                                    latch.countDown();
                                }
                            }

                        });

                        latch.await();

                        return null;
                    }
                };
            }

            protected void succeeded() {
                progress.setVisible(false);

                super.succeeded();
            }
        };service.start();
    }

    private void clear() {
        total.setText("0");
    }

    private void fillCombo() {
        try {
            patient.setItems(DrugsPatients.getData());
            patient.setConverter(new StringConverter<DrugsPatients>() {
                @Override
                public String toString(DrugsPatients patient) {
                    return patient.getName();
                }

                @Override
                public DrugsPatients fromString(String string) {
                    return null;
                }
            });
            patient.setCellFactory(cell -> new ListCell<DrugsPatients>() {

                // Create our layout here to be reused for each ListCell
                GridPane gridPane = new GridPane();
                Label lblid = new Label();
                Label lblName = new Label();

                // Static block to configure our layout
                {
                    // Ensure all our column widths are constant
                    gridPane.getColumnConstraints().addAll(
                            new ColumnConstraints(100, 100, 100),
                            new ColumnConstraints(100, 100, 100)
                    );

                    gridPane.add(lblid, 0, 1);
                    gridPane.add(lblName, 1, 1);

                }

                // We override the updateItem() method in order to provide our own layout for this Cell's graphicProperty
                @Override
                protected void updateItem(DrugsPatients person, boolean empty) {
                    super.updateItem(person, empty);

                    if (!empty && person != null) {

                        // Update our Labels
                        lblid.setText("م: " + Integer.toString(person.getId()));
                        lblName.setText("الاسم: " + person.getName());

                        // Set this ListCell's graphicProperty to display our GridPane
                        setGraphic(gridPane);
                    } else {
                        // Nothing to display here
                        setGraphic(null);
                    }
                }
            });
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void show(ActionEvent event) {
        if (patient.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار المريض اولا");
        } else {
            try {
                String to = db.get.getTableData("SELECT  `remaining` FROM `drg_accounts` WHERE  `patient_id` ='"+patient.getSelectionModel().getSelectedItem().getId()+"'").getValueAt(0, 0).toString();
                total.setText(to);
            } catch (Exception ex) {
               AlertDialogs.showErrors(ex);
            }
        }
    }

}
