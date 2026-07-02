# PookieMon-Android-jetpack

A Pokemon browsing Android app built with Jetpack Compose, Hilt, Room, Retrofit, and Kotlin Serialization.

## Features

- Browse Pokemon with paginated grid
- View detailed Pokemon info (stats, abilities, evolutions)
- Pull-to-refresh and caching via Room database
- Shared element transitions between list and detail
- Flavor-aware build configs (dev / staging / prod) with API URLs

## Tech Stack

- **UI**: Jetpack Compose, Material 3, Coil
- **Architecture**: MVVM, Clean Architecture (feature/pokemon)
- **DI**: Dagger Hilt
- **Network**: Retrofit + OkHttp + Kotlin Serialization
- **Database**: Room
- **Build**: Gradle with product flavors

## Project Structure

```
app/src/main/java/com/funapp/pookiemon/
├── core/
│   ├── config/
│   │   ├── navigation/    # AppNavGraph, Route
│   │   └── network/       # ApiConstants, EnvironmentConfig
│   ├── database/          # AppDatabase, Converters
│   ├── di/                # App/Network/Database Hilt modules
│   ├── theme/             # Material3 theme, colors, typography
│   ├── ui/components/     # Shared UI components
│   └── utils/             # Extensions, constants
└── feature/
    └── pokemon/           # Pokemon feature (clean architecture)
        ├── data/
        │   ├── dao/                 # Room DAOs (PokemonCacheDao)
        │   ├── datasource/
        │   │   ├── local/           # PokemonLocalDataSource
        │   │   └── remote/          # PokeApi, DTOs, RemoteDataSource
        │   ├── entity/              # Room entities
        │   ├── mapper/              # DTO ↔ Domain mapping
        │   └── repository/          # PokemonRepositoryImpl
        ├── domain/
        │   ├── model/               # Domain models
        │   ├── repository/          # PokemonRepository interface
        │   └── use_case/            # GetPokemonList, GetPokemonDetail
        └── presentation/
            ├── components/          # Reusable composables
            ├── di/                  # PokemonModule
            ├── events/              # UI event classes
            ├── screens/             # Screen composables (detail, list)
            ├── states/              # UI state classes
            └── viewmodels/          # ViewModels
```
