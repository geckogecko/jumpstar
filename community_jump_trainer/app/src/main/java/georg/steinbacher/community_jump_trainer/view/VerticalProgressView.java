package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import georg.steinbacher.community_jump_trainer.Configuration;
import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.db.VerticalHeightContract;
import georg.steinbacher.community_jump_trainer.db.VerticalHeightReader;


/**
 * Created by georg on 07.04.18.
 */

public class VerticalProgressView extends CardView implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener{
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
                Log.i(TAG, "setData: " +  timestamp + " " + (int) vertical);
                entries.add(new Entry(timestamp, (int) vertical));
            }

            LineDataSet dataSet = new LineDataSet(entries, "Progress");
            dataSet.setLineWidth(2);
            dataSet.setColor(mContext.getResources().getColor(R.color.colorAccent));
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
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
        } else {
            //TODO indicate or hide the view
        }
    }

    public class DayAxisValueFormatter implements IAxisValueFormatter
    {

        protected String[] mMonths = new String[]{
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };

        private BarLineChartBase<?> chart;

        public DayAxisValueFormatter(BarLineChartBase<?> chart) {
            this.chart = chart;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            int days = (int) value;

            int year = determineYear(days);

            int month = determineMonth(days);
            String monthName = mMonths[month % mMonths.length];
            String yearName = String.valueOf(year);

            if (chart.getVisibleXRange() > 30 * 6) {

                return monthName + " " + yearName;
            } else {

                int dayOfMonth = determineDayOfMonth(days, month + 12 * (year - 2016));

                String appendix = "th";

                switch (dayOfMonth) {
                    case 1:
                        appendix = "st";
                        break;
                    case 2:
                        appendix = "nd";
                        break;
                    case 3:
                        appendix = "rd";
                        break;
                    case 21:
                        appendix = "st";
                        break;
                    case 22:
                        appendix = "nd";
                        break;
                    case 23:
                        appendix = "rd";
                        break;
                    case 31:
                        appendix = "st";
                        break;
                }

                return dayOfMonth == 0 ? "" : dayOfMonth + appendix + " " + monthName;
            }
        }

        private int getDaysForMonth(int month, int year) {

            // month is 0-based

            if (month == 1) {
                boolean is29Feb = false;

                if (year < 1582)
                    is29Feb = (year < 1 ? year + 1 : year) % 4 == 0;
                else if (year > 1582)
                    is29Feb = year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);

                return is29Feb ? 29 : 28;
            }

            if (month == 3 || month == 5 || month == 8 || month == 10)
                return 30;
            else
                return 31;
        }

        private int determineMonth(int dayOfYear) {

            int month = -1;
            int days = 0;

            while (days < dayOfYear) {
                month = month + 1;

                if (month >= 12)
                    month = 0;

                int year = determineYear(days);
                days += getDaysForMonth(month, year);
            }

            return Math.max(month, 0);
        }

        private int determineDayOfMonth(int days, int month) {

            int count = 0;
            int daysForMonths = 0;

            while (count < month) {

                int year = determineYear(daysForMonths);
                daysForMonths += getDaysForMonth(count % 12, year);
                count++;
            }

            return days - daysForMonths;
        }

        private int determineYear(int days) {

            if (days <= 366)
                return 2016;
            else if (days <= 730)
                return 2017;
            else if (days <= 1094)
                return 2018;
            else if (days <= 1458)
                return 2019;
            else
                return 2020;

        }
    }
}
