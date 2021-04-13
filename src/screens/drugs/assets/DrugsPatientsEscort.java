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
/*     */ public class DrugsPatientsEscort
/*     */ {
/*     */   int id;
/*     */   int patientId;
/*     */   String name;
/*     */   String relation;
/*     */   String Address;
/*     */   String nationaId;
/*     */   String tele1;
/*     */   String tele2;
/*     */   InputStream photo;
/*     */   String photoExt;
/*     */   
/*     */   public DrugsPatientsEscort() {}
/*     */   
/*     */   public DrugsPatientsEscort(int id, String name, String relation, String Address, String nationaId, String tele1, String tele2) {
/*  40 */     this.id = id;
/*  41 */     this.name = name;
/*  42 */     this.relation = relation;
/*  43 */     this.Address = Address;
/*  44 */     this.nationaId = nationaId;
/*  45 */     this.tele1 = tele1;
/*  46 */     this.tele2 = tele2;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  50 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  54 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getAddress() {
/*  58 */     return this.Address;
/*     */   }
/*     */   
/*     */   public void setAddress(String Address) {
/*  62 */     this.Address = Address;
/*     */   }
/*     */   
/*     */   public String getTele1() {
/*  66 */     return this.tele1;
/*     */   }
/*     */   
/*     */   public void setTele1(String tele1) {
/*  70 */     this.tele1 = tele1;
/*     */   }
/*     */   
/*     */   public String getTele2() {
/*  74 */     return this.tele2;
/*     */   }
/*     */   
/*     */   public void setTele2(String tele2) {
/*  78 */     this.tele2 = tele2;
/*     */   }
/*     */   
/*     */   public int getPatientId() {
/*  82 */     return this.patientId;
/*     */   }
/*     */   
/*     */   public void setPatientId(int patientId) {
/*  86 */     this.patientId = patientId;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  90 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  94 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getRelation() {
/*  98 */     return this.relation;
/*     */   }
/*     */   
/*     */   public void setRelation(String relation) {
/* 102 */     this.relation = relation;
/*     */   }
/*     */   
/*     */   public String getNationaId() {
/* 106 */     return this.nationaId;
/*     */   }
/*     */   
/*     */   public void setNationaId(String nationaId) {
/* 110 */     this.nationaId = nationaId;
/*     */   }
/*     */   
/*     */   public InputStream getPhoto() {
/* 114 */     return this.photo;
/*     */   }
/*     */   
/*     */   public void setPhoto(InputStream photo) {
/* 118 */     this.photo = photo;
/*     */   }
/*     */   
/*     */   public String getPhotoExt() {
/* 122 */     return this.photoExt;
/*     */   }
/*     */   
/*     */   public void setPhotoExt(String photoExt) {
/* 126 */     this.photoExt = photoExt;
/*     */   }
/*     */   
/*     */   public boolean Add() throws Exception {
/* 130 */     PreparedStatement ps = get.Prepare("INSERT INTO `drg_patient_escort`(`id`, `patient_id`, `name`, `relation`, `address`, `national_id`, `tele1`, `tele2`, `national_photo`, `ext`) VALUES (?,?,?,?,?,?,?,?,?,?)");
/* 131 */     ps.setInt(1, this.id);
/* 132 */     ps.setInt(2, this.patientId);
/* 133 */     ps.setString(3, this.name);
/* 134 */     ps.setString(4, this.relation);
/* 135 */     ps.setString(5, this.Address);
/* 136 */     ps.setString(6, this.nationaId);
/* 137 */     ps.setString(7, this.tele1);
/* 138 */     ps.setString(8, this.tele2);
/* 139 */     ps.setBlob(9, this.photo);
/* 140 */     ps.setString(10, this.photoExt);
/* 141 */     ps.execute();
/* 142 */     return true;
/*     */   }
/*     */   
/*     */   public boolean AddWithoutPhoto() throws Exception {
/* 146 */     PreparedStatement ps = get.Prepare("INSERT INTO `drg_patient_escort`(`id`, `patient_id`, `name`, `relation`, `address`, `national_id`, `tele1`, `tele2`) VALUES (?,?,?,?,?,?,?,?)");
/* 147 */     ps.setInt(1, this.id);
/* 148 */     ps.setInt(2, this.patientId);
/* 149 */     ps.setString(3, this.name);
/* 150 */     ps.setString(4, this.relation);
/* 151 */     ps.setString(5, this.Address);
/* 152 */     ps.setString(6, this.nationaId);
/* 153 */     ps.setString(7, this.tele1);
/* 154 */     ps.setString(8, this.tele2);
/* 155 */     ps.execute();
/* 156 */     return true;
/*     */   }
/*     */   
/*     */   public boolean Edite() throws Exception {
/* 160 */     PreparedStatement ps = get.Prepare("UPDATE `drg_patient_escort` SET `patient_id`=?,`name`=?,`relation`=?,`national_id`=?,`tele1`=?,`national_photo`=?,`ext`=?,`address`=?,`tele2`=? WHERE `id`=?");
/* 161 */     ps.setInt(10, this.id);
/* 162 */     ps.setInt(1, this.patientId);
/* 163 */     ps.setString(2, this.name);
/* 164 */     ps.setString(3, this.relation);
/* 165 */     ps.setString(4, this.nationaId);
/* 166 */     ps.setString(5, this.tele1);
/* 167 */     ps.setBlob(6, this.photo);
/* 168 */     ps.setString(7, this.photoExt);
/* 169 */     ps.setString(8, this.Address);
/* 170 */     ps.setString(9, this.tele2);
/* 171 */     ps.execute();
/* 172 */     return true;
/*     */   }
/*     */   
/*     */   public boolean EditeWithoutPhoto() throws Exception {
/* 176 */     PreparedStatement ps = get.Prepare("UPDATE `drg_patient_escort` SET `patient_id`=?,`name`=?,`relation`=?,`national_id`=?,`tele1`=?,`address`=?,`tele2`=? WHERE `id`=?");
/* 177 */     ps.setInt(8, this.id);
/* 178 */     ps.setInt(1, this.patientId);
/* 179 */     ps.setString(2, this.name);
/* 180 */     ps.setString(3, this.relation);
/* 181 */     ps.setString(4, this.nationaId);
/* 182 */     ps.setString(5, this.tele1);
/* 183 */     ps.setString(6, this.Address);
/* 184 */     ps.setString(7, this.tele2);
/* 185 */     ps.execute();
/* 186 */     return true;
/*     */   }
/*     */   
/*     */   public boolean Delete() throws Exception {
/* 190 */     PreparedStatement ps = get.Prepare("DELETE FROM `drg_patient_escort` WHERE `id`=?");
/* 191 */     ps.setInt(1, this.id);
/* 192 */     ps.execute();
/* 193 */     return true;
/*     */   }
/*     */   
/*     */   public static ObservableList<DrugsPatientsEscort> getData(String patientId) throws Exception {
/* 197 */     ObservableList<DrugsPatientsEscort> data = FXCollections.observableArrayList();
/* 198 */     ResultSet rs = get.getReportCon().createStatement().executeQuery("SELECT `id`,`name`, `relation`, `address`,`national_id`, `tele1`,`tele2` FROM `drg_patient_escort` WHERE `patient_id` ='" + patientId + "'");
/* 199 */     while (rs.next()) {
/* 200 */       data.add(new DrugsPatientsEscort(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
/*     */     }
/* 202 */     return data;
/*     */   }
/*     */   
/*     */   public static String getAutoNum() throws Exception {
/* 206 */     return get.getTableData("select IFNULL(MAX(`id`)+1,1) from drg_patient_escort").getValueAt(0, 0).toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void getNationalIdPhoto() throws Exception {
/* 211 */     File file = null;
/* 212 */     String selectSQL = "SELECT `national_photo` FROM `drg_patient_escort` WHERE `id`='" + this.id + "'";
/* 213 */     JTable tab = get.getTableData("SELECT `ext` FROM `drg_patient_escort` WHERE `id`='" + this.id + "'");
/*     */     
/* 215 */     if (tab.getRowCount() <= 0) {
/* 216 */       Alert alert = new Alert(Alert.AlertType.INFORMATION);
/* 217 */       alert.setContentText("لا يوجد صورة متوفرة");
/* 218 */       alert.show();
/*     */     } else {
/* 220 */       String ext = tab.getValueAt(0, 0).toString();
/* 221 */       ResultSet rs = null;
/*     */       
/*     */       try {
/* 224 */         PreparedStatement pstmt = get.Prepare(selectSQL);
/*     */ 
/*     */         
/* 227 */         rs = pstmt.executeQuery();
/*     */         
/* 229 */         File directory = new File(System.getProperty("user.home") + "\\Desktop\\Elbarbary System\\img");
/* 230 */         directory.mkdirs();
/*     */         
/* 232 */         file = new File(directory + "\\" + this.id + "-" + this.name + "." + ext);
/*     */         
/* 234 */         FileOutputStream output = new FileOutputStream(file);
/*     */         
/* 236 */         String payPath = file.getAbsolutePath();
/* 237 */         while (rs.next()) {
/* 238 */           InputStream input = rs.getBinaryStream("photo");
/* 239 */           byte[] buffer = new byte[1024];
/* 240 */           while (input.read(buffer) > 0) {
/* 241 */             output.write(buffer);
/*     */           }
/*     */         } 
/* 244 */         Desktop d = Desktop.getDesktop();
/* 245 */         d.open(file);
/* 246 */       } catch (Exception e) {
/* 247 */         System.out.println(e.getMessage());
/*     */       } finally {
/*     */         try {
/* 250 */           if (rs != null) {
/* 251 */             rs.close();
/*     */           }
/* 253 */         } catch (Exception e) {
/* 254 */           System.out.println(e.getMessage());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\xampp\htdocs\github\ElBarbary-Hospital\dist\ElBarbary Hospital.jar!\screens\drugs\assets\DrugsPatientsEscort.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */