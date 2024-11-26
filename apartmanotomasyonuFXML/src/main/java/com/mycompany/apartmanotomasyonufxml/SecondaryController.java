package com.mycompany.apartmanotomasyonufxml;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
    private Label aidat3;
    @FXML
    private Spinner<Intager> aidatmiktari;
    @FXML
    private ComboBox daire_nocmb;
    @FXML
    private TableView<AidatGeliri> Gelir_table;
    private int aidat;
    @FXML
    private TableColumn<AidatGeliri, Integer> binaNoColumn;
    @FXML
    private TableColumn<AidatGeliri, String> daireNoColumn;
    @FXML
    private TableColumn<AidatGeliri, String> tarihColumn;
    @FXML
    private TableColumn<AidatGeliri, Double> miktarColumn;

    @FXML
    public void initialize(){
         
         binaNoColumn.setCellValueFactory(new PropertyValueFactory<>("binaNo"));
        daireNoColumn.setCellValueFactory(new PropertyValueFactory<>("daireNo"));
        tarihColumn.setCellValueFactory(new PropertyValueFactory<>("tarih"));
        miktarColumn.setCellValueFactory(new PropertyValueFactory<>("miktar"));
        butceyaz();
        cmbdolur();
        aidatyaz();
        gelirlerdoldur();
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
    private void aidat_geliri(){
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

                butce1.setText("Bütçe: " + (toplam - toplam2));
                butce2.setText("Bütçe: " + (toplam - toplam2));
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