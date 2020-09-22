package ru.otus.homework;

import ru.otus.homework.reflection.ReflectionHelper;

import javax.json.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class MyGson {

    public String toJson(Object obj) {
        return parserObject(obj).toString();
    }

    private JsonValue parserObject(Object obj) {
        if (obj == null)
            return JsonValue.NULL;

        if (obj instanceof String || obj instanceof Character) {
            return Json.createValue(obj.toString());
        } else if (obj instanceof Number) {
            if (obj instanceof Long || obj instanceof Integer || obj instanceof Short || obj instanceof Byte) {
                return Json.createValue(Long.valueOf(obj.toString()));
            } else if (obj instanceof Double || obj instanceof Float) {
                return Json.createValue(Double.valueOf(obj.toString()));
            } else {
                throw new RuntimeException("Невозможно создать Json, неизвестный тип объекта");
            }
        } else if (obj instanceof Boolean) {
            if ((boolean) obj) {
                return JsonValue.TRUE;
            } else {
                return JsonValue.FALSE;
            }
        } else if (ReflectionHelper.isArray(obj)) {
            return array2Json((Object[]) obj);
        } else if (obj instanceof Collection) {
            return array2Json(((Collection) obj).toArray());
        } else if (obj instanceof Map) {
            return map2Json((Map) obj);
        } else {
            return object2Json(obj);
        }
    }

    private JsonValue object2Json(Object obj) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        Arrays.stream(ReflectionHelper.getFields(obj)).forEach(field -> {
            builder.add(field.getName(), parserObject(ReflectionHelper.getFieldValue(obj, field)));
        });
        return builder.build();
    }

    private JsonValue array2Json(Object[] array) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for (Object obj : array) {
            builder.add(parserObject(obj));
        }
        return builder.build();
    }

    private JsonValue map2Json(Map map) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        map.forEach((key, value) -> {
            builder.add(key instanceof String
                            ? ((JsonString) parserObject(key)).getString()
                            : String.valueOf(parserObject(key))
                    , parserObject(value));
        });
        return builder.build();
    }
}
