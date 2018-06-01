package org.commondata.utils;

import org.commondata.data.Exclude;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class MyExclusionStrategy implements ExclusionStrategy
{

	@Override
	public boolean shouldSkipField(FieldAttributes f)
	{
		Exclude annotation = f.getAnnotation(Exclude.class);
	    if(annotation!=null)
	    {
	    	return true;
	    }
	    
	    return false;
		
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz)
	{
		return false;
	}

}
