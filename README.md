# STARZPLAY Android Code Challenge


## 🧠 Tech Stack

- **Language:** Kotlin
- **UI Toolkit:** Jetpack Compose
- **Architecture:** Clean Architecture (multi-module)
- **Design Pattern:** MVI (Model-View-Intent)
- **Dependency Injection:** Hilt
- **Networking:** Retrofit + OkHttp
- **Image Loading:** Coil
- **Local Persistence:** DataStore


✨ Features Implemented 
- Search Screen – Search with debounced input and responsive UI for both mobile and tablets
- Media Carousels – Results are grouped and displayed by media type (movie, tv, others)
- Detail Screen – Displays selected media's title, image, description, and conditionally a "Play" button for movies or TV
- Basic ExoPlayer Integration – Playback support for media items using ExoPlayer (TV & movie only)
- Shared ViewModel – Maintains selected media across navigation using Hilt-scoped ViewModel
- Image Caching – Image loading via Coil with disk caching and offline support (6 Hours)
- API Caching – Network calls with retrofit with caching and offline support (1 Hour)
- Clean Navigation – Single-activity architecture with NavController and type-safe routes
- Scalable DI Setup – Hilt-based DI for networking, repositories, data sources, and local storage
- Unit Testing Added – Includes tests for ViewModel and Repository with mocked data sources

👤 Author
AWAIZ HASSAN
Senior Android Developer