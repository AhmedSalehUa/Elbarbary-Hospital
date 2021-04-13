/*     */ package screens.drugs.assets;
/*     */ 
/*     */ import db.get;
/*     */ import java.awt.Desktop;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.Alert;
/*     */ import javax.swing.JTable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DrugsPatients
/*     */ {
/*     */   int id;
/*     */   String name;
/*     */   String government;
/*     */   String address;
/*     */   String age;
/*     */   int tranportOrgId;
/*     */   String tranportOrg;
/*     */   String dateOfBirth;
/*     */   String national_id;
/*     */   String gender;
/*     */   String diagnosis;
/*     */   String doctor_name;
/*     */   int doctor_id;
/*     */   String tele1;
/*     */   String tele2;
/*     */   InputStream photo;
/*     */   String photoExt;
/*     */   
/*     */   public DrugsPatients() {}
/*     */   
/*     */   public DrugsPatients(int id, String name, String address, String age, String national_id, String diagnosis, int doctor_id, String tele1, String tele2) {
/*  48 */     this.id = id;
/*  49 */     this.name = name;
/*  50 */     this.address = address;
/*  51 */     this.age = age;
/*  52 */     this.national_id = national_id;
/*  53 */     this.diagnosis = diagnosis;
/*  54 */     this.doctor_id = doctor_id;
/*  55 */     this.tele1 = tele1;
/*  56 */     this.tele2 = tele2;
/*     */   }
/*     */ 
/*     */   
/*     */   public DrugsPatients(int id, String name, String government, String address, String dateOfBirth, String age, String national_id, String doctor_name, String tranportOrg, String diagnosis, String tele1, String tele2, String gender) {
/*  61 */     this.id = id;
/*  62 */     this.name = name;
/*  63 */     this.government = government;
/*  64 */     this.address = address;
/*  65 */     this.age = age;
/*  66 */     this.tranportOrg = tranportOrg;
/*  67 */     this.dateOfBirth = dateOfBirth;
/*  68 */     this.national_id = national_id;
/*  69 */     this.diagnosis = diagnosis;
/*  70 */     this.doctor_name = doctor_name;
/*  71 */     this.tele1 = tele1;
/*  72 */     this.tele2 = tele2;
/*  73 */     this.gender = gender;
/*     */   }
/*     */   
/*     */   public DrugsPatients(int id, String name, String address, String age, String national_id, String doctor_name, String diagnosis, String tele1, String tele2) {
/*  77 */     this.id = id;
/*  78 */     this.name = name;
/*  79 */     this.address = address;
/*  80 */     this.age = age;
/*  81 */     this.national_id = national_id;
/*  82 */     this.diagnosis = diagnosis;
/*  83 */     this.doctor_name = doctor_name;
/*  84 */     this.tele1 = tele1;
/*  85 */     this.tele2 = tele2;
/*     */   }
/*     */ 
/*     */   
/*     */   public DrugsPatients(int id, String name, String address, String age, String national_id, String diagnosis, String doctor_name, String tele1, String tele2, InputStream photo, String photoExt) {
/*  90 */     this.id = id;
/*  91 */     this.name = name;
/*  92 */     this.address = address;
/*  93 */     this.age = age;
/*  94 */     this.national_id = national_id;
/*  95 */     this.doctor_name = doctor_name;
/*  96 */     this.diagnosis = diagnosis;
/*  97 */     this.tele1 = tele1;
/*  98 */     this.tele2 = tele2;
/*  99 */     this.photo = photo;
/* 100 */     this.photoExt = photoExt;
/*     */   }
/*     */   
/*     */   public DrugsPatients(int id, String name, String address, String age, String national_id, String diagnosis, int doctor_id, String tele1, String tele2, InputStream photo, String photoExt) {
/* 104 */     this.id = id;
/* 105 */     this.name = name;
/* 106 */     this.address = address;
/* 107 */     this.age = age;
/* 108 */     this.national_id = national_id;
/* 109 */     this.doctor_id = doctor_id;
/* 110 */     this.diagnosis = diagnosis;
/* 111 */     this.tele1 = tele1;
/* 112 */     this.tele2 = tele2;
/* 113 */     this.photo = photo;
/* 114 */     this.photoExt = photoExt;
/*     */   }
/*     */   
/*     */   public int getId() {
/* 118 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/* 122 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getGender() {
/* 126 */     return this.gender;
/*     */   }
/*     */   
/*     */   public void setGender(String gender) {
/* 130 */     this.gender = gender;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 134 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/* 138 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getAddress() {
/* 142 */     return this.address;
/*     */   }
/*     */   
/*     */   public void setAddress(String address) {
/* 146 */     this.address = address;
/*     */   }
/*     */   
/*     */   public String getAge() {
/* 150 */     return this.age;
/*     */   }
/*     */   
/*     */   public void setAge(String age) {
/* 154 */     this.age = age;
/*     */   }
/*     */   
/*     */   public String getTele1() {
/* 158 */     return this.tele1;
/*     */   }
/*     */   
/*     */   public void setTele1(String tele1) {
/* 162 */     this.tele1 = tele1;
/*     */   }
/*     */   
/*     */   public String getTele2() {
/* 166 */     return this.tele2;
/*     */   }
/*     */   
/*     */   public void setTele2(String tele2) {
/* 170 */     this.tele2 = tele2;
/*     */   }
/*     */   
/*     */   public String getNational_id() {
/* 174 */     return this.national_id;
/*     */   }
/*     */   
/*     */   public void setNational_id(String national_id) {
/* 178 */     this.national_id = national_id;
/*     */   }
/*     */   
/*     */   public String getDiagnosis() {
/* 182 */     return this.diagnosis;
/*     */   }
/*     */   
/*     */   public void setDiagnosis(String diagnosis) {
/* 186 */     this.diagnosis = diagnosis;
/*     */   }
/*     */   
/*     */   public InputStream getPhoto() {
/* 190 */     return this.photo;
/*     */   }
/*     */   
/*     */   public void setPhoto(InputStream photo) {
/* 194 */     this.photo = photo;
/*     */   }
/*     */   
/*     */   public String getPhotoExt() {
/* 198 */     return this.photoExt;
/*     */   }
/*     */   
/*     */   public void setPhotoExt(String photoExt) {
/* 202 */     this.photoExt = photoExt;
/*     */   }
/*     */   
/*     */   public String getDoctor_name() {
/* 206 */     return this.doctor_name;
/*     */   }
/*     */   
/*     */   public void setDoctor_name(String doctor_name) {
/* 210 */     this.doctor_name = doctor_name;
/*     */   }
/*     */   
/*     */   public int getDoctor_id() {
/* 214 */     return this.doctor_id;
/*     */   }
/*     */   
/*     */   public void setDoctor_id(int doctor_id) {
/* 218 */     this.doctor_id = doctor_id;
/*     */   }
/*     */   
/*     */   public String getGovernment() {
/* 222 */     return this.government;
/*     */   }
/*     */   
/*     */   public void setGovernment(String government) {
/* 226 */     this.government = government;
/*     */   }
/*     */   
/*     */   public int getTranportOrgId() {
/* 230 */     return this.tranportOrgId;
/*     */   }
/*     */   
/*     */   public void setTranportOrgId(int tranportOrgId) {
/* 234 */     this.tranportOrgId = tranportOrgId;
/*     */   }
/*     */   
/*     */   public String getTranportOrg() {
/* 238 */     return this.tranportOrg;
/*     */   }
/*     */   
/*     */   public void setTranportOrg(String tranportOrg) {
/* 242 */     this.tranportOrg = tranportOrg;
/*     */   }
/*     */   
/*     */   public String getDateOfBirth() {
/* 246 */     return this.dateOfBirth;
/*     */   }
/*     */   
/*     */   public void setDateOfBirth(String dateOfBirth) {
/* 250 */     this.dateOfBirth = dateOfBirth;
/*     */   }
/*     */   
/*     */   public boolean Add() throws Exception {
/* 254 */     PreparedStatement st = get.Prepare("INSERT INTO `drg_patient`(`id`, `name`, `government`, `address`, `date_of_birth`, `age`, `national_id`, `doctor_id`, `transport_org_id`, `diagnosis`, `tele1`, `tele2`,`gender`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
/* 255 */     st.setInt(1, this.id);
/* 256 */     st.setString(2, this.name);
/* 257 */     st.setString(3, this.government);
/* 258 */     st.setString(4, this.address);
/* 259 */     st.setString(5, this.dateOfBirth);
/* 260 */     st.setString(6, this.age);
/* 261 */     st.setString(7, this.national_id);
/* 262 */     st.setInt(8, this.doctor_id);
/* 263 */     st.setInt(9, this.tranportOrgId);
/* 264 */     st.setString(10, this.diagnosis);
/* 265 */     st.setString(11, this.tele1);
/* 266 */     st.setString(12, this.tele2);
/* 267 */     st.setString(13, this.gender);
/* 268 */     st.execute();
/* 269 */     return true;
/*     */   }
/*     */   
/*     */   public boolean AddWithPhoto() throws Exception {
/* 273 */     Add();
/* 274 */     PreparedStatement st = get.Prepare("INSERT INTO `drg_patient_photo`(`patient_id`, `photo`, `ext`) VALUES (?,?,?)");
/* 275 */     st.setInt(1, this.id);
/* 276 */     st.setBlob(2, this.photo);
/* 277 */     st.setString(3, this.photoExt);
/* 278 */     st.execute();
/* 279 */     return true;
/*     */   }
/*     */   
/*     */   public boolean Edite() throws Exception {
/* 283 */     PreparedStatement st = get.Prepare("UPDATE `drg_patient` SET `name`=?,`address`=?,`age`=?,`national_id`=?,`doctor_id`=?,`diagnosis`=?,`tele1`=?,`tele2`=?,`government`=?,`date_of_birth`=?,`transport_org_id`=?,`gender`=? WHERE `id`=?");
/* 284 */     st.setInt(13, this.id);
/* 285 */     st.setString(1, this.name);
/* 286 */     st.setString(2, this.address);
/* 287 */     st.setString(3, this.age);
/* 288 */     st.setString(4, this.national_id);
/* 289 */     st.setInt(5, this.doctor_id);
/* 290 */     st.setString(6, this.diagnosis);
/* 291 */     st.setString(7, this.tele1);
/* 292 */     st.setString(8, this.tele2);
/* 293 */     st.setString(9, this.government);
/* 294 */     st.setString(10, this.dateOfBirth);
/* 295 */     st.setInt(11, this.tranportOrgId);
/* 296 */     st.setString(12, this.gender);
/* 297 */     st.execute();
/* 298 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean EditeWithPhoto() throws Exception {
/* 303 */     Edite();
/* 304 */     PreparedStatement st = get.Prepare("INSERT INTO drg_patient_photo (`patient_id`, `photo`, `ext`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE `photo` = ?,`ext`=?");
/* 305 */     st.setInt(1, this.id);
/* 306 */     st.setBlob(2, this.photo);
/* 307 */     st.setString(3, this.photoExt);
/* 308 */     st.setBlob(4, this.photo);
/* 309 */     st.setString(5, this.photoExt);
/* 310 */     st.execute();
/* 311 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean Delete() throws Exception {
/* 316 */     PreparedStatement st = get.Prepare("DELETE FROM `drg_patient` WHERE `id`=?");
/* 317 */     st.setInt(1, this.id);
/*     */     
/* 319 */     PreparedStatement sta = get.Prepare("DELETE FROM `drg_patient_photo` WHERE `patient_id`=?");
/* 320 */     sta.setInt(1, this.id);
/* 321 */     sta.execute();
/* 322 */     st.execute();
/* 323 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ObservableList<DrugsPatients> getData() throws Exception {
/* 328 */     ObservableList<DrugsPatients> data = FXCollections.observableArrayList();
/*     */     
/* 330 */     String SQL = "SELECT `drg_patient`.`id`, `drg_patient`.`name`,`drg_patient`.`government`, `drg_patient`.`address`, `drg_patient`.`date_of_birth`, `drg_patient`.`age`, `drg_patient`.`national_id`, `doctors`.`name`, `patient_transporting_org`.`name`, `drg_patient`.`diagnosis`, `drg_patient`.`tele1`, `drg_patient`.`tele2`,`drg_patient`.`gender` FROM `patient_transporting_org`,`drg_patient`,`doctors` WHERE `doctors`.`id` =`drg_patient`.`doctor_id` AND `patient_transporting_org`.`id` =  `drg_patient`.`transport_org_id`";
/*     */     
/* 332 */     ResultSet rs = get.getReportCon().createStatement().executeQuery(SQL);
/*     */     
/* 334 */     while (rs.next()) {
/* 335 */       data.add(new DrugsPatients(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)));
/*     */     }
/* 337 */     return data;
/*     */   }
/*     */   
/*     */   public static String getAutoNum() throws Exception {
/* 341 */     return get.getTableData("SELECT IFNULL(max(`id`) + 1,'1') FROM `drg_patient`").getValueAt(0, 0).toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void getNationalIdPhoto() throws Exception {
/* 346 */     File file = null;
/* 347 */     String selectSQL = "SELECT `photo` FROM `drg_patient_photo` WHERE `patient_id`='" + this.id + "'";
/* 348 */     JTable tab = get.getTableData("SELECT `ext` FROM `drg_patient_photo` WHERE `patient_id`='" + this.id + "'");
/*     */     
/* 350 */     if (tab.getRowCount() <= 0) {
/* 351 */       Alert alert = new Alert(Alert.AlertType.INFORMATION);
/* 352 */       alert.setContentText("لا يوجد صورة متوفرة");
/* 353 */       alert.show();
/*     */     } else {
/* 355 */       String ext = tab.getValueAt(0, 0).toString();
/* 356 */       ResultSet rs = null;
/*     */       
/*     */       try {
/* 359 */         PreparedStatement pstmt = get.Prepare(selectSQL);
/*     */ 
/*     */         
/* 362 */         rs = pstmt.executeQuery();
/*     */         
/* 364 */         File directory = new File(System.getProperty("user.home") + "\\Desktop\\Elbarbary System\\img");
/* 365 */         directory.mkdirs();
/*     */         
/* 367 */         file = new File(directory + "\\" + this.id + "-" + this.name + "." + ext);
/*     */         
/* 369 */         FileOutputStream output = new FileOutputStream(file);
/*     */         
/* 371 */         String payPath = file.getAbsolutePath();
/* 372 */         while (rs.next()) {
/* 373 */           InputStream input = rs.getBinaryStream("photo");
/* 374 */           byte[] buffer = new byte[1024];
/* 375 */           while (input.read(buffer) > 0) {
/* 376 */             output.write(buffer);
/*     */           }
/*     */         } 
/* 379 */         Desktop d = Desktop.getDesktop();
/* 380 */         d.open(file);
/* 381 */       } catch (Exception e) {
/* 382 */         System.out.println(e.getMessage());
/*     */       } finally {
/*     */         try {
/* 385 */           if (rs != null) {
/* 386 */             rs.close();
/*     */           }
/* 388 */         } catch (Exception e) {
/* 389 */           System.out.println(e.getMessage());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\xampp\htdocs\github\ElBarbary-Hospital\dist\ElBarbary Hospital.jar!\screens\drugs\assets\DrugsPatients.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */