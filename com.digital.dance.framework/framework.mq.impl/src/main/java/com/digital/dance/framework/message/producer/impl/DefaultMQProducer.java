package com.digital.dance.framework.message.producer.impl;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.digital.dance.framework.message.producer.MQProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class DefaultMQProducer
  implements MQProducer, InitializingBean
{
  private static Logger logger = LoggerFactory.getLogger(DefaultMQProducer.class);
  private String namesrvAddr;
  private String producerGroup;
  private com.alibaba.rocketmq.client.producer.DefaultMQProducer producer;
  private String retryAnotherBrokerWhenNotStoreOK;
  
  public DefaultMQProducer(){

		this.producer = new com.alibaba.rocketmq.client.producer.DefaultMQProducer();
  }

  private void initializingMQProducer()
    throws MQClientException
  {    
    if ((this.producerGroup != null) && (this.producerGroup.trim().length() > 0)) {
      this.producer.setProducerGroup(this.producerGroup);
      logger.debug("set producer group " + this.producerGroup);
    }
    if (this.retryAnotherBrokerWhenNotStoreOK != null) {
      this.retryAnotherBrokerWhenNotStoreOK = this.retryAnotherBrokerWhenNotStoreOK.trim();
      if ("true".equalsIgnoreCase(this.retryAnotherBrokerWhenNotStoreOK)) {
        this.producer.setRetryAnotherBrokerWhenNotStoreOK(true);
        logger.debug("set producer retryAnotherBrokerWhenNotStoreOK value is true");
      }
    }
    if( this.namesrvAddr != null && !"".equals(this.namesrvAddr) ){
        this.producer.setNamesrvAddr(this.namesrvAddr);
        logger.debug("set producer name server address " + this.namesrvAddr);    	
    }

  }
 
  public void afterPropertiesSet() throws Exception {
	
    initializingMQProducer();

    this.producer.start();
    logger.debug("producer start successd!");
  }

  public SendResult send(Message msg) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
    return this.producer.send(msg);
  }

  public void send(Message msg, SendCallback sendCallback) throws MQClientException, RemotingException, InterruptedException {
    this.producer.send(msg, sendCallback);
  }

  public void sendOneway(Message msg) throws MQClientException, RemotingException, InterruptedException {
    this.producer.sendOneway(msg);
  }

  public SendResult send(Message msg, MessageQueue mq) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
    return this.producer.send(msg, mq);
  }

  public void send(Message msg, MessageQueue mq, SendCallback sendCallback) throws MQClientException, RemotingException, InterruptedException {
    this.producer.send(msg, mq, sendCallback);
  }

  public void sendOneway(Message msg, MessageQueue mq) throws MQClientException, RemotingException, InterruptedException {
    this.producer.sendOneway(msg, mq);
  }

  public SendResult send(Message msg, MessageQueueSelector selector, Object arg) throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
    return this.producer.send(msg, selector, arg);
  }

  public void send(Message msg, MessageQueueSelector selector, Object arg, SendCallback sendCallback) throws MQClientException, RemotingException, InterruptedException {
    this.producer.send(msg, selector, arg, sendCallback);
  }

  public void sendOneway(Message msg, MessageQueueSelector selector, Object arg) throws MQClientException, RemotingException, InterruptedException {
    this.producer.sendOneway(msg, selector, arg);
  }

  public void setNamesrvAddr(String namesrvAddr)
  {
    this.namesrvAddr = namesrvAddr;
    try {
		initializingMQProducer();
	} catch (MQClientException e) {
		logger.error(e.getErrorMessage(), e);
	}
  }

  public void setProducerGroup(String producerGroup) {
    this.producerGroup = producerGroup;
    try {
		initializingMQProducer();
	} catch (MQClientException e) {
		logger.error(e.getErrorMessage(), e);
	}
  }

  public void setRetryAnotherBrokerWhenNotStoreOK(String retryAnotherBrokerWhenNotStoreOK) {
    this.retryAnotherBrokerWhenNotStoreOK = retryAnotherBrokerWhenNotStoreOK;
    try {
		initializingMQProducer();
	} catch (MQClientException e) {
		logger.error(e.getErrorMessage(), e);
	}
  }
}