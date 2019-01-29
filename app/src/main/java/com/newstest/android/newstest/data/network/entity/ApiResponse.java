package com.newstest.android.newstest.data.network.entity;

import com.testapp.android.testapp.utils.Status;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.testapp.android.testapp.utils.Status.ERROR;
import static com.testapp.android.testapp.utils.Status.LOADING;
import static com.testapp.android.testapp.utils.Status.SUCCESS;


public class ApiResponse {

    public final Status status;

    @Nullable
    public final List<FlowerItem> data;

    @Nullable
    public final Throwable error;

    private ApiResponse(Status status, @Nullable List<FlowerItem> data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null);
    }

    public static ApiResponse success(@NonNull List<FlowerItem> data) {
        return new ApiResponse(SUCCESS, data, null);
    }

    public static ApiResponse error(@NonNull Throwable error) {
        return new ApiResponse(ERROR, null, error);
    }

}
