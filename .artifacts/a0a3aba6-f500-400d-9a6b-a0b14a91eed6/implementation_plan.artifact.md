# Fix Android BaseExtension not found error

The project is using Android Gradle Plugin (AGP) 9.3.1, which introduces a new DSL and hides the legacy `BaseExtension` by default. Third-party plugins like Hilt (version 2.57) still rely on this legacy extension, leading to the "Android BaseExtension not found" error during sync.

## Proposed Changes

### Build Configuration

#### [MODIFY] [gradle.properties](file:///C:/Users/ALSAAD NASR CITY/AndroidStudioProjects/MyApplication/gradle.properties)
- Add `android.newDsl=false` to restore compatibility with plugins that depend on the legacy `BaseExtension`.
- Add `android.builtInKotlin=false` if we want to continue using the `kotlin-android` plugin explicitly, or keep it `true` if we want to use AGP 9's built-in Kotlin support. Given the current state, `android.newDsl=false` is the critical fix for Hilt.

#### [MODIFY] [libs.versions.toml](file:///C:/Users/ALSAAD NASR CITY/AndroidStudioProjects/MyApplication/gradle/libs.versions.toml)
- Upgrade Hilt to `2.60.1` for better compatibility and bug fixes.

## Verification Plan

### Automated Tests
- Run Gradle sync to ensure the "Android BaseExtension not found" error is resolved.
- Build the project using `gradlew assembleDebug`.

### Manual Verification
- Verify that Hilt code generation works as expected.
