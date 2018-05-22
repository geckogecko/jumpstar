package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
        Log.i(TAG, "onLongClick: ");
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

        List<Entry> entries = new ArrayList<Entry>();

        VerticalHeightReader reader = new VerticalHeightReader(getContext());
        Cursor cursor = reader.getAll();

        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_DATE));
                double vertical = cursor.getDouble(cursor.getColumnIndexOrThrow(VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_HEIGHT));
                entries.add(new Entry(timestamp, (int)vertical));
            }

            LineDataSet dataSet = new LineDataSet(entries, "Progress");
            dataSet.setLineWidth(2);
            dataSet.setColor(mContext.getResources().getColor(R.color.colorAccent));
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.getAxisRight().setEnabled(false);
            chart.getLegend().setEnabled(false);
            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chart.getDescription().setEnabled(false);
            chart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
                private SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM");

                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    long millis = TimeUnit.HOURS.toMillis((long) value);
                    return mFormat.format(new Date(millis));
                }
            });
            chart.setOnLongClickListener(this);
        } else {
            //TODO indicate or hide the view
        }
    }
}
