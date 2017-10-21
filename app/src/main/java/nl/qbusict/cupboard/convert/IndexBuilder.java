package nl.qbusict.cupboard.convert;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import nl.qbusict.cupboard.annotation.CompositeIndex;
import nl.qbusict.cupboard.annotation.Index;

public class IndexBuilder {
    private CompositeIndexBuilder mCompositeIndexBuilder = new CompositeIndexBuilder();
    private boolean mUnique = false;

    public class CompositeIndexBuilder {
        private CompositeIndexImpl mCurrentIndex;
        private List<CompositeIndex> mIndices;
        private List<CompositeIndex> mUniqueIndices;

        private CompositeIndexBuilder() {
            this.mIndices = new ArrayList(10);
            this.mUniqueIndices = new ArrayList(10);
        }

        public CompositeIndexBuilder ascending() {
            this.mCurrentIndex.setAscending(true);
            return this;
        }

        public Index build() {
            return new IndexImpl(false, this.mIndices, this.mUniqueIndices);
        }

        public CompositeIndexBuilder descending() {
            this.mCurrentIndex.setAscending(false);
            return this;
        }

        public CompositeIndexBuilder named(String str) {
            this.mCurrentIndex = new CompositeIndexImpl(str);
            this.mIndices.add(this.mCurrentIndex);
            return this;
        }

        public CompositeIndexBuilder order(int i) {
            this.mCurrentIndex.setOrder(i);
            return this;
        }

        public CompositeIndexBuilder unique() {
            if (this.mIndices.remove(this.mCurrentIndex)) {
                this.mUniqueIndices.add(this.mCurrentIndex);
            }
            return this;
        }
    }

    class CompositeIndexImpl implements CompositeIndex {
        private boolean mAscending;
        private final String mName;
        private int mOrder;

        public CompositeIndexImpl(String str) {
            this.mName = str;
        }

        public Class<? extends Annotation> annotationType() {
            return CompositeIndex.class;
        }

        public boolean ascending() {
            return this.mAscending;
        }

        public String indexName() {
            return this.mName;
        }

        public int order() {
            return this.mOrder;
        }

        void setAscending(boolean z) {
            this.mAscending = z;
        }

        void setOrder(int i) {
            this.mOrder = i;
        }
    }

    class IndexImpl implements Index {
        private final CompositeIndex[] mNames;
        private final boolean mUnique;
        private final CompositeIndex[] mUniqueNames;

        public IndexImpl(boolean z, List<CompositeIndex> list, List<CompositeIndex> list2) {
            this.mUnique = z;
            this.mNames = (CompositeIndex[]) list.toArray(new CompositeIndex[list.size()]);
            this.mUniqueNames = (CompositeIndex[]) list2.toArray(new CompositeIndex[list2.size()]);
        }

        public Class<? extends Annotation> annotationType() {
            return Index.class;
        }

        public CompositeIndex[] indexNames() {
            return this.mNames;
        }

        public boolean unique() {
            return this.mUnique;
        }

        public CompositeIndex[] uniqueNames() {
            return this.mUniqueNames;
        }
    }

    public Index build() {
        return new IndexImpl(this.mUnique, this.mCompositeIndexBuilder.mIndices, this.mCompositeIndexBuilder.mUniqueIndices);
    }

    public CompositeIndexBuilder named(String str) {
        this.mCompositeIndexBuilder.named(str);
        if (this.mUnique) {
            this.mCompositeIndexBuilder.unique();
        }
        return this.mCompositeIndexBuilder;
    }

    public IndexBuilder unique() {
        this.mUnique = true;
        return this;
    }
}
