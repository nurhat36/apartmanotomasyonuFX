package com.mycompany.apartmanotomasyonufxml;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;

public class PrimaryController {
    
    public TextField yoneticibinano_jtf1;
    public TextField yonetici_kayit_binano_jtf1;
    public TextField yonetici_daire_sayisi_jtf1;
    public TextField kullanıcıbinano_jtf1;
    public TextField kullanıcıbinanokayıt_jtf1;
    public TextField kullanıci_e_posta_jtf1;
    public TextField kullanıcı_telefon_nokayıt_jtf1;
    public TextField yonetici_e_posta_jtf1;
    public TextField yoneticici_telefon_nokayıt_jtf1;
    public Label yön_gr_hata_lbli;
     public Label kul_gr_hata_lbli;
     public Label kul_ky_hata_lbli;
     public Label yon_ky_hata_lbli;
    public PasswordField yöneticigirispass;
    public PasswordField kullanicigirispass;
    public PasswordField kullanici_kaydol_pass;
    public PasswordField kullanici_kaydol_pass_tek;
    public PasswordField yonetici_kaydol_pass;
    public PasswordField yonetici_kaydol_pass_tek;
    public ComboBox dairenokayıt_cmb;
    public ComboBox dairenogiris_cmb;
    @FXML
    private ToggleButton toggleButton;
    @FXML
    private ToggleButton yon_toggleButton;
    @FXML
    private TextField textField;
    @FXML
    private TextField yon_sif_textField;
    @FXML
    private ToggleButton yon_kay_toggleButton;
    @FXML
    private TextField yon_kay_text;
    @FXML
    private TextField yon_kay_text_tek;
    @FXML
    private ToggleButton kul_kay_toggleButton;
    @FXML
    private TextField kul_kay_text;
    @FXML
    private TextField kul_kay_text_tek;
    public static String bina_no;
    public static String daire_no;
    @FXML
    public void initialize() {
        textField.setVisible(false);
        yon_sif_textField.setVisible(false);
        yon_kay_text_tek.setVisible(false);
        yon_kay_text.setVisible(false);
        kul_kay_text.setVisible(false);
        kul_kay_text_tek.setVisible(false);
    }
    @FXML
    private void toggle_kul_kayit(){
        if (kul_kay_toggleButton.isSelected()) {
            kul_kay_toggleButton.setText("Hide");
            kul_kay_text.setText(kullanici_kaydol_pass.getText()); // Şifreyi TextField'e aktar
            kul_kay_text.setVisible(true);
            kullanici_kaydol_pass.setVisible(false);
            kul_kay_text_tek.setText(kullanici_kaydol_pass_tek.getText()); // Şifreyi TextField'e aktar
            kul_kay_text_tek.setVisible(true);
            kullanici_kaydol_pass_tek.setVisible(false);
        } else {
            kul_kay_toggleButton.setText("Show");
            kullanici_kaydol_pass.setText(kul_kay_text.getText()); // Şifreyi PasswordField'e aktar
            kullanici_kaydol_pass.setVisible(true);
            kul_kay_text.setVisible(false);
            kullanici_kaydol_pass_tek.setText(kul_kay_text_tek.getText()); // Şifreyi PasswordField'e aktar
            kullanici_kaydol_pass_tek.setVisible(true);
            kul_kay_text_tek.setVisible(false);
        }


    }
    @FXML
    private void toggle_yonetici_kayit(){
        if (yon_kay_toggleButton.isSelected()) {
            yon_kay_toggleButton.setText("Hide");
            yon_kay_text.setText(yonetici_kaydol_pass.getText()); // Şifreyi TextField'e aktar
            yon_kay_text.setVisible(true);
            yonetici_kaydol_pass.setVisible(false);
            yon_kay_text_tek.setText(yonetici_kaydol_pass_tek.getText()); // Şifreyi TextField'e aktar
            yon_kay_text_tek.setVisible(true);
            yonetici_kaydol_pass_tek.setVisible(false);
        } else {
            yon_kay_toggleButton.setText("Show");
            yonetici_kaydol_pass.setText(yon_kay_text.getText()); // Şifreyi PasswordField'e aktar
            yonetici_kaydol_pass.setVisible(true);
            yon_kay_text.setVisible(false);
            yonetici_kaydol_pass_tek.setText(yon_kay_text_tek.getText()); // Şifreyi PasswordField'e aktar
            yonetici_kaydol_pass_tek.setVisible(true);
            yon_kay_text_tek.setVisible(false);
        }


    }
    @FXML
    private void toggle_yonetici_giris(){
        if (yon_toggleButton.isSelected()) {
            yon_toggleButton.setText("Hide");
            yon_sif_textField.setText(yöneticigirispass.getText()); // Şifreyi TextField'e aktar
            yon_sif_textField.setVisible(true);
            yöneticigirispass.setVisible(false);
        } else {
            yon_toggleButton.setText("Show");
            yöneticigirispass.setText(yon_sif_textField.getText()); // Şifreyi PasswordField'e aktar
            yöneticigirispass.setVisible(true);
            yon_sif_textField.setVisible(false);
        }


    }
   @FXML
   private void toggle_kullanici_giris(){
       if (toggleButton.isSelected()) {
           toggleButton.setText("Hide");
           textField.setText(kullanicigirispass.getText()); // Şifreyi TextField'e aktar
           textField.setVisible(true);
           kullanicigirispass.setVisible(false);
       } else {
           toggleButton.setText("Show");
           kullanicigirispass.setText(textField.getText()); // Şifreyi PasswordField'e aktar
           kullanicigirispass.setVisible(true);
           textField.setVisible(false);
       }


   }
    @FXML
    private void yön_kaydol() {                                             
        SQLHelper dbhelper = new SQLHelper();

        // Parametreli sorgu (önce kayıt var mı kontrol et)
        String checkSQL = "SELECT COUNT(*) FROM yötici_kayitlari_table WHERE Bina_No = ? and Daire_Sayısı = ?";

        try (ResultSet rs = dbhelper.executeQuery(checkSQL, yonetici_kayit_binano_jtf1.getText(), yonetici_daire_sayisi_jtf1.getText())) {
            while (rs != null && rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    // Eğer kayıt varsa, hata mesajı
                    yon_ky_hata_lbli.setText("Bu bina no ve daire no için zaten kayıt bulunuyor!");
                    return;  // İşlem sonlandırılır
                }
            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        }

        // Kayıt var mı kontrol et
        // Eğer kayıt yoksa, yeni veriyi ekle
        String insertSQL = "INSERT INTO yötici_kayitlari_table (Bina_No, Daire_Sayısı,şifre,Telefon_No,e_posta) VALUES (?, ?, ?,?,?)";
        if (yonetici_kaydol_pass.getText().equals(yonetici_kaydol_pass_tek.getText())) {

            // Veritabanına ekleme işlemi
            int result = dbhelper.executeUpdate(insertSQL, yonetici_kayit_binano_jtf1.getText(), yonetici_daire_sayisi_jtf1.getText(), yonetici_kaydol_pass_tek.getText(),yoneticici_telefon_nokayıt_jtf1.getText(),yonetici_e_posta_jtf1.getText());
            if (result > 0) {
                System.out.println("Veri başarıyla eklendi.");
            } else {
                yon_ky_hata_lbli.setText("Veri ekleme başarısız.");
                System.err.println("Veri ekleme başarısız.");
            }
            dbhelper.close();

            if (result > 0) {
                System.out.println("Kayıt başarıyla eklendi!");
                yon_ky_hata_lbli.setText("Kayıt başarıyla eklendi!");
            }
        } else {
            yon_ky_hata_lbli.setText("Şifre ve şifre tekrarı aynı değil.");
        }


    }

    @FXML
    private void kullanicikaydol(){
        SQLHelper sql = new SQLHelper();

        // Parametreli sorgu (önce kayıt var mı kontrol et)
        String checkSQL = "SELECT COUNT(*) FROM kullaniciler_table WHERE bina_no = ? AND daire_no = ?";
        String secilenVeri = (String) dairenokayıt_cmb.getValue();
        int index = secilenVeri.indexOf(": ");
        String daireNoStr = secilenVeri.substring(index + 2); // ": " karakterinden sonrası
        try (ResultSet rs = sql.executeQuery(checkSQL, kullanıcıbinanokayıt_jtf1.getText(), daireNoStr)) {
            while (rs != null && rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    // Eğer kayıt varsa, hata mesajı
                    kul_ky_hata_lbli.setText("Bu bina no ve daire no için zaten kayıt bulunuyor!");
                    return;  // İşlem sonlandırılır
                }
            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        }

        // Kayıt var mı kontrol et
        // Eğer kayıt yoksa, yeni veriyi ekle
        String insertSQL = "INSERT INTO kullaniciler_table (bina_no, daire_no, şifre,Telefon_No,e_posta) VALUES (?, ?, ?,?,?)";
        if (kullanici_kaydol_pass.getText().equals(kullanici_kaydol_pass_tek.getText())) {

            // Veritabanına ekleme işlemi
            int result = sql.executeUpdate(insertSQL, kullanıcıbinanokayıt_jtf1.getText(), daireNoStr, kullanici_kaydol_pass_tek.getText(),kullanıcı_telefon_nokayıt_jtf1.getText(),kullanıci_e_posta_jtf1.getText());
            if (result > 0) {
                System.out.println("Veri başarıyla eklendi.");
            } else {
                System.err.println("Veri ekleme başarısız.");
            }
            sql.close();

            if (result > 0) {
                System.out.println("Kayıt başarıyla eklendi!");
                kul_ky_hata_lbli.setText("Kayıt başarıyla eklendi!");
            }
        } else {
            kul_ky_hata_lbli.setText("Şifre ve şifre tekrarı aynı değil.");
        }
    }
    @FXML
    private void kullanicigiris(){
        
        SQLHelper dbhelper = new SQLHelper();

        String sql = "SELECT Bina_No, şifre FROM kullaniciler_table WHERE Bina_No = ? and daire_no = ?";
        String girilenKullaniciAdi = kullanıcıbinano_jtf1.getText(); // Kullanıcı adını UI'den çekiyoruz
        String secilenVeri = (String) dairenogiris_cmb.getValue();
        int index = secilenVeri.indexOf(": ");
        String daireNoStr = secilenVeri.substring(index + 2);
        String girilenSifre = kullanicigirispass.getText();

        try (ResultSet rs = dbhelper.executeQuery(sql, girilenKullaniciAdi, daireNoStr)) {

            if (rs.next()) {
                String veritabanindakiSifre = rs.getString("şifre");

                // Girilen şifreyle veritabanındaki şifreyi karşılaştırıyoruz
                if (veritabanindakiSifre.equals(girilenSifre)) {
                    System.out.println("Bina_No adı ve şifre doğru, işlem başarılı!");
                    
                    bina_no=girilenKullaniciAdi;
                    daire_no=daireNoStr;
                    App.setRoot("kullaniciekrani",new Stage());
                    // Burada gerekli işlemleri yapabilirsiniz
                } else {
                    kul_gr_hata_lbli.setText("Bina no ve/veya şifre yanlış");
                }
            } else {
                kul_gr_hata_lbli.setText("Bina no ve/veya şifre yanlış");
            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    @FXML
    private void cmbdolurkayıt() {
        // SQL bağlantı bilgileri
        SQLHelper dbhelper = new SQLHelper();
        String sql = "SELECT Daire_Sayısı FROM yötici_kayitlari_table WHERE Bina_No = ?";

        // ComboBox'u temizle
        dairenokayıt_cmb.getItems().clear();

        try (ResultSet rs = dbhelper.executeQuery(sql, kullanıcıbinanokayıt_jtf1.getText())) {
            if (rs.next()) {
                int daireSayisi = rs.getInt("Daire_Sayısı");

                // Yeni bir liste oluştur ve daire numaralarını ekle
                ObservableList<String> daireListesi = FXCollections.observableArrayList();
                for (int i = 1; i <= daireSayisi; i++) {
                    daireListesi.add("Daire No: " + i);
                }

                // ComboBox'a listeyi ata
                dairenokayıt_cmb.setItems(daireListesi);
            } else {
                // Kayıt bulunamazsa ComboBox'u temizle
                dairenokayıt_cmb.getItems().clear();
            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        }
    }
    @FXML
    private void cmbdolur() {
        // SQL bağlantı bilgileri
        SQLHelper dbhelper = new SQLHelper();
        String sql = "SELECT Daire_Sayısı FROM yötici_kayitlari_table WHERE Bina_No = ?";

        // ComboBox'u temizle
        dairenogiris_cmb.getItems().clear();

        try (ResultSet rs = dbhelper.executeQuery(sql, kullanıcıbinano_jtf1.getText())) {
            if (rs.next()) {
                int daireSayisi = rs.getInt("Daire_Sayısı");

                // Yeni bir liste oluştur ve daire numaralarını ekle
                ObservableList<String> daireListesi = FXCollections.observableArrayList();
                for (int i = 1; i <= daireSayisi; i++) {
                    daireListesi.add("Daire No: " + i);
                }

                // ComboBox'a listeyi ata
                dairenogiris_cmb.setItems(daireListesi);
            } else {
                // Kayıt bulunamazsa ComboBox'u temizle
                dairenogiris_cmb.getItems().clear();
            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        }
    }
    @FXML
    private void switchToSecondary() throws IOException {

        SQLHelper dbhelper = new SQLHelper();

        String sql = "SELECT Bina_No, şifre FROM yötici_kayitlari_table WHERE Bina_No = ?";

        String girilenKullaniciAdi = yoneticibinano_jtf1.getText(); // Kullanıcı adını UI'den çekiyoruz
        String girilenSifre = yöneticigirispass.getText(); // Şifreyi UI'den çekiyoruz

        try (ResultSet rs = dbhelper.executeQuery(sql, girilenKullaniciAdi)) {

            if (rs.next()) {
                String veritabanindakiSifre = rs.getString("şifre");
                // Girilen şifreyle veritabanındaki şifreyi karşılaştırıyoruz
                if (veritabanindakiSifre.equals(girilenSifre)) {
                    bina_no = yoneticibinano_jtf1.getText();
                    System.out.println("Bina_No adı ve şifre doğru, işlem başarılı!");
                    App.setRoot("secondary",new Stage());
                    // Burada gerekli işlemleri yapabilirsiniz
                } else {
                    yön_gr_hata_lbli.setText("Bina no ve/veya şifre yanlış");
                }
            } else {
                yön_gr_hata_lbli.setText("Bina no ve/veya şifre yanlış");
            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        }
    }
     @FXML
    private void switchToyonsifunut() {
        try {
            App.setRoot("yonsifunut",new Stage());
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void switchTokulsifunut(){
        try {
            App.setRoot("kulsifunut",new Stage());
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
