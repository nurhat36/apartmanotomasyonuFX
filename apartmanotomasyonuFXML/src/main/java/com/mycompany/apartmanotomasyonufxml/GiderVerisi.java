package com.mycompany.apartmanotomasyonufxml;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class GiderVerisi {
    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty binaNo;
    private final SimpleStringProperty tarih;
    private final SimpleStringProperty giderturu;
    private final SimpleObjectProperty<Object> miktar;
    private final SimpleObjectProperty<Object> dekont;

    public GiderVerisi(int id, int binaNo, String tarih, String giderturu, Object miktar, Object dekont) {
        this.id = new SimpleIntegerProperty(id);
        this.binaNo = new SimpleIntegerProperty(binaNo);
        this.tarih = new SimpleStringProperty(tarih);
        this.giderturu = new SimpleStringProperty(giderturu);
        this.miktar = new SimpleObjectProperty<>(miktar);
        this.dekont = new SimpleObjectProperty<>(dekont);
    }

    // Getter Metotları
    public int getId() {
        return id.get();
    }

    public int getBinaNo() {
        return binaNo.get();
    }

    public String getTarih() {
        return tarih.get();
    }

    public String getGiderturu() { // İsimlendirme düzeltildi
        return giderturu.get();
    }

    public Object getMiktar() {
        return miktar.get();
    }

    public Object getDekont() {
        return dekont.get();
    }

    // Property Metotları
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public SimpleIntegerProperty binaNoProperty() {
        return binaNo;
    }

    public SimpleStringProperty tarihProperty() {
        return tarih;
    }

    public SimpleStringProperty giderturuProperty() { // İsimlendirme düzeltildi
        return giderturu;
    }

    public SimpleObjectProperty<Object> miktarProperty() {
        return miktar;
    }

    public SimpleObjectProperty<Object> dekontProperty() {
        return dekont;
    }
}