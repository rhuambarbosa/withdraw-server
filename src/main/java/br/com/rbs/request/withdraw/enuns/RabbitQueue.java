package br.com.rbs.request.withdraw.enuns;

public enum RabbitQueue {
    REGISTER_TRANSACTION("register.transaction");

    private final String queue;

    RabbitQueue(String queue) {
        this.queue = queue;
    }

    public String getQueue() {
        return queue;
    }
}
