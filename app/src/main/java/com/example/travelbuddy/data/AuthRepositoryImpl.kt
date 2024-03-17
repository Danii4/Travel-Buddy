package com.example.travelbuddy.data

import com.example.travelbuddy.domain.model.Response
import com.example.travelbuddy.domain.types.User
import com.example.travelbuddy.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val user get() = firebaseAuth.currentUser

    override fun login(email: String, password: String): Flow<Response<AuthResult>> {
        return flow {
            emit(Response.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Response.Success(data = result))
        }.catch {
            emit(Response.Failure(data = null, msg = it.message.toString()))
        }
    }

    override fun signup(name: String, email: String, password: String
    ): Flow<Response<AuthResult>> {
        return flow {
            emit(Response.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            val user = hashMapOf("uid" to result.user?.uid, "name" to name, "email" to email)

            result.user?.uid?.let {
                val firestoreDb = Firebase.firestore
                firestoreDb.collection("users").document(result.user?.uid!!).set(user)
                emit(Response.Success(result))
            } ?: emit(Response.Failure(msg = "Error adding value to Database"))

        }.catch {
            emit(Response.Failure(msg = it.message.toString()))
        }
    }

    override suspend fun getUserInfo(uid: String): Response<User> {
        return try {
            val firestoreDb = Firebase.firestore
            val userSnapshot = firestoreDb.collection("users").document(uid).get().await()

            if (userSnapshot.exists()) {
                val userData = userSnapshot.data

                userData?.let {
                    val user = User(
                        userData["id"] as String,
                        userData["email"] as String,
                        userData["name"] as String,
                    )
                    Response.Success(user)
                } ?: Response.Failure(msg = "Failed to convert document to User")
            } else {
                Response.Failure(msg = "User document does not exist")
            }
        } catch (e: Exception) {
            Response.Failure(msg = e.message.toString())
        }
    }

    override fun sendPasswordResetEmail(email: String): Flow<Response<Boolean>> {
        return flow {
            emit(Response.Loading())
            val result = firebaseAuth.sendPasswordResetEmail(email).await()
            emit(Response.Success(true))
        }.catch {
            emit(Response.Failure(data = null, msg = it.message.toString()))
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}
