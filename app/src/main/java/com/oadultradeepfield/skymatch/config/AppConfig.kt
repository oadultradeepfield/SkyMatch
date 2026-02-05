package com.oadultradeepfield.skymatch.config

object AppConfig {
    object Network {
        const val DEFAULT_DELAY_MS = 500L
        const val DEBOUNCE_DELAY_MS = 300L
    }

    object Fake {
        const val FAILURE_PROBABILITY = 0.10
        const val OBSERVE_FAILURE_PROBABILITY = 0.20
    }

    object UI {
        const val GRID_COLUMNS = 2
        const val BOTTOM_PADDING_DP = 120
        const val MAX_PHOTO_PICKER_ITEMS = 10
    }

    object Cache {
        const val MEMORY_CACHE_PERCENT = 0.25
        const val DISK_CACHE_PERCENT = 0.02
    }
}
