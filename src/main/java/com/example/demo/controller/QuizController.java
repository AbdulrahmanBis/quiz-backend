package com.example.demo.controller;

// import com.example.demo.model.Question;
import com.example.demo.model.QuestionWrapper;
import com.example.demo.model.Quiz;
import com.example.demo.model.QuizQuestionsResponse;
import com.example.demo.model.Response;
import com.example.demo.service.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
// controller for quiz
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody Map<String, Object> quizData) {
        String category = (String) quizData.get("category");
        int numQ = ((Number) quizData.get("numQ")).intValue();
        String title = (String) quizData.get("title");
        return quizService.createQuiz(category, numQ, title);
    }
    @GetMapping("get/{id}")
    public ResponseEntity<QuizQuestionsResponse> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @GetMapping("all")
    public ResponseEntity<List<Quiz>> getAllQuizzes(){
        return quizService.getAllQuizzes();
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<String> updateQuiz (@PathVariable int id , @RequestBody Map<String , Object> quizData){

    return quizService.updateQuiz(id, quizData);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable int id){
        
        return quizService.deleteQuiz(id);
    }

    @GetMapping("getQuiz/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable int id){
         return quizService.getQuiz(id);
    }


}
