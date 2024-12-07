package com.innercircle.common.application;

import com.innercircle.common.config.messaging.CommandConfig;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = CommandConfig.REQUEST_CHANNEL_NAME)
public interface CommandGateway {

	<T> T publish(Command command);
}
