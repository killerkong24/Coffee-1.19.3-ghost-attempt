//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package coffee.client.jMessenger.impl;

public record SubscriberUnregisterEvent(Class<?> handlerClass) {
    public SubscriberUnregisterEvent(Class<?> handlerClass) {
        this.handlerClass = handlerClass;
    }

    public Class<?> handlerClass() {
        return this.handlerClass;
    }
}
