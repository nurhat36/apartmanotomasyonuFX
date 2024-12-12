package com.mycompany.apartmanotomasyonufxml;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.*;
import java.util.stream.Collectors;

public class Grafikler {

    @FXML
    private LineChart<String, Number> grafik;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    public void initialize() {
        // Veri serisi oluştur
        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName("Giderler");

        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.setName("Gider Türleri");
        List<GiderVerisi> giderListesi;
        if (SecondaryController.isactivyoneyici) {
            giderListesi = SecondaryController.gelirListesi;
        } else {
            giderListesi = kullaniciekraniController.gelirListesi;
        }


        // Aynı tarihe sahip giderlerin toplamını hesapla
        Map<String, Double> tarihToplamMap = new HashMap<>();
        // Aynı gider türüne sahip giderlerin toplamını hesapla
        Map<String, Double> giderTuruToplamMap = new HashMap<>();

        for (GiderVerisi gider : giderListesi) {
            String tarih = gider.getTarih();
            String giderTuru = gider.getGiderturu();
            Object miktarObj = gider.getMiktar();
            double miktar = 0;

            // Miktar tipini kontrol et, Double olmalı
            if (miktarObj instanceof Double) {
                miktar = (Double) miktarObj;
            } else if (miktarObj instanceof Integer) {
                miktar = (Integer) miktarObj;
            }

            // Tarihe göre gider toplamını hesapla
            tarihToplamMap.put(tarih, tarihToplamMap.getOrDefault(tarih, 0.0) + miktar);

            // Gider türüne göre gider toplamını hesapla
            giderTuruToplamMap.put(giderTuru, giderTuruToplamMap.getOrDefault(giderTuru, 0.0) + miktar);
        }

        // Tarihe göre gider toplamlarını line chart'a ekle
        for (Map.Entry<String, Double> entry : tarihToplamMap.entrySet()) {
            String tarih = entry.getKey();
            Double toplamMiktar = entry.getValue();
            lineSeries.getData().add(new XYChart.Data<>(tarih, toplamMiktar));
        }

        // Gider türüne göre gider toplamlarını bar chart'a ekle
        for (Map.Entry<String, Double> entry : giderTuruToplamMap.entrySet()) {
            String giderTuru = entry.getKey();
            Double toplamMiktar = entry.getValue();
            barSeries.getData().add(new XYChart.Data<>(giderTuru, toplamMiktar));
        }

        // Serileri grafiğe ekle
        grafik.getData().add(lineSeries);
        barChart.getData().add(barSeries);
    }

}
