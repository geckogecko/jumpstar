package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import georg.steinbacher.community_jump_trainer.R;
import georg.steinbacher.community_jump_trainer.db.VerticalHeightContract;
import georg.steinbacher.community_jump_trainer.db.VerticalHeightReader;


/**
 * Created by georg on 07.04.18.
 */

public class VerticalProgressView extends CardView {

    private View mRootView;

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
        mRootView = inflate(context, R.layout.view_vertical_progress, this);

        initChart();
    }

    public void setTitle(String name) {
        mTitle = name;
        TextView txtView = mRootView.findViewById(R.id.name);
        txtView.setText(name);
    }

    public String getTitle() {
        return mTitle;
    }

    public void initChart() {
        LineChart chart = mRootView.findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<Entry>();

        VerticalHeightReader reader = new VerticalHeightReader(getContext());
        Cursor cursor = reader.getAll();

        if(cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_DATE));
                int vertical = cursor.getInt(cursor.getColumnIndexOrThrow(VerticalHeightContract.VerticalHeightEntry.COLUMN_NAME_HEIGHT));
                entries.add(new Entry(timestamp, vertical));
            }

            LineDataSet dataSet = new LineDataSet(entries, "Progress");
            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
        } else {
            //TODO indicate or hide the view
        }
    }
}
