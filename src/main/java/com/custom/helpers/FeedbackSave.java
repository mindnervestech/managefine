package com.custom.helpers;

import models.Feedback;

import com.mnt.core.helper.SaveModel;

public class FeedbackSave extends SaveModel<Feedback> {

	public FeedbackSave() {
		ctx = Feedback.class;
	}

}
