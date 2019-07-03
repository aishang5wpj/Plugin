package com.aishang5wpj.pluginlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by admin on 2019-06-28.
 */
public class ProxyActivity extends Activity {

    private static final String EXTRA_STRING_PLUGIN_NAME = "EXTRA_STRING_PLUGIN_NAME";

    private PluginApk mPluginApk;
    private IPlugin mIPlugin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String pluginName = getIntent().getStringExtra(EXTRA_STRING_PLUGIN_NAME);
        mPluginApk = PluginManager.getInstance().loadPlugin(this, pluginName);
        launchActivity(savedInstanceState, mPluginApk.mEntryName);
    }

    private void launchActivity(Bundle savedInstanceState, String activityName) {
        try {
            Class<?> clz = mPluginApk.mDexClassLoader.loadClass(activityName);
            Object object = clz.newInstance();
            if (object instanceof IPlugin) {
                mIPlugin = (IPlugin) object;
                mIPlugin.onAttatch(this);
                Bundle bundle = new Bundle();
                bundle.putInt(IPlugin.EXTRA_INT_FROM, IPlugin.FROM_EXTERNAL);
                mIPlugin.onCreate(bundle);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        mIPlugin.onRestart();
    }

    @Override
    public void onStart() {
        super.onStart();
        mIPlugin.onStart();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mIPlugin.onNewIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mIPlugin.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mIPlugin.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mIPlugin.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIPlugin.onDestroy();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginApk != null ? mPluginApk.mDexClassLoader : super.getClassLoader();
    }

    @Override
    public AssetManager getAssets() {
        return mPluginApk != null ? mPluginApk.mAssetManager : super.getAssets();
    }

    @Override
    public Resources getResources() {
        return mPluginApk != null ? mPluginApk.mResource : super.getResources();
    }

    public static void launch(Context context, String pluginName) {
        Intent intent = new Intent(context, ProxyActivity.class);
        intent.putExtra(EXTRA_STRING_PLUGIN_NAME, pluginName);
        context.startActivity(intent);
    }
}
