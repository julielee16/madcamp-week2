package com.example.project2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.R;

public class AddListViewDetail extends AppCompatActivity {

    private Intent intent;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_add);

        intent = getIntent();


        textView1 = (TextView) findViewById(R.id.add_textView1);
        textView2 = (TextView) findViewById(R.id.add_textView2);
        textView3 = (TextView) findViewById(R.id.add_textView3);

        Button button = (Button) findViewById(R.id.add_button);

        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){

                String add_name = textView1.getText().toString();
                String add_content = textView2.getText().toString();
                String add_number = textView3.getText().toString();

                ListViewCustomDTO add_dto = new ListViewCustomDTO();

                //add_dto.setResId(mod_imgres);
                add_dto.setName(add_name);
                add_dto.setContent(add_content);
                add_dto.setNumber(add_number);

                Intent new_dto = new Intent();
                new_dto.putExtra("dto", add_dto);
                setResult(0, new_dto);

                Toast.makeText(AddListViewDetail.this, "Saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }



}
