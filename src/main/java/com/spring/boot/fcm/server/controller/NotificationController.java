package com.spring.boot.fcm.server.controller;

import com.spring.boot.fcm.server.dto.NotificationRequestDto;
import com.spring.boot.fcm.server.dto.SubscriptionRequestDto;
import com.spring.boot.fcm.server.service.NotificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

	private final NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	@PostMapping("/subscribe")
	public String subscribeToTopic(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
		return notificationService.subscribeToTopic(subscriptionRequestDto);
	}

	@PostMapping("/unsubscribe")
	public String unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
		return notificationService.unsubscribeFromTopic(subscriptionRequestDto);
	}

	@PostMapping("/token")
	public String sendPushNotificationToDevice(@RequestBody NotificationRequestDto notificationRequestDto) {
		return notificationService.sendPushNotificationToDevice(notificationRequestDto);
	}

	@PostMapping("/topic")
	public String sendPushNotificationToTopic(@RequestBody NotificationRequestDto notificationRequestDto) {
		return notificationService.sendPushNotificationToTopic(notificationRequestDto);
	}
}
