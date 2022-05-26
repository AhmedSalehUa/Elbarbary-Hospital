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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        };
        service.start();
    }

    private void clear() {
        total.setText("0");
    }

    private void fillCombo() {
         
            Service<Void> service = new Service<Void>() {
                ObservableList<DrugsPatients> clientData;
                ObservableList<DrugsPatients> clientDataSearch;

                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            try {

                                clientData = DrugsPatients.getData();
                            } catch (Exception ex) {
                                AlertDialogs.showErrors(ex);
                            }
                            return null;
                        }
                    };

                }

                @Override
                protected void succeeded() {
                    progress.setVisible(false);
                    patient.setItems(clientData);
                    patient.setEditable(true);
                    patient.setOnKeyReleased((event) -> {

                        if (patient.getEditor().getText().length() == 0) {
                            patient.setItems(clientData);
                        } else {
                            clientDataSearch = FXCollections.observableArrayList();

                            for (DrugsPatients a : clientData) {
                                if (a.getName().contains(patient.getEditor().getText())) {
                                    clientDataSearch.add(a);
                                }
                            }
                            patient.setItems(clientDataSearch);
                            patient.show();
                        }
                    });
                    patient.setConverter(new StringConverter<DrugsPatients>() {
                        @Override
                        public String toString(DrugsPatients object) {
                            return object.getName();
                        }

                        @Override
                        public DrugsPatients fromString(String string) {
                            return null;
                        }
                    });
                    patient.setCellFactory(cell -> new ListCell<DrugsPatients>() {

                        GridPane gridPane = new GridPane();
                        Label lblid = new Label();
                        Label lblName = new Label();

                        {
                            gridPane.getColumnConstraints().addAll(
                                    new ColumnConstraints(50, 50, 50),
                                    new ColumnConstraints(200, 200, 200)
                            );

                            gridPane.add(lblid, 0, 1);
                            gridPane.add(lblName, 1, 1);
                        }

                        @Override
                        protected void updateItem(DrugsPatients person, boolean empty) {
                            super.updateItem(person, empty);

                            if (!empty && person != null) {

                                lblid.setText("م: " + Integer.toString(person.getId()));
                                lblName.setText("الاسم: " + person.getName());
                                setGraphic(gridPane);
                            } else {
                                setGraphic(null);
                            }
                        }
                    });
                    super.succeeded();
                }
            };
            service.start();

         
    }

    @FXML
    private void show(ActionEvent event) {
        if (patient.getSelectionModel().getSelectedIndex() == -1) {
            AlertDialogs.showError("اختار المريض اولا");
        } else {
            try {
                String to = db.get.getTableData("SELECT  `remaining` FROM `drg_accounts` WHERE  `patient_id` ='" + patient.getItems().get(patient.getSelectionModel().getSelectedIndex()).getId() + "'").getValueAt(0, 0).toString();
                total.setText(to);
            } catch (Exception ex) {
                AlertDialogs.showErrors(ex);
            }
        }
    }

}
