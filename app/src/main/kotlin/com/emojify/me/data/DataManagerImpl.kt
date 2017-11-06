package com.emojify.me.data

import com.emojify.me.data.file.FileHelper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author lusinabrian on 06/11/17.
 * @Notes implementation for data manager
 */
@Singleton
class DataManagerImpl @Inject constructor(val fileHelper: FileHelper): DataManager{

}