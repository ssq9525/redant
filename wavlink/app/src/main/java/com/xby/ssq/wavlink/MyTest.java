package com.xby.ssq.wavlink;

import android.content.Context;
import android.widget.Toast;

import com.xby.ssq.wavlink.bean.Person;

import java.lang.ref.WeakReference;

public class MyTest {
    private static WeakReference<Context> mContext;

    public MyTest(Context context) {
        mContext = new WeakReference<Context>(context);
        Person person=new Person();
        person.setName("test");
        Toast.makeText(context,person.getName(),Toast.LENGTH_LONG).show();
    }

    public MyTest() {

    }
    public void test(Context context){
        mContext = new WeakReference<Context>(context);
        Person person=new Person();
        person.setName("test200");
        Toast.makeText(context,person.getName(),Toast.LENGTH_LONG).show();
    }
}
