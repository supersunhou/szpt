package com.szpt.hasee.szpt;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import base.BaseActivity;
import bean.Course;
import bean.SchoolTimeTable;
import cn.bmob.v3.Bmob;
import fragment.AnswerFragemnt;
import fragment.LostFragment;
import fragment.MainFragment;
import fragment.SecondHandFragment;
import ui.ChatActivity;

/**
 * Created by hasee on 2016/11/27.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{
    private DrawerLayout mDrawerLayout;
    private Button btn_me;
    private TextView tev_title;
    //底部按钮
    private ViewPager mViewPager;
    private Button btn_main,btn_lost,btn_second_hand,btn_answer;
    private List<Button> mTabIndicators = new ArrayList<Button>();
    private boolean isScrolling = false;
    // 手指是否在滑动
    private boolean isBackScrolling = false; //
    // 手指离开后的回弹
//    private long startTime = 0;
//    private long currentTime = 0;
    private FragmentPagerAdapter mAdapter;
//    private int moveOne = 0; // 下划线移动一个选项卡
    //fragments
    private AnswerFragemnt answerFragemnt;
    private LostFragment lostFragment;
    private MainFragment mainFragment;
    private SecondHandFragment secondHandFragment;
    private List<android.support.v4.app.Fragment> mTabs = new ArrayList<android.support.v4.app.Fragment>();
//    private static FragmentTransaction transaction;
//    private int index;//用来判断导航来加载Frangement
//    private int currentTabIndex;//判断当前的Framgent
//    private Button[] mTabs;
//    private Fragment[] fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一：默认初始化Bmob
      Bmob.initialize(this, "094aaa7dc57ca4bd04781a4ceb7af1c0");
      initView();
        initDatas();
        requestPermission(new String[]{Manifest.permission.INTERNET,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,Manifest.permission.WAKE_LOCK
        ,Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.ACCESS_WIFI_STATE},0x0001);
//     if(savedInstanceState==null){
//         //初始化fragment
//         transaction = getFragmentManager().beginTransaction();
//         mainFragment=new MainFragment();
//         lostFragment=new LostFragment();
//         answerFragemnt=new AnswerFragemnt();
//         secondHandFragment=new SecondHandFragment();
//         fragments=new Fragment[]{mainFragment,lostFragment,secondHandFragment,answerFragemnt};
//         FragmentManager mFragmentManager=getFragmentManager();
//         mFragmentManager.beginTransaction().add(R.id.frameLayout,mainFragment)
//                 .add(R.id.frameLayout,lostFragment)
//                 .add(R.id.frameLayout,secondHandFragment)
//                 .add(R.id.frameLayout,answerFragemnt)
//                 .hide(lostFragment)
//                 .hide(secondHandFragment)
//                 .hide(answerFragemnt)
//                 .show(mainFragment)
//                 .commit();
//       }
    }
    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_activity_layout);
        mDrawerLayout= (DrawerLayout) findViewById(R.id.id_drawerLayout);
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        tev_title= (TextView) findViewById(R.id.tev_title);
        btn_me= (Button) findViewById(R.id.btn_me);
        btn_answer= (Button) findViewById(R.id.btn_answer);
        btn_lost= (Button) findViewById(R.id.btn_lost);
        btn_main= (Button) findViewById(R.id.btn_mian);
        btn_second_hand= (Button) findViewById(R.id.btn_second_hand);
        btn_main.setOnClickListener(this);
        btn_answer.setOnClickListener(this);
        btn_lost.setOnClickListener(this);
        btn_second_hand.setOnClickListener(this);
//        mTabs=new Button[]{btn_main,btn_lost,btn_second_hand,btn_answer};
//        mTabs[0].setSelected(true);
        mTabIndicators.add(btn_main);
        mTabIndicators.add(btn_lost);
        mTabIndicators.add(btn_second_hand);
        mTabIndicators.add(btn_answer);
        btn_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

    }
    private void initDatas() {
        mainFragment=new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(mainFragment.TITLE, "首页");
        mainFragment.setArguments(bundle);
        mTabs.add(mainFragment);

        lostFragment=new LostFragment();
        Bundle bundle2 = new Bundle();
        bundle.putString(mainFragment.TITLE, "失物招领");
        lostFragment.setArguments(bundle2);
        mTabs.add(lostFragment);

        secondHandFragment=new SecondHandFragment();
        Bundle bundle3 = new Bundle();
        bundle.putString(mainFragment.TITLE, "二手交易");
        secondHandFragment.setArguments(bundle3);
        mTabs.add(secondHandFragment);

        answerFragemnt=new AnswerFragemnt();
        Bundle bundle4 = new Bundle();
        bundle.putString(mainFragment.TITLE, "解答");
        answerFragemnt.setArguments(bundle4);
        mTabs.add(answerFragemnt);

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return mTabs.get(position);
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);//此处有待优化 这是一开始就全部优化
        mViewPager.setOnPageChangeListener(this);
        setTitle(0);
    }
    @Override
    public void onClick(View view) {
        clickTab(view);
//        hideAllFragment();
//        switch (view.getId()){
//            case R.id.btn_mian:
//                tev_title.setText("首页");
//                index = 0;
//                showFragment(mainFragment);
//
//                break;
//            case R.id.btn_lost:
//                tev_title.setText("失物招领");
//                index = 1;
//                showFragment(lostFragment);
//
//                break;
//            case R.id.btn_second_hand:
//                index = 2;
//                tev_title.setText("二手交易");
//                showFragment(secondHandFragment);
//
//                break;
//            case R.id.btn_answer:
//                index = 3;
//                tev_title.setText("疑问解答");
//                showFragment(answerFragemnt);
//                Intent intent=new Intent(MainActivity.this, ChatActivity.class);
//                startActivity(intent);
//                break;
//        }
//        onTabIndex(index);
    }
    private void showFragment(Fragment fragment) {
        FragmentManager mFragmentManager=getFragmentManager();
        mFragmentManager.beginTransaction().show(fragment).commit();
    }
    private void hideAllFragment(){
//        FragmentManager mFragmentManager=getFragmentManager();
//        mFragmentManager.beginTransaction()
//                .hide(mainFragment)
//                .hide(lostFragment)
//                .hide(secondHandFragment)
//                .hide(answerFragemnt).commit();
    }
    private void onTabIndex(int index) {
//        if (currentTabIndex != index) {
//            FragmentTransaction trx = getFragmentManager().beginTransaction();
//            if (transaction == null) {
//                Log.d("onTabIndex", "onTabIndex: transaction is null");
//            }
//            trx.hide(fragments[currentTabIndex]);
//            if (!fragments[index].isAdded()) {
//                trx.add(R.id.frameLayout, fragments[index]);
//            }
//            trx.show(fragments[index]).commit();
//        }
//        mTabs[currentTabIndex].setSelected(false);
//        mTabs[index].setSelected(true);
//        currentTabIndex = index;
    }

    @Override
    public void permissionSuccess(int requestCode) {
        super.permissionSuccess(requestCode);
        switch (requestCode) {
            case 0x0001:
                //toast("权限通过");
                break;
        }
    }
    @Override
    public void permissionFail(int requestCode) {
        super.permissionFail(requestCode);
        switch (requestCode) {
            case 0x0001:
               // toast("权限获取失败");
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setTitle(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case 1: isScrolling = true;
                isBackScrolling = false;
                break;
            case 2: isScrolling = false;
                isBackScrolling = true;
                break;
            default:
                isScrolling = false;
                isBackScrolling = false;
                break;
        }
    }
    /**
     * 点击Tab按钮
     *
     * @param v
     */
    private void clickTab(View v) {
        //resetOtherTabs();
        switch (v.getId()) {
            case R.id.btn_mian:
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.btn_lost:
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.btn_second_hand:
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.btn_answer:
                mViewPager.setCurrentItem(3, false);
                break;
        }
    }

  public void  setTitle(int position){
      switch (position) {
          case 1:
              btn_main.setTextColor(Color.WHITE);
              btn_lost.setTextColor(Color.BLACK);
              btn_second_hand.setTextColor(Color.WHITE);
              btn_answer.setTextColor(Color.WHITE);
              tev_title.setText("失物招领");
              break;
          case 2:
              tev_title.setText("二手交易");
              btn_main.setTextColor(Color.WHITE);
              btn_lost.setTextColor(Color.WHITE);
              btn_second_hand.setTextColor(Color.BLACK);
              btn_answer.setTextColor(Color.WHITE);
              break;
          case 3:
              tev_title.setText("校园解答");
              btn_main.setTextColor(Color.WHITE);
              btn_lost.setTextColor(Color.WHITE);
              btn_second_hand.setTextColor(Color.WHITE);
              btn_answer.setTextColor(Color.BLACK);
              break;
          default:
              tev_title.setText("首页");
              btn_main.setTextColor(Color.BLACK);
              btn_lost.setTextColor(Color.WHITE);
              btn_second_hand.setTextColor(Color.WHITE);
              btn_answer.setTextColor(Color.WHITE);
              break;
      }

    }

}
