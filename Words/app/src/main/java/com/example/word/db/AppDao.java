package com.example.word.db;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.example.word.model.Question;
import com.example.word.model.User;
import com.example.word.model.Words;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void coll(Words... data);

    @Query("SELECT * FROM words")
    public  List<Words> getAll();

    @Query("DELETE  FROM words where word=:word")
      void delelte(String word);


    @Insert(onConflict = OnConflictStrategy.ABORT)
     void signUp(User... data);


    @Query("SELECT * FROM user WHERE password= :password AND "
            + "name = :name ")
    User login(String name, String password);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertError(Question... questions);

    @Query("SELECT * FROM question")
    public  List<Question> getAllQuestion();

    @Query("DELETE  FROM question where id=:id")
    void delelteQuestion(String id);
}
