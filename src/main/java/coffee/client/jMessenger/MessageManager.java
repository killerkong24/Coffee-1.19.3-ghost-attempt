//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package coffee.client.jMessenger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import coffee.client.jMessenger.exception.InvalidSubscriberException;
import coffee.client.jMessenger.exception.SubscriberAlreadyRegisteredException;
import coffee.client.jMessenger.impl.SubscriberUnregisterEvent;
import coffee.client.jMessenger.impl.SubscriberRegisterEvent;

public class MessageManager {
    protected final List<Handler> handlers = new CopyOnWriteArrayList();

    public MessageManager() {
    }

    protected void register(Handler handler) {
        this.handlers.add(handler);
        this.sortHandlers();
        this.send(new SubscriberRegisterEvent(handler));
    }

    private void sortHandlers() {
        this.handlers.sort(Comparator.comparingInt(Handler::priority));
    }

    protected List<Handler> getSubscribersByType(Class<?> subscriptionType) {
        return this.handlers.stream().filter((handler) -> {
            return handler.subscriptionType.isAssignableFrom(subscriptionType);
        }).toList();
    }

    public void send(Object o) {
        try {
            Class<?> aClass = o.getClass();
            Iterator var3 = this.getSubscribersByType(aClass).iterator();

            while(var3.hasNext()) {
                Handler handler = (Handler)var3.next();
                handler.invoke(o);
            }

        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    public void unregister(Object instance) {
        this.unregister(instance.getClass());
    }

    public void unregister(Class<?> i) {
        this.handlers.removeIf((handler) -> {
            return handler.ownerClass.equals(i);
        });
        this.send(new SubscriberUnregisterEvent(i));
    }

    public void registerSubscribers(Object instance) {
        Class<?> aClass = instance.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        Method[] var4 = declaredMethods;
        int var5 = declaredMethods.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Method declaredMethod = var4[var6];
            MessageSubscription annotationFrom = (MessageSubscription)Util.getAnnotationFrom(declaredMethod, MessageSubscription.class);
            if (annotationFrom != null) {
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new InvalidSubscriberException(String.format("Handler %s.%s%s has an invalid signature: Expected 1 argument, found %s", aClass.getName(), declaredMethod.getName(), Util.signatureOf(declaredMethod), parameterTypes.length));
                }

                Class<?> listenerType = parameterTypes[0];
                Handler handler = new Handler(instance, aClass, declaredMethod, listenerType, annotationFrom.priority());
                if (this.handlers.contains(handler)) {
                    throw new SubscriberAlreadyRegisteredException(String.format("Handler %s.%s%s is already registered", aClass.getName(), declaredMethod.getName(), Util.signatureOf(declaredMethod)));
                }

                this.register(handler);
            }
        }

    }

    public static record Handler(Object ownerInstance, Class<?> ownerClass, Method callee, Class<?> subscriptionType, int priority) {
        public Handler(Object ownerInstance, Class<?> ownerClass, Method callee, Class<?> subscriptionType, int priority) {
            this.ownerInstance = ownerInstance;
            this.ownerClass = ownerClass;
            this.callee = callee;
            this.subscriptionType = subscriptionType;
            this.priority = priority;
        }

        public void invoke(Object message) throws InvocationTargetException, IllegalAccessException {
            this.callee.setAccessible(true);
            this.callee.invoke(this.ownerInstance, message);
        }

        public String toString() {
            return "Handler{ownerInstance=" + this.ownerInstance + ", ownerClass=" + this.ownerClass + ", callee=" + this.callee + ", subscriptionType=" + this.subscriptionType + ", priority=" + this.priority + "}";
        }

        public boolean equals(Object obj) {
            if (obj instanceof Handler h) {
                return this.callee.equals(h.callee);
            } else {
                return false;
            }
        }

        public Object ownerInstance() {
            return this.ownerInstance;
        }

        public Class<?> ownerClass() {
            return this.ownerClass;
        }

        public Method callee() {
            return this.callee;
        }

        public Class<?> subscriptionType() {
            return this.subscriptionType;
        }

        public int priority() {
            return this.priority;
        }
    }
}

