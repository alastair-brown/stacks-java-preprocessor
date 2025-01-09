package com.amido.stacks.azure.servicebus.publisher;

import com.amido.stacks.core.messaging.event.ApplicationEvent;
import com.amido.stacks.core.messaging.listen.ApplicationEventListener;
import com.amido.stacks.core.messaging.publish.ApplicationEventPublisherWithListener;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConditionalOnProperty(value = "azure.servicebus.enabled", havingValue = "true")
public class UpdateEventServiceBusDispatcherWithListener
        extends ApplicationEventPublisherWithListener {

    private final Logger logger = LoggerFactory.getLogger(UpdateEventServiceBusDispatcherWithListener.class);

    private final ServiceBusSenderAsyncClient topicAsyncSender;
    private final JsonMapper jsonMapper;

    public UpdateEventServiceBusDispatcherWithListener(
            ServiceBusSenderAsyncClient topicAsyncSender,
            JsonMapper jsonMapper,
            ApplicationEventListener applicationEventListener) {
        super(applicationEventListener);
        super.listen();
        this.topicAsyncSender = topicAsyncSender;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public void publish(ApplicationEvent applicationEvent) {

        try {

            ServiceBusMessage serviceBusMessage = createServiceBusMessageFromEvent(applicationEvent);

            logger.debug("Message sending: Id = {}", serviceBusMessage.getMessageId());

            topicAsyncSender
                    .sendMessage(serviceBusMessage)
                    .subscribe(
                            unused ->
                                    logger.info("Message acknowledged: Id = {}", serviceBusMessage.getMessageId()),
                            error ->
                                    logger.error("Error when sending message: Id = {}, error: {}",
                                            serviceBusMessage.getMessageId(),
                                            error.toString()),
                            () -> logger.info("Message sent: Id = {}", serviceBusMessage.getMessageId()));

        } catch (JsonProcessingException e) {
            logger.error("Unable to process ApplicationEvent", e);
        }
    }

    protected ServiceBusMessage createServiceBusMessageFromEvent(ApplicationEvent applicationEvent)
            throws JsonProcessingException {
        String content = jsonMapper.writeValueAsString(applicationEvent);
        ServiceBusMessage message = new ServiceBusMessage(content);
        message.setContentType("application/json");
        message.setMessageId(applicationEvent.getId().toString());
        message.setTimeToLive(Duration.ofMinutes(2));
        message.setSubject(applicationEvent.getClass().getSimpleName());
        return message;
    }
}
