package ui;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.szpt.hasee.szpt.R;

import base.BaseActivity;

/**
 * Created by hasee on 2016/12/12.
 */

public class ChatActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.chat_activity_layout);
   toast("师兄帮你解答");
    }
}
