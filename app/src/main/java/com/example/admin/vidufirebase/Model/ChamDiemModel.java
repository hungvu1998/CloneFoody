package com.example.admin.vidufirebase.Model;

public class ChamDiemModel {
    double vitri;
    double khonggian;
    double giaca;
    double dichvu;
    double chatluong;
    public ChamDiemModel(){}
    public ChamDiemModel(double vitri,double giaca,double dichvu,double chatluong, double khonggian){
        this.vitri=vitri;
        this.giaca=giaca;
        this.khonggian=khonggian;
        this.chatluong=chatluong;
        this.dichvu=dichvu;
    }
    public double getVitri() {
        return vitri;
    }

    public void setVitri(double vitri) {
        this.vitri = vitri;
    }

    public double getKhonggian() {
        return khonggian;
    }

    public void setKhonggian(double khonggian) {
        this.khonggian = khonggian;
    }

    public double getGiaca() {
        return giaca;
    }

    public void setGiaca(double giaca) {
        this.giaca = giaca;
    }

    public double getDichvu() {
        return dichvu;
    }

    public void setDichvu(double dichvu) {
        this.dichvu = dichvu;
    }

    public double getChatluong() {
        return chatluong;
    }

    public void setChatluong(double chatluong) {
        this.chatluong = chatluong;
    }


}
