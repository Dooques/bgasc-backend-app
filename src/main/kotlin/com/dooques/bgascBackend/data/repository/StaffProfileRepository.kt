package com.dooques.bgascBackend.data.repository

import com.dooques.bgascBackend.data.dto.StaffDto
import com.google.cloud.firestore.Firestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import com.google.firebase.cloud.FirestoreClient
import org.springframework.stereotype.Repository
import java.io.FileNotFoundException

@Repository
class StaffProfileRepository {
    private val db: Firestore get() = FirestoreClient.getFirestore()

    fun saveStaff(idToken: String, staff: StaffDto): StaffDto {
        println("Saving staff profile: $staff")
        println("Checking Id Token: $idToken")
        val decodedToken: FirebaseToken
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
        } catch (e: Exception) {
            println("Error verifying token: $e")
            throw e
        }
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

        try {
            db.collection("staff").document(staff.id ?: throw NullPointerException("Staff ID is null")).set(staff).get()
        } catch (e: Exception) {
            println("Error updating staff profile: $e")
            throw e
        }
    }

    fun deleteStaff(idToken: String) {
        val decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)

        try {
            db.collection("staff").document(decodedToken.uid).delete().get()
        } catch (e: Exception) {
            println("Error deleting staff profile: $e")
            throw e
        }
    }

    fun getStaffByName(name: String): List<StaffDto?> {
        println("Getting staff profile for $name")
        val collectionRef = db.collection("staff")
        val query = collectionRef
            .whereEqualTo("name", name)
            .get()
            .get()
        println("Got staff profile: $query")
        return query.mapNotNull {
            it.toObject(StaffDto::class.java)
        }
    }

    fun getAllStaff(): List<StaffDto>? {
        println("Getting all staff profiles")
        val snapshot = db.collection("staff").get().get()
        return snapshot.mapNotNull {
            it.toObject(StaffDto::class.java)
        }
    }
}