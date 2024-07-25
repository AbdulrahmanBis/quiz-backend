package com.example.demo.service;

import com.example.demo.dao.QuestionDao;
import com.example.demo.dao.QuizDao;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionWrapper;
import com.example.demo.model.Quiz;
import com.example.demo.model.QuizQuestionsResponse;
import com.example.demo.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return new ResponseEntity<>(quizDao.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<QuizQuestionsResponse> getQuizQuestions(Integer id) {
        Optional<Quiz> quizOptional = quizDao.findById(id);
        
        if (quizOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        Quiz quiz = quizOptional.get();
        List<Question> questionsFromDB = quiz.getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        
        for (Question q : questionsFromDB) {
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }
    
        QuizQuestionsResponse response = new QuizQuestionsResponse(quiz.getTitle() , quiz.getId(), questionsForUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;
        for(Response response : responses){
            if(response.getResponse().equals(questions.get(i).getCorrectAnswer()))
                right++;

            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }

    public ResponseEntity<String> updateQuiz(int id, Map<String, Object> quizData) {
        
        try {
            String category = (String) quizData.get("category");
            int numQ = ((Number) quizData.get("numQ")).intValue();
            String title = (String)  quizData.get("title");
            Optional<Quiz> quizOptional = quizDao.findById(id);
            if (quizOptional.isPresent()) {
                 Quiz quiz = quizOptional.get();
                 quiz.setTitle(title);
                 quiz.setQuestions(questionDao.findRandomQuestionsByCategory(category, numQ));
                 quizDao.save(quiz);
                 return new ResponseEntity<>("success", HttpStatus.OK);
            } else {
                 return  new ResponseEntity<>("quiz not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
           e.printStackTrace();
        }

        return new ResponseEntity<>("failure", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuiz(int id){
        
        try {
            quizDao.deleteById(id);
            return new ResponseEntity<>("success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("failure", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Quiz> getQuiz(int id){

        try {
            return new ResponseEntity<>(quizDao.findById(id).get(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new Quiz(), HttpStatus.BAD_REQUEST);
    }
}
