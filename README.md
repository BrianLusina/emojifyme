# EmojifyMe

A toy app for "emojifying" your face :smile: with your camera. This project has great consideration for modularity and is focused on MVP (Model View Presenter) pattern. Even though it is a small project, this pattern allows it to be easily adopted and built upon easily.

View the app on [PlayStore](https://play.google.com/store/apps/details?id=com.emojify.me)

## Getting Started

Simply clone the project onto your local machine and you are ready to go.

```bash
$ git clone https://github.com/BrianLusina/emojifyme.git
```

Create a `keystores.properties` file that matches something like this:

```properties
keyAlias=<KEY_ALIAS>
keyPassword=<KEY_PASSWORD>
storeFile=<STORE_FILE>
storePassword=<STORE_PASSWORD>
```
> Keystore properties file

This will be used when generating release builds. Read more about this [here](https://developer.android.com/studio/publish/app-signing.html)

## Pre-requisites

You will need to have [Android Studio 3.0](https://developer.android.com/studio/index.html) installed on your development environment. This project is developed with Android Studio 3.0 stable release.

## Built with

+ [Kotlin](https://kotlinlang.org/)
+ [Dagger2](https://google.github.io/dagger/)
+ [Google Vision Library](https://developers.google.com/vision/android/getting-started)

## License

This project is licenses under the Apache License See the [License](./LICENSE) for more details

[![forthebadge](http://forthebadge.com/images/badges/built-for-android.svg)](http://forthebadge.com)
[![forthebadge](http://forthebadge.com/images/badges/built-with-love.svg)](http://forthebadge.com)