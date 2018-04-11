package com.bmarty.homemonitor.realm

import com.bmarty.homemonitor.data.Message
import io.realm.Realm

// TODO Store date?
fun storeMessage(realm: Realm, message: Message) {
    // Check that message has an Id
    if (message.id == null) {
        // Find next id
        val nextId = realm.where(Message::class.java).count()

        message.id = nextId + 1
    }

    realm.executeTransaction {
        it.copyToRealmOrUpdate(message)
    }
}

fun clearMessages(realm: Realm) {
    realm.executeTransaction {
        it.where(Message::class.java).findAll().deleteAllFromRealm()
    }
}