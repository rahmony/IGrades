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
import net.rahmony.igrades.Grade;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GradeRealmProxy extends Grade
    implements RealmObjectProxy, GradeRealmProxyInterface {

    static final class GradeColumnInfo extends ColumnInfo {

        public final long courseTitleIndex;
        public final long gradeTitleIndex;
        public final long earnedGradeIndex;
        public final long totalGradeIndex;

        GradeColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(4);
            this.courseTitleIndex = getValidColumnIndex(path, table, "Grade", "courseTitle");
            indicesMap.put("courseTitle", this.courseTitleIndex);

            this.gradeTitleIndex = getValidColumnIndex(path, table, "Grade", "gradeTitle");
            indicesMap.put("gradeTitle", this.gradeTitleIndex);

            this.earnedGradeIndex = getValidColumnIndex(path, table, "Grade", "earnedGrade");
            indicesMap.put("earnedGrade", this.earnedGradeIndex);

            this.totalGradeIndex = getValidColumnIndex(path, table, "Grade", "totalGrade");
            indicesMap.put("totalGrade", this.totalGradeIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final GradeColumnInfo columnInfo;
    private final ProxyState proxyState;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("courseTitle");
        fieldNames.add("gradeTitle");
        fieldNames.add("earnedGrade");
        fieldNames.add("totalGrade");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    GradeRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (GradeColumnInfo) columnInfo;
        this.proxyState = new ProxyState(Grade.class, this);
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

    @SuppressWarnings("cast")
    public String realmGet$gradeTitle() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.gradeTitleIndex);
    }

    public void realmSet$gradeTitle(String value) {
        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.gradeTitleIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.gradeTitleIndex, value);
    }

    @SuppressWarnings("cast")
    public double realmGet$earnedGrade() {
        proxyState.getRealm$realm().checkIfValid();
        return (double) proxyState.getRow$realm().getDouble(columnInfo.earnedGradeIndex);
    }

    public void realmSet$earnedGrade(double value) {
        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setDouble(columnInfo.earnedGradeIndex, value);
    }

    @SuppressWarnings("cast")
    public double realmGet$totalGrade() {
        proxyState.getRealm$realm().checkIfValid();
        return (double) proxyState.getRow$realm().getDouble(columnInfo.totalGradeIndex);
    }

    public void realmSet$totalGrade(double value) {
        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setDouble(columnInfo.totalGradeIndex, value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_Grade")) {
            Table table = transaction.getTable("class_Grade");
            table.addColumn(RealmFieldType.STRING, "courseTitle", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "gradeTitle", Table.NULLABLE);
            table.addColumn(RealmFieldType.DOUBLE, "earnedGrade", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.DOUBLE, "totalGrade", Table.NOT_NULLABLE);
            table.setPrimaryKey("");
            return table;
        }
        return transaction.getTable("class_Grade");
    }

    public static GradeColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_Grade")) {
            Table table = transaction.getTable("class_Grade");
            if (table.getColumnCount() != 4) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 4 but was " + table.getColumnCount());
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < 4; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final GradeColumnInfo columnInfo = new GradeColumnInfo(transaction.getPath(), table);

            if (!columnTypes.containsKey("courseTitle")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'courseTitle' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("courseTitle") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'courseTitle' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.courseTitleIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'courseTitle' is required. Either set @Required to field 'courseTitle' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("gradeTitle")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'gradeTitle' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("gradeTitle") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'gradeTitle' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.gradeTitleIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'gradeTitle' is required. Either set @Required to field 'gradeTitle' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("earnedGrade")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'earnedGrade' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("earnedGrade") != RealmFieldType.DOUBLE) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'double' for field 'earnedGrade' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.earnedGradeIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'earnedGrade' does support null values in the existing Realm file. Use corresponding boxed type for field 'earnedGrade' or migrate using RealmObjectSchema.setNullable().");
            }
            if (!columnTypes.containsKey("totalGrade")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'totalGrade' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("totalGrade") != RealmFieldType.DOUBLE) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'double' for field 'totalGrade' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.totalGradeIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'totalGrade' does support null values in the existing Realm file. Use corresponding boxed type for field 'totalGrade' or migrate using RealmObjectSchema.setNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The Grade class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_Grade";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static Grade createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        Grade obj = realm.createObject(Grade.class);
        if (json.has("courseTitle")) {
            if (json.isNull("courseTitle")) {
                ((GradeRealmProxyInterface) obj).realmSet$courseTitle(null);
            } else {
                ((GradeRealmProxyInterface) obj).realmSet$courseTitle((String) json.getString("courseTitle"));
            }
        }
        if (json.has("gradeTitle")) {
            if (json.isNull("gradeTitle")) {
                ((GradeRealmProxyInterface) obj).realmSet$gradeTitle(null);
            } else {
                ((GradeRealmProxyInterface) obj).realmSet$gradeTitle((String) json.getString("gradeTitle"));
            }
        }
        if (json.has("earnedGrade")) {
            if (json.isNull("earnedGrade")) {
                throw new IllegalArgumentException("Trying to set non-nullable field earnedGrade to null.");
            } else {
                ((GradeRealmProxyInterface) obj).realmSet$earnedGrade((double) json.getDouble("earnedGrade"));
            }
        }
        if (json.has("totalGrade")) {
            if (json.isNull("totalGrade")) {
                throw new IllegalArgumentException("Trying to set non-nullable field totalGrade to null.");
            } else {
                ((GradeRealmProxyInterface) obj).realmSet$totalGrade((double) json.getDouble("totalGrade"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static Grade createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        Grade obj = realm.createObject(Grade.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("courseTitle")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((GradeRealmProxyInterface) obj).realmSet$courseTitle(null);
                } else {
                    ((GradeRealmProxyInterface) obj).realmSet$courseTitle((String) reader.nextString());
                }
            } else if (name.equals("gradeTitle")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    ((GradeRealmProxyInterface) obj).realmSet$gradeTitle(null);
                } else {
                    ((GradeRealmProxyInterface) obj).realmSet$gradeTitle((String) reader.nextString());
                }
            } else if (name.equals("earnedGrade")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field earnedGrade to null.");
                } else {
                    ((GradeRealmProxyInterface) obj).realmSet$earnedGrade((double) reader.nextDouble());
                }
            } else if (name.equals("totalGrade")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field totalGrade to null.");
                } else {
                    ((GradeRealmProxyInterface) obj).realmSet$totalGrade((double) reader.nextDouble());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static Grade copyOrUpdate(Realm realm, Grade object, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().threadId != realm.threadId) {
            throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
        }
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy)object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return object;
        }
        return copy(realm, object, update, cache);
    }

    public static Grade copy(Realm realm, Grade newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache) {
        Grade realmObject = realm.createObject(Grade.class);
        cache.put(newObject, (RealmObjectProxy) realmObject);
        ((GradeRealmProxyInterface) realmObject).realmSet$courseTitle(((GradeRealmProxyInterface) newObject).realmGet$courseTitle());
        ((GradeRealmProxyInterface) realmObject).realmSet$gradeTitle(((GradeRealmProxyInterface) newObject).realmGet$gradeTitle());
        ((GradeRealmProxyInterface) realmObject).realmSet$earnedGrade(((GradeRealmProxyInterface) newObject).realmGet$earnedGrade());
        ((GradeRealmProxyInterface) realmObject).realmSet$totalGrade(((GradeRealmProxyInterface) newObject).realmGet$totalGrade());
        return realmObject;
    }

    public static Grade createDetachedCopy(Grade realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        Grade unmanagedObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (Grade)cachedObject.object;
            } else {
                unmanagedObject = (Grade)cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            unmanagedObject = new Grade();
            cache.put(realmObject, new RealmObjectProxy.CacheData(currentDepth, unmanagedObject));
        }
        ((GradeRealmProxyInterface) unmanagedObject).realmSet$courseTitle(((GradeRealmProxyInterface) realmObject).realmGet$courseTitle());
        ((GradeRealmProxyInterface) unmanagedObject).realmSet$gradeTitle(((GradeRealmProxyInterface) realmObject).realmGet$gradeTitle());
        ((GradeRealmProxyInterface) unmanagedObject).realmSet$earnedGrade(((GradeRealmProxyInterface) realmObject).realmGet$earnedGrade());
        ((GradeRealmProxyInterface) unmanagedObject).realmSet$totalGrade(((GradeRealmProxyInterface) realmObject).realmGet$totalGrade());
        return unmanagedObject;
    }

    @Override
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Grade = [");
        stringBuilder.append("{courseTitle:");
        stringBuilder.append(realmGet$courseTitle() != null ? realmGet$courseTitle() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{gradeTitle:");
        stringBuilder.append(realmGet$gradeTitle() != null ? realmGet$gradeTitle() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{earnedGrade:");
        stringBuilder.append(realmGet$earnedGrade());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{totalGrade:");
        stringBuilder.append(realmGet$totalGrade());
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
        GradeRealmProxy aGrade = (GradeRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aGrade.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aGrade.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aGrade.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }

}
