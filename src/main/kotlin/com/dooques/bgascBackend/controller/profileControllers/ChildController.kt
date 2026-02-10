package com.dooques.bgascBackend.controller.profileControllers

import com.dooques.bgascBackend.data.dto.ChildDto
import com.dooques.bgascBackend.data.service.FirestoreService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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

    @GetMapping("/single")
    fun getChildById(@RequestParam name: String) =
        firebaseService.getChildByName(name)

    @GetMapping("/all")
    fun getAllChildren() =
        firebaseService.getChildProfiles()

    @PostMapping
    fun saveChild(
        @RequestParam idToken: String,
        @RequestBody child: ChildDto
    ) = firebaseService.saveChild(idToken, child)

    @DeleteMapping
    fun deleteChild(idToken: String) =
        firebaseService.deleteChild(idToken)
}