package com.example.project2.ui.pictures;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.ConsumerIrManager;
import android.media.ExifInterface;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.app.Activity.RESULT_OK;
import static android.content.Context.SYSTEM_HEALTH_SERVICE;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class PicturesFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private Context context;
    private GridView gridview;
    private ImageView selectedImage;
    private Animator currentAnimator;
    private ArrayList<Bitmap> gallery;
    private TextView no_i;
    private Button uploadButton;
    private TextView tvData;
    private Button serverButton;
    public  static final int GET_FROM_GALLERY=3;
    String filePath="/document/image:67/";


    ArrayList<String> arrayList;
    JSONArray jsonArray;
    private UserAdpater adpater;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;


    public static final String KEY_User_Document1 = "doc1";
    ImageView IDProf;
    private String Document_img1="";
    private static final int PICK_FROM_FILE=100;
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";



    public PicturesFragment() {
        // Required empty public constructor
    }

    public static PicturesFragment newInstance(String param1, String param2) {
        PicturesFragment fragment = new PicturesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pictures, container, false);
        context = getActivity();
        final String url = "http://192.249.19.243:0680/image/";
        try {
            jsonArray = new JSONTASK().execute(url).get();
            arrayList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                arrayList.add(jsonObject.getString("path"));

            }

            System.out.println(arrayList);

            //CustomAdapter adapter = new CustomAdapter(context, arrayList);



            System.out.println("hihi");
            //recyclerView.setAdapter(adapter);


            //String test_url= arrayList.get(arrayList.size()-1).toString();
            //Glide.with(getActivity()).load(test_url).override(300,300).into(selectedImage);
            //selectedImage.setVisibility(View.VISIBLE);

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }





        if(context != null) {
//            gallery = fetchGalleryImages(context);
//            if (gallery.size()==0){
//            } else {
//
//                //gridview = (GridView) view.findViewById(R.id.gridView);
//                //gridview.setAdapter(new GalleryAdapter(context, gallery));
//
//            }
            selectedImage = (ImageView) view.findViewById(R.id.selectedImageView);
            uploadButton=(Button) view.findViewById(R.id.uplaod_button);
            //tvData = (TextView) view.findViewById(R.id.test_connect);
            //serverButton= (Button) view.findViewById(R.id.server_button);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

            layoutManager= new GridLayoutManager(context, 3);
            //recyclerView.addItemDecoration(new GridSpacingItemDecoration(3,50,true));
            recyclerView.setLayoutManager(layoutManager);
            adpater= new UserAdpater(arrayList, context);
            recyclerView.setAdapter(adpater);
            adpater.notifyDataSetChanged();

//            serverButton.setOnClickListener(new Button.OnClickListener(){
//
//                @Override
//                public void onClick(View view) {
//                    Glide.with(getActivity()).load(arrayList.get(0)).into(selectedImage);
//                    selectedImage.setVisibility(View.VISIBLE);
//
//
//
//
//
//                }
//            });



                uploadButton.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        selectImage();

                    }
                });

                //pictureWork();
            }
        return view;
    }



    private void selectImage() {
        final CharSequence[] options = { "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                 if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
             if (requestCode == 2) {
                Uri selectedImage_uri = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getActivity().getContentResolver().query(selectedImage_uri,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail=getResizedBitmap(thumbnail, 400);
                //Log.w("path of image from gallery......******************.........", picturePath+"");
                selectedImage.setImageBitmap(thumbnail);
                BitMapToString(thumbnail);
                selectedImage.setVisibility(View.VISIBLE);
                uploadImage(selectedImage_uri);
            }
        }
    }

    private String getRealPathFromURI(Uri contentUri){
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getActivity(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;

    }

    private void uploadImage(Uri uri) {
        UploadService service = MyRetrofit2.getRetrofit2().create(UploadService.class);
        File file= new File(getRealPathFromURI(uri));
        MultipartBody.Part body1 = prepareFilePart("img", uri);

        RequestBody description = createPathFromString("hello");
        Call<ResponseBody> call= service.uploadFile(description, body1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @NonNull
    private RequestBody createPathFromString(String descriptionString){
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri){
        File file=new File(getRealPathFromURI(fileUri));
        RequestBody requestFile= RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    protected void pictureWork() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (currentAnimator != null) { currentAnimator.cancel(); }
                selectedImage.setImageBitmap(gallery.get(position));
                selectedImage.setVisibility(View.VISIBLE);
                gridview.setAlpha(0f);

                selectedImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentAnimator != null) { currentAnimator.cancel(); }
                        AnimatorSet set = new AnimatorSet();
                        set.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                gridview.setAlpha(1f);
                                selectedImage.setVisibility(View.GONE);
                                currentAnimator = null;
                            }
                            @Override
                            public void onAnimationCancel(Animator animation) {
                                gridview.setAlpha(1f);
                                selectedImage.setVisibility(View.GONE);
                                currentAnimator = null;
                            }
                        });
                        set.start();
                        currentAnimator = set;
                    }
                });
            }
        });
    }

    public  ArrayList<Bitmap> fetchGalleryImages(Context context) {
        ArrayList<Bitmap> galleryImageUrls = new ArrayList<Bitmap>();
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};//get all columns of type images
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;//order data by date

        Cursor imagecursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC");//get all data in Cursor by sorting in DESC order

        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);//get column index
            String picturePath = imagecursor.getString(dataColumnIndex);
            Bitmap loadedBitmap = BitmapFactory.decodeFile(picturePath);
            ExifInterface exif = null;
            try {
                File pictureFile = new File(picturePath);
                exif = new ExifInterface(pictureFile.getAbsolutePath());


            } catch (IOException e) {
                e.printStackTrace();
            }

            int orientation = ExifInterface.ORIENTATION_NORMAL;

            if (exif != null)
                orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    loadedBitmap = rotateBitmap(loadedBitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    loadedBitmap = rotateBitmap(loadedBitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    loadedBitmap = rotateBitmap(loadedBitmap, 270);
                    break;
            }
            galleryImageUrls.add(loadedBitmap);//get Image from column index



        }
        return galleryImageUrls;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    public class JSONTASK extends AsyncTask<String, Void, JSONArray> {
        JSONArray b_jsonArray;


//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        protected JSONObject doInBackground(String... urls) {
//
//            try {
//
//
//                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                /*JSONObject jsonObject = new JSONObject();
//               jsonObject.accumulate("user_id", "androidTest");
//                jsonObject.accumulate("name", "yun");*/
//
//                HttpURLConnection con = null;
//                BufferedReader reader = null;
//
//
//
//
//
//                    try{
//                    //URL url = new URL("http://192.168.25.16:3000/users");
//                    URL url = new URL(urls[0]);//url을 가져온다.
//                    con = (HttpURLConnection) url.openConnection();
//                    con.setRequestMethod("GET");
//                    con.setConnectTimeout(7000);
//                    con.connect();//연결 수행
//
//                    //입력 스트림 생성
//                    InputStream stream = con.getInputStream();
//
//                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
//                    reader = new BufferedReader(new InputStreamReader(stream));
//
//                    //실제 데이터를 받는곳
//                    StringBuffer buffer = new StringBuffer();
//
//
//                    //line별 스트링을 받기 위한 temp 변수
//                    String line = "";
//
//                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
//                    while((line = reader.readLine()) != null){
//                        buffer.append(line);
//
//                        System.out.println("hi"+line);
//                    }
//                    System.out.println(buffer.toString());
//                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
//                    return new JSONObject(buffer.toString());
//
//                    //아래는 예외처리 부분이다.
//                } catch (MalformedURLException e){
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    //종료가 되면 disconnect메소드를 호출한다.
//                    if(con != null){
//                        con.disconnect();
//                    }
//                    try {
//                        //버퍼를 닫아준다.
//                        if(reader != null){
//                            reader.close();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }//finally 부분
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//
//        }


        @Override
        protected JSONArray doInBackground(String... params) {
            try{
                URL url=new URL(params[0]);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuffer builder = new StringBuffer();

                String inputString=null;
                while((inputString = bufferedReader.readLine())!=null){
                    builder.append(inputString);
                    System.out.println(inputString);
                }

                String s = builder.toString();
                b_jsonArray= new JSONArray(s);

                conn.disconnect();
                bufferedReader.close();
                in.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return b_jsonArray;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);

            //String data = result.toString();
            //tvData.setText(data);


//            String split_result= result.substring(1,result.length()-1);
//            System.out.println(split_result);
//
//
//            String[] myArray=null;
//            ArrayList path=new ArrayList();
//
//            myArray=split_result.split(",");
//            System.out.println(myArray);
//            //result.getJSONArray("Path")
//            for(int i=0 ; i<myArray.length; i++){
//                String item=null;
//                String temp=null;
//                if (i%2==1){
//
//                    item= myArray[i].substring(0,myArray[i].length()-1);
//                    //temp=item.split(":")[1];
//
//                }else{
//
//                    item =myArray[i].substring(1,myArray[i].length());
//                    //temp=item.split(":")[1];
//                }
//                //final_result.add(item);
//                System.out.println(item);
//            }
            //System.out.println(final_result);
            //Glide.with(getActivity()).load("http://192.249.19.243:0680/image/1595250411081.jpg/").override(300,300).into(selectedImage);
            //selectedImage.setVisibility(View.VISIBLE);
            //"http://192.249.19.243:0680/image/upload/1595250411081.jpg"
            //"https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"


        }

    }




}