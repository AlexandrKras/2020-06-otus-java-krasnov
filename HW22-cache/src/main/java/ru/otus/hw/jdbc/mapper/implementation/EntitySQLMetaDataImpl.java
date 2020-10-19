package ru.otus.hw.jdbc.mapper.implementation;

import ru.otus.hw.jdbc.exception.EntitySQLMetaDataImplException;
import ru.otus.hw.jdbc.mapper.EntityClassMetaData;
import ru.otus.hw.jdbc.mapper.EntitySQLMetaData;
import ru.otus.hw.reflection.ReflectionHelper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData data;
    private String selectAllSql = null;
    private String selectByIdSql = null;
    private String insertSql = null;
    private String updateSql = null;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> data) {
        this.data = data;
    }

    @Override
    public String getSelectAllSql() {
        if (selectAllSql == null) {
            selectAllSql = String.format("select * from %s", data.getName());
        }
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        if (selectByIdSql == null) {
            selectByIdSql = String.format("select * from %s where %s=?"
                    , data.getName()
                    , data.getIdField().getName());
        }
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        if (insertSql == null) {
            StringBuilder strFields = new StringBuilder();
            StringBuilder strValues = new StringBuilder();

            List<Field> fields = data.getFieldsWithoutId();
            for (var field : fields) {
                strFields.append(",").append(field.getName());
                strValues.append(",").append("?");
            }

            insertSql = String.format("insert into %s (%s) values (%s)"
                    , data.getName()
                    , strFields.substring(1)
                    , strValues.substring(1));
        }
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        if (updateSql == null) {
            StringBuilder set = new StringBuilder();
            List<Field> fields = data.getFieldsWithoutId();
            for (Field field : fields) {
                set.append(",")
                        .append(field.getName())
                        .append("=")
                        .append("?");
            }
            updateSql = String.format("update %s set %s where %s=?"
                    , data.getName()
                    , set.substring(1)
                    , data.getIdField().getName());
        }
        return updateSql;
    }

    public EntityClassMetaData getData() {
        return data;
    }

    public List getFieldsValue(Object obj, List<Field> fields) {
        return fields.stream()
                .map(field -> ReflectionHelper.getFieldValue(obj, field))
                .collect(Collectors.toList());
    }

    public Object getFieldIdValue(Object obj) {
        return ReflectionHelper.getFieldValue(obj, data.getIdField());
    }

    public Object setFieldsValue(Object obj, ResultSet resultSet) {
        List<Field> fields = data.getAllFields();
        fields.forEach(field -> {
            try {
                ReflectionHelper.setFieldValue(obj, field, resultSet.getObject(field.getName()));
            } catch (SQLException e) {
                e.printStackTrace();
                throw new EntitySQLMetaDataImplException("Ошибка доступа к столбцу, Или ResultSetset недоступен.");
            }
        });
        return obj;
    }

    public void setFieldsIdValue(Object obj, Object value) {
        ReflectionHelper.setFieldValue(obj, data.getIdField(), value);
    }
}
