/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apartmanotomasyonufxml;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 *
 * @author Nurhat
 */
public class KulSifUnutController {
    @FXML
    private TextField Sif_Unt_binano_jtf;
    @FXML
    private ComboBox DaireNo;
   
    @FXML
    private TextField Sif_Unt_tel_no_jtf;
    @FXML
    private TextField Sif_Unt_e_posta_jtf;
    @FXML
    private TextField sif_unt_yeni_sif_pwf;
    @FXML
    private TextField sif_unt_yenisifretekrar_pwf;
    @FXML
    private Label Sif_Unt_kon_jlbl;
    @FXML
    private ToggleButton kul_kay_toggleButton;
    @FXML
    private TextField kul_kay_text;
    @FXML
    private TextField kul_kay_text_tek;

    @FXML
    private void toggle_kul_kayit(){
        if (kul_kay_toggleButton.isSelected()) {
            kul_kay_toggleButton.setText("Hide");
            kul_kay_text.setText(sif_unt_yeni_sif_pwf.getText()); // Şifreyi TextField'e aktar
            kul_kay_text.setVisible(true);
            sif_unt_yeni_sif_pwf.setVisible(false);
            kul_kay_text_tek.setText(sif_unt_yenisifretekrar_pwf.getText()); // Şifreyi TextField'e aktar
            kul_kay_text_tek.setVisible(true);
            sif_unt_yenisifretekrar_pwf.setVisible(false);
        } else {
            kul_kay_toggleButton.setText("Show");
            sif_unt_yeni_sif_pwf.setText(kul_kay_text.getText()); // Şifreyi PasswordField'e aktar
            sif_unt_yeni_sif_pwf.setVisible(true);
            kul_kay_text.setVisible(false);
            sif_unt_yenisifretekrar_pwf.setText(kul_kay_text_tek.getText()); // Şifreyi PasswordField'e aktar
            sif_unt_yenisifretekrar_pwf.setVisible(true);
            kul_kay_text_tek.setVisible(false);
        }


    }
    @FXML
    private void Sif_Gün() {                                             
        String binano = Sif_Unt_binano_jtf.getText();
        String telefonNumarasi = Sif_Unt_tel_no_jtf.getText();
        String secilenVeri = (String) DaireNo.getValue();
        int index = secilenVeri.indexOf(": ");
        String daireNoStr = secilenVeri.substring(index + 2);
        String yeniSifre = sif_unt_yeni_sif_pwf.getText();
        String yeniSifreTekrar = sif_unt_yenisifretekrar_pwf.getText();
        SQLHelper dbhelper = new SQLHelper();

        // Parametreli sorgu (önce kayıt var mı kontrol et)
        String checkSQL = "SELECT COUNT(*) FROM kullaniciler_table WHERE bina_no = ? and daire_no=? and Telefon_No = ? and e_posta=?";

        try (ResultSet rs = dbhelper.executeQuery(checkSQL, binano,daireNoStr, telefonNumarasi,Sif_Unt_e_posta_jtf.getText())) {
            while (rs != null && rs.next()) {
                int count = rs.getInt(1);
                if (count == 0) {
                    // Eğer kayıt varsa, hata mesajı
                    Sif_Unt_kon_jlbl.setText("Bu bina no ve daire no için kayıt bulunmuyor!");
                    Sif_Unt_kon_jlbl.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                    return;  // İşlem sonlandırılır
                }
            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        }
        String insertSQL = "UPDATE kullaniciler_table SET şifre = ? WHERE bina_no = ? and daire_no=? and Telefon_No = ? and e_posta=?";
        if (!yeniSifre.equals(yeniSifreTekrar)) {
            // Mesajı JLabel üzerinde göster
            Sif_Unt_kon_jlbl.setText("Şifreler uyuşmuyor. Lütfen tekrar deneyin.");
            Sif_Unt_kon_jlbl.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            return;
        } else {
            int result = dbhelper.executeUpdate(insertSQL, dbhelper.hashPassword(yeniSifreTekrar), binano,daireNoStr,telefonNumarasi,Sif_Unt_e_posta_jtf.getText());
            if (result > 0) {
                Sif_Unt_kon_jlbl.setText("şifre başarıyla güncellendi.");
                Sif_Unt_kon_jlbl.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                System.out.println("Veri başarıyla eklendi.");
            } else {
                System.err.println("Veri ekleme başarısız.");
            }
            dbhelper.close();

            if (result > 0) {
                System.out.println("Kayıt başarıyla eklendi!");

            }

        }
    }  
    @FXML
    private void cmbdolur() {

        // SQL bağlantı bilgileri
        SQLHelper dbhelper = new SQLHelper();

        String sql = "SELECT Daire_Sayısı FROM yötici_kayitlari_table WHERE Bina_No = ?";
        DaireNo.getItems().clear();
        try (ResultSet rs = dbhelper.executeQuery(sql, Sif_Unt_binano_jtf.getText())) {

            if (rs.next()) {
                int daireSayisi = rs.getInt("Daire_Sayısı");

                // Yeni bir liste oluştur ve daire numaralarını ekle
                ObservableList<String> daireListesi = FXCollections.observableArrayList();
                for (int i = 1; i <= daireSayisi; i++) {
                    daireListesi.add("Daire No: " + i);
                }

                // ComboBox'a listeyi ata
                DaireNo.setItems(daireListesi);
            } else {
                DaireNo.getItems().clear(); // Kayıt bulunmazsa ComboBox'u temizleyin
            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        }

    }
}
