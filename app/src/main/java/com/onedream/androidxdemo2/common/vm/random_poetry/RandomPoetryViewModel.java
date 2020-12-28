package com.onedream.androidxdemo2.common.vm.random_poetry;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.onedream.androidxdemo2.common.bean.random_poetry.RandomPoetryBean;
import com.onedream.androidxdemo2.framework.http.base.BaseRxViewModel;
import com.onedream.androidxdemo2.framework.http.custome.BodyOut;
import com.onedream.androidxdemo2.framework.http.custome.MovieApi;
import com.onedream.androidxdemo2.framework.utils.json_parse.JacksonUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author jdallen
 * @since 2020/12/24
 */
public class RandomPoetryViewModel extends BaseRxViewModel {
    private MutableLiveData<Integer> pageNumLiveData = new MutableLiveData<>();
    //
    public LiveData<List<RandomPoetryBean>> resultListDataLiveData = Transformations.switchMap(pageNumLiveData, new Function<Integer, LiveData<List<RandomPoetryBean>>>() {
        @Override
        public LiveData<List<RandomPoetryBean>> apply(Integer pageNum) {
            return createApiLiveData(pageNum);
        }
    });


    public void getListDataByPageNum(int pageNum) {
        //请求第10页
        pageNumLiveData.setValue(pageNum);
    }


    private LiveData<List<RandomPoetryBean>> createApiLiveData(int pageNum) {
        printLog("开始请求网络12第" + pageNum + "页");
        final MediatorLiveData<List<RandomPoetryBean>> mediatorLiveData = new MediatorLiveData<>();
        MovieApi.getInstance().sendRandomPoetry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BodyOut>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        addSubscribe(disposable);//防止泄露
                    }

                    @Override
                    public void onNext(BodyOut bodyOut) {
                        printLog("请求网络成功");
                        if (bodyOut.isSuccess()) {
                            List<RandomPoetryBean> dataList = new ArrayList<>();
                            List<RandomPoetryBean> apiDataList = JacksonUtils.parseObjectList(bodyOut.getData(), RandomPoetryBean.class);
                            if (null != apiDataList && apiDataList.size() > 0) {
                                dataList.addAll(apiDataList);
                            }
                            mediatorLiveData.setValue(dataList);
                        } else {
                            printLog("请求网络成功,但接口返回错误信息：" + bodyOut.getApiMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        printLog("请求网络失败" + e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        return mediatorLiveData;
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        printLog("MyViewModel我跟着生命周期死了");
    }
}
