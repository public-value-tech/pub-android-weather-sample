# Android Weather Sample App

<div>
<img alt="Kotlin Static Badge" src="https://img.shields.io/badge/Kotlin-7f52ff?style=for-the-badge&logo=Kotlin&logoColor=ffffff">
<img alt="Flows Static Badge" src="https://img.shields.io/badge/Flows-7f52ff?style=for-the-badge">
<img alt="Coroutines Static Badge" src="https://img.shields.io/badge/Coroutines-7f52ff?style=for-the-badge">
<img alt="Jetpack Static Badge" src="https://img.shields.io/badge/Jetpack-3983f9?style=for-the-badge&logo=JetpackCompose&logoColor=ffffff">
<img alt="Compose Static Badge" src="https://img.shields.io/badge/Compose-3983f9?style=for-the-badge">
<img alt="Datastore Static Badge" src="https://img.shields.io/badge/Datastore-3983f9?style=for-the-badge">
<img alt="ViewModel Static Badge" src="https://img.shields.io/badge/ViewModel-3983f9?style=for-the-badge">
<img alt="Android Static Badge" src="https://img.shields.io/badge/Android-34a853?style=for-the-badge&logo=Android&logoColor=ffffff">
<img alt="Android API Static Badge" src="https://img.shields.io/badge/API%2024%2B-34a853?style=for-the-badge">
<img alt="Hilt Static Badge" src="https://img.shields.io/badge/Hilt-34a853?style=for-the-badge">
<img alt="Material Design 2 Static Badge" src="https://img.shields.io/badge/Material%20Design%202-747474?style=for-the-badge&logo=MaterialDesign&logoColor=ffffff">
<img alt="Lottie Compose Static Badge" src="https://img.shields.io/badge/Lottie Compose-04d1c1?style=for-the-badge&logo=airbnb&logoColor=ffffff">
<img alt="Moshi Static Badge" src="https://img.shields.io/badge/Moshi-333333?style=for-the-badge&logo=Square&logoColor=ffffff">
<img alt="Retrofit Static Badge" src="https://img.shields.io/badge/Retrofit-47b983?style=for-the-badge&logo=square&logoColor=ffffff">
</div>

---

#### An android weather sample app with [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAiApuCrBhAuEiwA8VJ6Jv6a4ODF3hlsKL1xSl8aBWfprO7tOZ-vFEOYSQh0lT7d1XHemHpnExoCnfIQAvD_BwE&gclsrc=aw.ds) which uses the [Bright Sky API](https://brightsky.dev/) to get data of the [German Meteorological Service - DWD](https://www.dwd.de/EN/ourservices/opendata/opendata.html).

> [!NOTE]
> Only locations inside germany are supported for now.

## Screenshots

<p align="left">
<img src="https://github.com/public-value-tech/pub-android-weather-sample/assets/36038891/4a691a6a-588c-4e40-8c54-529899fb9764" width="32%"/>
<img src="https://github.com/public-value-tech/pub-android-weather-sample/assets/36038891/c2c12839-8465-4497-b589-a806ec562a7a" width="32%"/>
<img src="https://github.com/public-value-tech/pub-android-weather-sample/assets/36038891/c5d985d1-9341-40a0-bdf2-f79fc1bb9588" width="32%"/>
</p>

## Build with

- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Kotlin Flows](https://kotlinlang.org/docs/reference/coroutines/flow.html)
- [Material Design](https://m2.material.io/)
- [Multiple language support (en, de)](https://developer.android.com/training/basics/supporting-devices/languages)
- [Jetpack Compose](https://developer.android.com/jetpack/compose?)
- [Datastore](https://developer.android.com/topic/libraries/architecture/datastore)
- [Retrofit & OkHttp](https://github.com/square/retrofit)
- [Moshi](https://github.com/square/moshi)
- [Lottie](https://github.com/airbnb/lottie-android)
- [brightsky-API](https://brightsky.dev/)

## Principles, Pattern & Architecture

- [Layer architecture](https://developer.android.com/topic/architecture#common-principles)
- [Repository Pattern](https://developer.android.com/codelabs/basic-android-kotlin-training-repository-pattern#0)
- [MVVM / Clean Architecture](https://medium.com/@ami0275/mvvm-clean-architecture-pattern-in-android-with-use-cases-eff7edc2ef76)

## Setup

To build the app
a [google-services.json](https://support.google.com/firebase/answer/7015592?hl=en#android&zippy=%2Cin-this-article) file needs to
be added to the app folder. Therefore it is necessary to create
a [firebase](https://support.google.com/appsheet/answer/10104995?hl=en) account.

## Contribute

Rules and guidelines for contributing in this project.

### How to:

- [Fork the project to your own GitHub profile](https://help.github.com/articles/fork-a-repo/)
- Download the forked project using git clone:

    ```sh
    git clone git@github.com:<YOUR_USERNAME>/<YOUR_FORKED_REPONAME>.git
    ```
- Create a new branch with a descriptive name:

    ```sh
    git checkout -b feat/<SHORT-FEATURE-NAME>
    ```
    
    Recommended pattern for branch-names & commit-messages:
    - Feature-branch: ```feat/<SHORT-FEATURE-NAME>``` e.g.: ```feat/added-new-language```
    - Bugfix-branch: ```fix/<SHORT-FIX-NAME>``` e.g.: ```fix/fixed-icon-mapping```
    - Commit on feature branch: e.g.: ```Added new language for france```
    - Commit on bugfix branch: e.g.: ```Fixed icon mapping for heavy rain```
    
- Write some code, fix something, and add a test to prove that it works.
- Commit your code and push it to GitHub
- [Open a new pull request](https://help.github.com/articles/creating-a-pull-request/) and describe the changes you have made.
- We'll accept your changes after a completed review.

> [!IMPORTANT]
> Do not exceed the number of 500 lines for a pull-request to keep them small and the review easier.

### Code of Conduct:

This project and everyone participating in it is governed by its [Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are
expected to uphold this code.

Thank you!

## Why we made this?
As part of of the German public broadcaster, we aim to offer insights into our Android development process and openly showcase it to the public community.

## Acknowledgements

- [Bright Sky Team](https://github.com/jdemaeyer/brightsky)
- [Deutscher Wetterdienst - DWD](https://www.dwd.de/)
- [Great meteocons by basmilius](https://github.com/basmilius/weather-icons)
- [Tabler Icons](https://github.com/tabler/tabler-icons)
- [Flag Icons](https://github.com/lipis/flag-icons/tree/main)
- [Landscape photography by Florian Thoma](https://www.instagram.com/flori.thoma/)

<a href="https://pub.tech/"><img src="https://pub.tech/_next/static/media/pub_logo_aqua.3362b8ee.png" alt="Bright Sky Api" height="60"></a>
<a href="https://github.com/jdemaeyer/brightsky"><img src="https://raw.githubusercontent.com/jdemaeyer/brightsky/280f2901013c1b7f16d2f30549ad14aa51067703/docs/favicon.svg" alt="Bright Sky Api" height="100"></a>
<a href="https://www.dwd.de/"><img src="https://raw.githubusercontent.com/jdemaeyer/brightsky/280f2901013c1b7f16d2f30549ad14aa51067703/docs/img/dwd.svg" alt="Deutscher Wetterdienst" height="100"></a>
