package com.cepheuen.expensemanager;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cepheuen.expensemanager.views.SlidingTabLayout;
import com.konifar.fab_transformation.FabTransformation;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.fragment_pager);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sub_appbar);
        ImageView closeBtn = (ImageView) findViewById(R.id.close_btn);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.toolbar_footer);
        toolbar.setContentInsetsAbsolute(0,0);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FabTransformation.with(fab).transformFrom(linearLayout);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FabTransformation.with(view).transformTo(linearLayout);
            }
        });

    }
}
