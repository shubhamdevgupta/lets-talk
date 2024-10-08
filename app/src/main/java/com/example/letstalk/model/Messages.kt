package com.example.letstalk.model

class Messages {
    var senderId: String = ""
    var reciverId: String = ""
    var message: String = ""
    var timeStamp: String = ""
    var attachImage: String = ""
    var userProfile: String = ""
    var isOutgoing: Boolean = false

    constructor()
    constructor(
        senderId: String,
        message: String,
        timeStamp: String,
        reciverId: String,
        attachImage: String,
        userProfile: String,
        isOutgoing: Boolean
    ) {
        this.reciverId = reciverId
        this.timeStamp = timeStamp
        this.message = message
        this.senderId = senderId
        this.attachImage = attachImage
        this.userProfile = userProfile
        this.isOutgoing = isOutgoing
    }
}

