package com.custom.helpers;

import com.mnt.core.helper.SaveModel;

import dto.TimeSheetBucket;

public class TimesheetBucketSave extends SaveModel<TimeSheetBucket> {

	public TimesheetBucketSave() {
		ctx = TimeSheetBucket.class;
	}

}
