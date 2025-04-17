package dev.patika.hrmsManager.core.utilities;


import dev.patika.hrmsManager.core.result.Result;
import dev.patika.hrmsManager.core.result.ResultData;

public class ResultHelper {

    public static <T> ResultData<T> created(T data){
        return new ResultData<>(true,Msg.CREATED,"201",data);
    }

    public static <T> ResultData<T> validateError(T data){
        return new ResultData<>(false, Msg.VALIDATE_ERROR,"400",data);
    }

    public static <T> ResultData<T> success(T data){
        return new ResultData<>(true,Msg.OK,"200",data);
    }

    public static Result Ok(){
        return new Result(true,Msg.OK,"200");
    }
    public static Result notFoundError(String msg){
        return new Result(false,msg,"404");
    }

}
