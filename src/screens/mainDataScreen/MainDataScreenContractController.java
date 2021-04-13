/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.mainDataScreen;

import assets.classes.AlertDialogs;
import assets.classes.AutoCompleteBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import screens.mainDataScreen.assets.Contract;
import screens.mainDataScreen.assets.ContractServices;
import screens.mainDataScreen.assets.ContractServicesName;

public class MainDataScreenContractController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private ProgressIndicator progress;
    @FXML
    private TableView<Contract> contractTable;
    @FXML
    private TableColumn<Contract, String> contractTabDate;
    @FXML
    private TableColumn<Contract, String> contractTabDiscount;
    @FXML
    private TableColumn<Contract, String> contractTabName;
    @FXML
    private TableColumn<Contract, String> contractTabId;
    @FXML
    private Label contractId;
    @FXML
    private TextField contractName;
    @FXML
    private TextField contractDiscount;
    @FXML
    private ImageView contractPhotoPicker;
    @FXML
    private TextField contractPhoto;
    @FXML
    private JFXDatePicker contractDate;
    @FXML
    private Button contractNew;
    @FXML
    private Button contractEdite;
    @FXML
    private Button contractAdd;
    @FXML
    private Button contractDelete;
    @FXML
    private ImageView showPicture;
    @FXML
    private TableColumn<Contract, String> contractTabExamination;
    @FXML
    private TextField contractExamination;
    @FXML
    private JFXTextField servicesSearch;
    @FXML
    private TableView<ContractServices> servicesTable;
    @FXML
    private TableColumn<ContractServices, String> servicesTabCost;
    @FXML
    private TableColumn<ContractServices, String> servicesTabName;
    @FXML
    private TableColumn<ContractServices, String> servicesTabId;
    @FXML
    private Label servicesId;
    @FXML
    private TextField servicesCost;
    @FXML
    private MaterialIconView servicesNameAdd;
    @FXML
    private ComboBox<ContractServicesName> servicesName;
    @FXML
    private ProgressIndicator servicesProgress;
    @FXML
    private Button servicesNew;
    @FXML
    private Button servicesDelete;
    @FXML
    private Button servicesEdite;
    @FXML
    private Button servicesAdd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contractDate.setConverter(new StringConverter<LocalDate>() {

            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty())
                        ? LocalDate.parse(string, dateFormatter)
                        : null;
            }

        });
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
                                    setServiceColumn();
                                    fillCombo();
                                    getDataToTable();
                                    clearServices();
                                } catch (Exception e) {
                                    AlertDialogs.showErrors(e);
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
                servicesProgress.setVisible(false);
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
        contractTable.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (contractTable.getSelectionModel().getSelectedIndex() == -1) {
                servicesTable.setItems(null);
            } else {
                contractAdd.setDisable(true);
                contractEdite.setDisable(false);
                contractDelete.setDisable(false);
                contractNew.setDisable(false);

                Contract pa = contractTable.getSelectionModel().getSelectedItem();
                try {
                   servicesProgress.setVisible(true);
                    servicesTable.setItems(ContractServices.getData(pa.getId()));
                    servicesProgress.setVisible(false);
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                contractId.setText(Integer.toString(pa.getId()));
                contractName.setText(pa.getName());
                contractDiscount.setText(pa.getDiscount());
                contractExamination.setText(pa.getExaminationCost());
                contractDate.setValue(LocalDate.parse(pa.getDate()));
            }
        });
        servicesTable.setOnMouseClicked((e) -> {
            if (servicesTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                servicesAdd.setDisable(true);
                servicesDelete.setDisable(false);
                servicesEdite.setDisable(false);
                servicesNew.setDisable(false);
                ContractServices s = servicesTable.getSelectionModel().getSelectedItem();
                servicesId.setText(Integer.toString(s.getId()));
                servicesCost.setText(s.getCost());

                ObservableList<ContractServicesName> item = servicesName.getItems();
                for (ContractServicesName a : item) {
                    if (a.getName().equals(s.getService())) {
                        servicesName.getSelectionModel().select(a);
                    }
                }
            }
        });
    }

    @FXML
    private void contractNew(ActionEvent event) {
        clear();
    }

    private void setServiceColumn() {
        servicesTabCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        servicesTabName.setCellValueFactory(new PropertyValueFactory<>("service"));
        servicesTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void fillCombo() {
        try {
            servicesName.setItems(ContractServicesName.getData());
            servicesName.setConverter(new StringConverter<ContractServicesName>() {
                @Override
                public String toString(ContractServicesName patient) {
                    return patient.getName();
                }

                @Override
                public ContractServicesName fromString(String string) {
                    return null;
                }
            });
            servicesName.setCellFactory(cell -> new ListCell<ContractServicesName>() {

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
                protected void updateItem(ContractServicesName person, boolean empty) {
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
    private void contractEdite(ActionEvent event) {
        if (contractName.getText().isEmpty() || contractName.getText() == null) {
            AlertDialogs.showError("اسم الشركة لا يجب ان يكون فارغا!!");
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
                                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                            alert.setTitle("Deleting Contract");
                                            alert.setHeaderText("سيتم تعديل بيانات التعاقد");
                                            alert.setContentText("هل انت متاكد؟");

                                            Optional<ButtonType> result = alert.showAndWait();
                                            if (result.get() == ButtonType.OK) {
                                                if (contractPhoto.getText().isEmpty() || contractPhoto.getText() == null) {
                                                    Contract cont = new Contract();
                                                    cont.setId(Integer.parseInt(contractId.getText()));
                                                    cont.setName(contractName.getText());
                                                    cont.setDiscount(contractDiscount.getText());
                                                    cont.setExaminationCost(contractExamination.getText());
                                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                    cont.setDate(contractDate.getValue().format(format));

                                                    cont.EditeWithoutPhoto();
                                                } else {
                                                    Contract cont = new Contract();
                                                    cont.setId(Integer.parseInt(contractId.getText()));
                                                    cont.setName(contractName.getText());
                                                    cont.setDiscount(contractDiscount.getText());
                                                    cont.setExaminationCost(contractExamination.getText());
                                                    InputStream in = new FileInputStream(new File(contractPhoto.getText()));
                                                    cont.setPhoto(in);

                                                    String[] st = contractPhoto.getText().split(Pattern.quote("."));
                                                    cont.setPhotoExt(st[st.length - 1]);

                                                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                    cont.setDate(contractDate.getValue().format(format));

                                                    cont.Edite();
                                                }

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
    }

    @FXML
    private void contractAdd(ActionEvent event) {
        if (contractName.getText().isEmpty() || contractName.getText() == null) {
            AlertDialogs.showError("اسم الشركة لا يجب ان يكون فارغا!!");
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
                                            if (contractPhoto.getText().isEmpty() || contractPhoto.getText() == null) {
                                                Contract cont = new Contract();
                                                cont.setId(Integer.parseInt(contractId.getText()));
                                                cont.setName(contractName.getText());
                                                cont.setDiscount(contractDiscount.getText());
                                                cont.setExaminationCost(contractExamination.getText());

                                                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                cont.setDate(contractDate.getValue().format(format));
                                                cont.AddWithoutPhoto();
                                            } else {
                                                Contract cont = new Contract();
                                                cont.setId(Integer.parseInt(contractId.getText()));
                                                cont.setName(contractName.getText());
                                                cont.setDiscount(contractDiscount.getText());
                                                cont.setExaminationCost(contractExamination.getText());
                                                InputStream in = new FileInputStream(new File(contractPhoto.getText()));
                                                cont.setPhoto(in);

                                                String[] st = contractPhoto.getText().split(Pattern.quote("."));
                                                cont.setPhotoExt(st[st.length - 1]);

                                                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                cont.setDate(contractDate.getValue().format(format));
                                                cont.Add();
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
    }

    @FXML
    private void contractDelete(ActionEvent event) {
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
                                        alert.setTitle("Deleting Contract");
                                        alert.setHeaderText("سيتم حذف التعاقد");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            Contract cont = new Contract();
                                            cont.setId(Integer.parseInt(contractId.getText()));
                                            cont.Delete();
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
        FilteredList<Contract> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            if (pa.getName().contains(lowerCaseFilter)
                    || pa.getDate().contains(lowerCaseFilter)) {
                return true;
            } else {
                return false;
            }

        });

        SortedList< Contract> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(contractTable.comparatorProperty());
        contractTable.setItems(sortedData);
    }

    private void setAutoNumber() {
        try {
            contractId.setText(Contract.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clear() {
        setAutoNumber();
        contractName.setText("");
        contractDiscount.setText("");
        contractPhoto.setText("");
        contractExamination.setText("");
        contractDate.setValue(null);
        contractAdd.setDisable(false);
        contractEdite.setDisable(true);
        contractDelete.setDisable(true);
        contractNew.setDisable(true);
    }

    private void setTableColumns() {
        contractTabDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        contractTabDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        contractTabExamination.setCellValueFactory(new PropertyValueFactory<>("examinationCost"));
        contractTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        contractTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
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

                                        contractTable.setItems(Contract.getData());

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

                items = contractTable.getItems();
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();
    }
    ObservableList<Contract> items;

    @FXML
    private void pickPhoto(MouseEvent event) {
        FileChooser fil_chooser = new FileChooser();
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fil_chooser.showOpenDialog(st);

        if (file != null) {
            contractPhoto.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void showPicture(MouseEvent event) {
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

                                        try {
                                            Contract tab = contractTable.getSelectionModel().getSelectedItem();
                                            Contract cont = new Contract();
                                            cont.setId(tab.getId());
                                            cont.setDate(tab.getDate());
                                            cont.getContractPhoto();
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
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
                progress.setVisible(false);
                super.succeeded();
            }
        };
        service.start();

    }

    @FXML
    private void servicesNameAdd(MouseEvent event) {

        servicesProgress.setVisible(true);
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Service Name");
        dialog.setHeaderText("اضافة خدمة للتعاقد");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().isEmpty() || result.get() == null) {
                AlertDialogs.showError("خطا!! يرجي ادخال اسم خدمة");
            } else {
                final String results = result.get();
                try {
                    Service service = new Service() {
                        @Override
                        protected Task createTask() {
                            return new Task() {
                                @Override
                                protected Object call() throws Exception {
                                    final CountDownLatch latch = new CountDownLatch(1);
                                    Platform.runLater(() -> {
                                        try {
                                            ContractServicesName.Add(results);
                                        } catch (Exception ex) {
                                            AlertDialogs.showErrors(ex);
                                        } finally {
                                            latch.countDown();
                                        }
                                    });
                                    latch.await();

                                    return null;
                                }

                                @Override
                                protected void succeeded() {
                                    try {
                                        fillCombo();
                                        servicesProgress.setVisible(false);
                                    } catch (Exception ex) {
                                        AlertDialogs.showErrors(ex);
                                    }
                                }
                            };
                        }
                    };
                    service.start();

                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
            }
        }
    }

    @FXML
    private void servicesNew(ActionEvent event) {
        clearServices();
    }

    @FXML
    private void servicesDelete(ActionEvent event) {
        servicesProgress.setVisible(true);
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
                                    if (contractTable.getSelectionModel().getSelectedIndex() == -1) {
                                        AlertDialogs.showError("اختار التعاقد اولا");
                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting Service");
                                        alert.setHeaderText("سيتم حذف الخدمة");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            ContractServices co = new ContractServices();
                                            co.setId(Integer.parseInt(servicesId.getText()));
                                            co.Delete();
                                        }
                                    }
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

            @Override
            protected void succeeded() {
                servicesProgress.setVisible(false);clearServices();
                try {
                    servicesTable.setItems(ContractServices.getData(contractTable.getSelectionModel().getSelectedItem().getId()));
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void servicesEdite(ActionEvent event) {
        servicesProgress.setVisible(true);
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
                                    if (contractTable.getSelectionModel().getSelectedIndex() == -1) {
                                        AlertDialogs.showError("اختار التعاقد اولا");
                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Editing Service");
                                        alert.setHeaderText("سيتم تعديل الخدمة");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {
                                            ContractServices co = new ContractServices();
                                            co.setId(Integer.parseInt(servicesId.getText()));
                                            co.setServiceId(servicesName.getSelectionModel().getSelectedItem().getId());
                                            co.setCost(servicesCost.getText());
                                            co.setContractId(contractTable.getSelectionModel().getSelectedItem().getId());
                                            co.Edite();
                                        }
                                    }
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

            @Override
            protected void succeeded() {
                servicesProgress.setVisible(false);clearServices();
                try {
                    servicesTable.setItems(ContractServices.getData(contractTable.getSelectionModel().getSelectedItem().getId()));
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void servicesAdd(ActionEvent event) {
        servicesProgress.setVisible(true);
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
                                    if (contractTable.getSelectionModel().getSelectedIndex() == -1) {
                                        AlertDialogs.showError("اختار التعاقد اولا");
                                    } else {
                                        ContractServices co = new ContractServices();
                                        co.setServiceId(servicesName.getSelectionModel().getSelectedItem().getId());
                                        co.setCost(servicesCost.getText());
                                        co.setContractId(contractTable.getSelectionModel().getSelectedItem().getId());
                                        co.Add();
                                    }
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

            @Override
            protected void succeeded() {
                servicesProgress.setVisible(false);clearServices();
                try {
                    servicesTable.setItems(ContractServices.getData(contractTable.getSelectionModel().getSelectedItem().getId()));
                } catch (Exception ex) {
                    AlertDialogs.showErrors(ex);
                }
                super.succeeded();
            }
        };
        service.start();
    }

    private void clearServices() {
        setServiceAutoNum();

        servicesName.getSelectionModel().clearSelection();
        servicesCost.setText("");
 servicesProgress.setVisible(false);
        servicesAdd.setDisable(false);
        servicesDelete.setDisable(true);
        servicesEdite.setDisable(true);
        servicesNew.setDisable(true);
    }

    private void setServiceAutoNum() {
        try {
            servicesId.setText(ContractServices.getAutoNum());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

}
