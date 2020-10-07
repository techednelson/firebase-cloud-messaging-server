package com.spring.boot.fcm.server.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.spring.boot.fcm.server.dto.NotificationRequestDto;
import com.spring.boot.fcm.server.dto.SubscriptionRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;


@Service
public class NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

	@Value("${app.firebase-config}")
	private String firebaseConfig;

	private FirebaseApp firebaseApp;

	@PostConstruct
	private void initialize() {
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream())).build();

			if (FirebaseApp.getApps().isEmpty()) {
				this.firebaseApp = FirebaseApp.initializeApp(options);
			} else {
				this.firebaseApp = FirebaseApp.getInstance();
			}
		} catch (IOException e) {
			logger.error("Create FirebaseApp Error", e);
		}
	}

	public String subscribeToTopic(SubscriptionRequestDto subscriptionRequestDto) {
		String response;
		try {
			FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(subscriptionRequestDto.getTokens(),
				subscriptionRequestDto.getTopicName());
			response = "Subscription successful";
		} catch (FirebaseMessagingException e) {
			logger.error("Firebase subscribe to topic fail", e);
			response = "Firebase subscribe to topic fail" + e;
		}
		return response;
	}

	public String unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
		String response;
		try {
			FirebaseMessaging.getInstance(firebaseApp).unsubscribeFromTopic(subscriptionRequestDto.getTokens(),
				subscriptionRequestDto.getTopicName());
			response = "unsubscription successful";
		} catch (FirebaseMessagingException e) {
			logger.error("Firebase unsubscribe from topic fail", e);
			response = "Firebase unsubscribe from topic fail" + e;
		}
		return response;
	}

	public String sendPushNotificationToDevice(NotificationRequestDto notificationRequestDto) {
		Message message = Message.builder()
			.setToken(notificationRequestDto.getTarget())
			.setNotification(new Notification(notificationRequestDto.getTitle(), notificationRequestDto.getBody()))
			.putData("content", notificationRequestDto.getTitle())
			.putData("body", notificationRequestDto.getBody())
			.build();

		String response;
		try {
			response = FirebaseMessaging.getInstance().send(message);
		} catch (FirebaseMessagingException e) {
			logger.error("Fail to send firebase notification", e);
			response = "Fail to send firebase notification " + e;
		}

		return response;
	}

	public String sendPushNotificationToTopic(NotificationRequestDto notificationRequestDto) {
		Message message = Message.builder()
			.setTopic(notificationRequestDto.getTarget())
			.setNotification(new Notification(notificationRequestDto.getTitle(), notificationRequestDto.getBody()))
			.putData("content", notificationRequestDto.getTitle())
			.putData("body", notificationRequestDto.getBody())
			.build();

		String response;
		try {
			response = FirebaseMessaging.getInstance().send(message);
		} catch (FirebaseMessagingException e) {
			logger.error("Fail to send firebase notification", e);
			response = "Fail to send firebase notification " + e;
		}

		return response;
	}
}
