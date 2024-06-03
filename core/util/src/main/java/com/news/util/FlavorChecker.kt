package com.news.util


class FlavorChecker {
    companion object {
        private fun checkFlavor(source: String): Boolean {
            return BuildConfig.FLAVOR.contains(source)
        }

        fun isBBCFlavor(): Boolean {
            return checkFlavor("bbc")
        }

        fun isFullFlavor(): Boolean {
            return checkFlavor("full")
        }
    }
}