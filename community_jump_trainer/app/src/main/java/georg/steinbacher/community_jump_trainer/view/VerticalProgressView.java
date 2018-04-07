package georg.steinbacher.community_jump_trainer.view;

import android.content.Context;
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

    //Todo replace with real data from database
    public void initChart() {
        LineChart chart = mRootView.findViewById(R.id.chart);

        List<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(0, 40));
        entries.add(new Entry(1, 44));
        entries.add(new Entry(2, 45));
        entries.add(new Entry(3, 47));
        entries.add(new Entry(4, 49));
        LineDataSet dataSet = new LineDataSet(entries, "Progress");
        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
    }
}
