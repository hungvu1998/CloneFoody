package com.example.admin.vidufirebase.Controller;

import com.example.admin.vidufirebase.Model.ThanhVienModel;

public class ControllerDangKy {
    ThanhVienModel thanhVienModel;
    public  ControllerDangKy(){
        thanhVienModel=new ThanhVienModel();
    }
    public void ThemthongtinThanhVienController(ThanhVienModel thanhVienModell,String userID){
        thanhVienModel.ThemThongTinThanhVien(thanhVienModell,userID);
    }
}
