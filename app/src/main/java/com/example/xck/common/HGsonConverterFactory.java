package com.example.xck.common;


import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity;
import com.xx.baseutilslibrary.network.entity.BaseResponseStatusEntity;
import com.xx.baseutilslibrary.network.exception.ApiFaileException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * HGsonConverterFactory
 * 沉迷学习不能自拔
 * Describe：
 * Created by 雷小星🍀 on 2018/5/4 11:12.
 */

public class HGsonConverterFactory extends Converter.Factory {

    private final Gson gson;

    private HGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    public static HGsonConverterFactory create() {
        return create(new Gson());
    }

    public static HGsonConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new HGsonConverterFactory(gson);
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new HGsonResponseBodyConverter<>(adapter);
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new HGsonRequestBodyConverter<>(adapter);
    }

    private class HGsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private final Charset UTF_8 = Charset.forName("UTF-8");

        private final TypeAdapter<T> adapter;

        HGsonRequestBodyConverter(TypeAdapter<T> adapter) {
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(@NonNull T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = gson.newJsonWriter(writer);
            adapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }

    private class HGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final TypeAdapter<T> adapter;

        HGsonResponseBodyConverter(TypeAdapter<T> adapter) {
            this.adapter = adapter;
        }

        @Override
        public T convert(@NonNull ResponseBody value) throws IOException {
            try {
                String valueString = value.string();
                valueString = valueString.replaceAll(":null", ":\"\"");
                BaseResponseStatusEntity baseResponseEntity = gson.fromJson(valueString, BaseResponseStatusEntity.class);
                if (baseResponseEntity == null) {
                    throw new ApiFaileException("服务器未返回内容");
                }
//                else if (LONG_TOKEN_INVALID.equals(baseResponseEntity.getCode()) || LONG_TOKEN_INVALID_.equals(baseResponseEntity.getCode())) {
//                    //长token过期,重新登录
//                    throw new InvalidLongTokenException();
//                }
                else if (Constants.SHORT_TOKEN_INVALID.equals(baseResponseEntity.getCode()) || Constants.SHORT_TOKEN_INVALID_.equals(baseResponseEntity.getCode())||"40004".equals(baseResponseEntity.getCode())) {
                    //短token过期,刷新token
//                    valueString = valueString.replace("data:[]", "data:{}");
                    return adapter.fromJson(valueString);
                } else if (baseResponseEntity.getStatus().equals(BaseResponseEntity.Companion.getFAILE())) {
                    //接口返回失败时,不继续解析data
                    throw new ApiFaileException(baseResponseEntity.getMsg());
                } else {
                    return adapter.fromJson(valueString);
                }
            } finally {
                value.close();
            }
        }
    }
}
