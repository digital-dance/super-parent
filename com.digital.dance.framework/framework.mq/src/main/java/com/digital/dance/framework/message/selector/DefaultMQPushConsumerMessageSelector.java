package com.digital.dance.framework.message.selector;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.digital.dance.framework.message.handler.MQMessageHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class DefaultMQPushConsumerMessageSelector
  implements MQConsumerMessageSelector, InitializingBean
{
  private static Logger logger = LoggerFactory.getLogger(DefaultMQPushConsumerMessageSelector.class);
  private String namesrvAddr;
  private String consumerGroup;
  private String messageModel;
  private String messageListener;
  private Map<String, MQMessageHandler> handlermap = new HashMap<String, MQMessageHandler>();
  private DefaultMQPushConsumer consumer;

  private void initializingMessageSelector() throws InterruptedException, MQClientException {
	 stopConsumer();
	 consumer = new DefaultMQPushConsumer();
    if ((this.consumerGroup != null) && (this.consumerGroup.trim().length() > 0)) {
      consumer.setConsumerGroup(this.consumerGroup);
      logger.debug("set consumer group " + this.consumerGroup);
    }
    consumer.setNamesrvAddr(this.namesrvAddr);
    consumer.setConsumeMessageBatchMaxSize(1);
    logger.debug("set consumer name server address " + this.namesrvAddr);
    logger.debug("set consumer message batch max size 1");

    if ("BROADCASTING".equals(this.messageModel)) {
      consumer.setMessageModel(MessageModel.BROADCASTING);
      logger.debug("set consumer message model BROADCASTING");
    }
    else if ("CLUSTERING".equals(this.messageModel)) {
      consumer.setMessageModel(MessageModel.CLUSTERING);
      logger.debug("set consumer message model CLUSTERING");
    } else {
      logger.debug("set consumer message model should be BROADCASTING or CLUSTERING");
      throw new RuntimeException("set consumer message model should be BROADCASTING or CLUSTERING");
    }

    if ((this.handlermap != null) && (!this.handlermap.isEmpty())) {
      for (String topic : this.handlermap.keySet()) {
        consumer.subscribe(topic, "*");
        logger.debug("consumer subscribe topic " + topic + " *");
      }
    } else {
      logger.debug("you should provide at least one message handler.");
      throw new RuntimeException("you should provide at least one message handler.");
    }

    consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
    if ("CONCURRENTLY".equals(this.messageListener)) {
      consumer.registerMessageListener(new MessageListenerConcurrently()
      {
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context)
        {
          try
          {
            if ((msgs != null) && (!msgs.isEmpty())) {
              for (MessageExt msg : msgs) {
                DefaultMQPushConsumerMessageSelector.logger.debug(String.format("start consum message: message:id:%s topic:%s tags:%s message:%s", new Object[] { msg.getMsgId(), msg.getTopic(), msg.getTags(), new String(msg.getBody()) }));
                MQMessageHandler handler = (MQMessageHandler)DefaultMQPushConsumerMessageSelector.this.handlermap.get(msg.getTopic());
                if (handler != null) {
                  handler.handlerMessage(msg);
                }
                DefaultMQPushConsumerMessageSelector.logger.debug(String.format("consume message success! message:id:%s topic:%s tags:%s message:%s", new Object[] { msg.getMsgId(), msg.getTopic(), msg.getTags(), new String(msg.getBody()) }));
              }
            }
            /**
             * 业务实现消费回调的时候，当且仅当此回调函数返回ConsumeConcurrentlyStatus.CONSUME_SUCCESS，RocketMQ才会认为这批消息（默认是1条）是消费完成的
             */
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
          } catch (Exception e) {
            DefaultMQPushConsumerMessageSelector.logger.error("consume message error!", e);
          }
          /**
           * 中途断电，抛出异常等都不会认为成功——即都会重新投递
           */
          return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }

      });
    }
    else if ("ORDERLY".equals(this.messageListener)) {
      consumer.registerMessageListener(new MessageListenerOrderly()
      {
        public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context)
        {
          try
          {
            if ((msgs != null) && (!msgs.isEmpty())) {
              for (MessageExt msg : msgs) {
                DefaultMQPushConsumerMessageSelector.logger.debug(String.format("start consum message: message:id:%s topic:%s tags:%s message:%s", new Object[] { msg.getMsgId(), msg.getTopic(), msg.getTags(), new String(msg.getBody()) }));
                MQMessageHandler handler = (MQMessageHandler)DefaultMQPushConsumerMessageSelector.this.handlermap.get(msg.getTopic());
                if (handler != null) {
                  handler.handlerMessage(msg);
                }
                DefaultMQPushConsumerMessageSelector.logger.debug(String.format("consume message success! message:id:%s topic:%s tags:%s message:%s", new Object[] { msg.getMsgId(), msg.getTopic(), msg.getTags(), new String(msg.getBody()) }));
              }
            }
            /**
             * 业务实现消费回调的时候，当且仅当此回调函数返回ConsumeConcurrentlyStatus.CONSUME_SUCCESS，RocketMQ才会认为这批消息（默认是1条）是消费完成的
             */
            return ConsumeOrderlyStatus.SUCCESS;
          } catch (Exception e) {
            DefaultMQPushConsumerMessageSelector.logger.error("consume message error!", e);
          }
          /**
           * 中途断电，抛出异常等都不会认为成功——即都会重新投递
           */
          return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }

      });
    }

    consumer.start();
    logger.debug("consumer start successd!");
  }

  public void afterPropertiesSet() throws Exception {
    initializingMessageSelector();
  }
  
  public void stopConsumer() {
		if( this.consumer != null ){
			
			this.consumer.shutdown();			
			logger.debug("consumer shutdown successd!");
		}
  }
  
  public void setNamesrvAddr(String namesrvAddr) {
    this.namesrvAddr = namesrvAddr;
  }

  public void setConsumerGroup(String consumerGroup) {
    this.consumerGroup = consumerGroup;
  }

  public void setMessageModel(String messageModel) {
    this.messageModel = messageModel;
  }

  public void setMessageListener(String messageListener) {
    this.messageListener = messageListener;
  }

  public void setHandlermap(Map<String, MQMessageHandler> handlermap) {
    this.handlermap = handlermap;
  }
}