package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.szpt.hasee.szpt.R;

import base.BaseFragment;
import ui.AddLostInfoActivity;
import ui.FindLostOwner;
import ui.ShowLostInfoActivity;

/**
 * Created by hasee on 2016/11/26.
 */

public class LostFragment extends BaseFragment {
    public static final String TITLE = "title";
    private Button btn_want_publish;
    private Button btn_want_found;
  private  Button btn_want_give;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lost_fragment_layout, container, false);
        btn_want_publish= (Button) view.findViewById(R.id.btn_want_publish);
        btn_want_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AddLostInfoActivity.class);
                startActivity(intent);
            }
        });
        btn_want_found= (Button) view.findViewById(R.id.btn_want_find);
   btn_want_found.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Intent intent=new Intent(getActivity(), ShowLostInfoActivity.class);
           startActivity(intent);
       }
   });
        btn_want_give= (Button) view.findViewById(R.id.btn_want_give);
        btn_want_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), FindLostOwner.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
