package ing.espinoza.architectcoders

import android.app.Application
import ing.espinoza.architectcoders.domain.movie.DomainMovieModule
import ing.espinoza.architectcoders.domain.region.DomainRegionModule
import ing.espinoza.architectcoders.framework.core.frameworkCoreModule
import ing.espinoza.architectcoders.framework.movie.FrameworkMovieModule
import ing.espinoza.architectcoders.framework.region.FrameworkRegionModule
import ing.espinoza.architectcoders.framework.region.frameworkRegionModule
import ing.espinoza.architectcoders.ui.detail.FeatureDetailModule
import ing.espinoza.architectcoders.ui.home.FeatureHomeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ksp.generated.module

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                appModule,
                FeatureHomeModule().module,
                FeatureDetailModule().module,
                DomainMovieModule().module,
                DomainRegionModule().module,
                frameworkCoreModule,
                FrameworkMovieModule().module,
                FrameworkRegionModule().module,
                frameworkRegionModule
            )
        }
    }
}

val appModule = module {
    single(named("apiKey")) { BuildConfig.TMDB_API_KEY }
}