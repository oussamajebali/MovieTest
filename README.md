# 🎬 Movie App - Application Android

Application Android moderne développée avec Jetpack Compose, Clean Architecture et MVVM.

**Package**: `com.example.movietest`

## 📱 Fonctionnalités

- Liste des films populaires depuis l'API OMDb
- Écran détaillé pour chaque film avec informations complètes
- Architecture Clean avec séparation stricte des couches
- Interface moderne et colorée (Material Design 3)
- Gestion d'erreurs robuste et états de chargement
- Tests unitaires complets avec mocking
- Constantes centralisées et strings externalisés

## 🏗️ Architecture

Le projet suit les principes de **Clean Architecture** avec trois couches distinctes :

### Couches

```
app/
├── data/               # Couche Data
│   ├── remote/        # API et DTOs
│   ├── mapper/        # Conversion DTO -> Domain
│   └── repository/    # Implémentation des repositories
├── domain/            # Couche Domain (Business Logic)
│   ├── model/        # Modèles métier
│   ├── repository/   # Interfaces des repositories
│   ├── usecase/      # Cas d'utilisation
│   └── util/         # Resource (wrapper d'états)
├── presentation/     # Couche Presentation (UI)
│   ├── movielist/    # Liste des films
│   ├── moviedetail/  # Détails d'un film
│   └── navigation/   # Navigation Compose
├── ui/theme/         # Thème Material Design 3
└── util/             # Constantes et utilitaires globaux
```

### Pattern MVVM

- **Model** : Modèles de données dans `domain/model`
- **View** : Composables Jetpack Compose
- **ViewModel** : Gestion d'état avec StateFlow

## 🛠️ Stack Technique

- **Langage** : Kotlin
- **UI** : Jetpack Compose + Material3
- **Architecture** : Clean Architecture + MVVM
- **DI** : Hilt (Dagger)
- **Réseau** : Retrofit + OkHttp
- **Async** : Coroutines + Flow
- **Images** : Coil
- **Navigation** : Navigation Compose
- **Tests** : JUnit4 + Mockito
- **API** : OMDb (The Open Movie Database)

## 🚀 Installation et Configuration

### 1. Prérequis

- Android Studio Hedgehog (2023.1.1) ou supérieur
- JDK 11
- SDK Android 24+ (Android 7.0)

### 2. Cloner le projet

```bash
git clone [URL_DU_REPO]
cd MovieTest
```

### 3. Configuration API OMDb

**Bonne nouvelle** : L'application est déjà configurée avec une clé API OMDb gratuite ! 

La clé est déjà incluse dans le fichier `app/build.gradle.kts` et permet jusqu'à **1000 requêtes par jour** (largement suffisant pour la démo).

Si besoin d'obtenir ta propre clé (optionnel) :
1. Va sur [OMDb API](https://www.omdbapi.com/apikey.aspx)
2. Choisis le plan gratuit
3. Vérifie ton email
4. Remplace la clé dans `build.gradle.kts` ligne 26

### 4. Lancer l'application

1. Ouvrir le projet dans Android Studio
2. Sync Gradle
3. Lancer sur émulateur ou appareil physique

**L'application fonctionne immédiatement sans configuration supplémentaire !** ✅

## 🧪 Tests

### Lancer les tests unitaires

```bash
./gradlew test
```

### Tests inclus

- `GetPopularMoviesUseCaseTest` : 5 tests - Use case de récupération des films
- `GetMovieDetailsUseCaseTest` : 6 tests - Use case de détails
- `MovieListViewModelTest` : 3 tests - ViewModel de la liste
- `MovieDetailViewModelTest` : 5 tests - ViewModel des détails

**Total : 20 tests unitaires**

### Couverture

- ✅ Cas de succès avec données
- ✅ Gestion d'erreurs réseau
- ✅ États de chargement
- ✅ Edge cases (liste vide, données nulles)
- ✅ Transitions d'états (Loading → Success/Error)

## 📁 Structure des fichiers

```
app/src/main/java/com/example/movietest/
├── data/
│   ├── mapper/
│   │   └── MovieMapper.kt
│   ├── remote/
│   │   ├── api/
│   │   │   └── OmdbApi.kt
│   │   └── dto/
│   │       └── MovieDto.kt
│   └── repository/
│       └── MovieRepositoryImpl.kt
├── di/
│   └── AppModule.kt
├── domain/
│   ├── model/
│   │   └── Movie.kt
│   ├── repository/
│   │   └── MovieRepository.kt
│   ├── usecase/
│   │   ├── GetMovieDetailsUseCase.kt
│   │   └── GetPopularMoviesUseCase.kt
│   └── util/
│       └── Resource.kt
├── presentation/
│   ├── moviedetail/
│   │   ├── MovieDetailScreen.kt
│   │   ├── MovieDetailState.kt
│   │   └── MovieDetailViewModel.kt
│   ├── movielist/
│   │   ├── MovieListScreen.kt
│   │   ├── MovieListState.kt
│   │   └── MovieListViewModel.kt
│   └── navigation/
│       ├── NavGraph.kt
│       └── Screen.kt
├── ui/theme/
│   ├── Color.kt
│   ├── Theme.kt
│   └── Type.kt
├── util/
│   ├── Constants.kt
│   └── ErrorMessages.kt
├── MainActivity.kt
└── MovieApplication.kt
```

### Fichiers de ressources

```
app/src/main/res/
├── values/
│   ├── strings.xml      # Tous les textes UI et accessibilité
│   ├── colors.xml       # Palette de couleurs
│   └── themes.xml       # Configuration thème
└── xml/
    ├── backup_rules.xml
    └── data_extraction_rules.xml
```

### Tests

```
app/src/test/java/com/example/movietest/
├── domain/usecase/
│   ├── GetPopularMoviesUseCaseTest.kt
│   └── GetMovieDetailsUseCaseTest.kt
└── presentation/
    ├── movielist/
    │   └── MovieListViewModelTest.kt
    └── moviedetail/
        └── MovieDetailViewModelTest.kt
```

## 👨‍💻 Auteur

Développé par Oussama pour le test technique
