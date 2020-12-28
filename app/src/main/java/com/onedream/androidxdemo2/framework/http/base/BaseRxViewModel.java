package com.onedream.androidxdemo2.framework.http.base;

import androidx.lifecycle.ViewModel;

import com.onedream.androidxdemo2.framework.utils.system.LogHelper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author jdallen
 * @since 2020/12/24
 * 基于Rx的ViewModel封装,控制订阅的生命周期
 * unsubscribe() 这个方法很重要，
 * 因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，
 * 这个引用如果不能及时被释放，将有内存泄露的风险。
 */
public class BaseRxViewModel extends ViewModel {

    protected CompositeDisposable mCompositeDisposable;

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    protected void unSubscribe() {
        printLog("跟着死亡,不再请求网络");
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        unSubscribe();
    }

    protected void printLog(String content) {
        LogHelper.e("ATU", content);
    }
}
