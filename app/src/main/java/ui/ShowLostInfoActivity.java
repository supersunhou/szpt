package ui;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import com.szpt.hasee.szpt.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.ListDropDownAdapter;
import adapter.LostSelectAdapter;
import adapter.LostListviewAdapter;
import base.BaseActivity;
import bean.LostandFoundInfo;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import view.DropDownMenu;
import view.ReFlashListView;

/**
 * Created by hasee on 2016/11/28.
 */

public class ShowLostInfoActivity extends BaseActivity implements ReFlashListView.IReflashListener{
    private ProgressDialog progressDialog;
    //菜单区域
   private String headers[] = {"筛选","全校区"};
    private List<View> popupViews = new ArrayList<>();
    private DropDownMenu mDropDownMenu;
    private LostSelectAdapter lostSelectAdapter;
     private ListDropDownAdapter campusAdapter;
    private String constellations[] = {"不限", "校园卡", "身份证", "钱包", "手机", "课本", "书", "U盘","笔记本","手表"};
 private String campus[] = {"不限", "东校区", "西校区", "北校区"};
    private int constellationPosition = 0;//判断选择的是哪个筛选
    private int campusPosition = 0;//记录所选的校区
    //内容区域
    private ReFlashListView listview;
    private LostListviewAdapter mAdapter;
    private ArrayList<LostandFoundInfo> infos;
    private int count;//加载的页数
    private BmobQuery<LostandFoundInfo> query;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            getDataFromUser(constellations[constellationPosition],campus[campusPosition]);
            listview.reflashComplete();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_show_mian_layout);
        mDropDownMenu= (DropDownMenu) findViewById(R.id.dropDownMenu);
        initView();
        getDataFromUser(constellations[constellationPosition],campus[campusPosition]);
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void initView() {
        progressDialog = new ProgressDialog(ShowLostInfoActivity.this);
        progressDialog.setMessage("正在加载。。。");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
         //init campus
        final ListView campusView = new ListView(this);
        campusView.setDividerHeight(0);
       campusAdapter = new ListDropDownAdapter(this, Arrays.asList(campus));
       campusView.setAdapter(campusAdapter);


        //init constellation菜单下拉内容
        final View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView constellation = (GridView) constellationView.findViewById(R.id.constellation);
        lostSelectAdapter = new LostSelectAdapter(this, Arrays.asList(constellations));
        constellation.setAdapter(lostSelectAdapter);
        TextView ok = (TextView) constellationView.findViewById( R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  toast(constellationPosition);
                progressDialog.show();
                getDataFromUser(constellations[constellationPosition],campus[campusPosition]);
                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[0] : constellations[constellationPosition]);
                mDropDownMenu.closeMenu();
            }
        });
        //填充菜单view
        popupViews.add(constellationView);
        popupViews.add(campusView);
        //注册点击事件
           constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lostSelectAdapter.setCheckItem(position);
                constellationPosition = position;
            }
        });
          campusView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                campusAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : campus[position]);
                campusPosition=position;
               // toast(campus[position]);
                progressDialog.show();
                getDataFromUser(constellations[constellationPosition],campus[campusPosition]);
                mDropDownMenu.closeMenu();
            }
        });
        //内容区域
        final View contentView = getLayoutInflater().inflate(R.layout.showlostinfo_activity, null);
        listview= (ReFlashListView) contentView.findViewById(R.id.listview_show_activity);
        listview.setInterface(this);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public void onReflash() {
        Message msg=Message.obtain();
        handler.sendMessageDelayed(msg,500);
    }

    @Override
    public void onLastflash() {
        onReflashData(constellations[constellationPosition],campus[campusPosition]);
    }
    public void getDataFromUser(String str,String campus){
        if(query!=null){
            query=null;
            count=0;
        }if(infos!=null){
            infos.clear();
            mAdapter.notifyDataSetChanged();
        }
        query = new BmobQuery<LostandFoundInfo>();
        if(!str.equals("不限")){
            query.addWhereEqualTo("ThingsName", str);
        }
        if(!campus.equals("不限")){
            query.addWhereEqualTo("campus", campus);
        }
        query.addWhereEqualTo("ThingsType", 1);
        query.order("-IdForNumber");
        query.setLimit(5);//分页查询，限制获取的数目
        query.findObjects(new FindListener<LostandFoundInfo>() {
            @Override
            public void done(List<LostandFoundInfo> object, BmobException e) {
                if(e==null){
                    infos= (ArrayList<LostandFoundInfo>) object;
                    mAdapter=new LostListviewAdapter(infos,ShowLostInfoActivity.this);
                    listview.setAdapter(mAdapter);

                }else{
                    toast("没有相应的信息，尝试不筛选逐个找找看吧");
                }
                progressDialog.dismiss();
            }
        });
    }
public void onReflashData(String str,String campus){
    count++;
    query = new BmobQuery<LostandFoundInfo>();
    if(!str.equals("不限")){
        query.addWhereEqualTo("ThingsName", str);
    }
    if(!campus.equals("不限")){
        query.addWhereEqualTo("campus", campus);
    }
    query.addWhereEqualTo("ThingsType", 1);
    query.order("-IdForNumber");
    query.setLimit(5);//分页查询，限制获取的数目
    query.setSkip(5*count);
    query.findObjects(new FindListener<LostandFoundInfo>() {
        @Override
        public void done(List<LostandFoundInfo> object, BmobException e) {
            if(e==null){
                infos.addAll(object);
                mAdapter.notifyDataSetChanged();
                listview.reflashComplete();
                //toast("上拉加载"+infos.size());
            }else{
                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
            }
        }
    });
}
}
