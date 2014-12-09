package com.hunau.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class isConnect_Internet {

	public static boolean isConnect(Context context) {
		boolean Flag = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo != null) {
			Flag = networkInfo.isAvailable();
		}
		return Flag;
	}
}
