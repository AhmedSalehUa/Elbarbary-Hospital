/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screens.hr;

import assets.animation.ZoomInLeft;
import assets.animation.ZoomInRight;
import assets.classes.AlertDialogs;
import assets.classes.statics;
import static assets.classes.statics.uploadUsers;
import elbarbary.hospital.ElBarbaryHospital;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.ImageView;
import org.apache.batik.svggen.font.table.Device;
import org.controlsfx.dialog.ProgressDialog;
import screens.hr.assets.Devices;
import screens.hr.assets.Employee;

/**
 * FXML Controller class
 *
 * @author AHMED
 */
public class HrScreenMainUploadDataController implements Initializable {

    @FXML
    private Button downloadAttendane;
    @FXML
    private Button downloadEmployee;
    @FXML
    private Button uploadEmployee;
    Preferences prefs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(ElBarbaryHospital.class);
        new ZoomInRight(uploadEmployee).play();
        new ZoomInRight(downloadEmployee).play();

        new ZoomInLeft(downloadAttendane).play();
    }

    @FXML
    private void downloadAttendane(ActionEvent event) {
        try {
            ObservableList<Devices> data = Devices.getData();
            List<String> choices = new ArrayList<>();
            for (Devices a : data) {
                choices.add(a.getName());
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(data.get(0).getName(), choices);
            dialog.setTitle("Choice Device");
            dialog.setHeaderText("اختار الجهاز");
            dialog.setContentText("اختار اسم الجهاز");
            dialog.setGraphic(new ImageView(this.getClass().getResource("/assets/icons/icons8_hdd_80px.png").toString()));
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                db.get.getReportCon().createStatement().execute("REPLACE INTO `att_target_devices`(`device_id`) select `att_machines`.`id` from `att_machines` where `att_machines`.`name`='" + result.get() + "'");
                AlertDialogs.showmessage("سيتم تنزيل البيانات قد يتسبب فى توقف مؤقت للبرنامج");
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
                                                Process p = Runtime.getRuntime().exec(prefs.get(statics.PYTHON_PATH, statics.PYTHON_PATH_DEFAULT) + statics.downloadAttendance);
                                                p.waitFor();
                                                BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                                BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                                                String line;
                                                while ((line = bri.readLine()) != null) {
                                                    System.out.println(line);
                                                }
                                                bri.close();
                                                while ((line = bre.readLine()) != null) {
                                                    System.out.println(line);

                                                }
                                                bre.close();
                                                p.waitFor();
                                                System.out.println("Done.");

                                                p.destroy();

                                            } catch (Exception ex) {
                                                AlertDialogs.showErrors(ex);
                                            } finally {
                                                latch.countDown();
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
                        AlertDialogs.showmessage("تم");
                        super.succeeded();
                    }
                };
                service.start();

            }
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }

    }

    @FXML
    private void downloadEmployee(ActionEvent event) {
        try {

            ObservableList<Devices> data = Devices.getData();
            List<String> choices = new ArrayList<>();
            for (Devices a : data) {
                choices.add(a.getName());
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(data.get(0).getName(), choices);
            dialog.setTitle("Choice Device");
            dialog.setHeaderText("اختار الجهاز");
            dialog.setContentText("اختار اسم الجهاز");
            dialog.setGraphic(new ImageView(this.getClass().getResource("/assets/icons/icons8_hdd_80px.png").toString()));
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                db.get.getReportCon().createStatement().execute("REPLACE INTO `att_target_devices`(`device_id`) select `att_machines`.`id` from `att_machines` where `att_machines`.`name`='" + result.get() + "'");
                AlertDialogs.showmessage("سيتم تنزيل البيانات قد يتسبب فى توقف مؤقت للبرنامج");
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
                                                Process p = Runtime.getRuntime().exec(prefs.get(statics.PYTHON_PATH, statics.PYTHON_PATH_DEFAULT) + statics.downloadUsers);
                                                p.waitFor();
                                                BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                                BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                                                String line;
                                                while ((line = bri.readLine()) != null) {
                                                    System.out.println(line);
                                                }
                                                bri.close();
                                                while ((line = bre.readLine()) != null) {
                                                    System.out.println(line);

                                                }
                                                bre.close();
                                                p.waitFor();
                                                System.out.println("Done.");

                                                p.destroy();

                                            } catch (Exception ex) {
                                                AlertDialogs.showErrors(ex);
                                            } finally {
                                                latch.countDown();
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
                        try {
                            if (Employee.syncData()) {
                                AlertDialogs.showmessage("تم");
                            }
                        } catch (Exception ex) {
                            AlertDialogs.showErrors(ex);
                        }

                        super.succeeded();
                    }
                };
                service.start();

            }
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }

    }

    @FXML
    private void uploadEmployee(ActionEvent event) {
        try {
            ObservableList<Devices> data = Devices.getData();
            List<String> choices = new ArrayList<>();
            for (Devices a : data) {
                choices.add(a.getName());
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(data.get(0).getName(), choices);
            dialog.setTitle("Choice Device");
            dialog.setHeaderText("اختار الجهاز");
            dialog.setContentText("اختار اسم الجهاز");
            dialog.setGraphic(new ImageView(this.getClass().getResource("/assets/icons/icons8_hdd_80px.png").toString()));
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                if (Employee.syncUpload()) {
                    db.get.getReportCon().createStatement().execute("REPLACE INTO `att_target_devices`(`device_id`) select `att_machines`.`id` from `att_machines` where `att_machines`.`name`='" + result.get() + "'");
                    AlertDialogs.showmessage("سيتم رفع البيانات قد يتسبب فى توقف مؤقت للبرنامج");
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
                                                    Process p = Runtime.getRuntime().exec(prefs.get(statics.PYTHON_PATH, statics.PYTHON_PATH_DEFAULT) + statics.uploadUsers);
                                                    p.waitFor();
                                                    BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                                    BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                                                    String line;
                                                    while ((line = bri.readLine()) != null) {
                                                        System.out.println(line);
                                                    }
                                                    bri.close();
                                                    while ((line = bre.readLine()) != null) {
                                                        System.out.println(line);

                                                    }
                                                    bre.close();
                                                    p.waitFor();
                                                    System.out.println("Done.");

                                                    p.destroy();

                                                } catch (Exception ex) {
                                                    AlertDialogs.showErrors(ex);
                                                } finally {
                                                    latch.countDown();
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
                            AlertDialogs.showmessage("تم");
                            super.succeeded();
                        }
                    };
                    service.start();
                }
            }
        } catch (Exception ex) {
            AlertDialogs.showErrors(ex);
        }

    }

}
