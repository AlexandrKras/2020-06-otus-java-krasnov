package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        Method[] methods = configClass.getMethods();
        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .forEachOrdered(method -> createComponent(createConstructor(configClass), method));
    }

    private Object createConstructor(Class<?> configClass) {
        try {
            return configClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("Для класса %S нет конструктора по умолчанию",
                    configClass.getName()));
        }
    }

    private void createComponent(Object configObject, Method method) {
        Object appComponent = null;

        if (method.getParameterCount() == 0) {
            appComponent = createObject(configObject, method);
        } else {
            Parameter[] parameters = method.getParameters();
            List<Object> args = new ArrayList<>();
            for (Parameter parameter : parameters) {
                var param = getAppComponent(parameter.getType());
                args.add(param);
            }
            appComponent = createObject(configObject, method, args.toArray());
        }

        appComponents.add(appComponent);
        appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), appComponent);
    }

    private Object createObject(Object configObject, Method method, Object... args) {
        Object appComponents = null;
        try {
            if (args == null || args.length == 0) {
                appComponents = method.invoke(configObject);
            } else {
                appComponents = method.invoke(configObject, args);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return appComponents;
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream().filter(object -> componentClass.isAssignableFrom(object.getClass()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
