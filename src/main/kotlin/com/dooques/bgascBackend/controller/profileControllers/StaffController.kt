package com.dooques.bgascBackend.controller.profileControllers

import com.dooques.bgascBackend.data.dto.StaffDto
import com.dooques.bgascBackend.data.service.FirestoreService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/staff")
class StaffController(
    private val firestoreService: FirestoreService
) {
    init {
        println("***************************************")
        println("***  Staff Controller Initialized   ***")
        println("***************************************")
    }

    @GetMapping("/one")
    fun getStaffById(idToken: String) =
        firestoreService.getStaffById(idToken)

    @GetMapping("/all")
    fun getAllStaff() =
        firestoreService.getStaffProfiles()

    @PostMapping
    fun saveStaff(idToken: String, staff: StaffDto) =
        firestoreService.saveStaff(idToken, staff)

    @DeleteMapping
    fun deleteStaff(idToken: String) =
        firestoreService.deleteStaff(idToken)

}