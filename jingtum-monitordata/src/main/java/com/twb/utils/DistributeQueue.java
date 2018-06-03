package com.twb.utils;

import java.util.concurrent.LinkedBlockingQueue;

public class DistributeQueue
{
	private static LinkedBlockingQueue DISTRIBUTE_QUEUE = new LinkedBlockingQueue();
	
	public static void add(Object obj) throws InterruptedException
	{
		DISTRIBUTE_QUEUE.put(obj);
	}
	
	public static Object get() throws InterruptedException
	{
		return DISTRIBUTE_QUEUE.take();
	}
}
