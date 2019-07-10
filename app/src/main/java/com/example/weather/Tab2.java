package com.example.weather;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Tab2 extends Fragment  {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        LineChartView lineChartView;
////        Returning the layout file after inflating
////        Change R.layout.tab1 in you classes
        View rootview = inflater.inflate(R.layout.tab2 , container , false);
//
////        Chart Chart;
//////        RelativeLayout root  = rootview.findViewById(R.id.root);
//////        LineChartView chart = new LineChartView(getContext());
//////        root.addView(chart);
////        Chart.setInteractive(boolean isInteractive);
////        Chart.setZoomType(ZoomType zoomType);
////        Chart.setContainerScrollEnabled(boolean isEnabled, ContainerScrollType type);
////
////
////        ChartData.setAxisXBottom(Axis axisX);
////        ColumnChartData.setStacked(boolean isStacked);
////        Line.setStrokeWidth(int strokeWidthDp);
//
//
//
//        public void drawSinAbsChart() {
//            String decimalPattern = "#.##";
//            DecimalFormat decimalFormat = new DecimalFormat(decimalPattern);
//
//            lineChartView = (LineChartView) findViewById(R.id.chart);
//
//            List<PointValue> values = new ArrayList<PointValue>();
//
//            PointValue tempPointValue;
//            for (float i = 0; i <= 360.0; i+= 15.0f) {
//                tempPointValue = new PointValue(i, Math.abs((float)Math.sin(Math.toRadians(i))));
//                tempPointValue.setLabel(decimalFormat
//
//                        .format(Math.abs((float)Math.sin(Math.toRadians(i)))));
//                values.add(tempPointValue);
//            }
//
//            Line line = new Line(values)
//
//                    .setColor(Color.BLUE)
//
//                    .setCubic(false)
//
//                    .setHasPoints(true).setHasLabels(true);
//            List<Line> lines = new ArrayList<Line>();
//            lines.add(line);
//
//            LineChartData data = new LineChartData();
//            data.setLines(lines);
//
//            List<AxisValue> axisValuesForX = new ArrayList<>();
//            List<AxisValue> axisValuesForY = new ArrayList<>();
//            AxisValue tempAxisValue;
//            for (float i = 0; i <= 360.0f; i += 30.0f){
//                tempAxisValue = new AxisValue(i);
//                tempAxisValue.setLabel(i+"\u00b0");
//                axisValuesForX.add(tempAxisValue);
//            }
//
//            for (float i = 0.0f; i <= 1.00f; i += 0.25f){
//                tempAxisValue = new AxisValue(i);
//                tempAxisValue.setLabel(""+i);
//                axisValuesForY.add(tempAxisValue);
//            }
//
//            Axis xAxis = new Axis(axisValuesForX);
//            Axis yAxis = new Axis(axisValuesForY);
//            data.setAxisXBottom(xAxis);
//            data.setAxisYLeft(yAxis);
//
//
//            lineChartView.setLineChartData(data);
//
//
//        }

        return rootview;
 }
}
