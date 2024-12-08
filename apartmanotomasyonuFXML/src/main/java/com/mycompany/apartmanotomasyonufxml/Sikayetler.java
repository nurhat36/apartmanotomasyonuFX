package com.mycompany.apartmanotomasyonufxml;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Sikayetler {
    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty binaNo;
    private final SimpleIntegerProperty daireNo;
    private final SimpleStringProperty tarih;
    private final SimpleStringProperty sikayet;


    private final SimpleStringProperty cozulmedurumu;
    private final SimpleObjectProperty<Object> dekont;

    public Sikayetler(int id, int binaNo,int daireNo, String tarih, String sikayet, String cozulmedurumu,Object dekont) {
        this.id = new SimpleIntegerProperty(id);
        this.binaNo = new SimpleIntegerProperty(binaNo);

        this.daireNo = new SimpleIntegerProperty(daireNo);
        this.tarih = new SimpleStringProperty(tarih);
        this.sikayet = new SimpleStringProperty(sikayet);
        this.cozulmedurumu = new SimpleStringProperty(cozulmedurumu);
        this.dekont = new SimpleObjectProperty<>(dekont);

    }

    // Getter Metotları
    public int getId() {
        return id.get();
    }

    public int getBinaNo() {
        return binaNo.get();
    }
    public int getdaireNo() {
        return daireNo.get();
    }

    public String getTarih() {
        return tarih.get();
    }

    public String getsikayet() { // İsimlendirme düzeltildi
        return sikayet.get();
    }

    public String getcozulmedurumu() {
        return cozulmedurumu.get();
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
    public SimpleIntegerProperty daireNoProperty() {
        return daireNo;
    }

    public SimpleStringProperty tarihProperty() {
        return tarih;
    }

    public SimpleStringProperty sikayetProperty() { // İsimlendirme düzeltildi
        return sikayet;
    }

    public SimpleStringProperty cozulmedurumuProperty() {
        return cozulmedurumu;
    }
    public SimpleObjectProperty<Object> dekontProperty() {
        return dekont;
    }


}
