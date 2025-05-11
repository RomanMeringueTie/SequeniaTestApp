# Тестовое задание для компании [Секвения](https://sequenia.com/)

## Android приложение для отображения списка фильмов и просмотра информации о них.<br>За основу взята информация с сайта КиноПоиск.

# [APK Файл](/app-release.apk)

### Минимальная поддерживаемая версия Android: 8 (SDK 26)

### Приложение состоит из двух экранов
- Список фильмов с фильтром по жанру (есть возможность обновления по свайпу)
- Подробная информация о фильме

### Стек
- Язык программирования: Kotlin
- UI: View/XML/Fragments
- DI: Koin
- Сетеые запросы: Retrofit, OkHttp
- Unit Тесты: Junit, Mockk
- UI Тесты: Espresso
- Картинки по URL: Glide
- Асинхронность: Kotlin Coroutines
- Json сериализация: Kotlinx serialization json

### Архитектура
- MVVM
- Single Activity

### CI
- На каждый пуш в master выполнятся сборка и запуск тестов.
- Результат можно посмотреть в [Actions](https://github.com/RomanMeringueTie/SequeniaTestApp/actions)