package com.cepheuen.expensemanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    private ListAdapter adapter;
    private ViewPagerAdapter viewPagerAdapter;
    private boolean isInContextMode = false;
    private Button saveButton,datePicker;
    private EditText titleText,notesText,amountText;
    private LinearLayout linearLayout;
    private FloatingActionButton fab;
    private AdapterView.AdapterContextMenuInfo info;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        viewPager = (ViewPager) findViewById(R.id.fragment_pager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sub_appbar);
        ImageView closeBtn = (ImageView) findViewById(R.id.close_btn);
        saveButton = (Button) findViewById(R.id.save_btn);
        titleText = (EditText) findViewById(R.id.title_value);
        notesText = (EditText) findViewById(R.id.notes_value);
        amountText = (EditText) findViewById(R.id.amount_value);
        listView = (ListView) findViewById(R.id.list_log);
        linearLayout = (LinearLayout) findViewById(R.id.toolbar_footer);
        datePicker = (Button) findViewById(R.id.datePicker);

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
                titleText.setText("");
                amountText.setText("");
                datePicker.setText("Choose Date");
                notesText.setText("");
                FabTransformation.with(view).transformTo(linearLayout);
            }
        });

        ExpenseAPI.getApplicationService().getAllExpenses().enqueue(new Callback<List<Expenses>>() {
            @Override
            public void onResponse(Call<List<Expenses>> call, Response<List<Expenses>> response) {
                expensesList = response.body();
                adapter = new ListAdapter(expensesList, context);
                listView.setAdapter(adapter);

                viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(),expensesList);
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
                        if (!isInContextMode) {
                            Expenses expenses = new Expenses();
                            expenses.setMoneySpent(Float.parseFloat(amountText.getText().toString()));
                            expenses.setNotes(notesText.getText().toString());
                            expenses.setTitle(titleText.getText().toString());
                            if (datePickerView == null)
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
                        else {
                            Log.d("Hello",amountText.getText().toString());
                            isInContextMode = false;
                            Expenses expenses = new Expenses();
                            expenses.setMoneySpent(Float.parseFloat(amountText.getText().toString()));
                            expenses.setNotes(notesText.getText().toString());
                            expenses.setTitle(titleText.getText().toString());
                            expenses.setID(expensesList.get(info.position).getID());
                            if (datePickerView == null)
                                expenses.setTimeStamp(System.currentTimeMillis());
                            else
                                expenses.setTimeStamp(TimeUtils.getTimestamp(datePickerView));
                            ExpenseAPI.getApplicationService().updateExpense(expenses).enqueue(new Callback<List<Expenses>>() {
                                @Override
                                public void onResponse(Call<List<Expenses>> call, Response<List<Expenses>> response) {
                                    FabTransformation.with(fab).transformFrom(linearLayout);
                                    expensesList.clear();
                                    expensesList.addAll(response.body());
                                    adapter.notifyDataSetChanged();
                                    viewPagerAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<List<Expenses>> call, Throwable t) {

                                }
                            });
                        }
                    }
                });
                registerForContextMenu(listView);
                Collections.sort(expensesList,new TimeComparator());
            }

            @Override
            public void onFailure(Call<List<Expenses>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.list_log)
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {

            case R.id.edit:
                            FabTransformation.with(fab).transformTo(linearLayout);
                            isInContextMode = true;
                            titleText.setText(expensesList.get(info.position).getTitle());
                            amountText.setText(String.valueOf(expensesList.get(info.position).getMoneySpent()));
                            datePicker.setText(TimeUtils.getDate(expensesList.get(info.position).getTimeStamp()));
                            notesText.setText(expensesList.get(info.position).getNotes());
                            return true;

            case R.id.delete : ExpenseAPI.getApplicationService().deleteExpense(expensesList.get(info.position).getTimeStamp()).enqueue(new Callback<List<Expenses>>() {
                @Override
                public void onResponse(Call<List<Expenses>> call, Response<List<Expenses>> response) {
                    expensesList.clear();
                    expensesList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    viewPagerAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Expenses>> call, Throwable t) {

                }
            });

        }
        return true;
    }

}
