package com.example.xck.bean;

/**
 * author ： xiaogf
 * time    ： 2023/3/11
 * describe    ：
 */
public class RegisterIM {

    /**
     * access_token : YWMtt2zh0L-nEe2qnA-puX60PlyUFL-nLU6Rk9FKo8d-nhxIG9Kgv6cR7ZzEcXcILV3nAwMAAAGGzixWUABPGgDYrOIHJx42AzgXpeTof1n31KLM-Q7FzCPoYOYA6rY4nQ
     * expires_in : 5184000
     */

    private String access_token;
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
