package com.bmarty.homemonitor.realm


import com.bmarty.homemonitor.data.Message
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.rx.CollectionChange

fun getMessages(realm: Realm): Observable<CollectionChange<RealmResults<Message>>> {
    return realm
            .where(Message::class.java)
            .sort("id", Sort.ASCENDING)
            .findAllAsync()
            .asChangesetObservable()
}