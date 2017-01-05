package fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.szpt.hasee.szpt.R;

import java.util.ArrayList;
import java.util.List;

import adapter.SecondHandleAdapter;
import base.BaseFragment;
import bean.ScondHandle;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import ui.SecondHandleCommit;
import view.ReFlashListView;

/**
 * Created by hasee on 2016/11/26.
 */

public class SecondHandFragment extends BaseFragment implements ReFlashListView.IReflashListener {
    private ReFlashListView lv_list;
    public static final String TITLE = "title";
    private List<ScondHandle> mlist = new ArrayList<>();
    private SecondHandleAdapter adapter;
    private BmobQuery<ScondHandle> queryAll;
    private Button btn_send;
    private ImageView img_sunmit;
    private int count = 0;
   // ProgressDialog p;
    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            adapter = new SecondHandleAdapter(getActivity(), mlist);
            adapter.notifyDataSetChanged();
            //lv_list.reflashComplete();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_hand_fragment_layout, container, false);
       // Bmob.initialize(getActivity(), "094aaa7dc57ca4bd04781a4ceb7af1c0");
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
//        p = new ProgressDialog(getActivity());
//        p.setMessage("正在加载数据...");
//        p.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        p.show();
        lv_list = (ReFlashListView) view.findViewById(R.id.lv_list);
        lv_list.setInterface(this);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        img_sunmit = (ImageView) view.findViewById(R.id.img_submit);
    }

    private void initData() {
        queryAll = new BmobQuery<ScondHandle>();
        queryAll.order("-createdAt");
        queryAll.setLimit(5);
        queryAll.findObjects(new FindListener<ScondHandle>() {
            @Override
            public void done(List<ScondHandle> list, BmobException e) {
                if (e == null) {
                    mlist = list;
                    adapter = new SecondHandleAdapter(getActivity(), mlist);
                    lv_list.setAdapter(adapter);
                   // p.dismiss();
                } else {
                    //p.dismiss();
                    Toast.makeText(getActivity(), "查询不到数据", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SecondHandleCommit.class);
                startActivity(intent);
            }
        });
        img_sunmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation ani = AnimationUtils.loadAnimation(getActivity(), R.anim.submit_rotate);
                img_sunmit.startAnimation(ani);
                Intent intent = new Intent(getActivity(), SecondHandleCommit.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onReflash() {
//        if(queryAll!=null){
//            queryAll=null;
//            count=0;
//        }if(mlist!=null){
//            mlist.clear();
//            adapter.notifyDataSetChanged();
//        }
        count=0;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                queryAll = new BmobQuery<ScondHandle>();
                queryAll.order("-createdAt");
                queryAll.setLimit(5);
                queryAll.findObjects(new FindListener<ScondHandle>() {
                    @Override
                    public void done(List<ScondHandle> list, BmobException e) {
                        if (e == null) {
                            mlist = list;
                            adapter = new SecondHandleAdapter(getActivity(), mlist);
                            lv_list.setAdapter(adapter);
                            lv_list.reflashComplete();
                          //  p.dismiss();
                        } else {
                          //  p.dismiss();
                            Toast.makeText(getActivity(), "查询不到数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }, 2000);
    }

//    @Override
//    public void onLastflash() {
//
//    }

//    @Override
//    public void onReflash() {
//       Message msg=Message.obtain();
//        handler.sendMessageDelayed(msg,500);
//    }

    @Override
    public void onLastflash() {
        count++;
        queryAll = new BmobQuery<ScondHandle>();
        queryAll.order("-createdAt");
        queryAll.setLimit(5);
        queryAll.setSkip(5 * count);
        queryAll.findObjects(new FindListener<ScondHandle>() {
            @Override
            public void done(List<ScondHandle> list, BmobException e) {
                if (e == null) {
                    mlist.addAll(list);
                    adapter.notifyDataSetChanged();
                    lv_list.reflashComplete();
                } else {
                    Toast.makeText(getActivity(), "查询不到数据", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
