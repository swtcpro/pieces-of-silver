package com.twb.utils;

import java.util.concurrent.LinkedBlockingQueue;

import com.twb.entity.CommitchainData;

public class CommitchainDataQueue
{
	private static LinkedBlockingQueue<CommitchainData> COMMITCHAIN_QUEUE = new LinkedBlockingQueue<CommitchainData>();
	
	public static void add(CommitchainData obj) throws InterruptedException
	{
		COMMITCHAIN_QUEUE.put(obj);
	}
	
	public static CommitchainData get() throws InterruptedException
	{
		return COMMITCHAIN_QUEUE.take();
	}
}
