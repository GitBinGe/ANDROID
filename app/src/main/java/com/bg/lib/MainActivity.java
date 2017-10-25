package com.bg.lib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bg.library.TestClass;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestClass tc = new TestClass();
    }
}
