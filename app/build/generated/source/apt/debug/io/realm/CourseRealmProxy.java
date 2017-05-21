package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.RealmFieldType;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import net.rahmony.igrades.Course;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CourseRealmProxy extends Course
    implements RealmObjectProxy, CourseRealmProxyInterface {

    static final class CourseColumnInfo extends ColumnInfo {

        public final long courseTitleIndex;

        CourseColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(1);
            this.courseTitleIndex = getValidColumnIndex(path, table, "Course", "courseTitle");
            indicesMap.put("courseTitle", this.courseTitleIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final CourseColumnInfo columnInfo;
    private final ProxyState proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("courseTitle");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    CourseRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (CourseColumnInfo) columnInfo;
        this.proxyState = new ProxyState(Course.class, this);
    }

    @SuppressWarnings("cast")
    public String realmGet$courseTitle() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.courseTitleIndex);
    }

    public void realmSet$courseTitle(String value) {
        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.courseTitleIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.courseTitleIndex, value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_Course")) {
            Table table = transaction.getTable("class_Course");
            table.addColumn(RealmFieldType.STRING, "courseTitle", Table.NULLABLE);
            table.addSearchIndex(table.getColumnIndex("courseTitle"));
            table.setPrimaryKey("courseTitle");
            return table;
        }
        return transaction.getTable("class_Course");
    }

    public static CourseColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_Course")) {
            Table table = transaction.getTable("class_Course");
            if (table.getColumnCount() != 1) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 1 but was " + table.getColumnCount());
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < 1; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final CourseColumnInfo columnInfo = new CourseColumnInfo(transaction.getPath(), table);

            if (!columnTypes.containsKey("courseTitle")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'courseTitle' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("courseTitle") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'courseTitle' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.courseTitleIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(),"@PrimaryKey field 'courseTitle' does not support null values in the existing Realm file. Migrate using RealmObjectSchema.setNullable(), or mark the field as @Required.");
            }
            if (table.getPrimaryKey() != table.getColumnIndex("courseTitle")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Primary key not defined for field 'courseTitle' in existing Realm file. Add @PrimaryKey.");
            }
            if (!table.hasSearchIndex(table.getColumnIndex("courseTitle"))) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Index not defined for field 'courseTitle' in existing Realm file. Either set @Index or migrate using io.realm.internal.Table.removeSearchIndex().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The Course class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_Course";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static Course createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        Course obj = null;
        if (update) {
            Table table = realm.getTable(Course.class);
            long pkColumnIndex = table.getPrimaryKey();
            long rowIndex = TableOrView.NO_MATCH;
            if (json.isNull("courseTitle")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("courseTitle"));
            }
            if (rowIndex != TableOrView.NO_MATCH) {
                obj = new CourseRealmProxy(realm.schema.getColumnInfo(Course.class));
                ((RealmObjectProxy)obj).realmGet$proxyState().setRealm$realm(realm);
                ((RealmObjectProxy)obj).realmGet$proxyState().setRow$realm(table.getUncheckedRow(rowIndex));
            }
        }
        if (obj == null) {
            if (json.has("courseTitle")) {
                if (json.isNull("courseTitle")) {
                    obj = (CourseRealmProxy) realm.createObject(Course.class, null);
                } else {
                    obj = (CourseRealmProxy) realm.createObject(Course.class, json.getString("courseTitle"));
                }
            } else {
                obj = (CourseRealmProxy) realm.createObject(Course.class);
            }
        }
        if (json.has("courseTitle")) {
            if (json.isNull("courseTitle")) {
                ((CourseRealmProxyInterface) obj).realmSet$courseTitle(null);
            } else {
                ((CourseRealmProxyInterface) obj).realmSet$courseTitle((String) json.getString("courseTitle"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static Course createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        Course obj = realm.createObject(Course.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("courseTitle")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((CourseRealmProxyInterface) obj).realmSet$courseTitle(null);
                } else {
                    ((CourseRealmProxyInterface) obj).realmSet$courseTitle((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static Course copyOrUpdate(Realm realm, Course object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        Course realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(Course.class);
            long pkColumnIndex = table.getPrimaryKey();
            String value = ((CourseRealmProxyInterface) object).realmGet$courseTitle();
            long rowIndex = TableOrView.NO_MATCH;
            if (value == null) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, value);
            }
            if (rowIndex != TableOrView.NO_MATCH) {
                realmObject = new CourseRealmProxy(realm.schema.getColumnInfo(Course.class));
                ((RealmObjectProxy)realmObject).realmGet$proxyState().setRealm$realm(realm);
                ((RealmObjectProxy)realmObject).realmGet$proxyState().setRow$realm(table.getUncheckedRow(rowIndex));
                cache.put(object, (RealmObjectProxy) realmObject);
            } else {
                canUpdate = false;
            }
        }

        if (canUpdate) {
            return update(realm, realmObject, object, cache);
        } else {
            return copy(realm, object, update, cache);
        }
    }

    public static Course copy(Realm realm, Course newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        Course realmObject = realm.createObject(Course.class, ((CourseRealmProxyInterface) newObject).realmGet$courseTitle());
        cache.put(newObject, (RealmObjectProxy) realmObject);
        ((CourseRealmProxyInterface) realmObject).realmSet$courseTitle(((CourseRealmProxyInterface) newObject).realmGet$courseTitle());
        return realmObject;
    }

    public static Course createDetachedCopy(Course realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        Course unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (Course)cachedObject.object;
            } else {
                unmanagedObject = (Course)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new Course();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }
        ((CourseRealmProxyInterface) unmanagedObject).realmSet$courseTitle(((CourseRealmProxyInterface) realmObject).realmGet$courseTitle());
        return unmanagedObject;
    }

    static Course update(Realm realm, Course realmObject, Course newObject, Map<RealmModel, RealmObjectProxy> cache) {
        return realmObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Course = [");
        stringBuilder.append("{courseTitle:");
        stringBuilder.append(realmGet$courseTitle() != null ? realmGet$courseTitle() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseRealmProxy aCourse = (CourseRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aCourse.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aCourse.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aCourse.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
