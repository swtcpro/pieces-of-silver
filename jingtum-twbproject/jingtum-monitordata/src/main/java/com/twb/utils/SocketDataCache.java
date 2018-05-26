package com.twb.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SocketDataCache
{
	private static Set socketDataSet =  Collections.synchronizedSet(new HashSet());

	public static boolean check(Object obj)
	{
		return socketDataSet.contains(obj);
	}
	
	public static boolean add(Object obj)
	{
		return socketDataSet.add(obj);
	}
	
	public static boolean remove(Object obj)
	{
		return socketDataSet.remove(obj);
	}
	
	public static int size()
	{
		return socketDataSet.size();
	}
	
	
	
	
}
