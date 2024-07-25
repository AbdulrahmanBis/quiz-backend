package com.example.demo.service;

import com.example.demo.model.Question;
import com.example.demo.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateQuestion(int id , Question question) {
        try {
            Optional<Question> quesOptional = questionDao.findById(id);
            if (quesOptional.isPresent()){
                Question ques = quesOptional.get();
                ques.setCategory(question.getCategory());
                ques.setOption1(question.getOption1());
                ques.setOption2(question.getOption2());
                ques.setOption3(question.getOption3());
                ques.setOption4(question.getOption4());
                ques.setCorrectAnswer(question.getCorrectAnswer());
                ques.setDifficultyLevel(question.getDifficultyLevel());
                ques.setQuestionTitle(question.getQuestionTitle());
                questionDao.save(ques);
               return new ResponseEntity<>("success",HttpStatus.OK);
            }else {
                return new ResponseEntity<>("question not found",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("failure",HttpStatus.BAD_REQUEST);
        }
        
  public ResponseEntity<String> deleteQuestion(int id){
    
        try{
        questionDao.deleteById(id);
        return new ResponseEntity<>("success",HttpStatus.OK);
        }catch( Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("faliure", HttpStatus.BAD_REQUEST);
        
  }

public ResponseEntity<Question> getQuestion(int id) {
    try {
        return new ResponseEntity<>(questionDao.findById(id).get() , HttpStatus.OK);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return new ResponseEntity<>(new Question(),HttpStatus.BAD_REQUEST);
}

}
