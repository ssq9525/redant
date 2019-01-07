package com.xby.ssq.wavlink;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xby.ssq.wavlink.utils.LoadDevice;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoadDevice loadDevice=new LoadDevice();
        loadDevice.test(this);
    }
}
