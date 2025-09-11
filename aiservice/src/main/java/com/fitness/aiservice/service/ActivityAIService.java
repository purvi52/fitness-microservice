package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService
{
    private final GeminiService geminiService;

    public String generateRecommendation(Activity activity)
    {
        String prompt=createPromptForActivity(activity);
        String aiResponse=geminiService.getAnswer(prompt);
        log.info("RESPONSE from AI: {}",aiResponse);
        return aiResponse;
    }

    private String createPromptForActivity(Activity activity)
    {
        return String.format("""
                Analyze this fitness activity and provide detailed recommendation in the exact same format
                {
                    "analysis":{
                        "overall":"Overall analysis here",
                        "pace":"pace analysis here",
                        "heartRate":"Heart Rate analysis here",
                        "caloriesBurned":"Calories analysis here"
                    },
                    "improvements":[
                    {
                        "area":"Area name",
                        "recommendation":"Detailed recommendation"
                    }],
                    "suggestions":[
                    {
                        "workout":"Workout name",
                        "description":"Detailed workout description"
                    }],
                    "safety":[
                        "safety point 1",
                        "safety point 2"
                    ],
                }
                
                Analyze this activity:
                Activity Type : %s
                Duration: %d minutes
                Calories Burned: %d
                Additional Metrics: %s
                
                Provide detailed analysis focusing on performance, improvements, next workout suggestions, safety guidelines
                Ensure the response follows the exact JSON format above.""",
                activity.getType(),
                activity.getDuration(),
                activity.getCaloriesBurned(),
                activity.getAdditionalMetrics()
        );
    }

}
