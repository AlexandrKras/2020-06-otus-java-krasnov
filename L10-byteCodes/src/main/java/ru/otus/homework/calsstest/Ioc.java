package ru.otus.homework.calsstest;

import ru.otus.homework.annotations.Log;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class Ioc {

    public ITestLogging createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (ITestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{ITestLogging.class}, handler);
    }

    class DemoInvocationHandler implements InvocationHandler {
        private ITestLogging testLogging;
        private Set<String> logMethodsStr = new HashSet<>();

        public DemoInvocationHandler(ITestLogging testLogging) {
            this.testLogging = testLogging;
            Method[] methods = testLogging.getClass().getDeclaredMethods();
            List<Method> logMethods = Arrays.stream(methods)
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .collect(Collectors.toList());

            logMethods.forEach(method -> logMethodsStr.add(getShortNameMathod(method)));
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (logMethodsStr.contains(getShortNameMathod(method))) {
                StringBuilder str = new StringBuilder("executed method: calculation, param: ");
                Arrays.stream(args).forEach(s -> str.append(s + ", "));
                System.out.println(str.toString().substring(0, str.length() - 2));
            }

            return method.invoke(testLogging, args);
        }
    }

    private String getShortNameMathod(Method method) {
        String methodStr = method.toString();
        return methodStr.substring(methodStr.indexOf(method.getName()));
    }
}
