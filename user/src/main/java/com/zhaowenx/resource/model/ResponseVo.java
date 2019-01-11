package com.zhaowenx.resource.model;

/**
 * Created by zhaowenx on 2018/8/23.
 */
public class ResponseVo<T> {

	private boolean success;
	private Integer code;
	private String msg;
	private T data;


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ResponseVo{" +
				"success=" + success +
				", code=" + code +
				", msg='" + msg + '\'' +
				", data=" + data +
				'}';
	}
}
