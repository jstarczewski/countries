# Countries
Simple app that displays countries, created as a recruitment task for a position of Android internm in Indroorway.
## Requirements
- [x] User sees countries list on main screen.
- [x] User searches for specific country with search bar located on main screen.
- [x] After taping on a specific country user sees it's details on second screen.
- [x] Details screen contains map centered on selected country. 
## Preview
![gif](/pictures/all.gif)

## Technologies and architecture
Countries app was built with **Kotlin** and uses **MVVM** architecture, other tools used in development process are:
- [x] Dagger as dependency injection framework
- [x] Retrofit for executing network calls
- [x] Room for offline data storage
- [x] RxJava for async calls to data sources
- [x] Databinding library.  
### Testing
Testing involves simple unit tests, tests using hand written fake dependencies, mock tests and simple integration tests. For testing I used following tools:
- [x] JUnit
- [x] Mockito
- [x] Espresso
