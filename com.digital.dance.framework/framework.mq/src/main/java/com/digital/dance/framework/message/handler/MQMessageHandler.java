package com.digital.dance.framework.message.handler;

import com.alibaba.rocketmq.common.message.MessageExt;

public abstract interface MQMessageHandler
{
  public abstract void handlerMessage(MessageExt paramMessageExt)
    throws Exception;
}
