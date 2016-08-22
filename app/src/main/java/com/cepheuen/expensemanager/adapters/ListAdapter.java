package com.cepheuen.expensemanager.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.cepheuen.expensemanager.R;
import com.cepheuen.expensemanager.model.Expenses;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Ashik Vetrivelu on 20/08/16.
 */
public class ListAdapter extends BaseAdapter {

    private List<Expenses> expenses;
    private Context context;
    private ViewHolder viewHolder;
    public ListAdapter(List<Expenses> expenses, Context mContext)
    {
        this.expenses = expenses;
        this.context = mContext;
    }
    @Override
    public int getCount() {
        return expenses.size();
    }

    @Override
    public Expenses getItem(int position) {
        return expenses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.list_item,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.titleText = (TextView) view.findViewById(R.id.title);
            viewHolder.notesText = (TextView) view.findViewById(R.id.notes);
            viewHolder.moneySpent = (TextView) view.findViewById(R.id.money_spent);
            viewHolder.timeStamp = (TextView) view.findViewById(R.id.time);
            viewHolder.listIcon = (ImageView) view.findViewById(R.id.text_image);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.titleText.setText(expenses.get(position).getTitle());
        viewHolder.notesText.setText(expenses.get(position).getNotes());
        String money = "\u20b9"+expenses.get(position).getMoneySpent();
        viewHolder.moneySpent.setText(money);
        Date date = new Date(expenses.get(position).getTimeStamp());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY h:mm a");
        TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
        sdf.setTimeZone(istTimeZone);
        String strtime = sdf.format(date);
        viewHolder.timeStamp.setText(strtime);
        String firstChar = String.valueOf(expenses.get(position).getTitle().charAt(0));
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        TextDrawable drawable = TextDrawable.builder().buildRound(firstChar, colorGenerator.getRandomColor());
        viewHolder.listIcon.setImageDrawable(drawable);
        return view;
    }

    public class ViewHolder
    {
        TextView titleText,notesText,moneySpent,timeStamp;
        ImageView listIcon;
    }
}
