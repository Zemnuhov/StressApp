package com.zemnuhov.stressapp.MainResurce;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.zemnuhov.stressapp.ConstantAndHelp;
import com.zemnuhov.stressapp.R;
import com.zemnuhov.stressapp.ScanResurce.ScanFragment;

public class PhasicGraphLayout extends Fragment {
    private GraphView mainGraph;
    private TonicGraphLayout swappingGraph;
    private final LineGraphSeries<DataPoint> seriesNormal = new LineGraphSeries<>(new DataPoint[]{});
    private final PointsGraphSeries<DataPoint> seriesPeaks = new PointsGraphSeries<>(new DataPoint[]{});
    private Integer i=0;
    private ImageView swapGraphButton;

    public static PhasicGraphLayout newInstance() {

        Bundle args = new Bundle();

        PhasicGraphLayout fragment = new PhasicGraphLayout();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_phasic_graph_layout,container,false);
        mainGraph=view.findViewById(R.id.phasic_graph_main);
        swapGraphButton=view.findViewById(R.id.phasic_swap_button);
        swapGraphButton.setOnClickListener(v -> {
            getFragmentManager().beginTransaction().
                    replace(R.id.graph_fragment_in_main, swappingGraph).
                    commit();
        });
        init();
        return view;
    }

    private void init(){
        mainGraph.addSeries(seriesNormal);
        mainGraph.addSeries(seriesPeaks);

        mainGraph.getViewport().setYAxisBoundsManual(true);
        mainGraph.getViewport().setXAxisBoundsManual(false);
        mainGraph.getViewport().setMinY(-3);
        mainGraph.getViewport().setMaxY(3);

        mainGraph.getViewport().setMinX(0);
        mainGraph.getViewport().setMaxX(15000);
        mainGraph.getViewport().setScalable(true);
        mainGraph.getViewport().setScrollable(true);
        mainGraph.getViewport().setScalableY(false);
        mainGraph.getViewport().setScrollableY(false);
        mainGraph.setBackgroundColor(Color.WHITE);
        mainGraph.getGridLabelRenderer().setGridColor(Color.WHITE);

        mainGraph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        mainGraph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        mainGraph.getGridLabelRenderer().setHumanRounding(false);
        mainGraph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        mainGraph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

        seriesPeaks.setColor(Color.RED);
        seriesPeaks.setSize(3);

        seriesNormal.setColor(Color.BLACK);

    }

    public void addLineSeriesValue(Double value,Long time){
        if(value>0.7){
            seriesPeaks.appendData(new DataPoint(time,value),true,100000);
        }
        seriesNormal.appendData(new DataPoint(time,value),true,100000);
    }

    public void setSwappingGraph(TonicGraphLayout swappingGraph){
        this.swappingGraph=swappingGraph;
    }
}
