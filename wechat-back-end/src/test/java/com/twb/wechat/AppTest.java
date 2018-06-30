package com.twb.wechat;

import com.twb.wechat.utils.jingtum.CheckUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	public void testWallet()
	{
		assertFalse(CheckUtils.isValidAddress(null));
		assertFalse(CheckUtils.isValidAddress(""));
		 assertFalse(CheckUtils.isValidAddress("123"));
		 assertFalse(CheckUtils.isValidAddress("jELSEmqBspxkUtLKVUN1Vf6EjoMCAVXnF1"));
		 assertTrue(CheckUtils.isValidAddress("jELSEmqBspxkUtLKVUN1Vf6EjoMCAVXnFL"));
		 
	}
//    /**
//     * Create the test case
//     *
//     * @param testName name of the test case
//     */
//    public AppTest( String testName )
//    {
//        super( testName );
//    }
//
//    /**
//     * @return the suite of tests being tested
//     */
//    public static Test suite()
//    {
//        return new TestSuite( AppTest.class );
//    }
//
//    /**
//     * Rigourous Test :-)
//     */
//    public void testApp()
//    {
//        assertTrue( true );
//    }
}
