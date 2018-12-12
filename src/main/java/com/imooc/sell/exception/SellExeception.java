package com.imooc.sell.exception;

import com.imooc.sell.enums.ResultEnum;
import lombok.Getter;

@Getter
public class SellExeception extends RuntimeException {
    private Integer code;
    public SellExeception(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code=resultEnum.getCode();
    }
    public SellExeception(Integer code,String message){
        super(message);
        this.code=code;
    }
}
