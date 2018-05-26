package com.jingtongsdk.bean.Jingtong;

import com.jingtongsdk.bean.JingtongResponse;
import com.jingtongsdk.utils.JingtongRequstConstants;

public class JingtongBaseResponse implements JingtongResponse
{

	private boolean success;//请求结果
	private String message;
	private String status_code;
	private String error_type;
	private String error;
	
	public boolean isSuccess()
	{
		return success;
	}
	public void setSuccess(boolean success)
	{
		this.success = success;
	}
	
	public String toString() {
        return JingtongRequstConstants.PRETTY_PRINT_GSON.toJson(this);
    }
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public String getStatus_code()
	{
		return status_code;
	}
	public void setStatus_code(String status_code)
	{
		this.status_code = status_code;
	}
	public String getError_type()
	{
		return error_type;
	}
	public void setError_type(String error_type)
	{
		this.error_type = error_type;
	}
	public String getError()
	{
		return error;
	}
	public void setError(String error)
	{
		this.error = error;
	}
	
	
}
