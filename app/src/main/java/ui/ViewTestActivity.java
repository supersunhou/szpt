package ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.szpt.hasee.szpt.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 * Created by hasee on 2016/12/4.
 */

public class ViewTestActivity extends BaseActivity implements ReFlashListView.IReflashListener{
    private String headers[] = {"筛选"};
    private List<View> popupViews = new ArrayList<>();
    private DropDownMenu mDropDownMenu;
    private LostSelectAdapter lostSelectAdapter;
    private String constellations[] = {"不限", "校园卡", "身份证", "钱包", "手机", "课本", "书", "U盘","笔记本","手表"};
    private int constellationPosition = 0;
    //内容区域
    private ReFlashListView listview;
    private LostListviewAdapter mAdapter;
    private ArrayList<LostandFoundInfo> infos;
    private int count;//加载的页数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_show_mian_layout);
        mDropDownMenu= (DropDownMenu) findViewById(R.id.dropDownMenu);
        initView();
       // getData();
    }
    private void initView() {
        //init constellation
        final View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView constellation = (GridView) constellationView.findViewById(R.id.constellation);
        lostSelectAdapter = new LostSelectAdapter(this, Arrays.asList(constellations));
        constellation.setAdapter(lostSelectAdapter);
        TextView ok = (TextView) constellationView.findViewById( R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mDropDownMenu.setTabText(constellationPosition == 0 ? headers[3] : constellations[constellationPosition]);
               // String str=constellations[constellationPosition].get
                toast("ok");
                mDropDownMenu.closeMenu();
            }
        });
        popupViews.add(constellationView);
        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lostSelectAdapter.setCheckItem(position);
                constellationPosition = position;
            }
        });

        final View contentView = getLayoutInflater().inflate(R.layout.showlostinfo_activity, null);
        listview= (ReFlashListView) contentView.findViewById(R.id.listview_show_activity);
        listview.setInterface(this);
        //init dropdownview
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
    private  void getData(){
        BmobQuery<LostandFoundInfo> query = new BmobQuery<LostandFoundInfo>();
        query.addWhereEqualTo("ThingsType", 1);
        query.order("-IdForNumber");
        query.setLimit(5);//分页查询，限制获取的数目
        query.findObjects(new FindListener<LostandFoundInfo>() {
            @Override
            public void done(List<LostandFoundInfo> object, BmobException e) {
                if(e==null){
                    infos= (ArrayList<LostandFoundInfo>) object;
                    mAdapter=new LostListviewAdapter(infos,ViewTestActivity.this);
                    listview.setAdapter(mAdapter);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    @Override
    public void onReflash() {
        if(count<5){
            count=5;
        }
        BmobQuery<LostandFoundInfo> query = new BmobQuery<LostandFoundInfo>();
        query.addWhereEqualTo("ThingsType", 1);
        query.order("-IdForNumber");//查询的顺序
        query.setLimit(5*count);//分页查询，限制获取的数目
        query.findObjects(new FindListener<LostandFoundInfo>() {
            @Override
            public void done(List<LostandFoundInfo> object, BmobException e) {
                if(e==null){
                    infos= (ArrayList<LostandFoundInfo>) object;
                    mAdapter=new LostListviewAdapter(infos,ViewTestActivity.this);
                    listview.setAdapter(mAdapter);
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        listview.reflashComplete();
    }

    @Override
    public void onLastflash() {
        count++;
        BmobQuery<LostandFoundInfo> query = new BmobQuery<LostandFoundInfo>();
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
