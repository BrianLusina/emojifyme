package com.emojify.me.data

import com.emojify.me.data.file.FileHelper
import com.emojify.me.data.prefs.SharedPrefsHelper

/**
 * @author lusinabrian on 06/11/17.
 * @Notes DataManager interface that will handle data/model layer of app
 */
interface DataManager : FileHelper, SharedPrefsHelper{
}