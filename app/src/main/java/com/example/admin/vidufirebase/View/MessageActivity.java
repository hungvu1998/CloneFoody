package com.example.admin.vidufirebase.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.admin.vidufirebase.Model.Mess;
import com.example.admin.vidufirebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MessageActivity extends AppCompatActivity {


    Button btnSendMess;
    EditText editText;
    FirebaseAuth firebaseAuth;
    Intent intent;
    ImageView imgtest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test_queue);
        MultiDex.install(this);
        /*btnSendMess=(Button)findViewById(R.id.btnSendMess);
        editText=(EditText)findViewById(R.id.edtTextSend);
        firebaseAuth = FirebaseAuth.getInstance();
        intent=getIntent();
         final FirebaseUser user=firebaseAuth.getCurrentUser();
        btnSendMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg =editText.getText().toString();

                if(!msg.equals("")){
                    sendMess(user.getUid(), msg);
                }
                editText.setText("");
            }
        });*/

    }
    private void sendMess(String nguoigui, String noidung){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        Mess mess=new Mess();
        Calendar calendar =Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss ");
        String giohientai =simpleDateFormat.format(calendar.getTime());

        mess.setNgaydang(giohientai);
        mess.setNguoigui(nguoigui);
        mess.setNoidung(noidung);
        reference.child("Chats").push().setValue(mess);

    }
}


