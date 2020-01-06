### Description

This is a simple Android application that lists movies from "The Movie Database (TMDb)". It consists
of a simple screen that lists the most popular movies. For each movie we can see the following elements:

1. Title
2. Poster Image
3. Release Date
4. Voting Average Score (as a percentage)
5. Flag marking it as a personal favourite

The personal favourite flag is only implemented locally.

There are 2 implementations of this application:

1. MovieListRx: Uses RxJava2 to manage the TMDb and internal cache operations
2. MovieListCR: Uses Kotlin Coroutines to manage the TMDb and internal cache operations

### Use

Both projects are Android Studio projects. To build the selected app, import the project in the corresponding directory into Android Studio.

The app consists only of a single screen where the popular movies are displayed.
When reaching the bottom of the movie list try to scroll further down to see more movies.

Please note that while the app can present its data in both portrait and landscape modes,
its layout has only been designed for the former.

### Configuration

The API configuration properties are in the project `build.gradle` file. There are two external properties defined:

- apiBaseUrl
- apiKey

Please modify these properties only if you wish to change the API key or endpoint.

### Design

The design follows the MVVM and Clean Architecture design patterns. Consequently,
the design might appear a bit too complex for such a simple project. However, it
was selected to illustrate the principles of the chosen design patterns,
which are better suited to more complex, large scale projects.

LiveData is used for the MVVM implementation, in conjunction with Retrofit2
utilised for server communication. The Room database is used for caching
and local persistence purposes and Koin for dependency injection. The server and
cache operations are orchestrated using either RxJava2 or Kotlin Coroutines (the
former in the MovieListRx project and the latter in the MovieListCR one).

#### Future Tasks

In both project implementations there is room for improvement. Tasks related to such
improvement include:

1. Implement cache cleaning strategy
2. Code re-factoring
3. Layout re-factoring
4. Adding styles and grouping layout dimensions
5. More unit tests
6. UI tests
7. More manual testing
8. UI improvements 

### Contact

Please e-mail <akm799@yahoo.com> if there are any questions or requests.


