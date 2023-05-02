//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package coffee.client.jMessenger.impl;

import coffee.client.jMessenger.MessageManager;

public record SubscriberRegisterEvent(MessageManager.Handler handler) {
    public SubscriberRegisterEvent(MessageManager.Handler handler) {
        this.handler = handler;
    }

    public MessageManager.Handler handler() {
        return this.handler;
    }
}
