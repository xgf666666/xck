package com.xx.baseutilslibrary.network.gson;


import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.annotations.Nullable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * XxGsonConverterFactory
 * (。・∀・)ノ
 * Describe：
 * Created by 雷小星🍀 on 2017/8/22 10:21.
 */

public class XxGsonConverterFactory extends Converter.Factory {

    private final Gson gson;

    private XxGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    public static XxGsonConverterFactory create() {
        return create(new Gson());
    }

    public static XxGsonConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new XxGsonConverterFactory(gson);
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new XxGsonResponseBodyConverter<>(gson, adapter);
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new XxGsonRequestBodyConverter<>(gson, adapter);
    }
}
