package com.hackathon.lib;

import com.hackathon.construct.TimeMaximum;

import java.util.Date;

public class FormatDate {
	public static String formatDate(Long date) {
		long curTime = System.currentTimeMillis();

		long regTime = date;

		long diffTime = (curTime - regTime) / 1000;

		String msg;
		if (diffTime < TimeMaximum.SEC) {
			msg = "방금 전";

		} else if ((diffTime /= TimeMaximum.SEC) < TimeMaximum.MIN) {
			msg = diffTime + "분 전";

		} else if ((diffTime /= TimeMaximum.MIN) < TimeMaximum.HOUR) {
			msg = (diffTime) + "시간 전";

		} else if ((diffTime /= TimeMaximum.HOUR) < TimeMaximum.DAY) {
			msg = (diffTime) + "일 전";

		} else if ((diffTime /= TimeMaximum.DAY) < TimeMaximum.MONTH) {
			msg = (diffTime) + "달 전";
		} else {
			msg = (diffTime) + "년 전";
		}

		return msg;

	}
}
