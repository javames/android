package com.hong.cookbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hong.cookbook.widget.FilterTab;

/**
 * Created by Administrator on 2018/2/24.
 */

public class TestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        FilterTab filterTab=findViewById(R.id.tab);

        String [] items={"Item1","Item2","Item3","Item4","Item5","Item6","Item7","Item8","Item9"};
        filterTab.setData(items);
        filterTab.setShowTabCount(4);
        filterTab.invalite();
    }
}
