/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apartmanotomasyonufxml;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 *
 * @author Nurhat
 */
public class yonSifUnutController {

    @FXML
    private TextField Sif_Unt_binano_jtf;
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
    private void Sif_unut_onayla() {
        String binano = Sif_Unt_binano_jtf.getText();
        String telefonNumarasi = Sif_Unt_tel_no_jtf.getText();
        String yeniSifre = sif_unt_yeni_sif_pwf.getText();
        String yeniSifreTekrar = sif_unt_yenisifretekrar_pwf.getText();
        SQLHelper dbhelper = new SQLHelper();

        // Parametreli sorgu (önce kayıt var mı kontrol et)
        String checkSQL = "SELECT COUNT(*) FROM yötici_kayitlari_table WHERE Bina_No = ? and Telefon_No = ? and e_posta=?";

        try (ResultSet rs = dbhelper.executeQuery(checkSQL, binano, telefonNumarasi, Sif_Unt_e_posta_jtf.getText())) {
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
        String insertSQL = "UPDATE yötici_kayitlari_table SET şifre = ? WHERE Bina_No = ?and Telefon_No=? and e_posta=?";
        if (!yeniSifre.equals(yeniSifreTekrar)) {
            // Mesajı JLabel üzerinde göster
            Sif_Unt_kon_jlbl.setText("Şifreler uyuşmuyor. Lütfen tekrar deneyin.");
            Sif_Unt_kon_jlbl.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            return;
        } else {
            int result = dbhelper.executeUpdate(insertSQL, yeniSifreTekrar, binano, telefonNumarasi, Sif_Unt_e_posta_jtf.getText());
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
}
