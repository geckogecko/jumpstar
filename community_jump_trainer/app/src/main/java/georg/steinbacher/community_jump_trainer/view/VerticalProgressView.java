package georg.steinbacher.community_jump_trainer.view;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import georg.steinbacher.community_jump_trainer.Configuration;
import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.db.VerticalHeightContract;
import georg.steinbacher.community_jump_trainer.db.VerticalHeightReader;
import georg.steinbacher.community_jump_trainer.db.VerticalHeightWriter;


/**
 * Created by georg on 07.04.18.
 */

public class VerticalProgressView extends LinearLayoutCompat implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener{
    private static final String TAG = "VerticalProgressView";

    private View mRootView;
    private Context mContext;
    private String mTitle = "";

    public VerticalProgressView(Context context) {
        super(context);
        init(context);
    }

    public VerticalProgressView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init(context);
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

        List<Entry> entries = new ArrayList<>();

        VerticalHeightReader reader = new VerticalHeightReader(getContext());
        Cursor cursor = reader.getAll();

        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_DATE));
                double vertical = cursor.getDouble(cursor.getColumnIndexOrThrow(VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_HEIGHT));
                if(useImperialValues()) {
                    vertical = cmToInches(vertical);
                }
                Log.i(TAG, "setData: " +  timestamp + " " + (int) vertical);
                entries.add(new Entry(timestamp, (int) vertical));
            }

            LineDataSet dataSet = new LineDataSet(entries, "Progress");
            dataSet.setLineWidth(2);
            dataSet.setColor(mContext.getResources().getColor(R.color.colorAccent));
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

            Log.i(TAG, "getFormattedValue: "+ value);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(TimeUnit.DAYS.toMillis((long)value));
            String date = DateFormat.format("dd", cal).toString();
            date += " " + MONTHS[cal.get(Calendar.MONTH)];
            return date;
        }
    }

    private void createAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.AlertDialogStyle));
        builder.setTitle(mContext.getString(R.string.vertical_progress_input_hint));

        final AppCompatEditText input = new AppCompatEditText(mContext);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        if(Configuration.getString(mContext, Configuration.UNIT_LOCAL_KEY, Configuration.UnitLocal.METRIC.name())
                .equals(Configuration.UnitLocal.METRIC.name())) {
            input.setHint(mContext.getResources().getString(R.string.reach_height_input_hint,
                    mContext.getResources().getString(R.string.centimeters_short)));
        } else {
            input.setHint(mContext.getResources().getString(R.string.reach_height_input_hint,
                    mContext.getResources().getString(R.string.inches_short)));
        }
        builder.setView(input);

        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final String inputString =  input.getText().toString();
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

        builder.show();
    }

    private void createInfoDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.AlertDialogStyle));
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
