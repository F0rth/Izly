package nl.qbusict.cupboard.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import nl.qbusict.cupboard.annotation.CompositeIndex;
import nl.qbusict.cupboard.annotation.Index;

public class IndexStatement {
    public static final String INDEX_PREFIX = "_cb";
    public final boolean[] mAscendings;
    public final String[] mColumnNames;
    public final String mIndexName;
    public final boolean mIsUnique;

    public static class Builder {
        public static final String GENERATED_INDEX_NAME = "%s_%s";
        Map<String, Set<IndexColumnMetadata>> indexes = new HashMap();
        Map<String, Set<IndexColumnMetadata>> uniqueIndexes = new HashMap();

        class IndexColumnMetadata implements Comparable<IndexColumnMetadata> {
            boolean mAscending;
            String mColumnName;
            int mOrder;

            public IndexColumnMetadata(String str, boolean z, int i) {
                this.mColumnName = str;
                this.mAscending = z;
                this.mOrder = i;
            }

            public int compareTo(IndexColumnMetadata indexColumnMetadata) {
                if (this.mOrder < indexColumnMetadata.mOrder) {
                    return -1;
                }
                if (this.mOrder > indexColumnMetadata.mOrder) {
                    return 1;
                }
                throw new IllegalArgumentException(String.format("Columns '%s' and '%s' cannot have the same composite index order %d", new Object[]{this.mColumnName, indexColumnMetadata.mColumnName, Integer.valueOf(this.mOrder)}));
            }

            public boolean equals(Object obj) {
                if (this != obj) {
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    IndexColumnMetadata indexColumnMetadata = (IndexColumnMetadata) obj;
                    if (this.mColumnName == null) {
                        if (indexColumnMetadata.mColumnName != null) {
                            return false;
                        }
                    } else if (!this.mColumnName.equals(indexColumnMetadata.mColumnName)) {
                        return false;
                    }
                }
                return true;
            }

            public int hashCode() {
                return (this.mColumnName == null ? 0 : this.mColumnName.hashCode()) + 31;
            }
        }

        private void addCompositeIndex(String str, Map<String, Set<IndexColumnMetadata>> map, boolean z, int i, String str2) {
            Set set = (Set) map.get(str2);
            if (set == null) {
                set = new HashSet();
                map.put(str2, set);
            }
            if (!set.add(new IndexColumnMetadata(str, z, i))) {
                throw new IllegalArgumentException(String.format("Column '%s' has two indexes with the same name %s", new Object[]{str, str2}));
            }
        }

        private void addCompositeIndexes(String str, Map<String, Set<IndexColumnMetadata>> map, CompositeIndex[] compositeIndexArr) {
            for (CompositeIndex compositeIndex : compositeIndexArr) {
                addCompositeIndex(str, map, compositeIndex.ascending(), compositeIndex.order(), compositeIndex.indexName());
            }
        }

        public void addIndexedColumn(String str, String str2, Index index) {
            boolean z;
            if (index.indexNames().length != 0) {
                addCompositeIndexes(str2, this.indexes, index.indexNames());
                z = true;
            } else {
                z = false;
            }
            if (index.uniqueNames().length != 0) {
                addCompositeIndexes(str2, this.uniqueIndexes, index.uniqueNames());
                z = true;
            }
            if (!z) {
                addCompositeIndex(str2, index.unique() ? this.uniqueIndexes : this.indexes, true, 0, String.format(GENERATED_INDEX_NAME, new Object[]{str, str2}));
            }
        }

        public void addStatementToList(String str, boolean z, List<IndexStatement> list, Set<IndexColumnMetadata> set) {
            List arrayList = new ArrayList(set);
            Collections.sort(arrayList);
            int size = arrayList.size();
            String[] strArr = new String[size];
            boolean[] zArr = new boolean[size];
            for (int i = 0; i < size; i++) {
                IndexColumnMetadata indexColumnMetadata = (IndexColumnMetadata) arrayList.get(i);
                strArr[i] = indexColumnMetadata.mColumnName;
                zArr[i] = indexColumnMetadata.mAscending;
            }
            list.add(new IndexStatement(z, strArr, zArr, str));
        }

        public List<IndexStatement> build() {
            List<IndexStatement> arrayList = new ArrayList();
            Set hashSet = new HashSet();
            for (Entry entry : this.indexes.entrySet()) {
                String str = (String) entry.getKey();
                hashSet.add(str);
                addStatementToList(str, false, arrayList, (Set) entry.getValue());
            }
            for (Entry entry2 : this.uniqueIndexes.entrySet()) {
                str = (String) entry2.getKey();
                if (hashSet.add(str)) {
                    addStatementToList(str, true, arrayList, (Set) entry2.getValue());
                } else {
                    throw new IllegalArgumentException(String.format("There are both unique and non-unique indexes with the same name : %s", new Object[]{str}));
                }
            }
            return arrayList;
        }

        public Map<String, IndexStatement> buildAsMap() {
            Map<String, IndexStatement> hashMap = new HashMap();
            for (IndexStatement indexStatement : build()) {
                hashMap.put(indexStatement.mIndexName, indexStatement);
            }
            return hashMap;
        }
    }

    public IndexStatement(boolean z, String[] strArr, boolean[] zArr, String str) {
        this.mIsUnique = z;
        this.mColumnNames = strArr;
        this.mAscendings = zArr;
        this.mIndexName = str;
    }

    public String getCreationSql(String str) {
        return getCreationSql(str, true);
    }

    public String getCreationSql(String str, boolean z) {
        StringBuilder stringBuilder = new StringBuilder("create ");
        if (this.mIsUnique) {
            stringBuilder.append("unique ");
        }
        stringBuilder.append("index ");
        if (z) {
            stringBuilder.append("if not exists ");
        }
        stringBuilder.append(INDEX_PREFIX).append(this.mIndexName).append(" on %s (");
        int length = this.mColumnNames.length;
        stringBuilder.append('\'').append(this.mColumnNames[0]).append("' ").append(this.mAscendings[0] ? "ASC" : "DESC");
        for (int i = 1; i < length; i++) {
            stringBuilder.append(", '").append(this.mColumnNames[i]).append("' ").append(this.mAscendings[i] ? "ASC" : "DESC");
        }
        stringBuilder.append(')');
        return String.format(stringBuilder.toString(), new Object[]{str, Boolean.valueOf(z)});
    }
}
