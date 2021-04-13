/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.mainDataScreen;

import assets.classes.AlertDialogs;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import screens.mainDataScreen.assets.Clincs;

public class MainDataScreenClincsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private ProgressIndicator progress;

    @FXML
    private TableView<Clincs> clincsTable;
    @FXML
    private TableColumn<Clincs, String> clincsTabIllustraion;
    @FXML
    private TableColumn<Clincs, String> clincsTabReExamination;
    @FXML
    private TableColumn<Clincs, String> clincsTabExamination;
    @FXML
    private TableColumn<Clincs, String> clincsTabName;
    @FXML
    private TableColumn<Clincs, String> clincsTabId;
    @FXML
    private Label clincsId;
    @FXML
    private TextField clincsName;
    @FXML
    private TextField clincsExamination;
    @FXML
    private TextField clincsReExamination;
    @FXML
    private TextField clincsIllustarion;
    @FXML
    private Button clincsNew;
    @FXML
    private Button clincsEdite;
    @FXML
    private Button clincsAdd;
    @FXML
    private Button clincsDelete;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work                       
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    clear();
                                    setTableColumns();
                                    getDataToTable();
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

            @Override
            protected void succeeded() {

                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
        clincsTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            clincsAdd.setDisable(true);
            clincsEdite.setDisable(false);
            clincsDelete.setDisable(false);
            clincsNew.setDisable(false);

            Clincs cl = clincsTable.getSelectionModel().getSelectedItem();
            clincsId.setText(Integer.toString(cl.getId()));
            clincsName.setText(cl.getName());
            clincsExamination.setText(cl.getExaminationCost());
            clincsReExamination.setText(cl.getReExaminationCost());
            clincsIllustarion.setText(cl.getIllustarionCost());

        });
    }

    @FXML
    private void clincsNew(ActionEvent event) {
        clear();
    }

    @FXML
    private void clincsEdite(ActionEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work                       
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    try {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting Clinc");
                                        alert.setHeaderText("سيتم تعديل بيانات العيادة");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            Clincs cl = new Clincs();
                                            cl.setId(Integer.parseInt(clincsId.getText()));
                                            cl.setName(clincsName.getText());
                                            cl.setExaminationCost(clincsExamination.getText());
                                            cl.setReExaminationCost(clincsReExamination.getText());
                                            cl.setIllustarionCost(clincsIllustarion.getText());

                                            cl.Edite();
                                        }
                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
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

            @Override
            protected void succeeded() {
                clear();
                getDataToTable();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void clincsAdd(ActionEvent event) {
        if (clincsName.getText().isEmpty() || clincsName.getText() == null
                || clincsExamination.getText().isEmpty() || clincsExamination.getText() == null
                || clincsReExamination.getText().isEmpty() || clincsReExamination.getText() == null) {
            AlertDialogs.showError("اسم الدواء والمية والوحدة لا يجب ان يكون فارغا!!");
        } else {
            progress.setVisible(true);
            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            //Background work                       
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        try {
                                            Clincs cl = new Clincs();
                                            cl.setId(Integer.parseInt(clincsId.getText()));
                                            cl.setName(clincsName.getText());
                                            cl.setExaminationCost(clincsExamination.getText());
                                            cl.setReExaminationCost(clincsReExamination.getText());
                                            cl.setIllustarionCost(clincsIllustarion.getText());
                                            cl.Add();

                                        } catch (Exception ex) {
                                            System.out.println(ex.getMessage());
                                            ex.printStackTrace();
                                            AlertDialogs.showErrors(ex);
                                        }
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

                @Override
                protected void succeeded() {
                    clear();
                    getDataToTable();
                    progress.setVisible(false);
                    super.succeeded();
                }
            };
            service.start();
        }
    }

    @FXML
    private void clincsDelete(ActionEvent event) {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work                       
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    try {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting Clinc");
                                        alert.setHeaderText("سيتم حذف العيادة");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            Clincs cl = new Clincs();
                                            cl.setId(Integer.parseInt(clincsId.getText()));
                                            cl.Delete();
                                        }

                                    } catch (Exception ex) {
                                       AlertDialogs.showErrors(ex);
                                    }
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

            @Override
            protected void succeeded() {
                clear();
                getDataToTable();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();

    }

    @FXML
    private void search(KeyEvent event) {
        FilteredList<Clincs> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< Clincs> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(clincsTable.comparatorProperty());
        clincsTable.setItems(sortedData);
    }

    private void setAutoNumber() {
        try {
            clincsId.setText(Clincs.getAutoNum());
        } catch (Exception ex) {
           AlertDialogs.showErrors(ex);
        }
    }

    private void clear() {
        setAutoNumber();
        clincsName.setText("");
        clincsExamination.setText("");
        clincsReExamination.setText("");
        clincsIllustarion.setText("");
        clincsAdd.setDisable(false);
        clincsEdite.setDisable(true);
        clincsDelete.setDisable(true);
        clincsNew.setDisable(true);
    }

    private void setTableColumns() {
        clincsTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clincsTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clincsTabExamination.setCellValueFactory(new PropertyValueFactory<>("examinationCost"));
        clincsTabReExamination.setCellValueFactory(new PropertyValueFactory<>("reExaminationCost"));
        clincsTabIllustraion.setCellValueFactory(new PropertyValueFactory<>("illustarionCost"));

    }

    private void getDataToTable() {
        progress.setVisible(true);
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work                       
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    try {

                                        clincsTable.setItems(Clincs.getData());

                                    } catch (Exception ex) {
                                       AlertDialogs.showErrors(ex);
                                    }
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

            @Override
            protected void succeeded() {

                items = clincsTable.getItems();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<Clincs> items;

}
