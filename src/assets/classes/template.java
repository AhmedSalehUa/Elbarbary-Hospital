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
         * 
         *

            *   
         */
        d.add("ALTER TABLE `drg_accounts` ADD `date_of_entrance` DATE NULL AFTER `patient_id`, ADD `date_of_exite` DATE NULL AFTER `date_of_entrance`;");
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
    
     progress.setVisible(true); 
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                                try {
                                    

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
               clear();getData();
                super.succeeded();
            }
        };
        service.start();
    
    boolean ok = true;
    ok = false;
    if (ok) {
                    clear();
                    getData();
                }
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
    
    babyDateOfBirth.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return format.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, format);
            }
        });
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
