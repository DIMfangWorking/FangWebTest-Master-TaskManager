package com.xinwei.taskmanager.model.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class AnnotationTool {

	private MongoTemplate mongoTemplate = null;

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public <T> boolean valid(T t) {
		Class<?> clz = t.getClass();
		Field[] fields = clz.getDeclaredFields();
		try {
			for (Field field : fields) {
				if (field.getAnnotation(CheckField.class) != null) {
					field.setAccessible(true);
					CheckField annotation = field.getDeclaredAnnotation(CheckField.class);
					if (annotation.required()) {
						checkRequired(field.get(t), field);
					}
					if (annotation.trim()) {
						checkTrim(t, clz, field, field.get(t));
					}
					if (annotation.unique()) {
						checkUnique(t, clz, field, field.get(t));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private <T> void checkTrim(T t, Class<?> clz, Field field, Object value) throws Exception {
		String fieldName = field.getName();
		String methodName = "set" + fieldName.replaceFirst(String.valueOf(fieldName.charAt(0)),
				String.valueOf(fieldName.charAt(0)).toUpperCase());
		Method m1 = clz.getDeclaredMethod(methodName, field.getType());
		m1.invoke(t, String.valueOf(value).trim());
	}

	private void checkRequired(Object value, Field field) throws Exception {
		if (value == null || String.valueOf(value).equals("")) {
			throw new Exception(field.getName() + " 不允许为空");
		}
	}

	private <T> void checkUnique(T t, Class<?> clz, Field field, Object value) throws Exception {
		Query query = new Query();
		query.addCriteria(
				Criteria.where(field.getName()).is(value instanceof Integer ? (Integer) value : String.valueOf(value)));
		if (mongoTemplate.find(query, clz).size() == 0) {
			return;
		}
		if (mongoTemplate.find(query, clz).get(0) != null) {
			throw new Exception(field.getName() + " 重复字段不允许");
		}
	}

}
