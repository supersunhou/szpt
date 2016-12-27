package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szpt.hasee.szpt.R;

import base.BaseFragment;

/**
 * Created by hasee on 2016/11/26.
 */

public class AnswerFragemnt extends BaseFragment{

    public static final String TITLE = "title";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.answer_fragment_layout, container, false);
        return view;
    }
}
