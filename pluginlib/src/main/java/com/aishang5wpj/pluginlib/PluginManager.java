package com.aishang5wpj.pluginlib;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by admin on 2019-06-28.
 * <p>
 * Android插件化框架系列之类加载器
 * https://www.jianshu.com/p/57fc356b9093
 * <p>
 * 06-30 23:03:24.198 2051-2051/? E/android.os.Debug: failed to load memtrack module: -2
 * 06-30 23:03:24.358 2060-2066/? E/jdwp: Failed sending reply to debugger: Broken pipe
 * 06-30 23:03:24.478 2060-2060/? E/dalvikvm: Could not find class 'android.graphics.drawable.RippleDrawable', referenced from method android.support.v7.widget.AppCompatImageHelper.hasOverlappingRendering
 * 06-30 23:03:28.898 2060-2060/com.aishang5wpj.plugin E/AndroidRuntime: FATAL EXCEPTION: main
 * Process: com.aishang5wpj.plugin, PID: 2060
 * java.lang.IllegalAccessError: Class ref in pre-verified class resolved to unexpected implementation
 * at dalvik.system.DexFile.defineClassNative(Native Method)
 * at dalvik.system.DexFile.defineClass(DexFile.java:222)
 * at dalvik.system.DexFile.loadClassBinaryName(DexFile.java:215)
 * at dalvik.system.DexPathList.findClass(DexPathList.java:322)
 * at dalvik.system.BaseDexClassLoader.findClass(BaseDexClassLoader.java:54)
 * at java.lang.ClassLoader.loadClass(ClassLoader.java:497)
 * at java.lang.ClassLoader.loadClass(ClassLoader.java:457)
 * at com.aishang5wpj.pluginlib.ProxyActivity.launchActivity(ProxyActivity.java:32)
 * at com.aishang5wpj.pluginlib.ProxyActivity.onCreate(ProxyActivity.java:27)
 * at android.app.Activity.performCreate(Activity.java:5231)
 * at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1104)
 * at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2157)
 * at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2243)
 * at android.app.ActivityThread.access$800(ActivityThread.java:135)
 * at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1196)
 * at android.os.Handler.dispatchMessage(Handler.java:102)
 * at android.os.Looper.loop(Looper.java:136)
 * at android.app.ActivityThread.main(ActivityThread.java:5019)
 * at java.lang.reflect.Method.invokeNative(Native Method)
 * at java.lang.reflect.Method.invoke(Method.java:515)
 * at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:779)
 * at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:595)
 * at dalvik.system.NativeStart.main(Native Method)
 */
public class PluginManager {

    private static final PluginManager INSTANCE = new PluginManager();

    public static PluginManager getInstance() {
        return INSTANCE;
    }

    public PluginApk loadPlugin(Context context, String apkPath) {
        Context applicationContext = context.getApplicationContext();
        PluginApk pluginApk = new PluginApk();
        pluginApk.mPackageInfo = applicationContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        pluginApk.mEntryName = pluginApk.mPackageInfo.activities[0].name;
        pluginApk.mDexClassLoader = createDexClassLoader(applicationContext, apkPath);
        pluginApk.mAssetManager = createAssetManager(apkPath);
        pluginApk.mResource = createResource(applicationContext, pluginApk.mAssetManager);
        return pluginApk;
    }

    private DexClassLoader createDexClassLoader(Context context, String apkPath) {
        File file = context.getDir("PluginDex", Context.MODE_PRIVATE);
        DexClassLoader classLoader = new DexClassLoader(
                apkPath,
                file.getAbsolutePath(),
                null,
                context.getClassLoader());
        return classLoader;
    }

    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getMethod("addAssetPath", String.class);
            method.invoke(assetManager, apkPath);
            return assetManager;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Resources createResource(Context context, AssetManager assetManager) {
        Resources resources = context.getResources();
        return new Resources(assetManager,
                resources.getDisplayMetrics(),
                resources.getConfiguration());
    }
}
