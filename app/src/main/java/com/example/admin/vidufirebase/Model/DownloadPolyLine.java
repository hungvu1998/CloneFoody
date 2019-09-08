package com.example.admin.vidufirebase.Model;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DownloadPolyLine extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {
        StringBuffer stringBuffer=new StringBuffer();
        try {
            URL url=new URL(params[0]);//chuyền đường dẫn
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();//mở kết nối
            connection.connect();//thực hiến kết nối (enter)

            InputStream inputStream=connection.getInputStream();//lấy dữ liệu
            InputStreamReader reader= new InputStreamReader(inputStream);//đọc
            BufferedReader bufferedReader =new BufferedReader(reader);//vẫn là đọc
            // Đã đọc đc nọi dung trang web


            String line="";
            while ((line = bufferedReader.readLine())!=null){//khi đọc hết
                stringBuffer.append(line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuffer.toString();
    }

    @Override
    protected void onPostExecute(String dataJSon) {
        super.onPostExecute(dataJSon);
    }
}
