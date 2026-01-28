package com.dooques.bgascBackend.data.repository

import com.dooques.bgascBackend.data.dto.ChildDto
import com.google.cloud.firestore.Firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import org.springframework.stereotype.Repository

@Repository
class ChildProfileRepository {
    private val db: Firestore get() = FirestoreClient.getFirestore()

    fun saveChild(idToken: String, child: ChildDto) {
        println("Saving child profile: $child")
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        val name = decodedToken.name
        val uid = decodedToken.uid

        println("Creating profile for ${name +uid}")

        val childProfile = ChildDto(
            id = child.name + uid,
            name = child.name,
            age = child.age,
            school = child.school,
            guardians = child.guardians,
            allergies = child.allergies,
            keyChild = child.keyChild
        )

        db.collection("children").document(uid).set(childProfile).get()
    }

    fun updateChild(idToken: String, child: ChildDto) {
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        val uid = decodedToken.uid

        db.collection("children").document(uid).set(child).get()
    }

    fun deleteChild(idToken: String) {
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        val uid = decodedToken.uid

        db.collection("children").document(uid).delete().get()
    }

    fun getAllChildren(): List<ChildDto>? {
        return db.collection("children").get().get().mapNotNull {
            it.toObject(ChildDto::class.java)
        }
    }

    fun getChildById(userId: String): ChildDto? {
        val docRef = db.collection("children").document(userId)
        val snapshot = docRef.get().get()
        return if (snapshot.exists())
            snapshot.toObject(ChildDto::class.java)
        else null
    }
}