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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
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
import static javafx.scene.effect.BlendMode.GREEN;
import static javafx.scene.effect.BlendMode.RED;
import static javafx.util.Duration.millis;

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

import javax.swing.*;

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
    private Label ust_label;
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
    private Button kull_ekr_dekont_gör_Button;
    @FXML
    private Button grafikgit;
    @FXML
    private Button sikayet_resim_yukle;
    @FXML
    private Button sikayet_resim_goruntule;
    @FXML
    private Button secilen_resim_gor;
    @FXML
    private Button sikayet_onayla;
    @FXML
    private TextArea sikayet_aciklamasi;
    @FXML
    private Spinner<Integer> secilen_resim_spinner;
    @FXML
    private Label secilen_resim_url_lbl;
    @FXML
    private Label uyarı_lbl;
    @FXML
    private Label ID_uyarı_lbl;
    @FXML
    private String tarih;
    private String resimyolu;
    @FXML
    private DatePicker sikayet_tarihi;
    @FXML
    private TableView<Sikayetler> sikayet_table;
    @FXML
    private TableColumn<Sikayetler, Integer> sikayetidColumn;
    @FXML
    private TableColumn<Sikayetler, Integer> sikayetbinaNoColumn;
    @FXML
    private TableColumn<Sikayetler, Integer> sikayetdaireNoColumn;
    @FXML
    private TableColumn<Sikayetler, String> sikayettarihColumn;
    @FXML
    private TableColumn<Sikayetler, String> sikayetturColumn;
    @FXML
    private TableColumn<Sikayetler, Object> cozulmedurumuColumn;
    @FXML
    private TableColumn<Sikayetler, Object> sikayetresimColumn;

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

        sikayetidColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        sikayetbinaNoColumn.setCellValueFactory(new PropertyValueFactory<>("binaNo"));
        sikayetdaireNoColumn.setCellValueFactory(new PropertyValueFactory<>("daireNo"));
        sikayettarihColumn.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        sikayetturColumn.setCellValueFactory(new PropertyValueFactory<>("sikayet"));
        cozulmedurumuColumn.setCellValueFactory(new PropertyValueFactory<>("cozulmedurumu"));
        sikayetresimColumn.setCellValueFactory(new PropertyValueFactory<>("dekont"));
        butceyaz();
        sikayettablosunuDoldur();

        aidatyaz();
        gelirlerdoldur();
        GidertablosunuDoldur();
        kull_ekr_ust_lbl1();
        
        dekontID.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 0));
        secilen_resim_spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000, 0));

        addHoverEffect(kull_ekr_dekont_gör_Button);
        addHoverEffect(grafikgit);
        addHoverEffect(sikayet_resim_yukle);
        addHoverEffect(sikayet_resim_goruntule);
        addHoverEffect(secilen_resim_gor);
        addHoverEffect(sikayet_onayla);
    }
    private void sikayettablosunuDoldur() {
        SQLHelper dbhelper = new SQLHelper();
        String sql = "SELECT ID,bina_no,daire_no, tarih, sikayet, cozulme_durumu, sikayet_resmi FROM sikayet_table where bina_no=?";
        ObservableList<Sikayetler> sikayetListesi = FXCollections.observableArrayList();

        try (ResultSet rs = dbhelper.executeQuery(sql, PrimaryController.bina_no)) {
            while (rs.next()) {
                int id = rs.getInt("ID");
                int binaNo = rs.getInt("bina_no");
                int daireNo = rs.getInt("daire_no");
                String tarih = rs.getString("tarih");
                String tur = rs.getString("sikayet");
                System.out.println(tur);
                String cozulme_durumu = rs.getString("cozulme_durumu");
                Object sikayet_resmi = rs.getObject("sikayet_resmi");

                // Listeye yeni satır ekle
                sikayetListesi.add(new Sikayetler(id, binaNo,daireNo, tarih, tur, cozulme_durumu, sikayet_resmi));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tabloya verileri ekle
        sikayet_table.setItems(sikayetListesi);
        cozulmedurumuColumn.setCellFactory(column -> {
            return new TableCell<Sikayetler, Object>() {
                @Override
                protected void updateItem(Object item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                        return;
                    }

                    setText(item.toString());
                    String cozulmeDurumu = item.toString().toLowerCase();
                    switch (cozulmeDurumu) {
                        case "çözülmedi":
                            setStyle("-fx-background-color: red; -fx-text-fill: white;");
                            break;
                        case "çözüldü":
                            setStyle("-fx-background-color: green; -fx-text-fill: white;");
                            break;
                        case "çözülmeye çalışılıyor":
                            setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
                            break;
                        default:
                            setStyle("");
                            break;
                    }
                }
            };
        });
    }
    @FXML
    private void sikayetisqlegonder() {
        SQLHelper dbhelper = new SQLHelper();
        String insertSQL = "INSERT INTO sikayet_table (bina_no,daire_no, tarih, sikayet, cozulme_durumu, sikayet_resmi) VALUES (?, ?, ?, ?, ?,?)";
        Alert alert = new Alert(AlertType.CONFIRMATION);

        String cozulme_durumu="Çözülmedi";

        alert.setTitle(" Şikayet Uyarısı");
        alert.setHeaderText("Bu Şikayeti onaylıyor musunuz" +
                "!");
        alert.setContentText(String.format("Yapılan şikayet: %s TL\n " +
                "Bu şikayeti onaylıyormusunuz?", sikayet_aciklamasi.getText()));

        // "Yes" ve "No" butonları
        ButtonType yesButton = new ButtonType("Evet");
        ButtonType noButton = new ButtonType("Hayır");
        alert.getButtonTypes().setAll(yesButton, noButton);

        // Kullanıcı seçimini al
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == yesButton) {
            try (FileInputStream fis = new FileInputStream(new File(resimyolu))) {
                int fileLength = (int) new File(resimyolu).length();

                // Veritabanına ekleme işlemi
                int result1 = dbhelper.executeUpdatesikayet(insertSQL, PrimaryController.bina_no, PrimaryController.daire_no,sikayet_tarihi.getValue(), sikayet_aciklamasi.getText(), cozulme_durumu, fis, fileLength);

                if (result1 > 0) {
                    System.out.println("Veri başarıyla eklendi.");
                    uyarı_lbl.setText("Kayıt başarıyla eklendi!");
                } else {
                    uyarı_lbl.setText("Veri ekleme başarısız.");
                    System.err.println("Veri ekleme başarısız.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dbhelper.close();
            }
            GidertablosunuDoldur();
            secilen_resim_url_lbl.setText(null);
        }else{
            System.out.println("onaylanmadı");

        }
        sikayettablosunuDoldur();




    }

    public void sikayetac() {
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
    private void sikayet_resmi_yukleme() throws IOException {
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
            secilen_resim_url_lbl.setText(resimyolu);
            sikayetac();

            // Gerekirse bir etiket veya alan içinde yolu göster
            // Örnek: bir TextField'e yazdırabilirsiniz
            // diger_gider_tf.setText(resimyolu);
        } else {
            System.out.println("Hiçbir dosya seçilmedi.");
        }
    }
    @FXML
    private void idlisikayet() {
        SQLHelper dphelper = new SQLHelper();

        String selectSQL = "SELECT sikayet_resmi FROM sikayet_table WHERE ID = ? AND bina_no = ?";
        try (ResultSet rs = dphelper.executeQuery(selectSQL, secilen_resim_spinner.getValue(), PrimaryController.bina_no)) {
            if (rs.next()) {
                // Veriyi alıyoruz
                byte[] imageBytes = rs.getBytes("sikayet_resmi");

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
                ID_uyarı_lbl.setText("Yanlış ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Veritabanı Hatası", "Sorgu İşlemi Başarısız",
                    "Veritabanına erişim sırasında bir hata oluştu.\n" + e.getMessage());
        }
    }
    @FXML
    private void grafikgit() throws IOException {
        App.setRoot("grafikler");
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
    
    private void kull_ekr_ust_lbl1() {
        // Son ödeme tarihini kontrol et
        LocalDate ayinIlkGunu = LocalDate.now().withDayOfMonth(1);

        System.out.println("Bu ayın ilk günü: " + ayinIlkGunu);
        String tarihstr = this.tarih;

        try {
            // String tarih bilgisini LocalDate'e çevirin
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate tarih1 = LocalDate.parse(tarihstr, formatter);
            System.out.println(tarih1+"  "+ayinIlkGunu);

            // Tarihi kontrol edin
            if (tarih1.isBefore(ayinIlkGunu)) {
                // Ödeme yapılmamış veya geç yapılmışsa kırmızı
                ust_label.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

                ust_label.setText("Ödeme yapılmadı veya geç yapıldı");
            } else {
                // Ödeme zamanında yapılmışsa yeşil
                ust_label.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                ust_label.setText("Ödeme yapıldı");
            }
        } catch (DateTimeParseException e) {
            // Geçersiz tarih formatı durumunda hata mesajı
            ust_label.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            ust_label.setText("Geçersiz tarih formatı");
            System.err.println("Tarih formatı hatalı: " + e.getMessage());
        } catch (NullPointerException e) {
            ust_label.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            ust_label.setText("Bu zamana kadar hiçbir aidat ödemediniz!");
        }
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
                if(toplam - toplam2<0){
                    butce1.setText("Bütçe: " + (toplam - toplam2));
                    butce1.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                    
                }else{
                    butce1.setText("Bütçe: " + (toplam - toplam2));
                    butce1.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                }

                
                
                
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
    public static ObservableList<GiderVerisi> gelirListesi = FXCollections.observableArrayList();

    private void GidertablosunuDoldur() {
        SQLHelper dbhelper = new SQLHelper();
        String sql = "SELECT id,Bina_no, tarih, Gidar_Türü, miktar, dekont FROM Bina_Giderleri_table where Bina_no=?";


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
        String sql = "SELECT bina_no, Daire_no, Tarih, miktar FROM aidat_gelirleri_table WHERE Bina_no=? and Daire_no=?";
        ObservableList<AidatGeliri> gelirListesi = FXCollections.observableArrayList();

        try (ResultSet rs = dbhelper.executeQuery(sql, PrimaryController.bina_no,PrimaryController.daire_no)) {
            while (rs.next()) {
                int binaNo = rs.getInt("bina_no");
                String daireNo = rs.getString("Daire_no");
                String tarih = rs.getString("Tarih");
                System.out.println(tarih);
                this.tarih=tarih;
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
