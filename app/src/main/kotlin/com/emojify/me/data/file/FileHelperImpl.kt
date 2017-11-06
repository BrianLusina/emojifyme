package com.emojify.me.data.file

import android.content.Context
import com.emojify.me.di.qualifiers.AppContextQualifier
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 06/11/17.
 * @Notes Implementation for the file helper
 */
@Singleton
class FileHelperImpl @Inject constructor(@AppContextQualifier val context: Context): FileHelper{

}