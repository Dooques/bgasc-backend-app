package com.dooques.bgascBackend.data.repository

import com.dooques.bgascBackend.data.dto.ParentDto
import com.google.cloud.firestore.Firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import org.springframework.stereotype.Repository

@Repository
class ParentProfileRepository {
    private val db: Firestore get() = FirestoreClient.getFirestore()

    fun saveParent(idToken: String, parent: ParentDto) {
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        val uid = decodedToken.uid
        val email = decodedToken.email

        val parentProfile = ParentDto(
            id = parent.name + uid,
            email = email,
            name = parent.name,
            number = parent.number,
            age = parent.age,
            address = parent.address,
            children = parent.children
        )

        db.collection("parents").document(uid).set(parentProfile).get()
    }

    fun updateParent(idToken: String, parent: ParentDto) {
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        val uid = decodedToken.uid

        db.collection("parents").document(uid).set(parent).get()
    }

    fun deleteParent(idToken: String) {
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        val uid = decodedToken.uid

        db.collection("parents").document(uid).delete().get()
    }

    fun getParentById(userId: String): ParentDto? {
        val docRef = db.collection("parents").document(userId)
        val snapshot = docRef.get().get()
        return if (snapshot.exists())
            snapshot.toObject(ParentDto::class.java)
        else null
    }

    fun getAllParents(): List<ParentDto>? {
        return db.collection("parents").get().get().mapNotNull {
            it.toObject(ParentDto::class.java)
        }
    }
}