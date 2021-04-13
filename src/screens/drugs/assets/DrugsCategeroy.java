/*    */ package screens.drugs.assets;
/*    */ 
/*    */ import db.get;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import javafx.collections.FXCollections;
/*    */ import javafx.collections.ObservableList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DrugsCategeroy
/*    */ {
/*    */   int id;
/*    */   String name;
/*    */   
/*    */   public DrugsCategeroy() {}
/*    */   
/*    */   public DrugsCategeroy(int id, String name) {
/* 26 */     this.id = id;
/* 27 */     this.name = name;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 31 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(int id) {
/* 35 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 39 */     return this.name;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 43 */     this.name = name;
/*    */   }
/*    */   
/*    */   public static boolean Add(String value) throws Exception {
/* 47 */     PreparedStatement ps = get.Prepare("INSERT INTO `drg_expense_cat`( `name`) VALUES (?)");
/* 48 */     ps.setString(1, value);
/* 49 */     ps.execute();
/* 50 */     return true;
/*    */   }
/*    */   
/*    */   public boolean Edite() throws Exception {
/* 54 */     PreparedStatement ps = get.Prepare("UPDATE `drg_expense_cat` SET `name`=? WHERE `id`=?");
/* 55 */     ps.setString(1, this.name);
/* 56 */     ps.setInt(2, this.id);
/* 57 */     ps.execute();
/* 58 */     return true;
/*    */   }
/*    */   
/*    */   public boolean Delete() throws Exception {
/* 62 */     PreparedStatement ps = get.Prepare("DELETE FROM `drg_expense_cat` WHERE `id`=?");
/* 63 */     ps.setInt(1, this.id);
/* 64 */     ps.execute();
/* 65 */     return true;
/*    */   }
/*    */   
/*    */   public static ObservableList<DrugsCategeroy> getData() throws Exception {
/* 69 */     ObservableList<DrugsCategeroy> data = FXCollections.observableArrayList();
/* 70 */     ResultSet rs = get.getReportCon().createStatement().executeQuery("SELECT * FROM `drg_expense_cat`");
/* 71 */     while (rs.next()) {
/* 72 */       data.add(new DrugsCategeroy(rs.getInt(1), rs.getString(2)));
/*    */     }
/* 74 */     return data;
/*    */   }
/*    */   
/*    */   public static String getAutoNum() throws Exception {
/* 78 */     return get.getTableData("SELECT IFNULL(MAX(`id`)+1,1) FROM `drg_expense_cat`").getValueAt(0, 0).toString();
/*    */   }
/*    */ }


/* Location:              C:\xampp\htdocs\github\ElBarbary-Hospital\dist\ElBarbary Hospital.jar!\screens\drugs\assets\DrugsCategeroy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */