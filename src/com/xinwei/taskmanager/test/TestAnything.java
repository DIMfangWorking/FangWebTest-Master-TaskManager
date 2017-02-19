package com.xinwei.taskmanager.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestAnything {
	public static void main(String[] args) {
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		System.out.println(date);
	}

	private static void testSplit() {
		String ss = "{\"type\":\"real\",\"env_type\":\"LTE\",\"revision\":null,\"code_path\":null,\"bin_file\":\"ftp://172.31.2.16/LTE-LM13/McLTE_macro_V3.1.8.2T100/software/McLTE.3.1.8.2T100.BIN\",\"ci_type\":\"Construct_Per_day\",\"date\":\"2016-11-17T09:18:10.005Z\"}|{\"user\":null,\"type\":\"real\",\"revision\":null,\"code_path\":null,\"test_group\":null,\"resource\":null,\"measured_object\":{\"exe_file\":\"/LTE-LM13/McLTE_macro_V3.1.8.2T100/software/McLTE.3.1.8.2T100.BIN\",\"db_file\":null,\"url\":\"ftp://172.31.2.16/LTE-LM13/McLTE_macro_V3.1.8.2T100/software/McLTE.3.1.8.2T100.BIN\",\"ftp\":{\"host\":\"172.31.2.16\",\"port\":21,\"user\":null,\"password\":null,\"path\":\"/LTE-LM13/McLTE_macro_V3.1.8.2T100/software/McLTE.3.1.8.2T100.BIN\",\"original\":\"McLTE.3.1.8.2T100.BIN\"}}}";
		int aa = ss.indexOf("|");
		System.out.println(ss.substring(0, aa));
		System.out.println(ss.substring(aa + 1, ss.length()));
	}
}
