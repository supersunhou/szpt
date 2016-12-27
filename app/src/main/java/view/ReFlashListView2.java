package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.szpt.hasee.szpt.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CGS on 2016/12/26.
 */

public class ReFlashListView2 extends ListView implements AbsListView.OnScrollListener{

    View header;
    int headerHeight;
    int firstVisibleItem;
    int scrollState;
    boolean isRemarke;
    int state;
    int startY;
    final int NONE=0;
    final int PULL=1;
    final int RELESE=2;
    final int REFLASHING=3;
    IReflashListener iReflashListener;
    Date date1;
    public ReFlashListView2(Context context) {
        super(context);
        initView(context);

    }
    public ReFlashListView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public ReFlashListView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }
    private void initView(Context context){
        LayoutInflater inflater=LayoutInflater.from(context);
        header=inflater.inflate(R.layout.header, null);
        measureView(header);
        headerHeight=header.getMeasuredHeight();
        topPadding(-headerHeight);
        this.addHeaderView(header);
        this.setOnScrollListener(this);

    }
    private void topPadding(int topPadding) {
        header.setPadding(header.getPaddingLeft(), topPadding,
                header.getPaddingRight(), header.getPaddingBottom());
    }
    private void measureView(View view){
        ViewGroup.LayoutParams p=view.getLayoutParams();//子布局
        if(p==null){
            p=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width=ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight=p.height;
        if(tempHeight>0){
            height=MeasureSpec.makeMeasureSpec(tempHeight,MeasureSpec.EXACTLY);
        }else{
            height=MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
        }
        view.measure(width,height);
    }
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem=firstVisibleItem;//int firstVisibleItem监听listView滚动当前显示的第一个Item在listView的位置
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState=scrollState;
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(firstVisibleItem==0){
                    isRemarke=true;
                    startY=(int) ev.getY();
                    reflashViewByState();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                reflashViewByState();
                break;
            case MotionEvent.ACTION_UP:
                if(state==RELESE){
                    state=REFLASHING;
                    reflashViewByState();
                    iReflashListener.onReflash();//监听接口

                }else if(state==PULL){
                    state=NONE;
                    isRemarke=false;
                    reflashViewByState();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
    private void onMove(MotionEvent ev) {
        if(!isRemarke){
            return;
        }
        int tempY=(int) ev.getY();
        int space=tempY-startY;
        int topPadding=space-headerHeight;
        switch(state){
            case NONE:
                if(space>0){
                    state=PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if(space>headerHeight+30&&scrollState==SCROLL_STATE_TOUCH_SCROLL){
                    state=RELESE;
                    reflashViewByState();
                }
                break;
            case RELESE:
                topPadding(topPadding);
                if(space<headerHeight+30){
                    state=PULL;
                    reflashViewByState();
                }else if(space<=0){
                    state=NONE;
                    isRemarke=false;
                    reflashViewByState();
                }
                break;
        }
    }
    private void reflashViewByState(){
        TextView tip=(TextView)header.findViewById(R.id.tip);
        ImageView arrow=(ImageView) header.findViewById(R.id.arrow);
        ProgressBar progress =(ProgressBar) header.findViewById(R.id.progress);
        RotateAnimation anim=new RotateAnimation(0,180,RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(500);
        anim.setFillAfter(true);
        RotateAnimation anim1=new RotateAnimation(180,0,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        anim1.setDuration(500);
        anim1.setFillAfter(true);
        switch(state){
            case NONE:
                topPadding(-headerHeight);
                break;
            case PULL:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("下拉可以刷新！");
                arrow.clearAnimation();
                arrow.setAnimation(anim1);
                break;
            case RELESE:
                arrow.setVisibility(View.VISIBLE);
                progress.setVisibility(View.GONE);
                tip.setText("松开可以刷新！");
                arrow.clearAnimation();
                arrow.setAnimation(anim);
                break;
            case REFLASHING:
                topPadding(50);
                arrow.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                tip.setText("正在刷新...");
                arrow.clearAnimation();
                break;
        }
    }
    public void reflashComplete(){
        state=NONE;
        isRemarke=false;
        reflashViewByState();
        TextView lastUpdatetime=(TextView) header.findViewById(R.id.lastupdate_time);
        SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日  hh:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        String time=format.format(date);
        lastUpdatetime.setText(time);
    }
    public void setInterface(IReflashListener iReflashListener){
        this.iReflashListener=iReflashListener;
    }
    public interface IReflashListener{
        public void onReflash();
    }


}
