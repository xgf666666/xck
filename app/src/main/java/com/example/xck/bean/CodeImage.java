package com.example.xck.bean;

/**
 * author ： xiaogf
 * time    ： 2022/10/31
 * describe    ：
 */
public class CodeImage {

    /**
     * base64 : data
     */

    private String base64;
    private String key;
    private String md5;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
