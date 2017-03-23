package com.rxandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.rxandroid.R;
import com.rxandroid.model.WeatherForecast;
import com.util.AndroidLogger;
import com.util.StringUtils;
import com.util.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Android:网络请求
 * Created by Travis1022 on 2017/3/23.
 */
public class NetWorkFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.et_city)
    EditText mEtCity;            //城市
    @Bind(R.id.tv_time)
    TextView mTvTime;            //时间
    @Bind(R.id.tv_wind)
    TextView mTvWind;            //风力
    @Bind(R.id.tv_temp)
    TextView mTvTemp;            //温度
    @Bind(R.id.tv_weather)
    TextView mTvWeather;         //天气
    @Bind(R.id.tv_desc)
    TextView mTvDesc;
    @Bind(R.id.rv_weather)
    RecyclerView mRvWeather;     //未来一周天气


    private OkHttpClient mOkHttpClient;
    private static final String weatherUrl = "http://v.juhe.cn/weather/index?";
    private String cityName;

    //result
    private WeatherForecast weather;
    private WeatherForecast.ResultBean result;
    private BasicAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_network, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void postAsyHttp(String cityName, String format, String key) {
        mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("cityname", cityName)
                .add("format", format)
                .add("key", key)
                .build();
        Request request = new Request.Builder()
                .url(weatherUrl + "&format=" + format + "&cityname=" + cityName + "&key=" + key)
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                AndroidLogger.i("NetWorkFragment", str);

                weather = new WeatherForecast();
                weather = JSON.parseObject(str, WeatherForecast.class);
                result = weather.getResult();
                //Fragment中runOnUiThread不可以直接用，必须先getActivity
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToastShort(getActivity(), "Post请求成功");
                        setData(result);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_confirm)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                cityName = mEtCity.getText().toString();
                if (!StringUtils.isEmpty(cityName)) {
                    postAsyHttp(cityName, "2", "e21ed7d28bccd22e16aa15f824415df9");
                } else {
                    ToastUtils.showToastShort(getActivity(), "城市名称为空，请输入");
                }
                break;
        }
    }

    public void setData(WeatherForecast.ResultBean result) {
        WeatherForecast.ResultBean.TodayBean today = result.getToday();
        WeatherForecast.ResultBean.SkBean skbean = result.getSk();
        mTvTime.setText(today.getDate_y() + skbean.getTime());
        mTvTemp.setText(today.getTemperature());
        mTvWind.setText(skbean.getWind_direction() + skbean.getWind_strength());
        mTvWeather.setText(today.getWeather());
        mTvDesc.setText(today.getDressing_advice());
        //RecyclerView
        mAdapter = new BasicAdapter(result.getFuture());
        mRvWeather.setAdapter(mAdapter);
        mRvWeather.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
    }


    public class BasicAdapter extends RecyclerView.Adapter<WeatherViewHolder> {
        private List<WeatherForecast.ResultBean.FutureBean> futureLists;

        public BasicAdapter(List<WeatherForecast.ResultBean.FutureBean> futureList) {
            futureLists = new ArrayList<>();
            this.futureLists = futureList;
        }

        @Override
        public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_weather, parent, false);
            return new WeatherViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(WeatherViewHolder holder, int position) {
            //逆序输出数据
            holder.mTvDate.setText(futureLists.get(futureLists.size() - position - 1).getDate());
            holder.mTvWeek.setText(futureLists.get(futureLists.size() - position - 1).getWeek());
            holder.mTvFutureWind.setText(futureLists.get(futureLists.size() - position - 1).getWind());
            holder.mTvFutureWeather.setText(futureLists.get(futureLists.size() - position - 1).getWeather());

        }


        @Override
        public int getItemCount() {
            return futureLists.size();
        }
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_date)
        TextView mTvDate;           //时间
        @Bind(R.id.tv_week)
        TextView mTvWeek;           //星期
        @Bind(R.id.tv_future_wind)
        TextView mTvFutureWind;     //风力
        @Bind(R.id.tv_future_weather)
        TextView mTvFutureWeather;  //天气

        public WeatherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
