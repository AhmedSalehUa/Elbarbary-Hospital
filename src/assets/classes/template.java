/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assets.classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Parent node = FXMLLoader.load(getClass().getResource(NoPermission)); if
 * (User.canAccess("accountContracts")) { node =
 * FXMLLoader.load(getClass().getResource(AccountsScreenContract)); } access
 * list accounts accountExpenses accountYields accountContracts
 * accountMedicineCompany
 *
 * MainDataScreenContract MainDataScreenClincs MainDataScreenMedicine
 * MainDataScreenDoctors MainDataScreenPatient
 *
 * AdmissionScreen ReceptionScreenDailyExpenses ReceptionScreenExportToAccounts
 * ReceptionScreenShortsOrder ReceptionScreenGetYields
 *
 *
 *
 *
 *
 */
public class template {

    public template() {
        ObservableList<String> d = FXCollections.observableArrayList();
        /**
         * CREATE TABLE `admission_surgies_medicine` ( `id` int(11) NOT NULL
         * AUTO_INCREMENT, `admission_id` int(11) NOT NULL, `medicine_id`
         * int(11) NOT NULL, `amount` varchar(700) NOT NULL, PRIMARY KEY (`id`),
         * KEY `admission_id` (`admission_id`), KEY `medicine_id`
         * (`medicine_id`), CONSTRAINT `admission_surgies_medicine_ibfk_1`
         * FOREIGN KEY (`admission_id`) REFERENCES `admission` (`id`),
         * CONSTRAINT `admission_surgies_medicine_ibfk_2` FOREIGN KEY
         * (`medicine_id`) REFERENCES `stores_medicines` (`id`) ) ENGINE=InnoDB
         * AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
         *
         * CREATE TABLE `elbarbary_hospital`.`att_employee_solfa` ( `id` INT(11)
         * NOT NULL AUTO_INCREMENT , `emp_id` INT(11) NOT NULL , `acc_id`
         * INT(11) NOT NULL , `amount` VARCHAR(700) NOT NULL , `date` DATE NOT
         * NULL , PRIMARY KEY (`id`), INDEX (`emp_id`), INDEX (`acc_id`)) ENGINE
         * = InnoDB;
         *
         * CREATE TABLE `elbarbary_hospital`.`att_report` ( `id` INT(11) NOT
         * NULL AUTO_INCREMENT , `emp_id` INT(11) NOT NULL , `emp_name`
         * VARCHAR(700) NOT NULL , `date` DATE NOT NULL , `shift_name`
         * VARCHAR(700) NOT NULL , `shift_start` TIME NOT NULL , `shift_end`
         * TIME NOT NULL , `emp_att` TIME NOT NULL , `emp_leave` TIME NOT NULL ,
         * `overtime` VARCHAR(700) NULL , `late` VARCHAR(700) NULL ,
         * `earlyLeave` VARCHAR(700) NULL , `salary_calc` VARCHAR(700) NULL ,
         * `statue` VARCHAR(700) NOT NULL , `notes` VARCHAR(700) NULL , PRIMARY
         * KEY (`id`)) ENGINE = InnoDB;
         *
         * ALTER TABLE `att_report` ADD UNIQUE( `emp_id`, `date`);
         *
         *
         * ALTER TABLE `att_report` CHANGE `shift_name` `shift_name`
         * VARCHAR(700) CHARACTER SET utf8 COLLATE utf8_general_ci NULL, CHANGE
         * `shift_start` `shift_start` TIME NULL, CHANGE `shift_end` `shift_end`
         * TIME NULL, CHANGE `emp_att` `emp_att` TIME NULL, CHANGE `emp_leave`
         * `emp_leave` TIME NULL, CHANGE `statue` `statue` VARCHAR(700)
         * CHARACTER SET utf8 COLLATE utf8_general_ci NULL;
         *
         * create TABLE drg_patient like patients;
         *
         * create TABLE drg_patient_escort like patient_escort;
         *
         * create TABLE drg_patient_photo like patients_photo;
         *
         * CREATE TABLE `elbarbary_hospital`.`drg_accounts` ( `id` INT(11) NOT NULL AUTO_INCREMENT , `patient_id` INT(11) NOT NULL , `total_paied` VARCHAR(700) NOT NULL , `total_spended` VARCHAR(700) NOT NULL , `remaining` VARCHAR(700) NOT NULL , PRIMARY KEY (`id`), UNIQUE (`patient_id`)) ENGINE = InnoDB;
         * 
         * CREATE TABLE `elbarbary_hospital`.`drg_expense_cat` ( `id` INT(11) NOT NULL AUTO_INCREMENT , `name` VARCHAR(700) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;
         * 
         * CREATE TABLE `elbarbary_hospital`.`drg_patient_expenses` ( `id` INT(11) NOT NULL AUTO_INCREMENT , `patient_id` INT(11) NOT NULL , `acc_id` INT(11) NOT NULL , `cat_id` INT(11) NOT NULL , `amount` VARCHAR(700) NOT NULL , `date` DATE NOT NULL , PRIMARY KEY (`id`), INDEX (`patient_id`), INDEX (`acc_id`), INDEX (`cat_id`)) ENGINE = InnoDB;
         * 
         * CREATE TABLE `elbarbary_hospital`.`drg_money_in` ( `id` INT(11) NOT NULL AUTO_INCREMENT , `patient_id` INT(11) NOT NULL , `escort_id` INT(11) NOT NULL , `amount` VARCHAR(700) NOT NULL , `date` DATE NOT NULL , PRIMARY KEY (`id`), INDEX (`patient_id`), INDEX (`escort_id`)) ENGINE = InnoDB;
         * 
         * CREATE TABLE `elbarbary_hospital`.`drg_money_out` ( `id` INT(11) NOT NULL AUTO_INCREMENT , `patient_id` INT(11) NOT NULL , `escort_id` INT(11) NOT NULL , `amount` VARCHAR(700) NOT NULL , `date` DATE NOT NULL , PRIMARY KEY (`id`), INDEX (`patient_id`), INDEX (`escort_id`)) ENGINE = InnoDB;
         * 
         * CREATE TABLE `elbarbary_hospital`.`drg_patient_service` ( `id` INT(11) NOT NULL AUTO_INCREMENT , `patient_id` INT(11) NOT NULL , `acc_id` INT(11) NOT NULL , `doctor_id` INT(11) NOT NULL , `service_id` INT(11) NOT NULL , `cost` VARCHAR(700) NOT NULL , `date` DATE NOT NULL , PRIMARY KEY (`id`), INDEX (`patient_id`), INDEX (`acc_id`), INDEX (`doctor_id`), INDEX (`service_id`)) ENGINE = InnoDB;
         * 
         * CREATE TABLE `elbarbary_hospital`.`drg_patient_medicine` ( `id` INT(11) NOT NULL AUTO_INCREMENT , `patient_id` INT(11) NOT NULL , `acc_id` INT(11) NOT NULL , `medicine_id` INT(11) NOT NULL , `amount` VARCHAR(700) NOT NULL , `cost_for_one` VARCHAR(700) NOT NULL , `total_cost` VARCHAR(700) NOT NULL , PRIMARY KEY (`id`), INDEX (`patient_id`), INDEX (`acc_id`), INDEX (`medicine_id`)) ENGINE = InnoDB;
         * 
         * 

            *   
         */
        d.add("");
        d.add("");
        d.add("");
        d.add("");
        d.add("");
        d.add("");
        d.add("");
        d.add("");
        d.add("");

    }
    /*
      public boolean Add() throws Exception {PreparedStatement ps = db.get.Prepare("");ps.execute();return true;
    }

    public boolean Edite() throws Exception {PreparedStatement ps = db.get.Prepare("");ps.execute();return true;
    }

    public boolean Delete() throws Exception {PreparedStatement ps = db.get.Prepare("");ps.execute();return true;
    }

    public static ObservableList<> getData() throws Exception {
    ObservableList<> data = FXCollections.observableArrayList();
        ResultSet rs = db.get.getReportCon().createStatement().executeQuery("");
        while(rs.next()){
            data.add();
        }
        return data;
    }

    public static String getAutoNum() throws Exception {
     return db.get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM ``").getValueAt(0, 0).toString();
    }
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
                progress.setVisible(false);
               clear();getData();
                super.succeeded();
            }
        };
        service.start();
      serviceTabName.setCellValueFactory(new PropertyValueFactory<>("name"));
         .setCellValueFactory(new PropertyValueFactory<>(""));
    
    
    
    FilteredList<> filteredData = new FilteredList<>(items, p -> true);

        filteredData.setPredicate(pa -> {

            if (search.getText() == null || search.getText().isEmpty()) {
                return true;
            }

            String lowerCaseFilter = search.getText().toLowerCase();

            return (pa.getName().contains(lowerCaseFilter)
                    || pa.getAge().contains(lowerCaseFilter) 
                    || pa.getQualification_name().contains(lowerCaseFilter)
                    || pa.getTele1().contains(lowerCaseFilter)
                    || pa.getTele2().contains(lowerCaseFilter));

        });

        SortedList< > sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(.comparatorProperty());
        .setItems(sortedData);
    
     DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Deleting  ");
                                        alert.setHeaderText("سيتم حذف ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {}
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Editing  ");
                                        alert.setHeaderText("سيتم تعديل ");
                                        alert.setContentText("هل انت متاكد؟");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.get() == ButtonType.OK) {}
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                                    clear();
                                    intialColumn();
                                    getData();
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

            @Override
            protected void succeeded() {
                progress.setVisible(false);

                super.succeeded();
            }
        };
        service.start();
    }
private void intialColumn() {

    }
    private void clear() {
        getAutoNum();
     formNew.setDisable(true);

            formDelete.setDisable(true);

            formEdite.setDisable(true);

            formAdd.setDisable(false);
    }

    private void getAutoNum() {

    }

    private void getData() {

    }

    private void fillCombo() {

    }
    
    deptTable.setOnMouseClicked((e) -> {
            if (deptTable.getSelectionModel().getSelectedIndex() == -1) {
            } else {
                formNew.setDisable(false);
                
                formDelete.setDisable(false);
                
                formEdite.setDisable(false);
                
                formAdd.setDisable(true);
                
                  selected = deptTable.getSelectionModel().getSelectedItem();
                deptId.setText(Integer.toString(selected.getId()));
                deptName.setText(selected.getName());
                
                ObservableList<Organization> items1 = deptOrg.getItems();
                for (Organization a : items1) {
                    if (a.getName().equals(selected.getOrganization())) {
                        deptOrg.getSelectionModel().select(a);
                    }
                }
            }
        });
    
    
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
    
    
    
       TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Cat Name");
        dialog.setHeaderText("اضافة تصنيف جديد");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (result.get().isEmpty() || result.get() == null) {
                AlertDialogs.showError("خطا!! يرجي ادخال اسم نوع");
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
  try {
            Parent mainMember = FXMLLoader.load(getClass().getResource(HrScreenEmployes));
            mainMember.getStylesheets().add(getClass().getResource("/assets/styles/" + prefs.get(THEME, DEFAULT_THEME) + ".css").toExternalForm());
            Scene sc = new Scene(mainMember);
            Stage st = new Stage();
            st.getIcons().add(new Image(getClass().getResourceAsStream("/assets/icons/logo.png")));
            st.setTitle("Elbarbary Hospital (الموظفين)");
            st.setScene(sc);
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            st.setX((screenBounds.getWidth() - 1360) / 2);
            st.setY((screenBounds.getHeight() - 760) / 2);
            st.show();
        } catch (IOException ex) {
            AlertDialogs.showErrors(ex);
        }*/
}
