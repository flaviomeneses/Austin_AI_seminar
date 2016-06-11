package com.aqa.candidates;

import com.aqa.kb.KnowledgeBase;
import com.aqa.relations.SemanticRelation;
import com.aqa.relations.SemanticRelationExtractor;
import com.aqa.relations.SimpleSemanticRelationExtractor;
import edu.stanford.nlp.ling.Sentence;

import java.util.List;

/**
 * The class that uses a {@link SemanticRelationExtractor} to answer questions. You will need to implement the {@link
 * #answerQuestion(KnowledgeBase, String, SemanticRelationExtractor)} method.
 */
public class SemanticRelationRanker implements Ranker {
    @Override
    public RankedCandidates answerQuestion(KnowledgeBase knowledgeBase, String question) {
        return answerQuestion(knowledgeBase, question, SimpleSemanticRelationExtractor.getSemanticRelationExtractor());
    }

    private RankedCandidates answerQuestion(KnowledgeBase knowledgeBase, String question,
                                            SemanticRelationExtractor semanticRelationExtractor) {
        // TODO Implement a system that uses the knowledge base and semantic relation extractor to answer the question

        RankedCandidates.Builder builder = new RankedCandidates.Builder(question);
        List<SemanticRelation> questionRelations = semanticRelationExtractor.extractSemanticRelations(
                new edu.stanford.nlp.simple.Sentence(question));

        for (final com.aqa.kb.Document document : knowledgeBase.getDocuments()){
            float score = 0;
            for(SemanticRelation questionRelation : questionRelations) {
                for (SemanticRelation documentRelation : document.getSemanticRelations()) {
                    for (String featureName : questionRelation.getFeatures().keySet()) {
                        String questionFeatureValue = questionRelation.getFeatureValue(featureName);
                        String documentFeatureValue = documentRelation.getFeatureValue(featureName);
                        if (questionFeatureValue != null) {
                            if (questionFeatureValue.equalsIgnoreCase(documentFeatureValue)) {
                                score += 1;
                            }
                        }
                    }

                }
            }
            if(score > 0) builder.addCandidate(document, score);
        }
        return builder.build();
    }
}
