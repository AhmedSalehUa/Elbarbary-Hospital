/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.controlpanel;

import assets.classes.AlertDialogs;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import screens.accounts.assets.Category;
import screens.accounts.assets.YieldsCategory;
import screens.admission.assets.AdmissionSpacfication;
import screens.admission.assets.AdmissionStatue;
import screens.admission.assets.AdmissionType;
import screens.admission.assets.SurgeriesType;
import screens.drugs.assets.DrugsCategeroy;
import screens.mainDataScreen.assets.DoctorQualifications;
import screens.mainDataScreen.assets.DoctorsServicesName;
import screens.mainDataScreen.assets.MedicineUnit;
import screens.mainDataScreen.assets.TransferOrganization;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class ControlPanelValuesController implements Initializable {

    @FXML
    private TableView<DoctorQualifications> qualificationsTab;
    @FXML
    private TableColumn<DoctorQualifications, String> qualificationsTabName;
    @FXML
    private TableColumn<DoctorQualifications, String> qualificationsTabId;
    @FXML
    private Label qualificationsId;
    @FXML
    private TextField qualificationsName;
    @FXML
    private Button qualificationsNew;
    @FXML
    private Button qualificationsDelete;
    @FXML
    private Button qualificationsEdite;
    @FXML
    private Button qualificationsAdd;
    @FXML
    private TableView<DoctorsServicesName> doctor_services_names;
    @FXML
    private TableColumn<DoctorsServicesName, String> doctor_services_namesTabName;
    @FXML
    private TableColumn<DoctorsServicesName, String> doctor_services_namesTabId;
    @FXML
    private Label doctor_services_nameId;
    @FXML
    private TextField doctor_services_namesName;
    @FXML
    private Button doctor_services_namesNew;
    @FXML
    private Button doctor_services_namesDelete;
    @FXML
    private Button doctor_services_namesEdite;
    @FXML
    private Button doctor_services_namesAdd;
    @FXML
    private TableView<MedicineUnit> medicine_unitsTab;
    @FXML
    private TableColumn<MedicineUnit, String> medicine_unitsTabUnit;
    @FXML
    private TableColumn<MedicineUnit, String> medicine_unitsTabName;
    @FXML
    private TableColumn<MedicineUnit, String> medicine_unitsTabId;
    @FXML
    private Label medicine_unitsId;
    @FXML
    private TextField medicine_unitsName;
    @FXML
    private TextField medicine_unitsUnit;
    @FXML
    private Button medicine_unitsNew;
    @FXML
    private Button medicine_unitsDelete;
    @FXML
    private Button medicine_unitsEdite;
    @FXML
    private Button medicine_unitsAdd;
    @FXML
    private TableView<TransferOrganization> patient_transporting_org;
    @FXML
    private TableColumn<TransferOrganization, String> patient_transporting_orgTabName;
    @FXML
    private TableColumn<TransferOrganization, String> patient_transporting_orgTabId;
    @FXML
    private Label patient_transporting_orgid;
    @FXML
    private TextField patient_transporting_orgName;
    @FXML
    private Button patient_transporting_orgNew;
    @FXML
    private Button patient_transporting_orgDelete;
    @FXML
    private Button patient_transporting_orgEdite;
    @FXML
    private Button patient_transporting_orgAdd;
    @FXML
    private TableView<YieldsCategory> yields_catTab;
    @FXML
    private TableColumn<YieldsCategory, String> yields_catTabName;
    @FXML
    private TableColumn<YieldsCategory, String> yields_catTabId;
    @FXML
    private Label yields_catID;
    @FXML
    private TextField yields_catName;
    @FXML
    private Button yields_catNew;
    @FXML
    private Button yields_catDelete;
    @FXML
    private Button yields_catEdite;
    @FXML
    private Button yields_catAdd;
    @FXML
    private TableView<Category> categorysTab;
    @FXML
    private TableColumn<Category, String> categorysTabName;
    @FXML
    private TableColumn<Category, String> categorysTabId;
    @FXML
    private Label categorysId;
    @FXML
    private TextField categorysName;
    @FXML
    private Button categorysNew;
    @FXML
    private Button categorysDelete;
    @FXML
    private Button categorysEdite;
    @FXML
    private Button categorysAdd;
    @FXML
    private TableView<AdmissionSpacfication> admission_spacificationTab;
    @FXML
    private TableColumn<AdmissionSpacfication, String> admission_spacificationTabName;
    @FXML
    private TableColumn<AdmissionSpacfication, String> admission_spacificationTabId;
    @FXML
    private Label admission_spacificationId;
    @FXML
    private TextField admission_spacificationName;
    @FXML
    private Button admission_spacificationNew;
    @FXML
    private Button admission_spacificationDelete;
    @FXML
    private Button admission_spacificationEdite;
    @FXML
    private Button admission_spacificationAdd;
    @FXML
    private TableView<AdmissionType> admission_typeTab;
    @FXML
    private TableColumn<AdmissionType, String> admission_typeTabName;
    @FXML
    private TableColumn<AdmissionType, String> admission_typeTabId;
    @FXML
    private Label admission_typeId;
    @FXML
    private TextField admission_typeName;
    @FXML
    private Button admission_typeNew;
    @FXML
    private Button admission_typeDelete;
    @FXML
    private Button admission_typeEdite;
    @FXML
    private Button admission_typeAdd;
    @FXML
    private TableView<AdmissionStatue> admission_statueTab;
    @FXML
    private TableColumn<AdmissionStatue, String> admission_statueTabName;
    @FXML
    private TableColumn<AdmissionStatue, String> admission_statueTabId;
    @FXML
    private Label admission_statueId;
    @FXML
    private TextField admission_statueName;
    @FXML
    private Button admission_statueNew;
    @FXML
    private Button admission_statueDelete;
    @FXML
    private Button admission_statueEdite;
    @FXML
    private Button admission_statueAdd;
    @FXML
    private TableView<SurgeriesType> admission_services_surgeries_typeTab;
    @FXML
    private TableColumn<SurgeriesType, String> admission_services_surgeries_typeTabName;
    @FXML
    private TableColumn<SurgeriesType, String> admission_services_surgeries_typeTabId;
    @FXML
    private Label admission_services_surgeries_typeID;
    @FXML
    private TextField admission_services_surgeries_typeName;
    @FXML
    private Button admission_services_surgeries_typeDelete;
    @FXML
    private Button admission_services_surgeries_typeEdite;
    @FXML
    private Button admission_services_surgeries_typeAdd;
    @FXML
    private Button admission_services_surgeries_typeNew;
    @FXML
    private TableView<DrugsCategeroy> drugsTab;
    @FXML
    private TableColumn<DrugsCategeroy, String> drugsTabName;
    @FXML
    private TableColumn<DrugsCategeroy, String> drugsTabId;
    @FXML
    private Label drugsId;
    @FXML
    private TextField drugsName;
    @FXML
    private Button drugsNew;
    @FXML
    private Button drugsDelete;
    @FXML
    private Button drugsEdite;
    @FXML
    private Button drugsAdd;

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
                                    intialColumn();
                                    configQualification();
                                    configServicesName();
                                    configMedicineUnits();
                                    configTransportOrqanization();
                                    configYieldsCat();
                                    configCategorys();
                                    configAdmissionSpacification();
                                    configAdmissionType();
                                    configAdmissionStatue();
                                    configSurgies();
                                    configDrugs();
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
                super.succeeded();
            }
        };
        service.start();
        qualificationsTab.setOnMouseClicked((e) -> {
            if (qualificationsTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                DoctorQualifications selectedItem = qualificationsTab.getSelectionModel().getSelectedItem();
                qualificationsId.setText(Integer.toString(selectedItem.getId()));
                qualificationsName.setText(selectedItem.getName());
                qualificationsNew.setDisable(false);

                qualificationsDelete.setDisable(false);

                qualificationsEdite.setDisable(false);

                qualificationsAdd.setDisable(true);
            }
        });
        doctor_services_names.setOnMouseClicked((e) -> {
            if (doctor_services_names.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                DoctorsServicesName selectedItem = doctor_services_names.getSelectionModel().getSelectedItem();
                doctor_services_nameId.setText(Integer.toString(selectedItem.getId()));
                doctor_services_namesName.setText(selectedItem.getName());
                doctor_services_namesNew.setDisable(false);
                doctor_services_namesDelete.setDisable(false);
                doctor_services_namesEdite.setDisable(false);
                doctor_services_namesAdd.setDisable(true);
            }
        });
        medicine_unitsTab.setOnMouseClicked((e) -> {
            if (medicine_unitsTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                MedicineUnit selectedItem = medicine_unitsTab.getSelectionModel().getSelectedItem();
                medicine_unitsId.setText(Integer.toString(selectedItem.getId()));
                medicine_unitsName.setText(selectedItem.getUnit());
                medicine_unitsUnit.setText(selectedItem.getValue());
                medicine_unitsNew.setDisable(false);
                medicine_unitsDelete.setDisable(false);
                medicine_unitsEdite.setDisable(false);
                medicine_unitsAdd.setDisable(true);
            }
        });
        patient_transporting_org.setOnMouseClicked((e) -> {
            if (patient_transporting_org.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                TransferOrganization selectedItem = patient_transporting_org.getSelectionModel().getSelectedItem();
                patient_transporting_orgid.setText(Integer.toString(selectedItem.getId()));
                patient_transporting_orgName.setText(selectedItem.getName());
                patient_transporting_orgNew.setDisable(false);

                patient_transporting_orgDelete.setDisable(false);

                patient_transporting_orgEdite.setDisable(false);

                patient_transporting_orgAdd.setDisable(true);
            }
        });
        yields_catTab.setOnMouseClicked((e) -> {
            if (yields_catTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                YieldsCategory selectedItem = yields_catTab.getSelectionModel().getSelectedItem();
                yields_catID.setText(Integer.toString(selectedItem.getId()));
                yields_catName.setText(selectedItem.getName());
                yields_catNew.setDisable(false);
                yields_catEdite.setDisable(false);
                yields_catDelete.setDisable(false);
                yields_catAdd.setDisable(true);
            }
        });
        categorysTab.setOnMouseClicked((e) -> {
            if (categorysTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                Category selectedItem = categorysTab.getSelectionModel().getSelectedItem();
                categorysId.setText(Integer.toString(selectedItem.getId()));
                categorysName.setText(selectedItem.getName());
                categorysNew.setDisable(false);
                categorysEdite.setDisable(false);
                categorysDelete.setDisable(false);
                categorysAdd.setDisable(true);
            }
        });
        admission_spacificationTab.setOnMouseClicked((e) -> {
            if (admission_spacificationTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                AdmissionSpacfication selectedItem = admission_spacificationTab.getSelectionModel().getSelectedItem();
                admission_spacificationId.setText(Integer.toString(selectedItem.getId()));
                admission_spacificationName.setText(selectedItem.getName());
                admission_spacificationNew.setDisable(false);
                admission_spacificationEdite.setDisable(false);
                admission_spacificationDelete.setDisable(false);
                admission_spacificationAdd.setDisable(true);
            }
        });
        admission_typeTab.setOnMouseClicked((e) -> {
            if (admission_typeTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                AdmissionType selectedItem = admission_typeTab.getSelectionModel().getSelectedItem();
                admission_typeId.setText(Integer.toString(selectedItem.getId()));
                admission_typeName.setText(selectedItem.getName());
                admission_typeNew.setDisable(false);
                admission_typeEdite.setDisable(false);
                admission_typeDelete.setDisable(false);
                admission_typeAdd.setDisable(true);
            }
        });
        admission_statueTab.setOnMouseClicked((e) -> {
            if (admission_statueTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                AdmissionStatue selectedItem = admission_statueTab.getSelectionModel().getSelectedItem();
                admission_statueId.setText(Integer.toString(selectedItem.getId()));
                admission_statueName.setText(selectedItem.getName());
                admission_statueNew.setDisable(false);
                admission_statueEdite.setDisable(false);
                admission_statueDelete.setDisable(false);
                admission_statueAdd.setDisable(true);
            }
        });
        admission_services_surgeries_typeTab.setOnMouseClicked((e) -> {
            if (admission_services_surgeries_typeTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                SurgeriesType selectedItem = admission_services_surgeries_typeTab.getSelectionModel().getSelectedItem();
                admission_services_surgeries_typeID.setText(Integer.toString(selectedItem.getId()));
                admission_services_surgeries_typeName.setText(selectedItem.getType());
                admission_services_surgeries_typeNew.setDisable(false);
                admission_services_surgeries_typeEdite.setDisable(false);
                admission_services_surgeries_typeDelete.setDisable(false);
                admission_services_surgeries_typeAdd.setDisable(true);
            }
        });
        drugsTab.setOnMouseClicked((e) -> {
            if (drugsTab.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                DrugsCategeroy selectedItem = drugsTab.getSelectionModel().getSelectedItem();
                drugsId.setText(Integer.toString(selectedItem.getId()));
                drugsName.setText(selectedItem.getName());
                drugsNew.setDisable(false);
                drugsEdite.setDisable(false);
                drugsDelete.setDisable(false);
                drugsAdd.setDisable(true);
            }
        });
    }

    private void intialColumn() {
        qualificationsTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        qualificationsTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        doctor_services_namesTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        doctor_services_namesTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        medicine_unitsTabUnit.setCellValueFactory(new PropertyValueFactory<>("value"));
        medicine_unitsTabName.setCellValueFactory(new PropertyValueFactory<>("unit"));
        medicine_unitsTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        patient_transporting_orgTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        patient_transporting_orgTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        yields_catTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        yields_catTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        categorysTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        categorysTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        admission_spacificationTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        admission_spacificationTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        admission_typeTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        admission_typeTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        admission_statueTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        admission_statueTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        admission_services_surgeries_typeTabName.setCellValueFactory(new PropertyValueFactory<>("type"));
        admission_services_surgeries_typeTabId.setCellValueFactory(new PropertyValueFactory<>("id"));

        drugsTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
        drugsTabId.setCellValueFactory(new PropertyValueFactory<>("id"));
    }

    private void configQualification() {
        try {
            clearQualification();
            qualificationsTab.setItems(DoctorQualifications.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clearQualification() {
        try {
            qualificationsNew.setDisable(true);

            qualificationsDelete.setDisable(true);

            qualificationsEdite.setDisable(true);

            qualificationsAdd.setDisable(false);
            qualificationsId.setText(DoctorQualifications.getAutoNum());
            qualificationsName.setText("");
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void qualificationsNew(ActionEvent event) {
        clearQualification();
    }

    @FXML
    private void qualificationsDelete(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Qualification");
                                    alert.setHeaderText("سيتم حذف التخصص ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DoctorQualifications q = new DoctorQualifications();
                                        q.setId(Integer.parseInt(qualificationsId.getText()));
                                        q.setName(qualificationsName.getText());
                                        q.Delete();
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
                configQualification();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void qualificationsEdite(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  Qualification");
                                    alert.setHeaderText("سيتم تعديل التخصص ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DoctorQualifications q = new DoctorQualifications();
                                        q.setId(Integer.parseInt(qualificationsId.getText()));
                                        q.setName(qualificationsName.getText());
                                        q.Edite();
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
                configQualification();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void qualificationsAdd(ActionEvent event) {
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
                                    DoctorQualifications.Add(qualificationsName.getText());
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
                configQualification();
                super.succeeded();
            }
        };
        service.start();
    }

    private void configServicesName() {
        try {
            clearServicesName();
            doctor_services_names.setItems(DoctorsServicesName.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clearServicesName() {
        try {
            doctor_services_nameId.setText(DoctorsServicesName.getAutoNum());
            doctor_services_namesName.setText("");
            doctor_services_namesNew.setDisable(true);
            doctor_services_namesDelete.setDisable(true);
            doctor_services_namesEdite.setDisable(true);
            doctor_services_namesAdd.setDisable(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void doctor_services_namesNew(ActionEvent event) {
        clearServicesName();
    }

    @FXML
    private void doctor_services_namesDelete(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Service");
                                    alert.setHeaderText("سيتم حذف اسم الخدمة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DoctorsServicesName da = new DoctorsServicesName();
                                        da.setId(Integer.parseInt(doctor_services_nameId.getText()));
                                        da.setName(doctor_services_namesName.getText());
                                        da.Delete();
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
                configServicesName();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void doctor_services_namesEdite(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  Service");
                                    alert.setHeaderText("سيتم تعديل اسم الخدمة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DoctorsServicesName da = new DoctorsServicesName();
                                        da.setId(Integer.parseInt(doctor_services_nameId.getText()));
                                        da.setName(doctor_services_namesName.getText());
                                        da.Edite();
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
                configServicesName();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void doctor_services_namesAdd(ActionEvent event) {
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
                                    DoctorsServicesName.Add(doctor_services_namesName.getText());
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
                configServicesName();
                super.succeeded();
            }
        };
        service.start();
    }

    private void configMedicineUnits() {
        try {
            clearMedicineUnits();
            medicine_unitsTab.setItems(MedicineUnit.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clearMedicineUnits() {
        try {
            medicine_unitsId.setText(MedicineUnit.getAutoNum());
            medicine_unitsName.setText("");
            medicine_unitsUnit.setText("");
            medicine_unitsNew.setDisable(true);
            medicine_unitsDelete.setDisable(true);
            medicine_unitsEdite.setDisable(true);
            medicine_unitsAdd.setDisable(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void medicine_unitsNew(ActionEvent event) {
        clearMedicineUnits();
    }

    @FXML
    private void medicine_unitsDelete(ActionEvent event) {

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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Unit");
                                    alert.setHeaderText("سيتم حذف الوحدة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        MedicineUnit m = new MedicineUnit();
                                        m.setId(Integer.parseInt(medicine_unitsId.getText()));
                                        m.setUnit(medicine_unitsName.getText());
                                        m.setValue(medicine_unitsUnit.getText());
                                        m.Delete();
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
                configMedicineUnits();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void medicine_unitsEdite(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  Unit");
                                    alert.setHeaderText("سيتم تعديل الوحدة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        MedicineUnit m = new MedicineUnit();
                                        m.setId(Integer.parseInt(medicine_unitsId.getText()));
                                        m.setUnit(medicine_unitsName.getText());
                                        m.setValue(medicine_unitsUnit.getText());
                                        m.Edite();
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
                configMedicineUnits();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void medicine_unitsAdd(ActionEvent event) {
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
                                    MedicineUnit m = new MedicineUnit();
                                    m.setId(Integer.parseInt(medicine_unitsId.getText()));
                                    m.setUnit(medicine_unitsName.getText());
                                    m.setValue(medicine_unitsUnit.getText());
                                    m.Add();
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
                configMedicineUnits();
                super.succeeded();
            }
        };
        service.start();
    }

    private void configTransportOrqanization() {
        try {
            clearTransportOrqanization();
            patient_transporting_org.setItems(TransferOrganization.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clearTransportOrqanization() {
        try {
            patient_transporting_orgid.setText(TransferOrganization.getAutoNum());
            patient_transporting_orgName.setText("");
            patient_transporting_orgNew.setDisable(true);

            patient_transporting_orgDelete.setDisable(true);

            patient_transporting_orgEdite.setDisable(true);

            patient_transporting_orgAdd.setDisable(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void patient_transporting_orgNew(ActionEvent event) {
        clearTransportOrqanization();
    }

    @FXML
    private void patient_transporting_orgDelete(ActionEvent event) {

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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  org");
                                    alert.setHeaderText("سيتم حذف الجهة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        TransferOrganization t = new TransferOrganization();
                                        t.setId(Integer.parseInt(patient_transporting_orgid.getText()));
                                        t.setName(patient_transporting_orgName.getText());
                                        t.Delete();
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
                configTransportOrqanization();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void patient_transporting_orgEdite(ActionEvent event) {
//        
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  org");
                                    alert.setHeaderText("سيتم تعديل الجهة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        TransferOrganization t = new TransferOrganization();
                                        t.setId(Integer.parseInt(patient_transporting_orgid.getText()));
                                        t.setName(patient_transporting_orgName.getText());
                                        t.Edite();
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
                configTransportOrqanization();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void patient_transporting_orgAdd(ActionEvent event) {
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
                                    TransferOrganization.Add(patient_transporting_orgName.getText());
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
                configTransportOrqanization();
                super.succeeded();
            }
        };
        service.start();
    }

    private void configYieldsCat() {
        try {
            clearYieldsCat();
            yields_catTab.setItems(YieldsCategory.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clearYieldsCat() {
        try {
            yields_catID.setText(YieldsCategory.getAutoNum());
            yields_catName.setText("");
            yields_catNew.setDisable(true);
            yields_catEdite.setDisable(true);
            yields_catDelete.setDisable(true);
            yields_catAdd.setDisable(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void yields_catNew(ActionEvent event) {
        clearYieldsCat();
    }

    @FXML
    private void yields_catDelete(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Cat");
                                    alert.setHeaderText("سيتم حذف التصنيف ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        YieldsCategory y = new YieldsCategory();
                                        y.setId(Integer.parseInt(yields_catID.getText()));
                                        y.setName(yields_catName.getText());
                                        y.Delete();
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
                configYieldsCat();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void yields_catEdite(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  Cat");
                                    alert.setHeaderText("سيتم تعديل التصنيف ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        YieldsCategory y = new YieldsCategory();
                                        y.setId(Integer.parseInt(yields_catID.getText()));
                                        y.setName(yields_catName.getText());
                                        y.Edite();
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
                configYieldsCat();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void yields_catAdd(ActionEvent event) {
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
                                    YieldsCategory.Add(yields_catName.getText());

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
                configYieldsCat();
                super.succeeded();
            }
        };
        service.start();
    }

    private void configCategorys() {
        try {
            clearCategorys();
            categorysTab.setItems(Category.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clearCategorys() {
        try {
            categorysId.setText(Category.getAutoNum());
            categorysName.setText("");
            categorysNew.setDisable(true);
            categorysEdite.setDisable(true);
            categorysDelete.setDisable(true);
            categorysAdd.setDisable(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void categorysNew(ActionEvent event) {
        clearCategorys();
    }

    @FXML
    private void categorysDelete(ActionEvent event) {

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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Cat");
                                    alert.setHeaderText("سيتم حذف التصنيف ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Category a = new Category();
                                        a.setId(Integer.parseInt(categorysId.getText()));
                                        a.setName(categorysName.getText());
                                        a.Delete();
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
                configCategorys();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void categorysEdite(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  Cat");
                                    alert.setHeaderText("سيتم تعديل التصنيف ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        Category a = new Category();
                                        a.setId(Integer.parseInt(categorysId.getText()));
                                        a.setName(categorysName.getText());
                                        a.Edite();
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
                configCategorys();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void categorysAdd(ActionEvent event) {
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
                                    Category.Add(categorysName.getText());

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
                configCategorys();
                super.succeeded();
            }
        };
        service.start();
    }

    private void configAdmissionSpacification() {
        try {
            clearAdmissionSpadification();
            admission_spacificationTab.setItems(AdmissionSpacfication.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clearAdmissionSpadification() {
        try {
            admission_spacificationId.setText(AdmissionSpacfication.getAutoNum());
            admission_spacificationName.setText("");
            admission_spacificationNew.setDisable(true);
            admission_spacificationEdite.setDisable(true);
            admission_spacificationDelete.setDisable(true);
            admission_spacificationAdd.setDisable(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void admission_spacificationNew(ActionEvent event) {
        clearAdmissionSpadification();
    }

    @FXML
    private void admission_spacificationDelete(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Spacification");
                                    alert.setHeaderText("سيتم حذف التخصص ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        AdmissionSpacfication a = new AdmissionSpacfication();
                                        a.setId(Integer.parseInt(admission_spacificationId.getText()));
                                        a.setName(admission_spacificationName.getText());
                                        a.Delete();
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
                configAdmissionSpacification();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void admission_spacificationEdite(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  Spacification");
                                    alert.setHeaderText("سيتم تعديل التخصص ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        AdmissionSpacfication a = new AdmissionSpacfication();
                                        a.setId(Integer.parseInt(admission_spacificationId.getText()));
                                        a.setName(admission_spacificationName.getText());
                                        a.Edite();
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
                configAdmissionSpacification();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void admission_spacificationAdd(ActionEvent event) {
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
                                    AdmissionSpacfication.Add(admission_spacificationName.getText());

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
                configAdmissionSpacification();
                super.succeeded();
            }
        };
        service.start();
    }

    private void configAdmissionType() {
        try {
            clearAdmissionType();
            admission_typeTab.setItems(AdmissionType.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clearAdmissionType() {
        try {
            admission_typeId.setText(AdmissionType.getAutoNum());
            admission_typeName.setText("");
            admission_typeNew.setDisable(true);
            admission_typeEdite.setDisable(true);
            admission_typeDelete.setDisable(true);
            admission_typeAdd.setDisable(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void admission_typeNew(ActionEvent event) {
        clearAdmissionType();
    }

    @FXML
    private void admission_typeDelete(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Type");
                                    alert.setHeaderText("سيتم حذف النوع ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        AdmissionType a = new AdmissionType();
                                        a.setId(Integer.parseInt(admission_typeId.getText()));
                                        a.setName(admission_typeName.getText());
                                        a.Delete();
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
                configAdmissionType();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void admission_typeEdite(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  Type");
                                    alert.setHeaderText("سيتم تعديل النوع ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        AdmissionType a = new AdmissionType();
                                        a.setId(Integer.parseInt(admission_typeId.getText()));
                                        a.setName(admission_typeName.getText());
                                        a.Edite();
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
                configAdmissionType();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void admission_typeAdd(ActionEvent event) {
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
                                    AdmissionType.Add(admission_typeName.getText());

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
                configAdmissionType();
                super.succeeded();
            }
        };
        service.start();
    }

    private void configAdmissionStatue() {
        try {
            clearAdmissionStatue();
            admission_statueTab.setItems(AdmissionStatue.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clearAdmissionStatue() {
        try {
            admission_statueId.setText(AdmissionStatue.getAutoNum());
            admission_statueName.setText("");
            admission_statueNew.setDisable(true);
            admission_statueEdite.setDisable(true);
            admission_statueDelete.setDisable(true);
            admission_statueAdd.setDisable(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void admission_statueNew(ActionEvent event) {
        clearAdmissionStatue();
    }

    @FXML
    private void admission_statueDelete(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Statue");
                                    alert.setHeaderText("سيتم حذف الحالة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        AdmissionStatue a = new AdmissionStatue();
                                        a.setId(Integer.parseInt(admission_statueId.getText()));
                                        a.setName(admission_statueName.getText());
                                        a.Delete();
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
                configAdmissionStatue();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void admission_statueEdite(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  Statue");
                                    alert.setHeaderText("سيتم تعديل الحالة ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        AdmissionStatue a = new AdmissionStatue();
                                        a.setId(Integer.parseInt(admission_statueId.getText()));
                                        a.setName(admission_statueName.getText());
                                        a.Edite();
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
                configAdmissionStatue();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void admission_statueAdd(ActionEvent event) {
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
                                    AdmissionStatue.Add(admission_statueName.getText());

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
                configAdmissionStatue();
                super.succeeded();
            }
        };
        service.start();
    }

    private void configSurgies() {
        try {
            clearSurgies();
            admission_services_surgeries_typeTab.setItems(SurgeriesType.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clearSurgies() {
        try {
            admission_services_surgeries_typeID.setText(SurgeriesType.getAutoNum());
            admission_services_surgeries_typeName.setText("");
            admission_services_surgeries_typeNew.setDisable(true);
            admission_services_surgeries_typeEdite.setDisable(true);
            admission_services_surgeries_typeDelete.setDisable(true);
            admission_services_surgeries_typeAdd.setDisable(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void admission_services_surgeries_typeNew(ActionEvent event) {
        clearSurgies();
    }

    @FXML
    private void admission_services_surgeries_typeDelete(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Surgies");
                                    alert.setHeaderText("سيتم حذف العملية ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        SurgeriesType a = new SurgeriesType();
                                        a.setId(Integer.parseInt(admission_services_surgeries_typeID.getText()));
                                        a.setType(admission_services_surgeries_typeName.getText());
                                        a.Delete();
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
                configSurgies();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void admission_services_surgeries_typeEdite(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  Surgies");
                                    alert.setHeaderText("سيتم تعديل العملية ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        SurgeriesType a = new SurgeriesType();
                                        a.setId(Integer.parseInt(admission_services_surgeries_typeID.getText()));
                                        a.setType(admission_services_surgeries_typeName.getText());
                                        a.Edite();
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
                configSurgies();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void admission_services_surgeries_typeAdd(ActionEvent event) {
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
                                    SurgeriesType.Add(admission_services_surgeries_typeName.getText());

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
                configSurgies();
                super.succeeded();
            }
        };
        service.start();
    }

    private void configDrugs() {
        try {
            clearDrugs();
            drugsTab.setItems(DrugsCategeroy.getData());
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    private void clearDrugs() {
        try {
            drugsId.setText(DrugsCategeroy.getAutoNum());
            drugsName.setText("");
            drugsNew.setDisable(true);
            drugsEdite.setDisable(true);
            drugsDelete.setDisable(true);
            drugsAdd.setDisable(false);
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }
    }

    @FXML
    private void drugsNew(ActionEvent event) {
        clearDrugs();
    }

    @FXML
    private void drugsDelete(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Deleting  Cat");
                                    alert.setHeaderText("سيتم حذف التصنيف ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DrugsCategeroy a = new DrugsCategeroy();
                                        a.setId(Integer.parseInt(drugsId.getText()));
                                        a.setName(drugsName.getText());
                                        a.Delete();
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
                configDrugs();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void drugsEdite(ActionEvent event) {
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
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Editing  Cat");
                                    alert.setHeaderText("سيتم تعديل التصنيف ");
                                    alert.setContentText("هل انت متاكد؟");

                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        DrugsCategeroy a = new DrugsCategeroy();
                                        a.setId(Integer.parseInt(drugsId.getText()));
                                        a.setName(drugsName.getText());
                                        a.Edite();
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
                configDrugs();
                super.succeeded();
            }
        };
        service.start();
    }

    @FXML
    private void drugsAdd(ActionEvent event) {
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
                                    DrugsCategeroy.Add(drugsName.getText());

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
                configDrugs();
                super.succeeded();
            }
        };
        service.start();
    }

}
