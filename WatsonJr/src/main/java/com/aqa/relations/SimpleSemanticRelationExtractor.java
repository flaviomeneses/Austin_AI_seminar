package com.aqa.relations;

import edu.stanford.nlp.simple.Sentence;

import java.util.ArrayList;
import java.util.List;

/**
 * The class that extracts {@link SemanticRelation}s from documents. You will need to implement the {@link
 * #extractSemanticRelations(Sentence)} method.
 * <p>
 * You will need to use the information from CoreNLP in order to identify semantic relations. The information from
 * CoreNLP is contained within the {@link Sentence} that is given to the {@link #extractSemanticRelations(Sentence)}
 * method. Here are some of the methods from {@link Sentence} that will be useful: {@link Sentence#lemmas()}, {@link
 * Sentence#nerTags()}, {@link Sentence#parse()}, {@link Sentence#words()}, {@link Sentence#dependencyGraph()}.
 */
public class SimpleSemanticRelationExtractor implements SemanticRelationExtractor {
    private static final SimpleSemanticRelationExtractor simpleSemanticRelationExtractor =
            new SimpleSemanticRelationExtractor();

    private SimpleSemanticRelationExtractor() {
    }

    /**
     * Returns an instance of {@link SimpleSemanticRelationExtractor}.
     *
     * @return the instance
     */
    public static SemanticRelationExtractor getSemanticRelationExtractor() {
        return simpleSemanticRelationExtractor;
    }

    @Override
    public List<SemanticRelation> extractSemanticRelations(Sentence document) {
        // TODO Implement a system for examining a document and extracting semantic relations.

        // Use EmploymentSemanticRelation.createRelation(...) to create a semantic relation about employment.
        //
        // You will need to use CoreNLP methods in the Sentence class to get the information you need to pass to the
        // createRelation method.
        List<SemanticRelation> relations = new ArrayList<>();

        for(final String word: document.lemmas()){
            String employee=null;
            String employer=null;
            String location=null;
            if(word.equalsIgnoreCase("work") || word.equalsIgnoreCase("hire")
                    || word.equalsIgnoreCase("employ")) {
                final List<String> ner= document.nerTags();
                for(int j=0; j<ner.size(); j++){
                    if(document.nerTag(j).equalsIgnoreCase("person")) {
                        employee = document.word(j);
                    }
                    if(document.nerTag(j).equalsIgnoreCase("organization")) {
                        employer = document.word(j);
                    }
                    if(document.nerTag(j).equalsIgnoreCase("location")) {
                        location = document.word(j);
                    }
                }
                relations.add(EmploymentSemanticRelation.createRelation(employee, employer, location));
            }
        }
        return relations;
    }
}
