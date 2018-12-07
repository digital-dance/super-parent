package com.digital.dance.framework.message.producer;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public abstract interface MQProducer
{
  public abstract SendResult send(Message paramMessage)
    throws MQClientException, RemotingException, MQBrokerException, InterruptedException;

  public abstract void send(Message paramMessage, SendCallback paramSendCallback)
    throws MQClientException, RemotingException, InterruptedException;

  public abstract void sendOneway(Message paramMessage)
    throws MQClientException, RemotingException, InterruptedException;

  public abstract SendResult send(Message paramMessage, MessageQueue paramMessageQueue)
    throws MQClientException, RemotingException, MQBrokerException, InterruptedException;

  public abstract void send(Message paramMessage, MessageQueue paramMessageQueue, SendCallback paramSendCallback)
    throws MQClientException, RemotingException, InterruptedException;

  public abstract void sendOneway(Message paramMessage, MessageQueue paramMessageQueue)
    throws MQClientException, RemotingException, InterruptedException;

  public abstract SendResult send(Message paramMessage, MessageQueueSelector paramMessageQueueSelector, Object paramObject)
    throws MQClientException, RemotingException, MQBrokerException, InterruptedException;

  public abstract void send(Message paramMessage, MessageQueueSelector paramMessageQueueSelector, Object paramObject, SendCallback paramSendCallback)
    throws MQClientException, RemotingException, InterruptedException;

  public abstract void sendOneway(Message paramMessage, MessageQueueSelector paramMessageQueueSelector, Object paramObject)
    throws MQClientException, RemotingException, InterruptedException;
}