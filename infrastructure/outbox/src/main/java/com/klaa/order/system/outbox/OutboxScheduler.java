package com.klaa.order.system.outbox;

public interface OutboxScheduler {
   void processOutboxMessages();
}
