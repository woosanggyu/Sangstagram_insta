package com.example.sangstagram_firebase_server.navigation.model

data class AlarmDTO (
    var destinationUid : String? = null,
    var userId : String? = null,
    var uid : String? = null,
    var kind : Int? = null,
    var message : String? = null,
    var timestamp : Long? = null
)