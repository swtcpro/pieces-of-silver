package com.twb.utils;

import java.util.concurrent.LinkedBlockingQueue;

import com.twb.entity.CommitchainVerifyData;

public class CommitChainVerifyDataQueue
{
	private static LinkedBlockingQueue<CommitchainVerifyData> QUEUE = new LinkedBlockingQueue<CommitchainVerifyData>();
	
	public static void add(CommitchainVerifyData obj) throws InterruptedException
	{
		QUEUE.put(obj);
	}
	
	public static CommitchainVerifyData get() throws InterruptedException
	{
		return QUEUE.take();
	}
}
