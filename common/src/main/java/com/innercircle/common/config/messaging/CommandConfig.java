package com.innercircle.common.config.messaging;

import com.innercircle.common.application.Command;
import com.innercircle.common.application.CommandGateway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;

@Configuration
@ConditionalOnProperty(prefix = "common.messaging.command", name = "enabled", havingValue = "true")
@IntegrationComponentScan(basePackageClasses = {CommandGateway.class})
public class CommandConfig {

	public static final String REQUEST_CHANNEL_NAME = "requestChannel";

	@Bean(REQUEST_CHANNEL_NAME)
	public MessageChannel requestChannel() {
		return new DirectChannel();
	}

	@Bean
	public IntegrationFlow commandChannelRouter() {
		return IntegrationFlow.from(REQUEST_CHANNEL_NAME)
				.route(Command.class, command -> command.getClass().getSimpleName())
				.get();
	}
}
