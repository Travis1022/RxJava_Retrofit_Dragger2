package com.rxandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.rxandroid.R;
import com.util.AndroidLogger;
import com.util.StringUtils;
import com.util.ToastUtils;

import java.io.IOException;

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
    EditText mEtCity;

    private OkHttpClient mOkHttpClient;
    private static final String weatherUrl = "http://v.juhe.cn/weather/index?";

    private String cityName;

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
                //Fragment中runOnUiThread不可以直接用，必须先getActivity
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToastShort(getActivity(), "Post请求成功");
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
}
