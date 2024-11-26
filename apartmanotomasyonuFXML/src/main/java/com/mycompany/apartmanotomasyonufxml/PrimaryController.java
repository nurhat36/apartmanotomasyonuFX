package com.mycompany.apartmanotomasyonufxml;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PrimaryController {
    
    public TextField yoneticibinano_jtf1;
    public TextField kullanıcıbinano_jtf1;
    public TextField kullanıcıbinanokayıt_jtf1;
    public Label yön_gr_hata_lbli;
    public PasswordField yöneticigirispass;
    public ComboBox dairenokayıt_cmb;
    public ComboBox dairenogiris_cmb;
    
    public static String bina_no;
    public static String daire_no;
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
                    App.setRoot("secondary");
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
    private void switchToyonsifunut() throws IOException {
        App.setRoot("yonsifunut");
    }
    @FXML
    private void switchTokulsifunut() throws IOException {
        App.setRoot("kulsifunut");
    }
    
}
