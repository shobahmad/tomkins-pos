package com.erebor.tomkins.pos.di;

import android.content.Context;

import com.erebor.tomkins.pos.factory.SecurityEncryptionFactory;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.helper.WorkerHelper;
import com.erebor.tomkins.pos.helper.impl.DateConverterHelperImpl;
import com.erebor.tomkins.pos.helper.impl.ResourceHelperImpl;
import com.erebor.tomkins.pos.helper.impl.WorkerHelperImpl;
import com.erebor.tomkins.pos.tools.SecurityEncryption;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilsModule {

//    @Provides
//    @Singleton
//    LPOSDatabase providesDatabase(Context context)  {
//        return LPOSDatabase.getInstance(context);
//    }
//
    @Provides
    @Singleton
    public SecurityEncryption providesSecurityEncryption() {
        return SecurityEncryptionFactory.getInstance().getEncryption();
    }
    @Provides
    @Singleton
    public ResourceHelper providesResoirceHelper(Context context) {
        return new ResourceHelperImpl(context);
    }
    @Provides
    @Singleton
    public WorkerHelper providesWorkerHelper(ResourceHelper resourceHelper) {
        return new WorkerHelperImpl(resourceHelper);
    }
    @Provides
    @Singleton
    public DateConverterHelper providesDateConverterHelper(ResourceHelper resourceHelper) {
        return new DateConverterHelperImpl(resourceHelper);
    }





}
