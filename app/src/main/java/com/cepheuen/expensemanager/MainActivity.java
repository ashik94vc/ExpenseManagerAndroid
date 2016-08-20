package com.cepheuen.expensemanager;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cepheuen.expensemanager.adapters.ListAdapter;
import com.cepheuen.expensemanager.comparator.TimeComparator;
import com.cepheuen.expensemanager.model.Expenses;
import com.konifar.fab_transformation.FabTransformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    private List<Expenses> expensesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expensesList = new ArrayList<>();

        viewPager = (ViewPager) findViewById(R.id.fragment_pager);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sub_appbar);
        ImageView closeBtn = (ImageView) findViewById(R.id.close_btn);
        Button saveButton = (Button) findViewById(R.id.save_btn);
        final EditText titleText = (EditText) findViewById(R.id.title_value);
        final EditText notesText = (EditText) findViewById(R.id.notes_value);
        final EditText amountText = (EditText) findViewById(R.id.amount_value);
        final ListView listView = (ListView) findViewById(R.id.list_log);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.toolbar_footer);

        final ListAdapter adapter = new ListAdapter(expensesList,this);
        listView.setAdapter(adapter);

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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Expenses expenses = new Expenses();
                expenses.setMoneySpent(Float.parseFloat(amountText.getText().toString()));
                expenses.setNotes(notesText.getText().toString());
                expenses.setTitle(titleText.getText().toString());
                expenses.setTimeStamp(System.currentTimeMillis());
                expensesList.add(expenses);
                Collections.sort(expensesList,new TimeComparator());
                adapter.notifyDataSetChanged();
            }
        });

    }
}
