package com.example.project2.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project2.MainActivity;
import com.example.project2.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    String[] name_array = new String[100];
    String[] num_array = new String[100];
    private ListView listView;
    private ListViewCustomAdapter adapter;
    private ArrayList<ContactItem> contactlist;
    private boolean lock_short_touch;
    private String origin_name, mod_name, del_name;
    private String mod_phone;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        lock_short_touch = false;

        listView = (ListView) root.findViewById(R.id.lv_main);
        adapter = new ListViewCustomAdapter();



        ContactItem contactitem = new ContactItem(root.getContext());
        contactlist = contactitem.getContactList();

/*        for(int i=0;i<contactlist.size();i++){
            System.out.println(contactlist.get(i).getUser_Name() + "/" + contactlist.get(i).getUser_phNumber());
        }
*/
        //System.out.println(contactlist);

        //https말고 http로, slack에 올라와 있는 ip와 port로. android studio에서는 port 번호 4자리 모두 쓰고 nodejs 파일에서는 뒤에 2자리만
        //JSONTask jsonTask = (JSONTask) new JSONTask().execute("http://192.249.19.243:0680/post");//remote server
        JSONTask jsonTask = (JSONTask) new JSONTask().execute("http://192.168.0.112:3020/post");//local server

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(!lock_short_touch){
                    int imgRes = ((ListViewCustomDTO) adapter.getItem(position)).getResId();
                    String name = ((ListViewCustomDTO) adapter.getItem(position)).getName();
                    String content = ((ListViewCustomDTO) adapter.getItem(position)).getContent();
                    String number = ((ListViewCustomDTO) adapter.getItem(position)).getNumber();

                    Intent intent = new Intent(root.getContext(), ShowListViewDetail.class);

                    intent.putExtra("imgRes", imgRes);
                    intent.putExtra("name", name);
                    intent.putExtra("content", content);
                    intent.putExtra("number", number);

                    if(!lock_short_touch) startActivity(intent);

                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View v, int i, long l) {
                lock_short_touch = true;
                PopupMenu popup = new PopupMenu(root.getContext(), v);
                getActivity().getMenuInflater().inflate(R.menu.menu_listview, popup.getMenu());

                final int index = i;

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case R.id.modify:
                                int imgRes = ((ListViewCustomDTO) adapter.getItem(index)).getResId();
                                String name = ((ListViewCustomDTO) adapter.getItem(index)).getName();
                                String content = ((ListViewCustomDTO) adapter.getItem(index)).getContent();
                                String number = ((ListViewCustomDTO) adapter.getItem(index)).getNumber();

                                Intent intent = new Intent(getContext(), ModifyListViewDetail.class);

                                intent.putExtra("imgRes", imgRes);
                                intent.putExtra("name", name);
                                intent.putExtra("content", content);
                                intent.putExtra("number", number);
                                intent.putExtra("index",index);

                                startActivityForResult(intent, 0);

                                break;

                            case R.id.delete:
                                ListViewCustomDTO del_dto = (ListViewCustomDTO) adapter.getItem(index);
                                del_name = del_dto.getName();
                                adapter.delItem(del_dto);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                                JSONTask jsonTask = (JSONTask) new JSONTask().execute("http://192.168.0.112:3020/post_delete");
                                break;
                        }

                        lock_short_touch = false;

                        return false;
                    }
                });
                popup.show();
                return false;
            }
        });


        listView.setAdapter(adapter);
        return root;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 0:
                ListViewCustomDTO mod_dto = (ListViewCustomDTO) data.getSerializableExtra("dto");
                int index = (int) data.getIntExtra("index", 0);
                origin_name = ((ListViewCustomDTO) adapter.getItem(index)).getName();
                adapter.modifyItem(index, mod_dto);
                adapter.notifyDataSetChanged();

                mod_name = mod_dto.getName();
                mod_phone = mod_dto.getNumber();

                JSONTask jsonTask = (JSONTask) new JSONTask().execute("http://192.168.0.112:3020/post_modify");

                break;
        }
    }



    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {

            try {
                //JSONObject 만들고 key value 형식으로 값 저장
                JSONObject jsonObject = new JSONObject();

                if(urls[0].contains("post_modify")){//modify request

                    jsonObject.accumulate("origin_name", origin_name);
                    jsonObject.accumulate("mod_name", mod_name);
                    jsonObject.accumulate("number", mod_phone);

                }

                else if(urls[0].contains("post_delete")){//delete request
                    jsonObject.accumulate("del_name", del_name);

                }
                else if(urls[0].contains("post_add")){//add request

                }

                else{//initial post request

                    for (int i = 0; i < contactlist.size(); i++) {
                        //System.out.println(contactlist.get(i).getUser_Name() + "/" + contactlist.get(i).getUser_phNumber());
                        jsonObject.accumulate("name", contactlist.get(i).getUser_Name());
                        jsonObject.accumulate("phone", contactlist.get(i).getUser_phNumber());
                    }
                }


                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {

                    URL url = new URL(urls[0]);
                    //연결
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST 방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application jSON 형식으로 전송
                    //con.setRequestProperty("Accept", "application/json");
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream 으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미

                    con.connect();

                    //서버로 보내기위해서 스트림 생성
                    OutputStream outStream = con.getOutputStream();

                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();


                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    //서버로 부터 받은 값을 리턴
                    return buffer.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

        @SuppressLint("ResourceType")
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //System.out.println(result);
            //Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            String[] response = result.split("/");

            System.out.println(result);

            if(result.equals("modify_done") || result.equals("remove_done")){//response from modify or remove
                //do nothing
            }

            else {//response from initial post

                TypedArray arrResId = getResources().obtainTypedArray(R.array.resId);


                for (int i = 0; i < response.length; i += 2) {
                    ListViewCustomDTO dto = new ListViewCustomDTO();
                    dto.setResId(arrResId.getResourceId((i / 2) + 1, 0));
                    dto.setName(response[i]);
                    dto.setContent("default");
                    dto.setNumber(response[i + 1]);

                    adapter.addItem(dto);
                }

                adapter.notifyDataSetChanged();
            }

        }

    }

}