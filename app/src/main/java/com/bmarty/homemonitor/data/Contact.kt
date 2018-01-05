package com.bmarty.homemonitor.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class Contact(@PrimaryKey var number: String,
                   var name: String,
                   val isClient: Boolean) //: RealmObject()