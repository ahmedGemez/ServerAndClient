package com.example.clientapp.data.di

import com.example.clientapp.data.repos.RepoImp
import com.example.clientapp.domain.repos.Repo
import dagger.Module
import dagger.Provides

@Module
class RepoMoule {

    @Provides
    public fun provideRemoteRepo(repoImp: RepoImp): Repo {
        return repoImp;
    }
}