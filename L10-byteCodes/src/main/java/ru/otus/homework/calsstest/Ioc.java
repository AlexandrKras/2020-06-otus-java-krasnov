package ru.otus.homework.calsstest;

import ru.otus.aop.proxy.MyClassImpl;
import ru.otus.aop.proxy.MyClassInterface;
import ru.otus.homework.annotations.Log;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ioc {

    public ITestLogging createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (ITestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{ITestLogging.class}, handler);
    }

    class DemoInvocationHandler implements InvocationHandler {
        private ITestLogging testLogging;

        public DemoInvocationHandler(ITestLogging testLogging) {
            this.testLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method methodIsLog = null;
            if (args == null || args.length == 0) {
                methodIsLog = TestLogging.class.getDeclaredMethod(method.getName());
            } else {
                methodIsLog = TestLogging.class.getDeclaredMethod(method.getName(), getVarible(args));
            }

            if (methodIsLog.isAnnotationPresent(Log.class)) {
                StringBuilder str = new StringBuilder("executed method: calculation, param: ");
                Arrays.stream(args).parallel().forEach(s -> str.append(s + ", "));
                System.out.println(str.toString().substring(0, str.length() - 2));
            }

            return method.invoke(testLogging, args);
        }

        private Class[] getVarible(Object[] args) {
            Class[] typeArgs = new Class[args.length];
              for (int n = 0 ; n < args.length; n++) {
                typeArgs[n] = typeToPrimetiv(args[n].getClass());
            }
            return typeArgs;
        }

        private Class<?> typeToPrimetiv(Class<?> value) {
            if (value.equals(Integer.class)) {
                return int.class;
            } else if (value.equals(Long.class)) {
                return long.class;
            } else if (value.equals(boolean.class)) {
                return boolean.class;
            } else if (value.equals(Byte.class)) {
                return byte.class;
            } else if (value.equals(Short.class)) {
                return short.class;
            } else if (value.equals(Float.class)) {
                return float.class;
            } else if (value.equals(Double.class)) {
                return double.class;
            } else {
                return value;
            }
        }
    }
}
