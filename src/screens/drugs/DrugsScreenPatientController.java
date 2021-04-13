/*      */ package screens.drugs;
/*      */ 
/*      */ import assets.classes.AlertDialogs;
/*      */ import com.jfoenix.controls.JFXDatePicker;
/*      */ import com.jfoenix.controls.JFXDrawer;
/*      */ import com.jfoenix.controls.JFXTextField;
/*      */ import com.jfoenix.controls.events.JFXDrawerEvent;
/*      */ import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
/*      */ import de.jensd.fx.glyphs.materialicons.MaterialIconView;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.InputStream;
/*      */ import java.net.URL;
/*      */ import java.time.LocalDate;
/*      */ import java.time.format.DateTimeFormatter;
/*      */ import java.util.Optional;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.concurrent.CountDownLatch;
/*      */ import java.util.regex.Pattern;
/*      */ import javafx.application.Platform;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.collections.transformation.FilteredList;
/*      */ import javafx.collections.transformation.SortedList;
/*      */ import javafx.concurrent.Service;
/*      */ import javafx.concurrent.Task;
/*      */ import javafx.event.ActionEvent;
/*      */ import javafx.fxml.FXML;
/*      */ import javafx.fxml.Initializable;
/*      */ import javafx.scene.Node;
/*      */ import javafx.scene.control.Alert;
/*      */ import javafx.scene.control.Button;
/*      */ import javafx.scene.control.ButtonType;
/*      */ import javafx.scene.control.ComboBox;
/*      */ import javafx.scene.control.Label;
/*      */ import javafx.scene.control.ListCell;
/*      */ import javafx.scene.control.ListView;
/*      */ import javafx.scene.control.ProgressIndicator;
/*      */ import javafx.scene.control.TableColumn;
/*      */ import javafx.scene.control.TableView;
/*      */ import javafx.scene.control.TextField;
/*      */ import javafx.scene.control.TextInputDialog;
/*      */ import javafx.scene.control.cell.PropertyValueFactory;
/*      */ import javafx.scene.image.ImageView;
/*      */ import javafx.scene.input.KeyEvent;
/*      */ import javafx.scene.input.MouseEvent;
/*      */ import javafx.scene.layout.ColumnConstraints;
/*      */ import javafx.scene.layout.GridPane;
/*      */ import javafx.stage.FileChooser;
/*      */ import javafx.stage.Stage;
/*      */ import javafx.stage.Window;
/*      */ import javafx.util.Callback;
/*      */ import javafx.util.StringConverter;
/*      */ import screens.drugs.assets.DrugsAccounts;
/*      */ import screens.drugs.assets.DrugsPatients;
/*      */ import screens.drugs.assets.DrugsPatientsEscort;
/*      */ import screens.mainDataScreen.assets.Doctors;
/*      */ import screens.mainDataScreen.assets.TransferOrganization;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DrugsScreenPatientController
/*      */   implements Initializable
/*      */ {
/*      */   @FXML
/*      */   private TableView<DrugsPatients> patientTable;
/*      */   @FXML
/*      */   private TableColumn<DrugsPatients, String> patientTabTele2;
/*      */   @FXML
/*      */   private TableColumn<DrugsPatients, String> patientTabTele1;
/*      */   @FXML
/*      */   private TableColumn<DrugsPatients, String> patientTabName;
/*      */   @FXML
/*      */   private TableColumn<DrugsPatients, String> patientTabId;
/*      */   @FXML
/*      */   private TableColumn<DrugsPatients, String> patientTabDiagnoses;
/*      */   @FXML
/*      */   private TableColumn<DrugsPatients, String> patientTabDoctor;
/*      */   @FXML
/*      */   private Label patientId;
/*      */   @FXML
/*      */   private TextField patientName;
/*      */   @FXML
/*      */   private TextField patientAddress;
/*      */   @FXML
/*      */   private TextField patientAge;
/*      */   @FXML
/*      */   private TextField patientTele1;
/*      */   @FXML
/*      */   private TextField patientTele2;
/*      */   @FXML
/*      */   private Button patientNew;
/*      */   @FXML
/*      */   private Button patientEdite;
/*      */   @FXML
/*      */   private Button patientAdd;
/*      */   @FXML
/*      */   private Button patientDelete;
/*      */   @FXML
/*      */   private JFXTextField search;
/*      */   @FXML
/*      */   private ProgressIndicator progress;
/*      */   @FXML
/*      */   private TextField patientNational;
/*      */   @FXML
/*      */   private ImageView patientPhotoPicker;
/*      */   @FXML
/*      */   private TextField patientPhoto;
/*      */   @FXML
/*      */   private TextField patientGiagnose;
/*      */   @FXML
/*      */   private ComboBox<Doctors> patientDoctor;
/*      */   @FXML
/*      */   private ImageView showPicture;
/*      */   @FXML
/*      */   private JFXTextField searchEscort;
/*      */   @FXML
/*      */   private ImageView showEscortPicture;
/*      */   @FXML
/*      */   private TableView<DrugsPatientsEscort> escortTable;
/*      */   @FXML
/*      */   private TableColumn<DrugsPatientsEscort, String> escortTabNational;
/*      */   @FXML
/*      */   private TableColumn<DrugsPatientsEscort, String> escortTabName;
/*      */   @FXML
/*      */   private TableColumn<DrugsPatientsEscort, String> escortTabId;
/*      */   @FXML
/*      */   private TableColumn<DrugsPatientsEscort, String> escortTabTele2;
/*      */   @FXML
/*      */   private TableColumn<DrugsPatientsEscort, String> escortTabTele1;
/*      */   @FXML
/*      */   private TableColumn<DrugsPatientsEscort, String> escortTabAddress;
/*      */   @FXML
/*      */   private TableColumn<DrugsPatientsEscort, String> escortTabRelation;
/*      */   @FXML
/*      */   private Label escortId;
/*      */   @FXML
/*      */   private TextField escortName;
/*      */   @FXML
/*      */   private TextField escortNational;
/*      */   @FXML
/*      */   private ImageView escortPhotoPicker;
/*      */   @FXML
/*      */   private TextField escortPhoto;
/*      */   @FXML
/*      */   private ProgressIndicator escortProgress;
/*      */   @FXML
/*      */   private Button escortNew;
/*      */   @FXML
/*      */   private Button escortDelete;
/*      */   @FXML
/*      */   private Button escortEdite;
/*      */   @FXML
/*      */   private Button escortAdd;
/*      */   @FXML
/*      */   private JFXDatePicker patientDateOfBirth;
/*      */   @FXML
/*      */   private TextField patientGovernment;
/*      */   @FXML
/*      */   private ComboBox<TransferOrganization> patientTransName;
/*      */   @FXML
/*      */   private TextField escortTele1;
/*      */   @FXML
/*      */   private TextField escortRelation;
/*      */   @FXML
/*      */   private TextField escortAddress;
/*      */   @FXML
/*      */   private TextField escortTele2;
/*      */   @FXML
/*      */   private MaterialIconView addTranspering;
/*      */   @FXML
/*      */   private ComboBox<String> patientGender;
/*      */   @FXML
/*      */   private FontAwesomeIconView ham;
/*      */   @FXML
/*      */   private JFXDrawer drawer;
/*      */   ObservableList<DrugsPatientsEscort> Escortitems;
/*      */   ObservableList<DrugsPatients> items;
/*      */   
/*      */   public void initialize(URL url, ResourceBundle rb) {
/*  195 */     Service<Void> service = new Service<Void>()
/*      */       {
/*      */         protected Task<Void> createTask() {
/*  198 */           return new Task<Void>()
/*      */             {
/*      */               protected Void call() throws Exception
/*      */               {
/*  202 */                 final CountDownLatch latch = new CountDownLatch(1);
/*  203 */                 Platform.runLater(new Runnable()
/*      */                     {
/*      */                       public void run() {
/*      */                         try {
/*  207 */                           DrugsScreenPatientController.this.clear();
/*  208 */                           DrugsScreenPatientController.this.setTableColumns();
/*  209 */                           DrugsScreenPatientController.this.getDataToTable();
/*  210 */                           DrugsScreenPatientController.this.fillCombo();
/*      */                           
/*  212 */                           double drawerx = DrugsScreenPatientController.this.drawer.getLayoutX();
/*  213 */                           double drawery = DrugsScreenPatientController.this.drawer.getLayoutY();
/*  214 */                           DrugsScreenPatientController.this.drawer.setLayoutX(drawerx + 600.0D);
/*  215 */                           DrugsScreenPatientController.this.drawer.setLayoutY(drawery);
/*  216 */                           DrugsScreenPatientController.this.drawer.setVisible(false);
/*  217 */                           DrugsScreenPatientController.this.drawer.setMaxWidth(0.0D);
/*      */                           
/*  219 */                           DrugsScreenPatientController.this.drawer.setOnDrawerOpening(e -> {
/*      */                                 DrugsScreenPatientController.this.drawer.setLayoutX(drawerx);
/*      */                                 
/*      */                                 DrugsScreenPatientController.this.drawer.setLayoutY(drawery);
/*      */                                 
/*      */                                 DrugsScreenPatientController.this.drawer.setMaxWidth(600.0D);
/*      */                               });
/*  226 */                           DrugsScreenPatientController.this.drawer.setOnDrawerClosed(e -> {
/*      */                                 DrugsScreenPatientController.this.drawer.setLayoutX(drawerx + 600.0D);
/*      */                                 
/*      */                                 DrugsScreenPatientController.this.drawer.setLayoutY(drawery);
/*      */                                 
/*      */                                 DrugsScreenPatientController.this.drawer.setVisible(false);
/*      */                                 DrugsScreenPatientController.this.drawer.setMaxWidth(0.0D);
/*      */                               });
/*  234 */                           DrugsScreenPatientController.this.ham.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
/*      */                                 DrugsScreenPatientController.this.drawer.setVisible(true);
/*      */                                 
/*      */                                 if (DrugsScreenPatientController.this.drawer.isOpened()) {
/*      */                                   DrugsScreenPatientController.this.drawer.close();
/*      */                                 } else {
/*      */                                   DrugsScreenPatientController.this.drawer.open();
/*      */                                 } 
/*      */                               });
/*  243 */                         } catch (Exception ex) {
/*  244 */                           AlertDialogs.showErrors(ex);
/*      */                         } finally {
/*  246 */                           latch.countDown();
/*      */                         } 
/*      */                       }
/*      */                     });
/*  250 */                 latch.await();
/*      */                 
/*  252 */                 return null;
/*      */               }
/*      */             };
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         protected void succeeded() {
/*  261 */           DrugsScreenPatientController.this.progress.setVisible(false);
/*  262 */           super.succeeded();
/*      */         }
/*      */       };
/*  265 */     service.start();
/*      */     
/*  267 */     this.patientTable.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
/*      */           if (this.patientTable.getSelectionModel().getSelectedIndex() == -1) {
/*      */             this.escortTable.setItems(null);
/*      */           } else {
/*      */             this.patientAdd.setDisable(true);
/*      */             
/*      */             this.patientEdite.setDisable(false);
/*      */             
/*      */             this.patientDelete.setDisable(false);
/*      */             
/*      */             this.patientNew.setDisable(false);
/*      */             DrugsPatients pa = (DrugsPatients)this.patientTable.getSelectionModel().getSelectedItem();
/*      */             this.patientId.setText(Integer.toString(pa.getId()));
/*      */             this.patientName.setText(pa.getName());
/*      */             this.patientAddress.setText(pa.getAddress());
/*      */             this.patientAge.setText(pa.getAge());
/*      */             this.patientTele1.setText(pa.getTele1());
/*      */             this.patientTele2.setText(pa.getTele2());
/*      */             this.patientNational.setText(pa.getNational_id());
/*      */             this.patientGiagnose.setText(pa.getDiagnosis());
/*      */             this.patientDateOfBirth.setValue(LocalDate.parse(pa.getDateOfBirth()));
/*      */             this.patientGovernment.setText(pa.getGovernment());
/*      */             this.patientGender.getSelectionModel().select(pa.getGender());
/*      */             ObservableList<TransferOrganization> items = this.patientTransName.getItems();
/*      */             for (TransferOrganization a : items) {
/*      */               if (a.getName().equals(pa.getTranportOrg())) {
/*      */                 this.patientTransName.getSelectionModel().select(a);
/*      */               }
/*      */             } 
/*      */             ObservableList<Doctors> items1 = this.patientDoctor.getItems();
/*      */             for (Doctors a : items1) {
/*      */               if (a.getName().equals(pa.getDoctor_name())) {
/*      */                 this.patientDoctor.getSelectionModel().select(a);
/*      */               }
/*      */             } 
/*      */             try {
/*      */               this.escortProgress.setVisible(true);
/*      */               this.escortTable.setItems(DrugsPatientsEscort.getData(Integer.toString(pa.getId())));
/*      */               this.Escortitems = this.escortTable.getItems();
/*      */               clearEscort();
/*      */               this.escortProgress.setVisible(false);
/*  308 */             } catch (Exception ex) {
/*      */               AlertDialogs.showErrors(ex);
/*      */             } 
/*      */           } 
/*      */         });
/*  313 */     this.escortTable.setOnMouseClicked(e -> {
/*      */           if (this.escortTable.getSelectionModel().getSelectedIndex() != -1) {
/*      */             DrugsPatientsEscort ps = (DrugsPatientsEscort)this.escortTable.getSelectionModel().getSelectedItem();
/*      */             this.escortAdd.setDisable(true);
/*      */             this.escortEdite.setDisable(false);
/*      */             this.escortDelete.setDisable(false);
/*      */             this.escortNew.setDisable(false);
/*      */             this.escortId.setText(Integer.toString(ps.getId()));
/*      */             this.escortName.setText(ps.getName());
/*      */             this.escortRelation.setText(ps.getRelation());
/*      */             this.escortAddress.setText(ps.getAddress());
/*      */             this.escortNational.setText(ps.getNationaId());
/*      */             this.escortPhoto.setText("");
/*      */             this.escortTele1.setText(ps.getTele1());
/*      */             this.escortTele2.setText(ps.getTele2());
/*      */           } 
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fillCombo() throws Exception {
/*  336 */     this.patientGender.getItems().add("ذكر");
/*  337 */     this.patientGender.getItems().add("انثي");
/*  338 */     this.patientTransName.setItems(TransferOrganization.getData());
/*  339 */     this.patientTransName.setConverter(new StringConverter<TransferOrganization>()
/*      */         {
/*      */           public String toString(TransferOrganization contract) {
/*  342 */             return contract.getName();
/*      */           }
/*      */ 
/*      */           
/*      */           public TransferOrganization fromString(String string) {
/*  347 */             return null;
/*      */           }
/*      */         });
/*  350 */     this.patientTransName.setCellFactory(cell -> new ListCell<TransferOrganization>()
/*      */         {
/*      */           GridPane gridPane;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           Label lblid;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           Label lblName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           protected void updateItem(TransferOrganization person, boolean empty) {
/*  370 */             super.updateItem(person, empty);
/*      */             
/*  372 */             if (!empty && person != null) {
/*      */ 
/*      */               
/*  375 */               this.lblid.setText("م: " + Integer.toString(person.getId()));
/*  376 */               this.lblName.setText("الاسم: " + person.getName());
/*      */ 
/*      */               
/*  379 */               setGraphic((Node)this.gridPane);
/*      */             } else {
/*      */               
/*  382 */               setGraphic(null);
/*      */             } 
/*      */           }
/*      */         });
/*  386 */     this.patientDoctor.setItems(Doctors.getData());
/*  387 */     this.patientDoctor.setConverter(new StringConverter<Doctors>()
/*      */         {
/*      */           public String toString(Doctors contract) {
/*  390 */             return contract.getName();
/*      */           }
/*      */ 
/*      */           
/*      */           public Doctors fromString(String string) {
/*  395 */             return null;
/*      */           }
/*      */         });
/*  398 */     this.patientDoctor.setCellFactory(cell -> new ListCell<Doctors>()
/*      */         {
/*      */           GridPane gridPane;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           Label lblid;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           Label lblName;
/*      */ 
/*      */ 
/*      */           
/*      */           Label lblQuali;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           protected void updateItem(Doctors person, boolean empty) {
/*  420 */             super.updateItem(person, empty);
/*      */             
/*  422 */             if (!empty && person != null) {
/*      */ 
/*      */               
/*  425 */               this.lblid.setText("م: " + Integer.toString(person.getId()));
/*  426 */               this.lblName.setText("الاسم: " + person.getName());
/*  427 */               this.lblQuali.setText("التخصص: " + person.getQualification_name());
/*      */               
/*  429 */               setGraphic((Node)this.gridPane);
/*      */             } else {
/*      */               
/*  432 */               setGraphic(null);
/*      */             } 
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   @FXML
/*      */   private void patientNew(ActionEvent event) {
/*  440 */     clear();
/*      */   }
/*      */   
/*      */   @FXML
/*      */   private void patientEdite(ActionEvent event) {
/*  445 */     if (this.patientName.getText().isEmpty() || this.patientName.getText() == null) {
/*  446 */       AlertDialogs.showError("اسم المريض لا يجب ان يكون فارغا!!");
/*      */     } else {
/*  448 */       this.progress.setVisible(true);
/*  449 */       Service<Void> service = new Service<Void>()
/*      */         {
/*      */           protected Task<Void> createTask() {
/*  452 */             return new Task<Void>()
/*      */               {
/*      */                 protected Void call() throws Exception
/*      */                 {
/*  456 */                   final CountDownLatch latch = new CountDownLatch(1);
/*  457 */                   Platform.runLater(new Runnable()
/*      */                       {
/*      */                         public void run() {
/*      */                           try {
/*      */                             try {
/*  462 */                               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
/*  463 */                               alert.setTitle("Editing Patient");
/*  464 */                               alert.setHeaderText("سيتم تعديل بيانات المريض");
/*  465 */                               alert.setContentText("هل انت متاكد؟");
/*      */                               
/*  467 */                               Optional<ButtonType> result = alert.showAndWait();
/*  468 */                               if (result.get() == ButtonType.OK) {
/*  469 */                                 if (DrugsScreenPatientController.this.patientPhoto.getText().isEmpty() || DrugsScreenPatientController.this.patientPhoto.getText() == null) {
/*  470 */                                   DrugsPatients patient = new DrugsPatients();
/*  471 */                                   patient.setId(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
/*  472 */                                   patient.setName(DrugsScreenPatientController.this.patientName.getText());
/*  473 */                                   patient.setAddress(DrugsScreenPatientController.this.patientAddress.getText());
/*  474 */                                   patient.setAge(DrugsScreenPatientController.this.patientAge.getText());
/*  475 */                                   patient.setNational_id(DrugsScreenPatientController.this.patientNational.getText());
/*  476 */                                   patient.setDoctor_id(((Doctors)DrugsScreenPatientController.this.patientDoctor.getSelectionModel().getSelectedItem()).getId());
/*  477 */                                   patient.setDiagnosis(DrugsScreenPatientController.this.patientGiagnose.getText());
/*  478 */                                   patient.setGovernment(DrugsScreenPatientController.this.patientGovernment.getText());
/*  479 */                                   DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
/*  480 */                                   patient.setDateOfBirth(((LocalDate)DrugsScreenPatientController.this.patientDateOfBirth.getValue()).format(f));
/*  481 */                                   patient.setTranportOrgId(((TransferOrganization)DrugsScreenPatientController.this.patientTransName.getSelectionModel().getSelectedItem()).getId());
/*  482 */                                   patient.setTele1(DrugsScreenPatientController.this.patientTele1.getText());
/*  483 */                                   patient.setTele2(DrugsScreenPatientController.this.patientTele2.getText());
/*  484 */                                   patient.setGender((String)DrugsScreenPatientController.this.patientGender.getSelectionModel().getSelectedItem());
/*  485 */                                   patient.Edite();
/*      */                                 } else {
/*  487 */                                   DrugsPatients patient = new DrugsPatients();
/*  488 */                                   patient.setId(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
/*  489 */                                   patient.setName(DrugsScreenPatientController.this.patientName.getText());
/*  490 */                                   patient.setAddress(DrugsScreenPatientController.this.patientAddress.getText());
/*  491 */                                   patient.setAge(DrugsScreenPatientController.this.patientAge.getText());
/*  492 */                                   patient.setNational_id(DrugsScreenPatientController.this.patientNational.getText());
/*  493 */                                   patient.setDoctor_id(((Doctors)DrugsScreenPatientController.this.patientDoctor.getSelectionModel().getSelectedItem()).getId());
/*  494 */                                   patient.setDiagnosis(DrugsScreenPatientController.this.patientGiagnose.getText());
/*  495 */                                   patient.setTele1(DrugsScreenPatientController.this.patientTele1.getText());
/*  496 */                                   patient.setTele2(DrugsScreenPatientController.this.patientTele2.getText());
/*  497 */                                   patient.setGovernment(DrugsScreenPatientController.this.patientGovernment.getText());
/*  498 */                                   DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
/*  499 */                                   patient.setDateOfBirth(((LocalDate)DrugsScreenPatientController.this.patientDateOfBirth.getValue()).format(f));
/*  500 */                                   patient.setTranportOrgId(((TransferOrganization)DrugsScreenPatientController.this.patientTransName.getSelectionModel().getSelectedItem()).getId());
/*  501 */                                   InputStream in = new FileInputStream(new File(DrugsScreenPatientController.this.patientPhoto.getText()));
/*  502 */                                   patient.setPhoto(in);
/*      */                                   
/*  504 */                                   String[] st = DrugsScreenPatientController.this.patientPhoto.getText().split(Pattern.quote("."));
/*  505 */                                   patient.setPhotoExt(st[st.length - 1]);
/*  506 */                                   patient.setGender((String)DrugsScreenPatientController.this.patientGender.getSelectionModel().getSelectedItem());
/*  507 */                                   patient.EditeWithPhoto();
/*      */                                 } 
/*      */                               }
/*  510 */                             } catch (Exception ex) {
/*  511 */                               AlertDialogs.showErrors(ex);
/*      */                             } 
/*      */                           } finally {
/*  514 */                             latch.countDown();
/*      */                           } 
/*      */                         }
/*      */                       });
/*  518 */                   latch.await();
/*      */                   
/*  520 */                   return null;
/*      */                 }
/*      */               };
/*      */           }
/*      */ 
/*      */           
/*      */           protected void succeeded() {
/*  527 */             DrugsScreenPatientController.this.clear();
/*  528 */             DrugsScreenPatientController.this.getDataToTable();
/*  529 */             DrugsScreenPatientController.this.progress.setVisible(false);
/*  530 */             super.succeeded();
/*      */           }
/*      */         };
/*  533 */       service.start();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @FXML
/*      */   private void patientAdd(ActionEvent event) {
/*  540 */     if (this.patientName.getText().isEmpty() || this.patientName.getText() == null) {
/*  541 */       AlertDialogs.showError("اسم المريض لا يجب ان يكون فارغا!!");
/*      */     } else {
/*  543 */       this.progress.setVisible(true);
/*  544 */       Service<Void> service = new Service<Void>()
/*      */         {
/*      */           protected Task<Void> createTask() {
/*  547 */             return new Task<Void>()
/*      */               {
/*      */                 protected Void call() throws Exception
/*      */                 {
/*  551 */                   final CountDownLatch latch = new CountDownLatch(1);
/*  552 */                   Platform.runLater(new Runnable()
/*      */                       {
/*      */                         public void run() {
/*      */                           try {
/*      */                             try {
/*  557 */                               if (DrugsScreenPatientController.this.patientPhoto.getText().isEmpty() || DrugsScreenPatientController.this.patientPhoto.getText() == null) {
/*  558 */                                 DrugsPatients patient = new DrugsPatients();
/*  559 */                                 patient.setId(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
/*  560 */                                 patient.setName(DrugsScreenPatientController.this.patientName.getText());
/*  561 */                                 patient.setAddress(DrugsScreenPatientController.this.patientAddress.getText());
/*  562 */                                 patient.setAge(DrugsScreenPatientController.this.patientAge.getText());
/*  563 */                                 patient.setNational_id(DrugsScreenPatientController.this.patientNational.getText());
/*  564 */                                 patient.setDoctor_id(((Doctors)DrugsScreenPatientController.this.patientDoctor.getSelectionModel().getSelectedItem()).getId());
/*  565 */                                 patient.setDiagnosis(DrugsScreenPatientController.this.patientGiagnose.getText());
/*  566 */                                 patient.setGovernment(DrugsScreenPatientController.this.patientGovernment.getText());
/*  567 */                                 DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
/*  568 */                                 patient.setDateOfBirth(((LocalDate)DrugsScreenPatientController.this.patientDateOfBirth.getValue()).format(f));
/*  569 */                                 patient.setTranportOrgId(((TransferOrganization)DrugsScreenPatientController.this.patientTransName.getSelectionModel().getSelectedItem()).getId());
/*  570 */                                 patient.setTele1(DrugsScreenPatientController.this.patientTele1.getText());
/*  571 */                                 patient.setTele2(DrugsScreenPatientController.this.patientTele2.getText());
/*  572 */                                 patient.setGender((String)DrugsScreenPatientController.this.patientGender.getSelectionModel().getSelectedItem());
/*  573 */                                 patient.Add();
/*      */                               } else {
/*  575 */                                 DrugsPatients patient = new DrugsPatients();
/*  576 */                                 patient.setId(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
/*  577 */                                 patient.setName(DrugsScreenPatientController.this.patientName.getText());
/*  578 */                                 patient.setAddress(DrugsScreenPatientController.this.patientAddress.getText());
/*  579 */                                 patient.setAge(DrugsScreenPatientController.this.patientAge.getText());
/*  580 */                                 patient.setNational_id(DrugsScreenPatientController.this.patientNational.getText());
/*  581 */                                 patient.setDoctor_id(((Doctors)DrugsScreenPatientController.this.patientDoctor.getSelectionModel().getSelectedItem()).getId());
/*  582 */                                 patient.setDiagnosis(DrugsScreenPatientController.this.patientGiagnose.getText());
/*  583 */                                 patient.setTele1(DrugsScreenPatientController.this.patientTele1.getText());
/*  584 */                                 patient.setTele2(DrugsScreenPatientController.this.patientTele2.getText());
/*  585 */                                 patient.setGovernment(DrugsScreenPatientController.this.patientGovernment.getText());
/*  586 */                                 DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
/*  587 */                                 patient.setDateOfBirth(((LocalDate)DrugsScreenPatientController.this.patientDateOfBirth.getValue()).format(f));
/*  588 */                                 patient.setTranportOrgId(((TransferOrganization)DrugsScreenPatientController.this.patientTransName.getSelectionModel().getSelectedItem()).getId());
/*  589 */                                 InputStream in = new FileInputStream(new File(DrugsScreenPatientController.this.patientPhoto.getText()));
/*  590 */                                 patient.setPhoto(in);
/*      */                                 
/*  592 */                                 String[] st = DrugsScreenPatientController.this.patientPhoto.getText().split(Pattern.quote("."));
/*  593 */                                 patient.setPhotoExt(st[st.length - 1]);
/*  594 */                                 patient.setGender((String)DrugsScreenPatientController.this.patientGender.getSelectionModel().getSelectedItem());
/*  595 */                                 patient.AddWithPhoto();
/*      */                               } 
/*  597 */                               DrugsAccounts da = new DrugsAccounts();
/*  598 */                               da.setId(Integer.parseInt(DrugsAccounts.getAutoNum()));
/*  599 */                               da.setPaitent_id(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
/*  600 */                               da.setTotal_paied("0");
/*  601 */                               da.setTotal_spended("0");
/*  602 */                               da.setRemaining("0");
/*  603 */                               da.Add();
/*  604 */                             } catch (Exception ex) {
/*  605 */                               AlertDialogs.showErrors(ex);
/*      */                             } 
/*      */                           } finally {
/*  608 */                             latch.countDown();
/*      */                           } 
/*      */                         }
/*      */                       });
/*  612 */                   latch.await();
/*      */                   
/*  614 */                   return null;
/*      */                 }
/*      */               };
/*      */           }
/*      */ 
/*      */           
/*      */           protected void succeeded() {
/*  621 */             DrugsScreenPatientController.this.clear();
/*  622 */             DrugsScreenPatientController.this.getDataToTable();
/*  623 */             DrugsScreenPatientController.this.progress.setVisible(false);
/*  624 */             super.succeeded();
/*      */           }
/*      */         };
/*  627 */       service.start();
/*      */     } 
/*      */   }
/*      */   
/*      */   @FXML
/*      */   private void patientDelete(ActionEvent event) {
/*  633 */     this.progress.setVisible(true);
/*  634 */     Service<Void> service = new Service<Void>()
/*      */       {
/*      */         protected Task<Void> createTask() {
/*  637 */           return new Task<Void>()
/*      */             {
/*      */               protected Void call() throws Exception
/*      */               {
/*  641 */                 final CountDownLatch latch = new CountDownLatch(1);
/*  642 */                 Platform.runLater(new Runnable()
/*      */                     {
/*      */                       public void run() {
/*      */                         try {
/*      */                           try {
/*  647 */                             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
/*  648 */                             alert.setTitle("Deleting Patient");
/*  649 */                             alert.setHeaderText("سيتم حذف المريض");
/*  650 */                             alert.setContentText("هل انت متاكد؟");
/*      */                             
/*  652 */                             Optional<ButtonType> result = alert.showAndWait();
/*  653 */                             if (result.get() == ButtonType.OK) {
/*  654 */                               DrugsPatients patient = new DrugsPatients();
/*  655 */                               patient.setId(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
/*  656 */                               patient.Delete();
/*  657 */                               DrugsAccounts da = new DrugsAccounts();
/*  658 */                               da.setPaitent_id(Integer.parseInt(DrugsScreenPatientController.this.patientId.getText()));
/*  659 */                               da.Delete();
/*      */                             }
/*      */                           
/*  662 */                           } catch (Exception ex) {
/*  663 */                             AlertDialogs.showErrors(ex);
/*      */                           } 
/*      */                         } finally {
/*  666 */                           latch.countDown();
/*      */                         } 
/*      */                       }
/*      */                     });
/*  670 */                 latch.await();
/*      */                 
/*  672 */                 return null;
/*      */               }
/*      */             };
/*      */         }
/*      */ 
/*      */         
/*      */         protected void succeeded() {
/*  679 */           DrugsScreenPatientController.this.clear();
/*  680 */           DrugsScreenPatientController.this.getDataToTable();
/*  681 */           DrugsScreenPatientController.this.progress.setVisible(false);
/*  682 */           super.succeeded();
/*      */         }
/*      */       };
/*  685 */     service.start();
/*      */   }
/*      */ 
/*      */   
/*      */   private void setAutoNumber() {
/*      */     try {
/*  691 */       this.patientId.setText(DrugsPatients.getAutoNum());
/*  692 */     } catch (Exception ex) {
/*  693 */       AlertDialogs.showErrors(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void clear() {
/*  698 */     setAutoNumber();
/*  699 */     this.patientName.setText("");
/*  700 */     this.patientAddress.setText("");
/*  701 */     this.patientAge.setText("");
/*  702 */     this.patientNational.setText("");
/*  703 */     this.patientGiagnose.setText("");
/*  704 */     this.patientPhoto.setText("");
/*  705 */     this.patientDoctor.getSelectionModel().clearSelection();
/*  706 */     this.patientDateOfBirth.setValue(null);
/*  707 */     this.patientGovernment.setText("");
/*  708 */     this.patientGender.getSelectionModel().clearSelection();
/*  709 */     this.patientTransName.getSelectionModel().clearSelection();
/*  710 */     this.patientTele1.setText("");
/*  711 */     this.patientTele2.setText("");
/*  712 */     this.patientAdd.setDisable(false);
/*  713 */     this.patientEdite.setDisable(true);
/*  714 */     this.patientDelete.setDisable(true);
/*  715 */     this.patientNew.setDisable(true);
/*      */   }
/*      */   
/*      */   private void setTableColumns() {
/*  719 */     this.patientTabId.setCellValueFactory((Callback)new PropertyValueFactory("id"));
/*  720 */     this.patientTabName.setCellValueFactory((Callback)new PropertyValueFactory("name"));
/*  721 */     this.patientTabDiagnoses.setCellValueFactory((Callback)new PropertyValueFactory("diagnosis"));
/*  722 */     this.patientTabDoctor.setCellValueFactory((Callback)new PropertyValueFactory("doctor_name"));
/*  723 */     this.patientTabTele1.setCellValueFactory((Callback)new PropertyValueFactory("tele1"));
/*  724 */     this.patientTabTele2.setCellValueFactory((Callback)new PropertyValueFactory("tele2"));
/*      */     
/*  726 */     this.escortTabNational.setCellValueFactory((Callback)new PropertyValueFactory("id"));
/*  727 */     this.escortTabName.setCellValueFactory((Callback)new PropertyValueFactory("name"));
/*  728 */     this.escortTabId.setCellValueFactory((Callback)new PropertyValueFactory("id"));
/*  729 */     this.escortTabTele2.setCellValueFactory((Callback)new PropertyValueFactory("tele2"));
/*  730 */     this.escortTabTele1.setCellValueFactory((Callback)new PropertyValueFactory("tele1"));
/*  731 */     this.escortTabAddress.setCellValueFactory((Callback)new PropertyValueFactory("Address"));
/*  732 */     this.escortTabRelation.setCellValueFactory((Callback)new PropertyValueFactory("relation"));
/*      */   }
/*      */ 
/*      */   
/*      */   private void getDataToTable() {
/*  737 */     this.progress.setVisible(true);
/*  738 */     Service<Void> service = new Service<Void>()
/*      */       {
/*      */         protected Task<Void> createTask() {
/*  741 */           return new Task<Void>()
/*      */             {
/*      */               protected Void call() throws Exception
/*      */               {
/*  745 */                 final CountDownLatch latch = new CountDownLatch(1);
/*  746 */                 Platform.runLater(new Runnable()
/*      */                     {
/*      */                       public void run()
/*      */                       {
/*      */                         try {
/*      */                           try {
/*  752 */                             DrugsScreenPatientController.this.patientTable.setItems(DrugsPatients.getData());
/*      */                           }
/*  754 */                           catch (Exception ex) {
/*  755 */                             AlertDialogs.showErrors(ex);
/*      */                           } 
/*      */                         } finally {
/*  758 */                           latch.countDown();
/*      */                         } 
/*      */                       }
/*      */                     });
/*  762 */                 latch.await();
/*      */                 
/*  764 */                 return null;
/*      */               }
/*      */             };
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         protected void succeeded() {
/*  772 */           DrugsScreenPatientController.this.items = DrugsScreenPatientController.this.patientTable.getItems();
/*  773 */           DrugsScreenPatientController.this.progress.setVisible(false);
/*  774 */           super.succeeded();
/*      */         }
/*      */       };
/*  777 */     service.start();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @FXML
/*      */   private void search(KeyEvent event) {
/*  784 */     FilteredList<DrugsPatients> filteredData = new FilteredList(this.items, p -> true);
/*      */     
/*  786 */     filteredData.setPredicate(pa -> {
/*      */           if (this.search.getText() == null || this.search.getText().isEmpty()) {
/*      */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           String lowerCaseFilter = this.search.getText().toLowerCase();
/*      */           
/*  794 */           return (pa.getName().contains(lowerCaseFilter) || pa.getAddress().contains(lowerCaseFilter) || pa.getAge().contains(lowerCaseFilter) || pa.getNational_id().contains(lowerCaseFilter) || pa.getTele1().contains(lowerCaseFilter) || pa.getTele2().contains(lowerCaseFilter));
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  807 */     SortedList<DrugsPatients> sortedData = new SortedList((ObservableList)filteredData);
/*  808 */     sortedData.comparatorProperty().bind((ObservableValue)this.patientTable.comparatorProperty());
/*  809 */     this.patientTable.setItems((ObservableList)sortedData);
/*      */   }
/*      */   
/*      */   @FXML
/*      */   private void pickPhoto(MouseEvent event) {
/*  814 */     FileChooser fil_chooser = new FileChooser();
/*  815 */     Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
/*  816 */     File file = fil_chooser.showOpenDialog((Window)st);
/*      */     
/*  818 */     if (file != null) {
/*  819 */       this.patientPhoto.setText(file.getAbsolutePath());
/*      */     }
/*      */   }
/*      */   
/*      */   @FXML
/*      */   private void showPicture(MouseEvent event) {
/*  825 */     this.progress.setVisible(true);
/*  826 */     Service<Void> service = new Service<Void>()
/*      */       {
/*      */         protected Task<Void> createTask() {
/*  829 */           return new Task<Void>()
/*      */             {
/*      */               protected Void call() throws Exception {
/*  832 */                 final CountDownLatch latch = new CountDownLatch(1);
/*  833 */                 Platform.runLater(new Runnable()
/*      */                     {
/*      */                       public void run()
/*      */                       {
/*      */                         try {
/*      */                           try {
/*      */                             try {
/*  840 */                               DrugsPatients tab = (DrugsPatients)DrugsScreenPatientController.this.patientTable.getSelectionModel().getSelectedItem();
/*  841 */                               DrugsPatients cont = new DrugsPatients();
/*  842 */                               cont.setId(tab.getId());
/*  843 */                               cont.setName(tab.getName());
/*  844 */                               cont.getNationalIdPhoto();
/*  845 */                             } catch (Exception ex) {
/*  846 */                               AlertDialogs.showErrors(ex);
/*      */                             }
/*      */                           
/*  849 */                           } catch (Exception ex) {
/*  850 */                             AlertDialogs.showErrors(ex);
/*      */                           } 
/*      */                         } finally {
/*  853 */                           latch.countDown();
/*      */                         } 
/*      */                       }
/*      */                     });
/*  857 */                 latch.await();
/*      */                 
/*  859 */                 return null;
/*      */               }
/*      */             };
/*      */         }
/*      */ 
/*      */         
/*      */         protected void succeeded() {
/*  866 */           DrugsScreenPatientController.this.progress.setVisible(false);
/*  867 */           super.succeeded();
/*      */         }
/*      */       };
/*  870 */     service.start();
/*      */   }
/*      */   
/*      */   @FXML
/*      */   private void escortPhotoPick(MouseEvent event) {
/*  875 */     FileChooser fil_chooser = new FileChooser();
/*  876 */     Stage st = (Stage)((Node)event.getSource()).getScene().getWindow();
/*  877 */     File file = fil_chooser.showOpenDialog((Window)st);
/*      */     
/*  879 */     if (file != null) {
/*  880 */       this.escortPhoto.setText(file.getAbsolutePath());
/*      */     }
/*      */   }
/*      */   
/*      */   @FXML
/*      */   private void escortNew(ActionEvent event) {
/*  886 */     clearEscort();
/*      */   }
/*      */   
/*      */   @FXML
/*      */   private void escortEdite(ActionEvent event) {
/*  891 */     if (this.escortName.getText().isEmpty() || this.escortName.getText() == null) {
/*  892 */       AlertDialogs.showError("اسم المرافق لا يجب ان يكون فارغا!!");
/*      */     } else {
/*  894 */       this.escortProgress.setVisible(true);
/*  895 */       Service<Void> service = new Service<Void>()
/*      */         {
/*      */           protected Task<Void> createTask() {
/*  898 */             return new Task<Void>()
/*      */               {
/*      */                 protected Void call() throws Exception
/*      */                 {
/*  902 */                   final CountDownLatch latch = new CountDownLatch(1);
/*  903 */                   Platform.runLater(new Runnable()
/*      */                       {
/*      */                         public void run() {
/*      */                           try {
/*      */                             try {
/*  908 */                               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
/*  909 */                               alert.setTitle("Editing Patient");
/*  910 */                               alert.setHeaderText("سيتم تعديل بيانات المرافق");
/*  911 */                               alert.setContentText("هل انت متاكد؟");
/*      */                               
/*  913 */                               Optional<ButtonType> result = alert.showAndWait();
/*  914 */                               if (result.get() == ButtonType.OK) {
/*  915 */                                 if (DrugsScreenPatientController.this.escortPhoto.getText().isEmpty() || DrugsScreenPatientController.this.escortPhoto.getText() == null) {
/*  916 */                                   DrugsPatientsEscort patient = new DrugsPatientsEscort();
/*  917 */                                   patient.setId(Integer.parseInt(DrugsScreenPatientController.this.escortId.getText()));
/*  918 */                                   patient.setPatientId(((DrugsPatients)DrugsScreenPatientController.this.patientTable.getSelectionModel().getSelectedItem()).getId());
/*  919 */                                   patient.setName(DrugsScreenPatientController.this.escortName.getText());
/*  920 */                                   patient.setRelation(DrugsScreenPatientController.this.escortRelation.getText());
/*  921 */                                   patient.setAddress(DrugsScreenPatientController.this.escortAddress.getText());
/*  922 */                                   patient.setNationaId(DrugsScreenPatientController.this.escortNational.getText());
/*  923 */                                   patient.setTele1(DrugsScreenPatientController.this.escortTele1.getText());
/*  924 */                                   patient.setTele2(DrugsScreenPatientController.this.escortTele2.getText());
/*  925 */                                   patient.EditeWithoutPhoto();
/*      */                                 } else {
/*  927 */                                   DrugsPatientsEscort patient = new DrugsPatientsEscort();
/*  928 */                                   patient.setId(Integer.parseInt(DrugsScreenPatientController.this.escortId.getText()));
/*  929 */                                   patient.setPatientId(((DrugsPatients)DrugsScreenPatientController.this.patientTable.getSelectionModel().getSelectedItem()).getId());
/*  930 */                                   patient.setName(DrugsScreenPatientController.this.escortName.getText());
/*  931 */                                   patient.setRelation(DrugsScreenPatientController.this.escortRelation.getText());
/*  932 */                                   patient.setAddress(DrugsScreenPatientController.this.escortAddress.getText());
/*  933 */                                   patient.setNationaId(DrugsScreenPatientController.this.escortNational.getText());
/*  934 */                                   patient.setTele1(DrugsScreenPatientController.this.escortTele1.getText());
/*  935 */                                   patient.setTele2(DrugsScreenPatientController.this.escortTele2.getText());
/*      */                                   
/*  937 */                                   InputStream in = new FileInputStream(new File(DrugsScreenPatientController.this.escortPhoto.getText()));
/*  938 */                                   patient.setPhoto(in);
/*      */                                   
/*  940 */                                   String[] st = DrugsScreenPatientController.this.escortPhoto.getText().split(Pattern.quote("."));
/*  941 */                                   patient.setPhotoExt(st[st.length - 1]);
/*      */                                   
/*  943 */                                   patient.Edite();
/*      */                                 } 
/*      */                               }
/*  946 */                             } catch (Exception ex) {
/*  947 */                               AlertDialogs.showErrors(ex);
/*      */                             } 
/*      */                           } finally {
/*  950 */                             latch.countDown();
/*      */                           } 
/*      */                         }
/*      */                       });
/*  954 */                   latch.await();
/*      */                   
/*  956 */                   return null;
/*      */                 }
/*      */               };
/*      */           }
/*      */ 
/*      */           
/*      */           protected void succeeded() {
/*  963 */             DrugsScreenPatientController.this.clearEscort();
/*  964 */             DrugsScreenPatientController.this.clear();
/*  965 */             DrugsScreenPatientController.this.getDataToTable();
/*  966 */             DrugsScreenPatientController.this.escortProgress.setVisible(false);
/*  967 */             super.succeeded();
/*      */           }
/*      */         };
/*  970 */       service.start();
/*      */     } 
/*      */   }
/*      */   
/*      */   @FXML
/*      */   private void escortDelete(ActionEvent event) {
/*  976 */     if (this.escortName.getText().isEmpty() || this.escortName.getText() == null) {
/*  977 */       AlertDialogs.showError("اسم المرافق لا يجب ان يكون فارغا!!");
/*      */     } else {
/*  979 */       this.escortProgress.setVisible(true);
/*  980 */       Service<Void> service = new Service<Void>()
/*      */         {
/*      */           protected Task<Void> createTask() {
/*  983 */             return new Task<Void>()
/*      */               {
/*      */                 protected Void call() throws Exception
/*      */                 {
/*  987 */                   final CountDownLatch latch = new CountDownLatch(1);
/*  988 */                   Platform.runLater(new Runnable()
/*      */                       {
/*      */                         public void run() {
/*      */                           try {
/*      */                             try {
/*  993 */                               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
/*  994 */                               alert.setTitle("Deleting Patient");
/*  995 */                               alert.setHeaderText("سيتم حذف بيانات المرافق");
/*  996 */                               alert.setContentText("هل انت متاكد؟");
/*      */                               
/*  998 */                               Optional<ButtonType> result = alert.showAndWait();
/*  999 */                               if (result.get() == ButtonType.OK)
/*      */                               {
/* 1001 */                                 DrugsPatientsEscort patient = new DrugsPatientsEscort();
/* 1002 */                                 patient.setId(Integer.parseInt(DrugsScreenPatientController.this.escortId.getText()));
/*      */                                 
/* 1004 */                                 patient.Delete();
/*      */                               }
/*      */                             
/* 1007 */                             } catch (Exception ex) {
/* 1008 */                               AlertDialogs.showErrors(ex);
/*      */                             } 
/*      */                           } finally {
/* 1011 */                             latch.countDown();
/*      */                           } 
/*      */                         }
/*      */                       });
/* 1015 */                   latch.await();
/*      */                   
/* 1017 */                   return null;
/*      */                 }
/*      */               };
/*      */           }
/*      */ 
/*      */           
/*      */           protected void succeeded() {
/* 1024 */             DrugsScreenPatientController.this.clearEscort();
/* 1025 */             DrugsScreenPatientController.this.clear();
/* 1026 */             DrugsScreenPatientController.this.getDataToTable();
/* 1027 */             DrugsScreenPatientController.this.escortProgress.setVisible(false);
/* 1028 */             super.succeeded();
/*      */           }
/*      */         };
/* 1031 */       service.start();
/*      */     } 
/*      */   }
/*      */   
/*      */   @FXML
/*      */   private void escortAdd(ActionEvent event) {
/* 1037 */     if (this.escortName.getText().isEmpty() || this.escortName.getText() == null) {
/* 1038 */       AlertDialogs.showError("اسم المرافق لا يجب ان يكون فارغا!!");
/*      */     } else {
/* 1040 */       this.escortProgress.setVisible(true);
/* 1041 */       Service<Void> service = new Service<Void>()
/*      */         {
/*      */           protected Task<Void> createTask() {
/* 1044 */             return new Task<Void>()
/*      */               {
/*      */                 protected Void call() throws Exception
/*      */                 {
/* 1048 */                   final CountDownLatch latch = new CountDownLatch(1);
/* 1049 */                   Platform.runLater(new Runnable()
/*      */                       {
/*      */                         public void run()
/*      */                         {
/*      */                           try {
/*      */                             try {
/* 1055 */                               if (DrugsScreenPatientController.this.escortPhoto.getText().isEmpty() || DrugsScreenPatientController.this.escortPhoto.getText() == null) {
/* 1056 */                                 DrugsPatientsEscort patient = new DrugsPatientsEscort();
/* 1057 */                                 patient.setId(Integer.parseInt(DrugsScreenPatientController.this.escortId.getText()));
/* 1058 */                                 patient.setPatientId(((DrugsPatients)DrugsScreenPatientController.this.patientTable.getSelectionModel().getSelectedItem()).getId());
/* 1059 */                                 patient.setName(DrugsScreenPatientController.this.escortName.getText());
/* 1060 */                                 patient.setRelation(DrugsScreenPatientController.this.escortRelation.getText());
/* 1061 */                                 patient.setAddress(DrugsScreenPatientController.this.escortAddress.getText());
/* 1062 */                                 patient.setNationaId(DrugsScreenPatientController.this.escortNational.getText());
/* 1063 */                                 patient.setTele1(DrugsScreenPatientController.this.escortTele1.getText());
/* 1064 */                                 patient.setTele2(DrugsScreenPatientController.this.escortTele2.getText());
/* 1065 */                                 patient.AddWithoutPhoto();
/*      */                               } else {
/* 1067 */                                 DrugsPatientsEscort patient = new DrugsPatientsEscort();
/* 1068 */                                 patient.setId(Integer.parseInt(DrugsScreenPatientController.this.escortId.getText()));
/* 1069 */                                 patient.setPatientId(((DrugsPatients)DrugsScreenPatientController.this.patientTable.getSelectionModel().getSelectedItem()).getId());
/* 1070 */                                 patient.setName(DrugsScreenPatientController.this.escortName.getText());
/* 1071 */                                 patient.setRelation(DrugsScreenPatientController.this.escortRelation.getText());
/* 1072 */                                 patient.setAddress(DrugsScreenPatientController.this.escortAddress.getText());
/* 1073 */                                 patient.setNationaId(DrugsScreenPatientController.this.escortNational.getText());
/* 1074 */                                 patient.setTele1(DrugsScreenPatientController.this.escortTele1.getText());
/* 1075 */                                 patient.setTele2(DrugsScreenPatientController.this.escortTele2.getText());
/*      */                                 
/* 1077 */                                 InputStream in = new FileInputStream(new File(DrugsScreenPatientController.this.escortPhoto.getText()));
/* 1078 */                                 patient.setPhoto(in);
/*      */                                 
/* 1080 */                                 String[] st = DrugsScreenPatientController.this.escortPhoto.getText().split(Pattern.quote("."));
/* 1081 */                                 patient.setPhotoExt(st[st.length - 1]);
/*      */                                 
/* 1083 */                                 patient.Add();
/*      */                               }
/*      */                             
/* 1086 */                             } catch (Exception ex) {
/* 1087 */                               AlertDialogs.showErrors(ex);
/*      */                             } 
/*      */                           } finally {
/* 1090 */                             latch.countDown();
/*      */                           } 
/*      */                         }
/*      */                       });
/* 1094 */                   latch.await();
/*      */                   
/* 1096 */                   return null;
/*      */                 }
/*      */               };
/*      */           }
/*      */ 
/*      */           
/*      */           protected void succeeded() {
/* 1103 */             DrugsScreenPatientController.this.clearEscort();
/* 1104 */             DrugsScreenPatientController.this.clear();
/* 1105 */             DrugsScreenPatientController.this.getDataToTable();
/* 1106 */             DrugsScreenPatientController.this.escortProgress.setVisible(false);
/* 1107 */             super.succeeded();
/*      */           }
/*      */         };
/* 1110 */       service.start();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void clearEscort() {
/*      */     try {
/* 1116 */       if (this.patientTable.getSelectionModel().getSelectedIndex() == -1) {
/* 1117 */         this.escortTable.setItems(null);
/*      */       } else {
/* 1119 */         this.escortTable.setItems(DrugsPatientsEscort.getData(Integer.toString(((DrugsPatients)this.patientTable.getSelectionModel().getSelectedItem()).getId())));
/*      */       } 
/*      */       
/* 1122 */       setEscortAutoNum();
/* 1123 */       this.escortProgress.setVisible(false);
/* 1124 */       this.escortAdd.setDisable(false);
/* 1125 */       this.escortEdite.setDisable(true);
/* 1126 */       this.escortDelete.setDisable(true);
/* 1127 */       this.escortNew.setDisable(true);
/*      */       
/* 1129 */       this.escortName.setText("");
/* 1130 */       this.escortRelation.setText("");
/* 1131 */       this.escortAddress.setText("");
/* 1132 */       this.escortNational.setText("");
/* 1133 */       this.escortPhoto.setText("");
/* 1134 */       this.escortTele1.setText("");
/* 1135 */       this.escortTele2.setText("");
/* 1136 */     } catch (Exception ex) {
/* 1137 */       AlertDialogs.showErrors(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setEscortAutoNum() throws Exception {
/* 1142 */     this.escortId.setText(DrugsPatientsEscort.getAutoNum());
/*      */   }
/*      */   
/*      */   @FXML
/*      */   private void showEscortPicture(MouseEvent event) {
/* 1147 */     this.progress.setVisible(true);
/* 1148 */     Service<Void> service = new Service<Void>()
/*      */       {
/*      */         protected Task<Void> createTask() {
/* 1151 */           return new Task<Void>()
/*      */             {
/*      */               protected Void call() throws Exception {
/* 1154 */                 final CountDownLatch latch = new CountDownLatch(1);
/* 1155 */                 Platform.runLater(new Runnable()
/*      */                     {
/*      */                       public void run()
/*      */                       {
/*      */                         try {
/*      */                           try {
/*      */                             try {
/* 1162 */                               DrugsPatientsEscort tab = (DrugsPatientsEscort)DrugsScreenPatientController.this.escortTable.getSelectionModel().getSelectedItem();
/* 1163 */                               DrugsPatientsEscort cont = new DrugsPatientsEscort();
/* 1164 */                               cont.setId(tab.getId());
/* 1165 */                               cont.setName(tab.getName());
/* 1166 */                               cont.getNationalIdPhoto();
/* 1167 */                             } catch (Exception ex) {
/* 1168 */                               AlertDialogs.showErrors(ex);
/*      */                             }
/*      */                           
/* 1171 */                           } catch (Exception ex) {
/* 1172 */                             AlertDialogs.showErrors(ex);
/*      */                           } 
/*      */                         } finally {
/* 1175 */                           latch.countDown();
/*      */                         } 
/*      */                       }
/*      */                     });
/* 1179 */                 latch.await();
/*      */                 
/* 1181 */                 return null;
/*      */               }
/*      */             };
/*      */         }
/*      */ 
/*      */         
/*      */         protected void succeeded() {
/* 1188 */           DrugsScreenPatientController.this.progress.setVisible(false);
/* 1189 */           super.succeeded();
/*      */         }
/*      */       };
/* 1192 */     service.start();
/*      */   }
/*      */ 
/*      */   
/*      */   @FXML
/*      */   private void searchEscort(KeyEvent event) {
/* 1198 */     FilteredList<DrugsPatientsEscort> filteredData = new FilteredList(this.Escortitems, p -> true);
/*      */     
/* 1200 */     filteredData.setPredicate(pa -> {
/*      */           if (this.searchEscort.getText() == null || this.searchEscort.getText().isEmpty()) {
/*      */             return true;
/*      */           }
/*      */ 
/*      */           
/*      */           String lowerCaseFilter = this.searchEscort.getText().toLowerCase();
/*      */           
/* 1208 */           return (pa.getName().contains(lowerCaseFilter) || pa.getAddress().contains(lowerCaseFilter) || pa.getTele1().contains(lowerCaseFilter) || pa.getTele2().contains(lowerCaseFilter));
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1219 */     SortedList<DrugsPatientsEscort> sortedData = new SortedList((ObservableList)filteredData);
/* 1220 */     sortedData.comparatorProperty().bind((ObservableValue)this.escortTable.comparatorProperty());
/* 1221 */     this.escortTable.setItems((ObservableList)sortedData);
/*      */   }
/*      */   
/*      */   @FXML
/*      */   private void addTranspering(MouseEvent event) {
/* 1226 */     TextInputDialog dialog = new TextInputDialog("");
/* 1227 */     dialog.setTitle("Add Org Name");
/* 1228 */     dialog.setHeaderText("اضافة اسم المؤسسة");
/*      */     
/* 1230 */     Optional<String> result = dialog.showAndWait();
/* 1231 */     if (result.isPresent())
/* 1232 */       if (((String)result.get()).isEmpty() || result.get() == null) {
/* 1233 */         AlertDialogs.showError("خطا!! يرجي ادخال اسم المؤسسة");
/*      */       } else {
/* 1235 */         final String results = result.get();
/*      */         try {
/* 1237 */           Service service = new Service()
/*      */             {
/*      */               protected Task createTask() {
/* 1240 */                 return new Task()
/*      */                   {
/*      */                     protected Object call() throws Exception {
/* 1243 */                       CountDownLatch latch = new CountDownLatch(1);
/* 1244 */                       Platform.runLater(() -> {
/*      */                             try {
/*      */                               TransferOrganization.Add(results);
/* 1247 */                             } catch (Exception ex) {
/*      */                               AlertDialogs.showErrors(ex);
/*      */                             } finally {
/*      */                               latch.countDown();
/*      */                             } 
/*      */                           });
/* 1253 */                       latch.await();
/*      */                       
/* 1255 */                       return null;
/*      */                     }
/*      */ 
/*      */                     
/*      */                     protected void succeeded() {
/*      */                       try {
/* 1261 */                         DrugsScreenPatientController.this.fillCombo();
/* 1262 */                       } catch (Exception ex) {
/* 1263 */                         AlertDialogs.showErrors(ex);
/*      */                       } 
/*      */                     }
/*      */                   };
/*      */               }
/*      */             };
/* 1269 */           service.start();
/*      */         }
/* 1271 */         catch (Exception ex) {
/* 1272 */           AlertDialogs.showErrors(ex);
/*      */         } 
/*      */       }  
/*      */   }
/*      */ }


/* Location:              C:\xampp\htdocs\github\ElBarbary-Hospital\dist\ElBarbary Hospital.jar!\screens\drugs\DrugsScreenPatientController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */