package com.example.chatapp.database

import com.example.chatapp.database.model.AppUser
import com.example.chatapp.database.model.Message
import com.example.chatapp.database.model.Room
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun getCollection(collectionName:String):CollectionReference{
    val db = Firebase.firestore
    return db.collection(collectionName)
}

fun addUserToFireStore(user: AppUser,
                       onSuccessListener: OnSuccessListener<Void>,
                       onFailureListener: OnFailureListener){
    val userCollection = getCollection(AppUser.COLLECTION_NAME)
    val userDoc = userCollection.document(user.id!!)
    userDoc.set(user)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun signIn(uid:String,
           onSuccessListener: OnSuccessListener<DocumentSnapshot>,
           onFailureListener: OnFailureListener){
    val collectionRef = getCollection(AppUser.COLLECTION_NAME)
    collectionRef.document(uid)
        .get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)


}

fun addRoomToFireStore(room: Room,
                       onSuccessListener: OnSuccessListener<Void>,
                       onFailureListener: OnFailureListener){
    val coll = getCollection(Room.COLLECTION_NAME)
    val doc = coll.document()
    // a little different here between creating user and creating room
    // here at first the room id is null so after we create the document we have to path the id to the room.id
    room.id = doc.id
    doc.set(room)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getRoomsFromFireStore(onSuccessListener: OnSuccessListener<QuerySnapshot>,
                          onFailureListener: OnFailureListener) {
    val collection = getCollection(Room.COLLECTION_NAME)
    collection.get()
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)

}

fun getMessagesRef(roomId:String):CollectionReference{
    val collectionRef = getCollection(Room.COLLECTION_NAME)
    val roomRef = collectionRef.document(roomId)
    return roomRef.collection(Message.COLLECTION_NAME)
}

fun addMessageToFireStore(message: Message,
                          onSuccessListener: OnSuccessListener<Void>,
                          onFailureListener: OnFailureListener){

    val messagesCollRef = getMessagesRef(message.roomId!!)
    val messageRef = messagesCollRef.document()
    message.id = messageRef.id
    messageRef.set(message)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)


}

