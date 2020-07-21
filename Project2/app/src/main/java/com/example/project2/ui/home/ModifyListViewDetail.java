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

public class ModifyListViewDetail extends AppCompatActivity {

    private Intent intent;
    private ImageView imageView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_modify);

        intent = getIntent();

        imageView = (ImageView) findViewById(R.id.modify_imageView);
        textView1 = (TextView) findViewById(R.id.modify_textView1);
        textView2 = (TextView) findViewById(R.id.modify_textView2);
        textView3 = (TextView) findViewById(R.id.modify_textView3);

        final int mod_imgres = (int) intent.getIntExtra("imgRes", 0);
        final int index = (int) intent.getIntExtra("index", 0);
        imageView.setImageResource(mod_imgres);
        textView1.setText(intent.getStringExtra("name"));
        textView2.setText(intent.getStringExtra("content"));
        textView3.setText(intent.getStringExtra("number"));

        Button button = (Button) findViewById(R.id.modify_button);

        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){

                String mod_name = textView1.getText().toString();
                String mod_content = textView2.getText().toString();
                String mod_number = textView3.getText().toString();

                ListViewCustomDTO mod_dto = new ListViewCustomDTO();

                mod_dto.setResId(mod_imgres);
                mod_dto.setName(mod_name);
                mod_dto.setContent(mod_content);
                mod_dto.setNumber(mod_number);

                Intent new_dto = new Intent();
                new_dto.putExtra("dto", mod_dto);
                new_dto.putExtra("index", index);
                setResult(0, new_dto);

                Toast.makeText(ModifyListViewDetail.this, "Saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }



}
