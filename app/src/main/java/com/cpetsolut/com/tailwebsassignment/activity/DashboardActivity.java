package com.cpetsolut.com.tailwebsassignment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cpetsolut.com.tailwebsassignment.DBHelper.Controllerdb;
import com.cpetsolut.com.tailwebsassignment.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {

    private ListView detailsdata;
    private Button askQuestion;
    private Dialog QuestionDialog;
    private View questionView;
    private ImageView cancelDialog;
    private EditText name,subject,mark;
    private Button register;
    Controllerdb db =new Controllerdb(this);
    private Button detailssee;
    private FloatingActionButton askQuestion1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        detailsdata=(ListView) findViewById(R.id.forumlist);
        detailssee=(Button) findViewById(R.id.details);
        detailssee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayData();
            }
        });
        askQuestion1=(FloatingActionButton) findViewById(R.id.askQuestion1);
        askQuestion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDetails();
            }
        });
        askQuestion=(Button)findViewById(R.id.askQuestion);
        askQuestion.setOnClickListener(v->{
                AddDetails();
        });
    }

    private void AddDetails() {
        questionView= View.inflate(DashboardActivity.this,R.layout.add_details,null);
        questionView.startAnimation(AnimationUtils.loadAnimation(DashboardActivity.this,R.anim.zoom_in_enter));
        this.QuestionDialog=new Dialog(DashboardActivity.this,R.style.NewDialog);
        this.QuestionDialog.setContentView(questionView);
        this.QuestionDialog.setCancelable(true);
        this.QuestionDialog.show();

        Window window = this.QuestionDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER | Gravity.CENTER;
        window.setGravity(Gravity.CENTER);
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.dimAmount = 0.0f;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.windowAnimations = R.anim.slide_move;

        window.setAttributes(wlp);
        window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        name=(EditText)questionView.findViewById(R.id.name);
        subject=(EditText)questionView.findViewById(R.id.subject);
        mark=(EditText)questionView.findViewById(R.id.mark);
        register=(Button) questionView.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty()){
                    name.setError("Name");
                }else {
                    if (subject.getText().toString().isEmpty()){
                        subject.setError("Subject");
                    }else {
                        if (mark.getText().toString().isEmpty()){
                            mark.setError("Mark");
                        }else {
                            db = new Controllerdb(DashboardActivity.this);
                            ArrayList<HashMap<String, String>> sname=db.getname();
                            String nameo=sname.toString();
                            ArrayList<HashMap<String, String>> ssub=db.getsub();
                            String subo=ssub.toString();
                            if (name.getText().toString().equalsIgnoreCase(nameo) && subject.getText().toString().equalsIgnoreCase(subo)){
                                db.UpdateUserDetails(name.getText().toString(),subject.getText().toString(),mark.getText().toString());
                                displayData();
                            }else {
                                db.insertUserDetails(name.getText().toString(),subject.getText().toString(),mark.getText().toString());
                                displayData();
                            }
                            QuestionDialog.dismiss();
                        }
                    }
                }
            }
        });
    }
    private void displayData() {
        db = new Controllerdb(DashboardActivity.this);
        ArrayList<HashMap<String, String>> userList = db.GetUsers();
        ListAdapter data=new SimpleAdapter(DashboardActivity.this, userList, R.layout.result_layout,new String[]{"name","subject","mark"},
                new int[]{R.id.name, R.id.subject, R.id.marks});
        detailsdata.setAdapter(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bottom_nav_search:
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setMessage("Are you sure you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent=new Intent(DashboardActivity.this, ImageSliderActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                return true;

        }
        return super.onOptionsItemSelected(item);    }
}
