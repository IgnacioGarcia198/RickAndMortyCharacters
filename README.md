# Rick And Morty Characters App

## Features

The app gets data from [Rick and Morty characters api](https://rickandmortyapi.com/api/character) and display them in a list.
When clicking on a character in the list the user can be some character details.

The main screen allows to perform a basic search by character name, and also an advanced search based on several criteria.
The app changes to light or dark theme following the system.

## Dev Features

- Strict Feature-layer clean architecture (by modules)
- Build logic module (`build-src`) using version catalogs in both `build.gradle.kts` and plugins
- `Room` database
- Rest api queries using `Ktor`
- Paging (`common-jvm`) with abstraction in dedicated module (`paging`)
- Jetpack Compose with light and dark theme, material 3
- Dependency injection using `Hilt`
- Image caching using `Coil` and `Ktor`

## Project Structure

.\
├── android-utils  
├── app  
├── build-src   
├── feature-characters  
│   ├── data  
│   │   └── paging  
│   ├── domain  
│   ├── presentation  
│   └── ui  
├── framework  
│   ├── di-connector  
│   ├── local  
│   └── remote  
├── gradle  
├── kotlin-utils  
├── paging  
├── resources  
└── ui-common


## Project Status

The project is functional, although some things need to be improved:

- Missing some unit and integration tests for Paging affairs (basically `Mediator` and factories), also with db
- Network module has only basic tests and basic exception mapping so far
- Update Java version
- Other TODOs around the project
- Deeper cleanup of dependencies (some modules may have dependencies they don't need)
- Error reporting (logging errors to a file for example)
- App settings (like enabling error logging)
- Build scripts can be further abstracted to plugins
- Some colors need to be adapted to the theme to keep better consistency
