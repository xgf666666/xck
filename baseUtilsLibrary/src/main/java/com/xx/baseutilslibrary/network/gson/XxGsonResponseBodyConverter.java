/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xx.baseutilslibrary.network.gson;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.xx.baseutilslibrary.network.entity.BaseResponseEntity;
import com.xx.baseutilslibrary.network.entity.BaseResponseStatusEntity;
import com.xx.baseutilslibrary.network.exception.ApiFaileException;
import com.xx.baseutilslibrary.network.exception.TokenInvalidException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class XxGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private static final String TAG = "XxGsonResponseBodyConve";
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    XxGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String valueString = value.string();
            valueString = valueString.replaceAll(":null", ":\"\"");
            Log.e(TAG, valueString);//输出替换后的json数据
            BaseResponseStatusEntity baseResponseEntity = gson.fromJson(valueString, BaseResponseStatusEntity.class);
//            if (baseResponseEntity.getStatus().equals(BaseResponseEntity.Companion.getFAILE())) {
//                //错误情况不解析data数据,防止数据成功失败返回数据格式不一致的问题
//                throw new ApiFaileException(baseResponseEntity.getMsg());
//            }else
                if (baseResponseEntity.getCode().equals("40001")) {
                throw new TokenInvalidException("40001");
            }else if (baseResponseEntity.getCode().equals("40004")||baseResponseEntity.getCode().equals("40005")){
                throw new ApiFaileException(baseResponseEntity.getMsg());
            } else {
                return adapter.fromJson(valueString);
            }
        } finally {
            value.close();
        }
    }
}
