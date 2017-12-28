package comq.example.kita.kanemochiandroid;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        LineChart lineChart = (LineChart) findViewById(R.id.chart);

        ArrayList<Entry> entries = new ArrayList<Entry>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(2f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));

        LineDataSet lineDataSet = new LineDataSet(entries, "# of Ex-Rates");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        lineDataSet.setDrawCubic(true);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(lineDataSet);

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        LineData lineData = new LineData(labels, lineDataSet);
        lineChart.setData(lineData); // set the data and list of lables into chart

/*        MarkerView mv = new CustomMarkerView(this,R.layout.content_marker_view);
        lineChart.setMarkerView(mv);
        lineChart.setDrawMarkerViews(true);

        YAxis y = lineChart.getAxisLeft();
        y.setTextColor(Color.WHITE);

        XAxis x = lineChart.getXAxis();
        x.setTextColor(Color.WHITE);

        Legend legend = lineChart.getLegend();
        legend.setTextColor(Color.WHITE);

        lineChart.animateXY(2000, 2000);*/ //애니메이션 기능 활성화
        lineChart.setData(lineData);
        lineChart.invalidate();

    }
}
