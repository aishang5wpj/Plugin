package com.aishang5wpj.plugin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.aishang5wpj.pluginlib.ProxyActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadPlugin(View view) {
        copyAssets2SD("/sdcard/plugin/HelloPlugin.apk");
        Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show();
    }

    private void copyAssets2SD(String strOutFileName) {
        OutputStream myOutput = null;
        InputStream myInput = null;
        try {
            File file = new File(strOutFileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            myOutput = new FileOutputStream(file);
            myInput = getAssets().open("plugin/HelloPlugin.apk");
            byte[] buffer = new byte[1024];
            int length = 0;
            length = myInput.read(buffer);
            while (length > 0) {
                myOutput.write(buffer, 0, length);
                length = myInput.read(buffer);
            }
            myOutput.flush();
            myOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (myOutput != null) {
                    myOutput.flush();
                }
                if (myInput != null) {
                    myInput.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void launchPlugin(View view) {
        ProxyActivity.launch(this, "/sdcard/plugin/HelloPlugin.apk");
    }
}
