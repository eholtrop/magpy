package com.n41t.magpy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform