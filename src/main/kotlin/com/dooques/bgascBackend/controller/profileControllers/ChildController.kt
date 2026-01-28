package com.dooques.bgascBackend.controller.profileControllers

import com.dooques.bgascBackend.data.dto.ChildDto
import com.dooques.bgascBackend.data.service.FirestoreService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/child")
class ChildController(
   private val firebaseService: FirestoreService
) {
    init {
        println("***************************************")
        println("***  Child Controller Initialized   ***")
        println("***************************************")
    }

    @GetMapping("/one")
    fun getChildById(
        @RequestParam idToken: String
    ) = firebaseService.getChildById(idToken)

    @GetMapping("/all")
    fun getAllChildren() =
        firebaseService.getChildProfiles()

    @PostMapping
    fun saveChild(idToken: String, child: ChildDto) =
        firebaseService.saveChild(idToken, child)

    @DeleteMapping
    fun deleteChild(idToken: String) =
        firebaseService.deleteChild(idToken)
}