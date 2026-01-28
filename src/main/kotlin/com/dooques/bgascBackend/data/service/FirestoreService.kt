package com.dooques.bgascBackend.data.service

import com.dooques.bgascBackend.data.dto.ChildDto
import com.dooques.bgascBackend.data.dto.ParentDto
import com.dooques.bgascBackend.data.dto.StaffDto
import com.dooques.bgascBackend.data.repository.ChildProfileRepository
import com.dooques.bgascBackend.data.repository.ParentProfileRepository
import com.dooques.bgascBackend.data.repository.StaffProfileRepository
import org.springframework.stereotype.Service

@Service
class FirestoreService(
    private val staffProfileRepository: StaffProfileRepository,
    private val childProfileRepository: ChildProfileRepository,
    private val parentProfileRepository: ParentProfileRepository
) {
    // Save Functions
    fun saveStaff(idToken: String, staff: StaffDto) =
        staffProfileRepository.saveStaff(idToken, staff)
    fun saveChild(idToken: String, child: ChildDto) =
        childProfileRepository.saveChild(idToken, child)
    fun saveParent(idToken: String, parent: ParentDto) =
        parentProfileRepository.saveParent(idToken, parent)

    // Update Functions
    fun updateStaff(idToken: String, staff: StaffDto) =
        staffProfileRepository.updateStaff(idToken, staff)
    fun updateChild(idToken: String, child: ChildDto) =
        childProfileRepository.updateChild(idToken, child)
    fun updateParent(idToken: String, parent: ParentDto) =
        parentProfileRepository.updateParent(idToken, parent)

    // Delete Functions
    fun deleteStaff(idToken: String) =
        staffProfileRepository.deleteStaff(idToken)
    fun deleteChild(idToken: String) =
        childProfileRepository.deleteChild(idToken)
    fun deleteParent(idToken: String) =
        parentProfileRepository.deleteParent(idToken)

    // Get Functions
    fun getStaffProfiles() =
        staffProfileRepository.getAllStaff()
    fun getChildProfiles() =
        childProfileRepository.getAllChildren()
    fun getParentProfiles() =
        parentProfileRepository.getAllParents()

    fun getStaffById(userId: String) =
        staffProfileRepository.getStaffById(userId)
    fun getChildById(userId: String) =
        childProfileRepository.getChildById(userId)
    fun getParentById(userId: String) =
        parentProfileRepository.getParentById(userId)

}