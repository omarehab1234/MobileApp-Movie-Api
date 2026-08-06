# Fix Navigation and Crash in MovieDetailScreen

The user reports a crash when navigating to the `MovieDetailScreen` and that nothing happens. Research indicates two primary issues:
1. **Room Crash**: The `MovieDao.getMovie(id)` query has a non-nullable `Movie` return type. When a movie that is not in the database (i.e., not a favorite) is clicked, Room returns `null`, causing a crash in the Kotlin code.
2. **Missing Data**: Even if the crash is fixed, `MovieDetailScreen` only fetches the movie from the local database. Since the app lacks an API endpoint to fetch a single movie by ID, clicking a movie from the "Popular" list will result in a blank screen because it's not yet in the database.

## Proposed Changes

### Data Layer

#### [MODIFY] [MovieDao.kt](file:///C:/Users/ALSAAD NASR CITY/AndroidStudioProjects/MyApplication/app/src/main/java/com/example/myapplication/data/db/MovieDao.kt)
- Change `getMovie` return type to `Movie?` to prevent crashes when a movie is not found in the database.

### Navigation

#### [MODIFY] [AppNavigation.kt](file:///C:/Users/ALSAAD NASR CITY/AndroidStudioProjects/MyApplication/app/src/main/java/com/example/myapplication/ui/navigation/AppNavigation.kt)
- Update the route for movie details to accept a JSON string of the `Movie` object. This ensures the detail screen has the data immediately without needing a separate API call or database record.
- Convert `AppNavigation` from a class to a top-level `@Composable` function for better idiomatic use and to avoid unnecessary object creation in `MainActivity`.

#### [MODIFY] [MovieScreenShowAll.kt](file:///C:/Users/ALSAAD NASR CITY/AndroidStudioProjects/MyApplication/app/src/main/java/com/example/myapplication/ui/screens/MovieScreenShowAll.kt)
- Update the click listener to serialize the `Movie` object to JSON and pass it in the navigation route.

#### [MODIFY] [MainActivity.kt](file:///C:/Users/ALSAAD NASR CITY/AndroidStudioProjects/MyApplication/app/src/main/java/com/example/myapplication/MainActivity.kt)
- Update `setContent` to call the top-level `AppNavigation` function directly.

### UI Layer

#### [MODIFY] [MovieDetailScreen.kt](file:///C:/Users/ALSAAD NASR CITY/AndroidStudioProjects/MyApplication/app/src/main/java/com/example/myapplication/ui/screens/MovieDetailScreen.kt)
- Remove `import android.R`.
- Update the signature to receive the `Movie` object directly (or retrieve it from the navigation arguments).
- Improve the parallax `imageHeight` calculation to use `graphicsLayer` or similar to avoid relayouts during scroll if possible, or at least ensure it's robust. (Note: I will prioritize fixing the data flow first).

## Verification Plan

### Automated Tests
- Run `./gradlew assembleDebug` to ensure compilation.
- I will manually verify the logic by checking the navigation routes match the serialized data.

### Manual Verification
- Deploy to device and verify:
    1. Clicking a popular movie card navigates to the details screen.
    2. The details screen correctly displays movie info (title, overview, image).
    3. The app no longer crashes.
    4. Adding/Removing from favorites works and updates the UI state.
