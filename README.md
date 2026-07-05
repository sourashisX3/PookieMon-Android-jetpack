# PookieMon

A Pokémon browsing Android app built with Jetpack Compose, Hilt, Room, Retrofit, and Kotlin Serialization.

## Features

- **Pokémon** — Browse with a paginated grid, pull-to-refresh, view detailed stats/abilities/evolutions
- **Items** & **Moves** — Paginated grids with details, shimmer loading
- **Explore** — Berries, locations, evolution chains, encounter methods, games/generations, references (abilities, natures, types)
- **Settings** — Dark mode (system/light/dark), accent color palette selection
- **Floating Navigation Bar** — Capsule-style tab indicator with animated sliding, crossfading icon, and expand/shrink label
- **Shared element transitions** between list and detail screens
- **Flavor-aware build configs** (`devDebug` / `prodDebug`) with environment-specific API URLs

## Tech Stack

| Layer | Library |
|-------|---------|
| **UI** | Jetpack Compose + Material 3 |
| **Navigation** | Navigation Compose (type-safe routes) |
| **DI** | Dagger Hilt |
| **Network** | Retrofit + OkHttp + Kotlin Serialization |
| **Database** | Room (offline cache) |
| **Image Loading** | Coil |
| **Animations** | Lottie (splash), Compose animation APIs |
| **Build** | Gradle with product flavors |

## Project Structure

```
app/src/main/java/com/funapp/pookiemon/
├── core/
│   ├── config/
│   │   ├── navigation/        # AppNavGraph, Route definitions
│   │   └── network/           # ApiConstants, PokeApi
│   ├── data/
│   │   └── settings/          # DataStore preferences (theme, dark mode)
│   ├── database/              # AppDatabase, Converters
│   ├── di/                    # Hilt modules (app, network, database)
│   ├── theme/                 # Material3 theme, color palettes, typography
│   ├── ui/
│   │   └── components/        # Shared composables (nav bar, scroll-to-top, error view, shimmer)
│   └── utils/                 # Extensions, haptic feedback
└── feature/
    ├── pokemon/               # Pokémon list + detail
    ├── item/                  # Items list + detail
    ├── move/                  # Moves list + detail
    ├── explore/               # Berries, locations, evolution, encounters, games, references
    ├── settings/              # Theme + dark mode preferences
    ├── berry/                 # Berry list + detail
    ├── encounter/             # Encounter methods + conditions
    ├── evolution/             # Evolution chain viewer
    ├── games/                 # Generations + versions
    ├── location/              # Locations + areas
    └── references/            # Abilities, natures, types
```

Each feature follows clean architecture with `data/`, `domain/`, and `presentation/` layers.

## Build

```bash
# Development (default API)
./gradlew app:assembleDevDebug

# Production
./gradlew app:assembleProdDebug
```
