package com.crud.api;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Configuration
public class WebClientConfig {

	@Value("${httpConnectTimeoutInMS}")
	private Integer httpConnectTimeoutInMS;

	@Value("${httpResponseTimeoutInMS}")
	private Integer httpResponseTimeoutInMS;

	@Value("${httpReadTimeoutInMS}")
	private Integer httpReadTimeoutInMS;

	@Value("${httpConnectTimeoutInMS}")
	private Integer httpWriteTimeoutInMS;

	@Value("${httpRequestRetryCount}")
	private Integer httpRequestRetryCount;

	@Value("${httpRetryWaitTimeInSeconds}")
	private Integer httpRetryWaitTimeInSeconds;

	@Value("${httpMaxConnections}")
	private Integer httpMaxConnections;

	@Value("${httpPendingQueueSize}")
	private Integer httpPendingQueueSize;

	@Bean("service-a-web-client")
	public WebClient serviceAWebClient() {
		ConnectionProvider connectionProvider = ConnectionProvider.builder("connectionPool")
				.maxConnections(httpMaxConnections).pendingAcquireMaxCount(httpPendingQueueSize).build();

		HttpClient httpClient = HttpClient.create(connectionProvider)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, httpConnectTimeoutInMS)
				.responseTimeout(Duration.ofMillis(httpResponseTimeoutInMS))
				.doOnConnected(
						conn -> conn.addHandlerLast(new ReadTimeoutHandler(httpReadTimeoutInMS, TimeUnit.MILLISECONDS))
								.addHandlerLast(new WriteTimeoutHandler(httpWriteTimeoutInMS, TimeUnit.MILLISECONDS)))
				.wiretap("reactor.netty.http.client.HttpClient", LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL)
				.compress(true);

		return WebClient.builder().defaultHeaders(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_JSON))
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.exchangeStrategies(ExchangeStrategies.builder()
						.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(100 * 1024 * 1024)).build())
				.build();
	}

}