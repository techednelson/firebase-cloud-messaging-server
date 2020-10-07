package com.spring.boot.fcm.server.dto;

import java.io.Serializable;

public class NotificationRequestDto implements Serializable {

	private static final long serialVersionUID = -1405010687834208334L;
	private String target;
	private String title;
	private String body;

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
