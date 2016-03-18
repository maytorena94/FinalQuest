package com.friday.class7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    private FrameLayout mainLayout;
    private PieChart nChart;

    TextView id, max, cur,del;
    private PieChart mChart;
    private float[] yData = new float[2];
    private String[] xData = { "Occupied", "Available"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        id = (TextView)findViewById(R.id.textView2);
        //max = (TextView)findViewById(R.id.textView3);
        //cur = (TextView)findViewById(R.id.textView4);
        Intent intent = getIntent();
        id.setText("Zona: "+intent.getStringArrayExtra("values")[1]);



        yData[0]=Float.parseFloat(intent.getStringArrayExtra("values")[3]);
        yData[1]=Float.parseFloat(intent.getStringArrayExtra("values")[4]);

        mainLayout = (FrameLayout)findViewById(R.id.fl);
        nChart = new PieChart(this);

        mainLayout.addView(nChart);

        nChart.setUsePercentValues(true);


        nChart.setDrawHoleEnabled(true);
        nChart.setHoleRadius(7);
        nChart.setTransparentCircleRadius(10);

        nChart.setRotationAngle(0);
        nChart.setRotationEnabled(true);

        nChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                // display msg when value selected
                if (e == null)
                    return;

                //Toast.makeText(Main2Activity.this,
                        //xData[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });


        addData();

        //Legend l = nChart.getLegend();
        //l.setPosition(LegendPosition.RIGHT_OF_CHART);
        //l.setXEntrySpace(7);
        //l.setYEntrySpace(5);
    }

    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, " ");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        //data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.GRAY);

        nChart.setData(data);

        // undo all highlights
        nChart.highlightValues(null);

        // update pie chart
        nChart.invalidate();
    }


}
