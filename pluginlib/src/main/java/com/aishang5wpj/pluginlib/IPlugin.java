package com.aishang5wpj.pluginlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by admin on 2019-06-28.
 */

public interface IPlugin {

    String EXTRA_INT_FROM = "EXTRA_INT_FROM";
    int FROM_INTERNAL = 0;
    int FROM_EXTERNAL = 1;

    void onAttatch(Activity activity);

    void onCreate(@Nullable Bundle savedInstanceState);

    void onStart();

    void onRestart();

    void onNewIntent(Intent intent);

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
