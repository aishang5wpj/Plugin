package com.aishang5wpj.helloplugin;

import android.os.Bundle;

import com.aishang5wpj.pluginlib.PluginActivity;

public class FirstPluginActivity extends PluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
    }
}
