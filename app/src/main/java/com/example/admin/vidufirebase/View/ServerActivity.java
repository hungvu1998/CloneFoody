package com.example.admin.vidufirebase.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
//import android.support.multidex.MultiDex;
import android.support.multidex.MultiDex;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.vidufirebase.Controller.ControllerMess;
import com.example.admin.vidufirebase.Controller.ControllerPlace;
import com.example.admin.vidufirebase.R;

public class ServerActivity extends AppCompatActivity {
    int MAXSIZE;
    public final static int MINSIZE=0;
    private Context context;
    Button btnEnqueue,btnDequeue;
    LinearLayout queue;
    int head,tail;
    int test;
    ControllerMess controllerMess;

    //ListView lstTraLoi;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_server_queue);
        MultiDex.install(this);
        context=this;
        queue=(LinearLayout)findViewById(R.id.llqueue);
        btnEnqueue=(Button)findViewById(R.id.btnEnqueue) ;
        btnDequeue=(Button)findViewById(R.id.btnDequeue) ;

        //lstTraLoi=(ListView) findViewById(R.id.lstTraLoi);

       // controllerMess = new ControllerMess(this);


       // controllerMess.getDanhSachTraLoi(this,lstTraLoi);
        head=0;
        tail=0;
        test=0;

        btnEnqueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(head>=tail)
                {
                    test=0;
                }
                if((tail-head)<5){
                    enqueue();
                }
                else
                    Toast.makeText(context, "Queue is full", Toast.LENGTH_SHORT).show();
            }
        });
        btnDequeue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(head<tail){
                    dequeue();

                    btnEnqueue.setEnabled(false);
                }
                else
                    Toast.makeText(context, "Queue is empty", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public int convertDpToPixel(float dp){
        Resources resources=context.getResources();
        DisplayMetrics metrics=resources.getDisplayMetrics();
        float px=dp*(metrics.densityDpi/160f);
        return (int)px;
    }
    void enqueue(){
        TextView txtItem= new TextView(context);
        txtItem.setLayoutParams(new LinearLayout.LayoutParams(convertDpToPixel(50),convertDpToPixel(50)));
        txtItem.setText(String.valueOf(test+1));
        txtItem.setBackgroundResource(R.drawable.ve_hinh);
        txtItem.setTextColor(getResources().getColor(R.color.colorAccent));
        txtItem.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        txtItem.setGravity(Gravity.CENTER);
        queue.addView(txtItem);

        Animation animation=AnimationUtils.loadAnimation(getBaseContext(),R.anim.right_in);
        animation.setStartOffset(0);
        txtItem.startAnimation(animation);

        tail++;
        test++;

    }
    void dequeue(){
        View v=queue.getChildAt(head);

        Animation animation=AnimationUtils.loadAnimation(getBaseContext(),R.anim.left_out);
        animation.setStartOffset(0);
        v.startAnimation(animation);

        queue.removeViewAt(head);
        tail--;
    }

}
