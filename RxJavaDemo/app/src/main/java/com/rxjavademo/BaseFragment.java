package com.rxjavademo;

import android.app.AlertDialog;
import android.app.Fragment;

import butterknife.OnClick;
import rx.Subscription;

/**
 * Created by xuanwei.tian on 2016/10/24.
 */
public abstract class BaseFragment extends Fragment {
    protected Subscription mSubscription;

    //点击按钮显示Dialog
    @OnClick(R.id.btn_tip)
    void tip() {
        new AlertDialog.Builder(getActivity())
                .setTitle(getTitleRes())
                .setView(getActivity().getLayoutInflater().inflate(getDialogRes(), null))
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unSubScribe();
    }

    /**
     * 解除订阅
     */
    private void unSubScribe() {
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    /**
     * 获取Dialog的布局
     *
     * @return
     */
    protected abstract int getDialogRes();

    /**
     * 获取Title
     *
     * @return
     */
    protected abstract int getTitleRes();
}
