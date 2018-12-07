package com.digital.dance.framework.message.selector;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;

import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.digital.dance.framework.infrastructure.commons.Log;
import com.digital.dance.framework.message.handler.MQMessageHandler;

import java.util.List;
import java.util.Map;

public class DefaultMQPullConsumerMessageSelector
  implements MQConsumerMessageSelector//, InitializingBean
{
  private static Log logger = new Log(DefaultMQPullConsumerMessageSelector.class);
  //private String SALT_REX_FORMAT = "(-%s)$";
  private String namesrvAddr;
  private String consumerGroup;
  private String messageModel;
  private String messageListener;
  private DefaultMQPushConsumer consumer;
  //private Map<String, MQMessageHandler> handlermap = new HashMap<String, MQMessageHandler>();//Java缓存
  
  /*
  private static final Map<MessageQueue, Long> offseTable = new HashMap<MessageQueue, Long>();
  private MQPullConsumerScheduleService scheduleService;

  private void messageSelector(final String salt, Map<String, MQMessageHandler> handlermap) throws InterruptedException, MQClientException {
    DefaultMQPullConsumer consumer = new DefaultMQPullConsumer();
    if ((this.consumerGroup != null) && (this.consumerGroup.trim().length() > 0)) {
      consumer.setConsumerGroup(this.consumerGroup);
      logger.debug("set consumer group " + this.consumerGroup);
    }
    consumer.setNamesrvAddr(this.namesrvAddr);
    //consumer.setConsumeMessageBatchMaxSize(1);
    //consumer.setPersistConsumerOffsetInterval(100);
    consumer.start();
    logger.debug("consumer start successd!");
    
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

    if ((handlermap != null) && (!handlermap.isEmpty())) {
      for ( String topic : handlermap.keySet() ) {
        //consumer.subscribe(topic, "*");
    	  Set<MessageQueue> mqs = consumer.fetchSubscribeMessageQueues(topic + "-" + salt);
    	  for (MessageQueue mq : mqs) {
              System.out.println("Consume from the queue: " + mq);
              DefaultMQPullConsumerMessageSelector.logger.info("Consume from the queue: " + mq);
              SINGLE_MQ:while(true){
                  try {
                        
                      PullResult pullResult = consumer.pullBlockIfNotFound(mq, null, getMessageQueueOffset(mq), 32);
                      List<MessageExt> msgs = pullResult.getMsgFoundList();
                      try
                      {
                        if ((msgs != null) && (!msgs.isEmpty())) {
                          for (MessageExt msg : msgs) {
                            DefaultMQPullConsumerMessageSelector.logger.debug(String.format("start consum message: message:id:%s topic:%s tags:%s message:%s", new Object[] { msg.getMsgId(), msg.getTopic(), msg.getTags(), new String(msg.getBody()) }));
                            MQMessageHandler handler = (MQMessageHandler)handlermap.get(msg.getTopic().replaceFirst( String.format( SALT_REX_FORMAT, salt ), "" ));
                            if (handler != null) {
                              handler.handlerMessage(msg);
                            }
                            DefaultMQPullConsumerMessageSelector.logger.debug(String.format("consume message success! message:id:%s topic:%s tags:%s message:%s", new Object[] { msg.getMsgId(), msg.getTopic(), msg.getTags(), new String(msg.getBody()) }));
                            //msg.getMsgId()
                          }
                        }
                        //return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                      } catch (Exception e) {
                    	  DefaultMQPullConsumerMessageSelector.logger.error("consume message error!", e);
                      }
                      Long nextBeginOffset = pullResult.getNextBeginOffset();
                      System.out.println(nextBeginOffset);
                      putMessageQueueOffset(mq, nextBeginOffset);
                       
                      switch (pullResult.getPullStatus()) {
                      case FOUND:
                          // TODO
                          break;
                      case NO_MATCHED_MSG:
                          break SINGLE_MQ;
                      case NO_NEW_MSG:
                          break SINGLE_MQ;
                      case OFFSET_ILLEGAL:
                          break SINGLE_MQ;
                      default:
                          break SINGLE_MQ;
                      }
                  }
                  catch (Exception e) {
                	  DefaultMQPullConsumerMessageSelector.logger.error("consume message error!", e);
                      e.printStackTrace();
                  }
               }
          }

        logger.debug("consumer subscribe topic " + topic + " *");
      }
    } else {
      logger.debug("you should provide at least one message handler.");
      throw new RuntimeException("you should provide at least one message handler.");
    }

    //consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
    
    consumer.shutdown();
  }

  public void doHandleMessageTask(final String salt, final Map<String, MQMessageHandler> handlermap) throws Exception {
		DefaultMQPullConsumer consumer = new DefaultMQPullConsumer();
		if ((this.consumerGroup != null) && (this.consumerGroup.trim().length() > 0)) {
			consumer.setConsumerGroup(this.consumerGroup);
			logger.debug("set consumer group " + this.consumerGroup);
		}
		consumer.setNamesrvAddr(this.namesrvAddr);

		logger.debug("consumer start successd!");

		logger.debug("set consumer name server address " + this.namesrvAddr);
		logger.debug("set consumer message batch max size 1");

		if ("BROADCASTING".equals(this.messageModel)) {
			consumer.setMessageModel(MessageModel.BROADCASTING);
			logger.debug("set consumer message model BROADCASTING");
		} else if ("CLUSTERING".equals(this.messageModel)) {
			consumer.setMessageModel(MessageModel.CLUSTERING);
			logger.debug("set consumer message model CLUSTERING");
		} else {
			logger.debug("set consumer message model should be BROADCASTING or CLUSTERING");
			throw new RuntimeException("set consumer message model should be BROADCASTING or CLUSTERING");
		}
		scheduleService = new MQPullConsumerScheduleService(this.consumerGroup);
		scheduleService.setDefaultMQPullConsumer(consumer);
		logger.debug("set consumer group " + this.consumerGroup);
		
		if ((handlermap != null) && (!handlermap.isEmpty())) {
			for (String topic : handlermap.keySet()) {
				scheduleService.registerPullTaskCallback(topic + "-" + salt, new PullTaskCallback() {

					@Override
					public void doPullTask(MessageQueue mq, PullTaskContext context) {
						MQPullConsumer consumer = context.getPullConsumer();

						try {

							long offset = consumer.fetchConsumeOffset(mq, false);
							if (offset < 0)
								offset = 0;

							PullResult pullResult = consumer.pull(mq, "*", offset, 32);
							System.out.printf("%s%n", offset + "\t" + mq + "\t" + pullResult);
							List<MessageExt> msgs = pullResult.getMsgFoundList();
							try {
								if ((msgs != null) && (!msgs.isEmpty())) {
									for (MessageExt msg : msgs) {
										DefaultMQPullConsumerMessageSelector.logger.debug(String.format(
												"start consum message: message:id:%s topic:%s tags:%s message:%s",
												new Object[] { msg.getMsgId(), msg.getTopic(), msg.getTags(),
														new String(msg.getBody()) }));
										MQMessageHandler handler = (MQMessageHandler) handlermap
												.get(msg.getTopic().replaceFirst( String.format( SALT_REX_FORMAT, salt ), "" ));
										if (handler != null) {
											handler.handlerMessage(msg);
										}
										DefaultMQPullConsumerMessageSelector.logger.debug(String.format(
												"consume message success! message:id:%s topic:%s tags:%s message:%s",
												new Object[] { msg.getMsgId(), msg.getTopic(), msg.getTags(),
														new String(msg.getBody()) }));

									}
								}
								
							} catch (Exception e) {
								DefaultMQPullConsumerMessageSelector.logger.error("consume message error!", e);
							}

							switch (pullResult.getPullStatus()) {
							case FOUND:
								//consumer.fetchSubscribeMessageQueues(topic).registerMessageQueueListener(topic, listener);
								//目标消息已拉取到，并已处理完毕，因而不再需要再次拉取消息
								stopScheduleConsumer();
								break;
//							case NO_MATCHED_MSG:
//								break;
//							case NO_NEW_MSG:
//							case OFFSET_ILLEGAL:
//								break;
							default:
								consumer.updateConsumeOffset(mq, pullResult.getNextBeginOffset());

								////设置隔多长时间进行下次拉取
								context.setPullNextDelayTimeMillis(100);
								break;
							}
							
						} catch (Exception e) {
							DefaultMQPullConsumerMessageSelector.logger.error("consume message error!", e);
							e.printStackTrace();
						}
					}
				});
			}
		}
		scheduleService.start();
  }
*/	
  public void doHandleMessageTask(final String salt, final Map<String, MQMessageHandler> handlermap) throws Exception {
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

	    if ((handlermap != null) && (!handlermap.isEmpty())) {
	      for (String topic : handlermap.keySet()) {
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
	                logger.debug(String.format("start consum message: message:id:%s topic:%s tags:%s message:%s", new Object[] { msg.getMsgId(), msg.getTopic(), msg.getTags(), new String(msg.getBody()) }));
	                MQMessageHandler handler = (MQMessageHandler)handlermap.get(msg.getTopic());
	                if (handler != null) {
	                  handler.handlerMessage(msg);
	                }
	                logger.debug(String.format("consume message success! message:id:%s topic:%s tags:%s message:%s", new Object[] { msg.getMsgId(), msg.getTopic(), msg.getTags(), new String(msg.getBody()) }));
	              }
	            }
	            /**
	             * 业务实现消费回调的时候，当且仅当此回调函数返回ConsumeConcurrentlyStatus.CONSUME_SUCCESS，RocketMQ才会认为这批消息（默认是1条）是消费完成的
	             */
	            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	          } catch (Exception e) {
	            logger.error("consume message error!", e);
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
	                logger.debug(String.format("start consum message: message:id:%s topic:%s tags:%s message:%s", new Object[] { msg.getMsgId(), msg.getTopic(), msg.getTags(), new String(msg.getBody()) }));
	                MQMessageHandler handler = (MQMessageHandler)handlermap.get(msg.getTopic());
	                if (handler != null) {
	                  handler.handlerMessage(msg);
	                }
	                logger.debug(String.format("consume message success! message:id:%s topic:%s tags:%s message:%s", new Object[] { msg.getMsgId(), msg.getTopic(), msg.getTags(), new String(msg.getBody()) }));
	              }
	            }
	            /**
	             * 业务实现消费回调的时候，当且仅当此回调函数返回ConsumeConcurrentlyStatus.CONSUME_SUCCESS，RocketMQ才会认为这批消息（默认是1条）是消费完成的
	             */
	            return ConsumeOrderlyStatus.SUCCESS;
	          } catch (Exception e) {
	            logger.error("consume message error!", e);
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

//  public void setHandlermap(Map<String, MQMessageHandler> handlermap) {
//    this.handlermap = handlermap;
//  }
  
//  private static void putMessageQueueOffset(MessageQueue mq, long offset) {
//      offseTable.put(mq, offset);
//  }
//
//
//  private static long getMessageQueueOffset(MessageQueue mq) {
//      Long offset = offseTable.get(mq);
//      if (offset != null){
//          System.out.println(offset);
//          return offset;
//      }
//      return 0;
//  }

}
