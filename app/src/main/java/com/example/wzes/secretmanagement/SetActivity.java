package com.example.wzes.secretmanagement;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.wzes.util.AESHelper;
import com.example.wzes.util.NetUser;
import com.example.wzes.util.User;
import com.example.wzes.util.memoNetData;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.wzes.util.MD5.getMD5;

public class SetActivity extends Activity {
    private String masterPassword = "123456";
    private Button bachBtn, keepBtn;
    private Spinner spinner;
    private Switch aSwitch;
    private EditText myName, myEmail, myDate;
    private int type;
    private String men;
    private String birth;
    private String name;
    private String email;

    private int[] drawables = {R.drawable.usual, R.drawable.qq, R.drawable.wx , R.drawable.wb ,R.drawable.zfb,
            R.drawable.tc, R.drawable.ws , R.drawable.bdnm ,R.drawable.mm,
            R.drawable.bdnm, R.drawable.wph , R.drawable.aqy ,R.drawable.xc,
            R.drawable.qqyy, R.drawable.mp , R.drawable.aqy ,R.drawable.tb,
            R.drawable.kg, R.drawable.td ,R.drawable.qqyx };
    private String lastUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ActivityColletor.addActivity(SetActivity.this);
        //获取上次登录的用户名
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "lastUsername", MODE_PRIVATE);
        lastUsername = sharedPreferences.getString("lastUsername", "");

        spinner = (Spinner) findViewById(R.id.setImg);
        bachBtn = (Button) findViewById(R.id.setBackBtn);
        keepBtn = (Button) findViewById(R.id.setKeepBtn);
        myName = (EditText) findViewById(R.id.setName);
        myEmail = (EditText) findViewById(R.id.setEmail);
        myDate = (EditText) findViewById(R.id.setBirth);
        aSwitch = (Switch) findViewById(R.id.setMen);



        keepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birth = myDate.getText().toString();
                name = myName.getText().toString();
                email = myEmail.getText().toString();

                List<User> myData = DataSupport.where("userName = ?", lastUsername).find(User.class);
                if(myData.size()!=0){
                    Log.i("User", "onClick: "+myData.size());
                    String password = myData.get(0).getPassword();
                    User user = new User(lastUsername, password, type, men, birth, name, email);
                    //user.updateAll("userName = ?", lastUsername);
                    ContentValues values = new ContentValues();
                    values.put("type", type);
                    values.put("men", men);
                    values.put("birth", birth);
                    values.put("name", name);
                    values.put("email", AESHelper.encrypt(email, masterPassword));

                    DataSupport.updateAll( User.class, values, "userName = ?", lastUsername);

                    upData(password, type, men, birth, name, email);

                }else{
                    Toast.makeText(SetActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                }


            }
        });

        bachBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetActivity.this, MyMainActivity.class);
                intent.putExtra("item",3);
                startActivity(intent);
                finish();
            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    men = "女";
                }else{
                    men = "男";
                }
            }
        });
        spinner.setAdapter(adapter);
        List<User> myData = DataSupport.where("userName = ?", lastUsername).find(User.class);
        spinner.setSelection(myData.get(0).getType());
        myName.setText(myData.get(0).getName());
        myEmail.setText(myData.get(0).getEmail());
        Log.i("EMAIL", "onCreate: "+ myData.get(0).getEmail());
        myDate.setText(myData.get(0).getBirth());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int positon, long id) {
                type = positon;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void upData(String password, final int type, final String men
            , final String birth, final String name, final String email ){
        BmobQuery<NetUser> bmobQuery = new BmobQuery<NetUser>();
        bmobQuery.setLimit(1000);
        bmobQuery.findObjects(new FindListener<NetUser>() {
            @Override
            public void done(List<NetUser> list, BmobException e) {
                if (e == null) {
                    for(NetUser NData : list){
                        NetUser n = new NetUser();
                        if(NData.getUserName().equals(lastUsername)){
                            n.setObjectId(NData.getObjectId());
                            n.setBirth(birth);
                            n.setName(name);
                            n.setEmail(email);
                            n.setMen(men);
                            n.setType(type);
                            n.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        Toast.makeText(SetActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                                    }else{

                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    private BaseAdapter adapter = new BaseAdapter() {
        public int getCount() {
            return drawables.length;
        }


        public Object getItem(int position) {
            return drawables[position];
        }


        public long getItemId(int position) {
            return position;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout ll = new LinearLayout(SetActivity.this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setLayoutParams( new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            ImageView iv = new ImageView(SetActivity.this);
            iv.setImageResource(drawables[position]);
            ll.addView(iv);

            return ll;
        }
    };
}
