package com.mycompany.apartmanotomasyonufxml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import static javafx.util.Duration.millis;

public class SecondaryController {

    @FXML
    private CheckBox jCheckBox1;
    @FXML
    private CheckBox jCheckBox2;
    @FXML
    private CheckBox jCheckBox3;
    @FXML
    private CheckBox jCheckBox4;
    @FXML
    private CheckBox jCheckBox5;
    @FXML
    private CheckBox jCheckBox6;
    @FXML
    private TextField diger_gider_tf;
    @FXML
    private Label butce1;
    @FXML
    private Label gelir_on_hata_msj;
    @FXML
    private Label butce2;
    @FXML
    private Label aidat1;
    @FXML
    private Label aidat2;
    @FXML
    private DatePicker gelirtarih;
    @FXML
    private DatePicker gidertarih;
    @FXML
    private Label aidat3;
    @FXML
    private Label dekontyolu;
    @FXML
    private Label aidatbelirlemebilgi;
    @FXML
    private Spinner<Integer> aidatmiktari;
    @FXML
    private Spinner<Integer> gidermiktari;
    @FXML
    private Spinner<Integer> dekontID;
    @FXML
    private Spinner<Integer> aidatbelirle;

    @FXML
    private ComboBox daire_nocmb;
    private int aidat;
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
    private Label gider_bilgi_goster;
    @FXML
    private Button sec_gel_onayla_Button;
    @FXML
    private Button sec_gid_dek_yük_Button;
    @FXML
    private Button sec_gid_gör_Button;
    @FXML
    private Button sec_gid_onayla_Button;
    @FXML
    private Button sec_gid_grafik_Button;
    @FXML
    private Button sec_gid_dek_gör_Button;
    @FXML
    private Button sec_aidat_bel_onayla_Button;

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
        cmbdolur();
        aidatyaz();
        gelirlerdoldur();
        GidertablosunuDoldur();
        aidatmiktari.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, aidat));
        gidermiktari.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 500));
        dekontID.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 0));
        aidatbelirle.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, aidat));

        addHoverEffect(sec_gel_onayla_Button);
        addHoverEffect(sec_gid_dek_yük_Button);
        addHoverEffect(sec_gid_gör_Button);
        addHoverEffect(sec_gid_onayla_Button);
        addHoverEffect(sec_gid_grafik_Button);
        addHoverEffect(sec_gid_dek_gör_Button);
        addHoverEffect(sec_aidat_bel_onayla_Button);

    }
    private void addHoverEffect(Control control) {
        control.setOnMouseEntered(event -> applyscaletransition(control,1.0,1.1));
        control.setOnMouseExited(event -> applyscaletransition(control,1.1,1.0));

    }
    private void applyscaletransition(Control control,double scale,double toscale) {
        ScaleTransition scaleTransition = new ScaleTransition(millis(200),control);
        scaleTransition.setFromX(scale);
        scaleTransition.setFromY(scale);
        scaleTransition.setToX(toscale);
        scaleTransition.setToY(toscale);
        scaleTransition.play();

    }
    @FXML
    private void grafikgit() throws IOException {
        App.setRoot("grafikler");
    }
    @FXML
    private void Aidatonayla() {                                                
        SQLHelper sql = new SQLHelper();
        String insertSQL = "UPDATE yötici_kayitlari_table SET aidat = ? WHERE Bina_No = ?";

        // Veritabanına ekleme işlemi
        int result = sql.executeUpdate(insertSQL, aidatbelirle.getValue(), PrimaryController.bina_no);
        if (result > 0) {
            aidatbelirlemebilgi.setText("aidat başarıyla güncellendi.");
            aidat=aidatbelirle.getValue();
            aidatmiktari.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, aidat));
            System.out.println("Veri başarıyla eklendi.");
        } else {
            System.err.println("Veri ekleme başarısız.");
        }
        sql.close();

        if (result > 0) {
            System.out.println("Kayıt başarıyla eklendi!");

        }
        aidatyaz();

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
                gider_bilgi_goster.setText("Yanlış ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Veritabanı Hatası", "Sorgu İşlemi Başarısız",
                    "Veritabanına erişim sırasında bir hata oluştu.\n" + e.getMessage());
        }
    }

// Uyarı göstermek için yardımcı bir metot
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static ObservableList<GiderVerisi> gelirListesi = FXCollections.observableArrayList();

    private void GidertablosunuDoldur() {
        SQLHelper dbhelper = new SQLHelper();
        String sql = "SELECT id,Bina_no, tarih, Gidar_Türü, miktar, dekont FROM Bina_Giderleri_table where Bina_no=?";
        gelirListesi.clear();

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

    private void giderisqlegonder(String Gider) {
        SQLHelper dbhelper = new SQLHelper();
        String insertSQL = "INSERT INTO Bina_Giderleri_table (Bina_no, tarih, Gidar_Türü, miktar, dekont) VALUES (?, ?, ?, ?, ?)";

        try (FileInputStream fis = new FileInputStream(new File(resimyolu))) {
            int fileLength = (int) new File(resimyolu).length();

            // Veritabanına ekleme işlemi
            int result = dbhelper.executeUpdateresim(insertSQL, PrimaryController.bina_no, gidertarih.getValue(), Gider, gidermiktari.getValue(), fis, fileLength);

            if (result > 0) {
                System.out.println("Veri başarıyla eklendi.");
                gider_bilgi_goster.setText("Kayıt başarıyla eklendi!");
            } else {
                gider_bilgi_goster.setText("Veri ekleme başarısız.");
                System.err.println("Veri ekleme başarısız.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbhelper.close();
        }
        GidertablosunuDoldur();

    }

    @FXML
    private void giderOnayla() {
        if (jCheckBox1.isSelected()) {
            giderisqlegonder("Elektrik");
        } else if (jCheckBox2.isSelected()) {
            giderisqlegonder("Su");
        } else if (jCheckBox3.isSelected()) {
            giderisqlegonder("Doğalgaz");
        } else if (jCheckBox4.isSelected()) {
            giderisqlegonder("Bahçıvan");
        } else if (jCheckBox5.isSelected()) {
            giderisqlegonder("Asansör Bakımı");
        } else if (jCheckBox6.isSelected()) {
            if (diger_gider_tf.getText().isEmpty()) {
                gider_bilgi_goster.setText("Lütfen giderin açıklamasını girin!");

            } else {
                giderisqlegonder(diger_gider_tf.getText());

            }

        } else {
            gider_bilgi_goster.setText("Lütfen bir gider türü seçin!");
        }
        butceyaz();

    }
    private String resimyolu;

    public void dekontuac() {
        try {
            // Resim dosyasını JavaFX Image nesnesiyle okuma
            File imageFile = new File(resimyolu);
            if (!imageFile.exists()) {
                throw new IOException("Resim dosyası bulunamadı.");
            }

            // JavaFX Image sınıfıyla resmi yükleyelim
            Image image = new Image(imageFile.toURI().toString());

            // Resmi göstermek için bir ImageView oluşturuyoruz
            ImageView imageView = new ImageView(image);

            // Ekran boyutlarını alıyoruz
            double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
            double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

            // Resmin boyutunu ekran boyutuna göre sınırlıyoruz
            imageView.setPreserveRatio(true);  // Orantılı boyutlandırma
            imageView.setFitWidth(screenWidth * 0.9); // Ekranın %90'ine kadar sığdırıyoruz
            imageView.setFitHeight(screenHeight * 0.9); // Ekranın %90'ine kadar sığdırıyoruz

            // Yeni bir StackPane oluşturup resmi içine ekliyoruz
            StackPane root = new StackPane();
            root.getChildren().add(imageView);

            // Yeni bir Stage oluşturuyoruz (Açılan pencere)
            Stage imageStage = new Stage();
            imageStage.setTitle("Resim Görüntüleyici");
            imageStage.setScene(new Scene(root));

            // Stage'i ekrana ortalıyoruz
            imageStage.show();

        } catch (Exception e) {
            // Hata durumunda bir uyarı gösteriyoruz
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText("Resim Yüklenemedi");
            alert.setContentText("Resim dosyası yüklenemedi. Lütfen geçerli bir dosya seçin.\n" + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void dekontyukleme() throws IOException {
        // FileChooser oluştur
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Dekont Yükle");

        // Resim dosyaları için filtre ekle
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Resim Dosyaları", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"),
                new FileChooser.ExtensionFilter("Tüm Dosyalar", "*.*")
        );

        // FileChooser'ı göster ve dosya seç
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Seçilen dosyanın yolunu kontrol et ve işle
        if (selectedFile != null) {
            resimyolu = selectedFile.getAbsolutePath();

            System.out.println("Seçilen resim yolu: " + resimyolu);
            dekontyolu.setText(resimyolu);
            dekontuac();

            // Gerekirse bir etiket veya alan içinde yolu göster
            // Örnek: bir TextField'e yazdırabilirsiniz
            // diger_gider_tf.setText(resimyolu);
        } else {
            System.out.println("Hiçbir dosya seçilmedi.");
        }
    }

    private void cmbdolur() {

        // SQL bağlantı bilgileri
        SQLHelper dbhelper = new SQLHelper();

        String sql = "SELECT Daire_Sayısı FROM yötici_kayitlari_table WHERE Bina_No = ?";
        daire_nocmb.getItems().clear();
        try (ResultSet rs = dbhelper.executeQuery(sql, PrimaryController.bina_no)) {

            if (rs.next()) {
                int daireSayisi = rs.getInt("Daire_Sayısı");
                System.err.println(daireSayisi);

                // Şimdi daire numaralarını ComboBox'a ekleyelim
                ObservableList<String> daireListesi = FXCollections.observableArrayList();
                for (int i = 1; i <= daireSayisi; i++) {
                    daireListesi.add("Daire No: " + i);
                }
                daire_nocmb.setItems(daireListesi);
            } else {
                daire_nocmb.getItems().clear();
            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        }

    }

    @FXML
    private void aidat_geliri() {
        SQLHelper dbhelper = new SQLHelper();
        String insertSQL = "INSERT INTO aidat_gelirleri_table (bina_No, Daire_No,Tarih,miktar) VALUES (?, ?, ?,?)";
        String secilenVeri = (String) daire_nocmb.getValue();
        int index = secilenVeri.indexOf(": ");
        String daireNoStr = secilenVeri.substring(index + 2);

        // Veritabanına ekleme işlemi
        int result = dbhelper.executeUpdate(insertSQL, PrimaryController.bina_no, daireNoStr, gelirtarih.getValue(), aidatmiktari.getValue());
        if (result > 0) {
            System.out.println("Veri başarıyla eklendi." + result);
        } else {
            gelir_on_hata_msj.setText("Veri ekleme başarısız.");
            System.err.println("Veri ekleme başarısız.");
        }
        dbhelper.close();

        if (result > 0) {
            System.out.println("Kayıt başarıyla eklendi!");
            gelir_on_hata_msj.setText("Kayıt başarıyla eklendi!");
        }
        gelirlerdoldur();
        butceyaz();
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

    @FXML
    private void aidatyaz() {
        SQLHelper dbhelper = new SQLHelper();

        String sql = "SELECT aidat FROM yötici_kayitlari_table WHERE Bina_No = ?";

        try (ResultSet rs = dbhelper.executeQuery(sql, PrimaryController.bina_no)) {

            if (rs.next()) {
                aidat = rs.getInt("aidat");
                aidat1.setText("Aidat: " + aidat);
                aidat2.setText("Aidat: " + aidat);
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
                if (toplam - toplam2<0){
                butce1.setText("Bütçe: " + (toplam - toplam2));
                butce2.setText("Bütçe: " + (toplam - toplam2));
                
                butce1.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                butce2.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                else
                {
                butce1.setText("Bütçe: " + (toplam - toplam2));
                butce2.setText("Bütçe: " + (toplam - toplam2));
                
                butce1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                butce2.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                
            }
        } catch (SQLException e) {
            System.err.println("Veri çekme hatası: " + e.getMessage());
        }

    }

    @FXML
    private void checkbox1() throws IOException {
        if (jCheckBox1.isSelected()) {
            jCheckBox2.setSelected(false);
            jCheckBox3.setSelected(false);
            jCheckBox4.setSelected(false);
            jCheckBox5.setSelected(false);
            jCheckBox6.setSelected(false);

            diger_gider_tf.setDisable(true);
        }
    }

    @FXML
    private void checkbox2() throws IOException {
        if (jCheckBox2.isSelected()) {
            jCheckBox1.setSelected(false);
            jCheckBox3.setSelected(false);
            jCheckBox4.setSelected(false);
            jCheckBox5.setSelected(false);
            jCheckBox6.setSelected(false);

            diger_gider_tf.setDisable(true);
        }
    }

    @FXML
    private void checkbox3() throws IOException {
        if (jCheckBox3.isSelected()) {
            jCheckBox2.setSelected(false);
            jCheckBox1.setSelected(false);
            jCheckBox4.setSelected(false);
            jCheckBox5.setSelected(false);
            jCheckBox6.setSelected(false);

            diger_gider_tf.setDisable(true);
        }
    }

    @FXML
    private void checkbox4() throws IOException {
        if (jCheckBox4.isSelected()) {
            jCheckBox2.setSelected(false);
            jCheckBox3.setSelected(false);
            jCheckBox1.setSelected(false);
            jCheckBox5.setSelected(false);
            jCheckBox6.setSelected(false);

            diger_gider_tf.setDisable(true);
        }
    }

    @FXML
    private void checkbox5() throws IOException {
        if (jCheckBox5.isSelected()) {
            jCheckBox2.setSelected(false);
            jCheckBox3.setSelected(false);
            jCheckBox4.setSelected(false);
            jCheckBox1.setSelected(false);
            jCheckBox6.setSelected(false);

            diger_gider_tf.setDisable(true);
        }
    }

    @FXML
    private void checkbox6() throws IOException {
        if (jCheckBox6.isSelected()) {
            jCheckBox2.setSelected(false);
            jCheckBox3.setSelected(false);
            jCheckBox4.setSelected(false);
            jCheckBox5.setSelected(false);
            jCheckBox1.setSelected(false);

            diger_gider_tf.setDisable(false);
        }
    }
}
