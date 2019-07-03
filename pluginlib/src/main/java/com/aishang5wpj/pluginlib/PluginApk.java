package com.aishang5wpj.pluginlib;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * Created by admin on 2019-06-28.
 */

public class PluginApk {

    public String mEntryName;
    public PackageInfo mPackageInfo;
    public DexClassLoader mDexClassLoader;
    public AssetManager mAssetManager;
    public Resources mResource;
}
