package proto;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bson.BSONObject;

public class MyInteger implements DBObject {

    private int intValue;
    
    public MyInteger() {
        intValue = 0;
    }

    public MyInteger(int intValue) {
        super();
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public DBObject bsonFromPojo() {
        BasicDBObject document = new BasicDBObject();

        document.put("intValue", this.intValue);

        return document;
    }

    public void makePojoFromBson(DBObject bson) {
        BasicDBObject b = (BasicDBObject) bson;

        this.intValue = (int) b.get("intValue");
    }

    @Override
    public void markAsPartialObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isPartialObject() {
        return false;
    }

    @Override
    public Object put(String field, Object object) {
        if (field.equals("intValue")) {
            this.intValue = (int) object;
            return object;
        }
        return null;
    }

    @Override
    public void putAll(BSONObject bson) {
        bson.keySet().forEach((key) -> {
            put(key, bson.get(key));
        });
    }

    @Override
    public void putAll(Map map) {
        for (Map.Entry< String, Object> entry : (Set< Map.Entry< String, Object>>) map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Object get(String field) {
        if (field.equals("intValue")) {
            return this.intValue;
        }
        return null;
    }

    @Override
    public Map toMap() {
        Map< String, Object> map = new HashMap< String, Object>();

        map.put("intValue", this.intValue);

        return map;
    }

    @Override
    public Object removeField(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean containsKey(String key) {
        return containsField(key);
    }

    @Override
    public boolean containsField(String field) {
        return field.equals(this.intValue);
    }

    @Override
    public Set<String> keySet() {
        Set<String> set = new HashSet<String>();

        set.add("intValue");

        return set;
    }

}
