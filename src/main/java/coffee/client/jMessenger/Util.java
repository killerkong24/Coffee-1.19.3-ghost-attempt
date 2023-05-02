//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package coffee.client.jMessenger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Util {
    private static final Map<Class<?>, String> signatures = (Map)make(new HashMap(), (signatures) -> {
        signatures.put(Boolean.TYPE, "Z");
        signatures.put(Byte.TYPE, "B");
        signatures.put(Character.TYPE, "C");
        signatures.put(Short.TYPE, "S");
        signatures.put(Integer.TYPE, "I");
        signatures.put(Long.TYPE, "J");
        signatures.put(Float.TYPE, "F");
        signatures.put(Double.TYPE, "D");
        signatures.put(Void.TYPE, "V");
    });

    public Util() {
    }

    public static <T extends Annotation> T getAnnotationFrom(Method method, Class<T> annotationType) {
        Annotation[] var2 = method.getDeclaredAnnotations();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Annotation declaredAnnotation = var2[var4];
            if (annotationType.isInstance(declaredAnnotation)) {
                return (T) declaredAnnotation;
            }
        }

        return null;
    }

    public static <T> T make(T inst, Consumer<T> modifier) {
        modifier.accept(inst);
        return inst;
    }

    private static Class<?> recursiveComponentType(Class<?> t) {
        Class p;
        for(p = t; p.isArray(); p = p.componentType()) {
        }

        return p;
    }

    private static int determineArrayDepth(Class<?> t) {
        int a = 0;

        for(Class<?> p = t; p.isArray(); p = p.componentType()) {
            ++a;
        }

        return a;
    }

    private static String genericSignatureOf(Class<?> t) {
        Class<?> a = recursiveComponentType(t);
        return String.format("L%s;", a.getName().replaceAll("\\.", "/"));
    }

    private static String signatureOf(Class<?> t) {
        String sig = signatures.containsKey(t) ? (String)signatures.get(t) : genericSignatureOf(t);
        String var10000 = "[".repeat(determineArrayDepth(t));
        return var10000 + sig;
    }

    public static String signatureOf(Method m) {
        Class<?>[] parameterTypes = m.getParameterTypes();
        String params = (String)Arrays.stream(parameterTypes).map(Util::signatureOf).collect(Collectors.joining());
        String ret = signatureOf(m.getReturnType());
        return String.format("(%s)%s", params, ret);
    }
}
