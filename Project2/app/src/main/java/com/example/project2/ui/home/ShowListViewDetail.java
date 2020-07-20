package com.example.project2.ui.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.project2.R;

public class ShowListViewDetail extends AppCompatActivity {

    private Intent intent;
    private ImageView imageView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private Button button1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_detail);

        intent = getIntent();

        imageView = (ImageView) findViewById(R.id.imageView);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        button1 = (Button) findViewById(R.id.buttonCall);

        imageView.setImageResource(intent.getIntExtra("imgRes", 0));
        textView1.setText(intent.getStringExtra("name"));
        textView2.setText(intent.getStringExtra("content"));
        textView3.setText(intent.getStringExtra("number"));

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 사용자의 OS 버전이 마시멜로우 이상인지 체크한다.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    /**
                     * 사용자 단말기의 권한 중 "전화걸기" 권한이 허용되어 있는지 확인한다.
                     * Android는 C언어 기반으로 만들어졌기 때문에 Boolean 타입보다 Int 타입을 사용한다.
                     */
                    int permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE);

                    /**
                     * 패키지는 안드로이드 어플리케이션의 아이디이다.
                     * 현재 어플리케이션이 CALL_PHONE에 대해 거부되어있는지 확인한다.
                     */
                    if (permissionResult == PackageManager.PERMISSION_DENIED) {

                        /**
                         * 사용자가 CALL_PHONE 권한을 거부한 적이 있는지 확인한다.
                         * 거부한적이 있으면 True를 리턴하고
                         * 거부한적이 없으면 False를 리턴한다.
                         */
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(ShowListViewDetail.this);
                            dialog.setTitle("Needs Permission")
                                    .setMessage("We need your permission for Phonecall. Continue?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            /**
                                             * 새로운 인스턴스(onClickListener)를 생성했기 때문에
                                             * 버전체크를 다시 해준다.
                                             */
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                // CALL_PHONE 권한을 Android OS에 요청한다.
                                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                            }
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(ShowListViewDetail.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                        // 최초로 권한을 요청할 때
                        else {
                            // CALL_PHONE 권한을 Android OS에 요청한다.
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                        }
                    }
                    // CALL_PHONE의 권한이 있을 때
                    else {
                        // 즉시 실행
                        Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+intent.getStringExtra("number")));
                        startActivity(intent1);
                    }
                }
                // 마시멜로우 미만의 버전일 때
                else {
                    // 즉시 실행

                    Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+intent.getStringExtra("number")));
                    startActivity(intent1);
                }
            }
        });


    }



    /**
     * 권한 요청에 대한 응답을 이곳에서 가져온다.
     *
     * @param requestCode 요청코드
     * @param permissions 사용자가 요청한 권한들
     * @param grantResults 권한에 대한 응답들(인덱스별로 매칭)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        intent = getIntent();
        String numbers = getIntent().getStringExtra("number");

        if (requestCode == 1000) {

            // 요청한 권한을 사용자가 "허용" 했다면...
            //Toast.makeText(DetailListViewActivity.this,  "res[0] : " + grantResults[0] + ", package : " + PackageManager.PERMISSION_GRANTED, Toast.LENGTH_SHORT).show();

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent1 = new Intent(Intent.ACTION_CALL, Uri.parse("tel: "+numbers));
                // Add Check Permission
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent1);
                }
            }
            else {
                Toast.makeText(ShowListViewDetail.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
