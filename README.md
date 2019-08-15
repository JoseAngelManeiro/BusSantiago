# Bus Santiago

This project is part of the refactoring process of [Bus Santiago][bus-santiago], a personal app published in Google Play Store.

**NOTE** So far, this project covers only the main flow, i.e. obtaining the arrival times of a bus stop:


![ApplicationVideo][application-video]


## Architecture

This app uses [Android Architecture components][architecture] with [Koin][koin].

The architecture is divided into 3 layers:

### Data

I use **Repository** implementations to **control the flow of data access**. In those repositories where there is more than one data source, the abstract class [PrefetchLocalData][prefetchlocaldata] guides the search according to the precept of [Single source of truth][single-source].

### Domain

This layer includes those **models** that are part of the project core (*and are independent of the data layer*) and the **repository interfaces**. Also belong the **Interactors, which use repositories and models to accomplish the use case**.

### Presentation

This layer consists mainly of Fragment-ViewModel pairs.

#### ViewModel

The [ViewModel][viewmodel] is the fundamental piece of the [MVVM pattern][mvvm-pattern]. It exposes [LiveData][livedata] objects, which are **lifecycle-aware**, and thanks to this it can protect the state of the information in situations such as changing the orientation of the screen.

On the other hand, the management of asynchrony is done through an instance of [InteractorExecutor][executor], which is responsible for launching the execution of Interactors.

#### Fragment

The Fragment acts as a simple **Observer** of the data exposed by the ViewModel.

**NOTE** This app follows the **single activity structure** with the [Navigation component][navigation].

## Testing

In this project I have focused mainly on covering with **unit tests** the classes that contain most of the logic of the app: **ViewModels** and **Interactors**.

You can execute them all with this command: `./gradlew runUnitTests`




[bus-santiago]: https://play.google.com/store/apps/details?id=org.galio.bussantiago&hl=es
[application-video]: ./art/ApplicationVideo.gif
[architecture]: https://developer.android.com/topic/libraries/architecture
[koin]: https://insert-koin.io/
[prefetchlocaldata]: https://github.com/JoseAngelManeiro/BusSantiago/blob/master/app/src/main/java/org/galio/bussantiago/data/PrefetchLocalData.kt
[single-source]: https://developer.android.com/jetpack/docs/guide#truth
[viewmodel]: https://developer.android.com/topic/libraries/architecture/viewmodel
[mvvm-pattern]: https://docs.microsoft.com/en-us/previous-versions/msp-n-p/hh848246(v%3dpandp.10)
[livedata]: https://developer.android.com/topic/libraries/architecture/livedata
[resource]: https://github.com/JoseAngelManeiro/BusSantiago/blob/master/app/src/main/java/org/galio/bussantiago/common/Resource.kt
[executor]: https://github.com/JoseAngelManeiro/BusSantiago/blob/master/app/src/main/java/org/galio/bussantiago/common/executor/InteractorExecutor.kt
[navigation]: https://developer.android.com/guide/navigation/navigation-getting-started