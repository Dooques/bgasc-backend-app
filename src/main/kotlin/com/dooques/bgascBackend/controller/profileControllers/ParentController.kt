package com.dooques.bgascBackend.controller.profileControllers

import com.dooques.bgascBackend.data.dto.ParentDto
import com.dooques.bgascBackend.data.service.FirestoreService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/parent")
class ParentController(
    private val firestoreService: FirestoreService
) {
    init {
        println("***************************************")
        println("***  Parent Controller Initialized  ***")
        println("***************************************")
    }

    @GetMapping("/one")
    fun getParentById(@RequestParam idToken: String) =
        firestoreService.getParentById(idToken)

    @GetMapping("/all")
    fun getAllParents() =
        firestoreService.getParentProfiles()

    @PostMapping
    fun saveParent(idToken: String, parent: ParentDto) =
        firestoreService.saveParent(idToken, parent)

    @DeleteMapping
    fun deleteParent(idToken: String) =
        firestoreService.deleteParent(idToken)
}