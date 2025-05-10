package com.sequenia.movies.di

import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class AppModuleTest {
    @Test
    fun `check Koin module`() {
        appModule.verify()
    }
}