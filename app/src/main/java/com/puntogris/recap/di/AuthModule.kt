package com.puntogris.recap.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.puntogris.recap.BuildConfig
import com.puntogris.recap.core.data.remote.FirebaseClients
import com.puntogris.recap.feature_auth.data.datasource.GoogleSingInDataSource
import com.puntogris.recap.feature_auth.data.repository.AuthRepositoryImpl
import com.puntogris.recap.feature_auth.domain.repository.AuthRepository
import com.puntogris.recap.feature_auth.domain.user_case.LoginUseCase
import com.puntogris.recap.feature_auth.domain.user_case.LogoutUseCase
import com.puntogris.recap.feature_profile.domain.use_case.GetProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AuthModule {

    @Provides
    fun providesAuthRepository(
        firebase: FirebaseClients,
        googleSingInDataSource: GoogleSingInDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(firebase, googleSingInDataSource)
    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(BuildConfig.GOOGLE_WEB_CLIENT_ID)
            .build()

        return GoogleSignIn.getClient(context, gso)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(repository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(repository)
    }
}