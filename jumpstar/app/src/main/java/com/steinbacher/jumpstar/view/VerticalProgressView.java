package com.steinbacher.jumpstar.view;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.PopupMenu;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.steinbacher.jumpstar.Configuration;
import com.steinbacher.jumpstar.R;
import com.steinbacher.jumpstar.db.VerticalHeightContract;
import com.steinbacher.jumpstar.db.VerticalHeightReader;
import com.steinbacher.jumpstar.db.VerticalHeightWriter;
import com.steinbacher.jumpstar.util.SharedPreferencesManager;


/**
 * Created by georg on 07.04.18.
 */

public class VerticalProgressView extends LinearLayoutCompat implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener{
    private static final String TAG = "VerticalProgressView";

    private View mRootView;
    private Context mContext;
    private String mTitle = "";

    private IViewRemovedListener mListener;
    public interface IViewRemovedListener {
        void onRemoved();
    }

    public VerticalProgressView(Context context) {
        super(context);
        init(context);
    }

    public VerticalProgressView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init(context);
    }

    public void setListener(IViewRemovedListener listener) {
        mListener = listener;
    }

    private void init(Context context) {
        mContext = context;
        mRootView = inflate(context, R.layout.view_vertical_progress, this);

        setOnLongClickListener(this);
        setData();

        Button addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                createAddDialog();
            }
        });

        Button infoButton = findViewById(R.id.button_info);
        infoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                createInfoDialog();
            }
        });
    }

    @Override
    public boolean onLongClick(View v) {
        PopupMenu popup = new PopupMenu(mContext, v, Gravity.RIGHT);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.current_trainingsplan_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getTitle().equals(mContext.getString(R.string.remove))) {
            mRootView.setVisibility(View.GONE);
            Configuration.set(mContext, Configuration.SHOW_VERTICAL_PROGRESS, false);

            if(mListener != null) {
                mListener.onRemoved();
            }
        }
        return false;
    }

    public void setTitle(String name) {
        mTitle = name;
        TextView txtView = mRootView.findViewById(R.id.name);
        txtView.setText(name);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setData() {
        LineChart chart = mRootView.findViewById(R.id.chart);
        chart.clear();

        VerticalHeightReader reader = new VerticalHeightReader(getContext());
        Cursor cursor = reader.getAll();

        //used to set the axis to the highest value
        long maxValue = -1;

        //used to only add the newest entry of a day
        long prevDayTimestamp = -1;

        if(cursor.getCount() > 0) {
            List<Entry> entries = new ArrayList<>();

            while (cursor.moveToNext()) {
                long dayTimestamp = cursor.getLong(cursor.getColumnIndexOrThrow(VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_DATE));
                double vertical = cursor.getDouble(cursor.getColumnIndexOrThrow(VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_HEIGHT));
                if(useImperialValues()) {
                    vertical = cmToInches(vertical);
                }

                if(dayTimestamp == prevDayTimestamp) {
                    entries.remove(entries.size()-1);
                }

                if(vertical > maxValue) {
                    maxValue = (long) vertical;
                }

                entries.add(new Entry(dayTimestamp, (int) vertical));
                prevDayTimestamp = dayTimestamp;
            }

            LineDataSet dataSet = new LineDataSet(entries, "Progress");
            dataSet.setLineWidth(2);
            dataSet.setColor(mContext.getResources().getColor(R.color.colorAccentSecondary));
            dataSet.setMode(LineDataSet.Mode.LINEAR);
            LineData lineData = new LineData(dataSet);
            lineData.setDrawValues(false);
            chart.setData(lineData);
            chart.getXAxis().setTextColor(mContext.getResources().getColor(R.color.lightGrey));
            chart.getAxisRight().setEnabled(false);
            chart.getAxisLeft().setTextColor(mContext.getResources().getColor(R.color.lightGrey));
            chart.getLegend().setEnabled(false);
            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chart.getDescription().setEnabled(false);
            chart.getXAxis().setValueFormatter(new DayAxisValueFormatter(chart));
            chart.setOnLongClickListener(this);
            chart.notifyDataSetChanged();
            chart.setClickable(false);
            chart.getAxisLeft().setValueFormatter(new UnitAxisValueFormatter());
            chart.getAxisLeft().setAxisMinimum(0);
            chart.getAxisLeft().setAxisMaximum(maxValue + (long) (maxValue * 0.2));
            chart.getXAxis().setAxisMinimum(entries.get(0).getX() - 1);
            chart.getXAxis().setAxisMaximum(entries.get(entries.size()-1).getX() + 1);

            if(Configuration.isSet(mContext, "goal")) {
                try {
                    float goal = Float.valueOf(SharedPreferencesManager.getString(mContext, "goal", ""));
                    LimitLine ll = new LimitLine(goal, "Goal");
                    ll.setLineColor(mContext.getResources().getColor(R.color.colorAccentSecondary));
                    ll.setLineWidth(2f);
                    ll.setTextColor(mContext.getResources().getColor(R.color.lightGrey));
                    ll.setTextSize(12f);

                    chart.getAxisLeft().addLimitLine(ll);

                    //set the max if needed
                    float alternativeMax = goal + (long)(goal * 0.2);
                    if(chart.getAxisLeft().getAxisMaximum() < alternativeMax) {
                        chart.getAxisLeft().setAxisMaximum(alternativeMax);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //TODO indicate or hide the view
        }
    }

    double cmToInches(double cm) {
        return cm / 2.54;
    }

    public class UnitAxisValueFormatter implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            if(useImperialValues()) {
                return (int) value + " in";
            } else {
                return (int) value + " cm";
            }
        }
    }

    public class DayAxisValueFormatter implements IAxisValueFormatter
    {
        private final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        public DayAxisValueFormatter(BarLineChartBase<?> chart) {
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(TimeUnit.DAYS.toMillis((long)value));
            String date = DateFormat.format("dd", cal).toString();
            date += " " + MONTHS[cal.get(Calendar.MONTH)];
            return date;
        }
    }

    private void createAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.DialogTheme));
        builder.setTitle(mContext.getString(R.string.vertical_progress_input_hint));

        final LinearLayoutCompat layoutCompat = new LinearLayoutCompat(mContext);
        builder.setView(layoutCompat);
        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AppCompatEditText textView = layoutCompat.findViewById(R.id.edit_text);
                final String inputString = textView.getText().toString();
                if(inputString.isEmpty()) {
                    dialog.cancel();
                } else {
                    VerticalHeightWriter writer = new VerticalHeightWriter(mContext);
                    if(Configuration.getString(mContext, Configuration.UNIT_LOCAL_KEY, Configuration.UnitLocal.METRIC.name())
                            .equals(Configuration.UnitLocal.METRIC.name())) {
                        writer.add(TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()), Double.valueOf(inputString));
                    } else {
                        writer.add(TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()), inchesToCm(Double.valueOf(inputString)));
                    }

                    setData();
                }
            }
        });
        builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();

        View view = dialog.getLayoutInflater().inflate(R.layout.alertdialog_edittext, layoutCompat);
        final AppCompatEditText input = view.findViewById(R.id.edit_text);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        if(Configuration.getString(mContext, Configuration.UNIT_LOCAL_KEY, Configuration.UnitLocal.METRIC.name())
                .equals(Configuration.UnitLocal.METRIC.name())) {
            input.setHint(mContext.getResources().getString(R.string.reach_height_input_hint,
                    mContext.getResources().getString(R.string.centimeters_short)));
        } else {
            input.setHint(mContext.getResources().getString(R.string.reach_height_input_hint,
                    mContext.getResources().getString(R.string.inches_short)));
        }

        dialog.show();
    }

    private void createInfoDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.DialogTheme));
        builder.setTitle(mContext.getString(R.string.vertical_progress_info_hint));
        builder.setMessage(R.string.vertical_progress_info_show_youtube_video);
        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                watchYoutubeVideo(mContext, "zMQR6T2HVY0");
            }
        });

        builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    double inchesToCm(double inches) {
        return inches * 2.54;
    }

    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    private boolean useImperialValues() {
        return Configuration.getString(mContext, Configuration.UNIT_LOCAL_KEY, Configuration.UnitLocal.METRIC.name())
                .equals(Configuration.UnitLocal.IMPERIAL.name());
    }
}
