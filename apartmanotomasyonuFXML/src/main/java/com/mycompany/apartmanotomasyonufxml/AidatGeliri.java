/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.apartmanotomasyonufxml;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Nurhat
 */
public class AidatGeliri {
    private final SimpleIntegerProperty binaNo;
    private final SimpleStringProperty daireNo;
    private final SimpleStringProperty tarih;
    private final SimpleDoubleProperty miktar;

    public AidatGeliri(int binaNo, String daireNo, String tarih, double miktar) {
        this.binaNo = new SimpleIntegerProperty(binaNo);
        this.daireNo = new SimpleStringProperty(daireNo);
        this.tarih = new SimpleStringProperty(tarih);
        this.miktar = new SimpleDoubleProperty(miktar);
    }

    public int getBinaNo() { return binaNo.get(); }
    public String getDaireNo() { return daireNo.get(); }
    public String getTarih() { return tarih.get(); }
    public double getMiktar() { return miktar.get(); }

    public SimpleIntegerProperty binaNoProperty() { return binaNo; }
    public SimpleStringProperty daireNoProperty() { return daireNo; }
    public SimpleStringProperty tarihProperty() { return tarih; }
    public SimpleDoubleProperty miktarProperty() { return miktar; }
}
