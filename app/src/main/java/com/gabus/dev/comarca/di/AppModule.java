package com.gabus.dev.comarca.di;

import android.app.Application;

import com.gabus.dev.comarca.data.repository.CardRepository;
import com.gabus.dev.comarca.data.repository.DeckRepository;
import com.gabus.dev.comarca.data.repository.RunManager;
import com.gabus.dev.comarca.data.repository.RunRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public CardRepository provideCardRepository(Application application) {
        return new CardRepository(application);
    }

    @Provides
    @Singleton
    public DeckRepository provideDeckRepository(Application application) {
        return new DeckRepository(application);
    }

    @Provides
    @Singleton
    public RunRepository provideRunRepository(Application application) {
        return new RunRepository(application);
    }

    @Provides
    @Singleton
    public RunManager provideRunManager(RunRepository runRepository, DeckRepository deckRepository,
                                         CardRepository cardRepository) {
        return new RunManager(runRepository, deckRepository, cardRepository);
    }
}
