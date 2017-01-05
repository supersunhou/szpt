package fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.szpt.hasee.szpt.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.BaseFragment;
import bean.Course;
import bean.LostandFoundInfo;
import bean.SchoolTimeTable;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import view.MyScrollView;

/**
 * Created by hasee on 2016/11/26.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener {
    public static final String TITLE = "title";
    private RollPagerView mRollViewPager;
    private MyScrollView mScrollView;
   //轮播信息
   private TextSwitcher txtSwitcher;
    private String[] messages;//轮播的总消息
    private ArrayList<String>  lostInfos;
    private ArrayList<String>  click_lostInfos;
    private int i = 0,currentInfo=0;
    private GridView mGridView;
    private int[] icon = { R.drawable.gridchengji, R.drawable.gridxuanxiu,
            R.drawable.gridxuefen, R.drawable.gridtushuguan, R.drawable.gridyigong,
            R.drawable.gridkebiao};
    private String[] iconName = { "成绩", "选修", "选修分", "图书馆", "义工时", "课表" };
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
   private View view;
    //课表信息
    private TextView txv_day_one,txv_day_two,txv_day_three,txv_day_four,txv_day_five,
            txv_day_six,txv_day_sunday,txv_course_name,txv_course_time,txv_course_place,
            txv_course_next,txv_now_week,txv_last_course;

    private List<SchoolTimeTable> schoolTimeTables;
    private LinearLayout course_infromation;
    private int currentCourseNum=0;
    private  int currentDay=0;
    public ObjectAnimator animIn;
    public ObjectAnimator animOut;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.main_fragment_layout, container, false);
        mRollViewPager= (RollPagerView)view.findViewById(R.id.roll_view_pager);
        txtSwitcher = (TextSwitcher)view.findViewById(R.id.text_switcher);
        mGridView= (GridView) view.findViewById(R.id.gridview_main);
        mScrollView= (MyScrollView) view.findViewById(R.id.scrollview_main);
        lostInfos=new ArrayList<>();
        click_lostInfos=new ArrayList<>();
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(5000);
        mRollViewPager.setAnimationDurtion(5000); //设置切换时间
        mRollViewPager.setAdapter(new TestLoopAdapter(mRollViewPager)); //设置适配器
        mRollViewPager.setHintView(new ColorPointHintView(getActivity(), Color.WHITE, Color.GRAY));// 设置圆点指示器颜色
        initUI();
        getLostInfos();
        //置顶
        mScrollView.fullScroll(ScrollView.FOCUS_UP);
        // mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
      // 课表
        course_infromation= (LinearLayout) view.findViewById(R.id.course_infromation);
        txv_course_name= (TextView)view.findViewById(R.id.txv_course_name);
        txv_course_time= (TextView)view.findViewById(R.id.txv_course_time);
        txv_course_place= (TextView)view.findViewById(R.id.txv_course_place);
        txv_course_next= (TextView)view.findViewById(R.id.txv_next_course);
        txv_last_course= (TextView) view.findViewById(R.id.txv_last_course);
        txv_now_week= (TextView)view.findViewById(R.id.txv_now_week);
        txv_day_one= (TextView)view.findViewById(R.id.txv_day_one);
        txv_day_two= (TextView)view.findViewById(R.id.txv_day_two);
        txv_day_three= (TextView)view.findViewById(R.id.txv_day_three);
        txv_day_four= (TextView)view.findViewById(R.id.txv_day_four);
        txv_day_five= (TextView)view.findViewById(R.id.txv_day_five);
        txv_day_six= (TextView)view.findViewById(R.id.txv_day_six);
        txv_day_sunday= (TextView)view.findViewById(R.id.txv_day_sunday);
        txv_day_one.setOnClickListener(this);
        txv_day_two.setOnClickListener(this);
        txv_day_three.setOnClickListener(this);
        txv_day_four.setOnClickListener(this);
        txv_day_five.setOnClickListener(this);
        txv_day_six.setOnClickListener(this);
        txv_day_sunday.setOnClickListener(this);
        getmSchoolTimeTableDatas();
        txv_now_week.setText(schoolTimeTables.get(currentDay).getNowWeek());
        final PropertyValuesHolder pvh1=PropertyValuesHolder.ofFloat("scaleX",1f,0);
        final PropertyValuesHolder pvh2=PropertyValuesHolder.ofFloat("scaleY",1f,0);
        final PropertyValuesHolder pvh3=PropertyValuesHolder.ofFloat("scaleX",0,1f);
        final PropertyValuesHolder pvh4=PropertyValuesHolder.ofFloat("scaleY",0,1f);
         animOut= ObjectAnimator.ofPropertyValuesHolder(course_infromation,pvh1,pvh2).setDuration(500);
         animIn= ObjectAnimator.ofPropertyValuesHolder(course_infromation,pvh3,pvh4).setDuration(500);
        txv_course_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentCourseNum>=schoolTimeTables.get(currentDay).getCourses().size()-1){
                    Toast.makeText(getActivity(),"当前为最后的课",Toast.LENGTH_SHORT).show();
                    return ;
                }else {
                    currentCourseNum++;

                    animOut.start();
                    animOut.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            txv_course_name.setText(schoolTimeTables.get(currentDay).getCourses().get(currentCourseNum).getCoueseName());
                            txv_course_time.setText(schoolTimeTables.get(currentDay).getCourses().get(currentCourseNum).getCourseTime());
                            txv_course_place.setText(schoolTimeTables.get(currentDay).getCourses().get(currentCourseNum).getCoursePlace());
                            animIn.start();
                        }
                    });
                }

            }
        });
        txv_last_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentCourseNum!=schoolTimeTables.get(currentDay).getCourses().size()-1&&currentCourseNum==0){
                    Toast.makeText(getActivity(),"当前为第一节课",Toast.LENGTH_SHORT).show();
                    return ;
                }else {
                    currentCourseNum--;
//                    ObjectAnimator animOut= ObjectAnimator.ofPropertyValuesHolder(course_infromation,pvh1,pvh2).setDuration(500);
//                    final ObjectAnimator animIn= ObjectAnimator.ofPropertyValuesHolder(course_infromation,pvh3,pvh4).setDuration(500);
                    animOut.start();
                    animOut.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            txv_course_name.setText(schoolTimeTables.get(currentDay).getCourses().get(currentCourseNum).getCoueseName());
                            txv_course_time.setText(schoolTimeTables.get(currentDay).getCourses().get(currentCourseNum).getCourseTime());
                            txv_course_place.setText(schoolTimeTables.get(currentDay).getCourses().get(currentCourseNum).getCoursePlace());
                            animIn.start();
                        }
                    });
                }

            }
        });
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if(hidden){//当fragment从隐藏到出现的时候
            mScrollView.fullScroll(ScrollView.FOCUS_UP);
        }
    }

    private void initUI() {

        // 控件
        txtSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(getActivity());
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);
                tv.setTextColor(Color.WHITE);
                tv.setGravity(Gravity.CENTER);
                return tv;
            }
        });
        txtSwitcher.setInAnimation(getActivity(), R.anim.slide_in_bottom);
        txtSwitcher.setOutAnimation(getActivity(), R.anim.slide_out_top);

//        messages = new String[] { "11.26.周三，教工饭堂二楼\n遗失校园卡14240499\n联系15814556221", "11.27.周二，信息楼601\n遗失校园卡14240499\n联系15814556288", "11.26.周日，信息楼601\n遗失钱包\n联系15814556288" };

        txtSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"当前是"+messages[(i+messages.length-1)% messages.length],Toast.LENGTH_SHORT).show();
                View popupView = getActivity().getLayoutInflater().inflate(R.layout.lost_popup_view_layout, null);
                TextView textView= (TextView) popupView.findViewById(R.id.txv_lost_popuwin);
                textView.setText(click_lostInfos.get(currentInfo));
                //  2016/5/17 创建PopupWindow对象，指定宽度和高度
                PopupWindow window = new PopupWindow(popupView, 800, 600);
                //  2016/5/17 设置动画
                window.setAnimationStyle(R.style.popup_window_anim);
                //  2016/5/17 设置背景颜色
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
                //  2016/5/17 设置可以获取焦点
                window.setFocusable(false);
                //  2016/5/17 设置可以触摸弹出框以外的区域
                window.setOutsideTouchable(true);
                // 更新popupwindow的状态
                window.update();
                //  2016/5/17 以下拉的方式显示，并且可以设置显示的位置
                window.showAtLocation(view,Gravity.CENTER,0,0);


            }
        });
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getGridData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        sim_adapter = new SimpleAdapter(getActivity(), data_list, R.layout.mian_fragment_functions_item, from, to);
        //配置适配器
        mGridView.setAdapter(sim_adapter);

    }
    public List<Map<String, Object>> getGridData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(lostInfos!=null){
                currentInfo=i % lostInfos.size();
                txtSwitcher.setText(lostInfos.get(currentInfo));
                i++;
                Message msg1 =Message.obtain();
                mHandler.sendMessageDelayed(msg1, 5000);
            }
        }
    };

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.txv_day_one:
                currentDay=0;
                currentCourseNum=0;
                setCoueseFromDay(currentDay);
             //  Toast.makeText(getActivity(),currentDay+"",Toast.LENGTH_SHORT).show();
                break;
            case R.id.txv_day_two:
                currentDay=1;
                currentCourseNum=0;
                setCoueseFromDay(currentDay);
            //   Toast.makeText(getActivity(),currentDay+"",Toast.LENGTH_SHORT).show();
                break;
            case R.id.txv_day_three:
                currentDay=2;
                currentCourseNum=0;
                setCoueseFromDay(currentDay);
             //   Toast.makeText(getActivity(),currentDay+"",Toast.LENGTH_SHORT).show();
                break;
            case R.id.txv_day_four:
                currentDay=3;
                currentCourseNum=0;
                setCoueseFromDay(currentDay);
                break;
            case R.id.txv_day_five:
                currentDay=4;
                currentCourseNum=0;
                setCoueseFromDay(currentDay);
                break;
            case R.id.txv_day_six:
                currentDay=5;
                currentCourseNum=0;
                setCoueseFromDay(currentDay);
                break;
            case R.id.txv_day_sunday:
//                currentDay=6;
//                setCoueseFromDay(currentDay);
                break;
        }
    }

    private class TestLoopAdapter extends LoopPagerAdapter {
        private int[] imgs = {R.drawable.rollview1, R.drawable.rollview2, R.drawable.rollview3,
                R.drawable.rollview4};  // 本地图片
        private int count = imgs.length;// banner上图片的数量

        public TestLoopAdapter(RollPagerView viewPager)
        {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position)
        {
            final int picNo = position + 1;
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setOnClickListener(new View.OnClickListener()      // 点击事件
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(getActivity(), "点击了第" + picNo + "张图片", Toast.LENGTH_SHORT).show();
                }

            });

            return view;
        }

        @Override
        public int getRealCount()
        {
            return count;
        }

    }
    private void getLostInfos(){

        //
        txtSwitcher.setText("正在获取信息...");
        BmobQuery<LostandFoundInfo> query=new BmobQuery<LostandFoundInfo>();
        query.addWhereEqualTo("ThingsType", 1);
        query.order("-IdForNumber");
        query.setLimit(8);//分页查询，限制获取的数目
        query.findObjects(new FindListener<LostandFoundInfo>() {
            @Override
            public void done(List<LostandFoundInfo> object, BmobException e) {
                if(e==null){
                    for (int j=0;j<object.size();j++){
                        String s=object.get(j).getThingsTime()+",在"+ object.get(j).getThingsPlace()+"\n拾获"+object.get(j).getThingsName()+"\n联系"+object.get(j).getThingsPepContactWay();
                        String s2=object.get(j).getThingsTime()+",在"+ object.get(j).getThingsPlace()+"\n拾获"+object.get(j).getThingsName()+"\n"+object.get(j).getThingsInformation()+"\n联系"+object.get(j).getThingsPepContactWay();
                        lostInfos.add(s);
                        click_lostInfos.add(s2);
                    }
                    Message msg = Message.obtain();
                    msg.what = 1;
                    mHandler.sendMessage(msg);

                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }


    public void  getmSchoolTimeTableDatas(){
        List<Course> courses1=new ArrayList<Course>();
        List<Course> courses2=new ArrayList<Course>();
        schoolTimeTables=new ArrayList<SchoolTimeTable>();
        Course course1=new Course("Macios应用开发","3,4节8.30上课","西丽湖校区,信息楼408");
        Course course2=new Course("C#应用开发","5,6节2.00上课","西丽湖校区,信息楼508");
        Course course3=new Course("软件测试","7,7+节15.45上课","西丽湖校区,信息楼408");
        courses1.add(course1);
        courses1.add(course2);
       SchoolTimeTable mSchoolTimeTable1=new SchoolTimeTable("第十九周","三",courses1);
        courses2.add(course2);
        courses2.add(course3);
        SchoolTimeTable mSchoolTimeTable2=new SchoolTimeTable("第十九周","三",courses2);
        schoolTimeTables.add(mSchoolTimeTable1);
        schoolTimeTables.add(mSchoolTimeTable2);
        schoolTimeTables.add(mSchoolTimeTable1);
        schoolTimeTables.add(mSchoolTimeTable2);
        schoolTimeTables.add(mSchoolTimeTable1);
        schoolTimeTables.add(mSchoolTimeTable2);
    }

    public void setCoueseFromDay(final int currentDay){
        if (schoolTimeTables.get(currentDay).getCourses().size()-1==0){
            Toast.makeText(getActivity(),"今天没课",Toast.LENGTH_SHORT).show();
            return ;
        }else {
            // currentCourseNum--;
//                    ObjectAnimator animOut= ObjectAnimator.ofPropertyValuesHolder(course_infromation,pvh1,pvh2).setDuration(500);
//                    final ObjectAnimator animIn= ObjectAnimator.ofPropertyValuesHolder(course_infromation,pvh3,pvh4).setDuration(500);
            animOut.start();
            animOut.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    txv_course_name.setText(schoolTimeTables.get(currentDay).getCourses().get(0).getCoueseName());
                    txv_course_time.setText(schoolTimeTables.get(currentDay).getCourses().get(0).getCourseTime());
                    txv_course_place.setText(schoolTimeTables.get(currentDay).getCourses().get(0).getCoursePlace());
                    animIn.start();
                }
            });
        }
    }
}
