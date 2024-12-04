/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apartmanotomasyonufxml;


import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
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
    private ToggleButton yon_kay_toggleButton;
    @FXML
    private TextField yon_kay_text;
    @FXML
    private TextField yon_kay_text_tek;
    @FXML
    private TextField sif_unt_yenisifretekrar_pwf;
    @FXML
    private Label Sif_Unt_kon_jlbl;
    @FXML
    private Button yon_sif_degistir_Button;

    @FXML
    public void initialize() {
        addHoverEffect(Sif_Unt_binano_jtf);
        addHoverEffect(Sif_Unt_tel_no_jtf);
        addHoverEffect(Sif_Unt_e_posta_jtf);
        addHoverEffect(sif_unt_yeni_sif_pwf);
        addHoverEffect(sif_unt_yenisifretekrar_pwf);
        addHoverEffect(yon_kay_toggleButton);
        addHoverEffect(yon_sif_degistir_Button);
    }
    private void addHoverEffect(Control control) {
        DropShadow defaultShadow = new DropShadow();
        defaultShadow.setOffsetY(0);
        defaultShadow.setColor(Color.GRAY);

        control.setEffect(defaultShadow);

        control.setOnMouseEntered(event -> {
            DropShadow hoverShadow = new DropShadow();
            hoverShadow.setOffsetY(5);
            hoverShadow.setColor(Color.GRAY);
            control.setEffect(hoverShadow);
            control.setTranslateY(-5);
        });

        control.setOnMouseExited(event -> {

            control.setTranslateY(0);
        });
    }
    @FXML
    private void toggle_yonetici_kayit(){
        if (yon_kay_toggleButton.isSelected()) {
            yon_kay_toggleButton.setText("Hide");
            yon_kay_text.setText(sif_unt_yeni_sif_pwf.getText()); // Şifreyi TextField'e aktar
            yon_kay_text.setVisible(true);
            sif_unt_yeni_sif_pwf.setVisible(false);
            yon_kay_text_tek.setText(sif_unt_yenisifretekrar_pwf.getText()); // Şifreyi TextField'e aktar
            yon_kay_text_tek.setVisible(true);
            sif_unt_yenisifretekrar_pwf.setVisible(false);
        } else {
            yon_kay_toggleButton.setText("Show");
            sif_unt_yeni_sif_pwf.setText(yon_kay_text.getText()); // Şifreyi PasswordField'e aktar
            sif_unt_yeni_sif_pwf.setVisible(true);
            yon_kay_text.setVisible(false);
            sif_unt_yenisifretekrar_pwf.setText(yon_kay_text_tek.getText()); // Şifreyi PasswordField'e aktar
            sif_unt_yenisifretekrar_pwf.setVisible(true);
            yon_kay_text_tek.setVisible(false);
        }


    }

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
            int result = dbhelper.executeUpdate(insertSQL, dbhelper.hashPassword(yeniSifreTekrar), binano, telefonNumarasi, Sif_Unt_e_posta_jtf.getText());
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
