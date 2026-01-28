package com.dooques.bgascBackend.data.repository

import com.dooques.bgascBackend.data.dto.StaffDto
import com.google.cloud.firestore.Firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.cloud.FirestoreClient
import org.springframework.stereotype.Repository

@Repository
class StaffProfileRepository {
    private val db: Firestore get() = FirestoreClient.getFirestore()

    fun saveStaff(idToken: String, staff: StaffDto): StaffDto {
        println("Saving staff profile: $staff")
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        val uid = decodedToken.uid
        val name = decodedToken.name
        val email = decodedToken.email

        println("Creating profile for $name ($uid)")

        val staffProfile = StaffDto(
            id = staff.name + uid,
            email = email,
            name = staff.name,
            number = staff.number,
            role = staff.role,
            address = staff.address,
            nextOfKin = staff.nextOfKin
        )
        try {
            val result = db
                .collection("staff")
                .document(staff.id ?: throw NullPointerException("Staff ID is null"))
                .set(staffProfile).get()

            println("Saved staff profile: $result")
        } catch (e: Exception) {
            println("Error saving staff profile: $e")
            throw e
        }
        return staffProfile
    }

    fun updateStaff(idToken: String, staff: StaffDto) {
        println("Updating staff profile: $staff")
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        val uid = decodedToken.uid

        db.collection("staff").document(staff.id ?: throw NullPointerException("Staff ID is null")).set(staff).get()
    }

    fun deleteStaff(idToken: String) {
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)

        db.collection("staff").document(decodedToken.uid).delete().get()
    }

    fun getStaffById(userId: String): StaffDto? {
        println("Getting staff profile for $userId")
        val docRef = db.collection("staff").document(userId)
        val snapshot = docRef.get().get()
        return if (snapshot.exists())
            snapshot.toObject(StaffDto::class.java)
        else null
    }

    fun getAllStaff(): List<StaffDto>? {
        println("Getting all staff profiles")
        val snapshot = db.collection("staff").get().get()
        return snapshot.mapNotNull {
            it.toObject(StaffDto::class.java)
        }
    }
}