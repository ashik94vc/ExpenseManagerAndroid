package com.cepheuen.expensemanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cepheuen.expensemanager.adapters.ListAdapter;
import com.cepheuen.expensemanager.adapters.ViewPagerAdapter;
import com.cepheuen.expensemanager.comparator.TimeComparator;
import com.cepheuen.expensemanager.fragments.DatePickerFragment;
import com.cepheuen.expensemanager.model.Expenses;
import com.cepheuen.expensemanager.network.ExpenseAPI;
import com.cepheuen.expensemanager.utils.TimeUtils;
import com.konifar.fab_transformation.FabTransformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    private List<Expenses> expensesList;
    private Context context;
    private DatePicker datePickerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        viewPager = (ViewPager) findViewById(R.id.fragment_pager);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sub_appbar);
        ImageView closeBtn = (ImageView) findViewById(R.id.close_btn);
        final Button saveButton = (Button) findViewById(R.id.save_btn);
        final EditText titleText = (EditText) findViewById(R.id.title_value);
        final EditText notesText = (EditText) findViewById(R.id.notes_value);
        final EditText amountText = (EditText) findViewById(R.id.amount_value);
        final ListView listView = (ListView) findViewById(R.id.list_log);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.toolbar_footer);
        final Button datePicker = (Button) findViewById(R.id.datePicker);

        toolbar.setContentInsetsAbsolute(0, 0);
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

        ExpenseAPI.getApplicationService().getAllExpenses().enqueue(new Callback<List<Expenses>>() {
            @Override
            public void onResponse(Call<List<Expenses>> call, Response<List<Expenses>> response) {
                expensesList = response.body();
                final ListAdapter adapter = new ListAdapter(expensesList, context);
                listView.setAdapter(adapter);

                final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(),expensesList);
                viewPager.setAdapter(viewPagerAdapter);

                datePicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerFragment datePickerFragment = new DatePickerFragment();
                        datePickerFragment.show(getFragmentManager(), "dialog");
                        datePickerFragment.setOnDateSelectedListener(new DatePickerFragment.OnDateSelected() {
                            @Override
                            public void dateSelected(DatePicker datePickerWidget) {
                                datePickerView = datePickerWidget;
                                String date = datePickerView.getDayOfMonth() + "/" + datePickerView.getMonth() + datePickerView.getYear();
                                datePicker.setText(date);
                            }
                        });
                    }
                });

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Expenses expenses = new Expenses();
                        expenses.setMoneySpent(Float.parseFloat(amountText.getText().toString()));
                        expenses.setNotes(notesText.getText().toString());
                        expenses.setTitle(titleText.getText().toString());
                        if(datePickerView == null)
                            expenses.setTimeStamp(System.currentTimeMillis());
                        else
                            expenses.setTimeStamp(TimeUtils.getTimestamp(datePickerView));
                        expensesList.add(expenses);
                        Collections.sort(expensesList, new TimeComparator());
                        adapter.notifyDataSetChanged();
                        ExpenseAPI.getApplicationService().addExpense(expenses).enqueue(new Callback<Expenses>() {
                            @Override
                            public void onResponse(Call<Expenses> call, Response<Expenses> response) {
                                viewPagerAdapter.notifyDataSetChanged();
                                FabTransformation.with(fab).transformFrom(linearLayout);
                            }

                            @Override
                            public void onFailure(Call<Expenses> call, Throwable t) {

                            }
                        });

                    }
                });
                Collections.sort(expensesList,new TimeComparator());
            }

            @Override
            public void onFailure(Call<List<Expenses>> call, Throwable t) {

            }
        });
    }
}
