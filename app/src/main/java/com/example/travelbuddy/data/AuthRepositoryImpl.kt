package com.example.travelbuddy.data

import com.example.travelbuddy.data.model.ResponseModel
import com.example.travelbuddy.data.model.UserModel
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

    override var user = firebaseAuth.currentUser

    override fun login(email: String, password: String): Flow<ResponseModel.ResponseWithData<AuthResult>> {
        return flow {
            emit(ResponseModel.ResponseWithData.Loading())
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(ResponseModel.ResponseWithData.Success(data = result))
        }.catch {
            emit(ResponseModel.ResponseWithData.Failure(data = null, error = it.message.toString()))
        }
    }

    override fun signup(name: String, email: String, password: String
    ): Flow<ResponseModel.ResponseWithData<AuthResult>> {
        return flow {
            emit(ResponseModel.ResponseWithData.Loading())
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            val user = hashMapOf("uid" to result.user?.uid, "name" to name, "email" to email)

            result.user?.uid?.let {
                val firestoreDb = Firebase.firestore
                firestoreDb.collection("users").document(result.user?.uid!!).set(user)
                emit(ResponseModel.ResponseWithData.Success(result))
            } ?: emit(ResponseModel.ResponseWithData.Failure(error = "Error adding value to Database"))

        }.catch {
            emit(ResponseModel.ResponseWithData.Failure(error = it.message.toString()))
        }
    }

    override suspend fun getUserInfo(uid: String): ResponseModel.ResponseWithData<UserModel.User> {
        return try {
            val firestoreDb = Firebase.firestore
            val userSnapshot = firestoreDb.collection("users").document(uid).get().await()

            if (userSnapshot.exists()) {
                val userData = userSnapshot.data

                userData?.let {
                    val user = UserModel.User(
                        userData["id"] as String,
                        userData["email"] as String,
                        userData["name"] as String,
                        userData["trips"] as List<String>
                    )
                    ResponseModel.ResponseWithData.Success(user)
                } ?: ResponseModel.ResponseWithData.Failure(error = "Failed to convert document to User")
            } else {
                ResponseModel.ResponseWithData.Failure(error = "User document does not exist")
            }
        } catch (e: Exception) {
            ResponseModel.ResponseWithData.Failure(error = e.message.toString())
        }
    }

    override fun sendPasswordResetEmail(email: String): Flow<ResponseModel.ResponseWithData<Boolean>> {
        return flow {
            emit(ResponseModel.ResponseWithData.Loading())
            val result = firebaseAuth.sendPasswordResetEmail(email).await()
            emit(ResponseModel.ResponseWithData.Success(true))
        }.catch {
            emit(ResponseModel.ResponseWithData.Failure(data = null, error = it.message.toString()))
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}
