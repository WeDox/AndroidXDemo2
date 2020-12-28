package com.onedream.androidxdemo2.common.vm.random_poetry;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * @author jdallen
 * @since 2020/12/24
 */
public class RandomPoetryViewModelFactory implements ViewModelProvider.Factory {

    public RandomPoetryViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RandomPoetryViewModel();
    }
}
