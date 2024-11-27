/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apartmanotomasyonufxml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Nurhat
 */
public class kullaniciekraniController {
    @FXML
    private Label dekontgoruntule_uyari;
    @FXML
    private Label butce1;
    @FXML
    private Label aidat3;
    @FXML
    private Spinner<Integer> dekontID;
    @FXML
    private TableView<AidatGeliri> Gelir_table;

    @FXML
    private TableColumn<AidatGeliri, Integer> binaNoColumn;
    @FXML
    private TableColumn<AidatGeliri, String> daireNoColumn;
    @FXML
    private TableColumn<AidatGeliri, String> tarihColumn;
    @FXML
    private TableColumn<AidatGeliri, Double> miktarColumn;
    @FXML
    private TableView<GiderVerisi> Gider_table;
    @FXML
    private TableColumn<GiderVerisi, Integer> gididColumn;
    @FXML
    private TableColumn<GiderVerisi, Integer> gidbinaNoColumn;
    @FXML
    private TableColumn<GiderVerisi, String> gidtarihColumn;
    @FXML
    private TableColumn<GiderVerisi, String> turColumn;
    @FXML
    private TableColumn<GiderVerisi, Object> gidmiktarColumn;
    @FXML
    private TableColumn<GiderVerisi, Object> giddekontColumn;
    @FXML
    public void initialize() {

        binaNoColumn.setCellValueFactory(new PropertyValueFactory<>("binaNo"));
        daireNoColumn.setCellValueFactory(new PropertyValueFactory<>("daireNo"));
        tarihColumn.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        miktarColumn.setCellValueFactory(new PropertyValueFactory<>("miktar"));

        gididColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        gidbinaNoColumn.setCellValueFactory(new PropertyValueFactory<>("binaNo"));
        gidtarihColumn.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        turColumn.setCellValueFactory(new PropertyValueFactory<>("giderturu"));
        gidmiktarColumn.setCellValueFactory(new PropertyValueFactory<>("miktar"));
        giddekontColumn.setCellValueFactory(new PropertyValueFactory<>("dekont"));
        butceyaz();
        
        aidatyaz();
        gelirlerdoldur();
        GidertablosunuDoldur();
        
        dekontID.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 0));
        
    }
    private int aidat;
    @FXML
    private void aidatyaz() {
        SQLHelper dbhelper = new SQLHelper();

        String sql = "SELECT aidat FROM yötici_kayitlari_table WHERE Bina_No = ?";

        try (ResultSet rs = dbhelper.executeQuery(sql, PrimaryController.bina_no)) {

            if (rs.next()) {
                aidat = rs.getInt("aidat");
                
                aidat3.setText("Şimdiki Aidat: " + aidat);
            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        }
    }
    @FXML
    private void butceyaz() {
        SQLHelper dbhelper = new SQLHelper();

        String sql = "SELECT sum(miktar) toplam FROM aidat_gelirleri_table WHERE Bina_No = ?";

        int toplam = 0;
        try (ResultSet rs = dbhelper.executeQuery(sql, PrimaryController.bina_no)) {

            if (rs.next()) {
                toplam = rs.getInt("toplam");

            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        }
        String sql2 = "SELECT sum(miktar) toplam FROM Bina_Giderleri_table WHERE Bina_No = ?";

        int toplam2;
        try (ResultSet rs = dbhelper.executeQuery(sql2, PrimaryController.bina_no)) {

            if (rs.next()) {
                toplam2 = rs.getInt("toplam");

                butce1.setText("Bütçe: " + (toplam - toplam2));
                
            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        }

    }
    @FXML
    private void idlidekont() {
        SQLHelper dphelper = new SQLHelper();

        String selectSQL = "SELECT dekont FROM Bina_Giderleri_table WHERE id = ? AND Bina_no = ?";
        try (ResultSet rs = dphelper.executeQuery(selectSQL, dekontID.getValue(), PrimaryController.bina_no)) {
            if (rs.next()) {
                // Veriyi alıyoruz
                byte[] imageBytes = rs.getBytes("dekont");

                if (imageBytes != null && imageBytes.length > 0) {
                    try (InputStream in = new ByteArrayInputStream(imageBytes)) {
                        // Resim dosyasını JavaFX Image nesnesi ile yüklüyoruz
                        Image image = new Image(in);

                        // Resmi göstermek için bir ImageView oluşturuyoruz
                        ImageView imageView = new ImageView(image);

                        // Ekran boyutlarını alıyoruz
                        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
                        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

                        // Resmin boyutunu ekran boyutuna göre sınırlıyoruz
                        imageView.setPreserveRatio(true);  // Orantılı boyutlandırma
                        imageView.setFitWidth(screenWidth * 0.9); // Ekranın %90'ına kadar sığdırıyoruz
                        imageView.setFitHeight(screenHeight * 0.9); // Ekranın %90'ına kadar sığdırıyoruz

                        // Yeni bir StackPane oluşturup resmi içine ekliyoruz
                        StackPane root = new StackPane(imageView);

                        // Yeni bir Stage oluşturuyoruz (Açılan pencere)
                        Stage imageStage = new Stage();
                        imageStage.setTitle("Resim Görüntüleyici");
                        imageStage.setScene(new Scene(root));

                        // Stage'i ekrana ortalıyoruz
                        imageStage.show();

                    } catch (Exception e) {
                        // Hata durumunda bir uyarı gösteriyoruz
                        showAlert(Alert.AlertType.ERROR, "Hata", "Resim Yüklenemedi",
                                "Resim dosyası yüklenemedi. Lütfen geçerli bir dosya seçin.\n" + e.getMessage());
                    }
                } else {
                    showAlert(Alert.AlertType.WARNING, "Uyarı", "Dekont Bulunamadı",
                            "Seçilen ID için dekont bulunamadı.");
                }
            } else {
                dekontgoruntule_uyari.setText("Yanlış ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Veritabanı Hatası", "Sorgu İşlemi Başarısız",
                    "Veritabanına erişim sırasında bir hata oluştu.\n" + e.getMessage());
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void GidertablosunuDoldur() {
        SQLHelper dbhelper = new SQLHelper();
        String sql = "SELECT id,Bina_no, tarih, Gidar_Türü, miktar, dekont FROM Bina_Giderleri_table where Bina_no=?";
        ObservableList<GiderVerisi> gelirListesi = FXCollections.observableArrayList();

        try (ResultSet rs = dbhelper.executeQuery(sql, PrimaryController.bina_no)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int binaNo = rs.getInt("Bina_no");
                String tarih = rs.getString("tarih");
                String tur = rs.getString("Gidar_Türü");
                System.out.println(tur);
                Object miktar = rs.getObject("miktar");
                Object dekont = rs.getObject("dekont");

                // Listeye yeni satır ekle
                gelirListesi.add(new GiderVerisi(id, binaNo, tarih, tur, miktar, dekont));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tabloya verileri ekle
        Gider_table.setItems(gelirListesi);
    }
    public void gelirlerdoldur() {
        SQLHelper dbhelper = new SQLHelper();
        String sql = "SELECT bina_no, Daire_no, Tarih, miktar FROM aidat_gelirleri_table WHERE Bina_no=?";
        ObservableList<AidatGeliri> gelirListesi = FXCollections.observableArrayList();

        try (ResultSet rs = dbhelper.executeQuery(sql, PrimaryController.bina_no)) {
            while (rs.next()) {
                int binaNo = rs.getInt("bina_no");
                String daireNo = rs.getString("Daire_no");
                String tarih = rs.getString("Tarih");
                double miktar = rs.getDouble("miktar");

                // Listeye yeni satır ekle
                gelirListesi.add(new AidatGeliri(binaNo, daireNo, tarih, miktar));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tabloya verileri ekle
        Gelir_table.setItems(gelirListesi);
    }
}
