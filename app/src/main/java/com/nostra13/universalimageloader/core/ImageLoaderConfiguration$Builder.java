package com.nostra13.universalimageloader.core;

import android.content.Context;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.FuzzyKeyMemoryCache;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.ImageDecoder;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import java.util.concurrent.Executor;

public class ImageLoaderConfiguration$Builder {
    public static final QueueProcessingType DEFAULT_TASK_PROCESSING_TYPE = QueueProcessingType.FIFO;
    public static final int DEFAULT_THREAD_POOL_SIZE = 3;
    public static final int DEFAULT_THREAD_PRIORITY = 3;
    private static final String WARNING_OVERLAP_DISK_CACHE_NAME_GENERATOR = "diskCache() and diskCacheFileNameGenerator() calls overlap each other";
    private static final String WARNING_OVERLAP_DISK_CACHE_PARAMS = "diskCache(), diskCacheSize() and diskCacheFileCount calls overlap each other";
    private static final String WARNING_OVERLAP_EXECUTOR = "threadPoolSize(), threadPriority() and tasksProcessingOrder() calls can overlap taskExecutor() and taskExecutorForCachedImages() calls.";
    private static final String WARNING_OVERLAP_MEMORY_CACHE = "memoryCache() and memoryCacheSize() calls overlap each other";
    private Context context;
    private boolean customExecutor = false;
    private boolean customExecutorForCachedImages = false;
    private ImageDecoder decoder;
    private DisplayImageOptions defaultDisplayImageOptions = null;
    private boolean denyCacheImageMultipleSizesInMemory = false;
    private DiskCache diskCache = null;
    private int diskCacheFileCount = 0;
    private FileNameGenerator diskCacheFileNameGenerator = null;
    private long diskCacheSize = 0;
    private ImageDownloader downloader = null;
    private int maxImageHeightForDiskCache = 0;
    private int maxImageHeightForMemoryCache = 0;
    private int maxImageWidthForDiskCache = 0;
    private int maxImageWidthForMemoryCache = 0;
    private MemoryCache memoryCache = null;
    private int memoryCacheSize = 0;
    private BitmapProcessor processorForDiskCache = null;
    private Executor taskExecutor = null;
    private Executor taskExecutorForCachedImages = null;
    private QueueProcessingType tasksProcessingType = DEFAULT_TASK_PROCESSING_TYPE;
    private int threadPoolSize = 3;
    private int threadPriority = 3;
    private boolean writeLogs = false;

    public ImageLoaderConfiguration$Builder(Context context) {
        this.context = context.getApplicationContext();
    }

    private void initEmptyFieldsWithDefaultValues() {
        if (this.taskExecutor == null) {
            this.taskExecutor = DefaultConfigurationFactory.createExecutor(this.threadPoolSize, this.threadPriority, this.tasksProcessingType);
        } else {
            this.customExecutor = true;
        }
        if (this.taskExecutorForCachedImages == null) {
            this.taskExecutorForCachedImages = DefaultConfigurationFactory.createExecutor(this.threadPoolSize, this.threadPriority, this.tasksProcessingType);
        } else {
            this.customExecutorForCachedImages = true;
        }
        if (this.diskCache == null) {
            if (this.diskCacheFileNameGenerator == null) {
                this.diskCacheFileNameGenerator = DefaultConfigurationFactory.createFileNameGenerator();
            }
            this.diskCache = DefaultConfigurationFactory.createDiskCache(this.context, this.diskCacheFileNameGenerator, this.diskCacheSize, this.diskCacheFileCount);
        }
        if (this.memoryCache == null) {
            this.memoryCache = DefaultConfigurationFactory.createMemoryCache(this.context, this.memoryCacheSize);
        }
        if (this.denyCacheImageMultipleSizesInMemory) {
            this.memoryCache = new FuzzyKeyMemoryCache(this.memoryCache, MemoryCacheUtils.createFuzzyKeyComparator());
        }
        if (this.downloader == null) {
            this.downloader = DefaultConfigurationFactory.createImageDownloader(this.context);
        }
        if (this.decoder == null) {
            this.decoder = DefaultConfigurationFactory.createImageDecoder(this.writeLogs);
        }
        if (this.defaultDisplayImageOptions == null) {
            this.defaultDisplayImageOptions = DisplayImageOptions.createSimple();
        }
    }

    public ImageLoaderConfiguration build() {
        initEmptyFieldsWithDefaultValues();
        return new ImageLoaderConfiguration(this, null);
    }

    public ImageLoaderConfiguration$Builder defaultDisplayImageOptions(DisplayImageOptions displayImageOptions) {
        this.defaultDisplayImageOptions = displayImageOptions;
        return this;
    }

    public ImageLoaderConfiguration$Builder denyCacheImageMultipleSizesInMemory() {
        this.denyCacheImageMultipleSizesInMemory = true;
        return this;
    }

    @Deprecated
    public ImageLoaderConfiguration$Builder discCache(DiskCache diskCache) {
        return diskCache(diskCache);
    }

    @Deprecated
    public ImageLoaderConfiguration$Builder discCacheExtraOptions(int i, int i2, BitmapProcessor bitmapProcessor) {
        return diskCacheExtraOptions(i, i2, bitmapProcessor);
    }

    @Deprecated
    public ImageLoaderConfiguration$Builder discCacheFileCount(int i) {
        return diskCacheFileCount(i);
    }

    @Deprecated
    public ImageLoaderConfiguration$Builder discCacheFileNameGenerator(FileNameGenerator fileNameGenerator) {
        return diskCacheFileNameGenerator(fileNameGenerator);
    }

    @Deprecated
    public ImageLoaderConfiguration$Builder discCacheSize(int i) {
        return diskCacheSize(i);
    }

    public ImageLoaderConfiguration$Builder diskCache(DiskCache diskCache) {
        if (this.diskCacheSize > 0 || this.diskCacheFileCount > 0) {
            L.w(WARNING_OVERLAP_DISK_CACHE_PARAMS, new Object[0]);
        }
        if (this.diskCacheFileNameGenerator != null) {
            L.w(WARNING_OVERLAP_DISK_CACHE_NAME_GENERATOR, new Object[0]);
        }
        this.diskCache = diskCache;
        return this;
    }

    public ImageLoaderConfiguration$Builder diskCacheExtraOptions(int i, int i2, BitmapProcessor bitmapProcessor) {
        this.maxImageWidthForDiskCache = i;
        this.maxImageHeightForDiskCache = i2;
        this.processorForDiskCache = bitmapProcessor;
        return this;
    }

    public ImageLoaderConfiguration$Builder diskCacheFileCount(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxFileCount must be a positive number");
        }
        if (this.diskCache != null) {
            L.w(WARNING_OVERLAP_DISK_CACHE_PARAMS, new Object[0]);
        }
        this.diskCacheFileCount = i;
        return this;
    }

    public ImageLoaderConfiguration$Builder diskCacheFileNameGenerator(FileNameGenerator fileNameGenerator) {
        if (this.diskCache != null) {
            L.w(WARNING_OVERLAP_DISK_CACHE_NAME_GENERATOR, new Object[0]);
        }
        this.diskCacheFileNameGenerator = fileNameGenerator;
        return this;
    }

    public ImageLoaderConfiguration$Builder diskCacheSize(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("maxCacheSize must be a positive number");
        }
        if (this.diskCache != null) {
            L.w(WARNING_OVERLAP_DISK_CACHE_PARAMS, new Object[0]);
        }
        this.diskCacheSize = (long) i;
        return this;
    }

    public ImageLoaderConfiguration$Builder imageDecoder(ImageDecoder imageDecoder) {
        this.decoder = imageDecoder;
        return this;
    }

    public ImageLoaderConfiguration$Builder imageDownloader(ImageDownloader imageDownloader) {
        this.downloader = imageDownloader;
        return this;
    }

    public ImageLoaderConfiguration$Builder memoryCache(MemoryCache memoryCache) {
        if (this.memoryCacheSize != 0) {
            L.w(WARNING_OVERLAP_MEMORY_CACHE, new Object[0]);
        }
        this.memoryCache = memoryCache;
        return this;
    }

    public ImageLoaderConfiguration$Builder memoryCacheExtraOptions(int i, int i2) {
        this.maxImageWidthForMemoryCache = i;
        this.maxImageHeightForMemoryCache = i2;
        return this;
    }

    public ImageLoaderConfiguration$Builder memoryCacheSize(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("memoryCacheSize must be a positive number");
        }
        if (this.memoryCache != null) {
            L.w(WARNING_OVERLAP_MEMORY_CACHE, new Object[0]);
        }
        this.memoryCacheSize = i;
        return this;
    }

    public ImageLoaderConfiguration$Builder memoryCacheSizePercentage(int i) {
        if (i <= 0 || i >= 100) {
            throw new IllegalArgumentException("availableMemoryPercent must be in range (0 < % < 100)");
        }
        if (this.memoryCache != null) {
            L.w(WARNING_OVERLAP_MEMORY_CACHE, new Object[0]);
        }
        this.memoryCacheSize = (int) (((float) Runtime.getRuntime().maxMemory()) * (((float) i) / 100.0f));
        return this;
    }

    public ImageLoaderConfiguration$Builder taskExecutor(Executor executor) {
        if (!(this.threadPoolSize == 3 && this.threadPriority == 3 && this.tasksProcessingType == DEFAULT_TASK_PROCESSING_TYPE)) {
            L.w(WARNING_OVERLAP_EXECUTOR, new Object[0]);
        }
        this.taskExecutor = executor;
        return this;
    }

    public ImageLoaderConfiguration$Builder taskExecutorForCachedImages(Executor executor) {
        if (!(this.threadPoolSize == 3 && this.threadPriority == 3 && this.tasksProcessingType == DEFAULT_TASK_PROCESSING_TYPE)) {
            L.w(WARNING_OVERLAP_EXECUTOR, new Object[0]);
        }
        this.taskExecutorForCachedImages = executor;
        return this;
    }

    public ImageLoaderConfiguration$Builder tasksProcessingOrder(QueueProcessingType queueProcessingType) {
        if (!(this.taskExecutor == null && this.taskExecutorForCachedImages == null)) {
            L.w(WARNING_OVERLAP_EXECUTOR, new Object[0]);
        }
        this.tasksProcessingType = queueProcessingType;
        return this;
    }

    public ImageLoaderConfiguration$Builder threadPoolSize(int i) {
        if (!(this.taskExecutor == null && this.taskExecutorForCachedImages == null)) {
            L.w(WARNING_OVERLAP_EXECUTOR, new Object[0]);
        }
        this.threadPoolSize = i;
        return this;
    }

    public ImageLoaderConfiguration$Builder threadPriority(int i) {
        if (!(this.taskExecutor == null && this.taskExecutorForCachedImages == null)) {
            L.w(WARNING_OVERLAP_EXECUTOR, new Object[0]);
        }
        if (i <= 0) {
            this.threadPriority = 1;
        } else if (i > 10) {
            this.threadPriority = 10;
        } else {
            this.threadPriority = i;
        }
        return this;
    }

    public ImageLoaderConfiguration$Builder writeDebugLogs() {
        this.writeLogs = true;
        return this;
    }
}
