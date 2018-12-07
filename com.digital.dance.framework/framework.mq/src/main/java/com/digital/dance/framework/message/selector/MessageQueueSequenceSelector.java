package com.digital.dance.framework.message.selector;

import java.util.List;

import com.alibaba.rocketmq.client.producer.MessageQueueSelector;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageQueue;

public class MessageQueueSequenceSelector implements MessageQueueSelector {
	@Override
    public MessageQueue select( List<MessageQueue> mqs, Message msg, Object arg ) {
        Integer id = arg.hashCode();
        int index = id % mqs.size();
        return mqs.get( index );
    }

}
