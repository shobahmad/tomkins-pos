package com.erebor.tomkins.pos.di

import com.erebor.tomkins.pos.factory.SecurityEncryptionFactory
import com.erebor.tomkins.pos.tools.SecurityEncryption

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class UtilsModule {

    //    @Provides
    //    @Singleton
    //    LPOSDatabase providesDatabase(Context context)  {
    //        return LPOSDatabase.getInstance(context);
    //    }
    //
    @Provides
    @Singleton
    fun providesSecurityEncryption(): SecurityEncryption {
        return SecurityEncryptionFactory.instance.encryption
    }

}
