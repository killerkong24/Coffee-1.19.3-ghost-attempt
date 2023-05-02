//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package coffee.client.jMessenger;

class Example {
    static MessageManager manager = new MessageManager();

    Example() {
    }

    public static void main(String[] args) {
        manager.registerSubscribers(new EverythingHandler());
        manager.registerSubscribers(new Example());
        manager.send(new Message("Hello, World!"));
        manager.send(new NotAMessage(123));
        manager.send(456);
        manager.unregister(Example.class);
        manager.send(new Message("Only the EverythingHandler will receive this"));
    }

    @MessageSubscription(
            priority = 10
    )
    void handleMessage(Message message) {
        System.out.printf("(This will be printed after EverythingHandler) Received message: %s%n", message.content);
    }

    static class EverythingHandler {
        EverythingHandler() {
        }

        @MessageSubscription(
                priority = -10
        )
        void handleAllMessages(Object message) {
            System.out.printf("Received message of type %s: %s%n", message.getClass().getName(), message);
        }
    }

    static record Message(String content) {
        Message(String content) {
            this.content = content;
        }

        public String content() {
            return this.content;
        }
    }

    static record NotAMessage(int v) {
        NotAMessage(int v) {
            this.v = v;
        }

        public int v() {
            return this.v;
        }
    }
}
