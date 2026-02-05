package com.oadultradeepfield.skymatch

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.crossfade
import com.oadultradeepfield.skymatch.config.AppConfig
import dagger.hilt.android.HiltAndroidApp

/** Application class with optimized Coil image caching configuration. */
@HiltAndroidApp
class App : Application(), SingletonImageLoader.Factory {
  override fun newImageLoader(context: android.content.Context): ImageLoader {
    return ImageLoader.Builder(context)
        .memoryCache {
          MemoryCache.Builder()
              .maxSizePercent(context, AppConfig.Cache.MEMORY_CACHE_PERCENT)
              .build()
        }
        .diskCache {
          DiskCache.Builder()
              .directory(cacheDir.resolve("image_cache"))
              .maxSizePercent(AppConfig.Cache.DISK_CACHE_PERCENT)
              .build()
        }
        .crossfade(true)
        .build()
  }
}
