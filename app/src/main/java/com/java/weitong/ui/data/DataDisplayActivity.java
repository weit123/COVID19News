package com.java.weitong.ui.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.java.weitong.R;
import com.java.weitong.db.EpidemicData;
import com.java.weitong.db.EpidemicDataList;
import com.java.weitong.db.News;

import java.util.ArrayList;

public class DataDisplayActivity extends AppCompatActivity {

    private LineChart chart;
    private final int[] colors = new int[] {
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };
    private String region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_display);

        Intent intent = getIntent();
        region = intent.getStringExtra("region");

        chart = findViewById(R.id.chart1);

        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);

        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setDrawAxisLine(true);
        chart.getAxisLeft().setDrawGridLines(true);
        chart.getXAxis().setDrawAxisLine(true);
        chart.getXAxis().setSpaceMin(1);
//        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawGridLines(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

//        Legend l = chart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);

        EpidemicData epidata = EpidemicDataList.getData(region);


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        ArrayList<Entry> confirm_data = new ArrayList<>();
        ArrayList<Entry> cured_data = new ArrayList<>();
        ArrayList<Entry> dead_data = new ArrayList<>();
        for (int i = 0; i < epidata.getConfirmed().size(); i ++) {
            confirm_data.add(new Entry(i, epidata.getConfirmed().get(i)));
            cured_data.add(new Entry(i, epidata.getCured().get(i)));
            dead_data.add(new Entry(i, epidata.getDead().get(i)));
        }

        LineDataSet d = new LineDataSet(confirm_data, "确诊数");
        d.setLineWidth(2.5f);
        d.setCircleRadius(4f);
        int color = colors[0];
        d.setColor(color);
        d.setCircleColor(color);
        d.setDrawValues(false);
        dataSets.add(d);

        d = new LineDataSet(cured_data, "治愈数");
        color = colors[1];
        d.setColor(color);
        d.setCircleColor(color);
        dataSets.add(d);

        d = new LineDataSet(dead_data, "死亡数");
        color = colors[2];
        d.setColor(color);
        d.setCircleColor(color);
        dataSets.add(d);


        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
    }
}