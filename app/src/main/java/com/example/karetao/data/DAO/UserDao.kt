//package com.example.karetao.data.DAO
//
//import androidx.lifecycle.LiveData
//import androidx.room.*
//import com.example.karetao.model.User
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface UserDao {
//
//    @Query("SELECT * FROM users")
//    fun getUsers(): Flow<List<User>>
//
//    @Query("SELECT * From users WHERE username = :username")
//    suspend fun getUsersByUsername(username: String): User?
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertUser(user: User)
//
//    @Delete
//    suspend fun deleteUser(user: User)
//}