package com.mimacom.prometheusspringdemo

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@Document("users")
data class User(
    @Id var id: String? = null,
    @Indexed(unique = true) val name: String = "",
)

@Repository
interface UserRepository : MongoRepository<User, String> {
    fun findByName(name: String): User?
}

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class MongoController(
    private val repository: UserRepository,
    meterRegistry: MeterRegistry,
) {

    val userCounter: AtomicLong? = meterRegistry.gauge("users_count", AtomicLong(0))

    @GetMapping("/users")
    fun getAllUsers(): List<User> = repository.findAll().also { users ->
        userCounter?.set(users.size.toLong())
    }

    @PostMapping("/users/{name}")
    fun saveUser(@PathVariable name: String): ResponseEntity<User> {
        repository.findByName(name)?.let {
            return ResponseEntity.badRequest().build()
        }
        return ResponseEntity.ok(repository.save(User(name = name)))
    }

    @DeleteMapping("/users/{name}")
    fun deleteByName(@PathVariable name: String): ResponseEntity<User> {
        repository.findByName(name)?.let {
            repository.delete(it)
            return ResponseEntity.ok(it)
        }
        return ResponseEntity.notFound().build()
    }
}



