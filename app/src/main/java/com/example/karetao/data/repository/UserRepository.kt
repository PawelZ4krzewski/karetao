//package com.example.karetao.data.repository
//
//import androidx.lifecycle.LiveData
//import com.example.karetao.data.DAO.UserDao
//import com.example.karetao.model.User
//import kotlinx.coroutines.flow.Flow
//
//class UserRepository constructor(private val dao: UserDao) {
//
//    fun getUsers(): Flow<List<User>>{
//        return dao.getUsers()
//    }
//
//    suspend fun getUserByUsername(username: String): User? {
//        return dao.getUsersByUsername(username)
//    }
//
//    suspend fun insertUser(user: User){
//        dao.insertUser(user)
//    }
//
//    suspend fun  deleteUser(user: User){
//        dao.deleteUser(user)
//    }
//
//}