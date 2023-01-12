package com.esa.aspects;

import com.esa.domain.Logging;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Topic;
import java.util.Arrays;

@Aspect
@Component
public class AspectLogging {

    private JmsTemplate jmsTemplate;

    private Topic topic;

    @Autowired
    public AspectLogging(JmsTemplate jmsTemplate) throws JMSException {
        this.jmsTemplate = jmsTemplate;

        this.topic = jmsTemplate.getConnectionFactory()
                .createConnection()
                .createSession()
                .createTopic("event");
    }

    @AfterReturning(value = "within(com.esa.services.*) && @annotation(Loggable)", returning = "returnValue")
    public void performLogging(JoinPoint joinPoint, Object returnValue) throws JMSException {
        String methodName = joinPoint.getSignature().getName();
        String[] packageSplitted = joinPoint.getTarget().getClass().getName().split("\\.", 0);

        String entity = packageSplitted[packageSplitted.length - 1].split("RepositoryService")[0];
        String arguments = Arrays.toString(joinPoint.getArgs());

        String eventType = "";
        if (methodName.equals("save")) {
            eventType += "SAVE";
        } else if (methodName.equals("delete")) {
            eventType += "DELETE";
        }

        Logging logging = new Logging();
        logging.setEntity(entity);
        logging.setEventType(eventType);
        logging.setSubstance(arguments);

        jmsTemplate.convertAndSend(topic, logging);
    }

}
