package com.ojasx.eduplay.ViewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    private var userDocListener: ListenerRegistration? = null

    init {
        // Start listening if already logged in
        auth.currentUser?.uid?.let { uid ->
            listenToUserDoc(uid)
            ensureUserDoc(uid)
        }

        // Keep in sync with auth session changes
        auth.addAuthStateListener { firebaseAuth ->
            val uid = firebaseAuth.currentUser?.uid
            if (uid == null) {
                userDocListener?.remove()
                userDocListener = null
                _profileState.value = ProfileState()
            } else {
                listenToUserDoc(uid)
                ensureUserDoc(uid)
            }
        }
    }

    private fun listenToUserDoc(uid: String) {
        if (userDocListener != null) return
        userDocListener = firestore.collection("users")
            .document(uid)
            .addSnapshotListener { snapshot, _ ->
                val data = snapshot?.data.orEmpty()
                _profileState.value = _profileState.value.copy(
                    firstName = (data["firstName"] as? String).orEmpty(),
                    lastName = (data["lastName"] as? String).orEmpty(),
                    email = (data["email"] as? String).orEmpty(),
                    profileImageUri = data["profileImageUri"] as? String
                )
            }
    }

    private fun ensureUserDoc(uid: String) {
        val user = auth.currentUser ?: return
        val nameParts = (user.displayName ?: "").trim().split(" ").filter { it.isNotBlank() }
        val first = nameParts.firstOrNull().orEmpty()
        val last = nameParts.drop(1).joinToString(" ")
        val payload = mapOf(
            "firstName" to first,
            "lastName" to last,
            "email" to (user.email ?: "")
        )
        firestore.collection("users")
            .document(uid)
            .set(payload, com.google.firebase.firestore.SetOptions.merge())
    }

    fun updateProfile(first: String, last: String, mail: String) {
        val uid = auth.currentUser?.uid ?: return
        firestore.collection("users")
            .document(uid)
            .set(
                mapOf(
                    "firstName" to first,
                    "lastName" to last,
                    "email" to mail
                ),
                com.google.firebase.firestore.SetOptions.merge()
            )
    }

    fun updateProfileImage(uri: String?) {
        val uid = auth.currentUser?.uid ?: return
        firestore.collection("users")
            .document(uid)
            .set(
                mapOf("profileImageUri" to uri),
                com.google.firebase.firestore.SetOptions.merge()
            )
    }

}

data class ProfileState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val profileImageUri: String? = null
)