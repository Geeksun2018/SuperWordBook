package com.geeksun.superwordbook.ui.tools;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geeksun.superwordbook.R;
import com.geeksun.superwordbook.model.SearchAmount;
import com.geeksun.superwordbook.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    private TextView textView;

    public LineChartView lineChart;
//    String[] date = {"10-22","11-22","12-22","1-22","6-22","5-23","5-22","6-22","5-23","5-20"};//X轴的标注
//    int[] score= {50,42,90,33,10,74,22,18,79,206};//图表的数据点
    List<SearchAmount> list;
    //int[] score= {0,0,0,0,0,0,0,0,0,0};
    public List<PointValue> mPointValues = new ArrayList<PointValue>();
    public List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    final Handler handler = new Handler();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lineChart = getActivity().findViewById(R.id.line_chart);
        textView = getActivity().findViewById(R.id.average_count);

        initData();



    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        return root;
    }

    public void initData(){
        HttpUtil.sendOkHttpRequest("http://47.107.108.67:8080/getSearchAmount?uid=1", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //给用户显示网络异常！
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONObject jsonObject = JSON.parseObject(json);
                list = jsonObject.getJSONObject("data").getJSONArray("searchAmounts").toJavaList(SearchAmount.class);
                Collections.sort(list);
                handler.post(runnableUi);
            }
        });
    }

    public int averageCount(){
        int count = 0;
        for(int i = 0;i < list.size();i++){
            count += list.get(i).getAmount();
        }
        return count / list.size();
    }

    /**
     * 设置X 轴的显示
     */
    private void getAxisXLables() {
        for (int i = 0; i < list.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(list.get(i).getDate()));
        }
    }
    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints() {
        for (int i = 0; i < list.size(); i++) {
            mPointValues.add(new PointValue(i,list.get(i).getAmount()));
        }
    }

    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        //      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.GRAY);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线
        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边
        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 10;
        lineChart.setCurrentViewport(v);
    }

    Runnable runnableUi = new  Runnable(){
        @Override
        public void run() {
            //更新界面
            getAxisXLables();//获取x轴的标注
            getAxisPoints();//获取坐标点
            initLineChart();//初始化
            textView.setText(String.valueOf(averageCount()));
        }

    };
}